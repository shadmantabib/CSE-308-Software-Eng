import java.util.List;

public class ShakeDirector {
    private IShakeBuilder builder;

    public ShakeDirector(IShakeBuilder builder) {
        this.builder = builder;
    }

    public void setBuilder(IShakeBuilder builder) {
        this.builder = builder;
    }

    public Shake constructShake(List<String> customizations) {
        
        
        for (String customization : customizations) {
            // System.out.println(customization);
            switch (customization) {
                
                case "LactoseFree":
                    builder.addLactoseFree();
                    break;
                case "Candy":
                    builder.addCandy();
                    break;
                case "Cookies":
                    builder.addCookies();
                    break;
            }
        }
        return builder.getResult();
    }
}
