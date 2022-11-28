package com.expense.tracker.repositories.user;

import com.expense.tracker.models.User;
import com.expense.tracker.exceptions.AuthException;

public interface UserRepository {

 Integer create(String firstname, String lastname, String email, String password) throws AuthException;

 User findByEmailAndPassword(String email, String password) throws AuthException;

 Integer getCountByEmail(String email);

 User findByID(Integer user_id);
}

/*
 * CREATE TABLE IF NOT EXISTS users (
 * user_id serial NOT NULL PRIMARY KEY,
 * firstname VARCHAR (150) NOT NULL,
 * lastname VARCHAR (150) NOT NULL,
 * password VARCHAR (150) NOT NULL,
 * email VARCHAR (255) UNIQUE NOT NULL,
 * created_on TIMESTAMP,
 * last_login TIMESTAMP
 * );
 * 
 * CREATE SEQUENCE employee_seq
 * START 10
 * INCREMENT 5
 * MINVALUE 10
 * OWNED BY employee.emp_id;
 */