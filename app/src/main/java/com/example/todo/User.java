package com.example.todo;

public class User {

    private int uid;
    private String username ,email;
    final static String REGISTRATION_URL = "https://todoacirassi.000webhostapp.com/api/v1/user/add";

    public User(int uid,String username, String email){
        this.uid = uid;
        this.email = email;
        this.username = username;

    }

    public int getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }


}

