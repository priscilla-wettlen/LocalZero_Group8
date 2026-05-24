package client.view;

import protocol.Initiative;
import server.model.Neighborhood;
import server.model.User;
import server.model.Visibility;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class NotificationsDialog extends JDialog {

    public NotificationsDialog(List<Initiative> initiatives, User user) {
        super((Frame) null, "Notifications", true);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 450);
        setLocationRelativeTo(null);

        Neighborhood userNeighborhood = user != null ? user.getNeighborhood() : null;

        JPanel root = new JPanel(new BorderLayout(0, 12));
        root.setBorder(new EmptyBorder(14, 14, 14, 14));

        JPanel sections = new JPanel(new GridLayout(2, 1, 0, 12));

        JPanel publicSection = buildSectionPanel(
                "Public Initiatives",
                initiatives,
                null,
                true
        );

        JPanel neighborhoodSection = buildSectionPanel(
                "Your Neighborhood",
                initiatives,
                userNeighborhood,
                false
        );

        sections.add(publicSection);
        sections.add(neighborhoodSection);

        root.add(sections, BorderLayout.CENTER);

        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> dispose());

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.add(closeBtn);

        root.add(footer, BorderLayout.SOUTH);

        setContentPane(root);
    }

    private static JPanel buildSectionPanel(
            String title,
            List<Initiative> initiatives,
            Neighborhood neighborhood,
            boolean isPublicSection
    ) {
        JPanel panel = new JPanel(new BorderLayout(8, 8));

        JLabel heading = new JLabel(title);
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 14f));
        panel.add(heading, BorderLayout.NORTH);

        DefaultListModel<String> model = new DefaultListModel<>();

        initiatives.stream()
                .filter(i -> {
                    if (isPublicSection) {
                        return i.getCreatorNeighborhood() == null
                                || i.getVisibility() == Visibility.Public;
                    }
                    return neighborhood != null
                            && i.getCreatorNeighborhood() != null
                            && i.getCreatorNeighborhood().equals(neighborhood);
                })
                .sorted(Comparator.comparing(Initiative::getTitle, Comparator.nullsLast(String::compareToIgnoreCase)))
                .forEach(i -> model.addElement(i.getTitle()));

        JList<String> list = new JList<>(model);
        list.setEnabled(false);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(list);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        panel.add(scroll, BorderLayout.CENTER);

        if (model.isEmpty()) {
            // show an empty hint
            JLabel empty = new JLabel("No initiatives", SwingConstants.CENTER);
            empty.setForeground(Color.GRAY);
            panel.add(empty, BorderLayout.CENTER);
        }

        return panel;
    }
}

