package com.expense.tracker.repositories.category;

import java.util.List;

import com.expense.tracker.exceptions.BadRequestException;
import com.expense.tracker.exceptions.ResourceNotFoundException;
import com.expense.tracker.models.Category;

public interface CategoryRepository {

 List<Category> findAll(Integer user_id) throws ResourceNotFoundException;

 Category findByID(Integer user_id, Integer categoryID) throws ResourceNotFoundException;

 Integer create(Integer user_id, String title, String description) throws BadRequestException;

 void update(Integer user_id, Integer categoryID, Category category) throws BadRequestException;

 void removeByID(Integer user_id, Integer categoryID);
}

/*
 * CREATE TABLE IF NOT EXISTS users (
 * user_id serial NOT NULL PRIMARY KEY,
 * firstname VARCHAR (150) NOT NULL,
 * lastname VARCHAR (150) NOT NULL,
 * password VARCHAR (150) NOT NULL,
 * email VARCHAR (255) UNIQUE NOT NULL,
 * created_on TIMESTAMP NOT NULL,
 * last_login TIMESTAMP
 * );
 */