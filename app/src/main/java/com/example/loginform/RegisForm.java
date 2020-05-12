package com.example.loginform;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
import android.content.Intent;
import android.database.Observable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class RegisForm extends AppCompatActivity implements LifecycleOwner {
    private UserViewModel userViewModel;
    private User user;
    private List<User> users ;
    private volatile UserRoomDatabase db;
//    private UserDao userDao = db.userDao() ;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis_form);

        Button register = (Button)findViewById(R.id.register);
        EditText name = (EditText)findViewById(R.id.name);
        EditText userName = (EditText)findViewById(R.id.userName);
        EditText password = (EditText)findViewById(R.id.password);
        TextView login = findViewById(R.id.login);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getAllUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> user) {
                users = user;
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = name.getText().toString();
                String newUserId = userName.getText().toString();
                String newPassword =password.getText().toString();
                Boolean userExist= Boolean.FALSE;
                User newUser = new User();
                newUser.setName(newName);
                newUser.setUserId(newUserId);
                newUser.setPassword(newPassword);
                System.out.println("Name : "+userName.getText().toString());

//                AsyncTask.execute(new Runnable() {
//                    @Override
//                    public void run() {
//                            db.databaseWriteExecutor.execute(()->{
//                                checkUser.set(userViewModel.getUserById(userName.getText().toString()));
//                            });
//                    }
//                });
////                User checkUser=userViewModel.getUserById(checkUse);
//                System.out.println("User :"+checkUser.get().getName());
                if(!users.isEmpty())
                    for(User user : users)
                    {
                        if(user.getUserId().equals(newUserId))
                        {
                            userExist = Boolean.TRUE;
                            break;
                        }
                    }
                if(TextUtils.isEmpty(newName))
                    name.setError("Please Enter Name");
                else if(TextUtils.isEmpty(newUserId))
                    userName.setError("Please Enter User Id");
                else if(TextUtils.isEmpty(newPassword))
                    password.setError("Please Enter Password");
                else if(userExist)
                {
                    makeToast("User Id already exits");
                    userName.setError("User Id already exits");
                    userName.setText("");
                    userName.setFocusable(Boolean.TRUE);
                }
                else
                {
                    userViewModel.insert(newUser);
                    makeToast("User Registration Sucess");
                    name.setText("");
                    userName.setText("");
                    password.setText("");

                }

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisForm.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
    public void makeToast(String msg){
        Toast toast = Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
        TextView toastTextView = (TextView) toast.getView().findViewById(android.R.id.message);
        toastTextView.setTextColor(getResources().getColor(R.color.White));
        toastTextView.setTextSize(20);

        View toastView = toast.getView();
        toastView.setBackgroundColor(getResources().getColor(R.color.Black));

        toast.show();
    }
}
