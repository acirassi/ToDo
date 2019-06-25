package com.example.todo;

public class TodoModel {

    private  String task;
    private  int doneTask;
    private int todoid;

    public TodoModel(){

    }

    public TodoModel(int todoid,String task, int doneTask) {
        this.todoid = todoid;
        this.task = task;
        this.doneTask = doneTask;
    }

    public int getTodoid() {
        return todoid;
    }

    public void setTodoid(int todoid) {
        this.todoid = todoid;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getDoneTask() {
        return doneTask;
    }

    public void setDoneTask(int doneTask) {
        this.doneTask = doneTask;
    }
}
