// Concrete Decorator
class ImposterDecorator extends CrewmateDecorator {
    public ImposterDecorator(ICrewmateAction decoratedCrewmate) {
        super(decoratedCrewmate);
    }

    @Override
    public void login(String name) {
        decoratedCrewmate.login(name);
        System.out.println("We won’t tell anyone; you are an imposter");
    }

    @Override
    public void logout() {
        decoratedCrewmate.logout();
        System.out.println("See you again Comrade Imposter.");
    }

    @Override
    public void repair() {
        decoratedCrewmate.repair();
        System.out.println("Damaging the spaceship.");
    }

    @Override
    public void work() {
        decoratedCrewmate.work();
        System.out.println("Trying to kill a crewmate.");
        System.out.println("Successfully killed a crewmate.");
    }
}