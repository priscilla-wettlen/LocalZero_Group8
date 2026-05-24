package client.view;

import client.ClientConnectionManager;
import client.user_actions.LogEcoActionCommand;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class EcoTrackerPanel extends JPanel {

    private double totalCO2Saved = 0.0;
    private final JLabel totalLabel = new JLabel("Total CO2 saved: 0.0 kg");
    private final ClientConnectionManager clientConnectionManager;
    private final JTextField actionField = new JTextField();
    private final JLabel responseLabel = new JLabel("");

    public EcoTrackerPanel(ClientConnectionManager clientConnectionManager) {
        this.clientConnectionManager = clientConnectionManager;

        setLayout(new BorderLayout(0, 12));
        setBackground(UIManager.getColor("Panel.background"));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel heading = new JLabel("Eco Tracker");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 18f));
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        add(heading, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(4, 1, 10, 10));

        totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalLabel.setFont(totalLabel.getFont().deriveFont(Font.BOLD, 14f));

        actionField.setToolTipText("e.g. biked to work, skipped meat today");
        actionField.setColumns(25);

        JPanel inputRow = new JPanel(new BorderLayout(8, 8));
        inputRow.add(actionField, BorderLayout.CENTER);

        JButton logButton = new JButton("Log Action");
        logButton.addActionListener(e -> logAction());
        inputRow.add(logButton, BorderLayout.EAST);

        responseLabel.setHorizontalAlignment(SwingConstants.CENTER);

        center.add(totalLabel);
        center.add(inputRow);
        center.add(responseLabel);

        add(center, BorderLayout.CENTER);
    }

    private void logAction() {
        String action = actionField.getText();

        LogEcoActionCommand command = new LogEcoActionCommand(clientConnectionManager, null);
        command.setAction(action);
        command.execute();

        String msg = command.getLastResponse();
        responseLabel.setText(msg != null ? msg : "");

        if (msg != null && msg.contains("Estimated saving: ")) {
            try {
                String co2Part = msg.replace("Action logged! Estimated saving: ", "")
                                    .replace(" kg CO2", "")
                                    .trim();
                totalCO2Saved += Double.parseDouble(co2Part);
                totalLabel.setText("Total CO2 saved: " + totalCO2Saved + " kg");
            } catch (NumberFormatException ignored) {}
        }
    }
}