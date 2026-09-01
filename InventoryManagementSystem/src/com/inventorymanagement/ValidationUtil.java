package com.inventorymanagement;

public class ValidationUtil {

	public static boolean isValidProductName(String name) {
	    return name != null
	            && name.matches(".*[A-Za-z].*")
	            && name.matches("[A-Za-z0-9 ]+");
	}

    public static boolean isValidPrice(double price) {
        return price > 0;
    }

    public static boolean isValidQuantity(int quantity) {
        return quantity > 0;
    }

    public static boolean isValidId(int id) {
        return id > 0;
    }
}