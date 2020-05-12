package com.example.loginform;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Ignore;
import androidx.room.RoomDatabase;

import java.util.List;

public class UserRepository {

    private UserDao userDao;
    private UserRoomDatabase db;
    private LiveData<List<User>> userData;
    private LiveData<User> user;

    public  UserRepository(Application application){
        db = UserRoomDatabase.getDatabase(application);
        userDao =db.userDao();
        userData =userDao.getAllUser();
    }

    public LiveData<List<User>> getAllUser(){return userData;}

    public LiveData<User> getUserById(){
        UserRoomDatabase.databaseWriteExecutor.execute(()->{
          user =userDao.getUserById();
        });
        return user;
    }

   public void insertUserData(User user){
//        new LoginInsertion(userDao).execute(user);
       UserRoomDatabase.databaseWriteExecutor.execute(()->{
            userDao.insert(user);
       });
   }

   private static class LoginInsertion extends AsyncTask<User, Void, Void> {
       private UserDao userDao;

       private LoginInsertion(UserDao userDao) {
           this.userDao = userDao;
       }

       @Override
       protected Void doInBackground(User... data) {
           userDao.insert(data[0]);
           return null;
       }
   }
//    void insert(final User user){
//        UserRoomDatabase.databaseWriteExecutor.execute(()-> {
//                userDao.insert(user);
//        });
//    }
}
