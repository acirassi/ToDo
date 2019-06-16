package com.example.todo;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class VolleySingleton {

    private static VolleySingleton mInstance;
    private RequestQueue requestQuene;
    private static Context mCont;

    private VolleySingleton(Context context ){
        mCont = context;
        requestQuene = getRequestQueue();
    }

    public static synchronized VolleySingleton getInstance(Context context){
        if (mInstance == null) {

            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }


    private RequestQueue getRequestQueue() {

        if(mInstance == null){
            requestQuene= Volley.newRequestQueue(mCont.getApplicationContext());

        }
        return requestQuene;
    }
    public  <T> void addToRequestQueue(Request<T> req){
        getRequestQueue().add(req);
    }

}
