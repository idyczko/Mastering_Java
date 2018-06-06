package server;

import common.MessageUtils;
import java.net.Socket;
import java.io.IOException;

public class MessageWritingRequestProcessor implements SocketRequestProcessor {
  Socket socket;

  public MessageWritingRequestProcessor(Socket socket) {
    this.socket = socket;
  }

  public void process() {
    if(this.socket == null)
      return;

    try {
      String message = MessageUtils.getMessage(socket);
      Thread.sleep(1000);
      MessageUtils.sendMessage(socket, "Server: NO, U " + message);
      closeIgnoringException(socket);
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void closeIgnoringException(Socket socket) {
    if (this.socket != null) {
      try {
        socket.close();
      } catch(IOException ignore) {
      }
    }
  }
}