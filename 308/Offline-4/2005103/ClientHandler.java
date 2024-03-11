import java.io.*;
import java.net.*;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
public class ClientHandler implements Runnable {
    private Socket socket;
    private StockServer server;
    private PrintWriter out;
    private BufferedReader in;
    private Set<String> subscribedStocks;
    private String clientName; // Variable to store the client's name

    public ClientHandler(Socket socket, StockServer server) {
        this.socket = socket;
        this.server = server;
        subscribedStocks = ConcurrentHashMap.newKeySet();
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            clientName = in.readLine(); // Read the first line to get the client's name
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String line;
        try {
            while ((line = in.readLine()) != null) {
                System.out.println("Received: " + line);
                String[] tokens = line.split(" ");
                if (tokens.length < 2) {
                    out.println("Invalid command format.");
                    continue;
                }
                clientName = tokens[0]; // Client name is the first token
                String command = tokens[1].toUpperCase();
                String stockSymbol = (tokens.length > 2) ? tokens[2] : null; // Stock symbol may not always be present

                switch (command) {
                    case "S":
                        if (stockSymbol != null) {
                            subscribeToStock(stockSymbol);
                        } else {
                            out.println("Stock symbol needed for subscribing.");
                        }
                        break;
                    case "U":
                        if (stockSymbol != null) {
                            unsubscribeFromStock(stockSymbol);
                        } else {
                            out.println("Stock symbol needed for unsubscribing.");
                        }
                        break;
                    case "V":
                        viewSubscribedStocks();
                        break;
                    default:
                        out.println("Invalid command.");
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error handling connection for " + clientName + ": " + e.getMessage());
        } finally {
            closeResources();
        }
    }


    private void subscribeToStock(String stockSymbol) {
        subscribedStocks.add(stockSymbol);
        server.addSubscriber(stockSymbol, this);
        System.out.println(clientName + " has subscribed to " + stockSymbol);
    }

    private void unsubscribeFromStock(String stockSymbol) {
        subscribedStocks.remove(stockSymbol);
        server.removeSubscriber(stockSymbol, this);
        System.out.println(clientName + " has unsubscribed from " + stockSymbol);
    }

    private void viewSubscribedStocks() {
        if (subscribedStocks.isEmpty()) {
            out.println(clientName + " is not subscribed to any stocks.");
        } else {
            out.println(clientName + " is subscribed to: " + String.join(", ", subscribedStocks));
        }
    }

    public void sendUpdateToClient(String message) {
        out.println(message);
    }

    public void closeResources() {
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
            System.out.println("Error when closing client handler resources: " + e.getMessage());
        }
    }
}
