package com.expense.tracker.services;

import com.expense.tracker.models.User;
import com.expense.tracker.exceptions.AuthException;
import com.expense.tracker.repositories.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;
import java.lang.System;

@Service
@Transactional
public class UserServiceImp implements UserService {

 @Autowired
 UserRepository userRepository;

 @Override
 public User validateUser(String email, String password) throws AuthException {

  if (email != null) {
   email = email.toLowerCase();
  }

  return userRepository.findByEmailAndPassword(email, password);
 }

 @Override
 public User registerUser(String firstname, String lastname, String email, String password) throws AuthException {

  Pattern pattern = Pattern.compile("^(.+)@(.+)$");

  if (email != null) {
   email = email.toLowerCase();
  }

  if (!pattern.matcher(email).matches()) {
   throw new AuthException("Invalid email format.");
  }

  Integer count = userRepository.getCountByEmail(email);

  if (count > 0) {
   throw new AuthException("Email already exists");
  }

  Integer user_id = userRepository.create(firstname, lastname, email, password);

  return userRepository.findByID(user_id);
 }
}