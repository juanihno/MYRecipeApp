package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.myapplication.entities.Recipe;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyRecipeDBHelper extends SQLiteOpenHelper {
    private static final String TAG = MyRecipeDBHelper.class.getName();

    private static MyRecipeDBHelper mInstance = null;
    private Context context;
    private static final String DATABASE_NAME = "recipe.db";
    private static final Integer DATABASE_VERSION = 1;

    //create constants for the table's USER

    private static final String TABLE_USER = "USERS";
    private static final String COL_USER_ID = "USERID";
    private static final String COL_USER_NAME = "USERNAME";
    private static final String COL_USER_PASSWORD = "PASSWORD";

    //create constants for the table's RECIPE
    private static final String TABLE_RECIPE = "RECIPES";
    private static final String COL_RECIPE_ID = "ID";
    private static final String COL_RECIPE_NAME = "NAME";
    private static final String COL_RECIPE_DESCRIPTION = "DESCRIPTION";
    private static final String COL_RECIPE_DIFFICULTY = "DIFFICULTY";
    private static final String COL_RECIPE_IMAGE = "IMAGE";
    private static final String COL_RECIPE_VOTES = "VOTES";
    private static final String COL_RECIPE_STARS = "STARS";

    private static final String CREATE_TABLE_USER_ST = "CREATE TABLE " + TABLE_USER + "(" + COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_USER_NAME + " TEXT, " +
            COL_USER_PASSWORD + " TEXT );";

    private static final String DROP_TABLE_USER_ST = "DROP TABLE IF EXISTS " + TABLE_USER;
    private static final String GET_ALL_USER = "SELECT * FROM " + TABLE_USER;



    //create sql statements initial version
    private static final String CREATE_TABLE_RECIPE_ST = "CREATE TABLE " + TABLE_RECIPE +
            "(" + COL_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_RECIPE_NAME + " TEXT, " +
            COL_RECIPE_DESCRIPTION + " TEXT, " +
            COL_RECIPE_DIFFICULTY + " INTEGER, " +
            COL_RECIPE_IMAGE + " TEXT, " +
            COL_RECIPE_VOTES + " INTEGER DEFAULT 0, " +
            COL_RECIPE_STARS + " INTEGER DEFAULT 0) ";
            //COL_USER_ID + " INTEGER);";//", " +
            //"FOREIGN KEY(" + COL_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COL_USER_ID + ")" +
            //")";


    private static final String DROP_TABLE_RECIPE_ST = "DROP TABLE IF EXISTS " + TABLE_RECIPE;
    private static final String GET_ALL_RECIPE_ST = "SELECT * FROM " + TABLE_RECIPE;

    private static final String GET_RECIPE_BY_ID = "SELECT * FROM " + TABLE_RECIPE + " WHERE " + COL_RECIPE_ID + "= ?";
    private static final String UPDATE_RECIPE_VOTES = "UPDATE " + TABLE_RECIPE + " SET " + COL_RECIPE_STARS + " = " + COL_RECIPE_STARS + " + ? " + ", " + COL_RECIPE_VOTES + " = " + COL_RECIPE_VOTES + " + 1" + " WHERE " + COL_RECIPE_ID + "= ?";
    private static final String GET_RECIPES_BY_USER = "SELECT * FROM " + TABLE_RECIPE + " WHERE "+ COL_USER_ID + " = ?";
    private static final String GET_USER_ID = "SELECT  " + COL_USER_ID + " FROM " +TABLE_USER +" WHERE "+ COL_USER_NAME+"= ?"+ " AND "+COL_USER_PASSWORD+"= ?";



    public static synchronized MyRecipeDBHelper getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new MyRecipeDBHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    public MyRecipeDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER_ST);
        db.execSQL(CREATE_TABLE_RECIPE_ST);

        //("create Table users (userid Integer primary key autoincrement ,username Text , password Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_USER_ST);
        //("drop Table if exists users");
        db.execSQL(DROP_TABLE_RECIPE_ST);
        onCreate(db);

    }
    public boolean insertUserData(String user_name, String user_password){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL_USER_NAME, user_name);
        contentValues.put(COL_USER_PASSWORD, user_password);
        long result = db.insert(TABLE_USER, null, contentValues);
        //db.close();
        //if result is -1  insert was not performed due to an error, otherwise will have the row ID of the newly inserted row
        if(result==-1){
            return false;
        }
        else{
            return true;
        }
    }
    public boolean checkUserName (String username){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COL_USER_NAME + "= ?",new String[]{username});
        //"select * from USERS where USERNAME = ?", new String[]{username});

        if (cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }

    }
    public boolean checkUserPassword (String username, String password){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COL_USER_NAME + "= ?" + " AND " + COL_USER_PASSWORD+"=?",new String[]{username, password});
        //("select * from USERS where USERNAME = ? and PASSWORD =?", new String[]{username, password});
        if (cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }

    }
    /**
     * Add a recipe to the database
     * @param name          Recipe's name
     * @param description   Recipe's description
     * @param difficulty     Recipe's scariness level
     * @return      if it succeeded, the autogenerated id (primary key) of the recently added monster
     *              otherwise -1
     */
    public Long insertRecipe(String name, String description, Integer difficulty){//, Long userId) {
        //create an instance of SQLITE database
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_RECIPE_NAME, name);
        contentValues.put(COL_RECIPE_DESCRIPTION, description);
        contentValues.put(COL_RECIPE_DIFFICULTY, difficulty);
        //we store the image name, after using
        contentValues.put(COL_RECIPE_IMAGE, getRandomImageName());
        //contentValues.put(COL_USER_ID, userId);


        long result = db.insert(TABLE_RECIPE, null, contentValues);
        db.close();

        //if result is -1  insert was not performed due to an error, otherwise will have the row ID of the newly inserted row
        return result;
    }

    /**
     * @return  A cursor of all monsters in the table called monster.
     */
    private Cursor getAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(GET_ALL_RECIPE_ST, null);
    }

    /**
     * Update a Recipe record in the database
     * @param id            Primary key of the recipe
     * @param name          New Recipe's name
     * @param description   New Recipe's description
     * @param difficulty     New Recipe's difficulty level
     * @return      true is the recipe record in the database was updated, otherwise false.
     */
    public boolean updateRecipe(Long id, String name, String description, Integer difficulty) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_RECIPE_ID, id);
        contentValues.put(COL_RECIPE_NAME, name);
        contentValues.put(COL_RECIPE_DESCRIPTION, description);
        contentValues.put(COL_RECIPE_DIFFICULTY, difficulty);

        int numOfRowsUpdated = db.update(TABLE_RECIPE, contentValues, "ID = ?", new String[]{id.toString()});
        db.close();
        return numOfRowsUpdated == 1; //if your query is going to update more than 1 record (this is not the case) then the condition will be numRowsUpdated > 0
    }

    /**
     * Delete a monster from the database
     * @param id    Recipe's primary key
     * @return      true if the monster was deleted, otherwise false
     */
    public boolean deleteRecipe(Long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        //delete return the # of rows affected by the query
        int numOfRowsDeleted = db.delete(TABLE_RECIPE, "ID = ?", new String[]{id.toString()});
        db.close();
        return numOfRowsDeleted == 1;//if your query is going to delete more than 1 record (this is not the case) then the condition will be numOfRowsDeleted > 0
    }

    /**
     * @return an autogenerated image name string value
     */
    private String getRandomImageName() {
        Random ran = new Random();
        int value = ran.nextInt(30) + 1;
        return "monster_" + value;
    }

    /**
     * @return a list of all monsters from the database table called monster
     */
    public List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        Cursor cursor = getAll();

        if(cursor.getCount() > 0) {
            Recipe recipe;
            while (cursor.moveToNext()) {
                Long id = cursor.getLong(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                Integer difficulty = cursor.getInt(3);
                String imageFileName = cursor.getString(4);
                Integer votes = cursor.getInt(5);
                Integer stars = cursor.getInt(6);

                recipe = new Recipe(id, name, description, difficulty, imageFileName, votes, stars);
                recipes.add(recipe);
            }
        }
        cursor.close();
        return recipes;
    }
    public Long getUserId(Long userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        //Recipe recipe = null;
        Cursor cursor = db.rawQuery(GET_USER_ID, new String[]{userId.toString()});
    return userId;}


        public Recipe getRecipe(Long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Recipe recipe = null;
        Cursor cursor = db.rawQuery(GET_RECIPE_BY_ID, new String[]{id.toString()});

        if(cursor.getCount() > 0 ){
            while (cursor.moveToNext()){
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                Integer difficulty = cursor.getInt(3);
                String imageFileName = cursor.getString(4);
                Integer votes = cursor.getInt(5);
                Integer stars = cursor.getInt(6);

                recipe = new Recipe(id, name, description,  difficulty, imageFileName, votes, stars);
            }
        }
        cursor.close();
        return recipe;
    }

    public boolean rateRecipe(Long id, Integer stars){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(UPDATE_RECIPE_VOTES, new String[ ]{ stars.toString(), id.toString() });
        return true;
    }
}
