import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ServerMainForm extends JFrame{
    private JPanel formPanel;
    private JTextArea messagesTextArea;
    private JTextArea usersTextArea;
    private JLabel messagesLabel;
    private JLabel usersLabel;


    public void start() {
        setContentPane(formPanel);
        setVisible(true);
        setBounds(100, 100, 500, 350);
        //new Thread(this::updateOnlineUsersList);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    public void receiveMessage(String clientName, String msg) {
        messagesTextArea.append(clientName + " >> " + msg + "\n");
    }

    public void updateUsersList(List<ServerSession> clientList) {
        usersTextArea.setText("");
        clientList.forEach(c -> usersTextArea.append(c.getClientName() + "\n"));
    }
}
