package org.projects.sandbox.commons.serializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.projects.sandbox.model.exceptions.SerializerException;

@ApplicationScoped
public class SerializerImpl implements Serializer {

    private final ObjectReader reader;
    private final ObjectWriter writer;

    @Inject
    public SerializerImpl(ObjectMapper objectMapper) {
        this.reader = objectMapper.reader();
        this.writer = objectMapper.writer();
    }

    @Override
    public <T> T fromJson(String json,
                          Class<T> clazz) throws SerializerException {
        try {
            return reader.forType(clazz).readValue(json);
        } catch (Exception exception) {
            String detail = "Error deserializing " + clazz.getSimpleName() + " from JSON";
            throw new SerializerException(detail, exception);
        }
    }

    @Override
    public <T> T fromJson(String json,
                          TypeReference<T> typeReference) throws SerializerException {
        try {
            return reader.forType(typeReference).readValue(json);
        } catch (Exception exception) {
            String detail = "Error deserializing " + typeReference.getType().getTypeName() + " from JSON";
            throw new SerializerException(detail, exception);
        }
    }

    @Override
    public <T> String toJson(T object) throws SerializerException {
        try {
            return writer.writeValueAsString(object);
        } catch (Exception exception) {
            String detail = "Error serializing " + object.getClass().getName() + " to JSON";
            throw new SerializerException(detail, exception);
        }
    }
}