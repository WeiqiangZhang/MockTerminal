package driver;

/**
 * The History class prints out the past recent inputs received from the user.
 * If 1 argument (int numHist) is supplied, it prints out the last numHist
 * amount of input, whether they be proper commands or invalid inputs.
 */

public class History extends Command {

  private String toPrint;

  public History(JShell jShell) {
    super(jShell, "(number)", -1);
  }

  /**
   * Prints the last n amount of input given by the user
   * 
   * @param cmdArgs an array of strings that represents a user command
   */
  @Override
  public void run(String[] cmdArgs) {
    if (checkArgCount(cmdArgs)) {
      // Find the correct number of queries
      int numHist;
      int queryNum;
      if (checkArgExist(cmdArgs)) {
        numHist =
            Math.min(Integer.parseInt(cmdArgs[1]), jShell.cmdHistory.size());
      } else {
        numHist = jShell.cmdHistory.size();
      }
      for (int i = jShell.cmdHistory.size() - numHist; i < jShell.cmdHistory
          .size(); i++) {
        queryNum = i + 1;
        String cmd = (String) jShell.cmdHistory.get(i);
        toPrint = (queryNum + ". " + cmd);
        System.out.println(toPrint);
      }
    }
  }

  /**
   * checkArgCount checks if there are more than 2 arguments, as any more than 1
   * argument results in a misuse of the command.
   * 
   * @param cmdArgs A string array that contains the command and arguments
   */
  @Override
  protected boolean checkArgCount(String[] cmdArgs) {
    if (cmdArgs.length > 2) {
      System.out.println(
          cmdArgs[0] + "Usage: " + cmdArgs[0] + ' ' + getProperUsage());
      return false;
    }

    return true;
  }

  /**
   * checkArgExist checks if there is an argument or not associated with the
   * instance of the command.
   * 
   * @param cmdArgs A string array that contains the command and arguments
   */
  public boolean checkArgExist(String[] cmdArgs) {
    if (cmdArgs.length == 2) {
      return true;
    }
    return false;
  }

  public String getToPrint() {
    return toPrint;
  }
}
