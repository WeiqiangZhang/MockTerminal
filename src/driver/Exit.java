package driver;

/**
 * The Exit class exits the program on request
 */
public class Exit extends Command {

  public Exit(JShell jShell) {
    super(jShell, "", 0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void run(String[] cmdArgs) {
    if (checkArgCount(cmdArgs)) {
      // If the command has no arguments then exit
      jShell.setExit(true);
    }
  }
}
