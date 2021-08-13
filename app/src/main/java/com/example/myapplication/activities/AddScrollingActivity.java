package com.example.myapplication.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.database.MyRecipeDBHelper;
import com.example.myapplication.entities.Recipe;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class AddScrollingActivity extends AppCompatActivity {

    //Button addRecipeButton;
    //SeekBar difficultySeekBar;
    //private Integer difficultyValue=0;
    //private Recipe recipe;
    Integer REQUEST_CAMERA=1, SELECT_FILE=0;


    private EditText recipeNameEdiText;
    private EditText recipeDescriptionEditText;
    private SeekBar difficultySeekBar;
    private MaterialButton cancelRecipeButton;
    private MaterialButton addRecipeButton;
    private Button cameraButton;
    private ImageView viewImage;




    private Recipe recipe;
    private Integer difficultyValue = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_scrolling);
        Long userId = getIntent().getLongExtra("ID",0);


        recipeNameEdiText = findViewById(R.id.recipeEditNameEditText);
        recipeDescriptionEditText = findViewById(R.id.recipeEditDescription);
        difficultySeekBar = findViewById(R.id.editDificultySeekBar);
        cancelRecipeButton = findViewById(R.id.cancelEditRecipeButton);
        addRecipeButton = findViewById(R.id.addEditRecipeButton);
        cameraButton = findViewById(R.id.cameraEditRecipeButton);
        viewImage=findViewById(R.id.imageViewEditRecipe);



        //addRecipeButton=(MaterialButton)findViewById(R.id.addRecipeButton);
        //difficultySeekBar=(SeekBar)findViewById(R.id.dificultySeekBar);

        viewImage=(ImageView)findViewById(R.id.imageViewEditRecipe);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
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



    private void selectImage(){

        final CharSequence[] items={"Camera","Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(AddScrollingActivity.this);
        builder.setTitle("Add Image");

        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Camera")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);

                } else if (items[i].equals("Gallery")) {

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    //startActivityForResult(intent.createChooser(intent, "Select File"), SELECT_FILE);
                    startActivityForResult(intent, SELECT_FILE);

                } else if (items[i].equals("Cancel")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();

    }
    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);

        if(resultCode== Activity.RESULT_OK){

            if(requestCode==REQUEST_CAMERA){

                MyRecipeDBHelper db=new MyRecipeDBHelper(this);

                Bundle bundle = data.getExtras();
                final Bitmap photo = (Bitmap) bundle.get("data");
                viewImage.setImageBitmap(photo);
                /*ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                recipe.setImage(byteArray);

                db.insert(byteArray);*/

            }else if(requestCode==SELECT_FILE){

                Uri selectedImageUri = data.getData();
                viewImage.setImageURI(selectedImageUri);
            }

        }
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
        if (viewImage.getDrawable() == null){
            Snackbar.make(v,"image is required", Snackbar.LENGTH_SHORT).show();
        return;
        }

        String description=recipeDescriptionEditText.getText().toString().trim();
        Long userId = getIntent().getLongExtra("ID",0);


        Recipe recipe=new Recipe();
        recipe.setName(name);
        recipe.setDescription(description);
        recipe.setDifficulty(difficultyValue);
        recipe.setUserId(userId);
        Bitmap bm=((BitmapDrawable)viewImage.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        recipe.setImage(byteArray);

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