package com.dao;

import com.models.*;
import com.utils.DBConnection;
import java.sql.*;
import java.util.*;

public class MenuDAO {
    public List<MenuItem> listAll() throws SQLException {
        String q = "SELECT * FROM menu WHERE is_available=1 ORDER BY created_at DESC";
        List<MenuItem> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(q);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                MenuItem m = new MenuItem();
                m.setId(rs.getInt("id"));
                m.setName(rs.getString("name"));
                m.setDescription(rs.getString("description"));
                m.setPrice(rs.getDouble("price"));
                m.setAvailable(rs.getInt("is_available") == 1);
                list.add(m);
            }
        }
        return list;
    }

    public int add(MenuItem m) throws SQLException {
        String q = "INSERT INTO menu(name, description, price) VALUES(?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(q, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, m.getName());
            ps.setString(2, m.getDescription());
            ps.setDouble(3, m.getPrice());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        return -1;
    }
}
