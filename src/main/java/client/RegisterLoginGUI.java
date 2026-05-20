package client;

import client.user_actions.DashboardFrame;
import server.model.Neighborhood;
import server.model.Role;
import server.model.User;
import server.service.AccountService;

import javax.swing.*;
import java.awt.*;

public class RegisterLoginGUI extends JFrame {

    // LOGIN COMPONENTSs
    private JTextField loginEmailField;
    private JPasswordField loginPasswordField;
    private JButton loginButton;

    // REGISTER COMPONENTS
    private JTextField registerNameField;
    private JComboBox<Neighborhood> neighborhoodBox;
    private JTextField registerEmailField;
    private JPasswordField registerPasswordField;
    private JTextField registerAdminCodeField;
    private JLabel registerAdminCodeLabel;
    private JComboBox<Role> roleBox;
    private JButton registerButton;

    // STATUS
    private JLabel statusLabel;

    private  AccountService accountService;

    public RegisterLoginGUI() {

        accountService =
                AccountService.getAccountServiceInstance();

        initializeWindow();

        initializeComponents();

        setVisible(true);
    }

    private void initializeWindow() {

        setTitle("Login & Register");

        setSize(700, 400);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
    }

    private void initializeComponents() {

        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(new GridLayout(1, 2, 20, 20));

        // =========================
        // LOGIN PANEL
        // =========================

        JPanel loginPanel = new JPanel();

        loginPanel.setBorder(
                BorderFactory.createTitledBorder("Login"));

        loginPanel.setLayout(
                new GridLayout(5, 1, 10, 10));

        loginEmailField = new JTextField();

        loginPasswordField = new JPasswordField();

        loginButton = new JButton("Login");

        loginPanel.add(new JLabel("Email"));

        loginPanel.add(loginEmailField);

        loginPanel.add(new JLabel("Password"));

        loginPanel.add(loginPasswordField);

        loginPanel.add(loginButton);

        // =========================
        // REGISTER PANEL
        // =========================

        JPanel registerPanel = new JPanel();

        registerPanel.setBorder(
                BorderFactory.createTitledBorder("Register"));

        registerPanel.setLayout(
                new GridLayout(11, 1, 10, 10));

        registerNameField = new JTextField();

        neighborhoodBox =
                new JComboBox<>(Neighborhood.values());

        registerEmailField = new JTextField();

        registerPasswordField = new JPasswordField();

        registerAdminCodeField = new JTextField();
        registerAdminCodeLabel = new JLabel("Admin Code");

        roleBox = new JComboBox<>();

        roleBox.addItem(Role.Resident);

        roleBox.addItem(Role.CommunityOrganizer);

        registerButton = new JButton("Register");

        roleBox.addActionListener(e -> updateAdminCodeField());

        registerPanel.add(new JLabel("Name"));

        registerPanel.add(registerNameField);

        registerPanel.add(new JLabel("Neighborhood"));

        registerPanel.add(neighborhoodBox);

        registerPanel.add(new JLabel("Email"));

        registerPanel.add(registerEmailField);

        registerPanel.add(new JLabel("Password"));

        registerPanel.add(registerPasswordField);

        registerPanel.add(new JLabel("Role"));

        registerPanel.add(roleBox);

        registerPanel.add(registerAdminCodeLabel);

        registerPanel.add(registerAdminCodeField);

        registerPanel.add(registerButton);

        // =========================
        // STATUS LABEL
        // =========================

        statusLabel = new JLabel(
                "",
                SwingConstants.CENTER);

        // =========================
        // ADD COMPONENTS
        // =========================

        mainPanel.add(loginPanel);

        mainPanel.add(registerPanel);

        add(mainPanel, BorderLayout.CENTER);

        add(statusLabel, BorderLayout.SOUTH);

        // =========================
        // BUTTON ACTIONS
        // =========================

        loginButton.addActionListener(e -> login());

        registerButton.addActionListener(e -> register());

        updateAdminCodeField();
    }

    private void updateAdminCodeField() {
        Role selectedRole = (Role) roleBox.getSelectedItem();
        boolean isOrganizer = selectedRole == Role.CommunityOrganizer;
        registerAdminCodeField.setEditable(isOrganizer);
        registerAdminCodeField.setEnabled(isOrganizer);
        registerAdminCodeLabel.setEnabled(isOrganizer);
        if (!isOrganizer) {
            registerAdminCodeField.setText("");
        }
    }

    private void register() {

        String name =
                registerNameField.getText();

        Neighborhood neighborhood =
                (Neighborhood) neighborhoodBox.getSelectedItem();

        String email =
                registerEmailField.getText();

        String password =
                new String(registerPasswordField.getPassword());

        String adminCode =
                new String(registerAdminCodeField.getText());

        Role selectedRole =
                (Role) roleBox.getSelectedItem();

        if (name.isBlank()
                || email.isBlank()
                || password.isBlank()
                || neighborhood == null
                || selectedRole == null) {

            statusLabel.setText(
                    "All register fields, except Admin Code, are required");

            return;
        }

        User user = accountService.register(
                name,
                neighborhood,
                email,
                password,
                adminCode,
                selectedRole != null ? selectedRole.name() : null
        );

        if (user == null) {

            statusLabel.setText(
                    "Registration failed");

        } else {

            statusLabel.setText(
                    "Registered user: "
                            + user.getEmail());
        }
    }

    private void login() {
        String email = loginEmailField.getText();
        String password = new String(loginPasswordField.getPassword());

        if (email.isBlank() || password.isBlank()) {
            statusLabel.setText("Email and password required");
            return;
        }

        String userId = accountService.login(email, password);

        if (userId.isEmpty()) {
            statusLabel.setText("Login failed");
            return;
        }

        User user = accountService.getUserByEmail(email);
        if (user == null) {
            statusLabel.setText("Login failed (user not found)");
            return;
        }

        SwingUtilities.invokeLater(() -> {
            DashboardFrame dashboard = new DashboardFrame(user);
            dashboard.setVisible(true);
        });
        dispose(); // close login window
    }
}

