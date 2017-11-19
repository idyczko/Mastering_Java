import singleton.*;
import factory.*;
import factory.abstr.*;
import builder.*;

public class Main {

  public static void main(String args[]) {
      System.out.println("We're doomed!");

      //Singleton
      LazySingleton lazySingleton = LazySingleton.getInstance();
      EagerSingleton eagerSingleton = EagerSingleton.getInstance();

      //Factory
      Computer desktop = ComputerFactory.gimmeComputer("desktop", 100000, 16, 5.0f);
      Computer server = ComputerFactory.gimmeComputer("server", 10000000, 128, 5.0f);
      System.out.println(desktop.whatsYourSpecs());
      System.out.println(server.whatsYourSpecs());

      //Abstract factory
      DesktopFactory desktopFactory = new DesktopFactory(100000, 16, 5.0f);
      ServerFactory serverFactory = new ServerFactory(100000000, 128, 5.0f);
      Computer factorizedDesktop = ComputerFactoryAbstract.getComputer(desktopFactory);
      Computer factorizedServer = ComputerFactoryAbstract.getComputer(serverFactory);
      System.out.println(factorizedDesktop.whatsYourSpecs());
      System.out.println(factorizedServer.whatsYourSpecs());

      //Builder pattern
      BuiltComputer.ComputerBuilder cb = new BuiltComputer.ComputerBuilder(1024, 8, 4.0f);
      BuiltComputer builtComputer = cb.setExtraGpu(true).setSecondaryDrive(false).build();

  }
}
