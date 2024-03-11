import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class StockServer {
    private ServerSocket serverSocket;
    private ConcurrentHashMap<String, Stock> stockData;
    private ConcurrentHashMap<String, List<ClientHandler>> subscribers;

    public StockServer(int port) {
        stockData = new ConcurrentHashMap<>();
        subscribers = new ConcurrentHashMap<>();
        loadInitialStocks("D:\\3-1\\CSE 308 Software Sessional\\Offline 4\\Trial2\\src\\Resources\\init_stocks.txt");
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Stock Server is running on port " + port);
            new Thread(this::readAdminCommands).start();
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                    new Thread(clientHandler).start();
                } catch (IOException e) {
                    System.out.println("Exception when trying to accept client connection: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadInitialStocks(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                String symbol = parts[0];
                int count = Integer.parseInt(parts[1]);
                double price = Double.parseDouble(parts[2]);
                stockData.put(symbol, new Stock(symbol, count, price));
            }
        } catch (IOException e) {
            System.out.println("Could not load initial stocks from file: " + e.getMessage());
        }
    }

    public synchronized void increaseStockPrice(String stockSymbol, double increaseBy) {
        Stock stock = stockData.get(stockSymbol);
        if (stock != null) {
            stock.setPrice(stock.getPrice() + increaseBy);
            notifySubscribers(stockSymbol, "Price increased to " + stock.getPrice());
        }
    }

    public synchronized void decreaseStockPrice(String stockSymbol, double decreaseBy) {
        Stock stock = stockData.get(stockSymbol);
        if (stock != null) {
            stock.setPrice(stock.getPrice() - decreaseBy);
            notifySubscribers(stockSymbol, "Price decreased to " + stock.getPrice());
        }
    }

    public synchronized void changeStockCount(String stockSymbol, int changeBy) {
        Stock stock = stockData.get(stockSymbol);
        if (stock != null) {
            stock.setCount(stock.getCount() + changeBy);
            notifySubscribers(stockSymbol, "Count changed to " + stock.getCount());
        }
    }


    public void addSubscriber(String stockSymbol, ClientHandler clientHandler) {
        subscribers.computeIfAbsent(stockSymbol, k -> Collections.synchronizedList(new ArrayList<>())).add(clientHandler);
    }

    public void removeSubscriber(String stockSymbol, ClientHandler clientHandler) {
        List<ClientHandler> clientHandlers = subscribers.get(stockSymbol);
        if (clientHandlers != null) {
            clientHandlers.remove(clientHandler);
        }
    }

    public void notifySubscribers(String stockSymbol, String message) {
        List<ClientHandler> clientHandlers = subscribers.get(stockSymbol);
        if (clientHandlers != null) {
            for (ClientHandler handler : clientHandlers) {
                handler.sendUpdateToClient(message);
            }
        }
    }
    public void processAdminCommand(String adminCommand) {
        String[] tokens = adminCommand.trim().split(" ");
        if (tokens.length < 2) {
            System.out.println("Invalid admin command format.");
            return;
        }

        String command = tokens[0].toUpperCase();
        String stockSymbol = tokens[1];
        switch (command) {
            case "I": // Increase stock price
                if (tokens.length == 3) {
                    try {
                        double increaseAmount = Double.parseDouble(tokens[2]);
                        increaseStockPrice(stockSymbol, increaseAmount);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid amount for price increase.");
                    }
                } else {
                    System.out.println("Increase command requires a stock symbol and an amount.");
                }
                break;
            case "D": // Decrease stock price
                if (tokens.length == 3) {
                    try {
                        double decreaseAmount = Double.parseDouble(tokens[2]);
                        decreaseStockPrice(stockSymbol, decreaseAmount);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid amount for price decrease.");
                    }
                } else {
                    System.out.println("Decrease command requires a stock symbol and an amount.");
                }
                break;
            case "C": // Change stock count
                if (tokens.length == 3) {
                    try {
                        int countChange = Integer.parseInt(tokens[2]);
                        changeStockCount(stockSymbol, countChange);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid count for stock count change.");
                    }
                } else {
                    System.out.println("Change count command requires a stock symbol and a count.");
                }
                break;
            default:
                System.out.println("Unknown admin command.");
                break;
        }
    }
    public void readAdminCommands() {
        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter admin commands (I, D, C):");
        while (scanner.hasNextLine()) {
            String adminCommand = scanner.nextLine();
            processAdminCommand(adminCommand);
        }
        scanner.close();
    }


    public static void main(String[] args) {
        int port = 12345; // Set the port number
        StockServer server = new StockServer(port);
        System.out.println("hello");

    }
}


