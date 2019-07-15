import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server implements Runnable {
    private ServerSocket serverSocket;
    private Socket client;
    private Scanner scanner;
    private PrintWriter printWriter;
    private int port;
    private final int EXIT_SERVER = 1;

    public Server(int port) {
        this.port= port;
    }

    private void initServer() throws IOException {
        serverSocket = new ServerSocket(port);
        System.err.println("Server inited at port " + port);
    }

    private int getMessage(Socket client) throws IOException{
        scanner = new Scanner(client.getInputStream());
        if(scanner.hasNextLine())
        {
            String in_message = scanner.nextLine();
            if(in_message.equals("EXIT_SERVER")) {
                return EXIT_SERVER;
            }
            else {
                System.out.println("Client: " + in_message);
            }
        }
        return 0;
    }

    private void setMessage(Socket client, String message) throws IOException{
        printWriter = new PrintWriter(client.getOutputStream(), true);
        printWriter.println(message);
    }

    public void run() {
        try {
            initServer();
            client = serverSocket.accept();
            setMessage(client, "You've successfully connectd to server!");
            while(true) {
                if(getMessage(client) == EXIT_SERVER) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
