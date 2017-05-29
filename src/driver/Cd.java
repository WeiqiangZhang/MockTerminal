package driver;

/**
 * The Cd class changes the current directory in JShell to a designated
 * directory.
 */
public class Cd extends Command {

  public Cd(JShell jShell) {
    super(jShell, "DIR", 1);
  }

  /**
   * This method finds the directory specified in the command arguments and sets
   * the Jshell's current working directory to it.
   * 
   * @param cmdArgs[] a string array with a command and all the arguments.
   */
  @Override
  public void run(String[] cmdArgs) {
    if (checkArgCount(cmdArgs)) {
      Directory newDir;
      try {
        newDir = parsePath(cmdArgs[1]);
        jShell.setCurrentDir(newDir);
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }
}
