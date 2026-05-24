package client.view;

import server.model.Neighborhood;
import server.model.User;
import server.model.Visibility;
import server.service.InitiativeService;
import server.model.InitiativeType;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

//create initiative gui

public class CreateInitiativePanel extends JPanel {

    private final User currentUser;

    public CreateInitiativePanel(User currentUser) {
        this.currentUser = currentUser;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Create New Initiative");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        form.add(new JLabel("Title:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        JTextField titleField = new JTextField(20);
        form.add(titleField, gbc);

        // Description
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        form.add(new JLabel("Description:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        JTextArea descArea = new JTextArea(4, 20);
        form.add(new JScrollPane(descArea), gbc);

        // Image
        gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        form.add(new JLabel(""), gbc);
        gbc.gridx = 1;
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JLabel imageLabel = new JLabel("Choose a picture");
        imageLabel.setForeground(Color.GRAY);
        JButton browseBtn = new JButton();
        final String[] selectedImagePath = {null};
        browseBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                    "Images", "jpg", "jpeg", "png", "gif"));
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                selectedImagePath[0] = chooser.getSelectedFile().getAbsolutePath();
                imageLabel.setText(chooser.getSelectedFile().getName());
                imageLabel.setForeground(Color.BLACK);
            }
        });
        imagePanel.add(browseBtn);
        imagePanel.add(Box.createHorizontalStrut(10));
        imagePanel.add(imageLabel);
        form.add(imagePanel, gbc);

        add(form, BorderLayout.CENTER);

        // Initiative Type
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        form.add(new JLabel("Initiative Type:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        JComboBox<InitiativeType> initiativeTypeBox =
                new JComboBox<>(InitiativeType.values());

        form.add(initiativeTypeBox, gbc);

        // Location
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        form.add(new JLabel("Location:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        JTextField locationField = new JTextField(20);
        form.add(locationField, gbc);

        // Duration
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        form.add(new JLabel("Duration:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        JTextField durationField = new JTextField(20);
        form.add(durationField, gbc);

        // Visibility
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        form.add(new JLabel("Visibility:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 1;
        JCheckBox publicCheckBox = new JCheckBox("Public");
        JCheckBox neighborhoodCheckBox = new JCheckBox("Neighborhood Specific");
        ButtonGroup visibilityGroup = new ButtonGroup();
        visibilityGroup.add(publicCheckBox);
        visibilityGroup.add(neighborhoodCheckBox);

        JPanel visibilityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        visibilityPanel.add(publicCheckBox);
        visibilityPanel.add(neighborhoodCheckBox);
        form.add(visibilityPanel, gbc);

        add(form, BorderLayout.CENTER);

        // Submit Button
        JButton submitBtn = new JButton("Create Initiative");
        submitBtn.addActionListener(e -> {
            String t = titleField.getText().trim();
            String d = descArea.getText().trim();
            if (t.isEmpty() || d.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Title and description are required.");
                return;
            }

            Visibility visibility = publicCheckBox.isSelected()
                    ? Visibility.Public
                    : Visibility.Neighborhood;

            String specificLocation = locationField.getText().trim();
            if (specificLocation.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Location is required.");
                return;
            }

            if (currentUser == null || currentUser.getNeighborhood() == null) {
                JOptionPane.showMessageDialog(this, "You must be logged in with a registered neighborhood.");
                return;
            }

            String duration = durationField.getText().trim();
            InitiativeType selectedType =
                    (InitiativeType) initiativeTypeBox.getSelectedItem();

            if (selectedType == null) {
                JOptionPane.showMessageDialog(this, "Please select initiative type.");
                return;
            }

            String initiativeType = selectedType.name();
            URL imageUrl = null;

            String creator = currentUser.getEmail();
            Neighborhood creatorNeighborhood = currentUser.getNeighborhood();

            InitiativeService.getInitiativeServiceInstance()
                    .createInitiative(
                            creator,
                            t,
                            d,
                            initiativeType,
                            specificLocation,
                            creatorNeighborhood,
                            visibility,
                            duration,
                            imageUrl
                    );

            JOptionPane.showMessageDialog(this,
                    "Initiative created and posted to the forum: " + t);
            titleField.setText("");
            descArea.setText("");
            locationField.setText("");
            durationField.setText("");
            visibilityGroup.clearSelection();
        });

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(submitBtn);
        add(bottom, BorderLayout.SOUTH);
    }
}
