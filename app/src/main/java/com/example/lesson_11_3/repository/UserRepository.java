package com.example.lesson_11_3.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.lesson_11_3.dao.UserDao;
import com.example.lesson_11_3.dao.UserDatabase;
import com.example.lesson_11_3.model.User;

import java.util.List;

public class UserRepository {
    private final UserDao userDao;

    public UserRepository(Application application) {
        userDao = UserDatabase.getInstance(application).userDao();
    }

    public void insert(User user) {
        UserDatabase.databaseWriteExecutor.execute(() -> userDao.insert(user));
    }

    public void update(User user) {
        UserDatabase.databaseWriteExecutor.execute(() -> userDao.update(user));
    }

    public LiveData<User> findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    public LiveData<List<User>> findAll() {
        return userDao.findAll();
    }

    public void deleteAll() {
        UserDatabase.databaseWriteExecutor.execute(userDao::deleteAll);
    }

    public void delete(User user) {
        UserDatabase.databaseWriteExecutor.execute(() -> userDao.delete(user));
    }

    public LiveData<User> findUserById(long id) {
        return userDao.findUserById(id);
    }
}
