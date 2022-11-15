package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TextView toCreateAccount;
    ProgressBar progressBar;
    EditText email,password;
    MaterialButton loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toCreateAccount = findViewById(R.id.to_create_account);
        progressBar = findViewById(R.id.prog);
        email = findViewById(R.id.emailLoginInput);
        password = findViewById(R.id.passwordLoginInput);
        loginBtn = findViewById(R.id.login_btn);

        toCreateAccount.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,register.class)));
        loginBtn.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {
        String emailAdd = email.getText().toString();
        String pass = password.getText().toString();
        boolean valide= validateData(emailAdd,pass);
        if(valide){
            progress(true);
            FirebaseAuth firebaseAuth =  FirebaseAuth.getInstance();
            firebaseAuth.signInWithEmailAndPassword(emailAdd,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progress(false);
                    if(task.isSuccessful()){
                        if(firebaseAuth.getCurrentUser().isEmailVerified()){
                            startActivity(new Intent(MainActivity.this,reception.class));
                            finish();
                        }else{
                            Toast.makeText(MainActivity.this,"Your email adress is not verified,Please verify it.",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(MainActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    private boolean validateData(String emailAdd, String pass) {
        if(!Patterns.EMAIL_ADDRESS.matcher(emailAdd).matches()){
            email.setError("Email adress not valid");
            return false;
        }
        if(pass.length()<6){
            password.setError("Password length is lower than 6");
            return false;
        }
        return true;
    }

    void progress(boolean inProgress){
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            loginBtn.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            loginBtn.setVisibility(View.VISIBLE);
        }
    }
}