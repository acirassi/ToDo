package com.example.todo;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {
Button btnlogin,btnregistor;
String USERNAME="kishan",PASSWORD="1234";
EditText e_username,e_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnlogin = findViewById(R.id.btnlogin);
        btnregistor = findViewById(R.id.btnregistor);
        e_username = findViewById(R.id.username);
        e_password = findViewById(R.id.password);
        Onclicklistener();

    }

    public void Onclicklistener(){
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(e_username.getText().toString().equals(USERNAME) && e_password.getText().toString().equals(PASSWORD)){
                    Intent intent = new Intent(Login.this,HomeActivity.class);
                    startActivity(intent);
                }else{
                    Snackbar.make(v, "Invalid Username or Password", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });

        btnregistor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this,RegistorActivity.class);
                startActivity(i);
            }
        });
    }
}
