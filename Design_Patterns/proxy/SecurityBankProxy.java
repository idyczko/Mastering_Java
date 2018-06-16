package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import proxy.bank.Bank;

public class SecurityBankProxy implements InvocationHandler{
  private Bank bank;

  public SecurityBankProxy(Bank bank) {
    this.bank = bank;
  }

  @Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		switch(method.getName()) {
		case "getAccount":
			System.out.println("Authorizing: Retrieving account...");
			return bank.getAccount();
		case "setAccount":
			System.out.println("Authorizing: Setting account...");
			bank.setAccount((Integer) args[0]);
			return null;
		default:
			break;
		}
		return null;
	}
}