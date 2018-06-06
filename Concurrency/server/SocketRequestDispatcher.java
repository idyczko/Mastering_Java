package server;

public interface SocketRequestDispatcher {

  public void schedule(SocketRequestProcessor processor);
}