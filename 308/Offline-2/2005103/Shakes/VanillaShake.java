public class VanillaShake extends Shake {
    public VanillaShake() {
        description = "Vanilla Shake";
        this.cost=190.0;
        addSpecificIngredients();
    }

    @Override
    public void addSpecificIngredients() {
        ingredients.add("Vanilla flavoring");
        ingredients.add("Jello");
    }

    @Override
    public double cost() {
        
        return this.cost;
    }
}
