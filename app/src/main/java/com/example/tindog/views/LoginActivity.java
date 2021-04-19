package com.example.tindog.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tindog.R;
import com.example.tindog.models.ModelFirebase;

public class LoginActivity extends AppCompatActivity {

    private EditText emailInput;
    private EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.login_activity_email_edit_text);
        passwordInput = findViewById(R.id.login_activity_password_edit_text);
    }

    public void toRegisterPage(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void login(View view) {
        ModelFirebase.signIn(emailInput, passwordInput, listener -> {
            if (listener) {
                Toast.makeText(getApplicationContext(), "Welcome :)", Toast.LENGTH_SHORT).show();
                finishAffinity();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}