package org.example.dao.jpa;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.example.model.Trainee;
import org.example.model.Trainer;

import java.util.List;
import java.util.Optional;

public class JpaDaoTraineeImpl extends JpaDaoImpl<Trainee> {
    private static final String TRAINEE_ID_PARAM = "trainee_id";
    private static final String USERNAME_PARAM = "username";

    @Override
    public Optional<Trainee> get(int id) {
        return Optional.of(getEntityManager().find(Trainee.class, id));
    }

    @Override
    public List<Trainee> getAll() {
        return getEntityManager().createQuery("FROM Trainee", Trainee.class).getResultList();
    }

    @Override
    public Trainee save(Trainee trainee) {
        executeTransaction(entityManager -> entityManager.persist(trainee));
        return trainee;
    }

    @Override
    public Trainee update(int id, Trainee trainee) {
        executeTransaction(entityManager -> entityManager.merge(trainee));
        return trainee;
    }

    @Override
    public Optional<Trainee> delete(int id) {
        Optional<Trainee> foundTrainee = get(id);

        foundTrainee.ifPresent(this::deleteTrainee);

        return foundTrainee;
    }

    public Optional<Trainee> deleteByUsername(String username) {
        Query query = getEntityManager().createQuery("FROM Trainee t WHERE t.user.username = :username");
        query.setParameter(USERNAME_PARAM, username);
        Optional<Trainee> foundTrainee = Optional.of((Trainee) query.getSingleResult());

        foundTrainee.ifPresent(this::deleteTrainee);

        return foundTrainee;
    }

    public Optional<Trainee> getByUsername(String username) {
        try {
            TypedQuery<Trainee> query = getEntityManager().createQuery("FROM Trainee t WHERE t.user.username = :username", Trainee.class);
            query.setParameter(USERNAME_PARAM, username);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }


    public List<Trainer> getNotAssignedOnTraineeTrainersList(Trainee trainee) {
        TypedQuery<Trainer> query = getEntityManager().createQuery(
                "FROM Trainer trainer LEFT JOIN Training training" +
                        " ON trainer.id = training.trainer.id " +
                        "WHERE trainer.user.isActive = true AND (training.trainee.id != :trainee_id OR training.trainee.id IS NULL)",
                Trainer.class);
        query.setParameter(TRAINEE_ID_PARAM, trainee.getId());

        return query.getResultList();
    }

    private void deleteTrainee(Trainee trainee) {
        executeTransaction(entityManager -> {
            Query trainingDeleteQuery = entityManager.createQuery("DELETE Training tr WHERE tr.trainee.id = :trainee_id");
            trainingDeleteQuery.setParameter(TRAINEE_ID_PARAM, trainee.getId());
            trainingDeleteQuery.executeUpdate();
        });

        executeTransaction(entityManager -> entityManager.remove(trainee));
    }

}
