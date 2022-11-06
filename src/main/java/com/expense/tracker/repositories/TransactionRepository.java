package com.expense.tracker.repositories;

import com.expense.tracker.models.Transaction;
import com.expense.tracker.exceptions.BadRequestException;
import com.expense.tracker.exceptions.ResourceNotFoundException;

import java.util.List;

public interface TransactionRepository {

 List<Transaction> findAll(Integer userID, Integer categoryID);

 Transaction findByID(Integer userID, Integer categoryID, Integer transactionID) throws ResourceNotFoundException;

 Integer create(Integer userID, Integer categoryID, Double amount, String note, Long transactionDate)
   throws BadRequestException;

 void update(Integer userID, Integer categoryID, Integer transactionID, Transaction transaction)
   throws BadRequestException;

 void removeByID(Integer userID, Integer categoryID, Integer transactionID) throws ResourceNotFoundException;
}
