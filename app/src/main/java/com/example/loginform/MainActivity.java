package com.example.loginform;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;
import android.app.Application;

public class MainActivity extends AppCompatActivity {
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private UserViewModel userViewModel ;
    private UserRepository userRepo;
    private UserDao userDao ;
    private List<User> users;

    Button login;
    EditText userName, password;
    TextView register;
    String name;
    Boolean logout = Boolean.FALSE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         register = findViewById(R.id.register);
         login =(Button) findViewById(R.id.loginIn);
         userName =(EditText) findViewById(R.id.userName);
         password =(EditText) findViewById(R.id.password);


//        userViewModel = new ViewModelProviders.of(MainActivity.this).get(UserViewModel.class);
//        Object application;
//        userViewModel =new ViewModelProvider.AndroidViewModelFactory(application);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getAllUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> user) {
                    users = user;
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View v) {
                int size = users.size();
                String temp="";
                Boolean valid=Boolean.FALSE;
                String currentUser=userName.getText().toString();
                String currentPassword = password.getText().toString();
                if(!users.isEmpty())
                for(int i=0;i<size;i++)
                {
//                    temp=temp+users.get(i).getUserId();
                    if(currentUser.equals(users.get(i).getUserId()) && currentPassword.equals(users.get(i).getPassword()))
                    {
                        name = users.get(i).getName();
                        valid = Boolean.TRUE;
                        break;
                    }
                }
                if(TextUtils.isEmpty(currentUser))
                    userName.setError("Enter User Id");
                else if(TextUtils.isEmpty(currentPassword))
                    password.setError("Enter Password");
                else if(valid) {
                    clearField();
                    Intent i = new Intent(MainActivity.this,welcomePage.class);
                    i.putExtra("name_key",name);
                    startActivity(i);
//                    Toast.makeText(getApplicationContext(),"Welcome "+name,Toast.LENGTH_SHORT).show();
                } else{
                    clearField();
                    makeToast("Invalid User");
                }

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,RegisForm.class);
                startActivity(i);
            }
        });
        Intent j= getIntent();
        logout = j.getBooleanExtra("logout_key",Boolean.FALSE);
        if(logout)
            makeToast("Sucessfully Logged Out");


    }
        public void makeToast(String msg){
            Toast toast =Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);

            TextView toastTextView = (TextView) toast.getView().findViewById(android.R.id.message);
            toastTextView.setTextColor(getResources().getColor(R.color.White));
            toastTextView.setTextSize(20);

            View toastView = toast.getView();
            toastView.setBackgroundColor(getResources().getColor(R.color.Black));

            toast.show();
        }
        public void clearField(){
            userName.setText("");
            password.setText("");
        }
}
