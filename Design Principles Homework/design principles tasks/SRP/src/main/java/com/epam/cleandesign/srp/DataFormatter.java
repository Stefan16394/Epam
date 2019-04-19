package com.epam.cleandesign.srp;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class DataFormatter {

    public static String convertToJson(List<Employee> data){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(data);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String convertToHtml(List<Employee> data){
        StringBuilder builder = new StringBuilder();
        builder.append("<table>").append("<tr><th>Employee</th><th>Position</th></tr>");

        for (Employee employee : data) {
            builder.append("<tr><td>").append(employee.getFirstName()).append(" ").append(employee.getLastName())
                    .append("</td><td>").append(employee.getSeniority()).append(" ").append(employee.getRole())
                    .append("</td></tr>");
        }

        builder.append("</table>");

        return builder.toString();
    }
}
