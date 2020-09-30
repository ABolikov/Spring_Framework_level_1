package ru.bolikov.exception;

import java.util.function.Supplier;

public class NotFoundException extends RuntimeException implements Supplier<NotFoundException> {
    private String nameEntity;

    public NotFoundException(String message, String nameEntity) {
        super(message);
        this.nameEntity = nameEntity;
    }

    public String getNameEntity() {
        return nameEntity;
    }

    public void setNameEntity(String nameEntity) {
        this.nameEntity = nameEntity;
    }

    @Override
    public NotFoundException get() {
        return this;
    }
}
