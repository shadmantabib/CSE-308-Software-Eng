public interface IShakeBuilder {
    void reset();
    IShakeBuilder buildBaseShake();
    IShakeBuilder addLactoseFree();
    IShakeBuilder addCandy();
    IShakeBuilder addCookies();
    Shake getResult();
}
