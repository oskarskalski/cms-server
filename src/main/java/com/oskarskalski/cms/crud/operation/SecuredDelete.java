package com.oskarskalski.cms.crud.operation;

public interface SecuredDelete<T> {
    void softDeleteByIdAndAuthorizationHeader(T t, String header);
    void hardDeleteByIdAndAuthorizationHeader(T t, String header);
}
