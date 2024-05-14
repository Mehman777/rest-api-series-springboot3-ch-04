package com.tiagoamp.booksapi.util;

public class RESTResponse<T> {

    private Boolean success = false;
    private String code = "0";
    private String message = "";
    private T data = null;

    public RESTResponse() {
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
