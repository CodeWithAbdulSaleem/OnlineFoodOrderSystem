package com.dao;

import com.models.*;
import com.utils.DBConnection;
import java.sql.*;

public class UserDAO {
    public User findByEmail(String email) throws SQLException {
        String q = "SELECT * FROM users WHERE email=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(q)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                u.setPasswordHash(rs.getString("password_hash"));
                u.setAdmin(rs.getInt("is_admin") == 1);
                return u;
            }
        }
        return null;
    }

    public int create(User u) throws SQLException {
        String q = "INSERT INTO users(name,email,password_hash,is_admin) VALUES(?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(q, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPasswordHash());
            ps.setInt(4, u.isAdmin() ? 1 : 0);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        return -1;
    }
}
