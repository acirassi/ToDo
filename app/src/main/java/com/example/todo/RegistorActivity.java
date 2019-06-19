package com.example.todo;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.HashMap;
import java.util.Map;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class RegistorActivity extends AppCompatActivity {

    EditText textusername,textemail,textpass,textcpass;
    Button btnregistor;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registor);

        textusername = findViewById(R.id.regusername);
        textemail = findViewById(R.id.regemail);
        textpass = findViewById(R.id.regpass1);
        textcpass = findViewById(R.id.regpass2);
        progressBar = findViewById(R.id.progressBar);

        btnregistor = findViewById(R.id.btnregistration);

        btnregistor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registoruser();
            }
        });

    }

    public void  registoruser(){
        int uid;
        final String userName = textusername.getText().toString().trim();
        final String email = textemail.getText().toString().trim();
        final String pass = textpass.getText().toString().trim();
        final String pass2 = textcpass.getText().toString().trim(),username,e_mail;


         progressBar.setVisibility(View.VISIBLE);
         btnregistor.setEnabled(false);
        if(TextUtils.isEmpty(userName)){
            textusername.setError("Please Enter valid Username.");
            textusername.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(email)  || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            textemail.setError("Please Enter valid E-mail.");
            textemail.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            textpass.setError("Please Enter valid Password.");
            textpass.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(pass2)){
            textcpass.setError("Please Enter valid Password.");
            textcpass.requestFocus();
            return;
        }else if(!pass2.equals(pass)){
            textcpass.setError("Password Does Not Match.");
            textcpass.requestFocus();
            return;
        }



        StringRequest stringRequest = new StringRequest(Request.Method.POST, User.REGISTRATION_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            try{
                JSONArray jsonArray = new JSONArray(response);
                JSONObject object = jsonArray.getJSONObject(0);

                if(true){
                       // Toast.makeText(getApplicationContext(),object.getString("message"),Toast.LENGTH_LONG).show();
                             User user= new User(
                                Integer.parseInt(object.optString("userId")),
                                object.optString("userName"),
                                object.optString("email")

                            );
                            SharedPrefManager.getInstance(getApplicationContext()).userlogin(user);


                        finish();
                        progressBar.setVisibility(View.GONE);
                        Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }else{
                        Toast.makeText(getApplicationContext(),"Something went wrong.",Toast.LENGTH_LONG).show();
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Register Error : "+e.toString(),Toast.LENGTH_LONG).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Register Error : "+error.toString(),Toast.LENGTH_LONG).show();
            }
        }) {
            protected Map<String, String>getParams() throws AuthFailureError{
                Map<String,String>param = new HashMap<>();
                param.put("username",userName);
                param.put("email",email);
                param.put("pass",pass);
               return param;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
}
