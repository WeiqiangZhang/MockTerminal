package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import driver.JShell;
import driver.History;

public class HistoryTest {
  
  private JShell jShell;
  private History history;
  
  @Before
  public void setUp() throws Exception {
    
    jShell = new JShell();
    String[] historyArray = {"1", "2", "3", "4", "5"};
    jShell.setHistory(new ArrayList<String>(Arrays.asList(historyArray)));
    history = new History(jShell);
  }
  
  /**
   * Test if History works with no arguments
   */
  @Test
  public void testHistory1() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    String[] cmdArgs;
    cmdArgs = new String[1];
    cmdArgs[0] = "history";
    String expectedOutput = "1. 1" + System.lineSeparator() +
          "2. 2" + System.lineSeparator() + "3. 3" + System.lineSeparator() +
          "4. 4" + System.lineSeparator() + "5. 5" + System.lineSeparator();
    history.run(cmdArgs);
    assertEquals(expectedOutput, outContent.toString());
  }
  
  /**
   * Test if History works with a single argument
   */
  @Test
  public void testHistory2() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    String[] cmdArgs;
    cmdArgs = new String[2];
    cmdArgs[0] = "history";
    cmdArgs[1] = "3";
    String expectedOutput = "3. 3" + System.lineSeparator() +
        "4. 4" + System.lineSeparator() + "5. 5" + System.lineSeparator();
    history.run(cmdArgs);
    assertEquals(expectedOutput, outContent.toString());
  }
  
  /**
   * Test if History works with a zero as argument
   */
  @Test
  public void testHistory3() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    String[] cmdArgs;
    cmdArgs = new String[2];
    cmdArgs[0] = "history";
    cmdArgs[1] = "0";
    String expectedOutput = "";
    history.run(cmdArgs);
    assertEquals(expectedOutput, outContent.toString());
  }
  
  /**
   * Test if History works with a negative as argument
   */
  @Test
  public void testHistory4() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    String[] cmdArgs;
    cmdArgs = new String[2];
    cmdArgs[0] = "history";
    cmdArgs[1] = "-1";
    String expectedOutput = "";
    history.run(cmdArgs);
    assertEquals(expectedOutput, outContent.toString());
  }

}
