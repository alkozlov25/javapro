package edu.t1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class DBInitializer {
    @Autowired
    DataSource ds;

    public void createDB() {
        //try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
        try (Connection con = ds.getConnection(); Statement stm = con.createStatement()) {
            stm.executeUpdate("DROP TABLE Department IF EXISTS");
            stm.executeUpdate("CREATE TABLE Department(ID INT PRIMARY KEY, NAME VARCHAR(255))");
            stm.executeUpdate("INSERT INTO Department VALUES(1,'Accounting')");
            stm.executeUpdate("INSERT INTO Department VALUES(2,'IT')");
            stm.executeUpdate("INSERT INTO Department VALUES(3,'HR')");

            stm.executeUpdate("DROP TABLE Employee IF EXISTS");
            stm.executeUpdate("CREATE TABLE Employee(ID INT PRIMARY KEY, NAME VARCHAR(255), DepartmentID INT)");
            stm.executeUpdate("INSERT INTO Employee VALUES(1,'Pete',1)");
            stm.executeUpdate("INSERT INTO Employee VALUES(2,'Ann',1)");

            stm.executeUpdate("INSERT INTO Employee VALUES(3,'Liz',2)");
            stm.executeUpdate("INSERT INTO Employee VALUES(4,'Tom',2)");

            stm.executeUpdate("INSERT INTO Employee VALUES(5,'Todd',3)");

            stm.executeUpdate("DROP TABLE Users IF EXISTS");
            stm.executeUpdate("CREATE TABLE Users(id INT PRIMARY KEY, username VARCHAR(255) unique)");
            stm.executeUpdate("INSERT INTO Users VALUES(1,'Anna')");
            stm.executeUpdate("INSERT INTO Users VALUES(2,'Tom')");
            stm.executeUpdate("INSERT INTO Users VALUES(3,'Liza')");

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
