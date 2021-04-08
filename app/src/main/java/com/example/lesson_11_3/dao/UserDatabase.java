package com.example.lesson_11_3.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.lesson_11_3.model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = User.class, version = 1)
public abstract class UserDatabase extends RoomDatabase {
    private static final String DB_NAME = "user_database";
    private static UserDatabase instance;
    private static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract UserDao userDao();

    public static synchronized UserDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    UserDatabase.class, DB_NAME)
                    //    .addCallback(sRoomDatabaseCallback)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

//    private static final Callback sRoomDatabaseCallback = new Callback() {
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//            Log.i("dev", "sRoomDatabaseCallback");
//            databaseWriteExecutor.execute(() -> {
//                UserDao dao = instance.userDao();
//                User user = new User("Jack",
//                        "Black",
//                        "sk65@mail.ru",
//                        "Ender1Wiggin");
//                dao.insert(user);
//            });
//        }
//    };
}
