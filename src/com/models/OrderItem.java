package com.models;

public class OrderItem {
    private int id;
    private int orderId;
    private int menuId;
    private String menuName;
    private int quantity;
    private double priceEach;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getMenuId() { return menuId; }
    public void setMenuId(int menuId) { this.menuId = menuId; }

    public String getMenuName() { return menuName; }
    public void setMenuName(String menuName) { this.menuName = menuName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPriceEach() { return priceEach; }
    public void setPriceEach(double priceEach) { this.priceEach = priceEach; }
}
