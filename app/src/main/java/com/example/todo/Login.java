package com.example.todo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
Button btnlogin,btnregister;
EditText e_username,e_password;
ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnlogin = findViewById(R.id.btnlogin);
        btnregister = findViewById(R.id.btnregistor);
        e_username = findViewById(R.id.username);
        e_password = findViewById(R.id.password);
        loading = findViewById(R.id.progressBar2);
        Onclicklistener();

        if(SharedPrefManager.getInstance(this).isLoggedIn()){

            Intent i = new Intent(getApplicationContext(),HomeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }

    }

    public void Onclicklistener(){
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               userlogin();

            }
        });


//        Snackbar.make(v, "Invalid Username or Password", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this,RegistorActivity.class);
                startActivity(i);
            }
        });

    }

    public void userlogin(){
        final String username = e_username.getText().toString();
        final String password = e_password.getText().toString();


        if(TextUtils.isEmpty(username)){
            e_username.setError("Please Enter your Username.");
            e_username.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(password)) {
            e_password.setError("Please Enter Your Password.");
            e_password.requestFocus();
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET,User.LOGIN_URL+username, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.setVisibility(View.VISIBLE);

                Toast.makeText(getApplicationContext(),User.LOGIN_URL+username,Toast.LENGTH_LONG).show();
                try{

                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject object = jsonArray.getJSONObject(0);
                    if(object.getString("userName").equals(username) && object.getString("pass").equals(password)){

                        //Toast.makeText(getApplicationContext(),object.getString("message"),Toast.LENGTH_LONG).show();

                        //JSONObject userobject = object.getJSONObject("user");

                        User user= new User(
                                object.getInt("userId"),
                                object.getString("userName"),
                                object.getString("email")
                        );

                        SharedPrefManager.getInstance(getApplicationContext()).userlogin(user);
                        Toast.makeText(getApplicationContext(),"Login Successfully.",Toast.LENGTH_LONG).show();

                        finish();
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }else{
                        Toast.makeText(getApplicationContext(),"Invalid Username or Password.",Toast.LENGTH_LONG).show();
                        loading.setVisibility(View.GONE);

                    }


                }catch(JSONException e){
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userName", username);
                params.put("pass", password);
                return params;
            }
        };

        loading.setVisibility(View.GONE);
        btnlogin.setVisibility(View.VISIBLE);

     /*  if(password.equals("1234") && username.equals("kishan")){
           Intent i = new Intent(Login.this,HomeActivity.class);
           startActivity(i);
       }else{
           Toast.makeText(getApplicationContext(),"Something went wroung.",Toast.LENGTH_LONG).show();
       }*/

         VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


    }
}
