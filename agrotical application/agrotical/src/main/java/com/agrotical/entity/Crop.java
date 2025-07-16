package com.agrotical.entity;

import jakarta.persistence.*;

@Entity
public class Crop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String name;

    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;

    public Crop() {
    }

    public Crop(String type, Field field) {
        this.type = type;
        this.field = field;
    }

    
    private Crop(Builder builder) {
        this.type = builder.type;
        this.name = builder.name;
        this.field = builder.field;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Field getField() { return field; }
    public void setField(Field field) { this.field = field; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    
    public static class Builder {
        private String type;
        private String name;
        private Field field;

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder field(Field field) {
            this.field = field;
            return this;
        }

        public Crop build() {
            return new Crop(this);
        }
    }
}
