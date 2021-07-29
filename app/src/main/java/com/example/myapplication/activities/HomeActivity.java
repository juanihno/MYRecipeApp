package com.example.myapplication.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.database.MyRecipeDBHelper;
import com.example.myapplication.entities.Constants;
import com.example.myapplication.entities.Recipe;
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
        setContentView(R.layout.activity_home);
        //Intent intent = getIntent();
        //Long userId=intent.getLongExtra("USER_ID", 0);
        //String str=Long.toString(userId);
        Long userId = getIntent().getLongExtra("ID",0);


        Button createNewRecipeButton=findViewById(R.id.createNewRecipeButton);
        createNewRecipeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent  goToAddCreateRecipe = new Intent(HomeActivity.this, AddScrollingActivity.class);
                goToAddCreateRecipe.putExtra("ID", userId);

                startActivityForResult(goToAddCreateRecipe,ADD_RECIPE_ACTIVITY_CODE);
                Toast.makeText(HomeActivity.this,"UserId is"+userId,Toast.LENGTH_LONG).show();

            }
        });
        recipeDataService= new DataService();
        recipeDataService.init(this);
        rootView=findViewById(R.id.createNewRecipeButton).getRootView();
        //recipes =recipeDataService.getRecipes();
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
    }


