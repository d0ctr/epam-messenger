import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {
    private Socket socket;
    private PrintWriter printWriter;
    private Scanner scanner;
    private int PORT;
    private String ADDRESS;
    private int EXIT_SERVER = 000001;

    public Client(String address, int port) {
        PORT = port;
        ADDRESS = address;
    }

    private void initSocket() throws IOException {
       socket = new Socket(ADDRESS, PORT);
       scanner = new Scanner(socket.getInputStream());
       printWriter = new PrintWriter(socket.getOutputStream(), true);
    }

    private void getMessage(Socket socket) {
        if(scanner.hasNextLine()) {
            System.out.println(" -> " + scanner.nextLine());
        }
    }
    private int setMessage(Socket socket) {
        System.out.print(" <- ");
        String message = new Scanner(System.in).nextLine();
        printWriter.println(message);
        if(message.equals("EXIT_SERVER")) {
            return EXIT_SERVER;
        }
        return 0;
    }
    @Override
    public void run() {
        try {
            initSocket();
            getMessage(socket);
            while(true) {
                if(setMessage(socket) == EXIT_SERVER) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
