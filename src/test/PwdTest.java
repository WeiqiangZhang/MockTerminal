package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import driver.JShell;
import driver.Pwd;
import driver.Directory;
import driver.Mkdir;

public class PwdTest {

  private JShell jShell;
  private Pwd pwd;
  private String[] cmdArgs;
  private Mkdir mkdir;


  @Before
  public void setUp() throws Exception {
    jShell = new JShell();
    pwd = new Pwd(jShell);
    mkdir = new Mkdir(jShell);

  }

  /**
   * Test with proper usage with only root
   */
  @Test
  public void testPwd1() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    cmdArgs = new String[1];
    cmdArgs[0] = "pwd";

    String ExpectedOutput = "/" + System.lineSeparator();

    pwd.run(cmdArgs);
    assertEquals(ExpectedOutput, outContent.toString());

  }

  /**
   * Test with improper usage
   */

  @Test
  public void testPwd2() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    cmdArgs = new String[2];
    cmdArgs[0] = "pwd";
    cmdArgs[1] = "1";

    String ExpectedOutput = "/" + System.lineSeparator();

    pwd.run(cmdArgs);
    assertFalse(ExpectedOutput == outContent.toString());
  }

  /**
   * Test with a path other than root
   * 
   * @throws Exception
   */

  @Test
  public void testPwd3() throws Exception {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    String[] cmdArgs2 = new String[2];
    cmdArgs2[0] = "mkdir";
    cmdArgs2[1] = "a";

    mkdir.run(cmdArgs2);

    cmdArgs = new String[1];
    cmdArgs[0] = "pwd";

    jShell
        .setCurrentDir((Directory) (jShell.getCurrentDir()).getFileObject("a"));

    pwd.run(cmdArgs);

    String expectedOutput = "/a/" + System.lineSeparator();
    assertEquals(expectedOutput, outContent.toString());

  }
}
