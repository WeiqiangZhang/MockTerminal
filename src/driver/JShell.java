// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name:
// UT Student #:
// Author:
//
// Student2:
// UTORID user_name: zhan2748
// UT Student #: 1002531842
// Author: Weiqiang Zhang
//
// Student3:
// UTORID user_name:kushtovm
// UT Student #:1002301797
// Author:Lors Kushtov
//
// Student4:
// UTORID user_name: linjin14
// UT Student #: 1001829275
// Author:Jinghao Lin
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package driver;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A program used to represent and simulate a shell program similar to that of
 * Unix. This also simulates a simple file system containing directories and
 * files.
 */
public class JShell {
  /**
   * The root directory.
   */
  private Directory root = new Directory("/");
  /**
   * The current working directory.
   */
  private Directory currentDir = getRoot();
  /**
   * The list containing all previous inputs.
   */
  protected ArrayList<String> cmdHistory = new ArrayList<String>();
  private Hashtable<String, Command> commands;
  private boolean exit;

  /**
   * The main program that simulates and runs the JShell.
   * 
   * @param args Command line input, currently unused.
   */
  public JShell() {
    exit = false;
    setRoot(new Directory("/"));
    setCurrentDir(getRoot());
    cmdHistory = new ArrayList<String>();
    commands = new Hashtable<String, Command>();
    initializeCommands();
  }

  public static void main(String[] args) {

    JShell jShell = new JShell();

    // Preparing for user input
    InputStreamReader inRead = new InputStreamReader(System.in);
    BufferedReader bRead = new BufferedReader(inRead);

    while (!jShell.getExit()) {

      System.out.print(jShell.getCurrentDir().getName() + "$ ");

      String command = null;
      try {
        command = bRead.readLine();
      } catch (IOException e) {
        System.out.println("Error reading input");
      }

      // Add the input, good or bad, into cmdHistory
      jShell.cmdHistory.add(command);

      // Split the input into an array for: [command, arg1, arg2,...]
      String[] cmdArgs = splitCommands(command);

      // Find and run the appropriate command
      Command wantedCommand = jShell.commands.get(cmdArgs[0]);
      if (wantedCommand != null) {
        wantedCommand.run(cmdArgs);
      } else { // wantedCommand == null meaning it is not in the hashTable
        System.out.println(cmdArgs[0] + " is not a valid command.");
      }
    }
  }

  public boolean getExit() {
    return this.exit;
  }

  /**
   * Splits a string command into separate tokens. Also splits it into command
   * and arguments.
   * 
   * @param command the string representation of the command.
   * 
   * @return the tokens associated with the command input.
   */
  public static String[] splitCommands(String command) {
    boolean quote_flag = false;
    ArrayList<String> retv = new ArrayList<String>();
    retv.add("");
    for (int i = 0; i < command.length(); i++) {
      if (command.charAt(i) == '\"') {
        quote_flag = !quote_flag;
        // Add the quotation to the argument
        retv.set(retv.size() - 1,
            (String) retv.get(retv.size() - 1) + command.charAt(i));
      } else if (quote_flag) {
        // Contents are currently in quotes, so put the current character
        // Into the current argument
        retv.set(retv.size() - 1,
            (String) retv.get(retv.size() - 1) + command.charAt(i));
      } else if (command.charAt(i) == ' ' || command.charAt(i) == '\t') {
        // It is a space character and it is not within open quotes
        // Space means it is the start of a new argument unless its blank
        if (!((String) retv.get(retv.size() - 1)).equals("")) {
          retv.add("");
        } else {
          // Do Nothing
        }
      } else {
        // Its just a regular character so add it to the arguments
        retv.set(retv.size() - 1,
            (String) retv.get(retv.size() - 1) + command.charAt(i));
      }
    }
    return retv.toArray(new String[0]);
  }

  /**
   * Creates a hashTable that links the user input to an implementation for the
   * requested functionality.
   * 
   * @return a hashTable with keys containing a String input command and an
   *         object responsible for the execution of the command as a value.
   */
  private void initializeCommands() {
    Stack<Directory> dirStack = new Stack<Directory>();
    commands.put("pushd", new PushD(this, dirStack));
    commands.put("popd", new PopD(this, dirStack));
    commands.put("man", new Man(this));
    commands.put("mkdir", new Mkdir(this));
    commands.put("ls", new Ls(this));
    commands.put("cd", new Cd(this));
    commands.put("pwd", new Pwd(this));
    commands.put("exit", new Exit(this));
    commands.put("echo", new Echo(this));
    commands.put("cat", new Cat(this));
    commands.put("history", new History(this));
  }

  public Directory getCurrentDir() {
    return currentDir;
  }

  public void setCurrentDir(Directory currentDir) {
    this.currentDir = currentDir;
  }

  public Directory getRoot() {
    return root;
  }

  public void setRoot(Directory root) {
    this.root = root;
  }

  public void setExit(boolean exit) {
    this.exit = exit;
  }

  public void setHistory(ArrayList<String> newHistory) {
    cmdHistory = newHistory;
  }

}
