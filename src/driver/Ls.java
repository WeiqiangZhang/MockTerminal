package driver;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The Ls program either prints directories and files of the current directory,
 * prints directories and files of the directory in the given path, or prints
 * the name of the file if the path leads to a file.
 */
public class Ls extends Command {

  public Ls(JShell jShell) {
    // super(Usage message, argCount)
    super(jShell, " [PATH ...]", -1);
  }

  /**
   * This method loops through all the given path/directory names and checks if
   * the path leads to a file or directory and gives that information to
   * printDirectoryContent if it is a directory or print the file name if it is
   * a file.
   * 
   * @param cmdArgs a string array with a command and all the arguments.
   */
  @Override
  public void run(String[] cmdArgs) {
    // Check argument count
    if (cmdArgs.length == 1) {
      // print contents of current directory
      printDirectoryContent(jShell.getCurrentDir());
    } else {
      for (int i = 1; i < cmdArgs.length; i++) {
        String[] splitDir = cmdArgs[i].split("/");
        String fileName = splitDir[splitDir.length - 1];
        // Cut out the file name and only have the path
        String inPath = cmdArgs[i].substring(0,
            cmdArgs[i].length() - splitDir[splitDir.length - 1].length());
        Directory specified;
        try {
          // Get the wanted directory of the given path
          specified = parsePath(inPath);
          // Make a FileObject variable
          FileObject fo = specified.getFileObject(fileName);
          // If the FileObject is an instance of a File print the file name
          if (fo instanceof File) {
            System.out.println(cmdArgs[i]);
          }
          // If it's a directory print the call printDirectoryContent
          else if (fo instanceof Directory) {
            System.out.println(cmdArgs[i] + ": ");
            printDirectoryContent((Directory) fo);
            System.out.println();
          }
        } catch (Exception e) {
          System.out.println(
              "Error occured with the parameter \"" + cmdArgs[i] + "\"");
          System.out.println(e.getMessage());
        }
      }
    }
  }

  /**
   * This method prints the directories and files of the specified directory in
   * an alphabetical order.
   * 
   * @param specified the directory that is specified through path or current
   *        directory.
   */
  public void printDirectoryContent(Directory specified) {
    // get all the file objects of specified
    ArrayList<FileObject> data = specified.getData();
    ArrayList<String> orderedList = new ArrayList<String>();
    for (FileObject fo : data) {
      // get the name of the file object
      String name = fo.getName();
      // check if the file object is a file object
      orderedList.add(name);
    }
    // alphabetical sorting
    Collections.sort(orderedList);
    for (String dirName : orderedList) {
      System.out.println(dirName);
    }
  }
}
