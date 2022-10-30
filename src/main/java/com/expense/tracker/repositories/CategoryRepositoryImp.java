package com.expense.tracker.repositories;

import javax.swing.tree.RowMapper;

import com.expense.tracker.exceptions.ResourceNotFoundException;
import com.expense.tracker.repositories.CategoryRepository;

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

}
