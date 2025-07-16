package com.agrotical.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double areaInStremma;

    @ManyToOne
    @JsonIgnoreProperties("fields")
    @JoinColumn(name = "user_id")
    private User user;

    public Field() {}

    public Field(String name, Double area, User user) {
        this.name = name;
        this.areaInStremma = area;
        this.user = user;
    }

    private Field(Builder builder) {
        this.name = builder.name;
        this.areaInStremma = builder.areaInStremma;
        this.user = builder.user;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getAreaInStremma() { return areaInStremma; }
    public void setAreaInStremma(double areaInStremma) { this.areaInStremma = areaInStremma; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    
    public static class Builder {
        private String name;
        private double areaInStremma;
        private User user;

        public Builder name(String name) { this.name = name; return this; }
        public Builder areaInStremma(double area) { this.areaInStremma = area; return this; }
        public Builder user(User user) { this.user = user; return this; }

        public Field build() { return new Field(this); }
    }
}
