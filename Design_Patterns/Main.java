import singleton.*;
import factory.*;
import factory.abstr.*;
import builder.*;
import proxy.*;
import proxy.bank.Bank;
import proxy.bank.impl.BankImpl;
import java.lang.reflect.Proxy;

public class Main {

  public static void main(String args[]) {
      System.out.println("We're doomed!");

      //Singleton - problems with inheritance, as the constructor has to be private.
      //It technically isn't impossible to create inheritance, but it would involve
      //inner classes.
      LazySingleton lazySingleton = LazySingleton.getInstance();
      EagerSingleton eagerSingleton = EagerSingleton.getInstance();

      //Factory
      Computer desktop = ComputerFactory.gimmeComputer("desktop", 100000, 16, 5.0f);
      Computer server = ComputerFactory.gimmeComputer("server", 10000000, 128, 5.0f);
      System.out.println(desktop.whatsYourSpecs());
      System.out.println(server.whatsYourSpecs());

      //Abstract factory - if all the parameters are needed. It helps us decouple
      //factory implementation (how the object is really created) from the context
      //that uses the object retrieved from the factory.
      DesktopFactory desktopFactory = new DesktopFactory(100000, 16, 5.0f);
      ServerFactory serverFactory = new ServerFactory(100000000, 128, 5.0f);
      Computer factorizedDesktop = ComputerFactoryAbstract.getComputer(desktopFactory);
      Computer factorizedServer = ComputerFactoryAbstract.getComputer(serverFactory);
      System.out.println(factorizedDesktop.whatsYourSpecs());
      System.out.println(factorizedServer.whatsYourSpecs());

      //Builder pattern - if we have some non-mandatory parameters.
      BuiltComputer.ComputerBuilder cb = new BuiltComputer.ComputerBuilder(1024, 8, 4.0f);
      BuiltComputer builtComputer = cb.setExtraGpu(true).setSecondaryDrive(false).build();

      //Proxy pattern
      //Note how we encapsulated a simply POJO class (Bank) in two additional layers of abstraction. Those layers of abstraction
      // are obtained thanks to generated at runtime proxy classes - Java somehow manages to locate those proxies in Bank type
      //hierarchy (reflection alert). It is a retarded AOP design. The worst thing is BankImpl is final - yet somehow JVM manages
      //to rape this hermetization.
      Bank bank = (Bank) Proxy.newProxyInstance(Bank.class.getClassLoader(), new Class[] {Bank.class}, new SecurityBankProxy(
        (Bank) Proxy.newProxyInstance(Bank.class.getClassLoader(), new Class[] {Bank.class}, new LoggingBankProxy(new BankImpl(25)))));
      System.out.println(bank.getAccount());
		  bank.setAccount(12);
		  System.out.println(bank.getAccount());
  }
}
