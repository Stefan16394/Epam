package com.epam.cleandesign.srp;

import java.sql.Connection;
import java.util.List;

public final class EmployeeManager {

    private List<Employee> cache;

    public void sendEmployeesReport(Connection connection) {
        String data = this.getAllEmployeesAsHtml(connection);

        EmailSender.sendEmployeeReportMail(data);
    }

    private synchronized String getAllEmployeesAsHtml(Connection connection) {
        List<Employee> employees = readEmployees(connection);

        return DataFormatter.convertToHtml(employees);
    }

    public synchronized String employeesAsJson(Connection connection) {
        List<Employee> employees = readEmployees(connection);

        return DataFormatter.convertToJson(employees);
    }

    private List<Employee> readEmployees(Connection connection) {
        if (cache == null) {
            cache = Database.getAllEmployees(connection);
        }
        return cache;
    }
}
