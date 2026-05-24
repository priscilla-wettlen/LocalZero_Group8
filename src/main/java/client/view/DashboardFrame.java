package client.view;

import client.ClientConnectionManager;
import client.view.RegisterLoginGUI;
import server.model.Role;
import server.model.User;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class DashboardFrame extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(cardLayout);
    private final ClientConnectionManager clientConnectionManager;
    private final User loggedInUser;
    private ForumPanel forumPanel;
    private MyAccountPanel myAccountPanel;


    public DashboardFrame(User user,
                          ClientConnectionManager clientConnectionManager) {
        this.loggedInUser = user;
        this.clientConnectionManager = clientConnectionManager;
        setTitle("LocalZero Dashboard");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel menuPanel = new JPanel(new GridLayout(8, 1, 0, 8));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton createNewInitiative = new JButton("Create New Initiative");
        JButton forumBtn = new JButton("Forum");
        JButton inboxBtn = new JButton("Inbox");
        JButton ecoBtn = new JButton("Eco Tracker");
        JButton accountBtn = new JButton("My Account");
        //JButton forumBtn = new JButton("Forum");

        Border buttonBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        );
        createNewInitiative.setBorder(buttonBorder);
        forumBtn.setBorder(buttonBorder);
        ecoBtn.setBorder(buttonBorder);
        accountBtn.setBorder(buttonBorder);
        inboxBtn.setBorder(buttonBorder);
        //forumBtn.setBorder(buttonBorder);

//        boolean isOrganizer = user != null && user.getRoles() != null
//                && user.getRoles().contains(Role.CommunityOrganizer);
//        createTaskBtn.setEnabled(isOrganizer);

        contentPanel.add(makeLabelPanel("Create New Initiative"), "community");
        contentPanel.add(makeLabelPanel("Active Tasks View"), "tasks");
        contentPanel.add(new CreateInitiativePanel(loggedInUser, clientConnectionManager), "Create");
        forumPanel = new ForumPanel(loggedInUser, clientConnectionManager);
        contentPanel.add(forumPanel, "forum");
        InboxPanel inboxPanel = new InboxPanel(loggedInUser.getId(), loggedInUser.getName(),
                loggedInUser.getNeighborhood(), clientConnectionManager);
        contentPanel.add(inboxPanel, "inbox");

        contentPanel.add(new EcoTrackerPanel(clientConnectionManager), "eco");

        client.user_actions.ViewForumCommand notifCmd = new client.user_actions.ViewForumCommand(clientConnectionManager, loggedInUser.getNeighborhood());
        notifCmd.execute();
        java.util.List<protocol.Initiative> initiatives = notifCmd.getLoadedInitiatives();
        server.service.MessengerService messengerService = server.service.MessengerService.getMessengerServiceInstance();
        java.util.ArrayList<String> inboxMessages = messengerService.getInboxMessages(loggedInUser.getId());
        if (!initiatives.isEmpty() || (inboxMessages != null && !inboxMessages.isEmpty())) {
            SwingUtilities.invokeLater(() -> new NotificationsDialog(initiatives, loggedInUser, inboxMessages).setVisible(true));
        }


        myAccountPanel = new MyAccountPanel(loggedInUser);
        contentPanel.add(myAccountPanel, "my account");



        createNewInitiative.addActionListener(e -> cardLayout.show(contentPanel, "Create"));
        forumBtn.addActionListener(e -> {
            forumPanel.refreshForum();
            cardLayout.show(contentPanel, "forum");
        });
        inboxBtn.addActionListener(e -> {
            inboxPanel.refreshInboxPanel();
            cardLayout.show(contentPanel, "inbox");
        });

        
        ecoBtn.addActionListener(e -> cardLayout.show(contentPanel, "eco"));
        accountBtn.addActionListener(e -> cardLayout.show(contentPanel, "my account"));

        accountBtn.addActionListener(e -> {
            myAccountPanel.refreshUser(loggedInUser);
            cardLayout.show(contentPanel, "my account");
        });
      //forumBtn.addActionListener(e -> cardLayout.show(contentPanel, "forum"));

        menuPanel.add(createNewInitiative);
        menuPanel.add(forumBtn);
        menuPanel.add(inboxBtn);
        menuPanel.add(ecoBtn);
        menuPanel.add(accountBtn);
        //menuPanel.add(forumBtn);
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
            SwingUtilities.invokeLater(() -> {
                new RegisterLoginGUI(clientConnectionManager);
            });
            dispose();
        }
    }
}