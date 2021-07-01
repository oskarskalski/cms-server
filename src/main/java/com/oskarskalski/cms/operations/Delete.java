package com.oskarskalski.cms.operations;

public interface Delete<T> {
    void softByIdAndHeader(T id, String header);
    void hardByIdAndHeader(T id, String header);
}
