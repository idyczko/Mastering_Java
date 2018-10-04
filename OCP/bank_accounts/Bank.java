import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;

public final class Bank {

	private static Bank instance;

	public List<Account> accounts = new ArrayList<>();

	public List<Manager> managers = new ArrayList<>();
	
	private Bank() {}

	public static Bank getInstance() {
		if (instance == null)
			instance = new Bank();

		return instance;
	}

	public Account getRandomAccount() {
		return this.accounts.get(new BigDecimal(Math.random()).multiply(new BigDecimal(this.accounts.size())).intValue());
	}

	public void printState() {
		accounts.forEach(item -> System.out.println("Balance of account " + item.accountNumber + ": " + item.balance));
		managers.forEach(m -> System.out.println(m.status));
	}
}
