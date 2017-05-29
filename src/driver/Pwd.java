package driver;

/**
 * This class is responsible for the command functionality that is to print the
 * current working directory
 */
public class Pwd extends Command {

  public Pwd(JShell jShell) {
    super(jShell, "", 0);
  }

  /**
   * Prints the full path of the current working directory
   * 
   * @param cmdArgs the user input split into command and arguments
   */
  @Override
  public void run(String[] cmdArgs) {
    if (checkArgCount(cmdArgs)) {
      Directory curr = jShell.getCurrentDir();
      String retv = curr.getName();

      // Keep looking for parents until there is no more parents
      // That should be the root
      while (curr.getParentDirectory() != null) {
        curr = curr.getParentDirectory();
        // Place the new current directory on the front
        retv = curr.getName() + "/" + retv;
      }

      // Cleanup formatting below
      if (retv.length() > 1) {
        retv = retv.substring(1, retv.length());
        retv = retv + "/";
      }
      System.out.println(retv);
    }
  }
}
