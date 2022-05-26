package com.example.graphix.Models;

import java.util.ArrayList;

public class Database {
    private static ArrayList<User> users = new ArrayList<>();

    public static ArrayList<User>  getUsers(){
        return users;
    }

    public static User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static void removeUser(User user) {
        users.remove(user);
    }

    public static User getLoggedInUser(){
        for (User user: users){
            if(user.isLoggedIn())
                return user;
        }
        return null;
    }



}
