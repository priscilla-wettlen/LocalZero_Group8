package client.view;

import client.ClientConnectionManager;
import client.user_actions.ShowInboxCommand;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class InboxPanel extends JPanel {

    private final ShowInboxCommand showInboxCommand;
    private final JPanel messagesPanel = new JPanel();
    private JPanel composePanel;

    public InboxPanel(String userId,
                      String userName,
                      Object neighborhood,
                      ClientConnectionManager connectionManager) {
        this.showInboxCommand = new ShowInboxCommand(
                connectionManager,
                userId,
                userName,
                neighborhood
        );

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
        showInboxCommand.execute();
        renderMessages(showInboxCommand.getMessages());
    }

    private void renderMessages(List<String> messages) {
        messagesPanel.removeAll();

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

        JComboBox<String> recipientDropdown = new JComboBox<>();
        List<String> neighbors = showInboxCommand.getNeighbors();
        for (String neighbor : neighbors) {
            recipientDropdown.addItem(neighbor);
        }

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
            String recipient = (String) recipientDropdown.getSelectedItem();
            String message = messageArea.getText().trim();
            if (recipient == null) {
                JOptionPane.showMessageDialog(this, "No neighbors are available to message.");
                return;
            }
            if (message.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Message cannot be empty.");
                return;
            }

            if (!showInboxCommand.sendMessage(recipient, message)) {
                JOptionPane.showMessageDialog(this, "Message could not be sent.");
                return;
            }

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
}
