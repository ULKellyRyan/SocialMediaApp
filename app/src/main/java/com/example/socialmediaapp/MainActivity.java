package com.example.socialmediaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Request Code - we need to do this so that once the authentication process has completed and
    // Firebase Authentication returns data to our Activity, we have a way of picking up this data
    // via a unique request code.
    private int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickLogIn(View view){
        //Email is chosen authentication method
        List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build());
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build(), RC_SIGN_IN);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if(resultCode == RESULT_OK){
                displayPosts(); //call method to start DisplayPostsActivity
            } else {
                if (response == null) {
                    System.out.println("Sign in cancelled");
                    return;
                }
                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    System.out.println("No internet connection");
                    return;
                }
            }
        }
    }

    public void displayPosts(){
        Intent intent = new Intent(this, DisplayPostsActivity.class);
        startActivity(intent);
    }
}