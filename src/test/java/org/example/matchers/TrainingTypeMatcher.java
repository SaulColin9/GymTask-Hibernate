package org.example.matchers;

import org.example.model.TrainingType;
import org.mockito.ArgumentMatcher;

public class TrainingTypeMatcher implements ArgumentMatcher<TrainingType> {
    private final TrainingType left;

    public TrainingTypeMatcher(TrainingType left) {
        this.left = left;
    }

    @Override
    public boolean matches(TrainingType right) {
        return left.getTrainingTypeName().equals(right.getTrainingTypeName());
    }
}
