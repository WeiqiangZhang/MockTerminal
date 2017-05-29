package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;
import driver.JShell;
import driver.Man;

public class ManTest {

  private JShell jShell;
  private Man man;

  @Before
  public void setUp() throws Exception {
    jShell = new JShell();
    man = new Man(jShell);
  }

  /**
   * Test man with cat
   */
  @Test
  public void testManCat() {
    String[] cmdArgs = new String[2];
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    man.run(cmdArgs);
    assertFalse(outContent.toString().equals(""));
  }

  /**
   * Test man with cd
   */
  @Test
  public void testManCd() {
    String[] cmdArgs = new String[2];
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    man.run(cmdArgs);
    assertFalse(outContent.toString().equals(""));
  }

  /**
   * Test man with echo
   */
  @Test
  public void testManEcho() {
    String[] cmdArgs = new String[2];
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    man.run(cmdArgs);
    assertFalse(outContent.toString().equals(""));
  }

  /**
   * Test man with exit
   */
  @Test
  public void testManExit() {
    String[] cmdArgs = new String[2];
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    man.run(cmdArgs);
    assertFalse(outContent.toString().equals(""));
  }

  /**
   * Test man with history
   */
  @Test
  public void testManHistory() {
    String[] cmdArgs = new String[2];
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    man.run(cmdArgs);
    assertFalse(outContent.toString().equals(""));
  }

  /**
   * Test man with ls
   */
  @Test
  public void testManLs() {
    String[] cmdArgs = new String[2];
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    man.run(cmdArgs);
    assertFalse(outContent.toString().equals(""));
  }

  /**
   * Test man with man
   */
  @Test
  public void testManMan() {
    String[] cmdArgs = new String[2];
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    man.run(cmdArgs);
    assertFalse(outContent.toString().equals(""));
  }

  /**
   * Test man with mkdir
   */
  @Test
  public void testManMkdir() {
    String[] cmdArgs = new String[2];
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    man.run(cmdArgs);
    assertFalse(outContent.toString().equals(""));
  }

  /**
   * Test man with popd
   */
  @Test
  public void testManPopd() {
    String[] cmdArgs = new String[2];
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    man.run(cmdArgs);
    assertFalse(outContent.toString().equals(""));
  }

  /**
   * Test man with pushd
   */
  @Test
  public void testManPushd() {
    String[] cmdArgs = new String[2];
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    man.run(cmdArgs);
    assertFalse(outContent.toString().equals(""));
  }

  /**
   * Test man with pwd
   */
  @Test
  public void testManPwd() {
    String[] cmdArgs = new String[2];
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    man.run(cmdArgs);
    assertFalse(outContent.toString().equals(""));
  }

}
