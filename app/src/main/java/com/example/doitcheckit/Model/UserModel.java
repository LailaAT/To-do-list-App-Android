package com.example.doitcheckit.Model;

public class UserModel {
    private int id, totalCoins;
    //the total number of coins a user has
    private String username, password;
    //the username and password for the user


    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public int getTotalCoins() { return totalCoins; }

    public void setTotalCoins(int totalCoins) { this.totalCoins = totalCoins; }
}
