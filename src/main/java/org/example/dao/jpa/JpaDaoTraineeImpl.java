package org.example.dao.jpa;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.example.model.Trainee;

import java.util.List;
import java.util.Optional;

public class JpaDaoTraineeImpl extends JpaDaoImpl<Trainee> {
    @Override
    public Optional<Trainee> get(int id) {
        try {
            return Optional.of(getEntityManager().find(Trainee.class, id));
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Trainee> getAll() {
        return getEntityManager().createQuery("FROM Trainee").getResultList();
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
        foundTrainee.ifPresent(trainee -> {
            executeTransaction(entityManager -> {
                Query trainingDeleteQuery = entityManager.createQuery("DELETE Training tr WHERE tr.trainee.id = :trainee_id");
                trainingDeleteQuery.setParameter("trainee_id", trainee.getId());
                trainingDeleteQuery.executeUpdate();
            });
            executeTransaction(entityManager -> entityManager.remove(trainee));

        });
        return foundTrainee;
    }

    public Optional<Trainee> getByUsername(String username) {
        try {
            Query query = getEntityManager().createQuery("FROM Trainee t WHERE t.user.username = :username");
            query.setParameter("username", username);
            Optional<Trainee> foundTrainee = Optional.of((Trainee) query.getSingleResult());

            return foundTrainee;
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<Trainee> deleteByUsername(String username) {
        Query query = getEntityManager().createQuery("FROM Trainee t WHERE t.user.username = :username");
        query.setParameter("username", username);
        Optional<Trainee> foundTrainee = Optional.of((Trainee) query.getSingleResult());

        foundTrainee.ifPresent(trainee -> {
                    executeTransaction(entityManager -> {
                        Query trainingDeleteQuery = entityManager.createQuery("DELETE Training tr WHERE tr.trainee.id = :trainee_id");
                        trainingDeleteQuery.setParameter("trainee_id", trainee.getId());
                        trainingDeleteQuery.executeUpdate();
                    });
                    executeTransaction(entityManager -> entityManager.remove(trainee));
                }
        );

        return foundTrainee;
    }
}
