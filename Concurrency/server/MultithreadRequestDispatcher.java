package server;

public class MultithreadRequestDispatcher implements RequestDispatcher {

  public void schedule(RequestProcessor processor) {
    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        processor.process();
      }
    };
    runnable.run();
  }
}