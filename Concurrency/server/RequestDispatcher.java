package server;

public interface RequestDispatcher {

  public void schedule(RequestProcessor processor);
}