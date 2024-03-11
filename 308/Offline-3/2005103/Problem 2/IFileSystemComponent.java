import java.time.LocalDateTime;

public interface IFileSystemComponent {
    String getName();
    long getSize();
    String getType();
    String getDirectory();
    int getComponentCount();
    LocalDateTime getCreationTime();
    void add(IFileSystemComponent component) throws UnsupportedOperationException;
    void remove(IFileSystemComponent component) throws UnsupportedOperationException;
    IFileSystemComponent getChild(int i) throws UnsupportedOperationException;
    void display(String indent);
    IFileSystemComponent getParent();
    void setParent(IFileSystemComponent parent);
    
}
