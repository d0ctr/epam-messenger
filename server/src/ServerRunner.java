public class ServerRunner {
    public static void main(String[] args) {
        Server server = new Server(1000);
        new Thread(server).start();
    }
}
