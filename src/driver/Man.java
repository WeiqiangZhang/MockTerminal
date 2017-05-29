package driver;

/**
 * Prints the man pages for a requested command.
 */
import java.io.BufferedReader;
import java.io.FileReader;

public class Man extends Command {

  public Man(JShell jShell) {
    // super(Usage message, argCount)
    super(jShell, "CMD", 1);
  }

  /**
   * Opens the file containing the documentation for a command and prints it.
   * 
   * @param cmdArgs a String array that contains the command and arguments.
   */
  @Override
  public void run(String[] cmdArgs) {
    // Check argument count
    if (super.checkArgCount(cmdArgs)) {
      try {
        BufferedReader br = new BufferedReader(
            new FileReader("manpages/" + cmdArgs[1] + ".txt"));
        String line = br.readLine();
        while (line != null) {
          System.out.println(line);
          line = br.readLine();
        }
        br.close();
      } catch (Exception e) {
        System.out.println("Man pages not found for " + cmdArgs[1]);
      }
    }
  }
}
