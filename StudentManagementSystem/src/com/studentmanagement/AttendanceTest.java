package com.studentmanagement;

public class AttendanceTest {

    public static void main(String[] args) {

        AttendanceDAO dao = new AttendanceDAO();

        /*dao.markAttendance(
                1,
                "2026-08-21",
                "Present"
        );*/
        
        // dao.viewAttendance();
        
        dao.viewAttendanceWithStudentName();
        
        
    }
}