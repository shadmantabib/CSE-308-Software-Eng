import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<Shake> shakes = new ArrayList<>();
    private boolean isClosed = false;

    public void addShake(Shake shake) {
        
        if (!isClosed) {
            shakes.add(shake);
        }
    }

    public void closeOrder() {
        isClosed = true;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public double getTotalPrice() {
        return shakes.stream().mapToDouble(Shake::cost).sum();
    }

    public void printOrderDetails() {
        if (shakes.isEmpty()) {
            System.out.println("No shakes added to the order.");
            return;
        }

        System.out.println("Order Details:");
        for (Shake shake : shakes) {
            System.out.println("Shake: " + shake.getDescription() +" having base ingredients "+shake.getIngredients()+" and added customzations "+shake.getCustomizations()+ ", Cost: " + shake.cost);
        }
        System.out.println("Total Price: " + getTotalPrice());
    }
}
