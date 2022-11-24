package com.expense.tracker.controllers;

import com.expense.tracker.models.Category;
import com.expense.tracker.services.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

  @Autowired
  CategoryService categoryService;

  @PostMapping("")
  public ResponseEntity<Category> addCategory(HttpServletRequest req, @RequestBody Map<String, Object> categoryMap) {

    int userID = (Integer) req.getAttribute("userID");

    String title = (String) categoryMap.get("title");

    String description = (String) categoryMap.get("description");

    Category category = categoryService.addCategory(userID, title, description);

    return new ResponseEntity<>(category, HttpStatus.CREATED);
  }

  @GetMapping("")
  public ResponseEntity<List<Category>> getAllCategories(HttpServletRequest req) {

    int userID = (Integer) req.getAttribute("userID");

    List<Category> categories = categoryService.fetchAllCategories(userID);

    return new ResponseEntity<>(categories, HttpStatus.OK);
  }

  @GetMapping("/{categoryID}")
  public ResponseEntity<Category> getCategoryByID(HttpServletRequest req,
      @PathVariable("categoryID") Integer categoryID) {

    int userID = (Integer) req.getAttribute("userID");

    Category category = categoryService.fetchCategoryByID(userID, categoryID);

    return new ResponseEntity<>(category, HttpStatus.OK);
  }

  @PutMapping("/{categoryID}")
  public ResponseEntity<Map<String, Boolean>> updateCategory(HttpServletRequest req,
      @PathVariable("categoryID") Integer categoryID, @RequestBody Category category) {

    int userID = (Integer) req.getAttribute("userID");

    categoryService.updateCategory(userID, categoryID, category);

    Map<String, Boolean> map = new HashMap<>();

    map.put("success", true);

    return new ResponseEntity<>(map, HttpStatus.OK);
  }

  @DeleteMapping("/{categoryID}")
  public ResponseEntity<Map<String, Boolean>> deleteCategory(HttpServletRequest req,
      @PathVariable("categoryID") Integer categoryID) {

    int userID = (Integer) req.getAttribute("userID");

    categoryService.removeCategoryWithAllTransactions(userID, categoryID);

    Map<String, Boolean> map = new HashMap<>();

    map.put("success", true);

    return new ResponseEntity<>(map, HttpStatus.OK);
  }
}
