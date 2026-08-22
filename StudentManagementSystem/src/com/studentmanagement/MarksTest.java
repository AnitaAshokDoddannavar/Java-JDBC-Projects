package com.studentmanagement;

public class MarksTest {

    public static void main(String[] args) {

        MarksDAO dao = new MarksDAO();

        // dao.addMarks(1, "Java", 95);
        
        // dao.viewMarks();
        
        dao.viewMarksWithStudentName();
    }
}