package com.foodordering;

public class OrderTest {

    public static void main(String[] args) {

        OrderDAO dao = new OrderDAO();

        // dao.placeOrder();
        
        // dao.viewOrderHistory(1);
        
        dao.generateBill(2);;
    }
}