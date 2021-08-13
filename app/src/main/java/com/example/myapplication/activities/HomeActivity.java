package com.example.myapplication.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.database.MyRecipeDBHelper;
import com.example.myapplication.entities.Constants;
import com.example.myapplication.entities.Recipe;
import com.example.myapplication.recyclerview.RecipeRecyclerviewAdapter;
import com.example.myapplication.services.DataService;
import com.example.myapplication.activities.AddScrollingActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import static com.example.myapplication.entities.Constants.ADD_RECIPE_ACTIVITY_CODE;

public class HomeActivity extends AppCompatActivity {
    private List<Recipe>recipes;
    private DataService recipeDataService;
    private View rootView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_scrolling);
        //Intent intent = getIntent();
        //Long userId=intent.getLongExtra("USER_ID", 0);
        //String str=Long.toString(userId);
        Long userId = getIntent().getLongExtra("ID",0);









//load data from database
        recipeDataService= new DataService();
        recipeDataService.init(this);
        rootView=findViewById(R.id.imageViewCreateNew).getRootView();


    }
//private void addNewRecipe(){
    //Intent goToAddCreateRecipe=new Intent(this, AddScrollingActivity.class);
        //startActivityForResult(goToAddCreateRecipe,ADD_RECIPE_ACTIVITY_CODE);}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_RECIPE_ACTIVITY_CODE){
            if(resultCode == RESULT_OK){
                addRecipe(data);

            }
        }

    }

    private void addRecipe(Intent data) {
        String message;
        Recipe recipe= (Recipe) data.getSerializableExtra(Recipe.RECIPE_KEY);
        Long result =recipeDataService.add(recipe);
        if (result>0){
            message = "Your Recipe has been created with id: "+ result;
        }else{
            message = "We couldn't create your Recipe, try again";
        }
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show();
    }

    public void createImageView(View view) {
        ImageView createImageView=findViewById(R.id.imageViewCreateNew);
        //Button createNewRecipeButton=findViewById(R.id.createNewRecipeButton);
        createImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Long userId = getIntent().getLongExtra("ID",0);


                Intent  goToAddCreateRecipe = new Intent(HomeActivity.this, AddScrollingActivity.class);
                goToAddCreateRecipe.putExtra("ID", userId);

                startActivityForResult(goToAddCreateRecipe,ADD_RECIPE_ACTIVITY_CODE);
                Toast.makeText(HomeActivity.this,"UserId is"+userId,Toast.LENGTH_LONG).show();

            }
        });
    }

    public void discoverImageView(View view) {
        Long userId = getIntent().getLongExtra("ID",0);

        ImageView discoverNewImageView=findViewById(R.id.imageViewDiscover);

        //Button discoverNewButton=findViewById(R.id.discoverNewButton);
        discoverNewImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  goToListRecipes = new Intent(HomeActivity.this, ShowRecyclerActivity.class);
                goToListRecipes.putExtra("ID", userId);
                startActivity(goToListRecipes);


            }
        });
    }

    public void surpriseMeImageView(View view) {
        Long userId = getIntent().getLongExtra("ID",0);

        ImageView surpriseMeNewImageView=findViewById(R.id.imageViewSurpriseMe);

        //Button surpriseMe=findViewById(R.id.surpriseMeButton);
        surpriseMeNewImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  goToSurpriseMe = new Intent(HomeActivity.this, RandomScrollingActivity.class);
                goToSurpriseMe.putExtra("ID", userId);
                startActivity(goToSurpriseMe);
                //Toast.makeText(HomeActivity.this,"UserId is"+userId,Toast.LENGTH_LONG).show();



            }
        });
    }

    public void myRecipesImageView(View view) {
        Long userId = getIntent().getLongExtra("ID",0);

        ImageView myRecipesImagesView=findViewById(R.id.myRecipesimageView);

        //Button myOwnRecipesButton=findViewById(R.id.myOwnRecipesButton);
        myRecipesImagesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  goToMyOwnRecipes = new Intent(HomeActivity.this, ShowUserOwnRecipesActivity.class);
                goToMyOwnRecipes.putExtra("ID", userId);
                startActivity(goToMyOwnRecipes);
                //Toast.makeText(HomeActivity.this,"UserId is"+userId,Toast.LENGTH_LONG).show();



            }
        });

    }
}


