package com.studentmanagement;

public class TransactionTest {

    public static void main(String[] args) {

        TransactionDAO dao = new TransactionDAO();

        dao.enrollStudentTransaction(2, 2);
    }
}