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

import java.time.Duration;
import java.util.regex.Pattern;

public class register extends AppCompatActivity {

    EditText email,password,conformePassword;
    MaterialButton register;
    ProgressBar progressBar;

    TextView toLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toLogin = findViewById(R.id.to_create_account);
        email = findViewById(R.id.emailLoginInput);
        password = findViewById(R.id.passwordLoginInput);
        conformePassword = findViewById(R.id.conformepasswordLoginInput);
        register = findViewById(R.id.register_btn);
        progressBar = findViewById(R.id.progress);

        toLogin.setOnClickListener(v -> startActivity(new Intent(register.this,MainActivity.class)));

        register.setOnClickListener(v -> creteAccount());
    }

    void creteAccount(){
        String emailAdd = email.getText().toString();
        String pass = password.getText().toString();
        String conformPass = conformePassword.getText().toString();

        boolean valide = validateData(emailAdd,pass,conformPass);
        if(valide){
            progress(true);
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.createUserWithEmailAndPassword(emailAdd,pass).addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progress(false);
                    if(task.isSuccessful()){
                        Toast.makeText(register.this,"Thank you!\n" +
                                "Your account has been successfully created. We will contact you very soon!", Toast.LENGTH_SHORT).show();
                        firebaseAuth.getCurrentUser().sendEmailVerification();
                        firebaseAuth.signOut();
                        startActivity(new Intent(register.this,MainActivity.class));
                        finish();
                    }else{
                        Toast.makeText(register.this,task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private boolean validateData(String emailAdd, String pass, String conformPass) {
        if(!Patterns.EMAIL_ADDRESS.matcher(emailAdd).matches()){
            email.setError("Email adress not valid");
            return false;
        }
        if(pass.length()<6){
            password.setError("Password length is lower than 6");
            return false;
        }
        if(!conformPass.equals(pass)){
            conformePassword.setError("Password and confirme password are not the same");
            return false;
        }
        return true;
    }

    void progress(boolean inProgress){
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            register.setVisibility(View.INVISIBLE);
        }else{
            progressBar.setVisibility(View.INVISIBLE);
            register.setVisibility(View.VISIBLE);
        }
    }

}