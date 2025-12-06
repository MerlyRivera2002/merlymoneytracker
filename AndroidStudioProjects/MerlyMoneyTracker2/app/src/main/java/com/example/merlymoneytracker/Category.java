package com.example.merlymoneytracker;

public class Category {

    private int id;
    private String name;
    private String type;
    private String icon;

    public Category(int id, String name, String type, String icon) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.icon = icon;
    }

    public Category(String name, String type, String icon) {
        this.name = name;
        this.type = type;
        this.icon = icon;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getIcon() { return icon; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
    public void setIcon(String icon) { this.icon = icon; }
}
