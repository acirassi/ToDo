package com.example.todo;

import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.drm.ProcessedData;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Layout;
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
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private RecyclerView recyclerView,recyclerView2;
    private RecyclerView.LayoutManager layoutManager,layoutManager2;
   // private RecyclerView.Adapter adapter,doneadapter;
    private int uid;
    private List<TodoModel> todoList,todoDoneList;

    private String URL;
    private String URL_DELETE;

    private RequestQueue rq;
    ProgressBar progressBar;
    TextView megload;
    CheckBox checkdone;
    LinearLayout listlayout,loadinglayout;

    private static HomeActivity mIntance;
    private static Context mCtx;



    private static final String URL_GET_TODO = "https://todoacirassi.000webhostapp.com/api/v1/todos/";
    private  static  final String URL_DELETE_TODO = "https://todoacirassi.000webhostapp.com/api/v1/todo/delete/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        uid = SharedPrefManager.getUserId();
        URL = URL_GET_TODO+uid;

        listlayout =  findViewById(R.id.todolistlayout);
        loadinglayout =findViewById(R.id.loadingmeg);

        rq = Volley.newRequestQueue(this);

        recyclerView = findViewById(R.id.todoList);
        recyclerView2 = findViewById(R.id.tododoneList);

        checkdone = findViewById(R.id.checkdone);
        recyclerView.setHasFixedSize(true);recyclerView2.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        layoutManager2 = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView2.setLayoutManager(layoutManager2);

        todoList =new ArrayList<>();
        todoDoneList =new ArrayList<>();

        progressBar = findViewById(R.id.progressBar3);
        megload = findViewById(R.id.loadmeg);
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

        navigationView.setNavigationItemSelectedListener(t
    public void loadRecyclerViewData() {
       listlayout.setVisibility(View.GONE);
       loadinglayout.setVisibility(View.VISIBLE);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() { @Override
            public void onResponse(JSONArray response) {
              //  Toast.makeText(getApplicationContext(),"Lenght is equels"+response.length() +"and uid-"+uid,Toast.LENGTH_LONG).show();

                for(int i=0;i<response.length();i++){

                    TodoModel todoModel = new TodoModel();
                    try{
                        JSONObject jsonObject = response.getJSONObject(i);
                        todoModel.setTask(jsonObject.getString("task"));
                        todoModel.setTodoid(jsonObject.getInt("todoId"));
                        todoModel.setDoneTask(jsonObject.getInt("done"));

                    }catch(JSONException e){
                        Toast.makeText(getApplicationContext(),"Error:"+e.toString(),Toast.LENGTH_LONG).show();
                    }
                    if(todoModel.getDoneTask() == 1){
                        todoDoneList.add(todoModel);
                    }else{
                        todoList.add(todoModel);
                    }

                }
                TodoAdapter adapter = new TodoAdapter(HomeActivity.this,todoList);
                DoneToDoAdapter adapter2 = new DoneToDoAdapter(HomeActivity.this,todoDoneList);

                recyclerView.setAdapter(adapter);
                recyclerView2.setAdapter(adapter2);
                listlayout.setVisibility(View.VISIBLE);
                loadinglayout.setVisibility(View.GONE);


            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    Log.i("Volley Error: ",error.toString());
            }
        });

        rq.add(jsonArrayRequest);
        
        progressDialog.dismiss();

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);


    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback  = new ItemTouchHelper.SimpleCallback(0 ,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT ) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

            final TodoModel todo = todoList.get(viewHolder.getAdapterPosition());
            URL_DELETE = URL_DELETE_TODO + todo.getTodoid();
            Toast.makeText(getApplicationContext(), URL_DELETE , Toast.LENGTH_LONG).show();
            todoList.remove(viewHolder.getAdapterPosition());
            //todoAdapter.notifyDataSetChanged();
            todoDelete();
        }

    };

    private void todoDelete() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET , URL_DELETE, null, new Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Toast.makeText(getApplicationContext() , "" , Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext() , "Delete Successfuly" , Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
        requestQueue.add(jsonArrayRequest);

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
            Toast.makeText(getApplicationContext(),"Settingd selected.",Toast.LENGTH_LONG).show();
            return true;
        }else if(id == R.id.action_logout){
            SharedPrefManager.getInstance(getApplicationContext()).logout();

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
