package com.expense.tracker.repositories;

import com.expense.tracker.exceptions.AuthException;
import com.expense.tracker.models.User;

import org.mindrot.jbcrypt.BCrypt;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import org.springframework.stereotype.Repository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class UserRepositoryImp implements UserRepository {

 private static final String SQL_CREATE = "INSERT INTO users(userID, firstName, lastName, email, password) VALUES(NEXTVAL('usersSeq'), ?, ?, ?, ?)";

 private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM users WHERE email = ?";

 private static final String SQL_FIND_BY_ID = "SELECT userID, firstName, lastName, email, password "
   + "FROM users WHERE userID = ?";

 private static final String SQL_FIND_BY_EMAIL = "SELECT userID, firstName, lastName, email, password "
   + "FROM users WHERE email = ?";

 @Autowired
 JdbcTemplate jdbcTemplate;

 private RowMapper<User> userRowMapper = ((rs, rowNum) -> {
  return new User(
    rs.getInt("userID"),
    rs.getString("firstName"),
    rs.getString("lastName"),
    rs.getString("email"),
    rs.getString("password"));
 });

 @Override
 public Integer create(String firstName, String lastName, String email, String password) throws AuthException {
  String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));

  try {
   KeyHolder keyHolder = new GeneratedKeyHolder();

   jdbcTemplate.update(connection -> {
    PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
    ps.setString(1, firstName);
    ps.setString(2, lastName);
    ps.setString(3, email);
    ps.setString(4, hashedPassword);

    return ps;
   }, keyHolder);

   return (Integer) keyHolder.getKeys().get("userID");

  } catch (Exception e) {
   throw new AuthException("Failed to create user");
  }
 }

 @Override
 public User findByEmailAndPassword(String email, String password) throws AuthException {

  try {
   User user = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, new Object[] { email }, userRowMapper);

   if (!BCrypt.checkpw(password, user.getPassword())) {
    throw new AuthException("Invalid email/password");
   }
   return user;

  } catch (EmptyResultDataAccessException e) {
   throw new AuthException("Invalid email/password");
  }
 }

 @Override
 public Integer getCountByEmail(String email) {
  return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, new Object[] { email }, Integer.class);
 }

 @Override
 public User findByID(Integer userID) {
  return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[] { userID }, userRowMapper);
 }
}