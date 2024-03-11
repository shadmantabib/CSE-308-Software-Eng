import java.io.*;
import java.net.*;
import java.util.Scanner;


public class StockClient implements StockClientInterface {
//    private static final AtomicInteger clientCount = new AtomicInteger(0);
    private final String clientName;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public StockClient(String serverAddress, int serverPort) {
        clientName = "client";
        try {
            socket = new Socket(serverAddress, serverPort);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println(clientName); // Send the client name to the server upon connection
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void subscribeToStock(String stockSymbol) {
        out.println(clientName + " S " + stockSymbol);
    }

    @Override
    public void unsubscribeFromStock(String stockSymbol) {
        out.println(clientName + " U " + stockSymbol);
    }

    @Override
    public void viewSubscribedStocks() {
        out.println(clientName + " V");
    }
    public void listenForUpdates() {
        new Thread(() -> {
            try {
                String fromServer;
                while ((fromServer = in.readLine()) != null) {
                    System.out.println("Server: " + fromServer);
                }
            } catch (IOException e) {
                System.out.println("Error listening for updates: " + e.getMessage());
            } finally {
                closeResources();
            }
        }).start();
    }

    private void closeResources() {
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Error when closing resources: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        StockClient client = new StockClient("localhost", 12345);
        client.listenForUpdates();

        Scanner scanner = new Scanner(System.in);
       System.out.println("[" + client.clientName + "] Enter commands (S, U, V):");
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if ("S".equalsIgnoreCase(input.split(" ")[0])) {
                client.subscribeToStock(input.split(" ")[1]);
            } else if ("U".equalsIgnoreCase(input.split(" ")[0])) {
                client.unsubscribeFromStock(input.split(" ")[1]);
            } else if ("V".equalsIgnoreCase(input)) {
                client.viewSubscribedStocks();
            } else {
                System.out.println("Unknown command. Try again.");
            }
        }
        scanner.close();
    }


}
