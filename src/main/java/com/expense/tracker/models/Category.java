package com.expense.tracker.models;

public class Category {
 private Integer categoryID;
 private Integer userID;
 private String title;
 private String description;
 private Double totalExpense;

 public Category(Integer categoryID, Integer userID, String title, String description, Double totalExpense) {
  this.categoryID = categoryID;
  this.userID = userID;
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

 public Integer getUserID() {
  return userID;
 }

 public void setUserID(Integer userID) {
  this.userID = userID;
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
