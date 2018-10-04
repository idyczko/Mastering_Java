import java.util.Scanner;
import java.math.BigDecimal;

public class Main {

	public static void main(String[] args) {
		Scanner sc=  new Scanner(System.in);
		Bank bank = Bank.getInstance();
		bank.accounts.add(new Account("NR1", new BigDecimal(100)));
		bank.accounts.add(new Account("NR2", new BigDecimal(100)));
		bank.accounts.add(new Account("NR3", new BigDecimal(100)));
		bank.accounts.add(new Account("NR4", new BigDecimal(100)));
		bank.accounts.add(new Account("NR5", new BigDecimal(100)));
		bank.printState();
		String line;
		while(true) {
			line = sc.nextLine();
			if (line.equals("status")) {
				bank.printState();
				continue;
			}
			Manager m = new Manager(bank, Long.valueOf(line));
			bank.managers.add(m);
			Thread t = new Thread(m);
			t.start();
		}
	}
}
