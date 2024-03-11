package employees;

public class EmployeeMaker {
    public Employee createEmployee(String name,EmployeeType employeeType) {
        switch (employeeType) {
            case Cashier:
                return new Cashier(name);
            case Officer:
                return new Officer(name);
            case ManagingDirector:
                return new ManagingDirector(name);
            default:
                return null;
        }
    }
}
