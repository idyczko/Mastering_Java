package server;

public class SingleThreadDispatcher implements RequestDispatcher {

  public void schedule(RequestProcessor processor) {
    processor.process();
  }
}