package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.entities.Recipe;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.myapplication.databinding.ActivityRatingScrollingBinding;

public class RatingScrollingActivity extends AppCompatActivity {

    private ActivityRatingScrollingBinding binding;
    RatingBar ratingBar;
    Recipe recipe;
    Integer rate = 0;
    //private View rootView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //enable arrow to come back and send the data to the previous activity(recyclerview)

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIntentWithData();

            }
        });

        //rootView = findViewById(android.R.id.content).getRootView();

        ImageView recipeImageView = findViewById(R.id.recipeImageViewRatingActivity);
        TextView recipeNameTextView = findViewById(R.id.recipeNameTextViewRatingActivity);
        TextView recipeDescriptionTextView = findViewById(R.id.recipeDescriptionTextViewRatingActivity);

        ratingBar = findViewById(R.id.recipeRatingBarRatingActivity);

        Intent intentThatCalled = getIntent();
        if(intentThatCalled.hasExtra(Recipe.RECIPE_KEY)){
            recipe = (Recipe) intentThatCalled.getSerializableExtra(Recipe.RECIPE_KEY);
        }

        recipeNameTextView.setText(recipe.getName());
        recipeDescriptionTextView.setText(recipe.getDescription());

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rate = (int) ratingBar.getRating();
            }
        });
        View rootView = recipeImageView.getRootView();
        int resID = rootView.getResources().getIdentifier(recipe.imageFilename , "drawable" , rootView.getContext().getPackageName()) ;
        recipeImageView.setImageResource(resID);

    }

    @Override
    public void onBackPressed() {
        //before you go back do something
        setIntentWithData();
        super.onBackPressed();
    }

    private void setIntentWithData() {

        Intent goingBack = new Intent();
        goingBack.putExtra(Recipe.RECIPE_KEY, recipe);
        goingBack.putExtra(Recipe.RECIPE_STARS,rate );
        goingBack.putExtra(Recipe.RECIPE_ID, recipe.getId());

        setResult(RESULT_OK, goingBack);
        finish();
    }
}
