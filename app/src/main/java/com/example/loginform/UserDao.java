package com.example.loginform;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);

    @Query("DELETE FROM users")
    void deleteAll();

    @Query("Select * from users ")
    LiveData<List<User>> getAllUser();

    @Query("Select * from users where userid = 'razer' LIMIT 1 ")
    public LiveData<User> getUserById();

//    @Query("Select * from users where userid =:user")
//    User getUserId(user);
}
