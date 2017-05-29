package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import driver.Directory;
import driver.JShell;
import driver.Mkdir;

public class MkdirTest {
  private Mkdir mkdir;
  private JShell jShell;
  private Directory dir2;

  @Before
  public void setUp() throws Exception {
    jShell = new JShell();
    mkdir = new Mkdir(jShell);
  }

  /**
   * This method adds a single directory to the root directory and checks if the
   * directory has been created
   */
  @Test
  public void testSingleDirectoryMkdir() {
    String[] cmdArgs;
    cmdArgs = new String[2];
    cmdArgs[0] = "mkdir";
    cmdArgs[1] = "dir1";
    mkdir.run(cmdArgs);
    // True iff the dir1 directory is found
    assertTrue((jShell.getCurrentDir()).hasFileObject("dir1"));
  }

  /**
   * This method adds multiple directories to the root directory and checks if
   * the directory has been created
   */
  @Test
  public void testMultipleDirectoryMkdir() {
    String[] cmdArgs;
    cmdArgs = new String[3];
    cmdArgs[0] = "mkdir";
    cmdArgs[1] = "dir1";
    cmdArgs[2] = "dir2";
    mkdir.run(cmdArgs);
    // True iif dir1 and dir2 are found
    assertTrue((jShell.getCurrentDir()).hasFileObject("dir1")
        && (jShell.getCurrentDir()).hasFileObject("dir2"));
  }

  /**
   * adds a directory within a directory in one mkdir command
   */
  @Test
  public void testRelativePathMkdir() {
    String[] cmdArgs;
    cmdArgs = new String[3];
    cmdArgs[0] = "mkdir";
    cmdArgs[1] = "dir1";
    cmdArgs[2] = "dir1/dir2";
    mkdir.run(cmdArgs);
    try {
      dir2 = (Directory) (jShell.getCurrentDir()).getFileObject("dir1");
    } catch (Exception e) {
    }
    // True iif dir1 is made and dir2 in within dir1
    assertTrue((jShell.getCurrentDir()).hasFileObject("dir1")
        && dir2.hasFileObject("dir2"));
  }

  /**
   * adds a directory within a directory in one mkdir command using absolute
   * pathing
   */
  @Test
  public void testAbsolutePathMkdir() {
    String[] cmdArgs;
    cmdArgs = new String[3];
    cmdArgs[0] = "mkdir";
    cmdArgs[1] = "/dir1";
    cmdArgs[2] = "/dir1/dir2";
    mkdir.run(cmdArgs);
    try {
      dir2 = (Directory) (jShell.getCurrentDir()).getFileObject("dir1");
    } catch (Exception e) {
    }
    // True iif dir1 is made and dir2 in within dir1
    assertTrue((jShell.getCurrentDir()).hasFileObject("dir1")
        && dir2.hasFileObject("dir2"));
  }

  /**
   * adds a directory within a directory using two mkdir commands and using
   * relative pathing
   */
  @Test
  public void testTwoMkdirCalls() {
    String[] cmdArgs;
    cmdArgs = new String[2];
    cmdArgs[0] = "mkdir";
    cmdArgs[1] = "dir1";
    mkdir.run(cmdArgs);
    cmdArgs[1] = "dir1/dir2";
    mkdir.run(cmdArgs);
    try {
      dir2 = (Directory) (jShell.getCurrentDir()).getFileObject("dir1");
    } catch (Exception e) {
    }
    // True iff dir1 is made and dir2 in within dir1
    assertTrue((jShell.getCurrentDir()).hasFileObject("dir1")
        && dir2.hasFileObject("dir2"));
  }

  /**
   * adds one directory in another directory and try to insert a new directory
   * with a path that does not exist
   */
  @Test
  public void testRunAfterIllegalInput() {
    String[] cmdArgs;
    cmdArgs = new String[2];
    cmdArgs[0] = "mkdir";
    cmdArgs[1] = "dir1";
    mkdir.run(cmdArgs);
    cmdArgs[1] = "dir3/dir2";
    mkdir.run(cmdArgs);
    cmdArgs[1] = "dir1/dir2";
    mkdir.run(cmdArgs);
    try {
      dir2 = (Directory) (jShell.getCurrentDir()).getFileObject("dir1");
    } catch (Exception e) {
    }
    // True iff dir1 is made and dir2 in within dir1
    assertTrue((jShell.getCurrentDir()).hasFileObject("dir1")
        && dir2.hasFileObject("dir2"));
  }

  /**
   * add one directory and change current directory into that directory and add
   * another directory within the first directory using correct absolute path
   */
  @Test
  public void testAbsoluteAfterDirectoryChange() {
    String[] cmdArgs;
    cmdArgs = new String[2];
    cmdArgs[0] = "mkdir";
    cmdArgs[1] = "dir1";
    mkdir.run(cmdArgs);
    try {
      jShell.setCurrentDir(
          (Directory) (jShell.getCurrentDir()).getFileObject("dir1"));
    } catch (Exception e) {
    }
    cmdArgs[1] = "/dir1/dir2";
    mkdir.run(cmdArgs);
    // True iff dir1 is made and dir2 in within dir1
    assertTrue((jShell.getCurrentDir()).hasFileObject("dir2")
        && (jShell.getRoot()).hasFileObject("dir1"));
  }

  /**
   * add one directory and change current directory into that directory and add
   * another directory within the first directory using correct relative path
   */
  @Test
  public void testRelativeAfterDirectoryChange() {
    String[] cmdArgs;
    cmdArgs = new String[2];
    cmdArgs[0] = "mkdir";
    cmdArgs[1] = "dir1";
    mkdir.run(cmdArgs);
    try {
      jShell.setCurrentDir(
          (Directory) (jShell.getCurrentDir()).getFileObject("dir1"));
    } catch (Exception e) {
    }
    cmdArgs[1] = "dir1/dir2";
    mkdir.run(cmdArgs);
    // True iff dir1 is made and dir2 in is not within dir1
    assertFalse((jShell.getCurrentDir()).hasFileObject("dir2")
        && (jShell.getRoot()).hasFileObject("dir1"));
  }
}
