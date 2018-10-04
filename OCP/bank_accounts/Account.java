import java.math.BigDecimal;

public class Account {
	
	public String accountNumber;
	public BigDecimal balance;

	public Account(String number) {
		this.accountNumber = number;
		this.balance = BigDecimal.ZERO;
	}

	public Account(String number, BigDecimal balance) {
		this.accountNumber = number;
		this.balance = balance;
	}

	public void receive(BigDecimal amount) {
		this.balance = this.balance.add(amount);
	}

	public void transfer(Account receiver, BigDecimal amount) throws YouAreBrokeException {
		if (amount.compareTo(balance) >= 0)
			throw new YouAreBrokeException();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.balance = this.balance.subtract(amount);
		receiver.receive(amount);
	}

	public static class YouAreBrokeException extends RuntimeException {
		
	}

}
