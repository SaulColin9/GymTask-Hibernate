package org.example.dao.jpa;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class JpaDaoTrainerImpl extends JpaDaoImpl<Trainer> {
    @Override
    public Optional<Trainer> get(int id) {
        return Optional.of(getEntityManager().find(Trainer.class, id));
    }

    @Override
    public List<Trainer> getAll() {
        return getEntityManager().createQuery("FROM Trainer", Trainer.class).getResultList();
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

    public List<Trainer> updateTraineeTrainersList(int traineeId, Trainer trainer) {
        executeTransaction(entityManager -> {
            Query query = entityManager.createQuery("UPDATE Training SET trainer = :newTrainer" +
                    " WHERE trainee.id = :trainee_id");
            query.setParameter("newTrainer", trainer);
            query.setParameter("trainee_id", traineeId);
            query.executeUpdate();
        });
        TypedQuery<Trainer> query = getEntityManager().createQuery("FROM Trainer trainer LEFT JOIN Training training" +
                " ON trainer.id = training.trainer.id" +
                " WHERE training.trainee.id = :trainee_id", Trainer.class);
        query.setParameter("trainee_id", traineeId);
        return query.getResultList();
    }

    public List<Trainee> getTrainerTraineeList(Trainer trainer) {
        TypedQuery<Trainee> query = getEntityManager().createQuery(
                "FROM Trainee trainee LEFT JOIN Training training" +
                        " ON trainee.id = training.trainee.id " +
                        "WHERE trainee.user.isActive = true AND (training.trainer.id = :trainer_id)",
                Trainee.class);
        query.setParameter("trainer_id", trainer.getId());

        return query.getResultList();
    }

}
