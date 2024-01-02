package edu.birzeit.saeedmosaffer.newsaggregator;

import com.google.gson.Gson;

public class Account {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public Account(){}

    public Account(String firstName, String lastName, String email, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String toString(){
        return firstName + " " + lastName + " " + email + " " + password;
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }

    public String getInitials(){
        return firstName.charAt(0) + "" + lastName.charAt(0);
    }

    // toJson method to convert Account object to JSON string
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    // fromJson method to convert JSON string to Account object
    public static Account fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Account.class);
    }

}
