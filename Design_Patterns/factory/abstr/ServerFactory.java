package factory.abstr;

import factory.*;

public class ServerFactory implements AbstractComputerFactory {
  int mem;
  int ram;
  float freq;

  public ServerFactory(int mem, int ram, float freq) {
    this.mem = mem;
    this.ram = ram;
    this.freq = freq;
  }

  public Computer createComputer() {
    return new Server(mem, ram, freq);
  }

}
