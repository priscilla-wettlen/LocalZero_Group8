import javax.swing.SwingUtilities;

import client.view.RegisterLoginGUI;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RegisterLoginGUI();
        });
    }
}
