package server;

import java.net.Socket;

public class SocketClientConnection implements ClientConnection {
  public Socket socket;

  public SocketClientConnection(Socket socket) {
    this.socket = socket;
  }
}