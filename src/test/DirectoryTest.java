package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import driver.Directory;
import driver.File;

public class DirectoryTest {

  private Directory dir1;
  private Directory dir2;
  private File file1;

  @Before
  public void setUp() throws Exception {
    dir1 = new Directory("mydir");
    dir2 = new Directory("dir2");
    file1 = new File("file1");
    dir1.addFileObject(file1);
    dir1.addFileObject(dir2);
  }

  /**
   * Check if hasFileObject is working
   */
  @Test
  public void testHasFileObject() {
    assertTrue(dir1.hasFileObject("file1"));
    assertTrue(dir1.hasFileObject("dir2"));
    assertFalse(dir1.hasFileObject("nohere"));
  }

  /**
   * Test to check that deleteFileObject actually removes the fileobject from
   * the directory
   */
  @Test
  public void testDelete() {
    assertTrue(dir1.hasFileObject("file1"));
    dir1.deleteFileObject("file1");
    assertFalse(dir1.hasFileObject("file1"));

    assertTrue(dir1.hasFileObject("dir2"));
    dir1.deleteFileObject("dir2");
    assertFalse(dir1.hasFileObject("dir2"));
  }

}
