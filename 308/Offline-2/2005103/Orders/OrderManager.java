import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private List<Order> orders = new ArrayList<>();
    private Order currentOrder;

    public void startNewOrder() {
        if (currentOrder != null && !currentOrder.isClosed()) {
            System.out.println("An order is already in progress. Please close the current order before starting a new one.");
            return;
        }

        currentOrder = new Order();
        orders.add(currentOrder);
        System.out.println("New order started.");
    }
    
    public boolean isCurrentOrderActive() {
        return currentOrder != null && !currentOrder.isClosed();
    }
    public Order getCurrentOrder() {
        return currentOrder;
    }
    

    public void closeCurrentOrder() {
        if (currentOrder == null || currentOrder.isClosed()) {
            System.out.println("No active order to close.");
            return;
        }

        if (currentOrder.getTotalPrice() == 0) {
            System.out.println("Cannot close the order with no items.");
            return;
        }
        
        currentOrder.closeOrder();
        System.out.println("Order closed. Final details:");
        currentOrder.printOrderDetails();
    }
    public void printCurrentOrderDetails() {
        if (currentOrder != null && !currentOrder.isClosed()) {
            currentOrder.printOrderDetails();
        } else {
            System.out.println("No active order to print.");
        }
    }
    
    public void addShakeToCurrentOrder(Shake shake) {
        if (currentOrder == null || currentOrder.isClosed()) {
            System.out.println("No active order to add a shake. Please start a new order.");
            return;
        }
        
        currentOrder.addShake(shake);
        System.out.println(shake.getDescription() + " added to the order.");
    }
}
