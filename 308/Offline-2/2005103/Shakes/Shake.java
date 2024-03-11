import java.util.ArrayList;
import java.util.List;

public abstract class Shake {
    protected String description = "Unknown Shake";
    protected List<String> ingredients = new ArrayList<>();
    protected List<String> toppings = new ArrayList<>();
    protected boolean isLactoseFree = false;
    protected double cost;
    private String customizations;

    public void addCustomizations(String customizations) {
        this.customizations = customizations;
    }

    public String getCustomizations() {
        return customizations;
    }

    public void setLactoseFree(boolean lactoseFree) {
        isLactoseFree = lactoseFree;
        
    }

    public void addTopping(String topping) {
        toppings.add(topping);
       
    }
    public Shake() {
        addBaseIngredients();
    }

    private void addBaseIngredients() {
        ingredients.add("Milk");
        if (!this.getClass().getSimpleName().equals("ZeroShake")) {
            ingredients.add("Sugar");
        }
    }

    public abstract void addSpecificIngredients();

    public String getDescription() {
        return description;
    }
    public void addCost(double additionalCost) {
        this.cost += additionalCost;
    }

   
    public double getCost() {
        return this.cost;
    }
    public String getIngredients() {
        StringBuilder ingredientList = new StringBuilder();
        for (String ingredient : ingredients) {
            ingredientList.append(ingredient).append(", ");
        }
        return ingredientList.toString();
    }

    public abstract double cost();
}
