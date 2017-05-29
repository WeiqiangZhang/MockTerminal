package test;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import driver.JShell;

public class JShellTest {

  /**
   * test case for single and only one command
   */
  @Test
  public void testSplitCommand1() {
    String command = "ls";
    String[] expectedOutput = {"ls"};
    String[] output = JShell.splitCommands(command);
    assertTrue(Arrays.equals(output, expectedOutput));
  }

  /**
   * test case for one command and one argument
   */
  @Test
  public void testSplitCommand2() {
    String command = "cat dog";
    String[] expectedOutput = {"cat", "dog"};
    String[] output = JShell.splitCommands(command);
    assertTrue(Arrays.equals(output, expectedOutput));
  }

  /**
   * test case for one command and quotation for arguments
   */
  @Test
  public void testSplitCommand3() {
    String command = "man \"really awesome hat\"";
    String[] expectedOutput = {"man", "\"really awesome hat\""};
    String[] output = JShell.splitCommands(command);
    assertTrue(Arrays.equals(output, expectedOutput));
  }

  /**
   * test case for multiple spaces
   */
  @Test
  public void testSplitCommand4() {
    String command = "read        memory           and stuff";
    String[] expectedOutput = {"read", "memory", "and", "stuff"};
    String[] output = JShell.splitCommands(command);
    assertTrue(Arrays.equals(output, expectedOutput));
  }

  /**
   * test case for tab characters
   */
  @Test
  public void testSplitCommand5() {
    String command = "cd\there";
    String[] expectedOutput = {"cd", "here"};
    String[] output = JShell.splitCommands(command);
    assertTrue(Arrays.equals(output, expectedOutput));
  }

  /**
   * test case for tabs and spaces
   */
  @Test
  public void testSplitCommand6() {
    String command = "pushd   \tdirectory";
    String[] expectedOutput = {"pushd", "directory"};
    String[] output = JShell.splitCommands(command);
    assertTrue(Arrays.equals(output, expectedOutput));
  }
}
