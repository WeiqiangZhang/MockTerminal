package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import driver.Echo;
import driver.JShell;
import driver.Ls;
import driver.Mkdir;

public class LsTest {
  private Ls ls;
  private Mkdir mkdir;
  private String[] cmdArgs;
  private String[] cmdArgs2;
  private String[] cmdArgs3;
  private JShell jShell;
  private Echo echo;

  @Before
  public void setUp() throws Exception {
    jShell = new JShell();
    ls = new Ls(jShell);
    mkdir = new Mkdir(jShell);
    echo = new Echo(jShell);
  }

  /**
   * This method checks the directory printed in the current directory is
   * correct
   */
  @Test
  public void testSingleDirectoryLs() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    cmdArgs = new String[2];
    cmdArgs2 = new String[1];
    cmdArgs[0] = "mkdir";
    cmdArgs[1] = "dir1";
    mkdir.run(cmdArgs);
    cmdArgs2[0] = "ls";
    ls.run(cmdArgs2);
    // True iff the dir1 is printed
    assertEquals("dir1" + System.lineSeparator(), outContent.toString());
  }

  /**
   * This method checks the directories printed in the current directory is
   * correct
   */
  @Test
  public void testMultipleDirectoryLs() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    cmdArgs = new String[3];
    cmdArgs2 = new String[1];
    cmdArgs[0] = "mkdir";
    cmdArgs[1] = "dir1";
    cmdArgs[2] = "dir2";
    mkdir.run(cmdArgs);
    cmdArgs2[0] = "ls";
    ls.run(cmdArgs2);
    // True iff the dir1 and dir2 are printed
    assertEquals(
        "dir1" + System.lineSeparator() + "dir2" + System.lineSeparator(),
        outContent.toString());
  }

  /**
   * This method checks the directory printed in the given relative path is
   * correct
   */
  @Test
  public void testRelativePathLs() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    cmdArgs = new String[3];
    cmdArgs2 = new String[2];
    cmdArgs[0] = "mkdir";
    cmdArgs[1] = "dir1";
    cmdArgs[2] = "dir1/dir2";
    mkdir.run(cmdArgs);
    cmdArgs2[0] = "ls";
    cmdArgs2[1] = "dir1";
    ls.run(cmdArgs2);
    // True iff the dir1 is printed from the given path dir1
    assertEquals("dir1: " + System.lineSeparator() + "dir2"
        + System.lineSeparator() + System.lineSeparator(),
        outContent.toString());
  }

  /**
   * This method checks the directory printed in the given absolute path is
   * correct even if it has directories within itself
   */
  @Test
  public void testHasDirectoryLs() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    cmdArgs = new String[4];
    cmdArgs2 = new String[2];
    cmdArgs[0] = "mkdir";
    cmdArgs[1] = "dir1";
    cmdArgs[2] = "dir1/dir2";
    cmdArgs[3] = "dir1/dir2/dir3";
    mkdir.run(cmdArgs);
    cmdArgs2[0] = "ls";
    cmdArgs2[1] = "/dir1";
    ls.run(cmdArgs2);
    // True iff the dir2 is printed from the given path /dir1
    assertEquals("/dir1: " + System.lineSeparator() + "dir2"
        + System.lineSeparator() + System.lineSeparator(),
        outContent.toString());
  }


  /**
   * This method checks the file printed in the given absolute path is correct
   */
  @Test
  public void testAbsolutePathLs() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    cmdArgs = new String[2];
    cmdArgs2 = new String[2];
    cmdArgs3 = new String[4];
    cmdArgs[0] = "mkdir";
    cmdArgs[1] = "dir1";
    mkdir.run(cmdArgs);
    cmdArgs3[0] = "echo";
    cmdArgs3[1] = "\"\"";
    cmdArgs3[2] = ">";
    cmdArgs3[3] = "/dir1/file1";
    echo.run(cmdArgs3);
    cmdArgs2[0] = "ls";
    cmdArgs2[1] = "/dir1";
    ls.run(cmdArgs2);
    // True iff the file1 directory is printed from the given path /dir1
    assertEquals("/dir1: " + System.lineSeparator() + "file1"
        + System.lineSeparator() + System.lineSeparator(),
        outContent.toString());
  }

  /**
   * This method checks the error message is correct if path given is not real
   */
  @Test
  public void testErrorLs() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    cmdArgs2 = new String[2];
    cmdArgs2[0] = "ls";
    cmdArgs2[1] = "dir1";
    ls.run(cmdArgs2);
    // True iff proper error message is printed
    assertEquals("Error occured with the parameter " + "\"dir1\""
        + System.lineSeparator() + "Cannot find specified directory or file"
        + System.lineSeparator(), outContent.toString());
  }
}
