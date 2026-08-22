package com.inventorymanagement;

public class ProductTest {

    public static void main(String[] args) {

        ProductDAO dao = new ProductDAO();

        /*dao.addProduct(
                "Webcam",
                2500.00,
                15,
                1
        );*/
        
       // dao.viewProducts();
        
        // dao.updateStock(1, 15);
        
        // dao.recordSale(1, 2);
        
        // dao.addProductsBatch();
        
        dao.generateReport();
        
    }
}