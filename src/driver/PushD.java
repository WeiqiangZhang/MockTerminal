package driver;

import java.util.Stack;

/**
 * This class is responsible for adding directories into the directory stack and
 * navigating into a new directory given by the user afterwards
 */
public class PushD extends Command {
  /**
   * The current stack of directories
   */
  private Stack<Directory> dirStack;

  public PushD(JShell jShell, Stack<Directory> dirStack) {
    super(jShell, "DIR", 1);
    this.dirStack = dirStack;
  }

  /**
   * Pushes a the current directory into the directory stack and navigate to a
   * new directory
   * 
   * @param cmdArgs the user input split into command and arguments
   */
  @Override
  public void run(String[] cmdArgs) {
    if (checkArgCount(cmdArgs)) {
      // Try and find the specified directory
      Directory specified;
      try {
        specified = parsePath(cmdArgs[1]);
      } catch (Exception e) {
        System.out.println(e.getMessage());
        return;
      }
      assert (specified != null);
      this.dirStack.push(jShell.getCurrentDir());
      jShell.setCurrentDir(specified);
    }
  }
}
