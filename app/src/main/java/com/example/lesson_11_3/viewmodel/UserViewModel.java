package com.example.lesson_11_3.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.lesson_11_3.model.User;
import com.example.lesson_11_3.repository.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private final UserRepository userRepository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public void insert(User user) {
        userRepository.insert(user);
    }

    public void update(User user) {
        userRepository.update(user);
    }

    public LiveData<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public LiveData<User> findUserById(long id) {
        return userRepository.findUserById(id);
    }

    public LiveData<List<User>> findAll() {
        return userRepository.findAll();
    }

}
