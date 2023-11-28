package org.example.configuration;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.spi.PersistenceUnitInfo;
import jakarta.persistence.spi.PersistenceUnitTransactionType;
import org.example.dao.jpa.*;
import org.example.service.authentication.CredentialsAuthenticator;
import org.example.service.authentication.CredentialsAuthenticatorImpl;
import org.example.service.utils.*;
import org.hibernate.jpa.HibernatePersistenceProvider;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.dao.Dao;
import org.example.model.*;
import org.example.service.*;
import org.example.service.serviceImpl.TrainerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Configuration
@PropertySource(value = "storage.properties")
public class BeanConfiguration {
    @Value("${entities.source}")
    String url;
    @Value("${entities.username}")
    String username;
    @Value("${entities.password}")
    String password;
    @Value("${entities.driver}")
    String driver;

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
        CredentialsAuthenticatorImpl credentialsAuthenticator = new CredentialsAuthenticatorImpl();
        credentialsAuthenticator.setEntityManager(entityManager);
        return credentialsAuthenticator;
    }

    @Bean
    PersistenceUnitInfo persistenceUnitInfo(@Autowired DataSource dataSource) {
        PersistenceUnitInfoImpl persistenceUnitInfo = new PersistenceUnitInfoImpl();
        persistenceUnitInfo.setUrl(url);
        persistenceUnitInfo.setPassword(password);
        persistenceUnitInfo.setUsername(username);
        persistenceUnitInfo.setPersistenceUnitName("gym-persistence-unit");
        persistenceUnitInfo.setPersistenceProviderClassName("org.hibernate.jpa.HibernatePersistenceProvider");
        persistenceUnitInfo.setPersistenceUnitTransactionType(PersistenceUnitTransactionType.RESOURCE_LOCAL);
        persistenceUnitInfo.setManagedClassNames(List.of(User.class.getName(), Trainer.class.getName(),
                Trainee.class.getName(), Training.class.getName(), TrainingType.class.getName()));
        persistenceUnitInfo.setDataSource(dataSource);

        return persistenceUnitInfo;
    }

    @Bean
    public Map<String, String> props() {
        Map<String, String> props = new HashMap<>();
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "create");
        props.put("hibernate.hbm2ddl.import_files", "trainingTypes.sql");
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

    //    @Bean
//    public Storage storage(@Value("${entities.source}") String filePath) {
//        GymStorageImpl gymStorage = new GymStorageImpl();
//        gymStorage.setFilePath(filePath);
//        return gymStorage;
//    }
//
//    @Bean
//    public GymFacade gymFacade(@Autowired TraineeService traineeService, @Autowired TrainerService trainerService,
//                               @Autowired TrainingService trainingService) {
//        return new GymFacadeImpl(traineeService, trainerService, trainingService);
//    }
//
//    @Bean
//    public TrainingService trainingService(@Autowired Dao<Trainee> traineeDao,
//                                           @Autowired Dao<Trainer> trainerDao,
//                                           @Autowired Dao<Training> trainingDao,
//                                           @Autowired Dao<TrainingType> trainingTypeDao
//    ) {
//        TrainingServiceImpl trainingService = new TrainingServiceImpl();
//        trainingService.setTraineeDao(traineeDao);
//        trainingService.setTrainerDao(trainerDao);
//        trainingService.setTrainingDao(trainingDao);
//        trainingService.setTrainingTypeDao(trainingTypeDao);
//        return trainingService;
//    }
//
    @Bean
    public TrainerService trainerService(@Autowired Dao<Trainer> trainerDao, @Autowired UserUtils userUtils) {
        TrainerServiceImpl trainerService = new TrainerServiceImpl();
        trainerService.setTrainerDao(trainerDao);
        trainerService.setUserUtils(userUtils);
        return trainerService;
    }
//
//    @Bean
//    TraineeService traineeService(@Autowired Dao<Trainee> traineeDao, @Autowired UserUtils userUtils) {
//        TraineeServiceImpl traineeService = new TraineeServiceImpl();
//        traineeService.setTraineeDao(traineeDao);
//        traineeService.setUserUtils(userUtils);
//        return traineeService;
//    }
//
//    @Bean("users")
//    public Dao<User> userDao(@Autowired Storage storage) {
//        DaoImpl<User> userDao = new DaoImpl<>();
//        userDao.setStorageEntities(storage.getUsers());
//        return userDao;
//    }
//
//    @Bean("trainings")
//    public Dao<Training> trainingDao(@Autowired Storage storage) {
//        Dao<Training> trainingDao = new TrainingDao();
//        trainingDao.setStorage(storage);
//        return trainingDao;
//    }
//
//    @Bean("trainingTypes")
//    public Dao<TrainingType> trainingTypesDao(@Autowired Storage storage) {
//        Dao<TrainingType> trainingTypesDao = new TrainingTypeDao();
//        trainingTypesDao.setStorage(storage);
//        return trainingTypesDao;
//    }
//
//    @Bean("trainers")
//    public Dao<Trainer> trainerDao(@Autowired Storage storage) {
//        Dao<Trainer> trainerDao = new TrainerDao();
//        trainerDao.setStorage(storage);
//        return trainerDao;
//    }
//
//    @Bean("trainees")
//    public Dao<Trainee> traineeDao(@Autowired Storage storage) {
//        Dao<Trainee> traineeDao = new TraineeDao();
//        traineeDao.setStorage(storage);
//        return traineeDao;
//    }

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
        UserUtilsImpl userUtils = new UserUtilsImpl();
        userUtils.setUserDao(userDao);
        userUtils.setUsernameGenerator(usernameGenerator);
        userUtils.setPasswordGenerator(passwordGenerator);
        return userUtils;
    }
}
