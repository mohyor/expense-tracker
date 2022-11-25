package com.expense.tracker.models;

public class Category {
 private Integer categoryID;
 private Integer user_id;
 private String title;
 private String description;
 private Double totalExpense;

 public Category(Integer categoryID, Integer user_id, String title, String description, Double totalExpense) {
  this.categoryID = categoryID;
  this.user_id = user_id;
  this.title = title;
  this.description = description;
  this.totalExpense = totalExpense;
 }

 public Integer getCategoryID() {
  return categoryID;
 }

 public void setCategoryID(Integer categoryID) {
  this.categoryID = categoryID;
 }

 public Integer getuser_id() {
  return user_id;
 }

 public void setuser_id(Integer user_id) {
  this.user_id = user_id;
 }

 public String getTitle() {
  return title;
 }

 public void setTitle(String title) {
  this.title = title;
 }

 public String getDescription() {
  return description;
 }

 public void setDescription(String description) {
  this.description = description;
 }

 public double getTotalExpense() {
  return totalExpense;
 }

 public void setTotalExpense(Double totalExpense) {
  this.totalExpense = totalExpense;
 }

}
