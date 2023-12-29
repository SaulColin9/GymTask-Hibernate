package org.example.dao.jpa;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.example.model.Trainee;
import org.example.model.User;

import java.util.List;
import java.util.Optional;

public class JpaDaoUserImpl extends JpaDaoImpl<User> {

    @Override
    public Optional<User> get(int id) {
        return Optional.of(getEntityManager().find(User.class, id));
    }

    @Override
    public List<User> getAll() {
        return getEntityManager().createQuery("FROM User", User.class).getResultList();
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