package com.leo.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class login extends JFrame {
    private static final String URL = "jdbc:mysql://localhost:3306/restaurant";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private JPanel panelMain;
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton btnWaiters;
    private JButton btnCustomer;

    public login() {
        this.setContentPane(panelMain);
        this.setMinimumSize(new Dimension(450,460));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        btnWaiters.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticateUser("waitress", "Waiter");

            }
        });

        btnCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticateUser("customers", "Customer");
                Daftar_Menu daftar_menu = new Daftar_Menu();
                daftar_menu.setVisible(true);
            }


        });

    }

    public static void main(String[] args) {
        login login = new login();
        login.setVisible(true);
    }

    private void authenticateUser(String table, String role) {
        String username = tfUsername.getText();
        String password = String.valueOf(pfPassword.getPassword());
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM " + table + " WHERE username=? AND password=?")) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                JOptionPane.showMessageDialog(this, "Welcome, " + username + " (" + role + ")");
                this.dispose();
                Dashboard dashboard = new Dashboard(role);
                dashboard.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid " + role + " username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}