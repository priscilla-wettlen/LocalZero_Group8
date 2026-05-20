package client.user_actions;

import javax.swing.*;
import java.awt.*;

public class CreateInitiativePanel extends JPanel {

    public CreateInitiativePanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Create New Initiative");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(0, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        form.add(new JLabel("Title:"));
        JTextField titleField = new JTextField();
        form.add(titleField);

        form.add(new JLabel("Description:"));
        JTextArea descArea = new JTextArea(4, 20);
        form.add(new JScrollPane(descArea));

        form.add(new JLabel("Location:"));
        JTextField locationField = new JTextField();
        form.add(locationField);

        add(form, BorderLayout.CENTER);

        JButton submitBtn = new JButton("Create Initiative");
        submitBtn.addActionListener(e -> {
            String t = titleField.getText().trim();
            String d = descArea.getText().trim();
            if (t.isEmpty() || d.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Title and description are required.");
                return;
            }
            JOptionPane.showMessageDialog(this, "Initiative created: " + t);
            titleField.setText("");
            descArea.setText("");
            locationField.setText("");
        });

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(submitBtn);
        add(bottom, BorderLayout.SOUTH);
    }
}