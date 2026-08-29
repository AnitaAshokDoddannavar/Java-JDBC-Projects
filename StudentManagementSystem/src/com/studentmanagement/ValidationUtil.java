package com.studentmanagement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidationUtil {

    public static boolean isValidName(String name) {
        return name != null &&
               name.trim().matches("[A-Za-z ]+");
    }

    public static boolean isValidEmail(String email) {
        return email != null &&
               email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }
    
    public static boolean isValidPhone(String phone) {
        return phone != null &&
               phone.matches("[0-9]{10}");
    }

    public static boolean isValidPositiveNumber(int number) {
        return number > 0;
    }

    public static boolean isValidMarks(int marks) {
        return marks >= 0 && marks <= 100;
    }

    public static boolean isValidDuration(int duration) {
        return duration > 0 && duration <= 60;
    }

    public static boolean isValidAttendanceStatus(String status) {
        return status != null &&
               (status.equalsIgnoreCase("Present") ||
                status.equalsIgnoreCase("Absent"));
    }

    public static boolean isValidDate(String date) {

        try {
            LocalDate.parse(
                date,
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
            );
            return true;

        } catch (DateTimeParseException e) {
            return false;
        }
    }
}