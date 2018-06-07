package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class SimpleSocketConnectionManager implements ConnectionManager {
  ServerSocket serverSocket;

  public SimpleSocketConnectionManager(int port, int timeoutMillis) throws IOException{
    this.serverSocket = new ServerSocket(port);
    this.serverSocket.setSoTimeout(timeoutMillis);
  }

  @Override
  public ClientConnection awaitRequest() throws IOException{
      return new SocketClientConnection(this.serverSocket.accept());
  }
}