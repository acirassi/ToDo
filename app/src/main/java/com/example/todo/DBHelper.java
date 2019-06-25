package com.example.todo;

import android.content.Context;
import android.content.Intent;
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

public class DBHelper {

    private static final String URL_GET_TODO = "https://todoacirassi.000webhostapp.com/api/v1/todos/",
            URL_TODO_DONE="https://todoacirassi.000webhostapp.com/api/v1/todo/done/true/",
            URL_TODO_UNDONE="https://todoacirassi.000webhostapp.com/api/v1/todo/done/false/";


    private static DBHelper mIntance;
    private static Context mCtx;

    private DBHelper(Context context){
        mCtx = context;
    }

    public static synchronized DBHelper getInstance(Context context){
        if(mIntance == null){
            mIntance = new DBHelper(context);
        }
        return mIntance;
    }


    public void todoDone(String todoId){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_TODO_DONE+todoId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject object = jsonArray.getJSONObject(0);
                    object.getString("notice");

                }catch(JSONException e){
                    e.printStackTrace();
                   Toast.makeText(mCtx,"Json Error : "+e.toString(),Toast.LENGTH_LONG).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mCtx," Error : "+error.toString(),Toast.LENGTH_LONG).show();

            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>param = new HashMap<>();

                return param;
            }
        };

        VolleySingleton.getInstance(mCtx).addToRequestQueue(stringRequest);

    }

    public void todoUnDone(String todoId){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_TODO_UNDONE+todoId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject object = jsonArray.getJSONObject(0);
                    object.getString("notice");

                }catch(JSONException e){
                    e.printStackTrace();
                    Toast.makeText(mCtx,"Json Error : "+e.toString(),Toast.LENGTH_LONG).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mCtx," Error : "+error.toString(),Toast.LENGTH_LONG).show();

            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>param = new HashMap<>();

                return param;
            }
        };

        VolleySingleton.getInstance(mCtx).addToRequestQueue(stringRequest);

    }

}
