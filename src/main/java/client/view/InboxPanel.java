package client.view;

import client.ClientConnectionManager;
import client.user_actions.ShowInboxCommand;
import protocol.Initiative;
import protocol.Request;
import protocol.UserActionType;
import server.model.User;
import server.service.AccountService;
import server.service.MessengerService;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class InboxPanel extends JPanel {

    private final ClientConnectionManager connectionManager;
    private final User currentUser;
    private final AccountService accountService = AccountService.getAccountServiceInstance();
    private final MessengerService messengerService = MessengerService.getMessengerServiceInstance();
    private final JPanel messagesPanel = new JPanel();
    private JPanel composePanel;

    public InboxPanel(User currentUser, ClientConnectionManager connectionManager) {
        this.currentUser = currentUser;
        this.connectionManager = connectionManager;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel heading = new JLabel("Inbox");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 18f));
        add(heading, BorderLayout.NORTH);

        messagesPanel.setLayout(new BoxLayout(messagesPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(messagesPanel);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshInboxPanel());
        bottomPanel.add(refreshButton);

        JButton writeBtn = new JButton("Write Message");
        writeBtn.addActionListener(e -> showComposePanel());
        bottomPanel.add(writeBtn);

        add(bottomPanel, BorderLayout.SOUTH);
        refreshInboxPanel();
    }

    public void refreshInboxPanel() {
        messagesPanel.removeAll();

        List<String> messages = loadMessages();
        if (messages.isEmpty()) {
            JLabel emptyLabel = new JLabel("No messages yet.");
            emptyLabel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
            messagesPanel.add(emptyLabel);
        } else {
            for (String message : messages) {
                messagesPanel.add(buildMessageRow(message));
            }
        }

        messagesPanel.revalidate();
        messagesPanel.repaint();
    }

    private List<String> loadMessages() {
        ShowInboxCommand command = new ShowInboxCommand(
                connectionManager,
                currentUser.getId()
        );
        command.execute();
        return command.getMessages();
    }

    private JPanel buildMessageRow(String message) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        JTextArea messageText = new JTextArea(message);
        messageText.setLineWrap(true);
        messageText.setWrapStyleWord(true);
        messageText.setEditable(false);
        messageText.setOpaque(false);
        row.add(messageText, BorderLayout.CENTER);

        return row;
    }

    private void showComposePanel() {
        if (composePanel != null) {
            remove(composePanel);
        }

        composePanel = buildSendMessagePanel();
        add(composePanel, BorderLayout.EAST);
        revalidate();
        repaint();
    }

    private JPanel buildSendMessagePanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setPreferredSize(new Dimension(300, 0));
        panel.setBorder(BorderFactory.createTitledBorder("Write Message"));

        JComboBox<User> recipientDropdown = new JComboBox<>();
        List<User> neighbors = accountService.getUsersByNeighborhood(
                currentUser.getNeighborhood(),
                currentUser.getId()
        );
        for (User neighbor : neighbors) {
            recipientDropdown.addItem(neighbor);
        }
        recipientDropdown.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
            String label = value == null ? "" : value.getName() + " (" + value.getEmail() + ")";
            return new JLabel(label);
        });

        JPanel recipientPanel = new JPanel(new BorderLayout(4, 4));
        recipientPanel.add(new JLabel("To:"), BorderLayout.NORTH);
        recipientPanel.add(recipientDropdown, BorderLayout.CENTER);
        panel.add(recipientPanel, BorderLayout.NORTH);

        JTextArea messageArea = new JTextArea(8, 30);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        panel.add(new JScrollPane(messageArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelButton = new JButton("Cancel");
        JButton sendButton = new JButton("Send");

        cancelButton.addActionListener(e -> hideComposePanel());
        sendButton.addActionListener(e -> {
            User recipient = (User) recipientDropdown.getSelectedItem();
            String message = messageArea.getText().trim();
            if (recipient == null) {
                JOptionPane.showMessageDialog(this, "No neighbors are available to message.");
                return;
            }
            if (message.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Message cannot be empty.");
                return;
            }

            sendMessage(recipient, message);
            messageArea.setText("");
            hideComposePanel();
            JOptionPane.showMessageDialog(this, "Message sent.");
        });

        sendButton.setEnabled(!neighbors.isEmpty());
        buttonPanel.add(cancelButton);
        buttonPanel.add(sendButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void hideComposePanel() {
        if (composePanel != null) {
            remove(composePanel);
            composePanel = null;
            revalidate();
            repaint();
        }
    }

    private void sendMessage(User recipient, String message) {
        if (connectionManager != null) {
            HashMap<String, Object> details = new HashMap<>();
            details.put("senderName", currentUser.getName());
            details.put("recipientUserId", recipient.getId());
            details.put("message", message);

            Request request = new Request(UserActionType.SendMessage, details);
            request.setAuthToken(connectionManager.getToken());
            Initiative response = connectionManager.sendRequest(request);
            if (response != null && response.isSuccess()) {
                return;
            }
        }

        messengerService.sendMessage(currentUser.getName(), recipient.getId(), message);
    }
}
