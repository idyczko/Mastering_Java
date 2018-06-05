import java.net.*;
import java.io.*;

public class SocketServer implements Runnable{
  ServerSocket serverSocket;
  volatile boolean keepRunning = true;

  public SocketServer(int port, int timeoutMillis) throws IOException{
    serverSocket = new ServerSocket(port);
    serverSocket.setSoTimeout(timeoutMillis);
  }

  public static void main(String[] args) throws IOException{
    new SocketServer(8009, 10000).run();
  }

  public void run() {
      System.out.println("Server booting up...");

      while(keepRunning) {
        try {
          System.out.println("Waiting for client...");
          Socket socket = serverSocket.accept();
          System.out.println("Client got served :D...");
          process(socket);
        } catch (Exception e) {
          handle(e);
        }
      }
  }

  private void process(Socket socket) {
    if(socket == null)
      return;

    try {
      System.out.println("Server: Loading message...");
      String message = MessageUtils.getMessage(socket);
      System.out.println("Server: Message loaded: " + message);
      Thread.sleep(1000);
      System.out.println("Server: Sending response...");
      MessageUtils.sendMessage(socket, "Server: NO, U " + message);
      System.out.println("Server: Response sent.");
      closeIgnoringException(socket);
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void closeIgnoringException(Socket socket) {
    if (socket != null) {
      try {
        socket.close();
      } catch(IOException ignore) {
      }
    }
  }

  private void handle(Exception e) {
    if (!(e instanceof SocketTimeoutException)) {
      e.printStackTrace();
    }
  }

}