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
        return Optional.of(getEntityManager().find(Training.class, id));
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

    public List<Training> getTraineeTrainings(String username, String trainingTypeName,
                                              Double minDuration, Double maxDuration) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Training> criteriaQuery = criteriaBuilder.createQuery(Training.class);
        Root<Training> root = criteriaQuery.from(Training.class);
        List<Predicate> predicates = new ArrayList<>();

        Predicate equalsUsername = criteriaBuilder.equal(root.get("trainee").get("user").get("username"), username);
        predicates.add(equalsUsername);

        if (trainingTypeName != null) {
            Predicate equalsTrainingType = criteriaBuilder.equal(root.get("trainingType").get("trainingTypeName"), trainingTypeName);
            predicates.add(equalsTrainingType);
        }
        if (minDuration != null && maxDuration != null) {
            Predicate durationPredicate = criteriaBuilder.between(root.get("trainingDuration"), maxDuration, minDuration);
            predicates.add(durationPredicate);
        }

        criteriaQuery.select(root).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        TypedQuery<Training> query = getEntityManager().createQuery(criteriaQuery);

        return query.getResultList();
    }

    public List<Training> getTrainingsByTrainerUsername(String username,
                                                        Boolean isTrainingCompleted,
                                                        String trainingName) {
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
        if (trainingName != null) {
            Predicate trainingNamePredicate = criteriaBuilder.equal(root.get("trainingName"), trainingName);
            predicates.add(trainingNamePredicate);
        }

        criteriaQuery.select(root).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        TypedQuery<Training> query = getEntityManager().createQuery(criteriaQuery);

        return query.getResultList();
    }

}
