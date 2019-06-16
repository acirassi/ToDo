package com.example.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistorActivity extends AppCompatActivity {

    EditText textusername,textemail,textpass,textcpass;
    Button btnregistor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registor);

        textusername = findViewById(R.id.regusername);
        textemail = findViewById(R.id.regemail);
        textpass = findViewById(R.id.regpass1);
        textcpass = findViewById(R.id.regpass2);

        btnregistor = findViewById(R.id.btnregistration);

        btnregistor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registoruser();
            }
        });

    }

    public void  registoruser(){
       final String username = textusername.getText().toString().trim();
       final String email = textemail.getText().toString().trim();
       final String pass1 = textpass.getText().toString().trim();
       final String pass2 = textcpass.getText().toString().trim();




        if(TextUtils.isEmpty(username)){
            textusername.setError("Please Enter valid Username.");
            textusername.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(email)){
            textemail.setError("Please Enter valid E-mail.");
            textemail.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(pass1)){
            textpass.setError("Please Enter valid Password.");
            textpass.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(pass2)){
            textcpass.setError("Please Enter valid Password or Password Does Not Match.");
            textcpass.requestFocus();
            return;
        }else if(!pass2.equals(pass1)){
            textcpass.setError("Password Does Not Match.");
            textcpass.requestFocus();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, User.REGISTRATION_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONObject object = new JSONObject(response);

                    if(!object.getBoolean("error")){

                        Toast.makeText(getApplicationContext(),object.getString("message"),Toast.LENGTH_LONG).show();
                        JSONObject userJson = object.getJSONObject("user");

                        User user = new User(
                          userJson.getInt("userId"),
                          userJson.getString("userName"),
                          userJson.getString("email")
                        );

                        SharedPrefManager.getInstance(getApplicationContext()).userlogin(user);

                        finish();
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    }else{
                        Toast.makeText(getApplicationContext(),"message",Toast.LENGTH_LONG).show();

                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {
            protected Map<String, String>getParams() throws AuthFailureError{
                Map<String,String>param = new HashMap<>();
                param.put("username",username);
                param.put("email",email);
                param.put("pass",pass1);
               return param;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
