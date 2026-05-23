package client.user_actions;

import server.model.Neighborhood;
import server.model.Role;
import server.model.User;
import server.model.Visibility;
import server.service.InitiativeService;
import shared.Initiative;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/**
 * Swing forum view: lists sustainability initiatives the current user is allowed to see.
 */
public class ForumPanel extends JPanel {

    private final User currentUser;
    private final JPanel initiativesListPanel = new JPanel();

    public ForumPanel(User currentUser) {
        this.currentUser = currentUser;
        setLayout(new BorderLayout(0, 12));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel heading = new JLabel("Community Forum");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 18f));
        heading.setHorizontalAlignment(SwingConstants.CENTER);

        Neighborhood neighborhood = currentUser != null ? currentUser.getNeighborhood() : null;
        String neighborhoodText = neighborhood != null ? neighborhood.name() : "unknown";
        JLabel subtitle = new JLabel(
                "Showing public initiatives and neighborhood posts for: " + neighborhoodText);
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        subtitle.setForeground(Color.DARK_GRAY);

        JPanel header = new JPanel(new BorderLayout(0, 6));
        header.add(heading, BorderLayout.NORTH);
        header.add(subtitle, BorderLayout.CENTER);

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> refreshForum());
        JPanel headerSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        headerSouth.add(refreshBtn);
        header.add(headerSouth, BorderLayout.SOUTH);

        initiativesListPanel.setLayout(new BoxLayout(initiativesListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(initiativesListPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        add(header, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        refreshForum();
    }

    public void refreshForum() {
        Neighborhood viewerNeighborhood = currentUser != null ? currentUser.getNeighborhood() : null;
        ViewForumCommand command = new ViewForumCommand(viewerNeighborhood);
        command.execute();
        displayInitiatives(command.getLoadedInitiatives());
    }

    private void displayInitiatives(List<Initiative> initiatives) {
        initiativesListPanel.removeAll();

        if (initiatives.isEmpty()) {
            JLabel empty = new JLabel(
                    "<html><center>No initiatives on the forum yet.<br>Create one from the dashboard menu.</center></html>",
                    SwingConstants.CENTER);
            empty.setAlignmentX(Component.CENTER_ALIGNMENT);
            empty.setBorder(new EmptyBorder(40, 20, 40, 20));
            initiativesListPanel.add(empty);
        } else {
            for (Initiative initiative : initiatives) {
                initiativesListPanel.add(buildInitiativeCard(initiative));
                initiativesListPanel.add(Box.createVerticalStrut(12));
            }
        }

        initiativesListPanel.revalidate();
        initiativesListPanel.repaint();
    }

    private JPanel buildInitiativeCard(Initiative initiative) {
        JPanel card = new JPanel(new BorderLayout(8, 8));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(12, 14, 12, 14)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 240));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setBackground(Color.WHITE);

        String visibilityLabel = initiative.getVisibility() == Visibility.Public
                ? "Public"
                : "Neighborhood only";
        JLabel titleLabel = new JLabel(initiative.getTitle());
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 15f));

        String neighborhoodText = initiative.getCreatorNeighborhood() != null
                ? formatNeighborhoodName(initiative.getCreatorNeighborhood().name())
                : "—";
        JLabel metaLabel = new JLabel(String.format(
                "<html>%s · <b>Neighborhood:</b> %s · <b>Location:</b> %s · %s · by %s</html>",
                nullToDash(initiative.getInitiativeType()),
                neighborhoodText,
                nullToDash(initiative.getSpecificLocation()),
                nullToDash(initiative.getDuration()),
                nullToDash(initiative.getCreator())
        ));
        metaLabel.setForeground(Color.GRAY);

        JTextArea description = new JTextArea(initiative.getDescription());
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setEditable(false);
        description.setOpaque(false);
        description.setFont(metaLabel.getFont());
        description.setBorder(null);

        JLabel badge = new JLabel(visibilityLabel);
        badge.setOpaque(true);
        badge.setBorder(new EmptyBorder(4, 8, 4, 8));
        if (initiative.getVisibility() == Visibility.Public) {
            badge.setBackground(new Color(220, 245, 220));
        } else {
            badge.setBackground(new Color(255, 240, 210));
        }

        JPanel top = new JPanel(new BorderLayout());
        top.add(titleLabel, BorderLayout.CENTER);
        top.add(badge, BorderLayout.EAST);

        JPanel center = new JPanel(new BorderLayout(0, 6));
        center.add(metaLabel, BorderLayout.NORTH);
        center.add(description, BorderLayout.CENTER);

        card.add(top, BorderLayout.NORTH);
        card.add(center, BorderLayout.CENTER);

        if (currentUser != null && currentUser.hasRole(Role.CommunityOrganizer)) {
            JButton deleteButton = new JButton("Delete");
            deleteButton.addActionListener(e -> handleDelete(initiative));
            JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            footer.add(deleteButton);
            card.add(footer, BorderLayout.SOUTH);
        }

        return card;
    }

    private void handleDelete(Initiative initiative) {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Delete this initiative from the forum?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );
        if (choice != JOptionPane.YES_OPTION) {
            return;
        }
        boolean deleted = InitiativeService.getInitiativeServiceInstance()
                .deleteInitiative(initiative.getId());
        if (!deleted) {
            JOptionPane.showMessageDialog(this, "Failed to delete initiative.");
            return;
        }
        refreshForum();
    }

    private static String nullToDash(String value) {
        return value == null || value.isBlank() ? "—" : value;
    }

    /** Turns enum-style names like VästraHamnen into readable labels. */
    private static String formatNeighborhoodName(String enumName) {
        if (enumName == null || enumName.isBlank()) {
            return "—";
        }
        return enumName.replaceAll("([a-zåäö])([A-ZÅÄÖ])", "$1 $2");
    }
}
