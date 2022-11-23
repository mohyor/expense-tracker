package com.expense.tracker.repositories;

import com.expense.tracker.models.User;
import com.expense.tracker.exceptions.AuthException;

public interface UserRepository {

 Integer create(String firstName, String lastName, String email, String password) throws AuthException;

 User findByEmailAndPassword(String email, String password) throws AuthException;

 Integer getCountByEmail(String email);

 User findByID(Integer userID);
}

/*
 * CREATE TABLE IF NOT EXISTS users (
 * userID serial NOT NULL PRIMARY KEY,
 * firstName VARCHAR (150) NOT NULL,
 * lastName VARCHAR (150) NOT NULL,
 * password VARCHAR (150) NOT NULL,
 * email VARCHAR (255) UNIQUE NOT NULL,
 * created_on TIMESTAMP NOT NULL,
 * last_login TIMESTAMP
 * );
 */