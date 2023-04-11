package ru.stqa.pft.mantis.model;

public class Project {

    private int id;
    private String name;


    // GETTERS
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    // SETTERS
    public Project withId(int id) {
        this.id = id;
        return this;
    }

    public Project withName(String name) {
        this.name = name;
        return this;
    }
}
