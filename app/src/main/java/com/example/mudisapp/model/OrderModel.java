package com.example.mudisapp.model;

import com.example.mudisapp.enums.OrderStatus;
import com.example.mudisapp.enums.PaymentMethod;

import java.util.List;

public class OrderModel {
    private int totalAmount;
    private List<MenuModel> orderMenu;
    private String orderDate;
    private OrderStatus orderStatus;
    private String paymentMethod;
    private String id;

    public OrderModel(List<MenuModel> orderMenu, String orderDate, String paymentMethod){
        orderMenu.forEach(menuModel -> {
            totalAmount += menuModel.getPrice();
        });
        this.orderMenu = orderMenu;
        this.orderDate = orderDate;
        this.orderStatus = OrderStatus.InPROCESS;
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public String getId() {
        return id;
    }
    public void setId(String id){this.id = id;}

}
