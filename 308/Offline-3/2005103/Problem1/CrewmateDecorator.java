// Decorator Base Class
abstract class CrewmateDecorator implements ICrewmateAction {
    protected ICrewmateAction decoratedCrewmate;

    public CrewmateDecorator(ICrewmateAction decoratedCrewmate) {
        this.decoratedCrewmate = decoratedCrewmate;
    }
}