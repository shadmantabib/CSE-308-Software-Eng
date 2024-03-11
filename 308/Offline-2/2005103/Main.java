import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final OrderManager orderManager = new OrderManager();

    public static void main(String[] args) {
        System.out.println("Welcome to the Shake Shop!");
        String input;
        
        do {
            System.out.println("Press 'o' to open a new order, 'e' to close the current order, 'a' to add a shake, or 'q' to quit:");
            input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "o":
                    handleOpenOrder();
                    break;
                case "e":
                    handleCloseOrder();
                    break;
                case "a":
                    if (orderManager.isCurrentOrderActive()) {
                        addShakeToOrder();
                    } else {
                        System.out.println("No active order. Please start a new order before adding shakes.");
                    }
                    break;
                case "q":
                    System.out.println("Thank you for visiting the Shake Shop! Goodbye!");
                    break;
                default:
                    System.out.println("Invalid input, please try again.");
            }
        } while (!input.equals("q"));
    }
    private static void addShakeToOrder() {
        System.out.print("Enter the type of shake you want to add (Chocolate, Coffee, Strawberry, Vanilla, Zero): ");
        String shakeType = scanner.nextLine().toLowerCase();
        IShakeBuilder builder = getShakeBuilder(shakeType);
        builder.reset();
        Shake shake=builder.getResult();
        // System.out.println(shake.cost());
        
        List<String> customizations = getCustomizationOptions();
        ShakeDirector director = new ShakeDirector(builder);
        // System.out.println(customizations.toString());
        shake=director.constructShake(customizations);
        for (String customization : customizations){
            shake.addCustomizations(customization);
        }
        // System.out.println(shake.getCost());
        orderManager.addShakeToCurrentOrder(shake);
    }
    private static IShakeBuilder getShakeBuilder(String shakeType) {
        switch (shakeType.toLowerCase()) {
            case "chocolate":
                return new ChocolateShakeBuilder();
            case "coffee":
                return new CoffeeShakeBuilder();
            case "strawberry":
                return new StrawberryShakeBuilder();
            case "vanilla":
                return new VanillaShakeBuilder();
            case "zero":
                return new ZeroShakeBuilder();
            default:
                System.out.println("Shake type not recognized.");
                return null;
        }
    }
    
    private static List<String> getCustomizationOptions() {
        System.out.println("Customizations: 1 - Lactose-Free, 2 - Add Candy, 3 - Add Cookies");
        System.out.print("Enter customization options (e.g., '1 3' for Lactose-Free and Cookies, leave blank for none): ");
        String customizationInput = scanner.nextLine();
        List<String> customizations = new ArrayList<>();
        for (String option : customizationInput.split(" ")) {
            switch (option) {
                case "1":
                    customizations.add("LactoseFree");
                    break;
                case "2":
                    customizations.add("Candy");
                    break;
                case "3":
                    customizations.add("Cookies");
                    break;
            }
        }
        return customizations;
    }
    
    private static void handleOpenOrder() {
        if (!orderManager.isCurrentOrderActive()) {
            orderManager.startNewOrder();
            System.out.println("A new order has been started.");
        } else {
            System.out.println("An order is currently active. Please close the current order before opening a new one.");
        }
    }

    private static void handleCloseOrder() {
        if (orderManager.isCurrentOrderActive()) {
            orderManager.closeCurrentOrder();
              } else if (orderManager.isCurrentOrderActive()) {
            System.out.println("At least one shake must be added before closing the order.");
        } else {
            System.out.println("No active order to close.");
        }
    }

  
}
