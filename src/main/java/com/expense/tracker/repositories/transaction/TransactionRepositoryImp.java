package com.expense.tracker.repositories.transaction;

import com.expense.tracker.models.Transaction;
import com.expense.tracker.repositories.transaction.TransactionRepository;
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

  private static final String SQL_FIND_ALL = "SELECT transactionID, categoryID, user_id, amount, note, transactionDate,"
      + "FROM transactions WHERE user_id = ? AND categoryID = ?";

  private static final String SQL_FIND_BY_ID = "SELECT transactionID, categoryID, user_id, amount, note, transactionDate,"
      + "FROM transaction WHERE user_id = ? AND categoryID = ? AND transactionID = ?";

  private static final String SQL_CREATE = "INSERT INTO transactions (transactionID, categoryID, user_id, amount, note, transactionDate) VALUES (NEXTVAL('transactionsSeq'), ?, ?, ?, ?, ?)";

  private static final String SQL_UPDATE = "UPDATE transactions SET amount = ?, note = ?, transactionDate = ?"
      + "WHERE user_id = ? AND categoryID = ? AND transactionID = ?";

  private static final String SQL_DELETE = "DELETE FROM transactions WHERE user_id = ? AND categoryID = ?"
      + "AND transactionID = ?";

  private RowMapper<Transaction> transactionRowMapper = ((rs, rowNum) -> {
    return new Transaction(
        rs.getInt("transactionID"),
        rs.getInt("categoryID"),
        rs.getInt("user_id"),
        rs.getDouble("amount"),
        rs.getString("note"),
        rs.getLong("transactionDate"));
  });

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Override
  public Integer create(Integer user_id, Integer categoryID, Double amount, String note, Long transactionDate)
      throws BadRequestException {

    try {
      KeyHolder keyHolder = new GeneratedKeyHolder();

      jdbcTemplate.update(conn -> {
        PreparedStatement ps = conn.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);

        ps.setInt(1, categoryID);
        ps.setInt(2, user_id);
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
  public void update(Integer user_id, Integer categoryID, Integer transactionID, Transaction transaction)
      throws BadRequestException {

    try {
      jdbcTemplate.update(
          SQL_UPDATE, new Object[] { transaction.getAmount(), transaction.getNote(), transaction.getTransactionDate(),
              user_id, categoryID, transactionID });
    } catch (Exception e) {
      throw new BadRequestException("Invalid Request");
    }
  }

  @Override
  public List<Transaction> findAll(Integer user_id, Integer categoryID) {
    return jdbcTemplate.query(SQL_FIND_ALL, new Object[] { user_id, categoryID }, transactionRowMapper);
  }

  @Override
  public Transaction findByID(Integer user_id, Integer categoryID, Integer transactionID)
      throws ResourceNotFoundException {

    try {
      return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[] { user_id, categoryID, transactionID },
          transactionRowMapper);
    } catch (Exception e) {
      throw new ResourceNotFoundException("Transaction not found");
    }
  }

  @Override
  public void removeByID(Integer user_id, Integer categoryID, Integer transactionID) throws ResourceNotFoundException {

    int count = jdbcTemplate.update(SQL_DELETE, new Object[] { user_id, categoryID, transactionID });

    if (count == 0) {
      throw new ResourceNotFoundException("Transaction not found");
    }
  }
}
