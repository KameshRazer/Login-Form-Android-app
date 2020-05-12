package com.example.loginform;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    String name="ngp";
    private UserRepository userRepo;
    private LiveData<List<User>> userData;
    private LiveData<User> user;

    public UserViewModel(@NonNull Application application){
        super(application);
        userRepo=new UserRepository(application);
        userData=userRepo.getAllUser();
    }


    LiveData<List<User>> getAllUser(){return userData;}


    public void insert(User user){
        userRepo.insertUserData(user);
    }

    public LiveData<User> getUserById(){
        user = userRepo.getUserById();
        return user;
    }
}
