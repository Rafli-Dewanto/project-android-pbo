package com.sugiartha.juniorandroid.model;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Auth {
    private String fullname, username, password, gender;

    @NonNull
    @Override
    public String toString() {
        return "Auth{" +
                "fullname='" + fullname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auth auth = (Auth) o;
        return Objects.equals(fullname, auth.fullname) && Objects.equals(username, auth.username) && Objects.equals(password, auth.password) && Objects.equals(gender, auth.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullname, username, password, gender);
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Auth(String fullname, String username, String password, String gender) {
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.gender = gender;
    }

    public Auth(){}
}
