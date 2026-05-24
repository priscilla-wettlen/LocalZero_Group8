package client.view;

import client.ClientConnectionManager;
import client.user_actions.CreateInitiativeCommand;
import server.model.InitiativeType;
import server.model.Neighborhood;
import server.model.User;
import server.model.Visibility;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;

// Create initiative GUI

public class CreateInitiativePanel extends JPanel {

    private final User currentUser;

    private final ClientConnectionManager clientConnectionManager;

    public CreateInitiativePanel(
            User currentUser,
            ClientConnectionManager clientConnectionManager) {

        this.currentUser = currentUser;

        this.clientConnectionManager =
                clientConnectionManager;

        setLayout(new BorderLayout());

        setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        20,
                        20,
                        20));

        JLabel title =
                new JLabel(
                        "Create New Initiative");

        title.setFont(
                title.getFont()
                        .deriveFont(
                                Font.BOLD,
                                16f));

        title.setHorizontalAlignment(
                SwingConstants.CENTER);

        add(title, BorderLayout.NORTH);

        JPanel form =
                new JPanel(
                        new GridBagLayout());

        form.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        0,
                        20,
                        0));

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        10,
                        10,
                        10,
                        10);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        // =========================
        // TITLE
        // =========================

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;

        form.add(
                new JLabel("Title:"),
                gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;

        JTextField titleField =
                new JTextField(20);

        form.add(
                titleField,
                gbc);

        // =========================
        // DESCRIPTION
        // =========================

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;

        form.add(
                new JLabel("Description:"),
                gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;

        JTextArea descArea =
                new JTextArea(
                        4,
                        20);

        form.add(
                new JScrollPane(descArea),
                gbc);

        // =========================
        // INITIATIVE TYPE
        // =========================

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;

        form.add(
                new JLabel("Initiative Type:"),
                gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;

        JComboBox<InitiativeType> initiativeTypeBox =
                new JComboBox<>(
                        InitiativeType.values());

        form.add(
                initiativeTypeBox,
                gbc);

        // =========================
        // LOCATION
        // =========================

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;

        form.add(
                new JLabel("Location:"),
                gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;

        JTextField locationField =
                new JTextField(20);

        form.add(
                locationField,
                gbc);

        // =========================
        // DURATION
        // =========================

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;

        form.add(
                new JLabel("Duration:"),
                gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;

        JTextField durationField =
                new JTextField(20);

        form.add(
                durationField,
                gbc);

        // =========================
        // VISIBILITY
        // =========================

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;

        form.add(
                new JLabel("Visibility:"),
                gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 1;

        JCheckBox publicCheckBox =
                new JCheckBox("Public");

        JCheckBox neighborhoodCheckBox =
                new JCheckBox(
                        "Neighborhood Specific");

        ButtonGroup visibilityGroup =
                new ButtonGroup();

        visibilityGroup.add(publicCheckBox);

        visibilityGroup.add(neighborhoodCheckBox);

        JPanel visibilityPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT));

        visibilityPanel.add(publicCheckBox);

        visibilityPanel.add(neighborhoodCheckBox);

        form.add(
                visibilityPanel,
                gbc);

        // =========================
        // IMAGE UPLOAD
        // =========================

        gbc.gridx = 0;
        gbc.gridy = 6;

        form.add(
                new JLabel("Image:"),
                gbc);

        gbc.gridx = 1;

        JPanel imagePanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                0,
                                0));

        JLabel imageLabel =
                new JLabel(
                        "Choose a picture");

        imageLabel.setForeground(
                Color.GRAY);

        JButton browseBtn =
                new JButton("Browse");

        final String[] selectedImagePath =
                {null};

        browseBtn.addActionListener(e -> {

            JFileChooser chooser =
                    new JFileChooser();

            chooser.setFileFilter(
                    new javax.swing.filechooser.FileNameExtensionFilter(
                            "Images",
                            "jpg",
                            "jpeg",
                            "png",
                            "gif"));

            int result =
                    chooser.showOpenDialog(this);

            if (result
                    == JFileChooser.APPROVE_OPTION) {

                File selectedFile =
                        chooser.getSelectedFile();

                selectedImagePath[0] =
                        selectedFile.getAbsolutePath();

                imageLabel.setText(
                        selectedFile.getName());

                imageLabel.setForeground(
                        Color.BLACK);

                System.out.println(
                        "SELECTED IMAGE: "
                                + selectedImagePath[0]);
            }
        });

        imagePanel.add(browseBtn);

        imagePanel.add(
                Box.createHorizontalStrut(10));

        imagePanel.add(imageLabel);

        form.add(
                imagePanel,
                gbc);

        add(form, BorderLayout.CENTER);

        // =========================
        // SUBMIT BUTTON
        // =========================

        JButton submitBtn =
                new JButton(
                        "Create Initiative");

        submitBtn.addActionListener(e -> {

            String t =
                    titleField.getText()
                            .trim();

            String d =
                    descArea.getText()
                            .trim();

            if (t.isEmpty()
                    || d.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Title and description are required.");

                return;
            }

            Visibility visibility =
                    publicCheckBox.isSelected()
                            ? Visibility.Public
                            : Visibility.Neighborhood;

            String specificLocation =
                    locationField.getText()
                            .trim();

            if (specificLocation.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Location is required.");

                return;
            }

            if (currentUser == null
                    || currentUser.getNeighborhood()
                    == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "You must be logged in with a registered neighborhood.");

                return;
            }

            String duration =
                    durationField.getText()
                            .trim();

            InitiativeType selectedType =
                    (InitiativeType)
                            initiativeTypeBox
                                    .getSelectedItem();

            if (selectedType == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select initiative type.");

                return;
            }

            // =========================
            // IMAGE URL CONVERSION
            // =========================

            URL imageUrl = null;

            try {

                if (selectedImagePath[0]
                        != null) {

                    imageUrl =
                            new File(
                                    selectedImagePath[0])
                                    .toURI()
                                    .toURL();

                    System.out.println(
                            "IMAGE URL: "
                                    + imageUrl);
                }

            } catch (Exception ex) {

                ex.printStackTrace();
            }

            String creator =
                    currentUser.getEmail();

            Neighborhood creatorNeighborhood =
                    currentUser.getNeighborhood();

            // =========================
            // CREATE COMMAND
            // =========================

            CreateInitiativeCommand command =
                    new CreateInitiativeCommand(
                            clientConnectionManager,
                            creator,
                            t,
                            d,
                            visibility,
                            creatorNeighborhood,
                            selectedType,
                            specificLocation,
                            duration,
                            imageUrl
                    );

            command.execute();

            JOptionPane.showMessageDialog(
                    this,
                    "Initiative created and posted to the forum: "
                            + t);

            // =========================
            // RESET FORM
            // =========================

            titleField.setText("");

            descArea.setText("");

            locationField.setText("");

            durationField.setText("");

            imageLabel.setText(
                    "Choose a picture");

            imageLabel.setForeground(
                    Color.GRAY);

            visibilityGroup.clearSelection();
        });

        JPanel bottom =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT));

        bottom.add(submitBtn);

        add(bottom, BorderLayout.SOUTH);
    }
}