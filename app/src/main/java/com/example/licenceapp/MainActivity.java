package com.example.licenceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.licenceapp.BroadcastReceiver.AlarmReceiver;
import com.example.licenceapp.Common.Common;
import com.example.licenceapp.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference users;

    Button btnSingIn, btnSingUp, btnConfirm;
    EditText edtUsername, edtPassword;
    EditText edtNewUsername, edtNewEmail, edtNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerAlarm();

        database = FirebaseDatabase.getInstance();
        users = database.getReference("User");

        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        btnSingIn = (Button)findViewById(R.id.btn_sing_in);
        btnSingUp = (Button)findViewById(R.id.btn_sing_up);

        //btnConfirm = (Button)findViewById(R.id.btn_confirm);

        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSingUpDialog();
            }
        });

        btnSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singIn(edtUsername.getText().toString(), edtPassword.getText().toString());
            }
        });


    }

    private void registerAlarm() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 3);
        calendar.set(Calendar.MINUTE, 15);
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager)this.getSystemService(this.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }


    private void singIn(String username, String password) {
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(username).exists())
                {
                    if(!username.isEmpty())
                    {
                        User login = snapshot.child(username).getValue(User.class);
                        if(login.getPassword().equals(password))
                        {
                            Toast.makeText(MainActivity.this, "Login OK", Toast.LENGTH_SHORT).show();
                            Intent intentCategories = new Intent(MainActivity.this, Home.class);
                            Common.currentUser = login;
                            startActivity(intentCategories);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Please enter your user name", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "User is not exists!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void showSingUpDialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Sign Up");
        alertDialog.setMessage("Please fill full information");

        LayoutInflater inflater = this.getLayoutInflater();
        View sing_up_layout = inflater.inflate(R.layout.sing_up_layout, null);

        edtNewUsername = (EditText)sing_up_layout.findViewById(R.id.edtNewUserName);
        edtNewEmail= (EditText)sing_up_layout.findViewById(R.id.edtNewEmail);
        edtNewPassword = (EditText)sing_up_layout.findViewById(R.id.edtNewPassword);


        alertDialog.setView(sing_up_layout);
        alertDialog.setIcon(R.drawable.ic_baseline_account_circle_24);

        // Specifying a listener allows you to take an action before dismissing the dialog.
        // The dialog is automatically dismissed when a dialog button is clicked.
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final User user = new User(edtNewUsername.getText().toString(),
                        edtNewPassword.getText().toString(),
                        edtNewEmail.getText().toString());

                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child(user.getUserName()).exists())
                        {
                            Toast.makeText(MainActivity.this, "User already exists!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            users.child(user.getUserName()).setValue(user);
                            Toast.makeText(MainActivity.this, "User registration success", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }
}