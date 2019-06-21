package com.example.todo;

public class TodoModel {

    private  String task;
    private  int doneTask;

    public TodoModel(){

    }

    public TodoModel(String task, int doneTask) {
        this.task = task;
        this.doneTask = doneTask;
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
