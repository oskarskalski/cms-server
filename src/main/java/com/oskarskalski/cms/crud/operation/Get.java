package com.oskarskalski.cms.crud.operation;

public interface Get<T, K> {
    T getById(K k);
}
