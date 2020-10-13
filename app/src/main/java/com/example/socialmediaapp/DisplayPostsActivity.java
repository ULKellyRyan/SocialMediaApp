package com.example.socialmediaapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DisplayPostsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_posts);
        getPosts();     //retrieve 5 most recent posts from Cloud Firestore
    }

    public void loadStockPosts(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //preloaded posts
        Post post1 = new Post("Welcome to the Book of Faces!", "admin", Timestamp.now());
        db.collection("posts").add(post1);
        Post post2 = new Post("Register today!", "admin", Timestamp.now());
        db.collection("posts").add(post2);
        Post post3 = new Post("Login to view posts!", "admin", Timestamp.now());
        db.collection("posts").add(post3);
        Post post4 = new Post("Create your own posts!", "admin", Timestamp.now());
        db.collection("posts").add(post4);
        Post post5 = new Post("Tell us what you think!", "admin", Timestamp.now());
        db.collection("posts").add(post5);
    }

    public void getPosts(){
        final List<Post> listPosts = new ArrayList<Post>(5);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("posts").orderBy("timestamp").limitToLast(5).get()
        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                try{
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot document : task.getResult() ){
                            Post post = document.toObject(Post.class);
                            listPosts.add(post);
                        }
                        TextView[] recentPosts = new TextView[5];
                        recentPosts[0] = findViewById(R.id.mostRecentPost1);
                        recentPosts[1] = findViewById(R.id.mostRecentPost2);
                        recentPosts[2] = findViewById(R.id.mostRecentPost3);
                        recentPosts[3] = findViewById(R.id.mostRecentPost4);
                        recentPosts[4] = findViewById(R.id.mostRecentPost5);
                        for(int i = 0, j = 4; (i < 5 && j >= 0); i++, j--){
                            recentPosts[i].setText(listPosts.get(j).toString());
                        }
                    } else {
                        Log.w("tag", "Error getting documents.", task.getException());
                    }
                } catch (IndexOutOfBoundsException e) { //if there are fewer than 5 posts available stock posts will be uploaded and retrieved
                    loadStockPosts();
                    getPosts();
                }
            }
        });
    }

    //start NewPostActivity
    public void createNewPost(View view){
        Intent intent = new Intent(this, NewPostActivity.class);
        startActivity(intent);
    }
}