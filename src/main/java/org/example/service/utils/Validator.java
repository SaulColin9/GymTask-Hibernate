package org.example.service.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Validator {
    private static final Logger logger = LoggerFactory.getLogger(Validator.class);
    private static final String NULL_FIELD = "%s argument cannot be null";
    private static final String NEGATIVE_FIELD = "%s argument cannot be lower or equal to zero";
    private static final String NULL_ENTITY = "%s entity was not found";

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
