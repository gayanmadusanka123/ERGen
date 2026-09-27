package com.ergen.ui;

public class test {
    public static void main(String[] args) {
        UIApplication app = new UIApplication();
        app.setName("Employee Management");
        app.setLayout("dashboard");

        UIPage employeePage = new UIPage();
        employeePage.setName("Employees");
        employeePage.setEntity("Employee");
        employeePage.setRoute("/employees");
        employeePage.setView("table");

        UIComponent table = new UIComponent();
        table.setType("table");
        table.setEntity("Employee");

        employeePage.getComponents().add(table);
        app.getPages().add(employeePage);
    }
}
