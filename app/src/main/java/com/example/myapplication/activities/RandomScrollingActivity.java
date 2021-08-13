package com.example.myapplication.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.entities.Recipe;
import com.example.myapplication.services.DataService;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Random;


public class RandomScrollingActivity extends AppCompatActivity {

    private DataService recipeDataService;
    RatingBar ratingBar;
    Long randomId;
    int size;
    ImageView recipeImageView;

    private List<Recipe> recipes;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_scrolling);
        Long userId = getIntent().getLongExtra("ID",0);

        recipeDataService= new DataService();
        recipeDataService.init(this);
        recipes=recipeDataService.getRecipes();
        size= recipes.size();

       /* Random ran = new Random();
        int value = ran.nextInt(size) + 1;
        Recipe recipe=recipeDataService.getRecipe((long) value);*/
        Recipe recipe=recipeDataService.getRandomRecipe();
        recipeImageView = findViewById(R.id.recipeImageViewRandomActivity);
        TextView recipeNameTextView = findViewById(R.id.recipeNameTextViewRandomActivity);
        TextView recipeDescriptionTextView = findViewById(R.id.recipeDescriptionTextViewRandomActivity);

        ratingBar = findViewById(R.id.recipeRatingBarRandomActivity);

        recipeNameTextView.setText(recipe.getName());
        recipeDescriptionTextView.setText(recipe.getDescription());


        ratingBar.setProgress(recipe.getDifficulty());
        byte[] bitmapbytes=recipe.getImage();
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inDensity = 50;
        opt.inTargetDensity = 50;
        Bitmap bitmap= (BitmapFactory.decodeStream(new ByteArrayInputStream(bitmapbytes), null, opt));
        //Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapbytes , 0, bitmapbytes .length);
        this.recipeImageView.setImageBitmap(bitmap);

        //View rootView = recipeImageView.getRootView();
        //int resID = rootView.getResources().getIdentifier(recipe.imageFilename , "drawable" , rootView.getContext().getPackageName()) ;
        //recipeImageView.setImageResource(resID);





    }
}