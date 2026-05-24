import client.ClientConnectionManager;
import client.view.RegisterLoginGUI;

import javax.swing.SwingUtilities;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            ClientConnectionManager connectionManager =
                    null;
            try {
                connectionManager = new ClientConnectionManager("localhost", 1080);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            new RegisterLoginGUI(connectionManager);
        });
    }
}
