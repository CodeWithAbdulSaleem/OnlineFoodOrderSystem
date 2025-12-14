package com.dao;

import com.models.*;
import com.utils.DBConnection;
import java.sql.*;
import java.util.*;

public class CartDAO {
    public void addOrUpdate(int userId, int menuId, int qty) throws SQLException {
        String find = "SELECT id, quantity FROM cart_items WHERE user_id=? AND menu_id=?";
        String ins = "INSERT INTO cart_items(user_id, menu_id, quantity) VALUES(?,?,?)";
        String upd = "UPDATE cart_items SET quantity=? WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement psFind = c.prepareStatement(find)) {
            psFind.setInt(1, userId);
            psFind.setInt(2, menuId);
            ResultSet rs = psFind.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                int newQ = rs.getInt("quantity") + qty;
                try (PreparedStatement psUpd = c.prepareStatement(upd)) {
                    psUpd.setInt(1, newQ);
                    psUpd.setInt(2, id);
                    psUpd.executeUpdate();
                }
            } else {
                try (PreparedStatement psIns = c.prepareStatement(ins)) {
                    psIns.setInt(1, userId);
                    psIns.setInt(2, menuId);
                    psIns.setInt(3, qty);
                    psIns.executeUpdate();
                }
            }
        }
    }

    public List<CartItem> list(int userId) throws SQLException {
        String q = "SELECT c.id, c.menu_id, c.quantity, m.name, m.price FROM cart_items c JOIN menu m ON m.id=c.menu_id WHERE c.user_id=?";
        List<CartItem> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(q)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CartItem ci = new CartItem();
                ci.setId(rs.getInt("id"));
                ci.setMenuId(rs.getInt("menu_id"));
                ci.setQuantity(rs.getInt("quantity"));
                ci.setMenuName(rs.getString("name"));
                ci.setPrice(rs.getDouble("price"));
                ci.setLineTotal(rs.getDouble("price") * rs.getInt("quantity"));
                list.add(ci);
            }
        }
        return list;
    }

    public void remove(int userId, int cartItemId) throws SQLException {
        String q = "DELETE FROM cart_items WHERE id=? AND user_id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(q)) {
            ps.setInt(1, cartItemId);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }

    public void clear(int userId) throws SQLException {
        String q = "DELETE FROM cart_items WHERE user_id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(q)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }
}
