public class ClientRunner {
    private static String address = "localhost";
    private static int port = 4004;
    public static void main(String[] args) {
        Client client = new Client(address, port);
        new Thread(client).start();
    }
}
