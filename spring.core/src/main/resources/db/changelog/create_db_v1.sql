CREATE TABLE IF NOT EXISTS training_type (
                                             id BIGINT PRIMARY KEY,
                                             training_type_name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT PRIMARY KEY,
                                     first_name VARCHAR(255) NOT NULL,
                                     last_name VARCHAR(255) NOT NULL,
                                     username VARCHAR(255) UNIQUE NOT NULL,
                                     password VARCHAR(255) NOT NULL,
                                     is_active BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS trainee (
                                       id BIGINT PRIMARY KEY,
                                       date_of_birth DATE,
                                       address VARCHAR(255),
                                       user_id BIGINT,
                                       FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS trainer (
                                       id BIGINT PRIMARY KEY,
                                       user_id BIGINT,
                                       training_type_id BIGINT,
                                       FOREIGN KEY (user_id) REFERENCES users(id),
                                       FOREIGN KEY (training_type_id) REFERENCES training_type(id)
);

CREATE TABLE IF NOT EXISTS trainee_has_trainer (
                                                   trainee_id BIGINT NOT NULL,
                                                   trainer_id BIGINT NOT NULL,
                                                   PRIMARY KEY (trainee_id, trainer_id),
                                                   FOREIGN KEY (trainee_id) REFERENCES trainee(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                                   FOREIGN KEY (trainer_id) REFERENCES trainer(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS training (
                                        id BIGINT PRIMARY KEY,
                                        trainee_id BIGINT,
                                        trainer_id BIGINT,
                                        training_name VARCHAR(255) NOT NULL,
                                        training_type_id BIGINT,
                                        training_date DATE NOT NULL,
                                        training_duration NUMERIC NOT NULL,
                                        FOREIGN KEY (trainee_id) REFERENCES trainee(id),
                                        FOREIGN KEY (trainer_id) REFERENCES trainer(id),
                                        FOREIGN KEY (training_type_id) REFERENCES training_type(id)
);
