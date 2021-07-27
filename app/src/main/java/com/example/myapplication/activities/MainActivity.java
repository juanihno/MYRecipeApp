package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.database.MyRecipeDBHelper;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    EditText username, password, repassword;
    Button btnSignUp, btnSignIn;
    SeekBar difficultySeekBar;
    MyRecipeDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignUp = (Button) findViewById(R.id.btnSignup);


        db = new MyRecipeDBHelper(this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name = username.getText().toString();
                String user_password = password.getText().toString();
                String user_repassword = repassword.getText().toString();

                if (user_name.equals("") || user_password.equals("") || user_repassword.equals("")) {
                    Toast.makeText(MainActivity.this, "Fill all the fields", Toast.LENGTH_LONG).show();

                } else {
                    if (user_password.equals(user_repassword)) {

                        Boolean userCheckResult = db.checkUserName(user_name);
                        if (userCheckResult == false) {

                            Boolean registrationResult = db.insertUserData(user_name, user_password);


                            if (registrationResult == true) {
                                Toast.makeText(MainActivity.this, "Registration successful", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "Registration unsuccesfull", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "User already exists./ Please Sign In", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Password doesnt match", Toast.LENGTH_LONG).show();

                    }


                }
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(intent);

            }
        });


    }}