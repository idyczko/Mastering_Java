package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import proxy.bank.Bank;

public class LoggingBankProxy implements InvocationHandler{
  private Bank bank;

  public LoggingBankProxy(Bank bank) {
    this.bank = bank;
  }

  @Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		switch(method.getName()) {
		case "getAccount":
			System.out.println("Logging: Accessing retrieving account...");
      Integer result = bank.getAccount();
      System.out.println("Logging: Exiting retrieving account...");
      return result;
		case "setAccount":
			System.out.println("Logging: Accessing retrieving account...");
			bank.setAccount((Integer) args[0]);
      System.out.println("Logging: Exiting retrieving account...");
			return null;
		default:
			break;
		}
		return null;
	}
}