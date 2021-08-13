package com.example.myapplication.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.database.MyRecipeDBHelper;
import com.example.myapplication.entities.Recipe;
import com.example.myapplication.services.DataService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class EditRecipeActivity extends AppCompatActivity {
    private EditText recipeNameEdiText;
    private EditText recipeDescriptionEditText;
    private SeekBar difficultySeekBar;
    private MaterialButton deleteRecipeButton;
    private Button cameraButton;
    private MaterialButton updateRecipeButton;
    private ImageView recipeImageView;
    private DataService recipeDataService;



    private Recipe recipe;
    private Integer difficultyValue = 0;
    Integer REQUEST_CAMERA=1, SELECT_FILE=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //enable arrow to come back and send the data to the previous activity(recyclerview)

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            cancel(v);
            }
        });


        Long userId = getIntent().getLongExtra("ID",0);


        Intent intentThatCalled = getIntent();
        if(intentThatCalled.hasExtra(Recipe.RECIPE_KEY)){
            recipe = (Recipe) intentThatCalled.getSerializableExtra(Recipe.RECIPE_KEY);
        }
        recipeNameEdiText = findViewById(R.id.recipeEditNameEditTextEditActivity);
        recipeDescriptionEditText = findViewById(R.id.recipeEditDescriptionEditTextEditActivity);
        difficultySeekBar = findViewById(R.id.editDificultySeekBarEditActivity);
        deleteRecipeButton = findViewById(R.id.deleteEditRecipeButtonEditActivity);
        updateRecipeButton = findViewById(R.id.updateEditRecipeButtonEditActivity);
        cameraButton=findViewById(R.id.cameraEditRecipeButtonEditActivity);
        byte[] bitmapbytes=recipe.getImage();
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inDensity = 100;
        opt.inTargetDensity = 100;
        Bitmap bitmap= (BitmapFactory.decodeStream(new ByteArrayInputStream(bitmapbytes), null, opt));
        //Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapbytes , 0, bitmapbytes .length);
        recipeImageView = findViewById(R.id.imageViewEditRecipeEditActivity);

        this.recipeImageView.setImageBitmap(bitmap);


        recipeNameEdiText.setText(recipe.getName());
        recipeDescriptionEditText.setText(recipe.getDescription());
        difficultySeekBar.setProgress(recipe.getDifficulty());



        //addRecipeButton=(MaterialButton)findViewById(R.id.addRecipeButton);
        //difficultySeekBar=(SeekBar)findViewById(R.id.dificultySeekBar);


        deleteRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(v);
                cancel(v);
            }
        });
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        updateRecipeButton.setOnClickListener(new View.OnClickListener() {
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

    private void selectImage() {
        final CharSequence[] items={"Camera","Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(EditRecipeActivity.this);
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
                recipeImageView.setImageBitmap(photo);
                /*ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                recipe.setImage(byteArray);

                db.insert(byteArray);*/

            }else if(requestCode==SELECT_FILE){

                Uri selectedImageUri = data.getData();
                recipeImageView.setImageURI(selectedImageUri);
            }

        }
    }


    private void delete(View v) {

        recipeDataService= new DataService();
        recipeDataService.init(this);Long id=recipe.getId();
        recipeDataService.delete(recipe);

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
        Bitmap bm=((BitmapDrawable)recipeImageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        recipe.setImage(byteArray);

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