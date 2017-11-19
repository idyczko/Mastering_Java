package factory;

public abstract class Computer {
  private int memoryBytes;
  private int ramGigaBytes;
  private float cpuGhz;

  public Computer(int memoryBytes, int ramGigaBytes, float cpuGhz) {
    this.memoryBytes = memoryBytes;
    this.ramGigaBytes = ramGigaBytes;
    this.cpuGhz = cpuGhz;
  }

  protected abstract String giveName();

  public String whatsYourSpecs(){
    return "I am a " + giveName() + " with " + memoryBytes + " bytes of memory, " + ramGigaBytes + "GB of RAM and " + cpuGhz + "GHz brain.";
  }

}
