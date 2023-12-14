-- gym database
CREATE DATABASE gym;

-- Users table
CREATE TABLE user(
   id INT NOT NULL AUTO_INCREMENT,
   isActive bit(1) NOT NULL,
   firstName VARCHAR(255) NOT NULL,
   lastName VARCHAR(255) NOT NULL,
   password VARCHAR(255) NOT NULL,
   username VARCHAR(255) NOT NULL,
   PRIMARY KEY(`id`)
);

-- TrainingTypes table
CREATE TABLE trainingtype (
   id INT NOT NULL AUTO_INCREMENT,
   trainingTypeName VARCHAR(255) DEFAULT NULL,
   PRIMARY KEY (id)
);

-- Trainees table
CREATE TABLE trainee(
   id INT NOT NULL AUTO_INCREMENT,
   user_id INT NOT NULL,
   dateOfBirth DATETIME(6) DEFAULT NULL,
   address VARCHAR(255) DEFAULT NULL,
   PRIMARY KEY(id),
   UNIQUE KEY trainee_user_uk (user_id),
   CONSTRAINT trainee_user_fk FOREIGN KEY (user_id) REFERENCES `user` (id)
);

-- Trainers table
CREATE TABLE trainer(
   id INT NOT NULL AUTO_INCREMENT,
   specialization INT NOT NULL,
   user_id INT NOT NULL,
   PRIMARY KEY (id),
   UNIQUE KEY user_uk (user_id),
   CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES `user`(id),
   CONSTRAINT specialization_fk FOREIGN KEY (specialization) REFERENCES trainingtype (id)
);

-- Trainings table
CREATE TABLE training(
   id INT NOT NULL AUTO_INCREMENT,
   trainee_id INT NOT NULL,
   trainer_id INT NOT NULL,
   trainingDuration DOUBLE NOT NULL,
   trainingType_id INT NOT NULL,
   trainingDate DATETIME(6) DEFAULT NULL,
   trainingName VARCHAR(255) DEFAULT NULL,
   PRIMARY KEY (id),
   KEY trainee_fk (trainee_id),
   KEY trainer_fk (trainer_id),
   KEY trainingType_fk (trainingType_id),
   CONSTRAINT trainingType_fk FOREIGN KEY (trainingType_id) REFERENCES trainingtype (id),
   CONSTRAINT trainer_fk FOREIGN KEY (trainer_id) REFERENCES trainer (id),
   CONSTRAINT trainee_fk FOREIGN KEY (trainee_id) REFERENCES trainee (id)
);

-- Training Type values
INSERT INTO TrainingType (id, trainingTypeName) values (1, 'Cardiovascular Training');
INSERT INTO TrainingType (id, trainingTypeName) values (2, 'Strength Training');
INSERT INTO TrainingType (id, trainingTypeName) values (3, 'Yoga');
INSERT INTO TrainingType (id, trainingTypeName) values (4, 'Cross-Fit');
INSERT INTO TrainingType (id, trainingTypeName) values (5, 'Pilates');