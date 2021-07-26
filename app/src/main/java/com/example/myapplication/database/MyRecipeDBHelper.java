package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyRecipeDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "recipe.db";
    private static final Integer DATABASE_VERSION = 1;

    //create constants for the table's USER

    private static final String TABLE_USER = "USERS";
    private static final String COL_USER_ID = "USERID";
    private static final String COL_USER_NAME = "USERNAME";
    private static final String COL_USER_PASSWORD = "PASSWORD";

    //create constants for the table's RECIPE
    private static final String TABLE_RECIPE = "USERS";
    private static final String COL_RECIPE_ID = "ID";
    private static final String COL_RECIPE_NAME = "NAME";
    private static final String COL_RECIPE_DESCRIPTION = "DESCRIPTION";
    private static final String COL_RECIPE_DIFICULTY = "DIFICULTY";
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
            COL_RECIPE_DIFICULTY + " INTEGER, " +
            COL_RECIPE_IMAGE + " TEXT, " +
            COL_RECIPE_VOTES + " INTEGER DEFAULT 0, " +
            COL_RECIPE_STARS + " INTEGER DEFAULT 0 )";

    private static final String DROP_TABLE_RECIPE_ST = "DROP TABLE IF EXISTS " + TABLE_RECIPE;
    private static final String GET_ALL_RECIPE_ST = "SELECT * FROM " + TABLE_RECIPE;

    private static final String GET_RECIPE_BY_ID = "SELECT * FROM " + TABLE_RECIPE + " WHERE " + COL_RECIPE_ID + "= ?";
    private static final String UPDATE_RECIPE_VOTES = "UPDATE " + TABLE_RECIPE + " SET " + COL_RECIPE_STARS + " = " + COL_RECIPE_STARS + " + ? " + ", " + COL_RECIPE_VOTES + " = " + COL_RECIPE_VOTES + " + 1" + " WHERE " + COL_RECIPE_ID + "= ?";
    private static final String GET_RECIPES_BY_USER = "SELECT * FROM " + TABLE_RECIPE + "WHERE"+ COL_USER_ID + "=?";


    public MyRecipeDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
        //myDB.execSQL(DROP_TABLE_RECIPE_ST);

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
}
