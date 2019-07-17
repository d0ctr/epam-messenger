public class ServerRunner {
    private static int port;
    public static void main(String[] args) {
        if(!args[0].equals("")) {
            try {
                port = Integer.parseInt(args[0]);
                if(port > 1024) {
                    Server server = new Server(port);
                    server.start();
                }
            }
            catch (NumberFormatException e) {
                e.getStackTrace();
            }
        }
    }
}
