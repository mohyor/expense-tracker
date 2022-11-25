package com.expense.tracker.models;

public class Transaction {

  private Integer transactionID;
  private Integer categoryID;
  private Integer user_id;
  private Double amount;
  private String note;
  private Long transactionDate;

  public Transaction(Integer transactionID, Integer categoryID, Integer user_id, Double amount, String note,
      Long transactionDate) {
    this.transactionID = transactionID;
    this.categoryID = categoryID;
    this.user_id = user_id;
    this.amount = amount;
    this.note = note;
    this.transactionDate = transactionDate;
  }

  public Integer getTransactionID() {
    return transactionID;
  }

  public void setTransactionID(Integer transactionID) {
    this.transactionID = transactionID;
  }

  public Integer getCategoryID() {
    return categoryID;
  }

  public void setCategoryID(Integer categoryID) {
    this.categoryID = categoryID;
  }

  public Integer getuser_id() {
    return user_id;
  }

  public void setuser_id(Integer user_id) {
    this.user_id = user_id;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public Long getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(Long transactionDate) {
    this.transactionDate = transactionDate;
  }

}
