package org.example.configuration.jpa;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.spi.PersistenceUnitInfo;
import jakarta.persistence.spi.PersistenceUnitTransactionType;
import org.example.dao.Dao;
import org.example.dao.jpa.*;
import org.example.model.*;
import org.example.service.authentication.CredentialsAuthenticator;
import org.example.service.authentication.JpaCredentialsAuthenticator;
import org.example.service.serviceimpl.jpa.*;
import org.example.service.utils.*;
import org.example.service.utils.user.JpaUserUtils;
import org.example.service.utils.user.UserUtils;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@PropertySource(value = "classpath:storage.properties")
@Profile("jpa")
public class JpaBeanConfiguration {
    @Value("${entities.jpa.source}")
    String url;
    @Value("${entities.jpa.username}")
    String username;
    @Value("${entities.jpa.password}")
    String password;
    @Value("${entities.jpa.driver}")
    String driver;
    @Value("${entities.jpa.persistenceProvider}")
    String persistenceProvider;
    @Value("${entities.jpa.persistenceUnit}")
    String persistenceUnit;

    @Bean
    DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driver);

        return dataSource;
    }

    @Bean
    CredentialsAuthenticator credentialsAuthenticator(@Autowired EntityManager entityManager) {
        JpaCredentialsAuthenticator credentialsAuthenticator = new JpaCredentialsAuthenticator();
        credentialsAuthenticator.setEntityManager(entityManager);
        return credentialsAuthenticator;
    }

    @Bean
    PersistenceUnitInfo persistenceUnitInfo(@Autowired DataSource dataSource) {
        PersistenceUnitInfoImpl persistenceUnitInfo = new PersistenceUnitInfoImpl();
        persistenceUnitInfo.setPersistenceUnitName(persistenceUnit);
        persistenceUnitInfo.setPersistenceProviderClassName(persistenceProvider);
        persistenceUnitInfo.setPersistenceUnitTransactionType(PersistenceUnitTransactionType.RESOURCE_LOCAL);
        persistenceUnitInfo.setManagedClassNames(
                List.of(User.class.getName(), Trainer.class.getName(), Trainee.class.getName(),
                        Training.class.getName(), TrainingType.class.getName()));
        persistenceUnitInfo.setDataSource(dataSource);

        return persistenceUnitInfo;
    }

    @Bean
    public Map<String, String> props() {
        Map<String, String> props = new HashMap<>();
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.hbm2ddl.import_files", "trainingTypes.sql");
        props.put("hibernate.hbm2ddl.auto", "create-drop");
        return props;
    }

    @Bean
    public EntityManagerFactory emf(@Autowired PersistenceUnitInfo persistenceUnitInfo,
                                    @Autowired Map<String, String> props) {
        return new HibernatePersistenceProvider().createContainerEntityManagerFactory(persistenceUnitInfo, props);
    }

    @Bean
    public EntityManager entityManager(@Autowired EntityManagerFactory emf) {
        return emf.createEntityManager();
    }

    @Bean
    public JpaDaoUserImpl jpaDaoUser(@Autowired EntityManager entityManager) {
        JpaDaoUserImpl jpaDaoUser = new JpaDaoUserImpl();
        jpaDaoUser.setEntityManager(entityManager);
        return jpaDaoUser;
    }

    @Bean
    public JpaDaoTrainerImpl jpaDaoTrainer(@Autowired EntityManager entityManager) {
        JpaDaoTrainerImpl jpaDaoTrainer = new JpaDaoTrainerImpl();
        jpaDaoTrainer.setEntityManager(entityManager);
        return jpaDaoTrainer;
    }

    @Bean
    public JpaDaoTraineeImpl jpaDaoTrainee(@Autowired EntityManager entityManager) {
        JpaDaoTraineeImpl jpaDaoTrainee = new JpaDaoTraineeImpl();
        jpaDaoTrainee.setEntityManager(entityManager);
        return jpaDaoTrainee;
    }

    @Bean
    public JpaDaoTrainingImpl jpaDaoTraining(@Autowired EntityManager entityManager) {
        JpaDaoTrainingImpl jpaDaoTraining = new JpaDaoTrainingImpl();
        jpaDaoTraining.setEntityManager(entityManager);
        return jpaDaoTraining;
    }

    @Bean
    public JpaDaoTrainingTypeImpl jpaDaoTrainingType(@Autowired EntityManager entityManager) {
        JpaDaoTrainingTypeImpl jpaDaoTrainingType = new JpaDaoTrainingTypeImpl();
        jpaDaoTrainingType.setEntityManager(entityManager);
        return jpaDaoTrainingType;
    }


    @Bean
    public JpaTrainingService trainingService(@Autowired Dao<Trainee> traineeDao,
                                              @Autowired Dao<Trainer> trainerDao,
                                              @Autowired Dao<Training> trainingDao,
                                              @Autowired Dao<TrainingType> trainingTypeDao
    ) {
        JpaTrainingServiceImpl trainingService = new JpaTrainingServiceImpl();
        trainingService.setTraineeDao(traineeDao);
        trainingService.setTrainerDao(trainerDao);
        trainingService.setTrainingDao(trainingDao);
        trainingService.setTrainingTypeDao(trainingTypeDao);
        trainingService.setValidator(new Validator<>(Training.class));
        return trainingService;
    }

    @Bean
    public JpaTrainerService trainerService(@Autowired Dao<Trainer> trainerDao,
                                            @Autowired Dao<TrainingType> trainingTypeDao,
                                            @Autowired UserUtils userUtils) {
        JpaTrainerServiceImpl trainerService = new JpaTrainerServiceImpl();
        trainerService.setTrainerDao(trainerDao);
        trainerService.setTrainingTypeDao(trainingTypeDao);
        trainerService.setUserUtils(userUtils);
        trainerService.setValidator(new Validator<>(Trainer.class));
        return trainerService;
    }

    @Bean
    JpaTraineeService traineeService(@Autowired Dao<Trainee> traineeDao, @Autowired UserUtils userUtils) {
        JpaTraineeServiceImpl traineeService = new JpaTraineeServiceImpl();
        traineeService.setTraineeDao(traineeDao);
        traineeService.setUserUtils(userUtils);
        traineeService.setValidator(new Validator<>(Trainee.class));
        return traineeService;
    }

    @Bean
    public PasswordGenerator passwordGenerator() {
        return new PasswordGeneratorImpl();
    }

    @Bean
    UsernameGenerator usernameGenerator(@Autowired Dao<User> userDao) {
        UsernameGeneratorImpl usernameGenerator = new UsernameGeneratorImpl();
        usernameGenerator.setUserDao(userDao);
        return usernameGenerator;
    }

    @Bean
    UserUtils userUtils(@Autowired UsernameGenerator usernameGenerator,
                        @Autowired PasswordGenerator passwordGenerator,
                        @Autowired Dao<User> userDao) {
        JpaUserUtils userUtils = new JpaUserUtils();
        userUtils.setUserDao(userDao);
        userUtils.setUsernameGenerator(usernameGenerator);
        userUtils.setPasswordGenerator(passwordGenerator);
        return userUtils;
    }

}
