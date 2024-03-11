public class Stock {
    private String symbol;
    private int count;
    private double price;

    public Stock(String symbol, int count, double price) {
        this.symbol = symbol;
        this.count = count;
        this.price = price;
    }

    // Getters and setters for symbol, count, and price
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Synchronized method for updating price
    public synchronized void updatePrice(double newPrice) {
        this.price = newPrice;
        // Notify observers about price change
    }

    // Synchronized method for updating count
    public synchronized void updateCount(int newCount) {
        this.count = newCount;
        // Notify observers about count change
    }
}
