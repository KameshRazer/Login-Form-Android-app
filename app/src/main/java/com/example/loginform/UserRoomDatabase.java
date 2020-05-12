package com.example.loginform;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(version = 1,exportSchema = false,entities = {User.class})
public abstract class UserRoomDatabase extends RoomDatabase {

    abstract  UserDao userDao();
    private static final String Db_name="user";
    private static final int NUMBER_OF_THREADS =4;
    private static  volatile UserRoomDatabase instance;
    static final ExecutorService databaseWriteExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static UserRoomDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (UserRoomDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            UserRoomDatabase.class, Db_name)
                            .fallbackToDestructiveMigration()
//                            .addCallback(UserRoomDatabaseCallback)
                            .build();
                }
            }
        }

        return instance;
    }

    private static RoomDatabase.Callback UserRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            databaseWriteExecutor.execute(()->{
                UserDao dao = instance.userDao();
                dao.deleteAll();
//                User user = new User("kamesh","razer","1234");
//                dao.insert(user);
//                user.setName("Kameshwar");
//                user.setUserId("ghost");
//                user.setPassword("1234");
//                dao.insert(user);
            });
        }
    };

}

//@Database(version = 1,exportSchema = false,entities = {User.class})
//
//public abstract class UserRoomDatabase extends RoomDatabase {
//    private static UserRoomDatabase instance;
//
//    public static synchronized UserRoomDatabase getDatabase(final Context context){
//        if(instance == null){
//            instance=Room.databaseBuilder(context.getApplicationContext(),UserRoomDatabase.class,"user")
//                    .fallbackToDestructiveMigration()
//                    .build();
//        }
//        return instance;
//    }
//
//
//    public abstract  UserDao userDao();
//}