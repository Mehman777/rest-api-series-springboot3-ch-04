package com.tiagoamp.booksapi.util;

public class Global {

    public static final String OPERATION_COMPLETED_SUCCESSFULLY = "The operation completed successfully";
    public static final String AUTH_TOKEN_HEADER_NAME = "X-Authorization";
    public static final int MAX_AGE_COOKIE = 365 * 24 * 60 * 60; // 1 year
    public static final long TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 10; // 10 day
    public static final long CONFIRM_CODE_EXPIRATION_TIME = 1000L * 60 * 60 * 24; // 1 day
    public static final String NOTEBOOK_PAGE_EXTENTION  = ".png";
    public static final String ZIP_EXTENTION  = ".zip";
    public static final String MULTIMEDIA_ANDROID_ZIP_NAME = "model.zip";
    public static final String MULTIMEDIA_WIN64_ZIP_NAME = "model_win64.zip";

}
