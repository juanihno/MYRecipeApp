package com.example.myapplication.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entities.Recipe;

import java.util.List;

public class RecipeRecyclerviewAdapter extends RecyclerView .Adapter<RecipeViewHolder>{

   private List<Recipe> recipes;
   private Context context;
   private OnRecipeListener onRecipeListener;

    public RecipeRecyclerviewAdapter(List<Recipe> recipes, Context context, OnRecipeListener onRecipeListener) {
        this.recipes = recipes;
        this.context = context;
        this.onRecipeListener=onRecipeListener;
    }
    public List<Recipe> getRecipes() {
        return recipes;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View recipeView=inflater.inflate(R.layout.recycler_item_view,parent,false);
        //RecipeViewHolder recipeViewHolder= new RecipeViewHolder(recipeView, onMonsterListener);

        RecipeViewHolder recipeViewHolder= new RecipeViewHolder(recipeView, onRecipeListener);
        return recipeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull  RecipeViewHolder holder, int position) {
        //get data from recipes list based on postion
Recipe recipe=recipes.get(position);
//call method to set values in the recipeviewholder
holder.updateRecipe(recipe);
holder.bind(recipe, onRecipeListener);

    }


    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void addItem(Recipe recipe) {
        recipes.add(recipe);
        notifyItemInserted(getItemCount());
    }


    public void replaceItem(int position, Recipe recipe) {
        recipes.set(position, recipe);
        notifyItemChanged(position);

    }
}
