package client;

import common.MessageUtils;
import java.net.Socket;

public class Client implements Runnable{
  String name;
  int waitMillis;

  public Client(String name, int waitMillis) {
    this.name = name;
    this.waitMillis = waitMillis;
  }

  public static void main(String[] args){
    new Client(args[0], Integer.valueOf(args[1])).run();
  }

  public void run() {
    while(true) {
      try {
        Socket socket = new Socket("127.0.0.1", 8009);
        MessageUtils.sendMessage(socket, "Gruezzi from " + name);
        //System.out.println(name + " received answer: " + MessageUtils.getMessage(socket));
        MessageUtils.getMessage(socket);
        socket.close();
        Thread.sleep(waitMillis);
      } catch (Exception e) {
        //e.printStackTrace();
      }
    }
  }

}