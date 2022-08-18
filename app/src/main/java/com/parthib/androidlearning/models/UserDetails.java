package com.parthib.androidlearning.models;

public class UserDetails {
    private String name; // final - keyword in java to mark a var as constant
    private String userId;
    private String mobile;
    private String email;
    private String imageUrl;

    public UserDetails(){

    }
    // alt+insert for constructor
    public UserDetails(String name, String userId, String mobile, String email, String imageUrl) {
        this.name = name;
        this.userId = userId;
        this.mobile = mobile;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    // getters - methods that are dedicated to the variables through which we can access or retrieve the data


    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
