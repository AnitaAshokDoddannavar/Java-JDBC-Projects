package com.foodordering;

public class ValidationUtil {

    // Validate Customer Name
    public static boolean isValidName(String name) {

        return name != null
                && !name.trim().isEmpty()
                && name.matches("[A-Za-z ]+");
    }

    // Validate Email
    public static boolean isValidEmail(String email) {

        return email != null
                && email.matches(
                    "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
                );
    }

    // Validate Phone Number
    public static boolean isValidPhone(String phone) {

        return phone != null
                && phone.matches("[0-9]{10}");
    }

    // Validate Positive Integer
    public static boolean isValidPositiveNumber(int number) {

        return number > 0;
    }
}