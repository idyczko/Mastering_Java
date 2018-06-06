package server;

import java.net.Socket;
import java.io.IOException;

public interface SocketConnectionManager {

  public Socket awaitRequest() throws IOException;
}