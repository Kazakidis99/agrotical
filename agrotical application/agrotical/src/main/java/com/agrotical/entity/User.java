package com.agrotical.entity;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Field> fields;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private User(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.fields = builder.fields;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<Field> getFields() { return fields; }
    public void setFields(List<Field> fields) { this.fields = fields; }

    
    public static class Builder {
        private String username;
        private String password;
        private List<Field> fields;

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder fields(List<Field> fields) {
            this.fields = fields;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
} 
