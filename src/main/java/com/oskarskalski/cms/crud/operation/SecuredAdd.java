package com.oskarskalski.cms.crud.operation;

public interface SecuredAdd<T> {
    void addByObjectAndAuthorizationHeader(T t, String header);
}
