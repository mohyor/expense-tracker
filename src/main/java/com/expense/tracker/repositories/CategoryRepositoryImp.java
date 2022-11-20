package com.expense.tracker.repositories;

import com.expense.tracker.repositories.CategoryRepository;

import com.expense.tracker.models.Category;
import com.expense.tracker.exceptions.BadRequestException;
import com.expense.tracker.exceptions.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.util.pattern.PathPattern;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class CategoryRepositoryImp implements CategoryRepository {

        private static final String SQL_FIND_ALL = "SELECT c.categoryID, c.userID, c.title, c.description, "
                        + "COALESCE(SUM(t.amount), 0) total_expense"
                        + "FROM transactions t RIGHT OUTER JOIN categories c ON c.categoryID = t.categoryID"
                        + "WHERE c.userID = ? GROUP BY c.categoryID";

        private static final String SQL_FIND_BY_ID = "SELECT c.categoryID, c.userID, c.title, c.description,"
                        + "COALESCE(SUM(t.amount), 0) total_expense"
                        + "FROM transactions t RIGHT OUTER JOIN categories c ON c.categoryID = t.categoryID"
                        + "WHERE c.userID = ? AND c.categoryID = ? GROUP BY c.categoryID";

        private static final String SQL_CREATE = "INSERT INTO categories (categoryID, userID, title, description)"
                        + "VALUES(nextval('categoriesSEQ'), ?, ?, ?)";

        private static final String SQL_UPDATE = "UPDATE categories SET title = ?, description = ?"
                        + "WHERE userID = ? AND categoryID = ?";

        private static final String SQL_DELETE_CATEGORY = "DELETE FROM categories WHERE userID = ? AND categoryID = ?";

        private static final String SQL_DELETE_TRANSACTIONS = "DELETE FROM transactions WHERE categoryID = ?";

        @Autowired
        JdbcTemplate jdbcTemplate;

        private RowMapper<Category> categoryRowMapper = ((rs, rowNum) -> {
                return new Category(
                                rs.getInt("categoryID"),
                                rs.getInt("userID"),
                                rs.getString("title"),
                                rs.getString("description"),
                                rs.getDouble("totalExpense"));
        });

        @Override
        public List<Category> findAll(Integer userID) throws ResourceNotFoundException {
                return jdbcTemplate.query(SQL_FIND_ALL, new Object[] { userID }, categoryRowMapper);
        }

        @Override
        public Category findByID(Integer userID, Integer categoryID) throws ResourceNotFoundException {
                try {
                        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[] { userID, categoryID },
                                        categoryRowMapper);
                } catch (Exception e) {
                        throw new ResourceNotFoundException("Category not found");
                }
        }

        @Override
        public Integer create(Integer userID, String title, String description) throws BadRequestException {
                try {
                        KeyHolder keyHolder = new GeneratedKeyHolder();
                        jdbcTemplate.update(connection -> {
                                PreparedStatement ps = connection.prepareStatement(SQL_CREATE,
                                                Statement.RETURN_GENERATED_KEYS);
                                ps.setInt(1, userID);
                                ps.setString(2, title);
                                ps.setString(3, description);

                                return ps;
                        }, keyHolder);

                        return (Integer) keyHolder.getKeys().get("categoryID");
                } catch (Exception e) {
                        throw new BadRequestException("Invalid Request");
                }
        }

        @Override
        public void update(Integer userID, Integer categoryID, Category category) throws BadRequestException {
                try {
                        jdbcTemplate.update(SQL_UPDATE, new Object[] { category.getTitle(), category.getDescription(),
                                        userID, categoryID });
                } catch (Exception e) {
                        throw new BadRequestException("Invalid Request");
                }
        }

        @Override
        public void removeByID(Integer userID, Integer categoryID) {
                this.removeAllCatTransactions(categoryID);
                jdbcTemplate.update(SQL_DELETE_CATEGORY, new Object[] { userID, categoryID });
        }

        private void removeAllCatTransactions(Integer categoryID) {
                jdbcTemplate.update(SQL_DELETE_TRANSACTIONS, new Object[] { categoryID });
        }

}
