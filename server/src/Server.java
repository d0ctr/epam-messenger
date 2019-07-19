import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket serverSocket;
    private List<ServerSession> clientList;
    private int port;
    private ServerMainForm serverMainForm;

    public Server(int port) {
        this.port= port;
        clientList = new ArrayList<>();
        serverMainForm = new ServerMainForm();
    }

    private void initServer() throws IOException {
        serverSocket = new ServerSocket(port);
    }


    public void start() {
        try {
            initServer();
            serverMainForm.start();
            while(true) {
                Socket newClient = serverSocket.accept();
                ServerSession newClientSession = new ServerSession(newClient, this);
                sendAll("Server", newClientSession.getClientName() + " has connected");
                clientList.add(newClientSession);
                serverMainForm.updateUsersList(clientList);
                new Thread(newClientSession::start).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendAll(String clientName, String msg) {
        clientList.forEach(c -> c.send(clientName, msg));
        serverMainForm.receiveMessage(clientName, msg);
    }
}
