package com.example.licenceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.licenceapp.Model.User;
import com.example.licenceapp.Model.UserRegister;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Register extends AppCompatActivity {
    EditText username, email, password, secondPassword;
    Button btnRegister;

    FirebaseDatabase database;
    DatabaseReference  usersRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.edt_username);
        email = findViewById(R.id.edt_email);
        password = findViewById(R.id.edt_password);
        secondPassword = findViewById(R.id.edt_second_password);
        btnRegister = findViewById(R.id.btn_register);

        database = FirebaseDatabase.getInstance();
        usersRegister = database.getReference("UserRegister");

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountRegistration();
            }
        });

    }


    private void accountRegistration()
    {
        if(checkNetworkConnection())
        {
            final UserRegister userRegister = new UserRegister(username.getText().toString(),
                    email.getText().toString(),
                    password.getText().toString(),
                    secondPassword.getText().toString());

            usersRegister.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child(userRegister.getUsernameRegister()).exists())
                    {
                        Toast.makeText(Register.this, "Username already exists!", Toast.LENGTH_SHORT).show();
                    }
                    if (!userRegister.getPasswordRegister().equals(userRegister.getSecondPasswordRegister()))
                    {
                        Toast.makeText(Register.this, "Password don't match.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        usersRegister.child(userRegister.getUsernameRegister()).setValue(userRegister);
                        Toast.makeText(Register.this, "User registration success.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else
        {

        }

    }

    private boolean checkNetworkConnection()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null)
        {
            return true;
        }
        else
        {
            Toast.makeText(this, "Not Internet Connection", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    

}


