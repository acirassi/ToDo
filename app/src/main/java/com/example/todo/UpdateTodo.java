package com.example.todo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UpdateTodo extends AppCompatActivity {

    EditText edtUpdate;
    Button btnUpdate;
    static int UserId;

    SharedPrefManager sharedPrefManager = new SharedPrefManager(getApplicationContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_todo);

        edtUpdate = findViewById(R.id.update_task);
        btnUpdate = findViewById(R.id.btn_update);

        UserId = sharedPrefManager.getUserId();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTodo();
            }
        });
    }

    private void updateTodo() {

    }
}
