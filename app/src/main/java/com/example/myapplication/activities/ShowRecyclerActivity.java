package com.example.myapplication.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.database.MyRecipeDBHelper;
import com.example.myapplication.entities.Recipe;
import com.example.myapplication.recyclerview.OnRecipeListener;
import com.example.myapplication.recyclerview.RecipeRecyclerviewAdapter;
import com.example.myapplication.services.DataService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.entities.Constants.ADD_RECIPE_ACTIVITY_CODE;
import static com.example.myapplication.entities.Constants.VIEW_DETAILS_ACTIVITY_CODE;

public class ShowRecyclerActivity extends AppCompatActivity implements OnRecipeListener {
    private DataService recipeDataService;
    private List<Recipe> recipes;
    private RecipeRecyclerviewAdapter adapter;
    private View rootView;
    MyRecipeDBHelper db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recycler);
        Long userId = getIntent().getLongExtra("ID",0);



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        rootView = findViewById(android.R.id.content).getRootView();
        RecyclerView recipeRecyclerView=findViewById(R.id.recipesRecyclerView);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);



        recipeRecyclerView.setLayoutManager(linearLayoutManager);
        //loading data from the database
        recipeDataService=new DataService();
        recipeDataService.init(this);
        //call db to get all recipes*/

        recipes = recipeDataService.getRecipes();
        // create an instance of RecyclerviewAdapter and pass the data
        adapter = new RecipeRecyclerviewAdapter(recipes, this,this);
        // set adapter to the recyclerview
        recipeRecyclerView.setAdapter(adapter);
    }
//method that will send user to recycler view when clicked with all the recipe data to be displayed in the rating activity
    @Override
    public void onRecipeClick(Recipe recipe) {
        showRecipeDetail(recipe);
    }

    private void showRecipeDetail(Recipe recipe) {
        Intent goToRecipeDetail = new Intent(ShowRecyclerActivity.this, RatingScrollingActivity.class);
        goToRecipeDetail.putExtra(Recipe.RECIPE_KEY, recipe);

        startActivityForResult(goToRecipeDetail, VIEW_DETAILS_ACTIVITY_CODE);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == VIEW_DETAILS_ACTIVITY_CODE){
            if(resultCode == RESULT_OK){
                rateRecipe(data);


            }
        }

    }

    private void rateRecipe(Intent data) {
        int stars;
        Long id;
        if (data.hasExtra(Recipe.RECIPE_KEY) && data.hasExtra(Recipe.RECIPE_STARS))
{
            Recipe recipe = (Recipe) data.getSerializableExtra(Recipe.RECIPE_KEY);
            stars = data.getExtras().getInt(Recipe.RECIPE_STARS);
            id = data.getExtras().getLong(Recipe.RECIPE_ID);
            if(stars > 0){
                boolean result = recipeDataService.rateRecipe(id, stars);
                //find the monster in the list
                int position = adapter.getRecipes().indexOf(recipe);
                if(position >= 0){
                    //recipe was found
                    recipe = recipeDataService.getRecipe(id);
                    adapter.replaceItem(position, recipe);


                }
            }
        }


    }


}

