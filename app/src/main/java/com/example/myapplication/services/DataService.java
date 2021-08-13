package com.example.myapplication.services;
import android.content.Context;

import com.example.myapplication.database.MyRecipeDBHelper;
import com.example.myapplication.entities.Recipe;

import java.util.List;


public class


DataService {
    private MyRecipeDBHelper sqlite;

    public void connect(){

    }

    public void disconnect(){

    }

    public void init(Context context){
        sqlite = sqlite.getInstance(context);
    }

    public Long add(Recipe recipe){
        return sqlite.insertRecipe(recipe.getName(), recipe.getDescription(), recipe.getDifficulty(),recipe.getImage(),recipe.getUserId());
    }


    public boolean delete(Recipe recipe){
        return sqlite.deleteRecipe(recipe.getId());
    }

    public boolean update(Recipe recipe){
        return sqlite.updateRecipe(recipe.getId(), recipe.getName(), recipe.getDescription(), recipe.getDifficulty(), recipe.getImage());
    }
    public Recipe getRandomRecipe(){
        return sqlite.getRandomRecipe();
    }
    public List<Recipe> getRecipes(){

        List<Recipe> recipes = sqlite.getRecipes();
        return recipes;
    }
    public List<Recipe> getRecipesByUserId(Long userId){

        List<Recipe> recipes = sqlite.getRecipesByUserId(userId);
        return recipes;
    }


    public Recipe getRecipe(Long id){
        return sqlite.getRecipe(id);
    }


    public boolean rateRecipe(Long id, Integer stars){
        return sqlite.rateRecipe(id, stars);
    }
}


