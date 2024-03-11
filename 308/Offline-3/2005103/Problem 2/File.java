import java.time.LocalDateTime;

public class File implements IFileSystemComponent {
    private String name;
    private long size;
    private final String type = "File";
    private String directory; // Assuming the directory will be set after instantiation
    private LocalDateTime creationTime;
    private IFileSystemComponent parent;
    public File(String name, long size, LocalDateTime creationTime) {
        this.name = name;
        this.size = size;
        this.creationTime = creationTime;
    }
    @Override
    public IFileSystemComponent getParent() {
        return this.parent;
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
        return size;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getDirectory() {
        return directory;
    }

    @Override
    public int getComponentCount() {
        // Files do not contain components, so this will always return 0.
        return 0;
    }

    @Override
    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    @Override
    public void add(IFileSystemComponent component) {
        throw new UnsupportedOperationException("Cannot add to a file.");
    }


    @Override
    public void remove(IFileSystemComponent component) {
        throw new UnsupportedOperationException("Cannot remove from a file.");
    }

    @Override
    public IFileSystemComponent getChild(int i) {
        throw new UnsupportedOperationException("Files do not have children components.");
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "File: " + name + ", Size: " + size + " bytes, Created: " + creationTime);
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }
}
