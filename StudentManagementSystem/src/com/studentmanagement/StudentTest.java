package com.studentmanagement;

public class StudentTest {

    public static void main(String[] args) {

        StudentDAO dao = new StudentDAO();

        /*dao.addStudent(
                "Anita",
                "anita@gmail.com",
                "9876543210"
        );*/
        
        /*dao.updateStudent(
                1,
                "Anita A",
                "anitaA@gmail.com",
                "9999999999"
        );*/
          
        dao.deleteStudent(4);
        
        dao.viewStudents();
             
    }
}