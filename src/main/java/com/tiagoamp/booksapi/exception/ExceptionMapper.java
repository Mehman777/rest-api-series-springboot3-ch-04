package com.tiagoamp.booksapi.exception;


public class ExceptionMapper {
 
    public static BusinessException map(Exception exc) {
        BusinessException mappedExc = new BusinessException(BusinessException.BusinessError.GENERIC_ERROR, exc);
        if (exc instanceof BusinessException) {
            mappedExc = (BusinessException) exc;
        } else if (exc instanceof RepositoryExcepton) {
            mappedExc = new BusinessException(BusinessException.BusinessError.REPOSITORY_ERROR, exc);
        }
        return mappedExc;
    }
}
