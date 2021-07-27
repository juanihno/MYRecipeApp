package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.database.MyRecipeDBHelper;
import com.example.myapplication.entities.Recipe;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import com.example.myapplication.databinding.ActivityAddScrollingBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddScrollingActivity extends AppCompatActivity {

    //Button addRecipeButton;
    //SeekBar difficultySeekBar;
    //private Integer difficultyValue=0;
    //private Recipe recipe;


    private EditText recipeNameEdiText;
    private EditText recipeDescriptionEditText;
    private SeekBar difficultySeekBar;
    private MaterialButton cancelRecipeButton;
    private MaterialButton addRecipeButton;

    private Recipe recipe;
    private Integer difficultyValue = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_scrolling);

        recipeNameEdiText = findViewById(R.id.recipeNameEditText);
        recipeDescriptionEditText = findViewById(R.id.recipeDescriptionEditText);
        difficultySeekBar = findViewById(R.id.dificultySeekBar);
        cancelRecipeButton = findViewById(R.id.cancelAddRecipeButton);
        addRecipeButton = findViewById(R.id.addRecipeButton);


        //addRecipeButton=(MaterialButton)findViewById(R.id.addRecipeButton);
        //difficultySeekBar=(SeekBar)findViewById(R.id.dificultySeekBar);

        cancelRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel(v);
            }
        });


        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add(v);
            }
        });
        difficultySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                difficultyValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    private void add(View v) {

        String name=recipeNameEdiText.getText().toString();
        //TextInputEditText recipeDescriptionEditText=findViewById(R.id.recipeDescriptionEditText);
        //String description=recipeDescriptionEditText.getText().toString().trim();

        if (name.trim().isEmpty()){
            Snackbar.make(v,"name is required", Snackbar.LENGTH_SHORT).show();
            recipeNameEdiText.getText().clear();
            recipeNameEdiText.requestFocus();
            return;
        }
        String description=recipeDescriptionEditText.getText().toString().trim();
        Recipe recipe=new Recipe();
        recipe.setName(name);
        recipe.setDescription(description);
        recipe.setDifficulty(difficultyValue);

        //set the intent to return the monster to the caller activity

        Intent goingBack=new Intent();
        goingBack.putExtra(Recipe.RECIPE_KEY, recipe);
        setResult(RESULT_OK, goingBack);
        finish();
    }
    private void cancel(View v) {

        setResult(RESULT_CANCELED);
        finish();
    }

}