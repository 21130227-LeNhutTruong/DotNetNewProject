package com.example.app2_use_firebase.model;

public class User {
    private String _id;
    private String email;
    private String pass;
    private String username;
    private String name;
    private int age;
    private String avatar;
    private int sex;
    private int role;

    public User() {
    }

    public User(String _id, String email, String pass, String username, String name, int age, String avatar, int sex, int role) {
        this._id = _id;
        this.email = email;
        this.pass = pass;
        this.username = username;
        this.name = name;
        this.age = age;
        this.avatar = avatar;
        this.sex = sex;
        this.role = role;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "_id='" + _id + '\'' +
                ", email='" + email + '\'' +
                ", pass='" + pass + '\'' +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", avatar='" + avatar + '\'' +
                ", sex=" + sex +
                ", role=" + role +
                '}';
    }
}
