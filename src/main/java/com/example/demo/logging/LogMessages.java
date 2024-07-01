package com.example.demo.logging;

public enum LogMessages {
    HOME_PAGE_HIT("[INFO] Home page hit"),
    USER_LOGIN_SUCCESS("[INFO] User login successful"),
    USER_LOGIN_FAILURE("[ERROR] User login failed"),
    DATA_RETRIEVED("[INFO] Data retrieved successfully"),
    DATA_SAVE_FAILED("[ERROR] Data save failed"),
    DATA_SAVED("[INFO] Data saved successfully"),
    PAGE_NOT_FOUND("[ERROR] Page not found"),
    GENERAL_ERROR("[ERROR] An unexpected error occurred"),
    POST_ADDED("[INFO] Post added successfully"),
    ACC_CREATED("[INFO] Account created successfully"),
    ACC_FA_CREATED("[INFO] Account creation failed"),
    USER_NOT_FOUND("[ERROR] User not found");

    private final String message;

    LogMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
