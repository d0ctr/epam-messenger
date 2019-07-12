import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static Socket socket;
    private static PrintWriter printWriter;
    private static Scanner scanner;
    public static int EXIT_SERVER = 000001;
    public static int WAIT_SERVER = 000010;

    private static void initSocket() throws IOException {
       socket = new Socket("localhost", 1000);
       scanner = new Scanner(socket.getInputStream());
       printWriter = new PrintWriter(socket.getOutputStream(), true);
    }

    private static void getMessage(Socket socket) throws IOException {
        if(scanner.hasNextLine()) {
            System.out.println(" -> " + scanner.nextLine());
        }
    }
    private static int setMessage(Socket socket) throws IOException {
        System.out.print(" <- ");
        String message = new Scanner(System.in).nextLine();
        if(!message.equals("")) {
            printWriter.println(message);
            if(message.equals("EXIT_SERVER")) {
                return EXIT_SERVER;
            }
            return 0;
        }
        return WAIT_SERVER;
    }

    public static void main(String[] args) {
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
