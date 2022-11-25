package com.expense.tracker.services;

import com.expense.tracker.models.Transaction;

import com.expense.tracker.exceptions.BadRequestException;
import com.expense.tracker.exceptions.ResourceNotFoundException;

import com.expense.tracker.repositories.transaction.TransactionRepository;
import com.expense.tracker.services.transaction.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TransactionServiceImp implements TransactionService {

  @Autowired
  TransactionRepository transactionRepository;

  @Override
  public Transaction createTransaction(Integer user_id, Integer categoryID, Double amount, String note,
      Long transactionDate) throws BadRequestException {

    int transactionID = transactionRepository.create(user_id, categoryID, amount, note, transactionDate);

    return transactionRepository.findByID(user_id, categoryID, categoryID);
  }

  @Override
  public void updateTransaction(Integer user_id, Integer categoryID, Integer transactionID, Transaction transaction)
      throws BadRequestException {

    transactionRepository.update(user_id, categoryID, transactionID, transaction);
  }

  @Override
  public List<Transaction> getTransactions(Integer user_id, Integer categoryID) {
    return transactionRepository.findAll(user_id, categoryID);
  }

  @Override
  public Transaction getTransactionByID(Integer user_id, Integer categoryID, Integer transactionID)
      throws ResourceNotFoundException {
    return transactionRepository.findByID(user_id, categoryID, transactionID);
  }

  @Override
  public void removeTransaction(Integer user_id, Integer categoryID, Integer transactionID)
      throws ResourceNotFoundException {
    transactionRepository.removeByID(user_id, categoryID, transactionID);
  }
}
