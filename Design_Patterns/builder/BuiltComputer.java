package builder;

public class BuiltComputer  {
  private int memory;
  private int ram;
  private float cpuFreq;

  private boolean extraGPU;
  private boolean secondaryDrive;

  private BuiltComputer(ComputerBuilder cb) {
    this.memory = cb.memory;
    this.ram = cb.ram;
    this.cpuFreq = cb.cpuFreq;

    this.extraGPU = cb.extraGPU;
    this.secondaryDrive = cb.secondaryDrive;
  }

  private boolean someNonStaticMethod(){
    return true;
  }

  public static class ComputerBuilder {
    private int memory;
    private int ram;
    private float cpuFreq;

    private boolean extraGPU;
    private boolean secondaryDrive;

    public ComputerBuilder(int mem, int ram, float freq){
      this.memory = mem;
      this.ram = ram;
      this.cpuFreq = freq;
    }

    public ComputerBuilder setExtraGpu(boolean gpu){
      this.extraGPU = gpu;
      return this;
    }

    public ComputerBuilder setSecondaryDrive(boolean sd) {
      this.secondaryDrive = sd;
      return this;
    }

    public BuiltComputer build() {
      //someNonStaticMethod(); //Will throw non-static method cannot be called from static context
      return new BuiltComputer(this);
    }
  }

}
