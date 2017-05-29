package driver;

/**
 * The Mkdir creates a new directory in the current directory, or in the
 * absolute or relative path directory.
 */
public class Mkdir extends Command {

  public Mkdir(JShell jShell) {
    // super(Usage message, argCount)
    super(jShell, "DIR ...", -1);
  }

  /**
   * This method loops through all the given path/directory names and gives each
   * one to makeDirectory method.
   * 
   * @param cmdArgs a string array with a command and all the arguments
   */
  @Override
  public void run(String[] cmdArgs) {
    // Could have multiple arguments
    if (checkArgCount(cmdArgs)) {
      for (int i = 1; i < cmdArgs.length; i++) {
        // For each argument make a directory
        makeDirectory(cmdArgs[i]);
      }
    }
  }

  /**
   * This method checks if the total number of command and arguments are within
   * valid range, if not give an error message
   * 
   * @param cmdArgs the string array containing the command and arguments
   * 
   * @return return true if and only if it contains valid number of
   *                arguments
   */
  @Override
  protected boolean checkArgCount(String[] cmdArgs) {
    if (cmdArgs.length < 2) {
      System.out.println(
          cmdArgs[0] + " usage: " + cmdArgs[0] + ' ' + getProperUsage());
      return false;
    }
    return true;
  }

  /**
   * This method either first navigate to the appropriate location
   * to make the new directory and then create a directory there
   * 
   * @param inputDirectory  the name of the new directory or path
   */
  public void makeDirectory(String inputDirectory) {
    String[] splitDir = inputDirectory.split("/");
    String dirName = splitDir[splitDir.length - 1];
    // Cut out the file name and only have the path
    String inPath = inputDirectory.substring(0,
        inputDirectory.length() - splitDir[splitDir.length - 1].length());
    Directory specified;
    try {
      // Get the directory of the specified path
      specified = parsePath(inPath);
    } catch (Exception e) {
      System.out.println(
          "Error occured with the parameter \"" + inputDirectory + "\"");
      System.out.println(e.getMessage());
      return;
    }
    // Add the new directory to the given directory
    try {
      // Make new directory object
      Directory newDir = new Directory(dirName);
      // Add the new directory to the current directory
      specified.addFileObject(newDir);
    } catch (Exception e) {
      System.out.println(
          "Error occured with the parameter \"" + inputDirectory + "\"");
      System.out.println("Error: " + e.getMessage());
      return;
    }
  }
}
