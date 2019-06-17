package com.example.todo;

public class TodoModel {

    private  String task;
    private  String doneTask;

    public TodoModel(){

    }

    public TodoModel(String task, String doneTask) {
        this.task = task;
        this.doneTask = doneTask;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDoneTask() {
        return doneTask;
    }

    public void setDoneTask(String doneTask) {
        this.doneTask = doneTask;
    }
}
