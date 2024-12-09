package com.example.mudisapp.model;

import com.example.mudisapp.enums.OrderStatus;
import com.example.mudisapp.enums.PaymentMethod;

import java.util.List;

public class OrderModel {
    private int totalAmount;
    private List<MenuModel> orderMenu;
    private String orderDate;
    private OrderStatus orderStatus;
    private PaymentMethod paymentMethod;

    public OrderModel(List<MenuModel> orderMenu, String orderDate, OrderStatus orderStatus, PaymentMethod paymentMethod){
        orderMenu.forEach(menuModel -> {
            totalAmount += menuModel.getPrice();
        });
        this.orderMenu = orderMenu;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.paymentMethod = paymentMethod;
    }


    public List<MenuModel> getOrderMenu() {
        return orderMenu;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public int getTotalAmount() {
        return totalAmount;
    }
}
