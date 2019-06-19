package com.example.todo;

public class User {

    private int uid;
    private String username ,email,pass;
    final static String REGISTRATION_URL = "https://todoacirassi.000webhostapp.com/api/v1/user/add";
    final static String LOGIN_URL = " https://todoacirassi.000webhostapp.com/api/v1/nuser/";

   public User (){

   }

    public User(int uid, String username,String email) {
        this.email = email;
        this.username = username;
        this.uid = uid;
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

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}

