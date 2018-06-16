package server;

import java.io.IOException;

public interface ConnectionManager {

  public ClientConnection awaitRequest() throws IOException;
}