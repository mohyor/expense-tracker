package com.expense.tracker.services;

import com.expense.tracker.models.Category;
import com.expense.tracker.exceptions.BadRequestException;
import com.expense.tracker.exceptions.ResourceNotFoundException;

import java.util.List;

public interface CategoryService {

 List<Category> fetchAllCategories(Integer user_id);

 Category fetchCategoryByID(Integer user_id, Integer categoryID) throws ResourceNotFoundException;

 Category addCategory(Integer user_id, String title, String description) throws BadRequestException;

 void updateCategory(Integer user_id, Integer categoryID, Category category) throws BadRequestException;

 void removeCategoryWithAllTransactions(Integer user_id, Integer categoryID) throws ResourceNotFoundException;
}
