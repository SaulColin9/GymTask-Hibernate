package org.example.service.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Validator<T> {
    private static final Logger logger = LoggerFactory.getLogger(Validator.class);
    private final Class<T> tClass;
    private static final String NULL_ENTITY_ID_MSG = "Provided id of %s for %s entities was not found";
    private static final String NULL_ENTITY_USERNAME_MSG = "Provided username of %s for %s entities was not found";
    private static final String NULL_FIELD = "%s argument cannot be null";
    private static final String NEGATIVE_FIELD = "%s argument cannot be lower or equal to zero";
    private static final String NULL_ENTITY = "%s entity was not found";

    public Validator(Class<T> tClass) {
        this.tClass = tClass;
    }

    public void validateEntityNotNull(int id, T entity) {
        if (entity == null) {
            String errorMsg = String.format(NULL_ENTITY_ID_MSG, id, tClass.getSimpleName());
            logger.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
    }

    public void validateEntityNotNull(String username, T entity) {
        if (entity == null) {
            String errorMsg = String.format(NULL_ENTITY_USERNAME_MSG, username, tClass.getSimpleName());
            logger.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
    }

    public void validateEntitiesNotNull(Map<String, Object> entities) {
        for (Map.Entry<String, Object> entry : entities.entrySet()) {
            if (entry.getValue() == null) {
                String errorMsg = String.format(NULL_ENTITY, entry.getKey());
                logger.error(errorMsg);
                throw new IllegalArgumentException(errorMsg);
            }

        }
    }

    public void validateFieldsNotNull(Map<String, Object> params) {
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (entry.getValue() == null) {
                String errorMsg = String.format(NULL_FIELD, entry.getKey());
                logger.error(errorMsg);
                throw new IllegalArgumentException(errorMsg);
            }
        }
    }

    public void validatePositiveField(String fieldName, int field) {
        if (field <= 0) {
            String errorMsg = String.format(NEGATIVE_FIELD, fieldName);
            logger.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
    }
}
