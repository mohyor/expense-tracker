package com.expense.tracker.repositories;

import java.util.List;

import com.expense.tracker.exceptions.BadRequestException;
import com.expense.tracker.exceptions.ResourceNotFoundException;
import com.expense.tracker.models.Category;

public interface CategoryRepository {

 List<Category> findAll(Integer userID) throws ResourceNotFoundException;

 Category findByID(Integer userID, Integer categoryID) throws ResourceNotFoundException;

 Integer create(Integer userID, String title, String description) throws BadRequestException;

 void update(Integer userID, Integer categoryID, Category category) throws BadRequestException;

 void removeByID(Integer userID, Integer categoryID);
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