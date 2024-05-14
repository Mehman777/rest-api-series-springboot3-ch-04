package com.tiagoamp.booksapi.exception;


public class ErrorUtil {
    private String arguments;
    private String field;
    private String defaultMessage;

    public String getArguments() {
        return arguments;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    @Override
    public String toString() {
        return "ErrorUtil{" +
                "arguments='" + arguments + '\'' +
                ", field='" + field + '\'' +
                ", defaultMessage='" + defaultMessage + '\'' +
                '}';
    }
}
