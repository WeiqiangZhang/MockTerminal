package driver;

/**
 * The File class represents a file inside the FileSystem. It can contain string
 * contents and it has a name.
 */
public class File extends FileObject {
  /**
   * the data that the file stores
   */
  private String contents;

  public File(String name) {
    super(name);
    contents = "";
  }

  public String getContents() {
    return contents;
  }

  public void setContents(String contents) {
    this.contents = contents;
  }

  /**
   * Appends some String onto the end of this file.
   * 
   * @param appendage the string to be added onto the file
   */
  public void append(String appendage) {
    this.contents = this.contents + '\n' + appendage;
  }

  public void clear() {
    this.contents = "";
  }
}
