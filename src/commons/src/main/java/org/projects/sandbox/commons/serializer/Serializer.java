package org.projects.sandbox.commons.serializer;

import com.fasterxml.jackson.core.type.TypeReference;
import org.projects.sandbox.model.exceptions.SerializerException;


public interface Serializer {

    <T> T fromJson(String json,
                   Class<T> clazz) throws SerializerException;

    <T> T fromJson(String json,
                   TypeReference<T> typeReference) throws SerializerException;


    <T> String toJson(T object) throws SerializerException;
}
