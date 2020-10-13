package com.example.socialmediaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class NewPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
    }

    public void newPost(View view){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        EditText newPost = (EditText) findViewById(R.id.newPost);
        Post post = new Post(newPost.getText().toString(), user.getDisplayName(), Timestamp.now());
        db.collection("posts").add(post);

        //go back to DisplayPostsActivity where new post can be seen
        Intent intent = new Intent(this, DisplayPostsActivity.class);
        startActivity(intent);
    }
}