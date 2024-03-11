public class ZeroShake extends Shake {
    public ZeroShake() {
        description = "Zero Shake";
        this.cost=240.0;
        addSpecificIngredients();
    }

    @Override
    public void addSpecificIngredients() {
        ingredients.add("Sweetener");
        ingredients.add("Vanilla flavoring");
        ingredients.add("Sugar-free jello");
    }

    @Override
    public double cost() {
        
        return this.cost;
    }
}
