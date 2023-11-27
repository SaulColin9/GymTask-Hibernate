package org.example.dao.jpa;

import jakarta.persistence.Query;
import org.example.configuration.Storage;
import org.example.model.Trainer;

import java.util.List;
import java.util.Optional;

public class JpaDaoTrainerImpl extends JpaDaoImpl<Trainer> {
    @Override
    public Optional<Trainer> get(int id) {
        return Optional.of(getEntityManager().find(Trainer.class, id));
    }

    @Override
    public List<Trainer> getAll() {
        return getEntityManager().createQuery("FROM Trainer").getResultList();
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
        Query query = getEntityManager().createQuery("FROM Trainer t WHERE t.user.username = :username");
        query.setParameter("username", username);
        Optional<Trainer> foundTrainer = Optional.of((Trainer) query.getSingleResult());

        return foundTrainer;
    }

}
