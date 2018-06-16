package common;

public class SimpleConsoleLogger extends Logger{

  public SimpleConsoleLogger(String caller) {
    super(caller);
  }

  public void log(String message) {
    System.out.println(super.caller + ": " + message);
  }
}