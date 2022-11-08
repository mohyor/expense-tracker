package com.expense.tracker.repositories;

import com.expense.tracker.models.User;
import com.expense.tracker.exceptions.AuthException;

public interface UserRepository {

 Integer create(String firstName, String lastName, String email, String password) throws AuthException;

 User findByEmailAndPassword(String email, String password) throws AuthException;

 Integer getCountByEmail(String email);

 User findByID(Integer userID);
}