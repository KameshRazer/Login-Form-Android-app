package com.example.loginform;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.concurrent.atomic.AtomicReference;

@Entity(tableName = "users")
public class User {

    @ColumnInfo(name="name")
    private String name;

    @NonNull
    @PrimaryKey
    @ColumnInfo(name="userid")
    private String userId;

    @ColumnInfo(name = "password")
    private String password;
    @Ignore
    public User(){}

    public User(String name,String userId,String password){
        this.name=name;
        this.userId=userId;
        this.password=password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
