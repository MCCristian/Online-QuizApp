package com.example.licenceapp.Model;

public class Category {

    private String Name;
    private String Image;

    public Category()
    {

    }

    public Category(String name, String image) {
        this.Name = name;
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}

