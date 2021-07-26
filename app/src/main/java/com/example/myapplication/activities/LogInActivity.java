package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.database.MyRecipeDBHelper;

public class LogInActivity extends AppCompatActivity {
    EditText username, password;
    Button btnLogIn;
    MyRecipeDBHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_activity);

        username= (EditText) findViewById(R.id.usernameLogIn);
        password= (EditText) findViewById(R.id.passwordLogIn);
        btnLogIn =(Button) findViewById(R.id.btnLogIn);
        db= new MyRecipeDBHelper(this);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user =username.getText().toString();
                String pass =password.getText().toString();

                if(user.equals("")||pass.equals(""))
                {
                    Toast.makeText(LogInActivity.this,"Enter all the credentials",Toast.LENGTH_LONG).show();

                }
                else{
                    Boolean result =db.checkUserPassword(user,pass);
                    if (result==true){
                        Toast.makeText(LogInActivity.this,"valid credentials",Toast.LENGTH_LONG).show();

                        Intent intent= new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);

                }
                    else {
                        Toast.makeText(LogInActivity.this,"Invalid credentials",Toast.LENGTH_LONG).show();

                    }
                }

            }
        });

    }
}