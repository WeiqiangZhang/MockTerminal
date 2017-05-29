package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import driver.Cat;
import driver.Directory;
import driver.Echo;
import driver.JShell;
import driver.Mkdir;

public class CatTest {
  private Mkdir mkdir;
  private Echo echo;
  private Cat cat;
  private String[] cmdArgs;
  private String[] cmdArgs2;
  private JShell jShell;

  @Before
  public void setUp() throws Exception {
    jShell = new JShell();
    cat = new Cat(jShell);
    mkdir = new Mkdir(jShell);
    echo = new Echo(jShell);
  }

  /**
   * This method checks relative pathing to get the contents of a file
   */
  @Test
  public void testRelativeCat() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    cmdArgs = new String[2];
    cmdArgs[0] = "mkdir";
    cmdArgs[1] = "dir";
    mkdir.run(cmdArgs);
    cmdArgs2 = new String[4];
    cmdArgs2[0] = "echo";
    cmdArgs2[1] = "my file content";
    cmdArgs2[2] = ">";
    cmdArgs2[3] = "dir/file1";
    echo.run(cmdArgs2);
    cmdArgs[0] = "cat";
    cmdArgs[1] = "dir/file1";
    cat.run(cmdArgs);
    // True iff file1 content: "my file content" is printed
    assertEquals("my file content" + System.lineSeparator(),
        outContent.toString());
  }

  /**
   * This method checks absolute pathing to get the contents of a file and also
   * checks error message
   */
  @Test
  public void testAbsoluteAndErrorCat() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    cmdArgs = new String[2];
    cmdArgs[0] = "mkdir";
    cmdArgs[1] = "dir";
    mkdir.run(cmdArgs);
    cmdArgs2 = new String[4];
    cmdArgs2[0] = "echo";
    cmdArgs2[1] = "my file content";
    cmdArgs2[2] = ">";
    cmdArgs2[3] = "dir/file1";
    echo.run(cmdArgs2);
    try {
      jShell.setCurrentDir(
          (Directory) (jShell.getCurrentDir()).getFileObject("dir"));
    } catch (Exception e) {
    }
    cmdArgs[0] = "cat";
    cmdArgs[1] = "/dir/file1";
    cat.run(cmdArgs);
    // True iff file1 content: "my file content" is printed
    assertEquals("my file content" + System.lineSeparator(),
        outContent.toString());
    cmdArgs[0] = "cat";
    cmdArgs[1] = "dir/file1";
    cat.run(cmdArgs);
    // True iff file1 content: "my file content" is printed followed by the
    // proper error message
    assertEquals("my file content" + System.lineSeparator()
        + "Error with argument \"dir/file1\"" + System.lineSeparator()
        + "Cannot find specified directory or file" + System.lineSeparator(),
        outContent.toString());
  }

  /**
   * This method checks if Cat can get the contents of multiple files followed
   * by three white spaces
   */
  @Test
  public void testMutipleFileCat() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    cmdArgs = new String[4];
    cmdArgs[0] = "echo";
    cmdArgs[1] = "my file content";
    cmdArgs[2] = ">";
    cmdArgs[3] = "file1";
    echo.run(cmdArgs);
    cmdArgs[1] = "my file content2";
    cmdArgs[2] = ">";
    cmdArgs[3] = "file2";
    echo.run(cmdArgs);
    cmdArgs[1] = "my file content3";
    cmdArgs[2] = ">";
    cmdArgs[3] = "file3";
    echo.run(cmdArgs);
    cmdArgs[0] = "cat";
    cmdArgs[1] = "file1";
    cmdArgs[2] = "file2";
    cmdArgs[3] = "file3";
    cat.run(cmdArgs);
    // True iff my file content is printed followed by three white spaces, then
    // my file content2 is printed followed by three white spaces, and then
    // my file content3 is printed followed by a single white space
    assertEquals(
        "my file content" + System.lineSeparator() + "\n\n"
            + System.lineSeparator() + "my file content2"
            + System.lineSeparator() + "\n\n" + System.lineSeparator()
            + "my file content3" + System.lineSeparator(),
        outContent.toString());
  }
}
