public interface StockClientInterface {
    void subscribeToStock(String stockSymbol);
    void unsubscribeFromStock(String stockSymbol);
    void viewSubscribedStocks();
}