package com.expense.tracker.models;

public class User {
 private Integer user_id;
 private String firstname;
 private String lastname;
 private String email;
 private String password;

 public User(Integer user_id, String firstname, String lastname, String email, String password) {
  this.user_id = user_id;
  this.firstname = firstname;
  this.lastname = lastname;
  this.email = email;
  this.password = password;
 }

 public Integer getUserID() {
  return user_id;
 }

 public void setUserID(Integer user_id) {
  this.user_id = user_id;
 }

 public String getfirstname() {
  return firstname;
 }

 public void setfirstname(String firstname) {
  this.firstname = firstname;
 }

 public String getlastname() {
  return lastname;
 }

 public void setlastname(String lastname) {
  this.lastname = lastname;
 }

 public String getEmail() {
  return email;
 }

 public void setEmail(String email) {
  this.email = email;
 }

 public String getPassword() {
  return password;
 }

 public void setPassword(String password) {
  this.password = password;
 }

}