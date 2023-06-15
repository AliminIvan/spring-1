package com.javarush.alimin.controller;

import com.javarush.alimin.domain.Status;

@SuppressWarnings("unused")
public class TaskDTO {
    private String description;

    private Status status;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
