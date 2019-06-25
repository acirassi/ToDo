package com.example.todo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
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
    private CheckBox checkdone;
    private String todoid;
    private int isdone;
    TodoModel todo1;
    public TodoAdapter(Context context, List<TodoModel> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(context).inflate(R.layout.costom_list_todo,viewGroup,false);
        todo1 = list.get(i);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final TodoModel todo = list.get(i);
        todoid = ""+ todo.getTodoid();
        viewHolder.task.setText(todo.getTask());
        viewHolder.done.setTag(todo.getTodoid());
        if(todo.getDoneTask() == 1 ){
            viewHolder.done.setSelected(true);

        }
        viewHolder.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(viewHolder.done.isSelected()){
                    viewHolder.done.setSelected(false);
                    DBHelper.getInstance(v.getContext()).todoUnDone(viewHolder.done.getTag().toString());


                }else {
                    viewHolder.done.setSelected(true);
                    DBHelper.getInstance(v.getContext()).todoDone(viewHolder.done.getTag().toString());
                }

               context.startActivity(new Intent(context,HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView task,tid;
        public CheckBox done;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            task = itemView.findViewById(R.id.textTodo);
            done = itemView.findViewById(R.id.checkdone);

        }
    }
}
