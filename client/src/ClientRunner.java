public class ClientRunner {
    public static void main(String[] args) {
        Client client = new Client("localhost", 1000);
        client.start();
    }
}
