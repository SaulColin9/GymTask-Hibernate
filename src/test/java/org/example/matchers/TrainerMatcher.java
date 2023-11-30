package org.example.matchers;

import org.example.model.Trainer;
import org.mockito.ArgumentMatcher;

public class TrainerMatcher implements ArgumentMatcher<Trainer> {
    private Trainer left;

    public TrainerMatcher(Trainer left) {
        this.left = left;
    }

    @Override
    public boolean matches(Trainer right) {
        return left.getUser().equals(right.getUser()) && left.getSpecialization().getId() == right.getSpecialization().getId();
    }
}
