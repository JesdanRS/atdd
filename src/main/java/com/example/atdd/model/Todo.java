package com.example.atdd.model;

public class Todo {
    private String text;

    public Todo() {}

    public Todo(String text) {
        this.text = text;
    }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
}