package com.example.myapplication.entities;

import java.io.Serializable;

public class Recipe implements Serializable {
    public static final String RECIPE_KEY="recipe_key";
    public static final String RECIPE_STARS="recipe_stars";
    public static final String RECIPE_ID="recipe_id";
    public static final String RECIPE_NAME="recipe_name";
    public static final String RECIPE_DESCRIPTION="recipe_description";
    public static final String RECIPE_DIFFICULTY="recipe_difficulty";



    private Long id;
    private String name;
    private String description;
    private Integer difficulty;
    public String imageFilename;
    private Integer votes;
    private Integer stars;
    private Long userId;

    public Recipe() {
    }

    /*public Recipe(Long id, String name, String description, Integer difficulty, String image, Long votes, Long stars) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.image = image;
        this.votes = votes;
        this.stars = stars;
        this.userId= userId;
    }*/

    public Recipe(Long id, String name, String description, Integer difficulty, String imageFileName, Integer votes, Integer stars ,Long userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.imageFilename = imageFileName;
        this.votes = votes;
        this.stars = stars;
        this.userId= userId;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", difficulty=" + difficulty +
                ", image='" + imageFilename + '\'' +
                ", votes=" + votes +
                ", stars=" + stars +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public String getImage() {
        return imageFilename;
    }

    public void setImage(String image) {
        this.imageFilename = image;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }
}
