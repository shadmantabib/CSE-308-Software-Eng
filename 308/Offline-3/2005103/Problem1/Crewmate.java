class Crewmate implements ICrewmateAction {
    private String name;

    @Override
    public void login(String name) {
        this.name = name;
        System.out.println("Welcome Crewmate " + name + "!");
    }

    @Override
    public void logout() {
        System.out.println("Bye Bye crewmate " + name + ".");
    }

    @Override
    public void repair() {
        System.out.println("Repairing the spaceship.");
    }

    @Override
    public void work() {
        System.out.println("Doing research.");
    }
}