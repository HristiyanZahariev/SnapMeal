package com.snapmeal.entity.jpa;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.snapmeal.entity.enums.UserRole;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "User", uniqueConstraints = @UniqueConstraint(columnNames = { "username" }))
public class User implements UserDetails {

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public User(String username, Date expires) {
        this.username = username;
        this.expires = expires.getTime();
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Size(min = 4, max = 30)
    private String username;

    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    //@NotNull
    @Size(min = 4, max = 128)
    private String email;

    @NotNull
    private String diet;


    @Transient
    private long expires;

    //@NotNull
    private boolean accountExpired;

    //@NotNull
    private boolean accountLocked;

    //@NotNull
    private boolean credentialsExpired;

    //@NotNull
    private boolean accountEnabled;

    @Transient
    private String newPassword;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<UserAuthority> authorities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public String getNewPassword() {
        return newPassword;
    }

    @JsonProperty
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public void setAuthorities(Set<UserAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    @JsonIgnore
    public Set<UserAuthority> getAuthorities() {
        return authorities;
    }

    // Use Roles as external API
    public Set<UserRole> getRoles() {
        Set<UserRole> roles = EnumSet.noneOf(UserRole.class);
        if (authorities != null) {
            for (UserAuthority authority : authorities) {
                roles.add(UserRole.valueOf(authority));
            }
        }
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        for (UserRole role : roles) {
            grantRole(role);
        }
    }

    public void grantRole(UserRole role) {
        if (authorities == null) {
            authorities = new HashSet<UserAuthority>();
        }
        authorities.add(role.asAuthorityFor(this));
    }

    public void revokeRole(UserRole role) {
        if (authorities != null) {
            authorities.remove(role.asAuthorityFor(this));
        }
    }

    public boolean hasRole(UserRole role) {
        return authorities.contains(role.asAuthorityFor(this));
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return !accountExpired;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return !accountEnabled;
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + getUsername();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (expires != user.expires) return false;
        if (accountExpired != user.accountExpired) return false;
        if (accountLocked != user.accountLocked) return false;
        if (credentialsExpired != user.credentialsExpired) return false;
        if (accountEnabled != user.accountEnabled) return false;
        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (diet != null ? !diet.equals(user.diet) : user.diet != null) return false;
        if (newPassword != null ? !newPassword.equals(user.newPassword) : user.newPassword != null) return false;
        return authorities != null ? authorities.equals(user.authorities) : user.authorities == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (diet != null ? diet.hashCode() : 0);
        result = 31 * result + (int) (expires ^ (expires >>> 32));
        result = 31 * result + (accountExpired ? 1 : 0);
        result = 31 * result + (accountLocked ? 1 : 0);
        result = 31 * result + (credentialsExpired ? 1 : 0);
        result = 31 * result + (accountEnabled ? 1 : 0);
        result = 31 * result + (newPassword != null ? newPassword.hashCode() : 0);
        result = 31 * result + (authorities != null ? authorities.hashCode() : 0);
        return result;
    }
}
