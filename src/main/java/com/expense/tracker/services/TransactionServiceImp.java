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
 public List<Transaction> getTransactions(Integer userID, Integer categoryID) {
  return transactionRepository.findAll(userID, categoryID);
 }
}
