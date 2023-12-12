package org.example.dao.jpa;

import org.example.model.TrainingType;

import java.util.List;
import java.util.Optional;

public class JpaDaoTrainingTypeImpl extends JpaDaoImpl<TrainingType> {
    @Override
    public Optional<TrainingType> get(int id) {
        try {
            return Optional.of(getEntityManager().find(TrainingType.class, id));
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<TrainingType> getAll() {
        return getEntityManager().createQuery("FROM TrainingType", TrainingType.class).getResultList();
    }

    @Override
    public TrainingType save(TrainingType trainingType) {
        executeTransaction(entityManager -> entityManager.persist(trainingType));
        return trainingType;
    }

    @Override
    public TrainingType update(int id, TrainingType trainingType) {
        executeTransaction(entityManager -> entityManager.merge(trainingType));
        return trainingType;
    }

    @Override
    public Optional<TrainingType> delete(int id) {
        Optional<TrainingType> foundTrainingType = get(id);
        foundTrainingType.ifPresent(trainingType -> executeTransaction(entityManager -> entityManager.remove(trainingType)));
        return foundTrainingType;
    }
}
