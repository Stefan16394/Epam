package com.epam.cleandesign.srp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static final String SELECT_FROM_EMPLOYEES = "SELECT * FROM Employees";
    private static final String FIRST_NAME = "FIRST_NAME";
    private static final String LAST_NAME = "LAST_NAME";
    private static final String ROLE = "ROLE";
    private static final String SENIORITY = "SENIORITY";

    public static List<Employee> getAllEmployees(Connection connection){
        List<Employee> employees = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_FROM_EMPLOYEES)) {
            while (resultSet.next()) {

                Employee employee = new Employee();

                String firstName = resultSet.getString(FIRST_NAME);
                String lastName = resultSet.getString(LAST_NAME);
                String role = resultSet.getString(ROLE);
                String seniority = resultSet.getString(SENIORITY);

                employee.setFirstName(firstName);
                employee.setLastName(lastName);
                employee.setRole(EmployeeRole.valueOf(role));
                employee.setSeniority(EmployeeSeniority.valueOf(seniority));
                employees.add(employee);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return employees;
    }
}
