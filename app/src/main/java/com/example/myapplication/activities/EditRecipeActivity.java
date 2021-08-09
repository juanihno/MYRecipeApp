package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.entities.Recipe;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

public class EditRecipeActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_edit_recipe);

        Long userId = getIntent().getLongExtra("ID",0);
        ImageView recipeImageView = findViewById(R.id.recipeImageViewRatingActivity);


        Intent intentThatCalled = getIntent();
        if(intentThatCalled.hasExtra(Recipe.RECIPE_KEY)){
            recipe = (Recipe) intentThatCalled.getSerializableExtra(Recipe.RECIPE_KEY);
        }
        recipeNameEdiText = findViewById(R.id.recipeEditNameEditTextEditActivity);
        recipeDescriptionEditText = findViewById(R.id.recipeEditDescriptionEditTextEditActivity);
        difficultySeekBar = findViewById(R.id.editDificultySeekBarEditActivity);
        cancelRecipeButton = findViewById(R.id.cancelEditRecipeButtonEditActivity);
        addRecipeButton = findViewById(R.id.addEditRecipeButtonEditActivity);


        recipeNameEdiText.setText(recipe.getName());
        recipeDescriptionEditText.setText(recipe.getDescription());



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
        Long id=recipe.getId();


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
        Long userId = getIntent().getLongExtra("ID",0);

        Recipe recipe=new Recipe();
        recipe.setName(name);
        recipe.setDescription(description);
        recipe.setDifficulty(difficultyValue);
        recipe.setId(id);
        recipe.setUserId(userId);

        //set the intent to return the monster to the caller activity

        Intent goingBack=new Intent();
        goingBack.putExtra(Recipe.RECIPE_KEY, recipe);
        goingBack.putExtra(Recipe.RECIPE_ID, recipe.getId());
        goingBack.putExtra(Recipe.RECIPE_NAME, recipe.getName());
        goingBack.putExtra(Recipe.RECIPE_DESCRIPTION, recipe.getDescription());
        goingBack.putExtra(Recipe.RECIPE_DIFFICULTY, recipe.getDifficulty());


        setResult(RESULT_OK, goingBack);
        finish();
    }
    private void cancel(View v) {

        setResult(RESULT_CANCELED);
        finish();
    }

}