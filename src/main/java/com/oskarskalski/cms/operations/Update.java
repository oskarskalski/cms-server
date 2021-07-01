package com.oskarskalski.cms.operations;

public interface Update<T> {
    void updateByDtoAndHeader(T t, String header);
}
