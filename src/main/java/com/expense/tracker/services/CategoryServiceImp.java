package com.expense.tracker.services;

import com.expense.tracker.models.Category;
import com.expense.tracker.exceptions.BadRequestException;
import com.expense.tracker.exceptions.ResourceNotFoundException;
import com.expense.tracker.repositories.CategoryRepository;
import com.expense.tracker.services.CategoryService;

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
 public List<Category> fetchAllCategories(Integer userID) {

  return categoryRepository.findAll(userID);
 }

 @Override
 public Category fetchCategoryByID(Integer userID, Integer categoryID) throws ResourceNotFoundException {

  return categoryRepository.findByID(userID, categoryID);
 }

 @Override
 public Category addCategory(Integer userID, String title, String description) throws BadRequestException {

  int categoryID = categoryRepository.create(userID, title, description);

  return categoryRepository.findByID(userID, categoryID);
 }

 @Override
 public void updateCategory(Integer userID, Integer categoryID, Category category) throws BadRequestException {

  categoryRepository.update(userID, categoryID, category);
 }

 @Override
 public void removeCategoryWithAllTransactions(Integer userID, Integer categoryID) throws ResourceNotFoundException {

  this.fetchCategoryByID(userID, categoryID);

  categoryRepository.removeByID(userID, categoryID);
 }

}
