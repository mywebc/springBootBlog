package hello.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;

public class User {
    Integer id;
    String username;
    // @JsonIgnore注解表示在序列化时忽略encryptePassword字段
    @JsonIgnore
    String encryptePassword;
    String avatar;
    Instant updatedAt;
    Instant createdAt;

    public User(Integer id, String username, String encryptePassword) {
        this.id = id;
        this.username = username;
        this.encryptePassword = encryptePassword;
        this.avatar = "";
        this.updatedAt = Instant.now();
        this.createdAt = Instant.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEncryptePassword() {
        return encryptePassword;
    }

    public void setEncryptePassword(String encryptePassword) {
        this.encryptePassword = encryptePassword;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
