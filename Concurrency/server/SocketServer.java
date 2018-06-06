package server;

import common.Logger;
import common.SimpleConsoleLogger;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.io.IOException;

public class SocketServer implements Runnable{
  String name;
  SocketConnectionManager connectionManager;
  SocketRequestDispatcher requestDispatcher;
  Logger logger;

  public SocketServer(String name, SocketConnectionManager connectionManager, SocketRequestDispatcher requestDispatcher, Logger logger) {
    this.name = name;
    this.connectionManager = connectionManager;
    this.requestDispatcher = requestDispatcher;
    this.logger = logger;
  }

  public static void main(String[] args) throws IOException{
    new SocketServer(args[0], new SimpleSocketConnectionManager(Integer.valueOf(args[1]), 10000),
      new SingleThreadDispatcher(), new SimpleConsoleLogger(args[0])).run();
  }

  public void run() {
      logger.log("Server booting up...");

      while(true) {
        try {
          logger.log("Waiting for client...");
          Socket socket = connectionManager.awaitRequest();
          logger.log("Client request received...");
          SocketRequestProcessor processor = new MessageWritingRequestProcessor(socket);
          requestDispatcher.schedule(processor);
          logger.log("Client got served :D...");
        } catch (Exception e) {
          logger.log("This should not happen... :D");
          handle(e);
        }
      }
  }

  private void handle(Exception e) {
    if (!(e instanceof SocketTimeoutException)) {
      e.printStackTrace();
    }
  }

}