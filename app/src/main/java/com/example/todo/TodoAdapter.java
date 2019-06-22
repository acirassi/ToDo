package com.example.todo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {


    private Context context;
    private List<TodoModel> list;

    public TodoAdapter(Context context, List<TodoModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.costom_list_todo,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final TodoModel todo = list.get(i);

        viewHolder.task.setText(todo.getTask());
        //viewHolder.done.setText(String.valueOf(todo.getDoneTask()));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "task is " + todo.getTask() , Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context , UpdateTodo.class);
                intent.putExtra("task" , todo.getTask());
                intent.putExtra("todoid", todo.getTodoid());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView task;
        public CheckBox done;

        OnTodoListerner onTodoListerner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            task = itemView.findViewById(R.id.textTodo);
            //done = itemView.findViewById(R.id.done);

        }
    }

    public interface OnTodoListerner{
        void onTodoClick(int position);
    }
}
