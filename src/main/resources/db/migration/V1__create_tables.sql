CREATE TABLE IF NOT EXISTS books (
                                     id BIGSERIAL PRIMARY KEY,
                                     title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    genre VARCHAR(255),
    isbn VARCHAR(255),
    total_copies INTEGER,
    available_copies INTEGER,
    notes VARCHAR(255),
    added_date DATE
    );

CREATE TABLE IF NOT EXISTS students (
                                        id BIGSERIAL PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL,
    class_name VARCHAR(255),
    notes VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS loans (
                                     id BIGSERIAL PRIMARY KEY,
                                     book_id BIGINT NOT NULL,
                                     student_id BIGINT NOT NULL,
                                     lent_date DATE,
                                     due_date DATE,
                                     returned_date DATE
);