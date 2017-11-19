package factory;

import factory.abstr.*;

public class ComputerFactoryAbstract {

  public static Computer getComputer(AbstractComputerFactory factory) {
    return factory.createComputer();
  }
}
