package com.example.demo.logging;

public enum LogMessages {
    HOME_PAGE_HIT("[INFO] Home page hit"),
    USER_LOGIN_SUCCESS("[INFO] User login successful"),
    USER_LOGIN_FAILURE("[ERROR] User login failed"),
    DATA_RETRIEVED("[INFO] Data retrieved successfully"),
    DATA_SAVE_FAILED("[ERROR] Data save failed"),
    PAGE_NOT_FOUND("[ERROR] Page not found"),
    GENERAL_ERROR("[ERROR] An unexpected error occurred");

    private final String message;

    LogMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
