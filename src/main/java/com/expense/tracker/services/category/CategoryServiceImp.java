package com.expense.tracker.services;

import com.expense.tracker.models.Category;
import com.expense.tracker.exceptions.BadRequestException;
import com.expense.tracker.exceptions.ResourceNotFoundException;
import com.expense.tracker.repositories.category.CategoryRepository;
import com.expense.tracker.services.category.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImp implements CategoryService {

 @Autowired
 CategoryRepository categoryRepository;

 @Override
 public List<Category> fetchAllCategories(Integer user_id) {

  return categoryRepository.findAll(user_id);
 }

 @Override
 public Category fetchCategoryByID(Integer user_id, Integer categoryID) throws ResourceNotFoundException {

  return categoryRepository.findByID(user_id, categoryID);
 }

 @Override
 public Category addCategory(Integer user_id, String title, String description) throws BadRequestException {

  int categoryID = categoryRepository.create(user_id, title, description);

  return categoryRepository.findByID(user_id, categoryID);
 }

 @Override
 public void updateCategory(Integer user_id, Integer categoryID, Category category) throws BadRequestException {

  categoryRepository.update(user_id, categoryID, category);
 }

 @Override
 public void removeCategoryWithAllTransactions(Integer user_id, Integer categoryID) throws ResourceNotFoundException {

  this.fetchCategoryByID(user_id, categoryID);

  categoryRepository.removeByID(user_id, categoryID);
 }

}
