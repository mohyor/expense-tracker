package com.expense.tracker.services;

import com.expense.tracker.models.User;
import com.expense.tracker.exceptions.AuthException;

public interface UserService {

 User validateUser(String email, String password) throws AuthException;

 User registerUser(String firstname, String lastname, String email, String password) throws AuthException;
}