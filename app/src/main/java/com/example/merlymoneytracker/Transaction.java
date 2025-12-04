package com.example.merlymoneytracker;

public class Transaction {

    private int id;
    private String type;
    private double amount;
    private String category;
    private String description;
    private String date;
    private String paymentMethod;

    // Constructor sin ID (para insertar)
    public Transaction(String type, double amount, String category, String description, String date, String paymentMethod) {
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.date = date;
        this.paymentMethod = paymentMethod;
    }

    // Constructor con ID (para mostrar, actualizar, etc.)
    public Transaction(int id, String type, double amount, String category, String description, String date, String paymentMethod) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.date = date;
        this.paymentMethod = paymentMethod;
    }

    // Getters y setters
    public int getId() { return id; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public String getDate() { return date; }
    public String getPaymentMethod() { return paymentMethod; }

    public void setId(int id) { this.id = id; }
    public void setType(String type) { this.type = type; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setCategory(String category) { this.category = category; }
    public void setDescription(String description) { this.description = description; }
    public void setDate(String date) { this.date = date; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
}


