package com.hpt.ecommercev1.dao;

import com.hpt.ecommercev1.entity.Category;
import com.hpt.ecommercev1.utils.DBUtils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides APIs for manipulating data with the database
 *
 * @author Hoang Pham Thong
 * @version 1.0
 * @since 2023-2-27
 */
public class CategoryDAO implements Serializable {
    private static CategoryDAO instance;

    private CategoryDAO() {

    }

    public static CategoryDAO getInstance() {
        if (instance == null)
            instance = new CategoryDAO();

        return instance;
    }

    /**
     * Returns all instance of Category with pagination
     *
     * @param sortType  Specify the sort type, ASC or DESC.
     * @param pageSize  Specify the number of records per page.
     * @param sortField Specify the column name to sort.
     * @param pageIndex     Specify the page index.
     * @return List of Category entities.
     */
    public List<Category> findAll(String keyword, String sortField, String sortType, int pageSize, int pageIndex) {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories WHERE name LIKE ? " +
                "ORDER BY " + sortField + " " + sortType + " LIMIT ? OFFSET ?";

        // use try-with-resources Statement to auto close the connection.
        try (Connection conn = DBUtils.openConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setString(1, "%" + keyword + "%");
            stm.setInt(2, pageSize);
            stm.setInt(3, (pageIndex - 1) * pageSize);

            // use try-with-resources Statement to auto close the ResultSet.
            try (ResultSet rs = stm.executeQuery()) {
                // fetch data from result set
                while (rs.next()) {
                    Integer id = rs.getInt(1);
                    String name = rs.getString(2);
                    String alias = rs.getString(3);
                    int parentId = rs.getInt(4);
                    boolean enabled = rs.getBoolean(5);

                    categories.add(new Category(id, name, alias, parentId, enabled));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return categories;
    }

    /**
     * Save a category to the database.
     *
     * @param category The category entity to be saved.
     * @return True if the category is saved successfully, false otherwise.
     */
    public boolean save(Category category) {
        String sql = "INSERT INTO categories (name, alias, parent_id, enabled) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBUtils.openConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setString(1, category.getName());
            stm.setString(2, category.getAlias());

            if (category.getParentId() == 0)
                stm.setNull(3, Types.INTEGER);
            else
                stm.setInt(3, category.getParentId());

            stm.setBoolean(4, category.isEnabled());

            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves a category entity by its id.
     *
     * @param id An id specifying the id of the category.
     * @return category entity if the category entity with the given id exists, null otherwise.
     */
    public Category findById(Integer id) {
        String sql = "SELECT * FROM categories WHERE id = ?";

        try (Connection conn = DBUtils.openConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, id);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString(2);
                    String alias = rs.getString(3);
                    Integer parentId = rs.getInt(4);
                    boolean enabled = rs.getBoolean(5);

                    return new Category(id, name, alias, parentId, enabled);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves a category entity by its name.
     *
     * @param name A name specifying the name of the category.
     * @return category entity if the category entity with the given name exists, null otherwise.
     */
    public Category findByName(String name) {
        String sql = "SELECT * FROM categories WHERE name LIKE ?";

        try (Connection conn = DBUtils.openConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, "%" + name + "%");

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    Integer id = rs.getInt(1);
                    String alias = rs.getString(3);
                    Integer parentId = rs.getInt(4);
                    boolean enabled = rs.getBoolean(5);

                    Category category = new Category();
                    category.setId(id);
                    category.setName(name);
                    category.setAlias(alias);
                    category.setParentId(parentId);
                    category.setEnabled(enabled);

                    return category;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Update a Category in the database.
     *
     * @param category The Category entity to be updated.
     * @return True if the Category is updated successfully, false otherwise.
     */
    public boolean update(Category category) {
        String sql = "UPDATE categories SET name = ?, alias = ?, parent_id = ?, enabled = ? WHERE id = ?";

        try (Connection conn = DBUtils.openConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, category.getName());
            stm.setString(2, category.getAlias());

            if (category.getParentId() == 0)
                stm.setNull(3, Types.INTEGER);
            else
                stm.setInt(3, category.getParentId());

            stm.setBoolean(4, category.isEnabled());
            stm.setInt(5, category.getId());

            return stm.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Check if the parent id is already used.
     *
     * @param parentId The parent id to be checked.
     * @return True if the parent id is already in use, false otherwise.
     */
    public boolean checkParentIdExists(int parentId) {
        String sql = "SELECT COUNT(parent_id) FROM categories WHERE parent_id = ?";
        try (Connection conn = DBUtils.openConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, parentId);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Delete a category record from the database.
     *
     * @param categoryId The id of the category.
     */
    public void delete(Integer categoryId) {
        String sql = "DELETE FROM categories WHERE id = ?";
        try (Connection conn = DBUtils.openConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, categoryId);

            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update enabled status of a category.
     *
     * @param id      The id of the category to be updated.
     * @param enabled The updated enabled status.
     */
    public void updateEnabledStatus(Integer id, boolean enabled) {
        String sql = "UPDATE categories SET enabled = ? WHERE id = ?";

        try (Connection conn = DBUtils.openConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setBoolean(1, enabled);
            stm.setInt(2, id);

            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Total number of categories in the database.
     *
     * @return The number of category entities.
     */
    public int count() {
        String sql = "SELECT COUNT(id) FROM categories";
        try (Connection conn = DBUtils.openConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next())
                    return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Total results keyword search.
     *
     * @return The number of results. 0 if no results.
     */
    public int countByKeyword(String keyword) {
        String sql = "SELECT COUNT(id) FROM categories WHERE name LIKE ?";
        try (Connection conn = DBUtils.openConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, "%" + keyword + "%");
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next())
                    return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}