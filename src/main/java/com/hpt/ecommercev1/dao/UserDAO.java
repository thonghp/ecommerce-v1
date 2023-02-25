package com.hpt.ecommercev1.dao;

import com.hpt.ecommercev1.entity.Address;
import com.hpt.ecommercev1.entity.HashGenerator;
import com.hpt.ecommercev1.entity.Role;
import com.hpt.ecommercev1.entity.User;
import com.hpt.ecommercev1.utils.DBUtils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

/**
 * Provides APIs for manipulating data with the database
 *
 * @author Hoang Pham Thong
 * @version 1.0
 * @since 2023-2-2
 */

public class UserDAO implements Serializable {
    private static UserDAO instance;

    private UserDAO() {

    }

    public static UserDAO getInstance() {
        if (instance == null)
            instance = new UserDAO();

        return instance;
    }

    /**
     * Insert a new address into the database
     *
     * @param address The address to be inserted.
     * @param UserId  The id of the User who owns the address.
     */
    public void insertAddress(Address address, int UserId) {
        String sql = "INSERT INTO addresses (street, ward, district, city, user_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBUtils.openConnection();
             PreparedStatement stm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stm.setString(1, address.getStreet());
            stm.setString(2, address.getWard());
            stm.setString(3, address.getDistrict());
            stm.setString(4, address.getCity());
            stm.setInt(5, UserId);

            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Save a User to the database.
     *
     * @param user The User entity to be saved.
     */

    public void save(User user) {
        String sqlInsertUser = "INSERT INTO users (first_name, last_name, phone_number, image_path, email, "
                + "password, enabled) value(?, ?, ?, ?, ?, ?, ?)";
        String sqlInsertUserRole = "INSERT INTO user_role (user_id, role_id) value(?, ?)";

        try (Connection conn = DBUtils.openConnection();
             PreparedStatement userStm = conn.prepareStatement(sqlInsertUser, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement userRoleStm = conn.prepareStatement(sqlInsertUserRole)) {

            userStm.setString(1, user.getFirstName());
            userStm.setString(2, user.getLastName());
            userStm.setString(3, user.getPhoneNumber());

            userStm.setString(4, user.getImagePath());

            userStm.setString(5, user.getEmail());
            userStm.setString(6, HashGenerator.generateMD5(user.getPassword()));
            userStm.setBoolean(7, user.isEnabled());

            userStm.executeUpdate();

            // get the id of the inserted user
            ResultSet rs = userStm.getGeneratedKeys();
            int userKey;

            // If the result set is not empty, get the id
            if (rs.next()) {
                userKey = rs.getInt(1);

                insertAddress(user.getAddress(), userKey);

                Set<Role> roles = user.getRoles();
                for (Role role : roles) {
                    userRoleStm.setInt(1, userKey);
                    userRoleStm.setInt(2, role.getId());
                    userRoleStm.addBatch();
                }

                // execute multiple commands at the same time
                userRoleStm.executeBatch();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Return list of roles by user id.
     *
     * @param id The id of the user.
     * @return A list of Role entities.
     */
    public Set<Role> findRolesByUserId(Integer id) {
        Set<Role> roles = new HashSet<>();
        String sql = "SELECT r.* FROM user_role ur INNER JOIN roles r ON ur.role_id = r.id " +
                "WHERE ur.user_id = ?";

        try (Connection conn = DBUtils.openConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, id);

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Integer roleId = rs.getInt(1);
                    String roleName = rs.getString(2);
                    String description = rs.getString(3);

                    Role role = new Role(roleId, roleName, description);

                    roles.add(role);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roles;
    }

    /**
     * Retrieves a User entity by its id.
     *
     * @param id An id specifying the id of the User
     * @return User entity if the User entity with the given id exists, null otherwise.
     */
    public User findById(Integer id) {

        // use left join to get the address, user_role and role even though it is null
        String sql = "SELECT u.*, a.id, a.street, a.ward, a.district, a.city " +
                "FROM users u  LEFT JOIN addresses a ON u.id = a.user_id WHERE u.id = ?";

        try (Connection conn = DBUtils.openConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, id);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    String firstName = rs.getString(2);
                    String lastName = rs.getString(3);
                    String phoneNumber = rs.getString(4);
                    String imagePath = rs.getString(5);
                    String email = rs.getString(6);
                    String password = rs.getString(7);
                    boolean enabled = rs.getBoolean(8);
                    Integer addressId = rs.getInt(9);
                    String street = rs.getString(10);
                    String ward = rs.getString(11);
                    String district = rs.getString(12);
                    String city = rs.getString(13);

                    Address address = new Address();
                    address.setId(addressId);
                    address.setStreet(street);
                    address.setWard(ward);
                    address.setDistrict(district);
                    address.setCity(city);

                    User user = new User();
                    user.setId(id);
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setPhoneNumber(phoneNumber);
                    user.setAddress(address);
                    user.setImagePath(imagePath);
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setEnabled(enabled);

                    findRolesByUserId(id).forEach(user::addRole);

                    return user;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves a user entity by its email.
     *
     * @param email An email specifying the email of the user
     * @return User entity if the user entity with the given email exists, null otherwise.
     */
    public User findByEmail(String email) {

        // use left join to get the address, user_role and role even though it is null
        String sql = "SELECT u.*, a.id, a.street, a.ward, a.district, a.city " +
                "FROM users u  LEFT JOIN addresses a ON u.id = a.user_id WHERE u.email = ?";

        try (Connection conn = DBUtils.openConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, email);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    Integer id = rs.getInt(1);
                    String firstName = rs.getString(2);
                    String lastName = rs.getString(3);
                    String phoneNumber = rs.getString(4);
                    String imagePath = rs.getString(5);
                    String password = rs.getString(7);
                    boolean enabled = rs.getBoolean(8);
                    Integer addressId = rs.getInt(9);
                    String street = rs.getString(10);
                    String ward = rs.getString(11);
                    String district = rs.getString(12);
                    String city = rs.getString(13);

                    Address address = new Address();
                    address.setId(addressId);
                    address.setStreet(street);
                    address.setWard(ward);
                    address.setDistrict(district);
                    address.setCity(city);

                    User user = new User();
                    user.setId(id);
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setPhoneNumber(phoneNumber);
                    user.setAddress(address);
                    user.setImagePath(imagePath);
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setEnabled(enabled);

                    findRolesByUserId(id).forEach(user::addRole);

                    return user;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates an address record in the database.
     *
     * @param address The address to be updated.
     * @param userId  The id of the User who owns the address.
     */
    public void updateAddress(Address address, Integer userId) {
        String sql = "UPDATE addresses SET street = ?, ward = ?, district = ?, city = ? WHERE user_id = ? AND id = ?";
        try (Connection conn = DBUtils.openConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, address.getStreet());
            stm.setString(2, address.getWard());
            stm.setString(3, address.getDistrict());
            stm.setString(4, address.getCity());
            stm.setInt(5, userId);
            stm.setInt(6, address.getId());

            stm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete a user_role record from the database.
     *
     * @param userId The id of the user.
     */
    public void deleteUserRole(Integer userId) {
        String sql = "DELETE FROM user_role WHERE user_id = ?";
        try (Connection conn = DBUtils.openConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, userId);

            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update a user in the database. True if the user is updated successfully, false otherwise.
     *
     * @param user The user entity to be updated.
     */

    public void update(User user) {
        String updateUser = "UPDATE users SET first_name = ?, last_name = ?, phone_number = ?, " +
                "image_path = ?, email = ?, password = ?, enabled = ? WHERE id = ?";
        String sqlInsertUserRole = "INSERT INTO user_role (user_id, role_id) value(?, ?)";
        try (Connection conn = DBUtils.openConnection();
             PreparedStatement stm = conn.prepareStatement(updateUser);
             PreparedStatement userRoleStm = conn.prepareStatement(sqlInsertUserRole)) {
            stm.setString(1, user.getFirstName());
            stm.setString(2, user.getLastName());
            stm.setString(3, user.getPhoneNumber());
            stm.setString(4, user.getImagePath());

            stm.setString(5, user.getEmail());
            stm.setString(6, user.getPassword());
            stm.setBoolean(7, user.isEnabled());
            stm.setInt(8, user.getId());

            stm.executeUpdate();

            updateAddress(user.getAddress(), user.getId());

            deleteUserRole(user.getId());

            Set<Role> roles = user.getRoles();
            for (Role role : roles) {
                userRoleStm.setInt(1, user.getId());
                userRoleStm.setInt(2, role.getId());
                userRoleStm.addBatch();
            }

            // execute multiple commands at the same time
            userRoleStm.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete a user from the database.
     *
     * @param id The id of the user to be deleted.
     */

    public void delete(Integer id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DBUtils.openConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, id);

            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Total results keyword search.
     *
     * @return The number of results. 0 if no results.
     */
    public int countByKeyword(String keyword) {
        String sql = "SELECT COUNT(id) FROM users WHERE CONCAT(email, ' ', first_name, ' ', last_name) LIKE ?";
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

    /**
     * Update enable status of a user.
     *
     * @param id      The id of the user to be updated.
     * @param enabled The updated enable status.
     */
    public void updateEnableStatus(Integer id, boolean enabled) {
        String sql = "UPDATE users SET enabled = ? WHERE id = ?";

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
     * Get the user corresponding to the given email and password.
     *
     * @param email    The email of the user.
     * @param password The password of the user.
     * @return The user corresponding to the given email and password. Null if no such user exists.
     */
    public User doLogin(String email, String password) {
        String sql = "SELECT id, first_name, last_name, email, image_path FROM users WHERE email = ? AND password = ? AND enabled = 1";

        try (Connection conn = DBUtils.openConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, email);
            stm.setString(2, password);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt(1));
                    user.setFirstName(rs.getString(2));
                    user.setLastName(rs.getString(3));
                    user.setEmail(rs.getString(4));
                    user.setImagePath(rs.getString(5));

                    findRolesByUserId(user.getId()).forEach(user::addRole);
                    return user;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get address by user id.
     *
     * @param userId The id of the user.
     * @return The address entity.
     */
    public Address findAddressByUserId(Integer userId) {
        String sql = "SELECT street, ward, district, city FROM addresses WHERE user_id = ?";

        try (Connection conn = DBUtils.openConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, userId);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    String street = rs.getString(1);
                    String ward = rs.getString(2);
                    String district = rs.getString(3);
                    String city = rs.getString(4);

                    Address address = new Address();

                    address.setStreet(street);
                    address.setWard(ward);
                    address.setDistrict(district);
                    address.setCity(city);

                    return address;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns all instance of user.
     *
     * @param sortType  Specify the sort type, ASC or DESC.
     * @param pageSize  Specify the number of records per page.
     * @param sortField Specify the column name to sort.
     * @param pageIndex Specify the page index.
     * @return List of User entities.
     */
    public List<User> findAll(String keyword, String sortField, String sortType, int pageSize, int pageIndex) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, image_path, first_name, last_name, email, phone_number, enabled " +
                "FROM users WHERE CONCAT(email, ' ', last_name, ' ', first_name) LIKE ? " +
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
                    String imagePath = rs.getString(2);
                    String firstName = rs.getString(3);
                    String lastName = rs.getString(4);
                    String email = rs.getString(5);
                    String phoneNumber = rs.getString(6);
                    boolean enabled = rs.getBoolean(7);

                    Address address = findAddressByUserId(id);

                    User user = new User();

                    user.setId(id);
                    user.setImagePath(imagePath);
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setEmail(email);
                    user.setPhoneNumber(phoneNumber);
                    user.setEnabled(enabled);
                    user.setAddress(address);

                    findRolesByUserId(id).forEach(user::addRole); // forEach(role -> User.addRole(role));

                    users.add(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Check if the email is already in use.
     *
     * @param email The email to be checked.
     * @return True if the email is already in use, false otherwise.
     */
    public boolean checkEmailExists(String email) {
        String sql = "SELECT COUNT(email) FROM users WHERE email = ?";
        try (Connection conn = DBUtils.openConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, email);

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
}
