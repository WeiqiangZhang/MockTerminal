package driver;

/**
 * The Echo program takes a String and either append it, replace the String in
 * the given file or print the String if no file or path is given. The give file
 * can be a file in the current directory, in a given absolute or relative path.
 */
public class Echo extends Command {

  // Constructor
  public Echo(JShell jShell) {
    super(jShell, "STRING [> OUTFILE] or STRING >> OUTFILE", -1);
  }

  /**
   * This method either prints the String, or check if the file exist in the
   * given directory and check whether to replace or append the String in the
   * file. Once the checking is done either appendEcho or replace Echo is
   * called.
   * 
   * @param cmdArgs a String array that contains the command and arguments.
   */
  @Override
  public void run(String cmdArgs[]) {
    if (checkArgCount(cmdArgs)) {
      String echoStr = cmdArgs[1];
      // Remove quotation marks
      echoStr = echoStr.replaceAll("\"", "");
      if (cmdArgs.length == 2) {
        // If it is direct print to console then just print it
        System.out.println(echoStr);
      } else { // Either append or overwrite to file i.e > or >>

        // Check second parameter for correct operator
        if (!(cmdArgs[2].equals(">") || cmdArgs[2].equals(">>"))) {
          System.out.println("Invalid arguments\nProper Usage: " + cmdArgs[0]
              + ' ' + getProperUsage());
          return;
        }

        // Parse path into directory and file
        String[] splitDir = cmdArgs[3].split("/");
        String fileName = splitDir[splitDir.length - 1];
        // Cut out the file name and only have the path
        String inPath = cmdArgs[3].substring(0,
            cmdArgs[3].length() - splitDir[splitDir.length - 1].length());
        Directory specified;
        try {
          // Bring specified to the path location
          specified = parsePath(inPath);
        } catch (Exception e) {
          System.out.println(e.getMessage());
          return;
        }

        if (cmdArgs[2].equals(">")) {
          replaceEcho(fileName, specified, echoStr);
        } else { // It is >>
          appendEcho(fileName, specified, echoStr);
        }
      }
    }
  }

  /**
   * This method checks if there is a file object and if that file object is an
   * instance of a file, if so the method appends the string to the contents of
   * the file.
   * 
   * @param fileName name of the designated file.
   * 
   * @param specified the specified directory for the file.
   * 
   * @param echostr the string that wants to be appended.
   */
  private void appendEcho(String fileName, Directory specified,
      String echoStr) {
    // If such file does not exist create a new file
    if (specified.hasFileObject(fileName)) {
      FileObject fo;
      try {
        // Get the FileObject of the file name
        fo = specified.getFileObject(fileName);
      } catch (Exception e) {
        System.out.println(e.getMessage());
        return;
      }
      if (fo instanceof File) {
        // replace the contents of the file with echoStr
        ((File) fo).append(echoStr);
      } else {
        // There is FileObject but it is an instance of a file
        System.out.println("The name specified exist but is not a file");
      }
    } else { // File does not exist
      createNewFile(fileName, specified, echoStr);
    }
  }

  /**
   * This method creates a new file named fileName in the specified directory
   * and add the new string to the contents of the file.
   * 
   * @param fileName name of the file.
   * 
   * @param specified the specified directory.
   * 
   * @param echoString that will be added to the content of the new file.
   */
  public void createNewFile(String fileName, Directory specified,
      String echoStr) {
    // Make new file object
    File newFile = new File(fileName);
    // set the contents with the new String
    newFile.setContents(echoStr);
    try {
      // Add the new file object in the given directory
      specified.addFileObject(newFile);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return;
    }
  }

  /**
   * This method checks if the total number of command and arguments are within
   * valid range, if not give an error message.
   * 
   * @param the string array containing the command and arguments.
   * 
   * @return return false if and only if it does not contain valid number of
   *         arguments else return true.
   */
  @Override
  protected boolean checkArgCount(String[] cmdArgs) {
    if (cmdArgs.length < 2 || cmdArgs.length > 4) {
      System.out.println(
          cmdArgs[0] + " usage: " + cmdArgs[0] + ' ' + getProperUsage());
      return false;
    }
    return true;
  }

  /**
   * This method checks if there is a file object and if that file object is an
   * instance of a file, if so the method replace the string to the contents of
   * the file.
   * 
   * @param fileName name of the new file.
   * 
   * @param specified the specified directory.
   * 
   * @param echoStr the string that wants to be replaced.
   */
  public void replaceEcho(String fileName, Directory specified,
      String echoStr) {
    // If such file does not exist create a new file
    if (specified.hasFileObject(fileName)) {
      FileObject fo;
      try {
        // Get the FileObject of the file name
        fo = specified.getFileObject(fileName);
      } catch (Exception e) {
        System.out.println(e.getMessage());
        return;
      }
      if (fo instanceof File) {
        // Set the contents of the file with echoStr
        ((File) fo).setContents(echoStr);
      } else {
        // There is FileObject but it is an instance of a file
        System.out.println("The name specified exist but is not a file");
      }
    } else { // File does not exist
      createNewFile(fileName, specified, echoStr);
    }
  }
}
