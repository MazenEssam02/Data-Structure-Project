package com.myapp.datastructureproject.networkAnalysis;

import java.util.ArrayList;

public class User {
    String ID;
    String name;
    ArrayList<Post> Posts = new ArrayList<Post>();
    ArrayList<String> Followers = new ArrayList<String>();

    @Override
    public String toString() {
        return "user: " + this.name + " - " +"ID: " + this.getID();
    }


    public void print() {
        System.out.println("User ID is: " + ID);
        System.out.println("User Name is " + name);
        System.out.println("Number of followers: " + Followers);

    }

    public void printdata() {
        System.out.println("User ID is: " + ID);
        System.out.println("User Name is " + name);
    }

    public String getID() {
        return ID;
    }
}
