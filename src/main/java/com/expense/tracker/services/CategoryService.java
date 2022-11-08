package com.expense.tracker.services;

import com.expense.tracker.models.Category;
import com.expense.tracker.exceptions.BadRequestException;
import com.expense.tracker.exceptions.ResourceNotFoundException;

import java.util.List;

public interface CategoryService {

 List<Category> fetchAllCategories(Integer userID);

 Category fetchCategoryByID(Integer userID, Integer categoryID) throws ResourceNotFoundException;

 Category addCategory(Integer userID, String title, String description) throws BadRequestException;

 void updateCategory(Integer userID, Integer categoryID, Category category) throws BadRequestException;

 void removeCategoryWithAllTransactions(Integer userID, Integer categoryID) throws ResourceNotFoundException;
}
