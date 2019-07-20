import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerSession implements Runnable {
    private Scanner in;
    private PrintWriter out;
    private Server server;
    private static int clientsCount = 0;

    ServerSession(Socket client, Server server) throws IOException {
        clientsCount++;
        in = new Scanner(client.getInputStream());
        out = new PrintWriter(client.getOutputStream(), true);
        this.server = server;
    }

    @Override
    public void run() {
        try {
            while (true) {
                server.sendMessageToAll("                                                Новый участник вошёл в чат!");
                server.sendMessageToAll("                                                Клиентов в чате = " + clientsCount);
                break;
            }

            while (true) {
                if (in.hasNext()) {
                    String clientMessage = in.nextLine();
                    if (clientMessage.equalsIgnoreCase("##session##end##")) {
                        break;
                    }

                    System.out.println(clientMessage);

                    server.sendMessageToAll(clientMessage);
                }

                Thread.sleep(100);
            }
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        finally {
            this.close();
        }
    }

    void sendMsg(String msg) {
        try {
            out.println(msg);
            out.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void close() {
        server.removeClient(this);
        clientsCount--;
        server.sendMessageToAll("                                                Клиентов в чате = " + clientsCount);
    }

}
