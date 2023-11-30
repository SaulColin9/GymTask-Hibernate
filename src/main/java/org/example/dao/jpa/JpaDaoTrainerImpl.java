package org.example.dao.jpa;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.example.model.Trainee;
import org.example.model.Trainer;

import java.util.List;
import java.util.Optional;

public class JpaDaoTrainerImpl extends JpaDaoImpl<Trainer> {
    @Override
    public Optional<Trainer> get(int id) {
        try {
            return Optional.of(getEntityManager().find(Trainer.class, id));
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Trainer> getAll() {
        Query query = getEntityManager().createQuery("FROM Trainer");
        return query.getResultList();
    }

    @Override
    public Trainer save(Trainer trainer) {
        executeTransaction(entityManager -> entityManager.persist(trainer));
        return trainer;
    }

    @Override
    public Trainer update(int id, Trainer trainer) {
        executeTransaction(entityManager -> entityManager.merge(trainer));
        return trainer;
    }

    @Override
    public Optional<Trainer> delete(int id) {
        Optional<Trainer> foundTrainer = get(id);
        foundTrainer.ifPresent(trainer -> executeTransaction(entityManager -> entityManager.remove(trainer)));
        return foundTrainer;
    }

    public Optional<Trainer> getByUsername(String username) {
        try {
            Query query = getEntityManager().createQuery("FROM Trainer t WHERE t.user.username = :username");
            query.setParameter("username", username);
            return Optional.of((Trainer) query.getSingleResult());

        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<Trainer> updateTraineeTrainersList(int trainee_id, Trainer trainer) {
        executeTransaction(entityManager -> {
            Query query = entityManager.createQuery("UPDATE Training SET trainer = :newTrainer" +
                    " WHERE trainee.id = :trainee_id");
            query.setParameter("newTrainer", trainer);
            query.setParameter("trainee_id", trainee_id);
            query.executeUpdate();
        });
        Query query = getEntityManager().createQuery("FROM Trainer trainer LEFT JOIN Training training" +
                " ON trainer.id = training.trainer.id" +
                " WHERE training.trainee.id = :trainee_id");
        query.setParameter("trainee_id", trainee_id);
        return query.getResultList();
    }

}
