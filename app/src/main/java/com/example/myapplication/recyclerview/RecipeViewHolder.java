package com.example.myapplication.recyclerview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entities.Recipe;

import java.io.ByteArrayInputStream;

public class RecipeViewHolder extends RecyclerView.ViewHolder {
    //bind the recycler_item_view.xml elements
    public final ImageView recipeImageView;
    public final TextView recipeNameEditText;
    public final TextView recipeDescriptionEditText;
    public final TextView recipeTotalVotesTextView;

    private OnRecipeListener onRecipeListener;
    public RatingBar ratingBar;
    public Button actionButton1;


    /*public RecipeViewHolder(@NonNull View itemView, OnMonsterListener onMonsterListener) {
        super(itemView);*/
    public RecipeViewHolder(@NonNull View itemView, OnRecipeListener onRecipeListener) {
        super(itemView);

        this.recipeImageView = itemView.findViewById(R.id.recipeImageView);
        this.recipeNameEditText = itemView.findViewById(R.id.recipeNameEditTextRecycler);
        this.recipeDescriptionEditText = itemView.findViewById(R.id.recipeDescriptionEditTextRecycler);
        this.recipeTotalVotesTextView = itemView.findViewById(R.id.totalVotesTextView);
        this.onRecipeListener=onRecipeListener;
        this.ratingBar = itemView.findViewById(R.id.recipeRatingBar);
        this.actionButton1 = itemView.findViewById(R.id.actionButton1);

        //this.onMonsterListener = onMonsterListener;
    }


    public void updateRecipe(Recipe recipe) {

//        https://medium.com/androiddevelopers/understanding-androids-vector-image-format-vectordrawable-ab09e41d5c68

        //View rootView = recipeImageView.getRootView();
        //int resID = rootView.getResources().getIdentifier(recipe.getImage(), "drawable", rootView.getContext().getPackageName());
        //recipeImageView.setImageResource(resID);
        byte[] bitmapbytes=recipe.getImage();
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inDensity = 100;
        opt.inTargetDensity = 100;
        Bitmap bitmap= (BitmapFactory.decodeStream(new ByteArrayInputStream(bitmapbytes), null, opt));
        //Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapbytes , 0, bitmapbytes .length);
        this.recipeImageView.setImageBitmap(bitmap);



        this.recipeNameEditText.setText(recipe.getName());
        this.recipeDescriptionEditText.setText(recipe.getDescription());
        this.recipeTotalVotesTextView.setText(recipe.getVotes() + " Votes");
        float rate;
        if(recipe.getVotes() > 0){
            rate = 1.0f * recipe.getStars() / recipe.getVotes();
        }else{
            rate = 0.0f;
        }
        this.ratingBar.setRating(rate);
    }




      //Bind every recipe with a listener, to be used when the user clicks the ratingbar of each recipe in the recycler view
    public void bind(final Recipe recipe, final OnRecipeListener onRecipeListener) {
        this.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecipeListener.onRecipeClick(recipe);
            }
        });

        this.actionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecipeListener.onRecipeClick(recipe);


            }
        });

    }

    }