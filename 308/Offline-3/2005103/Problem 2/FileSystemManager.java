import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileSystemManager {
    private Map<String, Drive> drives;
    private IFileSystemComponent currentComponent;
    
    public FileSystemManager() {
        this.drives = new HashMap<>();
        this.currentComponent = null; // Starts with no current component
    }

    public void mkdrive(String name) {
        Drive drive = new Drive(name);
        drives.put(name, drive);
        currentComponent = drive;
    }

    public void cd(String path) {
        if (path.equals("~")) {
            // Navigate to the root of the current drive
            while (currentComponent.getParent() != null) {
                currentComponent = currentComponent.getParent();
            }
        } else if (path.endsWith(":\\") && drives.containsKey(path.substring(0, path.length() - 2))) {
            // If path is a drive letter (e.g., C:\), change to that drive's root directory
            currentComponent = drives.get(path.substring(0, path.length() - 2)).getRootDirectory();
        } else {
            // Change to a directory within the current drive
            if (currentComponent instanceof Directory) {
                IFileSystemComponent nextComponent = ((Directory) currentComponent).getSubComponent(path);
                if (nextComponent == null) {
                    System.out.println("Error: Directory does not exist.");
                } else if (nextComponent instanceof File) {
                    System.out.println("Error: Path is a file.");
                } else {
                    currentComponent = nextComponent;
                }
            } else {
                System.out.println("Error: Current component is not a directory.");
            }
        }
    }
    
    public void ls(String name) {
        IFileSystemComponent component = null;

        if (currentComponent instanceof Directory) {
            // If current component is a directory, look for the subcomponent with the provided name
            component = ((Directory) currentComponent).getSubComponent(name);
        } else if (currentComponent instanceof Drive && currentComponent.getName().equalsIgnoreCase(name)) {
            // If current component is a drive and the name matches, set the component to the current drive
            component = currentComponent;
        }

        if (component == null) {
            System.out.println("Error: No such file, directory, or drive '" + name + "' found.");
        } else {
            // Assuming we have a method to format the details of the component
            System.out.println(formatComponentDetails(component));
        }
    }

    private String formatComponentDetails(IFileSystemComponent component) {
        return "Name: " + component.getName() + "\n" +
            "Type: " + component.getType() + "\n" +
            "Size: " + component.getSize() + " kB\n" +
            "Directory: " + component.getDirectory() + "\n" +
            "Component Count: " + component.getComponentCount() + "\n" +
            "Creation time: " + component.getCreationTime().format(DateTimeFormatter.ofPattern("dd MMMM, yyyy HH:mm:ss"));
    }

    

   

    public void list() {
        if (currentComponent instanceof Directory) {
            Directory currentDir = (Directory) currentComponent;
            for (IFileSystemComponent component : currentDir.getChildren()) {
                System.out.println(component.getName() + " " + 
                                   component.getSize() + "kB " + 
                                   component.getCreationTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            }
        } else {
            System.out.println("Current component is not a directory.");
        }
    }
    

    public void delete(String name) {
        if (!(currentComponent instanceof Directory)) {
            System.out.println("Error: Current component is not a directory.");
            return;
        }
    
        Directory currentDir = (Directory) currentComponent;
        IFileSystemComponent component = currentDir.getSubComponent(name);
        
        if (component == null) {
            System.out.println("Error: No such file or directory.");
        } else if (component instanceof File) {
            currentDir.remove(component);
        } else if (component instanceof Directory) {
            if (((Directory) component).getComponentCount() == 0) {
                currentDir.remove(component);
            } else {
                System.out.println("Error: Directory is not empty.");
            }
        }
    }
    

    public void deleteRecursive(String name) {
        if (!(currentComponent instanceof Directory)) {
            System.out.println("Error: Not a directory.");
            return;
        }
    
        Directory currentDir = (Directory) currentComponent;
        IFileSystemComponent component = currentDir.getSubComponent(name);
    
        if (component == null) {
            System.out.println("Error: Component does not exist.");
        } else {
            deleteComponentRecursively(component);
            currentDir.remove(component);
        }
    }
    
    private void deleteComponentRecursively(IFileSystemComponent component) {
        if (component instanceof Directory) {
            // Recursively delete children first
            for (IFileSystemComponent child : ((Directory) component).getChildren()) {
                deleteComponentRecursively(child);
            }
        }
        // If it's a file or an empty directory, it can be safely deleted
    }

    public void mkdir(String name) {
        // Check if the current component is a directory.
        if (!(currentComponent instanceof Directory)) {
            System.out.println("Error: Current component is not a directory.");
            return;
        }
    
        // Cast the current component to Directory.
        Directory currentDir = (Directory) currentComponent;
    
        // Check if a directory with the same name already exists.
        if (currentDir.getSubComponent(name) != null) {
            System.out.println("Error: A directory with the same name already exists.");
            return;
        }
    
        // Create the new directory.
        Directory newDir = new Directory(name, LocalDateTime.now(), currentDir.getDirectory() + "\\" + name);
        
        // Add the new directory to the current directory's children.
        currentDir.add(newDir);
    }
    

    public void touch(String name, long size) {
        // Check if the current component is a directory.
        if (!(currentComponent instanceof Directory)) {
            System.out.println("Error: Current component is not a directory.");
            return;
        }
    
        // Cast the current component to Directory.
        Directory currentDir = (Directory) currentComponent;
    
        // Check if a file with the same name already exists.
        if (currentDir.getSubComponent(name) != null) {
            System.out.println("Error: A file with the same name already exists.");
            return;
        }
    
           // Create the new file.
    File newFile = new File(name, size, LocalDateTime.now());

    // Set the full directory path for the file.
    newFile.setDirectory(currentDir.getDirectory() + "\\" + name);

    // Add the new file to the current directory's children.
    currentDir.add(newFile);
    }

   
    

    // Additional methods as needed

    public static void main(String[] args) {
        FileSystemManager fsm = new FileSystemManager();
        Scanner scanner = new Scanner(System.in);
    
        String line;
        System.out.println("Enter filesystem commands (type 'exit' to quit):");
    
        while (true) {
            System.out.print("> ");
            line = scanner.nextLine();
            if ("exit".equals(line)) {
                break;
            }
    
            String[] parts = line.split("\\s+");
            if (parts.length == 0) continue;
    
            String command = parts[0];
            switch (command.toLowerCase()) {
                case "mkdrive":
                    if (parts.length > 1) fsm.mkdrive(parts[1]);
                    else System.out.println("Drive name not specified.");
                    break;
                case "cd":
                    if (parts.length > 1) fsm.cd(parts[1]);
                    else System.out.println("Directory path not specified.");
                    break;
                case "mkdir":
                    if (parts.length > 1) fsm.mkdir(parts[1]);
                    else System.out.println("Directory name not specified.");
                    break;
                case "touch":
                    if (parts.length > 2) {
                        try {
                            long size = Long.parseLong(parts[2]);
                            fsm.touch(parts[1], size);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid size specified for the file.");
                        }
                    } else {
                        System.out.println("File name or size not specified.");
                    }
                    break;
                case "list":
                    fsm.list();
                    break;
                case "ls":
                    if (parts.length > 1) fsm.ls(parts[1]);
                    else System.out.println("Name not specified for ls command.");
                    break;
                case "delete":
                    if (parts.length > 2 && "-r".equals(parts[1])) {
                        fsm.deleteRecursive(parts[2]);
                    } else if (parts.length > 1) {
                        fsm.delete(parts[1]);
                    } else {
                        System.out.println("Name not specified for delete command.");
                    }
                    break;
                default:
                    System.out.println("Unknown command.");
                    break;
            }
        }
    
        scanner.close();
        System.out.println("File system manager exited.");
    }
    
}
