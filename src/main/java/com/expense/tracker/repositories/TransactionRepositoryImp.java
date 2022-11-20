package com.expense.tracker.repositories;

import com.expense.tracker.models.Transaction;
import com.expense.tracker.repositories.TransactionRepository;
import com.expense.tracker.exceptions.BadRequestException;
import com.expense.tracker.exceptions.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TransactionRepositoryImp implements TransactionRepository {

  private static final String SQL_FIND_ALL = "SELECT transactionID, categoryID, userID, amount, note, transactionDate,"
      + "FROM transactions WHERE userID = ? AND categoryID = ?";

  private static final String SQL_FIND_BY_ID = "SELECT transactionID, categoryID, userID, amount, note, transactionDate,"
      + "FROM transaction WHERE userID = ? AND categoryID = ? AND transactionID = ?";

  private static final String SQL_CREATE = "INSERT INTO transactions (transactionID, categoryID, userID, amount, note, transactionDate) VALUES (NEXTVAL('transactionsSeq'), ?, ?, ?, ?, ?)";

  private static final String SQL_UPDATE = "UPDATE transactions SET amount = ?, note = ?, transactionDate = ?"
      + "WHERE userID = ? AND categoryID = ? AND transactionID = ?";

  private static final String SQL_DELETE = "DELETE FROM transactions WHERE userID = ? AND categoryID = ?"
      + "AND transactionID = ?";

  private RowMapper<Transaction> transactionRowMapper = ((rs, rowNum) -> {
    return new Transaction(
        rs.getInt("transactionID"),
        rs.getInt("categoryID"),
        rs.getInt("userID"),
        rs.getDouble("amount"),
        rs.getString("note"),
        rs.getLong("transactionDate"));
  });

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Override
  public Integer create(Integer userID, Integer categoryID, Double amount, String note, Long transactionDate)
      throws BadRequestException {

    try {
      KeyHolder keyHolder = new GeneratedKeyHolder();

      jdbcTemplate.update(conn -> {
        PreparedStatement ps = conn.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);

        ps.setInt(1, categoryID);
        ps.setInt(2, userID);
        ps.setDouble(3, amount);
        ps.setString(4, note);
        ps.setLong(5, transactionDate);

        return ps;
      }, keyHolder);

      return (Integer) keyHolder.getKeys().get("transactionID");
    } catch (Exception e) {
      throw new BadRequestException("Invalid Request");
    }
  }

  @Override
  public void update(Integer userID, Integer categoryID, Integer transactionID, Transaction transaction)
      throws BadRequestException {

    try {
      jdbcTemplate.update(
          SQL_UPDATE, new Object[] { transaction.getAmount(), transaction.getNote(), transaction.getTransactionDate(),
              userID, categoryID, transactionID });
    } catch (Exception e) {
      throw new BadRequestException("Invalid Request");
    }
  }

  @Override
  public List<Transaction> findAll(Integer userID, Integer categoryID) {
    return jdbcTemplate.query(SQL_FIND_ALL, new Object[] { userID, categoryID }, transactionRowMapper);
  }

  @Override
  public Transaction findByID(Integer userID, Integer categoryID, Integer transactionID)
      throws ResourceNotFoundException {

    try {
      return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[] { userID, categoryID, transactionID },
          transactionRowMapper);
    } catch (Exception e) {
      throw new ResourceNotFoundException("Transaction not found");
    }
  }

  @Override
  public void removeByID(Integer userID, Integer categoryID, Integer transactionID) throws ResourceNotFoundException {

    int count = jdbcTemplate.update(SQL_DELETE, new Object[] { userID, categoryID, transactionID });

    if (count == 0) {
      throw new ResourceNotFoundException("Transaction not found");
    }
  }
}
