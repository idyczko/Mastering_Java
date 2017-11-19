package factory.abstr;

import factory.*;

public class DesktopFactory implements AbstractComputerFactory {
  int mem;
  int ram;
  float freq;

  public DesktopFactory(int mem, int ram, float freq) {
    this.mem = mem;
    this.ram = ram;
    this.freq = freq;
  }

  public Computer createComputer() {
    return new Desktop(mem, ram, freq);
  }

}
