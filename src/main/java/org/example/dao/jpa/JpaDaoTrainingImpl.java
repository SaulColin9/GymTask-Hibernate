package org.example.dao.jpa;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;

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
        return getEntityManager().createQuery("FROM Training").getResultList();
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

    public List<Training> getTrainingsByTraineeUsername(String username, String trainingName, Double trainingDuration) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Training> criteriaQuery = criteriaBuilder.createQuery(Training.class);
        Root<Training> root = criteriaQuery.from(Training.class);

        Predicate equalsUsername = criteriaBuilder.equal(root.get("trainee").get("user").get("username"), username);
        Predicate equalsTrainingName = trainingName == null ? criteriaBuilder.and() :
                criteriaBuilder.equal(root.get("trainingName"), trainingName);
        Predicate equalsTrainingDuration = trainingDuration == null ? criteriaBuilder.and() :
                criteriaBuilder.equal(root.get("trainingDuration"), trainingDuration);


        criteriaQuery.select(root).where(criteriaBuilder.and(equalsUsername, equalsTrainingName, equalsTrainingDuration));
        Query query = getEntityManager().createQuery(criteriaQuery);

        return query.getResultList();
    }

    public List<Training> getTrainingsByTrainerUsername(String username, String trainingName, Double trainingDuration) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Training> criteriaQuery = criteriaBuilder.createQuery(Training.class);
        Root<Training> root = criteriaQuery.from(Training.class);

        Predicate equalsUsername = criteriaBuilder.equal(root.get("trainer").get("user").get("username"), username);

        Predicate equalsTrainingName = trainingName == null ? criteriaBuilder.and() :
                criteriaBuilder.equal(root.get("trainingName"), trainingName);

        Predicate equalsTrainingDuration = trainingDuration == null ? criteriaBuilder.and() :
                criteriaBuilder.equal(root.get("trainingDuration"), trainingDuration);


        criteriaQuery.select(root).where(criteriaBuilder.and(equalsUsername, equalsTrainingName, equalsTrainingDuration));
        Query query = getEntityManager().createQuery(criteriaQuery);

        return query.getResultList();
    }


}
