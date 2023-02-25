package com.hpt.ecommercev1.dao;

import com.hpt.ecommercev1.entity.Role;
import com.hpt.ecommercev1.utils.DBUtils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO implements Serializable {
    private static RoleDAO instance;

    private RoleDAO() {
    }

    public static RoleDAO getInstance() {
        if (instance == null) {
            instance = new RoleDAO();
        }
        return instance;
    }

    /**
     * Return all roles in the database except admin
     *
     * @return a list of roles except admin. If there is no role in the database, return an empty list.
     */
    public List<Role> findAllRolesExceptAdmin() {
        List<Role> roles = new ArrayList<>();
        String sql = "SELECT * FROM roles WHERE id != 1";

        try (Connection conn = DBUtils.openConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Integer id = rs.getInt("id");
                    String name = rs.getString("name");
                    String description = rs.getString("description");

                    Role role = new Role(id, name, description);

                    roles.add(role);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return roles;
    }
}
