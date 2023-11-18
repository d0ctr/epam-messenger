import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class Server {
    private ServerSocket serverSocket;
    private List<ServerSession> clientList;
    private int port;

    private void initServer() throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public Server(int port) {
        this.port = port;
        clientList = new ArrayList<>();

        try {
            initServer();
            System.out.println("Сервер запущен!");
            while (true) {
                Socket newClient = serverSocket.accept();
                ServerSession newClientSession = new ServerSession(newClient, this);
                clientList.add(newClientSession);
                new Thread(newClientSession).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendMessageToAll(String msg) {
        for (ServerSession o : clientList) {
            o.sendMsg(msg);
        }
    }

    void removeClient(ServerSession clientDeletion) {
        clientList.remove(clientDeletion);
    }
}
