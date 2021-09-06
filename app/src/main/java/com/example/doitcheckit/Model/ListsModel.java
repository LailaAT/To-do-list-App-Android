package com.example.doitcheckit.Model;

public class ListsModel {
    private int id;
    //each list will have its own unique id which will be used in the main task data base
    private String listName;
    //name of the list
    private String color;
    //color will be a hex value but will be stored as a string


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
