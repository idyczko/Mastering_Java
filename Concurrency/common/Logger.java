package common;

public abstract class Logger {
  String caller;

  protected Logger(String caller) {
    this.caller = caller;
  }

  public abstract void log(String message);
}