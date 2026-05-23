package client.user_actions;

import server.model.Role;
import server.model.User;
import server.service.AccountService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Set;

public class MyAccountPanel extends JPanel {

    private final AccountService accountService = AccountService.getAccountServiceInstance();
    private User currentUser;
    private boolean initialOrganizer;

    private final JLabel nameValue = new JLabel();
    private final JLabel emailValue = new JLabel();
    private final JLabel neighborhoodValue = new JLabel();
    private final JLabel rolesValue = new JLabel();

    private final JCheckBox organizerCheck = new JCheckBox("Community Organizer");
    private final JTextField adminCodeField = new JTextField();
    private final JLabel adminCodeLabel = new JLabel("Admin Code");
    private final JLabel statusLabel = new JLabel(" ");

    public MyAccountPanel(User currentUser) {
        this.currentUser = currentUser;
        setLayout(new BorderLayout(0, 12));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        adminCodeField.setColumns(12);

        JLabel heading = new JLabel("My Account");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 18f));
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        add(heading, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;
        addRow(infoPanel, gbc, row++, "Name:", nameValue);
        addRow(infoPanel, gbc, row++, "Email:", emailValue);
        addRow(infoPanel, gbc, row++, "Neighborhood:", neighborhoodValue);
        addRow(infoPanel, gbc, row++, "Roles:", rolesValue);

        JPanel rolesPanel = new JPanel(new GridBagLayout());
        rolesPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        GridBagConstraints rbc = new GridBagConstraints();
        rbc.insets = new Insets(6, 6, 6, 6);
        rbc.anchor = GridBagConstraints.WEST;

        JCheckBox residentCheck = new JCheckBox("Resident");
        residentCheck.setSelected(true);
        residentCheck.setEnabled(false);

        rbc.gridx = 0;
        rbc.gridy = 0;
        rolesPanel.add(residentCheck, rbc);

        rbc.gridy = 1;
        rolesPanel.add(organizerCheck, rbc);

        rbc.gridy = 2;
        rolesPanel.add(adminCodeLabel, rbc);

        rbc.gridx = 1;
        rolesPanel.add(adminCodeField, rbc);

        organizerCheck.addActionListener(e -> updateAdminCodeField());

        JPanel center = new JPanel(new BorderLayout(0, 12));
        center.add(infoPanel, BorderLayout.NORTH);
        center.add(rolesPanel, BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);

        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(e -> saveChanges());

        JPanel footer = new JPanel(new BorderLayout());
        statusLabel.setForeground(Color.DARK_GRAY);
        footer.add(statusLabel, BorderLayout.WEST);
        footer.add(saveButton, BorderLayout.EAST);
        add(footer, BorderLayout.SOUTH);

        refreshUser(currentUser);
    }

    public void refreshUser(User user) {
        this.currentUser = user;
        nameValue.setText(user != null ? user.getName() : "—");
        emailValue.setText(user != null ? user.getEmail() : "—");
        neighborhoodValue.setText(user != null && user.getNeighborhood() != null
                ? user.getNeighborhood().name()
                : "—");
        rolesValue.setText(user != null ? user.getRole() : "—");

        initialOrganizer = user != null && user.hasRole(Role.CommunityOrganizer);
        organizerCheck.setSelected(initialOrganizer);
        updateAdminCodeField();
        statusLabel.setText(" ");
    }

    private void updateAdminCodeField() {
        boolean wantsOrganizer = organizerCheck.isSelected();
        boolean needsCode = wantsOrganizer && !initialOrganizer;
        adminCodeField.setEnabled(needsCode);
        adminCodeField.setEditable(needsCode);
        adminCodeLabel.setEnabled(needsCode);
        if (!needsCode) {
            adminCodeField.setText("");
        }
    }

    private void saveChanges() {
        if (currentUser == null) {
            statusLabel.setText("No user loaded.");
            return;
        }
        boolean wantsOrganizer = organizerCheck.isSelected();
        String adminCode = adminCodeField.getText().trim();

        boolean updated = accountService.updateUserRoles(
                currentUser.getEmail(),
                wantsOrganizer,
                adminCode
        );

        if (!updated) {
            statusLabel.setText("Failed to update roles. Check admin code.");
            return;
        }

        Set<Role> roles = currentUser.getRoles();
        if (wantsOrganizer) {
            roles.add(Role.CommunityOrganizer);
        } else {
            roles.remove(Role.CommunityOrganizer);
        }
        currentUser.setRoles(roles);
        rolesValue.setText(currentUser.getRole());
        initialOrganizer = currentUser.hasRole(Role.CommunityOrganizer);
        updateAdminCodeField();
        statusLabel.setText("Roles updated.");
    }

    private static void addRow(JPanel panel, GridBagConstraints gbc, int row, String labelText, JComponent value) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(labelText), gbc);
        gbc.gridx = 1;
        panel.add(value, gbc);
    }
}
