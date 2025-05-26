package com.example.atdd.model;

import java.util.UUID;

public class Todo {
    private String id;
    private String text;

    public Todo() {
        this.id = UUID.randomUUID().toString();
    }

    public Todo(String text) {
        this.id = UUID.randomUUID().toString();
        this.text = text;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
}