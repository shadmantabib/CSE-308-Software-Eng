import java.util.Scanner;

public class Main {
     public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ICrewmateAction crewmate = new Crewmate();
        ICrewmateAction imposter = new ImposterDecorator(new Crewmate());

        String currentUser = "none"; // none, crew, imp

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            String[] parts = input.split(" ");

            switch (parts[0].toLowerCase()) {
                case "login":
                    if (parts[1].startsWith("crew")) {
                        crewmate.login(parts[1]);
                        currentUser = "crew";
                    } else if (parts[1].startsWith("imp")) {
                        imposter.login(parts[1]);
                        currentUser = "imp";
                    }
                    break;
                case "repair":
                    if ("imp".equals(currentUser)) {
                        imposter.repair();
                    } else if ("crew".equals(currentUser)) {
                        crewmate.repair();
                    }
                    break;
                case "work":
                    if ("imp".equals(currentUser)) {
                        imposter.work();
                    } else if ("crew".equals(currentUser)) {
                        crewmate.work();
                    }
                    break;
                case "logout":
                    if ("imp".equals(currentUser)) {
                        imposter.logout();
                    } else if ("crew".equals(currentUser)) {
                        crewmate.logout();
                    }
                    currentUser = "none";
                    break;
                default:
                    System.out.println("Invalid command.");
                    break;
            }
        }
        scanner.close();
    }
}
