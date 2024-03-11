import java.time.LocalDateTime;

public class Drive implements IFileSystemComponent {
    private String name;
    private Directory rootDirectory;
    private LocalDateTime creationTime;
    private IFileSystemComponent parent;
    public Drive(String name) {
        this.name = name;
        this.creationTime = LocalDateTime.now();
        // The root directory of a drive has the same name as the drive for identification purposes
        this.rootDirectory = new Directory(name, this.creationTime, name + ":\\");
    }
    @Override
    public IFileSystemComponent getParent() {
        return this.parent;
    }
    public Directory getRootDirectory() {
        return rootDirectory;
    }
    

    @Override
    public void setParent(IFileSystemComponent parent) {
        this.parent = parent;
    }


    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public long getSize() {
        // The size of the drive is the size of its root directory
        return rootDirectory.getSize();
    }

    @Override
    public String getType() {
        return "Drive";
    }

    @Override
    public String getDirectory() {
        // The drive itself does not have a directory path as it is the top-level component
        return name + ":\\";
    }

    @Override
    public int getComponentCount() {
        // The count is the number of components in the root directory
        return rootDirectory.getComponentCount();
    }

    @Override
    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    @Override
    public void add(IFileSystemComponent component) {
        // Add components to the root directory of the drive
        rootDirectory.add(component);
    }

    @Override
    public void remove(IFileSystemComponent component) {
        rootDirectory.remove(component);
    }

    @Override
    public IFileSystemComponent getChild(int i) {
        return rootDirectory.getChild(i);
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "Drive: " + name);
        rootDirectory.display(indent + "    ");
    }
}
