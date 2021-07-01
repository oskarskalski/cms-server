package com.oskarskalski.cms.operations;

public interface Get<T, K> {
    K getById(T t);
}
