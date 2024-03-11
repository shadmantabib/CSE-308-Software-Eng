public class StrawberryShake extends Shake {
    public StrawberryShake() {
        description = "Strawberry Shake";
        this.cost=200.0;
        addSpecificIngredients();
    }

    @Override
    public void addSpecificIngredients() {
        ingredients.add("Strawberry syrup");
        ingredients.add("Strawberry ice cream");
    }

    @Override
    public double cost() {
        
        return this.cost;
    }
}
