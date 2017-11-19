package factory;

public class Server extends Computer {
  public Server(int mem, int ram, float freq){
    super(mem, ram, freq);
  }

  protected String giveName() {
    return "Server";
  }
}
