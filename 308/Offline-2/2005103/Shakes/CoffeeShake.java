public class CoffeeShake extends Shake {
    public CoffeeShake() {
        this.cost =250.0;
        description = "Coffee Shake";
        addSpecificIngredients();
    }

    @Override
    public void addSpecificIngredients() {
        ingredients.add("Coffee");
        ingredients.add("Jello");
    }

    @Override
    public double cost() {
        
        return this.cost;
    }
}
