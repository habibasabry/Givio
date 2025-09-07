package com.example.givio;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUp extends AppCompatActivity {

    EditText signupemail, signupname, signupphonenum, signuppassword;
    Button signup;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.sign_up);

        signupemail = findViewById(R.id.SignupEmail);
        signupname = findViewById(R.id.SignupName);
        signupphonenum = findViewById(R.id.SignupPhoneNum);
        signuppassword = findViewById(R.id.SignupPassword);
        signup = findViewById(R.id.BtnSignUp);
        progressBar = findViewById(R.id.ProgressBar);
        fAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(v -> {

            final String email = signupemail.getText().toString().trim();
            String password = signuppassword.getText().toString().trim();
            final String name = signupname.getText().toString().trim();
            final String phonenum = signupphonenum.getText().toString().trim();


            if (TextUtils.isEmpty(email)) {
                signupemail.setError("Please enter your Email.");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                signuppassword.setError("Please enter the Password.");
                return;
            }
            if (password.length() < 6) {
                signuppassword.setError("Password must be at least 6 characters.");
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            fAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            FirebaseUser user = fAuth.getCurrentUser();
                            if (user != null) {
                                UserProfileChangeRequest profileUpdates =
                                        new UserProfileChangeRequest.Builder()
                                                .setDisplayName(name)
                                                .build();

                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(task1 -> {
                                            if (task1.isSuccessful()) {
                                                Toast.makeText(SignUp.this, "User created!", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(SignUp.this, MainPage.class);
                                                intent.putExtra("name", name);
                                                intent.putExtra("email", email);
                                                intent.putExtra("phone", phonenum);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                            }
                        } else {
                            Toast.makeText(SignUp.this, "Error! " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
