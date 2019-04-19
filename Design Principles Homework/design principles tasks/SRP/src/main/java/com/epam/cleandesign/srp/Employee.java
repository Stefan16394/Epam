package com.epam.cleandesign.srp;

import java.util.Objects;

public class Employee {

    private String firstName;
    private String lastName;
    private EmployeeRole role;
    private EmployeeSeniority seniority;

    public Employee() {

    }

    public Employee(String firstName, String lastName, EmployeeRole role, EmployeeSeniority seniority) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.seniority = seniority;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public EmployeeRole getRole() {
        return role;
    }

    public void setRole(EmployeeRole role) {
        this.role = role;
    }

    public EmployeeSeniority getSeniority() {
        return seniority;
    }

    public void setSeniority(EmployeeSeniority seniority) {
        this.seniority = seniority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        return Objects.equals(firstName, employee.firstName) &&
               Objects.equals(lastName, employee.lastName) &&
               role == employee.role &&
               seniority == employee.seniority;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, role, seniority);
    }

    @Override
    public String toString() {
        return "Employee{" +
               "firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", role=" + role +
               ", seniority=" + seniority +
               '}';
    }
}
