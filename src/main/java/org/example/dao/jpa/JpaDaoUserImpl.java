package org.example.dao.jpa;

import org.example.model.TrainingType;
import org.example.model.User;

import java.util.List;
import java.util.Optional;

public class JpaDaoUserImpl extends JpaDaoImpl<User> {

    @Override
    public Optional<User> get(int id) {
        try {
            return Optional.of(getEntityManager().find(User.class, id));
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> getAll() {
        return getEntityManager().createQuery("FROM User").getResultList();
    }

    @Override
    public User save(User user) {
        executeTransaction(entityManager -> entityManager.persist(user));
        return user;
    }

    @Override
    public User update(int id, User user) {
        executeTransaction(entityManager -> entityManager.merge(user));
        return user;
    }

    @Override
    public Optional<User> delete(int id) {
        Optional<User> foundUser = get(id);
        foundUser.ifPresent(user -> executeTransaction(em -> em.remove(user)));
        return foundUser;
    }


}