package com.expense.tracker.controllers.category;

import com.expense.tracker.models.Category;
import com.expense.tracker.services.category.CategoryService;

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

    int user_id = (Integer) req.getAttribute("user_id");

    String title = (String) categoryMap.get("title");

    String description = (String) categoryMap.get("description");

    Category category = categoryService.addCategory(user_id, title, description);

    return new ResponseEntity<>(category, HttpStatus.CREATED);
  }

  @GetMapping("")
  public ResponseEntity<List<Category>> getAllCategories(HttpServletRequest req) {

    int user_id = (Integer) req.getAttribute("user_id");

    List<Category> categories = categoryService.fetchAllCategories(user_id);

    return new ResponseEntity<>(categories, HttpStatus.OK);
  }

  @GetMapping("/{categoryID}")
  public ResponseEntity<Category> getCategoryByID(HttpServletRequest req,
      @PathVariable("categoryID") Integer categoryID) {

    int user_id = (Integer) req.getAttribute("user_id");

    Category category = categoryService.fetchCategoryByID(user_id, categoryID);

    return new ResponseEntity<>(category, HttpStatus.OK);
  }

  @PutMapping("/{categoryID}")
  public ResponseEntity<Map<String, Boolean>> updateCategory(HttpServletRequest req,
      @PathVariable("categoryID") Integer categoryID, @RequestBody Category category) {

    int user_id = (Integer) req.getAttribute("user_id");

    categoryService.updateCategory(user_id, categoryID, category);

    Map<String, Boolean> map = new HashMap<>();

    map.put("success", true);

    return new ResponseEntity<>(map, HttpStatus.OK);
  }

  @DeleteMapping("/{categoryID}")
  public ResponseEntity<Map<String, Boolean>> deleteCategory(HttpServletRequest req,
      @PathVariable("categoryID") Integer categoryID) {

    int user_id = (Integer) req.getAttribute("user_id");

    categoryService.removeCategoryWithAllTransactions(user_id, categoryID);

    Map<String, Boolean> map = new HashMap<>();

    map.put("success", true);

    return new ResponseEntity<>(map, HttpStatus.OK);
  }
}
