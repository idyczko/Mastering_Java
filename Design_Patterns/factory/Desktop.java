package factory;

public class Desktop extends Computer {
  public Desktop(int mem, int ram, float freq){
    super(mem, ram, freq);
  }

  protected String giveName() {
    return "Desktop";
  }
}
