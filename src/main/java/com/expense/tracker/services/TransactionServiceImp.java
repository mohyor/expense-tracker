package com.expense.tracker.services;

import com.expense.tracker.models.Transaction;

import com.expense.tracker.exceptions.BadRequestException;
import com.expense.tracker.exceptions.ResourceNotFoundException;

import com.expense.tracker.repositories.TransactionRepository;
import com.expense.tracker.services.TransactionService;

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
 public Transaction createTransaction(Integer userID, Integer categoryID, Double amount, String note,
   Long transactionDate) throws BadRequestException {

  int transactionID = transactionRepository.create(userID, categoryID, amount, note, transactionDate);

  return transactionRepository.findByID(userID, categoryID, categoryID);
 }

 @Override
 public void updateTransaction(Integer userID, Integer categoryID, Integer transactionID, Transaction transaction)
   throws BadRequestException {

  transactionRepository.update(userID, categoryID, transactionID, transaction);
 }

 @Override
 public List<Transaction> getTransactions(Integer userID, Integer categoryID) {
  return transactionRepository.findAll(userID, categoryID);
 }

 @Override
 public Transaction getTransactionByID(Integer userID, Integer categoryID, Integer transactionID)
   throws ResourceNotFoundException {
  return transactionRepository.findByID(userID, categoryID, transactionID);
 }

 @Override
 public void removeTransaction(Integer userID, Integer categoryID, Integer transactionID)
   throws ResourceNotFoundException {
  transactionRepository.removeByID(userID, categoryID, transactionID);
 }
}
