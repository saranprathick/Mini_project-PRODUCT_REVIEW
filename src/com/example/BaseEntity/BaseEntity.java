package com.example.BaseEntity;

public abstract class BaseEntity {
    private int id;

    public BaseEntity() {
    }

    public BaseEntity(int id) {
        this.id = id;
    }

    // Getter and Setter for id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // toString() method for common properties
    @Override
    public String toString() {
        return "Id=" + id;
    }
}
