import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private int port;
    private Scanner in;
    private PrintWriter out;
    private Scanner consoleIn;
    private String address;

    public Client(String address, int port) {
        this.port = port;
        this.address = address;
    }

    private void initSocket() throws IOException {
       socket = new Socket(address, port);
       in = new Scanner(socket.getInputStream());
       out = new PrintWriter(socket.getOutputStream(), true);
       consoleIn = new Scanner(System.in);
    }

    public void start() {
        try {
            initSocket();
            new Thread(() -> {
                while (in.hasNextLine()) {
                    System.out.println(in.nextLine());
                }
            }).start();
            while(consoleIn.hasNextLine()) {
                out.println(consoleIn.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
