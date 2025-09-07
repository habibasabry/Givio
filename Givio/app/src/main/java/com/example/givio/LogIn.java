package com.example.givio;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {

    EditText loginemail , loginpassword;
    Button login;
    TextView movetosignup;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.log_in);

        //Get the xml items id
        loginemail = findViewById(R.id.LoginEmail);
        loginpassword = findViewById(R.id.LoginPassword);

        login = findViewById(R.id.BtnLogin);

        movetosignup = findViewById(R.id.NewUser);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.ProgressBar);


        //Moving to the Sign Up page
        movetosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent moveSignUp = new Intent(LogIn.this , SignUp.class);
                startActivity(moveSignUp);
            }
        });


        //Log In
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = loginemail.getText().toString().trim();
                String password = loginpassword.getText().toString().trim();

                //Error Messages
                if(TextUtils.isEmpty(email)){
                    loginemail.setError("Pleas enter your Email.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    loginpassword.setError("Pleas enter the Password.");
                    return;
                }

                if(password.length() < 6){
                    loginpassword.setError("Password must be more than 6 characters.");
                    return;
                }



                //Authenticate the user
                fAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(LogIn.this, "Logged in!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext() , MainPage.class));

                        }else {
                            Toast.makeText(LogIn.this, "Error!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    }
                });

            }
        });
    }
}