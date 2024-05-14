package com.tiagoamp.booksapi.exception;

import com.tiagoamp.booksapi.util.RESTResponse;

public class ExceptionUtil {
     
    
    public static RESTResponse mapToResponse(RESTResponse response, BusinessException exc) {
        response.setSuccess(Boolean.FALSE);
        response.setCode(exc.getErrorCode());
        response.setMessage(exc.getErrorMessage());
        
        return response;
    }
}
