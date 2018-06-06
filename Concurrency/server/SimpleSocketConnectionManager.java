package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class SimpleSocketConnectionManager implements SocketConnectionManager {
  ServerSocket serverSocket;

  public SimpleSocketConnectionManager(int port, int timeoutMillis) throws IOException{
    this.serverSocket = new ServerSocket(port);
    this.serverSocket.setSoTimeout(timeoutMillis);
  }

  public Socket awaitRequest() throws IOException{
    return this.serverSocket.accept();
  }
}