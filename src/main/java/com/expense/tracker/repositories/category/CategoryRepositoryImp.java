package com.expense.tracker.repositories;

import com.expense.tracker.repositories.category.CategoryRepository;

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

        private static final String SQL_FIND_ALL = "SELECT c.categoryID, c.user_id, c.title, c.description, "
                        + "COALESCE(SUM(t.amount), 0) total_expense"
                        + "FROM transactions t RIGHT OUTER JOIN categories c ON c.categoryID = t.categoryID"
                        + "WHERE c.user_id = ? GROUP BY c.categoryID";

        private static final String SQL_FIND_BY_ID = "SELECT c.categoryID, c.user_id, c.title, c.description,"
                        + "COALESCE(SUM(t.amount), 0) total_expense"
                        + "FROM transactions t RIGHT OUTER JOIN categories c ON c.categoryID = t.categoryID"
                        + "WHERE c.user_id = ? AND c.categoryID = ? GROUP BY c.categoryID";

        private static final String SQL_CREATE = "INSERT INTO categories (categoryID, user_id, title, description)"
                        + "VALUES(nextval('categoriesSEQ'), ?, ?, ?)";

        private static final String SQL_UPDATE = "UPDATE categories SET title = ?, description = ?"
                        + "WHERE user_id = ? AND categoryID = ?";

        private static final String SQL_DELETE_CATEGORY = "DELETE FROM categories WHERE user_id = ? AND categoryID = ?";

        private static final String SQL_DELETE_TRANSACTIONS = "DELETE FROM transactions WHERE categoryID = ?";

        @Autowired
        JdbcTemplate jdbcTemplate;

        private RowMapper<Category> categoryRowMapper = ((rs, rowNum) -> {
                return new Category(
                                rs.getInt("categoryID"),
                                rs.getInt("user_id"),
                                rs.getString("title"),
                                rs.getString("description"),
                                rs.getDouble("totalExpense"));
        });

        @Override
        public List<Category> findAll(Integer user_id) throws ResourceNotFoundException {
                return jdbcTemplate.query(SQL_FIND_ALL, new Object[] { user_id }, categoryRowMapper);
        }

        @Override
        public Category findByID(Integer user_id, Integer categoryID) throws ResourceNotFoundException {
                try {
                        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[] { user_id, categoryID },
                                        categoryRowMapper);
                } catch (Exception e) {
                        throw new ResourceNotFoundException("Category not found");
                }
        }

        @Override
        public Integer create(Integer user_id, String title, String description) throws BadRequestException {
                try {
                        KeyHolder keyHolder = new GeneratedKeyHolder();
                        jdbcTemplate.update(connection -> {
                                PreparedStatement ps = connection.prepareStatement(SQL_CREATE,
                                                Statement.RETURN_GENERATED_KEYS);
                                ps.setInt(1, user_id);
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
        public void update(Integer user_id, Integer categoryID, Category category) throws BadRequestException {
                try {
                        jdbcTemplate.update(SQL_UPDATE, new Object[] { category.getTitle(), category.getDescription(),
                                        user_id, categoryID });
                } catch (Exception e) {
                        throw new BadRequestException("Invalid Request");
                }
        }

        @Override
        public void removeByID(Integer user_id, Integer categoryID) {
                this.removeAllCatTransactions(categoryID);
                jdbcTemplate.update(SQL_DELETE_CATEGORY, new Object[] { user_id, categoryID });
        }

        private void removeAllCatTransactions(Integer categoryID) {
                jdbcTemplate.update(SQL_DELETE_TRANSACTIONS, new Object[] { categoryID });
        }

}
