package com.example.batchwebmain.utils;

public interface MessageResolver {

    String messageCode();

    default String defaultMessage() {
        return "Message not found for code: '" + this.messageCode() + "'";
    }
}
