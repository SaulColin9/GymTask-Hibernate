package org.example.dao.jpa;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.example.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
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
        executeTransaction(entityManager -> {
            entityManager.persist(user);
        });
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

    private static final String USERNAME_PARAM = "username";

    public Optional<User> getByUsername(String username) {
        try {
            TypedQuery<User> query = getEntityManager().createQuery("FROM User u WHERE u.username = :username", User.class);
            query.setParameter(USERNAME_PARAM, username);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }


}