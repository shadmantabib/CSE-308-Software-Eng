public class StrawberryShakeBuilder implements IShakeBuilder {
    private StrawberryShake shake;

    @Override
    public void reset() {
        shake = new StrawberryShake();
    }

    @Override
    public IShakeBuilder buildBaseShake() {

        return this;
    }

   
    @Override
    public IShakeBuilder addLactoseFree() {
        if (shake != null) {
            shake.setLactoseFree(true);
            shake.addCost(60); 
        }
        return this;
    }

    @Override
    public IShakeBuilder addCandy() {
        if (shake != null) {
            
            shake.addTopping("Candy");
         
             System.out.println(shake.cost);
            shake.addCost(50);
            }
        return this;
    }

    @Override
    public IShakeBuilder addCookies() {
        if (shake != null) {
            shake.addTopping("Cookies"); 
            shake.addCost(40);
        }
        return this;
    }
    @Override
    public Shake getResult() {
        
        return shake;
    }
}
