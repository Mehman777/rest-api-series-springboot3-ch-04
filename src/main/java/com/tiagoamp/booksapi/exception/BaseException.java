package com.tiagoamp.booksapi.exception;

public class BaseException extends Exception {

    private static final long serialVersionUID = 1L;

    private IError error;

    public BaseException(IError error) {
        super(error.getMessage());
        this.error = error;
    }

    public BaseException(IError error, String message) {
        super(error.getMessage() + " \n" + message);
        this.error = error;
    }

    public BaseException(IError error, Throwable cause) {
        super(error.getMessage(), cause);
        this.error = error;
    }

    public BaseException(String message) {
        super(message);
    }

    public void setError(IError error) {
        this.error = error;
    }

    public IError getError() {
        return this.error;
    }

    public String getErrorCode() {
        return this.error.getCode();
    }

    public String getErrorMessage() {
        return this.error.getMessage();
    }

    @Override
    public String getMessage() {
        StringBuilder msg = new StringBuilder();
        msg.append(super.getMessage());

        if (getCause() != null) {
            msg.append(getCause().getMessage());
        }

        return msg.toString();
    }
}
