package test;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import driver.Directory;
import driver.JShell;
import driver.PopD;

public class PopDTest {

  private JShell jShell;
  private PopD popD;
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
    popD = new PopD(jShell, dirStack);
  }

  @Test
  /**
   * Test to pop one directory out of the stack
   */
  public void testpopD1() {
    String[] cmdArgs = new String[1];
    dirStack.clear();
    dirStack.push(dir1);
    jShell.setCurrentDir(jShell.getRoot());
    cmdArgs[0] = "popD";;
    popD.run(cmdArgs);
    assertTrue((jShell.getCurrentDir() == dir1));
  }

  @Test
  /**
   * Test to check directory deeper into the filesystem. I.E not in immediate
   * directory
   */
  public void testpopD2() {
    String[] cmdArgs = new String[1];
    dirStack.clear();
    dirStack.push(dir1a);
    jShell.setCurrentDir(jShell.getRoot());
    cmdArgs[0] = "popD";
    popD.run(cmdArgs);
    assertTrue((jShell.getCurrentDir() == dir1a));
  }

  @Test
  /**
   * Test to check for multiple pops
   */
  public void testpopD3() {
    String[] cmdArgs = new String[1];
    dirStack.clear();
    dirStack.push(dir2);
    dirStack.push(dir1a);
    jShell.setCurrentDir(jShell.getRoot());
    cmdArgs[0] = "popD";
    popD.run(cmdArgs);
    assertTrue((jShell.getCurrentDir() == dir1a));
    cmdArgs[0] = "popD";
    popD.run(cmdArgs);
    assertTrue((jShell.getCurrentDir() == dir2));
  }

  @Test
  /**
   * Test to make sure directory doesn't move for invalid input
   */
  public void testpopD4() {
    String[] cmdArgs = new String[1];
    jShell.setCurrentDir(jShell.getRoot());
    cmdArgs[0] = "popD dir1";
    popD.run(cmdArgs);
    assertTrue((jShell.getCurrentDir() == jShell.getRoot()));
  }

}
