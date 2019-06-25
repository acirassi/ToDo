package com.example.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class UpdateTodo extends AppCompatActivity {

    EditText edtUpdate;
    Button btnUpdate;
    static int UserId;
    int todoid;


    SharedPrefManager sharedPrefManager = new SharedPrefManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_todo);

        edtUpdate = findViewById(R.id.update_task);
        btnUpdate = findViewById(R.id.btn_update);

        UserId = sharedPrefManager.getUserId();

        todoid = (int) getIntent().getIntExtra("todoid", 0);

        String task = getIntent().getStringExtra("task");
        if (task != null){
            Toast.makeText(this, "task is " + task , Toast.LENGTH_LONG).show();
        }else  Toast.makeText(this, "can't get intent extra..." , Toast.LENGTH_LONG).show();

        edtUpdate.setText(task);


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTodo();
            }
        });
    }

    private void updateTodo() {

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            String URL = " https://todoacirassi.000webhostapp.com/api/v1/todo/update/";

            int uid = SharedPrefManager.getUserId();

            String URL_UPDATETODO = URL + todoid ;


            JSONObject jsonBody = new JSONObject();

            if (!TextUtils.isEmpty(edtUpdate.getText().toString())){
                jsonBody.put("task", edtUpdate.getText().toString());

                final String requestBody = jsonBody.toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATETODO, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("VOLLEY", response);
                        Intent intent = new Intent(UpdateTodo.this,HomeActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.toString());

                    }
                }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                            return null;
                        }
                    }

                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null) {
                            responseString = String.valueOf(response.statusCode);
                            // can get more details such as response.headers
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };

                requestQueue.add(stringRequest);

            }else {
                Toast.makeText(this , "Plese Enter Todo." ,Toast.LENGTH_LONG).show();
                edtUpdate.requestFocus();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
