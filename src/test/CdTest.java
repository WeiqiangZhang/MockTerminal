package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import driver.Directory;
import driver.JShell;
import driver.Cd;

public class CdTest {
  private Directory folderOne;
  private Directory folderTwo;
  private Directory subOne;
  private Directory subTwo;
  private Directory subThree;
  private JShell jShell;
  private String[] cmdArgs;
  private Cd cd;

  @Before
  /**
   * setting up all of the directories in order to test the different cases of
   * changing directories
   */
  public void setUp() throws Exception {
    jShell = new JShell();
    cd = new Cd(jShell);
    jShell.setCurrentDir(jShell.getRoot());
    folderOne = new Directory("folderOne");
    folderTwo = new Directory("folderTwo");
    subOne = new Directory("subOne");
    subTwo = new Directory("subTwo");
    subThree = new Directory("subThree");

    folderOne.addFileObject(subOne);
    folderOne.addFileObject(subTwo);
    folderTwo.addFileObject(subThree);

    jShell.getRoot().addFileObject(folderOne);
    jShell.getRoot().addFileObject(folderTwo);
  }

  @Test
  /**
   * testing changing to a child directory of the current one
   */
  public void testRelative() {
    // checking changing into a child directory
    cmdArgs = new String[2];
    cmdArgs[0] = "cd";
    cmdArgs[1] = "folderOne";
    cd.run(cmdArgs);
    assertEquals(jShell.getCurrentDir(), folderOne);
    // checking changing into a child directory with forward slash at end
    // should not affect the Cd
    cmdArgs[0] = "cd";
    cmdArgs[1] = "folderOne/";
    cd.run(cmdArgs);
    assertEquals(jShell.getCurrentDir(), folderOne);

  }

  @Test
  /**
   * testing changing directory to root
   */
  public void testToRoot() {
    // checking changing to root when currentDirectory is already root
    // should do nothing
    cmdArgs = new String[2];
    cmdArgs[0] = "cd";
    cmdArgs[1] = "/";
    cd.run(cmdArgs);
    assertEquals(jShell.getCurrentDir(), jShell.getRoot());
    // checking changing to root when currentDirectory is not root
    jShell.setCurrentDir(folderOne);
    cmdArgs[0] = "cd";
    cmdArgs[1] = "/";
    cd.run(cmdArgs);
    assertEquals(jShell.getCurrentDir(), jShell.getRoot());

  }

  @Test
  /**
   * Tests changing directory to the parent directory
   */
  public void testToParentDirectory() {
    // testing the ".." argument to go up one directory
    jShell.setCurrentDir(subTwo);
    cmdArgs = new String[2];
    cmdArgs[0] = "cd";
    cmdArgs[1] = "..";
    cd.run(cmdArgs);
    assertEquals(jShell.getCurrentDir(), folderOne);
  }

  @Test
  /**
   * changes to a directory within a directory using relative pathing
   */
  public void testRelativeNested() {
    // change to sub-directory of child directory
    jShell.setCurrentDir(jShell.getRoot());
    cmdArgs = new String[2];
    cmdArgs[0] = "cd";
    cmdArgs[1] = "folderOne/subOne";
    cd.run(cmdArgs);
    assertEquals(jShell.getCurrentDir(), subOne);
    // change to sub-directory of sister directory
    jShell.setCurrentDir(folderOne);
    cmdArgs[0] = "cd";
    cmdArgs[1] = "../folderTwo/subThree";
    cd.run(cmdArgs);
    assertEquals(jShell.getCurrentDir(), subThree);


  }

  @Test
  /**
   * changes to a directory using absolute pathing
   */
  public void testAbsolute() {
    // change to directory using absolute pathing. (starting from root and then
    // navigating)
    jShell.setCurrentDir(subOne);
    cmdArgs = new String[2];
    cmdArgs[0] = "cd";
    cmdArgs[1] = "/folderTwo/subThree";
    cd.run(cmdArgs);
    assertEquals(jShell.getCurrentDir(), subThree);
    // changing to directory using absolute pathing with a '..' in it
    jShell.setCurrentDir(folderOne);
    cmdArgs[0] = "cd";
    cmdArgs[1] = "/folderOne/../folderTwo/subThree";
    cd.run(cmdArgs);
    assertEquals(jShell.getCurrentDir(), subThree);
  }

}
