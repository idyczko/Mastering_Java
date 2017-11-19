package factory;

public class ComputerFactory {

  public static Computer gimmeComputer(String name, int mem, int ram, float freq) {
    if("Desktop".equalsIgnoreCase(name)) {
      return new Desktop(mem, ram, freq);
    } else if ("Server".equalsIgnoreCase(name)) {
      return new Server(mem, ram, freq);
    }
    return null;
  }
}
