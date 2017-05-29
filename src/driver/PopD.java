package driver;

/**
 * This class is responsible for popping the a directory out of the directory
 * stack and setting that as the current working directory.
 */
import java.util.Stack;

public class PopD extends Command {
  /**
   * The current stack of directories
   */
  private Stack<Directory> dirStack;

  public PopD(JShell jShell, Stack<Directory> dirStack) {
    // super(Usage message, argCount)
    super(jShell, "", 0);
    this.dirStack = dirStack;
  }

  /**
   * Pops out the next directory on the directory stack and then navigates to
   * it.
   * 
   * @param cmdArgs the user input split into command and arguments
   */
  @Override
  public void run(String cmdArgs[]) {
    if (checkArgCount(cmdArgs)) {
      if (!dirStack.isEmpty()) {
        jShell.setCurrentDir(this.dirStack.pop());
      } else {
        System.out.println("No directory on the stack");
      }
    }
  }

  public void set_dirStack(Stack<Directory> dirStack) {
    this.dirStack = dirStack;
  }

  public Stack<Directory> get_dirStack() {
    return this.dirStack;
  }
}
