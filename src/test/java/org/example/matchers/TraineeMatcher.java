package org.example.matchers;

import org.example.model.Trainee;
import org.mockito.ArgumentMatcher;

public class TraineeMatcher implements ArgumentMatcher<Trainee> {
    public TraineeMatcher(Trainee left) {
        this.left = left;
    }

    private Trainee left;

    @Override
    public boolean matches(Trainee right) {
        return left.getAddress().equals(right.getAddress()) &&
                left.getDateOfBirth().equals(right.getDateOfBirth()) &&
                left.getUser().equals(right.getUser());
    }
}
