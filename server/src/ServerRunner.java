public class ServerRunner {
    private static int port = 4004;
    public static void main(String[] args) {
        Server server = new Server(port);
        new Thread(server).start();
    }
}
