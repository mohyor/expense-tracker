package com.expense.tracker.repositories;

import java.util.List;

import com.expense.tracker.exceptions.BadRequestException;
import com.expense.tracker.exceptions.ResourceNotFoundException;
import com.expense.tracker.models.Category;

public interface CategoryRepository {
 List<Category> findAll(Integer userID) throws ResourceNotFoundException;

 Category findById(Integer userID, Integer categoryID) throws ResourceNotFoundException;

 Integer create(Integer userID, String title, String description) throws BadRequestException;

 void update(Integer userID, Integer categoryID, Category category) throws BadRequestException;

 void removeByID(Integer userID, Integer categoryID);
}
