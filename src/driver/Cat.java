package driver;

/**
 * The Cat class displays the contents of all the given files and concatenates
 * them into the shell.
 */
public class Cat extends Command {
  public Cat(JShell jShell) {
    super(jShell, "FILE1 [FILE2 ...]", -1);
  }

  /**
   * This method loops through all the given path/directory names and checks if
   * the path leads to contains the given file and gives that information to
   * catFile.
   * 
   * @param cmdArgs a string array with a command and all the arguments.
   */
  @Override
  public void run(String cmdArgs[]) {
    if (checkArgCount(cmdArgs)) {
      for (int i = 1; i < cmdArgs.length; i++) {
        String[] splitDir = cmdArgs[i].split("/");
        String fileName = splitDir[splitDir.length - 1];
        // Cut out the file name and only have the path
        String inPath = cmdArgs[i].substring(0,
            cmdArgs[i].length() - splitDir[splitDir.length - 1].length());
        Directory specified;
        try {
          // Get the directory of the specified path
          specified = parsePath(inPath);
        } catch (Exception e) {
          System.out.println("Error with argument \"" + cmdArgs[i] + '\"');
          System.out.println(e.getMessage());
          continue;
        }
        catFile(fileName, specified);
        // If it is not yet the last give file/path, double space
        if (i + 1 != cmdArgs.length) {
          System.out.println("\n\n");
        }
      }
    }
  }

  /**
   * This method checks if the total number of command and arguments are within
   * valid range, if not give an error message.
   * 
   * @param cmdArgs the string array containing the command and arguments.
   * 
   * @return return false if and only if it does not contain valid number of
   *         arguments else return true.
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
   * This method prints the contents of the given file.
   * 
   * @param fileName name of the wanted file.
   * 
   * @param specifiedDir the specified directory of the wanted file.
   * 
   */
  private void catFile(String fileName, Directory specifiedDir) {
    File specifiedFile;
    try {
      // Make the new file object point to the file object of the file name
      specifiedFile = (File) specifiedDir.getFileObject(fileName);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return;
    }
    assert (specifiedFile != null);
    // Print the contents of the file
    System.out.println(specifiedFile.getContents());
  }
}
