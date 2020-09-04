package hello.entity;

import java.time.Instant;

public class User {
    Integer id;
    String username;
    String avatar;
    Instant updatedAt;
    Instant createdAt;

    public User(Integer id,String name) {
        this.id = id;
        this.username = name;
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

    public String getName() {
        return username;
    }

    public void setName(String name) {
        this.username = name;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatar() {
        return avatar;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
