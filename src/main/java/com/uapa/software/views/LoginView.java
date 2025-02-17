package com.uapa.software.views;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import com.uapa.software.controllers.UserController;
import com.uapa.software.entities.User;
import com.uapa.software.services.RBACService;

public class LoginView extends JFrame {
    private JTextField txtUserName;
    private JPasswordField txtUserPassword;
    private JButton btnLogin, btnSignup, btnForgotPassword;
    private UserController userController = new UserController();

    public LoginView() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Login Portal");
        setSize(400, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(30, 30, 30));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel headerLabel = new JLabel("Welcome Back", SwingConstants.CENTER);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(headerLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        txtUserName = new JTextField(15);
        txtUserPassword = new JPasswordField(15);
        styleTextField(txtUserName, "Username");
        styleTextField(txtUserPassword, "Password");

        btnLogin = createButton("Login");
        btnSignup = createButton("Signup");
        btnForgotPassword = createButton("Forgot Password");

        btnLogin.addActionListener(this::handleLogin);
        btnSignup.addActionListener(this::handleRegistration);

        mainPanel.add(txtUserName);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(txtUserPassword);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(btnLogin);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(btnSignup);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(btnForgotPassword);

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

    public void handleLogin(ActionEvent event) {
        User user = new User();
        user.setUsername(txtUserName.getText());
        user.setPassword(new String(txtUserPassword.getPassword()));
        String token = userController.login(user);

        if (token != null) {
            if (!RBACService.isAdmin(token) && !RBACService.isUser(token)) {
                JOptionPane.showMessageDialog(this, "Access Denied", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                dispose();
                new HomeView();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Credentials", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handleRegistration(ActionEvent event) {
        new RegistrationView().setVisible(true);
        this.setVisible(false);
    }
}