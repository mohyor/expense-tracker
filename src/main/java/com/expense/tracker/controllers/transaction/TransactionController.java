package com.expense.tracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.expense.tracker.models.Transaction;
import com.expense.tracker.services.transaction.TransactionService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories/{categoryID}/transactions")
public class TransactionController {

 @Autowired
 TransactionService transactionService;

 @PostMapping("")
 public ResponseEntity<Transaction> createTransaction(HttpServletRequest request,
   @PathVariable("categoryID") Integer categoryID, @RequestBody Map<String, Object> transactionMap) {

  int user_id = (Integer) request.getAttribute("user_id");

  Double amount = Double.valueOf(transactionMap.get("amount").toString());

  String note = (String) transactionMap.get("note");

  Long transactionDate = (Long) transactionMap.get("transactionDate");

  Transaction transaction = transactionService.createTransaction(user_id, categoryID, amount, note, transactionDate);

  return new ResponseEntity<>(transaction, HttpStatus.CREATED);
 }

 @GetMapping("")
 public ResponseEntity<List<Transaction>> getTransactions(HttpServletRequest request,
   @PathVariable("categoryID") Integer categoryID) {

  int user_id = (Integer) request.getAttribute("user_id");

  List<Transaction> transactions = transactionService.getTransactions(user_id, categoryID);

  return new ResponseEntity<>(transactions, HttpStatus.OK);
 }

 @GetMapping("/{transactionID}")
 public ResponseEntity<Transaction> getTransactionByID(HttpServletRequest request,
   @PathVariable("categoryID") Integer categoryID, @PathVariable("transactionID") Integer transactionID) {

  int user_id = (Integer) request.getAttribute("user_id");

  Transaction transaction = transactionService.getTransactionByID(user_id, categoryID, transactionID);

  return new ResponseEntity<>(transaction, HttpStatus.OK);
 }

 @PutMapping("/{transactionID}")
 public ResponseEntity<Map<String, Boolean>> updateTransaction(HttpServletRequest request,
   @PathVariable("categoryID") Integer categoryID, @PathVariable("transactionID") Integer transactionID,
   @RequestBody Transaction transaction) {

  int user_id = (Integer) request.getAttribute("user_id");

  transactionService.updateTransaction(user_id, categoryID, transactionID, transaction);

  Map<String, Boolean> map = new HashMap<>();

  map.put("success", true);

  return new ResponseEntity<>(map, HttpStatus.OK);
 }

 @DeleteMapping("/{transactionID}")
 public ResponseEntity<Map<String, Boolean>> removeTransaction(HttpServletRequest request,
   @PathVariable("categoryID") Integer categoryID, @PathVariable("transactionID") Integer transactionID) {

  int user_id = (Integer) request.getAttribute("user_id");

  transactionService.removeTransaction(user_id, categoryID, transactionID);

  Map<String, Boolean> map = new HashMap<>();

  map.put("success", true);

  return new ResponseEntity<>(map, HttpStatus.OK);
 }
}
