package com.dao;

import com.models.*;
import com.utils.DBConnection;
import java.sql.*;
import java.util.*;

public class OrderDAO {
    public int createOrder(int userId, double total, String address, String paymentMethod) throws SQLException {
        String q = "INSERT INTO orders(user_id,total,address,payment_method) VALUES(?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(q, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            ps.setDouble(2, total);
            ps.setString(3, address);
            ps.setString(4, paymentMethod);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        return -1;
    }

    public void addOrderItem(int orderId, int menuId, int qty, double priceEach) throws SQLException {
        String q = "INSERT INTO order_items(order_id, menu_id, quantity, price_each) VALUES(?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(q)) {
            ps.setInt(1, orderId);
            ps.setInt(2, menuId);
            ps.setInt(3, qty);
            ps.setDouble(4, priceEach);
            ps.executeUpdate();
        }
    }

    public List<Order> listByUser(int userId) throws SQLException {
        String q = "SELECT * FROM orders WHERE user_id=? ORDER BY created_at DESC";
        List<Order> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(q)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("id"));
                o.setUserId(rs.getInt("user_id"));
                o.setTotal(rs.getDouble("total"));
                o.setAddress(rs.getString("address"));
                o.setPaymentMethod(rs.getString("payment_method"));
                o.setStatus(rs.getString("status"));
                o.setCreatedAt(rs.getString("created_at"));
                list.add(o);
            }
        }
        String qi = "SELECT oi.*, m.name FROM order_items oi JOIN menu m ON m.id=oi.menu_id WHERE oi.order_id=?";
        try (Connection c = DBConnection.getConnection()) {
            for (Order o : list) {
                List<com.models.OrderItem> items = new ArrayList<>();
                try (PreparedStatement ps = c.prepareStatement(qi)) {
                    ps.setInt(1, o.getId());
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        com.models.OrderItem it = new com.models.OrderItem();
                        it.setId(rs.getInt("id"));
                        it.setOrderId(rs.getInt("order_id"));
                        it.setMenuId(rs.getInt("menu_id"));
                        it.setMenuName(rs.getString("name"));
                        it.setQuantity(rs.getInt("quantity"));
                        it.setPriceEach(rs.getDouble("price_each"));
                        items.add(it);
                    }
                }
                o.setItems(items);
            }
        }
        return list;
    }
}
