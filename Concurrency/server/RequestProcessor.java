package server;

public abstract class RequestProcessor {
  protected ClientConnection clientConnection;

  protected RequestProcessor(ClientConnection clientConnection) {
    this.clientConnection = clientConnection;
  }

  public abstract void process();
}