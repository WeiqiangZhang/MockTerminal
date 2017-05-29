package test;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import driver.Directory;
import driver.JShell;
import driver.PushD;

public class PushDTest {

  private JShell jShell;
  private PushD pushD;
  private Directory dir1;
  private Directory dir2;
  private Directory dir3;
  private Directory dir1a;
  private Directory dir1b;
  private Directory dir1c;
  private Stack<Directory> dirStack;

  @Before
  public void setUp() throws Exception {
    jShell = new JShell();
    dir1 = new Directory("dir1");
    dir1a = new Directory("a");
    dir1b = new Directory("b");
    dir1c = new Directory("c");
    dir1.addFileObject(dir1a);
    dir1.addFileObject(dir1b);
    dir1.addFileObject(dir1c);
    dir2 = new Directory("dir2");
    dir3 = new Directory("dir3");
    jShell.getRoot().addFileObject(dir1);
    jShell.getRoot().addFileObject(dir2);
    jShell.getRoot().addFileObject(dir3);
    dirStack = new Stack<Directory>();
    pushD = new PushD(jShell, dirStack);
  }

  /**
   * Test that pushD works for relative path
   */
  @Test
  public void testPushD1() {
    String[] cmdArgs = new String[2];
    jShell.setCurrentDir(jShell.getRoot());
    cmdArgs[0] = "pushd";
    cmdArgs[1] = "dir1";
    pushD.run(cmdArgs);
    assertTrue((jShell.getCurrentDir() == dir1));
    assertTrue(dirStack.pop() == jShell.getRoot());
  }

  /**
   * Test that pushD works for relative path that navigates into something
   */
  @Test
  public void testPushD2() {
    String[] cmdArgs = new String[2];
    jShell.setCurrentDir(jShell.getRoot());
    cmdArgs[0] = "pushd";
    cmdArgs[1] = "dir1/a";
    pushD.run(cmdArgs);
    assertTrue((jShell.getCurrentDir() == dir1a));
    assertTrue(dirStack.pop() == jShell.getRoot());
  }

  /**
   * Test for adding multiple directories into the stack using relative
   * directories
   */
  @Test
  public void testPushD3() {
    String[] cmdArgs = new String[2];
    jShell.setCurrentDir(jShell.getRoot());
    cmdArgs[0] = "pushd";
    cmdArgs[1] = "dir1/a";
    pushD.run(cmdArgs);
    cmdArgs[0] = "pushd";
    cmdArgs[1] = "..";
    pushD.run(cmdArgs);
    assertTrue((jShell.getCurrentDir() == dir1));
    assertTrue(dirStack.pop() == dir1a);
    assertTrue(dirStack.pop() == jShell.getRoot());
  }

  /**
   * Test for adding multiple directories but using full path
   */
  @Test
  public void testPushD4() {
    String[] cmdArgs = new String[2];
    jShell.setCurrentDir(jShell.getRoot());
    cmdArgs[0] = "pushd";
    cmdArgs[1] = "/dir2/";
    pushD.run(cmdArgs);
    cmdArgs[0] = "pushd";
    cmdArgs[1] = "/dir1/b";
    pushD.run(cmdArgs);
    assertTrue((jShell.getCurrentDir() == dir1b));
    assertTrue(dirStack.pop() == dir2);
    assertTrue(dirStack.pop() == jShell.getRoot());
  }

}
