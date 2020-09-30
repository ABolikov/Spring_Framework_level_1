package ru.bolikov.exception.rest;

import java.util.Date;

public class ProductErrorResponse {
    private int status;
    private String message;
    private Date date;

    public ProductErrorResponse() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setTimestamp(long timestamp) {
        this.date = new Date(timestamp);
    }
}
