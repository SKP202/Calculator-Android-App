package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    Database db;
    EditText editUsername, editPassword, editRepeatPassword;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        db = new Database(this);

        editUsername = findViewById(R.id.register_username_input);
        editPassword = findViewById(R.id.register_password_input);
        editRepeatPassword = findViewById(R.id.register_repeat_password_input);
        btnRegister = findViewById(R.id.register_button);

        btnRegister.setOnClickListener(v -> {
            String username = editUsername.getText().toString();
            String password = editPassword.getText().toString();
            String repeatPassword = editRepeatPassword.getText().toString();

            if (username.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
                Toast.makeText(Register.this, "All fields are required", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(repeatPassword)) {
                Toast.makeText(Register.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else if (!isPasswordValid(password)) {
                Toast.makeText(Register.this, "Password must be at least 10 characters long and contain at least one number and one special character", Toast.LENGTH_SHORT).show();
            } else if (db.checkUsernameExists(username)) {
                Toast.makeText(Register.this, "Username already exists", Toast.LENGTH_SHORT).show();
            } else {
                boolean insert = db.insertUser(username, password);
                if (insert) {
                    Toast.makeText(Register.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                    // Navigate back to the welcome page
                    Intent intent = new Intent(Register.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Register.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isPasswordValid(String password) {
        if (password.length() < 10) {
            return false;
        }
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[!@#$%^&*()_+=<>?{}\\[\\]-]).{10,}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
