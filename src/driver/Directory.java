package driver;

/**
 * A class to represent a directory inside of a file system
 */
import java.util.ArrayList;

public class Directory extends FileObject {
  /**
   * holds a list of all the objects inside this directory
   */
  private ArrayList<FileObject> data;

  public Directory(String name) {
    super(name);
    data = new ArrayList<FileObject>();
  }

  /**
   * Gets the objects in the directory.
   * 
   * @return ArrayList of the objects in this directory.
   */
  public ArrayList<FileObject> getData() {
    return data;
  }

  public void setData(ArrayList<FileObject> data) {
    this.data = data;
  }

  /**
   * Add the given FileObject to this directory.
   * 
   * @param fo FileObject to be added to this Directory.
   * 
   * @throws Exception if the FileObject has the same name with another
   *         FileObject in this directory.
   */
  public void addFileObject(FileObject fo) throws Exception {
    boolean flag = true;
    for (Object obj : data) {
      if (((FileObject) obj).getName().equals(fo.getName())) {
        flag = false;
      }
    }
    if (flag) {
      fo.setParentDirectory(this);
      data.add(fo);
    } else {
      throw new Exception("Repeated Name");
    }
  }

  /**
   * Gets the FileObject with a given name
   * 
   * @param name the name of the wanted file.
   * 
   * @return FileObject of the requested file.
   * 
   * @throws Exception if the file does not exist.
   */
  public FileObject getFileObject(String name) throws Exception {
    for (FileObject fo : data) {
      if (name.equals(fo.getName())) {
        return fo;
      }
    }
    throw new Exception("Cannot find specified directory or file");
  }

  /**
   * Checks whether this directory has a FileObject.
   * 
   * @param name name of FileObject to be searched for.
   * 
   * @return true iff a FileObject with the name asked for exists.
   */
  public boolean hasFileObject(String name) {
    for (FileObject obj : data) {
      if (obj.getName().equals(name)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Delete the FileObject with the name.
   * 
   * @param name name of the FileObject to be deleted.
   */
  public void deleteFileObject(String name) {
    // Find the object
    for (int i = 0; i < data.size(); i++) {
      if (data.get(i).getName().equals(name)) {
        // Simply remove it from the directory list
        data.remove(i);
        break;
      }
    }
  }
}
