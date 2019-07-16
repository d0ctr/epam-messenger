public class ClientRunner {
    private static String address = "localhost";
    private static int port;
    public static void main(String[] args) {
        if(!args[0].equals("")) {
            try {
                port = Integer.parseInt(args[0]);
                if(port > 1024) {
                    Client client = new Client(address, port);
                    new Thread(client::start).start();
                }
            }
            catch (NumberFormatException e) {
                e.getStackTrace();
            }
        }
    }
}
