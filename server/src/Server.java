import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private static ServerSocket serverSocket;
    private static Socket client;
    private static Scanner scanner;
    private static PrintWriter printWriter;
    private static int PORT;
    public static int EXIT_SERVER = 000001;

    public static void initServer() throws IOException {
        serverSocket = new ServerSocket(1000);
        PORT = serverSocket.getLocalPort();
        System.err.println("Server inited at port " + PORT);
    }

    public static int getMessage(Socket client) throws IOException{
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

    public static void setMessage(Socket client, String message) throws IOException{
        printWriter = new PrintWriter(client.getOutputStream(), true);
        printWriter.println(message);
    }

    public static void main(String[] args) {
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
