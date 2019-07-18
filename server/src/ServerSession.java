import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerSession {
    private Scanner in;
    private PrintWriter out;
    private Server server;
    private String clientName;

    public String getClientName() {
        return clientName;
    }

    public ServerSession(Socket client, Server server) throws IOException {
        in = new Scanner(client.getInputStream());
        out = new PrintWriter(client.getOutputStream(), true);
        clientName = "User " + System.currentTimeMillis();
        this.server = server;
    }

    public void start() {
        out.println("Server >> You've connected to server.");
        while(in.hasNextLine()) {
            server.sendAll(clientName, in.nextLine());
        }
        server.sendAll("Server", clientName + " has disconnected");
    }

    public void send(String author, String msg) {
        out.println(author + " >> " + msg);
    }
}
