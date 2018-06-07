package server;

import common.Logger;
import common.SimpleConsoleLogger;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.io.IOException;

public class Server implements Runnable{
  Integer counter = 0;
  String name;
  ConnectionManager connectionManager;
  RequestDispatcher requestDispatcher;
  RequestProcessorFactory processorFactory;
  Logger logger;

  public Server(String name, ConnectionManager connectionManager,
    RequestProcessorFactory processorFactory, RequestDispatcher requestDispatcher,
    Logger logger) {
    this.name = name;
    this.connectionManager = connectionManager;
    this.processorFactory = processorFactory;
    this.requestDispatcher = requestDispatcher;
    this.logger = logger;
  }

  public static void main(String[] args) throws IOException{
    new Server(args[0], new SimpleSocketConnectionManager(Integer.valueOf(args[1]), 10000),
      new MessageWritingRequestProcessor.MessageWritingRequestProcessorFactory(),
      new MultithreadRequestDispatcher(), new SimpleConsoleLogger(args[0])).run();
  }

  public void run() {
      logger.log("Server booting up...");

      while(true) {
        try {
          logger.log("Waiting for client...");
          ClientConnection connection = connectionManager.awaitRequest();
          System.out.println(++counter);
          logger.log("Client request received...");
          RequestProcessor processor = processorFactory.produce(connection);
          requestDispatcher.schedule(processor);
          logger.log("Client got served :D...");
        } catch (Exception e) {
          handle(e);
        }
      }
  }

  private void handle(Exception e) {
    if (!(e instanceof SocketTimeoutException)) {
      logger.log("This should not happen... :D");
      e.printStackTrace();
    }
  }

}