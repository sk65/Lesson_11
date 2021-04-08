package com.example.lesson_11_3.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String userName;
    private String userLastName;
    private String email;
    private String password;
    private String imgUri;


    public User(String imgUri, String userName, String userLastName, String email, String password) {
        this.userName = userName;
        this.userLastName = userLastName;
        this.email = email;
        this.password = password;
        this.imgUri = imgUri;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
