package com.example.whatsappclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    Boolean loginModeActive = false;

    public void redirectIfLoggedIn(){

        if(ParseUser.getCurrentUser() != null){

            Intent intent = new Intent(getApplicationContext(),UserList.class);
            startActivity(intent);

        }

    }

    public void toggleLoginMode(View view){
        Button loginsignupbutton = (Button) findViewById(R.id.button);
        TextView toggleTextView = (TextView) findViewById(R.id.toggleTextView);

        if(loginModeActive){

            loginModeActive = false;
            loginsignupbutton.setText("Signup");
            toggleTextView.setText("Or Login");

        }else{

            loginModeActive = true;
            loginsignupbutton.setText("Login");
            toggleTextView.setText(("Or Signup"));

        }

    }

    public void signupLogin (View view){
        EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);

        if(loginModeActive){

            ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {

                    if(e==null){

                        Log.i("log","User is logged in");
                        redirectIfLoggedIn();

                    }else{

                        String message = e.getMessage();

                        if(message.toLowerCase().contains("java"));{
                            message=e.getMessage().substring(e.getMessage().indexOf(" "));
                        }

                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

                    }

                }
            });

        }else {

            ParseUser user = new ParseUser();

            user.setUsername(usernameEditText.getText().toString());
            user.setPassword(passwordEditText.getText().toString());

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {

                    if (e == null) {

                        Log.i("user", "signed up");
                        redirectIfLoggedIn();

                    } else {

                        String message = e.getMessage();

                        if(message.toLowerCase().contains("java"));{
                            message=e.getMessage().substring(e.getMessage().indexOf(" "));
                        }

                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

                    }

                }
            });
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Whatsapp Login");
        redirectIfLoggedIn();

        ParseAnalytics.trackAppOpenedInBackground(getIntent());

    }
}
