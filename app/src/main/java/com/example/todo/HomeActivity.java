package com.example.todo;

import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.drm.ProcessedData;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private RecyclerView.Adapter adapter;
    private int uid;
    private List<TodoModel> todoList;
    private String URL;

    private RequestQueue rq;
    ProgressDialog progressDialog;
    private static final String URL_GET_TODO = "https://todoacirassi.000webhostapp.com/api/v1/todos/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        uid = SharedPrefManager.getUserId();
        URL = URL_GET_TODO+uid;
        Toast.makeText(getApplicationContext(),uid+" "+URL,Toast.LENGTH_LONG).show();

        rq = Volley.newRequestQueue(this);
        recyclerView = findViewById(R.id.todoList);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        todoList =new ArrayList<>();

        progressDialog = new ProgressDialog(this);
        loadRecyclerViewData();



        FloatingActionButton fab = findViewById(R.id.addfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this,AddTodoActivity.class);
                startActivity(i);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void loadRecyclerViewData() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Getting your ToDos...");
        progressDialog.setCancelable(false);
        progressDialog.show();



        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(URL_GET_TODO, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Toast.makeText(getApplicationContext(),"Lenght is equels"+response.length() +"and uid-"+uid,Toast.LENGTH_LONG).show();
                for(int i=0;i<response.length();i++){

                    TodoModel todoModel = new TodoModel();
                    try{
                        JSONObject jsonObject = response.getJSONObject(i);
                        todoModel.setTask(jsonObject.getString("task"));
//                        todoModel.setDoneTask(jsonObject.getInt("done"));

                    }catch(JSONException e){
                        Toast.makeText(getApplicationContext(),"Error:"+e.toString(),Toast.LENGTH_LONG).show();
                    }
                    todoList.add(todoModel);

                }
                TodoAdapter adapter = new TodoAdapter(HomeActivity.this,todoList);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    Log.i("Volley Error: ",error.toString());
            }
        });

        rq.add(jsonArrayRequest);
        progressDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
