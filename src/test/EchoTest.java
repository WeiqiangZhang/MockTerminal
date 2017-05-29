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
import driver.Ls;
import driver.Mkdir;

public class EchoTest {
  private Ls ls;
  private JShell jShell;
  private Echo echo;
  private Cat cat;
  private Mkdir mkdir;
  private String[] cmdArgs;
  private String[] cmdArgs2;

  @Before
  public void setUp() throws Exception {
    jShell = new JShell();
    ls = new Ls(jShell);
    echo = new Echo(jShell);
    cat = new Cat(jShell);
    mkdir = new Mkdir(jShell);
  }

  /**
   * This method checks if a new empty file file is made
   */
  @Test
  public void testEchoEmptyFile() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    cmdArgs = new String[4];
    cmdArgs2 = new String[1];
    cmdArgs[0] = "echo";
    cmdArgs[1] = "\"\"";
    cmdArgs[2] = ">";
    cmdArgs[3] = "file1";
    echo.run(cmdArgs);
    cmdArgs2[0] = "ls";
    ls.run(cmdArgs2);
    // True iff the file name printed in the current directory is file1
    assertEquals("file1" + System.lineSeparator(), outContent.toString());
  }

  /**
   * This method checks out if replaceEcho replaces the contents of the file
   * with new String
   */
  @Test
  public void testReplaceEcho() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    cmdArgs = new String[4];
    cmdArgs2 = new String[2];
    cmdArgs[0] = "echo";
    cmdArgs[1] = "\"abc\"";
    cmdArgs[2] = ">";
    cmdArgs[3] = "file1";
    echo.run(cmdArgs);
    cmdArgs[1] = "\"efg\"";
    cmdArgs[2] = ">";
    cmdArgs[3] = "file1";
    echo.run(cmdArgs);
    cmdArgs2[0] = "cat";
    cmdArgs2[1] = "file1";
    cat.run(cmdArgs2);
    // True iff the contents of this file is the String "efg"
    assertEquals("efg" + System.lineSeparator(), outContent.toString());
  }

  /**
   * This method checks out if appendEcho appends the contents of the file with
   * new String
   */
  @Test
  public void testAppendEcho() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    cmdArgs = new String[4];
    cmdArgs2 = new String[2];
    cmdArgs[0] = "echo";
    cmdArgs[1] = "\"abc\"";
    cmdArgs[2] = ">";
    cmdArgs[3] = "file1";
    echo.run(cmdArgs);
    cmdArgs[1] = "\" efg\"";
    cmdArgs[2] = ">>";
    cmdArgs[3] = "file1";
    echo.run(cmdArgs);
    cmdArgs2[0] = "cat";
    cmdArgs2[1] = "file1";
    cat.run(cmdArgs2);
    // True iff "abc efg" is the contents of file1
    assertEquals("abc efg" + System.lineSeparator(), outContent.toString());
  }

  /**
   * This method tests relative pathing when making the file
   */
  @Test
  public void testRelativeEcho() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    cmdArgs = new String[4];
    cmdArgs2 = new String[2];
    cmdArgs2[0] = "mkdir";
    cmdArgs2[1] = "dir";
    mkdir.run(cmdArgs2);
    cmdArgs[0] = "echo";
    cmdArgs[1] = "\"efg\"";
    cmdArgs[2] = ">>";
    cmdArgs[3] = "dir/file1";
    echo.run(cmdArgs);
    cmdArgs2[0] = "cat";
    cmdArgs2[1] = "dir/file1";
    cat.run(cmdArgs2);
    // True iff "efg" is the contents of the file1
    assertEquals("efg" + System.lineSeparator(), outContent.toString());
  }

  /**
   * This method tests absolute pathing when making the file
   */
  @Test
  public void testAbsoluteEcho() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    cmdArgs = new String[4];
    cmdArgs2 = new String[2];
    cmdArgs2[0] = "mkdir";
    cmdArgs2[1] = "dir";
    mkdir.run(cmdArgs2);
    try {
      jShell.setCurrentDir(
          (Directory) (jShell.getCurrentDir()).getFileObject("dir"));
    } catch (Exception e) {
    }
    cmdArgs[0] = "echo";
    cmdArgs[1] = "\"efg\"";
    cmdArgs[2] = ">>";
    cmdArgs[3] = "/dir/file1";
    echo.run(cmdArgs);
    cmdArgs2[0] = "cat";
    cmdArgs2[1] = "/dir/file1";
    cat.run(cmdArgs2);
    // True iff "efg" is the contents of the file1
    assertEquals("efg" + System.lineSeparator(), outContent.toString());
  }
}
