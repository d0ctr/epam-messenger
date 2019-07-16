import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket serverSocket;
    private List<ServerSession> clientList;
    private int port;

    public Server(int port) {
        this.port= port;
        clientList = new ArrayList<>();
    }

    private void initServer() throws IOException {
        serverSocket = new ServerSocket(port);
    }


    public void start() {
        try {
            initServer();
            while(true) {
                Socket newClient = serverSocket.accept();
                ServerSession newClientSession = new ServerSession(newClient, this);
                clientList.add(newClientSession);
                new Thread(newClientSession::start).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendAll(String clientName, String nextLine) {
        clientList.forEach(c -> c.send(clientName, nextLine));
    }
}
