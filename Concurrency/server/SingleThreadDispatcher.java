package server;

public class SingleThreadDispatcher implements SocketRequestDispatcher {

  public void schedule(SocketRequestProcessor processor) {
    processor.process();
  }
}