package driver;

/**
 * This is an abstract class used to define other commands down the development
 * line.
 */
public abstract class Command {
  /**
   * the proper usage message after the error:
   */
  private String properUsage;
  /**
   * the number of arguments this command should have. -1 signals that it is not
   * just a single number
   */
  private int numArgs;
  /**
   * Access to Jshell and all the variables and directories that it holds
   */
  protected JShell jShell;

  public Command(JShell jShell, String properUsage, int numArgs) {
    this.setProperUsage(properUsage);
    this.numArgs = numArgs;
    this.jShell = jShell;
  }

  /**
   * The run method is overridden to provide some functionality This is the
   * method ran by JShell to execute some command.
   * 
   * @param cmdArgs a string array with a command and all the arguments.
   */
  public void run(String[] cmdArgs) {
    // Does nothing in this class
    // Needs to be implemented by subclass
  }

  /**
   * Check argument count if they are equal and prints usage method if it is not
   * correct.
   * 
   * @param cmdArgs a string array with a command and all the arguments.
   * 
   * @return return True iff the command split into cmdArgs is equal to the
   *         required argument count.
   */
  protected boolean checkArgCount(String[] cmdArgs) {
    if (cmdArgs.length != (this.numArgs + 1)) {
      System.out.println(
          cmdArgs[0] + " usage: " + cmdArgs[0] + ' ' + getProperUsage());
      return false;
    }
    return true;
  }

  public String getProperUsage() {
    return properUsage;
  }

  /**
   * Attempts to parse a given path to become a directory.
   * 
   * @param path string representation of a path in the file system.
   * 
   * @return directory object of the path given.
   * 
   * @throws Exception if the path is not a directory or does not exist.
   */
  public Directory parsePath(String path) throws Exception {
    Directory specified;

    // Bases cases for simple paths because blanks space and slashes are deleted
    if (path.equals("")) {
      return jShell.getCurrentDir();
    } else if (path.equals("/")) {
      return jShell.getRoot();
    }

    // Find the proper starting position. Then treat it as relative movement
    if (path.length() > 0 && path.charAt(0) == '/') {
      specified = jShell.getRoot();
      path = path.substring(1);
    } else {
      specified = jShell.getCurrentDir();
    }

    // Split into tokens
    String[] splitPath = path.split("/");
    // For each token try to navigate to it
    for (String dirName : splitPath) {
      if (dirName.equals("..")) {
        specified = specified.getParentDirectory();
        if (specified == null) {
          // If there is no parent directory then the directory is root
          // Therefore stay at root
          specified = jShell.getRoot();
        }
      } else {
        // Not a special case so just navigate into it
        specified = (Directory) specified.getFileObject(dirName);
      }
    }
    return specified;
  }

  public void setProperUsage(String properUsage) {
    this.properUsage = properUsage;
  }
}
