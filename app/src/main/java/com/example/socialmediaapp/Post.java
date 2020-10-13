package com.example.socialmediaapp;

import android.icu.text.DateFormat;

import com.google.firebase.Timestamp;

public class Post {

    String post, user;
    Timestamp timestamp;

    public Post(){
    }

    public Post(String post, String user, com.google.firebase.Timestamp timestamp){
        this.post = post;
        this.user = user;
        this.timestamp = timestamp;
    }

    public String getPost() {
        return post;
    }

    public String getUser() {
        return user;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String toString(){
        DateFormat df = DateFormat.getDateTimeInstance();
        String date = df.format(getTimestamp().toDate());
        return "Post: " + getPost() + "\n" + "User: " + getUser() + "\n" + date;
    }
}
