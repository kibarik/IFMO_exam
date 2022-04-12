package org.kibarik.exam;

import org.kibarik.exam.ui.ProductTableForm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {

    public static void main(String[] args) {
        new ProductTableForm();
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://116.202.236.174/DemoExam", "DemoExam", "DemoExam");
    }
}
