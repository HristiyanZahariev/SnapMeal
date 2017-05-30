package com.snapmeal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.mysql.cj.mysqlx.protobuf.MysqlxCrud;
import com.snapmeal.entity.RecipeJsonApi;
import com.snapmeal.entity.elasticsearch.RatingEs;
import com.snapmeal.entity.elasticsearch.RecipeEs;

import com.snapmeal.entity.jpa.*;
import com.snapmeal.repository.elasticsearch.RecipeEsRepository;
import com.snapmeal.repository.jpa.IngredientRepository;
import com.snapmeal.repository.jpa.RecipeRepository;
import com.snapmeal.repository.jpa.UserRepository;
import com.snapmeal.security.JwtUser;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import org.apache.hadoop.yarn.webapp.hamlet.HamletSpec;
import org.apache.mahout.cf.taste.common.NoSuchItemException;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.elasticsearch.common.recycler.Recycler;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.*;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Created by hristiyan on 12.12.16.
 */
@Component
public class RecipeService {

    @Autowired
    private RecipeEsRepository recipeEsRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IngredientRepository ingredientRepository;


    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    private List<RatingEs> ratingsEs = new ArrayList<RatingEs>();
    private Set<Rating> userRating = new ObjectArraySet<>();

    private float minScore = 0.12000f;

    private String scoreMode = "max";

    private int limit = 0;
    ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    Gson gson = new Gson();

    public Iterable<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public RecipeEs findRecipeById(String id) {
        return recipeEsRepository.findById(id);
    }

    public RecipeEs createRecipe(RecipeJsonApi recipeEs) throws IOException {
        String recipeJson = mapper.writer().withoutAttribute("ingredient").writeValueAsString(recipeEs);
        System.out.println(recipeJson);
        Recipe recipe = mapper.readValue(recipeJson, Recipe.class);
        recipeRepository.save(recipe);

        String recipeEsJson = mapper.writer().withoutAttribute("recipesIngredient").writeValueAsString(recipeEs);
        System.out.println(recipeEsJson);
        RecipeEs recipeEss = mapper.readValue(recipeEsJson, RecipeEs.class);
        return recipeEsRepository.save(recipeEss);

    }

    public List<String> getRecipesByDescription(String description, JwtUser currentJwtUser) throws JsonProcessingException {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        User currentUser = userRepository.findByUsername(currentJwtUser.getUsername());
        System.out.println(currentUser);
        if (currentUser.getDiet() != null) {
            //Ingredients allowed to be used for the diet
            Collection<Ingredient> dietIngredients = currentUser.getDiet().getIngredients();
            Collection<Ingredient> allIngredients = ingredientRepository.findAll();
            //Getting the !allowed ingredients
            allIngredients.removeAll(dietIngredients);
            List<String> ingredientNames = allIngredients.
                    stream()
                    .map((ingredient -> ingredient.getName()))
                    .distinct().collect(Collectors.toList());
            QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                    .must(matchQuery("description", description).boost(2.000f))
                    .mustNot((nestedQuery("ingredient", termsQuery("ingredient.name", ingredientNames))));

            nativeSearchQueryBuilder.withQuery(queryBuilder);
            //nativeSearchQueryBuilder.withMinScore(minScore);
        }
        else {
            QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                    .must(matchQuery("description", description));

            nativeSearchQueryBuilder.withQuery(queryBuilder);
            //nativeSearchQueryBuilder.withMinScore(minScore);
        }

        NativeSearchQuery nativeSearchQuery = nativeSearchQueryBuilder.withPageable(new PageRequest(2, 5)).build();

        System.out.println("nativeQuery" + nativeSearchQuery.getQuery());

        Page<RecipeEs> results = elasticsearchTemplate.queryForPage(nativeSearchQuery, RecipeEs.class);
        List<String> ids = new ArrayList<String>();

        for (RecipeEs result : results) {
            ids.add(result.getId());
        }

        return ids;
    }

    public List<RecipeAPI> getRecipesByIds(List<String> ids, String description) {
        List<RecipeAPI> recipes = new ArrayList<>();

        for (String id : ids) {
            if (recipeRepository.findById(Long.valueOf(id)) != null) {
                RecipeAPI recipeAPI = new RecipeAPI();
                Recipe currentRecipe = new Recipe();
                List<IngredientAPI> ingredients = new ArrayList<>();
                currentRecipe = recipeRepository.findById(Long.valueOf(id));
                for (RecipeIngredient recipeIngredient : currentRecipe.getRecipeIngredients()) {
                    IngredientAPI ingredientAPI = new IngredientAPI();
                    ingredientAPI.setName(recipeIngredient.getIngredient().getName());
                    ingredientAPI.setAmount(recipeIngredient.getAmount());
                    ingredientAPI.setCarbs(recipeIngredient.getCarbs());
                    ingredientAPI.setFats(recipeIngredient.getFats());
                    ingredientAPI.setProteins(recipeIngredient.getProteins());
                    ingredients.add(ingredientAPI);
                }
                recipeAPI.setIngredients(ingredients);
                recipeAPI.setRecipe(currentRecipe);
                recipeAPI.setRating(currentRecipe.getRatings().stream().mapToDouble(d->d.getValue()).average().orElse(0.0));
                recipeAPI.setSearchedFor(description);
                recipes.add(recipeAPI);
            }
        }
        return recipes;
    }

    public Page<RecipeEs> getRecipeByTags(List<Tags> tags, JwtUser currentJwtUser) {
        User currentUser = userRepository.findByUsername(currentJwtUser.getUsername());
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        List<String> userTags = tags.stream()
                .map((tags1 -> tags1.getName()))
                .distinct().collect(Collectors.toList());

        for (String userTag : userTags) {
            System.out.println(userTag);
        }
        if (currentUser.getDiet() != null) {
            //Ingredients allowed to be used for the diet
            Collection<Ingredient> dietIngredients = currentUser.getDiet().getIngredients();
            Collection<Ingredient> allIngredients = ingredientRepository.findAll();
            //Getting the !allowed ingredients
            allIngredients.removeAll(dietIngredients);
            System.out.println("ASDSADSADSADASASD" + allIngredients.toString());
            List<String> unallowedIngredients = allIngredients.
                    stream()
                    .map((ingredient -> ingredient.getName()))
                    .distinct().collect(Collectors.toList());
            QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                    .must((nestedQuery("ingredient", termsQuery("ingredient.name", userTags))))
                    .mustNot((nestedQuery("ingredient", termsQuery("ingredient.name", unallowedIngredients))));

            nativeSearchQueryBuilder.withQuery(queryBuilder);
        }

        else {
            QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                    .must((nestedQuery("ingredient", termsQuery("ingredient.name", userTags))));

            nativeSearchQueryBuilder.withQuery(queryBuilder);
            nativeSearchQueryBuilder.withPageable(new PageRequest(0, 1000));
        }

        NativeSearchQuery nativeSearchQuery = nativeSearchQueryBuilder.build();
        System.out.println("nativeQuery" + nativeSearchQuery.getQuery());


        Page<RecipeEs> results = elasticsearchTemplate.queryForPage(nativeSearchQuery, RecipeEs.class);

        return results;
    }

    public void rateRecipe(Long recipeId, float value, JwtUser jwtUser) {
        Rating rating = new Rating();
        RatingEs ratingEs = new RatingEs();
        boolean existent = false;
        User user = userRepository.findById(jwtUser.getId());
        System.out.println(user.getId());
        System.out.println(recipeId);
        Recipe recipe = recipeRepository.findById(recipeId);
        //RecipeEs recipeEs = recipeEsRepository.findById(recipeId.toString());
        Set<Rating> recipeRating = new ObjectArraySet<>();
        Long userId = user.getId();

        rating.setUser(user);
        rating.setRecipe(recipe);
        rating.setValue(value);

        for (Iterator<Rating> iterator = recipe.getRatings().iterator(); iterator.hasNext();) {
            Rating rating1 =  iterator.next();
            System.out.println("Recipe: " + rating1.getRecipe().getId() + "WITH VALUE: " + rating1.getValue());
            if (rating1.getUser().getId() == userId) {
                iterator.remove();
                System.out.println("SHOULD BE REMOVED: " + rating1.getRecipe().getId() + "WITH VALUE: " + rating1.getValue());
                System.out.println("XZCZXCASC");
            } else {
                recipeRating.add(rating1);
            }
        }


        for (Rating dsadsad : recipeRating) {
            System.out.println("SHOULD NOT BE REMOVED: " + dsadsad.getRecipe().getId() + "WITH VALUE: " + dsadsad.getValue());
        }

        recipeRating.add(rating);

        for (Rating xczxcas : recipeRating) {
            System.out.println("SHOULD NOT BE REMOVED2: " + xczxcas.getRecipe().getId() + "WITH VAL2UE: " + xczxcas.getValue());
        }


        recipe.setRatings(recipeRating);
        user.setRatings(recipeRating);

        userRepository.save(user);
        recipeRepository.save(recipe);
        //recipeEsRepository.save(recipeEs);


    }



    public double getPearsonScore (User currentUser, User user2) {

        if (currentUser.getRatings() != null) {
            List<Recipe> currentUserRecipes = currentUser.getRatings().stream()
                    .map(rating -> rating.getRecipe())
                    .collect(Collectors.toList());

            List<Long> user2RecipesIds = user2.getRatings().stream()
                    .map(rating -> rating.getRecipe().getId())
                    .collect(Collectors.toList());

            List<Recipe> recipesRatedFromBothUsers = new ArrayList<>();

            for (Recipe user1RatedRecipe : currentUserRecipes) {
                if (user2RecipesIds.contains(user1RatedRecipe.getId())) { //couldnt manage to get duplicated recipes when user2Recipes was of type Recipe
                    recipesRatedFromBothUsers.add(user1RatedRecipe);
                }
            }

            int n = recipesRatedFromBothUsers.size();

            if (n == 0) return 0;

            List<Rating> user1Ratings = new ArrayList<>();
            List<Rating> user2Ratings = new ArrayList<>();

            for (Recipe r : recipesRatedFromBothUsers) {
                if (r.getRatings() != null) {
                    for (Rating rating : r.getRatings()) {
                        if (rating.getUser().getId() == currentUser.getId())
                            user1Ratings.add(rating);
                        else if (rating.getUser().getId() == user2.getId())
                            user2Ratings.add(rating);
                    }
                }
            }

            double[] ratingUser1 = user1Ratings.stream().mapToDouble(rating -> rating.getValue()).toArray();
            double sum1 = DoubleStream.of(ratingUser1).sum();

            double sum1pow = 0;
            for (int i = 0; i < ratingUser1.length; i++) {
                sum1pow += Math.pow(ratingUser1[i], 2);
            }

            double[] ratingUser2 = user2Ratings.stream().mapToDouble(rating -> rating.getValue()).toArray();
            double sum2 = DoubleStream.of(ratingUser2).sum();

            double sum2pow = 0;
            for (int i = 0; i < ratingUser2.length; i++) {
                sum2pow += Math.pow(ratingUser2[i], 2);
            }

            double sumProducts = 0;
            for (int i = 0; i < ratingUser1.length; i++) {
                sumProducts += ratingUser1[i] * ratingUser2[i];
            }

            int numberOfElements = recipesRatedFromBothUsers.size()*2;

            // Calculate Pearson score
            double num = (numberOfElements*sumProducts) - (sum1 * sum2);

            double den = Math.sqrt((numberOfElements*sum1pow - Math.pow(sum1, 2)) * (numberOfElements*sum2pow - Math.pow(sum2, 2)));

            if (den == 0) return 0;

            double result = num / den;
            return result;
        } else {
            return -2;
        }
    }

    public List<Long> getRecommendations(Long currentUserId) {
        List<User> allUsers = userRepository.findAll();
        User currentUser = userRepository.findById(currentUserId);
        User bestMatchingUser = getBestMatchingUser(currentUser, allUsers);
        if (bestMatchingUser.getId() == null) {
            System.out.println("We cant give you recommendations :( ");
            throw new ArithmeticException("We cannot find any recipes for you :(");
        }
        System.out.println("BEST MATCHING: " + bestMatchingUser);
        List<Long> currentUserRecipes = new ArrayList<>();
        currentUserRecipes.addAll(currentUser.getRatings().stream()
                .map(rating -> rating.getRecipe().getId())
                .collect(Collectors.toList()));

        List<Long> recipes = getRecommendedRecipes(bestMatchingUser);
        return recipes;

    }

    public List<Long> getRecommendedRecipes(User bestMatchingUser) {
        List<Long> similarRecipes = new ArrayList<>();
        List<Long> currentUserRecipes = new ArrayList<>();
        List<Long> tenSimilarRecipes = new ArrayList<>();

        similarRecipes.addAll(bestMatchingUser.getRatings()
                .stream()
                .map(rating -> rating.getRecipe().getId())
                .collect(Collectors.toList()));


        similarRecipes.removeAll(currentUserRecipes);

        for (Long similarRecipe : similarRecipes) {
            if (tenSimilarRecipes.size() < 10) {
                tenSimilarRecipes.add(similarRecipe);
            }
        }

        return tenSimilarRecipes;
    }

    public User getBestMatchingUser(User currentUser, List<User> allUsers) {
        double[] similarity = new double[allUsers.size()];

        for (int i=1; i < allUsers.size(); i++) {
            if (currentUser.getId() != allUsers.get(i).getId()) {
                similarity[i] = getPearsonScore(currentUser, allUsers.get(i));
            }
        }

        User bestMatchingUser = new User();
        double maxCoeficient = 0;
        for (int i=1; i < similarity.length; i++ ) {
            double coeficient = similarity[i];
            System.out.println("COEFICIENTS: " + coeficient);
            if (coeficient > maxCoeficient) {
                maxCoeficient = coeficient;
                bestMatchingUser = allUsers.get(i);
            }
        }
        System.out.println("BEST MATCHING USER: " + bestMatchingUser);
        return bestMatchingUser;
    }

    public List<RecipeAPI> getRecipesFromIdsUser(List<Long> ids, User currentUser) {
        List<RecipeAPI> recipes = new ArrayList<>();
        for (Long id : ids) {
            Recipe recipe = recipeRepository.findById(id);
            if (currentUser.getDiet() != null) {
                Collection<Ingredient> dietIngredients = currentUser.getDiet().getIngredients();
                Collection<Ingredient> allIngredients = ingredientRepository.findAll();
                Set<Ingredient> recipeIngredients = new HashSet<>();
                for (RecipeIngredient recipeIngredient : recipe.getRecipeIngredients()) {
                    recipeIngredients.add(recipeIngredient.getIngredient());
                }
                //Getting the !allowed ingredients
                allIngredients.removeAll(dietIngredients);


                if (allIngredients.contains(recipeIngredients) == false) {
                    RecipeAPI recipeAPI = new RecipeAPI();
                    recipeAPI.setRecipe(recipe);
                    recipeAPI.setRating(recipe.getRatings().stream().mapToDouble(d -> d.getValue()).average().orElse(0.0));
                    recipes.add(recipeAPI);
                }
            } else {
                RecipeAPI recipeAPI = new RecipeAPI();
                recipeAPI.setRecipe(recipe);
                recipeAPI.setRating(recipe.getRatings().stream().mapToDouble(d->d.getValue()).average().orElse(0.0));
                recipes.add(recipeAPI);
            }

        }
        return recipes;
    }
}
