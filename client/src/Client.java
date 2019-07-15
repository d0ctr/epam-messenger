import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
    private Socket socket;
    private PrintWriter printWriter;
    private Scanner socketScanner;
    private Scanner inputScanner;
    private int port;
    private String address;
    private final int EXIT_SERVER = 1;

    public Client(String address, int port) {
        this.port = port;
        this.address = address;
    }

    private void initSocket() throws IOException {
       socket = new Socket(address, port);
       socketScanner = new Scanner(socket.getInputStream());
       inputScanner = new Scanner(System.in);
       printWriter = new PrintWriter(socket.getOutputStream(), true);
    }

    private void receiveMessage(Socket socket) {
        if(socketScanner.hasNextLine()) {
            System.out.println(" -> " + socketScanner.nextLine());
        }
    }

    private void setMessage(Socket socket) {
        System.out.print(" <- ");
        printWriter.println(inputScanner.nextLine());
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
