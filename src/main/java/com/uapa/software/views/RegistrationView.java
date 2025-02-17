package com.uapa.software.views;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import com.uapa.software.controllers.UserController;
import com.uapa.software.entities.User;

public class RegistrationView extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cbRoles;
    private UserController userController = new UserController();

    public RegistrationView() {
        setTitle("User Registration");
        setSize(400, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(30, 30, 30));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel headerLabel = new JLabel("Register", SwingConstants.CENTER);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(headerLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        txtUsername = new JTextField(15);
        txtPassword = new JPasswordField(15);
        cbRoles = new JComboBox<>(new String[] { "Admin", "User", "Guest" });
        styleTextField(txtUsername, "Username");
        styleTextField(txtPassword, "Password");
        styleComboBox(cbRoles);

        JButton btnRegister = createButton("Register");
        JButton btnClear = createButton("Clear");
        btnRegister.addActionListener(this::handleRegistration);
        btnClear.addActionListener(e -> clearFormFields());

        mainPanel.add(txtUsername);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(txtPassword);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(cbRoles);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(btnRegister);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(btnClear);

        add(mainPanel);
    }

    private void styleTextField(JTextField field, String placeholder) {
        field.setMaximumSize(new Dimension(250, 40));
        field.setBackground(new Color(50, 50, 50));
        field.setForeground(Color.WHITE);
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 70, 70)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        field.setToolTipText(placeholder);
    }

    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setMaximumSize(new Dimension(250, 40));
        comboBox.setBackground(new Color(50, 50, 50));
        comboBox.setForeground(Color.WHITE);
        comboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(250, 40));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    public void handleRegistration(ActionEvent event) {
        User user = new User();
        user.setUsername(txtUsername.getText());
        user.setPassword(new String(txtPassword.getPassword()));
        user.setRol((String) cbRoles.getSelectedItem());

        clearFormFields();

        String token = userController.saveAndLogin(user);

        if (token != null) {
            JOptionPane.showMessageDialog(this, "User registered successfully", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new HomeView();
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFormFields() {
        txtUsername.setText("");
        txtPassword.setText("");
        cbRoles.setSelectedIndex(0);
    }
}
