package com.ulk.model;

import com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonIgnore;

public class TodoUser {
    private int userId;
    private int id;
    private String title;
    private boolean completed;
    @JsonIgnore
    private String status;


    @Override
    public String toString() {
        return "TodoUser{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", completed=" + completed +
                ", status='" + status + '\'' +
                '}';
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TodoUser() {
    }

    public TodoUser(int userId, String title, boolean completed) {

        this.userId = userId;
        this.title = title;
        this.completed = completed;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TodoUser todoUser = (TodoUser) o;

        if (userId != todoUser.userId) return false;
        if (completed != todoUser.completed) return false;
        return title != null ? title.equals(todoUser.title) : todoUser.title == null;

    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (completed ? 1 : 0);
        return result;
    }
}
