package com.snapmeal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.mysql.cj.mysqlx.protobuf.MysqlxCrud;
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
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

    private int limit = 2;
    ObjectMapper mapper = new ObjectMapper();

    Gson gson = new Gson();

    public Iterable<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public RecipeEs findRecipeById(String id) {
        return recipeEsRepository.findById(id);
    }

    public CompletableFuture<Recipe> createRecipe(RecipeEs recipeEs) throws IOException {
        recipeEsRepository.save(recipeEs);
        String recipeJson = mapper.writeValueAsString(recipeEs);
        Recipe recipe = mapper.readValue(recipeJson, Recipe.class);
        return recipeRepository.save(recipe);

    }

    public Page<RecipeEs> getRecipesByDescription(String description, JwtUser currentJwtUser) throws JsonProcessingException {
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
            //nativeSearchQueryBuilder.withPageable(new PageRequest(limit * (page - 1), limit));
            //nativeSearchQueryBuilder.withMinScore(minScore);
        }
        else {
            QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                    .must(matchQuery("description", description));

            nativeSearchQueryBuilder.withQuery(queryBuilder);
            //nativeSearchQueryBuilder.withPageable(new PageRequest(limit * (page - 1), limit));
            //nativeSearchQueryBuilder.withMinScore(minScore);
        }

        NativeSearchQuery nativeSearchQuery = nativeSearchQueryBuilder.build();

        System.out.println("nativeQuery" + nativeSearchQuery.getQuery());

        Page<RecipeEs> results = elasticsearchTemplate.queryForPage(nativeSearchQuery, RecipeEs.class);

        return results;
    }

    public Page<RecipeEs> getRecipeByTags(List<Tags> tags, JwtUser currentJwtUser) {
        User currentUser = userRepository.findByUsername(currentJwtUser.getUsername());
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        List<String> userTags = tags.stream()
                .map((tags1 -> tags1.getName()))
                .distinct().collect(Collectors.toList());
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
                    .must((nestedQuery("ingredient", termsQuery("ingredient.name", userTags))))
                    .mustNot((nestedQuery("ingredient", termsQuery("ingredient.name", ingredientNames))));

            nativeSearchQueryBuilder.withQuery(queryBuilder);
        }

        else {
            QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                    .must((nestedQuery("ingredient", termsQuery("ingredient.name", userTags))));

            nativeSearchQueryBuilder.withQuery(queryBuilder);
        }

        NativeSearchQuery nativeSearchQuery = nativeSearchQueryBuilder.build();

        System.out.println("nativeQuery" + nativeSearchQuery.getQuery());

        Page<RecipeEs> results = elasticsearchTemplate.queryForPage(nativeSearchQuery, RecipeEs.class);

        return results;
    }

    public void rateRecipe(Long recipeId, float value, JwtUser jwtUser) {
        Rating rating = new Rating();
        RatingEs ratingEs = new RatingEs();
        User user = userRepository.findByUsername(jwtUser.getUsername());
        Recipe recipe = recipeRepository.findById(recipeId);
        RecipeEs recipeEs = recipeEsRepository.findById(recipeId.toString());
        Set<Rating> recipeRating = new ObjectArraySet<>();
        Long userId = user.getId();

        rating.setRecipe(recipe);
        rating.setUser(user);
        rating.setValue(value);

        ratingEs.setValue(value);
        ratingEs.setUserId(userId);

        recipeRating.removeIf(r -> r.getUser().equals(userId) && r.getRecipe() == recipe);
        recipeRating.add(rating);

        ratingsEs.removeIf(r -> r.getUserId().equals(userId));
        ratingsEs.add(ratingEs);
        System.out.println(rating);


        user.setRatings(recipeRating);
        recipe.setRatings(recipeRating);
        recipeEs.setRatings(ratingsEs);

        recipeRepository.save(recipe);
        userRepository.save(user);
        recipeEsRepository.save(recipeEs);
    }

    public double getPearsonScore (User user1, User user2) {

        List<Recipe> user1Recipes =  user1.getRatings().stream()
                .map(rating -> rating.getRecipe())
                .collect(Collectors.toList());

        List<Recipe> user2Recipes = user2.getRatings().stream()
                .map(rating -> rating.getRecipe())
                .collect(Collectors.toList());

//        if (user1Recipes.size() <= user2Recipes.size()) {
//        }
//        else {
//            for (Recipe recipe : user1Recipes) {
//                if (user2Recipes.contains(recipe)) {
//                    recipesRatedFromBothUsers.add(recipe);
//                }
//            }
//        }

//        recipesRatedFromBothUsers.addAll(user2Recipes.stream().filter(user1Recipes::contains).collect(Collectors.toList()));
//        recipesRatedFromBothUsers.addAll(user1Recipes.stream().filter(user2Recipes::contains).collect(Collectors.toList()));

        List<Recipe> recipesRatedFromBothUsers = new ArrayList<Recipe>(user1Recipes);
        recipesRatedFromBothUsers.addAll(user2Recipes);

        Set<Recipe> listaunionlistb =new HashSet<Recipe>(user1Recipes);
        listaunionlistb.addAll(user2Recipes);

        for(Recipe s:listaunionlistb)
        {
            recipesRatedFromBothUsers.remove(s);
        }
        System.out.println(recipesRatedFromBothUsers);

        recipesRatedFromBothUsers.forEach(recipe -> System.out.println("NAMESSS :" + recipe.getName()));

        int n = recipesRatedFromBothUsers.size();

        if (n == 0) {
            return 0;
        }

        List<Rating> user1Ratings = new ArrayList<>();
        List<Rating> user2Ratings = new ArrayList<>();

        for (Recipe r : recipesRatedFromBothUsers) {
            if (r.getRatings() != null) {
                for (Rating rating : r.getRatings()) {
                    if (rating.getUser().getId() == user1.getId()) {
                        user1Ratings.add(rating);
                    }
                    else if (rating.getUser().getId() == user2.getId()) {
                        user2Ratings.add(rating);
                    }
                }
            }
        }

        user1Ratings.stream().forEach(rating -> System.out.println("USER 1 RATINGS: " + rating.getValue()));

        double[] ratingUser1 = user1Ratings.stream().mapToDouble(rating -> rating.getValue()).toArray();
        double sum1 = DoubleStream.of(ratingUser1).sum();
        System.out.println("SUM USER 1: " + sum1);
        double sum1pow = 0;
        for (int i=0; i<ratingUser1.length; i++) {
            sum1pow += Math.pow(ratingUser1[i], 2);
        }
        System.out.println("SUMPOW USER 1: " + sum1pow);

        user2Ratings.stream().forEach(rating -> System.out.println("USER 2 RATINGS: " + rating.getValue()));

        double[] ratingUser2 = user2Ratings.stream().mapToDouble(rating -> rating.getValue()).toArray();
        double sum2 = DoubleStream.of(ratingUser2).sum();
        System.out.println("SUM USER 2: " + sum2);
        double sum2pow = 0;
        for (int i=0; i < ratingUser2.length; i++) {
            sum2pow += Math.pow(ratingUser2[i], 2);
        }
        System.out.println("SUMPOW USER 2: " + sum2pow);

        double sumProducts = 0;
        for (int i = 0; i < ratingUser1.length; i++) {
            sumProducts += ratingUser1[i] * ratingUser2[i];
        }
        System.out.println("SUM PRODUCTS: " + sumProducts);

        // Calculate Pearson score
        double num = sumProducts-(sum1*sum2/n);
        double den = Math.sqrt((sum1pow-Math.pow(sum1, 2)/n)*(sum2pow-Math.pow(sum2, 2)/n));
        if (den == 0) return 0;

        double result = num/den;
        return result;
    }

//    public double getRecommendations(long userId) {
//        List<User> allUsers = userRepository.findAll();
//        User user = userRepository.findById(userId);
//        double similarity = 0;
//        Arrays toatals = new A();
//        System.out.println(allUsers.size());
//        for (int i=0; i < allUsers.size(); i++) {
//            if (userId != allUsers.get(i).getId()) {
//                similarity =(getPearsonScore(user, allUsers.get(i)));
//                if (similarity < 0) {
//                    return 0;
//                }
//
//                Set<Rating> userRatings = user.getRatings();
//                for (Rating rating : userRatings) {
//                    toatals.add(rating.getValue()*similarity);
//                }
//
//            }
//        }
//    }


}
