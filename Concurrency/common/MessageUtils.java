package common;

import java.net.Socket;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MessageUtils {
  public static void sendMessage(Socket socket, String message) throws IOException{
    OutputStream stream = socket.getOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(stream);
    oos.writeUTF(message);
    oos.flush();
  }

  public static String getMessage(Socket socket) throws IOException{
    InputStream stream = socket.getInputStream();
    ObjectInputStream ois = new ObjectInputStream(stream);
    return ois.readUTF();
  }
}