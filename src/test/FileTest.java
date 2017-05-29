package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import driver.File;

public class FileTest {
  private File file1;
  private File file2;

  @Before
  public void setUp() throws Exception {
    file1 = new File("myFile");
    file2 = new File("myFile2");
    file1.setContents("Something inside here");
    file2.setContents("This is some good stuff");
  }

  /**
   * Test the clear method
   */
  @Test
  public void testClear() {
    file1.clear();
    assertTrue(file1.getContents().equals(""));
  }

  /**
   * Test the append method in file
   */
  @Test
  public void testAppend() {
    file2.append("Hello");
    assertTrue(file2.getContents().equals("This is some good stuff\nHello"));
  }

}
