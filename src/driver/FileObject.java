package driver;

/**
 * Abstract class used to describe how a FileObject should look like and the
 * basic functionality of all items in the file system. Such as having a name
 * and potentially a parent directory.
 */
public abstract class FileObject {
  /**
   * the name of the file object
   */
  private String name;
  /**
   * the parent directory of this FileObject
   */
  private Directory parentDirectory;

  public FileObject(String name) {
    this.setName(name);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Directory getParentDirectory() {
    return parentDirectory;
  }

  public void setParentDirectory(Directory parentDirectory) {
    if (!parentDirectory.hasFileObject(this.getName())) {
      this.parentDirectory = parentDirectory;
    } else {
      System.out.println("Error: Duplicate names");
    }
  }

}
