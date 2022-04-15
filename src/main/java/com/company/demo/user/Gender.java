package com.company.demo.user;

public enum Gender {
    FEMALE("F"),
    MALE("M"),
    OTHER("O");

    private String code;

    Gender(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
