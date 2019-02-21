package com.osetrova.documentservice.converter;

public interface Converter<T, R> {

    R convert(T object);
}
