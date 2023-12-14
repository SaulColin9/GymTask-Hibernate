package org.example.dao.jpa;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.model.Training;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class JpaDaoTrainingImpl extends JpaDaoImpl<Training> {
    @Override
    public Optional<Training> get(int id) {
        try {
            return Optional.of(getEntityManager().find(Training.class, id));
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Training> getAll() {
        return getEntityManager().createQuery("FROM Training", Training.class).getResultList();
    }

    @Override
    public Training save(Training training) {
        executeTransaction(entityManager -> entityManager.persist(training));
        return training;
    }

    @Override
    public Training update(int id, Training training) {
        executeTransaction(entityManager -> entityManager.merge(training));
        return training;
    }

    @Override
    public Optional<Training> delete(int id) {
        Optional<Training> foundTraining = get(id);
        foundTraining.ifPresent(training -> executeTransaction(entityManager -> entityManager.remove(training)));
        return foundTraining;
    }

    public List<Training> getTrainingsByTraineeUsername(String username, Integer trainingTypeId) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Training> criteriaQuery = criteriaBuilder.createQuery(Training.class);
        Root<Training> root = criteriaQuery.from(Training.class);
        List<Predicate> predicates = new ArrayList<>();

        Predicate equalsUsername = criteriaBuilder.equal(root.get("trainee").get("user").get("username"), username);
        predicates.add(equalsUsername);

        if (trainingTypeId != null) {
            Predicate equalsTrainingType = criteriaBuilder.equal(root.get("trainingType").get("id"), trainingTypeId);
            predicates.add(equalsTrainingType);
        }

        criteriaQuery.select(root).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        TypedQuery<Training> query = getEntityManager().createQuery(criteriaQuery);

        return query.getResultList();
    }

    public List<Training> getTrainingsByTrainerUsername(String username, Boolean isTrainingCompleted) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Training> criteriaQuery = criteriaBuilder.createQuery(Training.class);
        Root<Training> root = criteriaQuery.from(Training.class);
        List<Predicate> predicates = new ArrayList<>();

        Predicate equalsUsername = criteriaBuilder.equal(root.get("trainer").get("user").get("username"), username);
        predicates.add(equalsUsername);

        if (isTrainingCompleted != null) {
            Predicate isCompletedPredicate =
                    isTrainingCompleted ? criteriaBuilder.lessThan(root.get("trainingDate"), new Date()) :
                            criteriaBuilder.greaterThanOrEqualTo(root.get("trainingDate"), new Date());
            predicates.add(isCompletedPredicate);
        }

        criteriaQuery.select(root).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        TypedQuery<Training> query = getEntityManager().createQuery(criteriaQuery);

        return query.getResultList();
    }

}
