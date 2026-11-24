package com.example.todo.vo;


import jakarta.persistence.Column;
import jakarta.persistence.Id;



public class Todo {
	@Id
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private boolean completed;

    public Todo() {}

    public Todo(int id, String title, boolean completed) {
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

  

	public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

}
