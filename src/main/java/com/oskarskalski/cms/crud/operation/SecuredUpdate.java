package com.oskarskalski.cms.crud.operation;

public interface SecuredUpdate<T> {
    void updateByObjectAndAuthorizationHeader(T t, String header);
}
