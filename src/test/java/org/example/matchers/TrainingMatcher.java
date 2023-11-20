package org.example.matchers;

import org.example.model.Training;
import org.mockito.ArgumentMatcher;

public class TrainingMatcher implements ArgumentMatcher<Training> {
    private Training left;

    public TrainingMatcher(Training left) {
        this.left = left;
    }

    @Override
    public boolean matches(Training right) {

        return left.getTrainingName().equals(right.getTrainingName()) &&
                left.getTrainingDate().equals(right.getTrainingDate()) &&
                left.getTrainingDuration() == right.getTrainingDuration() &&
                left.getTrainee().equals(right.getTrainee()) &&
                left.getTrainer().equals(right.getTrainer()) &&
                left.getTrainingType().equals(right.getTrainingType());
    }
}
