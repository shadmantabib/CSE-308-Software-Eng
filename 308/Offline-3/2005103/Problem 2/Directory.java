import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Directory implements IFileSystemComponent {
    private String name;
    private List<IFileSystemComponent> children = new ArrayList<>();
    private LocalDateTime creationTime;
    private String directory; // This will be the path
    private IFileSystemComponent parent;

    public Directory(String name, LocalDateTime creationTime, String directory) {
        this.name = name;
        this.creationTime = creationTime;
        this.directory = directory;
    }
    public List<IFileSystemComponent> getChildren() {
        return new ArrayList<>(children); // Return a copy of the children list to avoid external modifications.
    }
    @Override
    public IFileSystemComponent getParent() {
        return this.parent;
    }

    @Override
    public void setParent(IFileSystemComponent parent) {
        this.parent = parent;
    }

    public IFileSystemComponent getSubComponent(String name) {
        for (IFileSystemComponent child : children) {
            if (child.getName().equals(name)) {
                return child;
            }
        }
        return null;
    }
    @Override
    public void add(IFileSystemComponent component) {
        children.add(component);
        component.setParent(this);
    }
    

    @Override
    public void remove(IFileSystemComponent component) {
        children.remove(component);
    }

    @Override
    public IFileSystemComponent getChild(int i) {
        return children.get(i);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getSize() {
        long totalSize = 0;
        for (IFileSystemComponent child : children) {
            totalSize += child.getSize();
        }
        return totalSize;
    }
    @Override
    public String getType() {
        return "Folder";
    }

    @Override
    public String getDirectory() {
        return directory;
    }

    @Override
    public int getComponentCount() {
        return children.size();
    }

    @Override
    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "Directory: " + name);
        for (IFileSystemComponent component : children) {
            component.display(indent + "    ");
        }
    }
}
