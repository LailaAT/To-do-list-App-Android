package com.example.doitcheckit.Model;

public class StoreItem {
    private int id, status, price;
    // the status will be whether the item has been bough or not
    private String name;
    //the name of the item


    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getPrice() { return price; }

    public void setPrice(int price) { this.price = price; }

    public int getStatus() { return status; }

    public void setStatus(int status) { this.status = status; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }
}
