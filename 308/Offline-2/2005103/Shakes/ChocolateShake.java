public class ChocolateShake extends Shake {
    public ChocolateShake() {
        description = "Chocolate Shake";
        this.cost=230.0;
        addSpecificIngredients();
    }

    @Override
    public void addSpecificIngredients() {
        ingredients.add("Chocolate syrup");
        ingredients.add("Chocolate ice cream");
    }

    @Override
    public double cost() {
        
        return this.cost;
    }
}
