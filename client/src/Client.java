import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
    private Socket socket;
    private PrintWriter printWriter;
    private Scanner scanner;
    private int port;
    private String address;
    private final int EXIT_SERVER = 1;

    public Client(String address, int port) {
        this.port = port;
        this.address = address;
    }

    private void initSocket() throws IOException {
       socket = new Socket(address, port);
       scanner = new Scanner(socket.getInputStream());
       printWriter = new PrintWriter(socket.getOutputStream(), true);
    }

    private void receiveMessage(Socket socket) {
        if(scanner.hasNextLine()) {
            System.out.println(" -> " + scanner.nextLine());
        }
    }

    private void setMessage(Socket socket) {
        System.out.print(" <- ");
        String message = new Scanner(System.in).nextLine();
        printWriter.println(message);
    }

    public void run() {
        try {
            initSocket();
            receiveMessage(socket);
            while(true) {
                setMessage(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
