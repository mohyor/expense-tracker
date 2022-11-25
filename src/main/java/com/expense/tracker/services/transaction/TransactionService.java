package com.expense.tracker.services;

import com.expense.tracker.models.Transaction;

import com.expense.tracker.exceptions.BadRequestException;
import com.expense.tracker.exceptions.ResourceNotFoundException;

import java.util.List;

public interface TransactionService {

  Transaction createTransaction(Integer user_id, Integer categoryID, Double amount, String note, Long transactionDate)
      throws BadRequestException;

  List<Transaction> getTransactions(Integer user_id, Integer categoryID);

  Transaction getTransactionByID(Integer user_id, Integer categoryID, Integer transactionID)
      throws ResourceNotFoundException;

  void updateTransaction(Integer user_id, Integer categoryID, Integer transactionID, Transaction transaction)
      throws BadRequestException;

  void removeTransaction(Integer user_id, Integer categoryID, Integer transactionID) throws ResourceNotFoundException;
}
