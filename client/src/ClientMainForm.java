import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

public class ClientMainForm extends JFrame {
    private JTextArea messagesTextArea;
    private JPanel formPanel;
    private JButton sendButton;
    private JTextField messageTextField;
    private PrintWriter out;

    public ClientMainForm(PrintWriter out) {
        this.out = out;
    }

    public void receiveMessage(String msg) {
        messagesTextArea.append(msg + "\n");
    }

    public void start() {
        setContentPane(formPanel);
        setVisible(true);
        setBounds(100, 100, 350, 350);
        ActionListener sendAction = e -> {
            out.println(messageTextField.getText());
            messageTextField.setText("");
        };
        sendButton.addActionListener(sendAction);
        messageTextField.addActionListener(sendAction);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
