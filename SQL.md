CREATE DATABASE school_db;

CREATE TABLE student (
                         id serial PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         email VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE course (
                        id serial PRIMARY KEY,
                        title VARCHAR(255) NOT NULL,
                        credits INT NOT NULL
);

CREATE TABLE enrollment (
                            student_id INT,
                            course_id INT,
                            grade DECIMAL(5,2) DEFAULT NULL,
                            PRIMARY KEY (student_id, course_id),
                            FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
                            FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE
);
