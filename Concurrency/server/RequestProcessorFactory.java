package server;

public interface RequestProcessorFactory {
  public RequestProcessor produce(ClientConnection clientConnection);
}