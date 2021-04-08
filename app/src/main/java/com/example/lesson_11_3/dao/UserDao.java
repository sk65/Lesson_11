package com.example.lesson_11_3.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.lesson_11_3.model.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM user")
    void deleteAll();

    @Query("SELECT * FROM user")
    LiveData<List<User>> findAll();

    @Query("SELECT * FROM user WHERE email=:email")
    LiveData<User> findUserByEmail(String email);

    @Query("SELECT * FROM user WHERE id=:id")
    LiveData<User> findUserById(long id);
}
