import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


public class MessengerWindow {
    @FXML
    private TextArea areaMesseng;
    @FXML
    private Button buttonSend;
    @FXML
    private TextArea textArea;
    @FXML
    private Button buttonExit;
    @FXML
    private Label onlineUser;
    static int port;
    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    static String userName;
    private boolean shiftPressed = false;


    @FXML
    void initialize() {
        areaMesseng.setEditable(false);
        onlineUser.setText("Online: " + userName);
            try {
                // подключаемся к серверу
                String address = "localhost";
                socket = new Socket(address, port);
                in = new Scanner(socket.getInputStream());
                out = new PrintWriter(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        new Thread(() -> {
            try {
                // бесконечный цикл
                while (true) {
                    // если есть входящее сообщение
                    if (in.hasNext()) {
                        // считываем его
                        String inMes = in.nextLine();
                            // выводим сообщение
                            areaMesseng.appendText(inMes);
                            // добавляем строку перехода
                            areaMesseng.appendText("\n");
                    }
                }
            } catch (Exception e) { e.printStackTrace();}
        }).start();

        buttonSend.setOnAction(event -> {
            if (!(textArea.getText().trim().isEmpty())) {
                sendMsg();
            }
        });

        textArea.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (!(textArea.getText().trim().isEmpty())) {
                KeyCode keyCode = keyEvent.getCode();
                if (keyCode.equals(KeyCode.ENTER)) {
                    if (!shiftPressed)
                        sendMsg(); // метод, срабатывающий при нажатии enter без shift
                    else
                        textArea.insertText(textArea.getCaretPosition(), "\n");
                    keyEvent.consume();
                } else if (keyCode.equals(KeyCode.SHIFT))
                    shiftPressed = true;
            }
        });
        textArea.setOnKeyReleased(event -> {
            if(event.getCode().equals(KeyCode.SHIFT))
                shiftPressed = false;
        });

        buttonExit.setOnAction(event -> {
            try {
                Stage stage = (Stage) buttonExit.getScene().getWindow();
                stage.close();
                out.println("                                                "+userName + " вышел/а из чата!");
                out.println("##session##end##");
                out.flush();
                out.close();
                in.close();
                socket.close();
            } catch (IOException exc) { exc.printStackTrace(); }
        });

    }

    private void sendMsg() {
        String messageStr = userName + ": " + textArea.getText();
        // отправляем сообщение
        out.println(messageStr);
        out.flush();
        textArea.setText("");
    }

}