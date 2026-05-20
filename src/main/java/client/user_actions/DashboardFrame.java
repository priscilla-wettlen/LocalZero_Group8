package client.user_actions;

import client.RegisterLoginGUI;
import server.model.Role;
import server.model.User;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class DashboardFrame extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(cardLayout);

    public DashboardFrame(User user) {
        setTitle("LocalZero Dashboard");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel menuPanel = new JPanel(new GridLayout(8, 1, 0, 8));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton communityBtn = new JButton("Community Board");
        JButton activeTasksBtn = new JButton("Active Tasks");
        JButton createTaskBtn = new JButton("Create New Task");
        JButton forumBtn = new JButton("Forum");

        Border buttonBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        );
        communityBtn.setBorder(buttonBorder);
        activeTasksBtn.setBorder(buttonBorder);
        createTaskBtn.setBorder(buttonBorder);
        forumBtn.setBorder(buttonBorder);

//        boolean isOrganizer = user != null && user.getRoles() != null
//                && user.getRoles().contains(Role.CommunityOrganizer);
//        createTaskBtn.setEnabled(isOrganizer);

        contentPanel.add(makeLabelPanel("Community Board View"), "community");
        contentPanel.add(makeLabelPanel("Active Tasks View"), "tasks");
        contentPanel.add(makeLabelPanel("Create New Task View"), "create");
        contentPanel.add(makeLabelPanel("Forum View"), "forum");

        communityBtn.addActionListener(e -> cardLayout.show(contentPanel, "community"));
        activeTasksBtn.addActionListener(e -> cardLayout.show(contentPanel, "tasks"));
        createTaskBtn.addActionListener(e -> cardLayout.show(contentPanel, "create"));
        forumBtn.addActionListener(e -> cardLayout.show(contentPanel, "forum"));

        menuPanel.add(communityBtn);
        menuPanel.add(activeTasksBtn);
        menuPanel.add(createTaskBtn);
        menuPanel.add(forumBtn);
        menuPanel.add(new JLabel());
        menuPanel.add(new JLabel());
        menuPanel.add(new JLabel());
        menuPanel.add(new JLabel());


        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, menuPanel, contentPanel);
        split.setDividerLocation(220);

        JLabel roleLabel = new JLabel("Logged in as: " + (user != null ? user.getRole() : "okänd"));
        JButton logoutButton = new JButton("Log out");
        logoutButton.addActionListener(e -> handleLogout());

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        topBar.add(roleLabel, BorderLayout.WEST);
        topBar.add(logoutButton, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);

        add(split);
        contentPanel.setBorder(
                BorderFactory.createLineBorder(Color.GRAY)
        );
        cardLayout.show(contentPanel, "community");
    }

    private JPanel makeLabelPanel(String text) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(text, SwingConstants.CENTER), BorderLayout.CENTER);
        return panel;
    }

    private void handleLogout() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to log out?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION
        );
        if (choice == JOptionPane.YES_OPTION) {
            SwingUtilities.invokeLater(RegisterLoginGUI::new);
            dispose();
        }
    }
}