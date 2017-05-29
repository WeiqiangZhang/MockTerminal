package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import driver.Exit;
import driver.JShell;

public class ExitTest {

  private JShell jShell;
  private Exit exit;

  @Before
  public void setUp() throws Exception {
    jShell = new JShell();
    exit = new Exit(jShell);
  }

  /**
   * Test for exit with proper usage
   */
  @Test
  public void testExit1() {
    String[] cmdArgs = new String[1];
    jShell.setExit(false);
    cmdArgs[0] = "exit";
    exit.run(cmdArgs);
    assertTrue(jShell.getExit());
  }

  /**
   * Test for exit with improper usage
   */
  @Test
  public void testExit2() {
    String[] cmdArgs = new String[2];
    jShell.setExit(false);
    cmdArgs[0] = "exit";
    cmdArgs[1] = "please";
    exit.run(cmdArgs);
    assertFalse(jShell.getExit());
  }
}
