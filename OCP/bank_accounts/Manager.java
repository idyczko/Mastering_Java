import java.math.BigDecimal;

public class Manager implements Runnable {
	
	private Bank bank;
	private static int ordinal = 0;
	private int number;
	private long nap;
	public String status = "EDEN";

	public Manager(Bank bank, long nap) {
		this.bank = bank;
		this.nap = nap;
		this.number += ordinal++;
		this.status = "READY";
	}
	
	@Override
	public void run() {
		this.status = "IN OPERATION";
		while(true) {
			System.out.println("Manager nr " +  number + " starts processing...");
			Account transmitter = bank.getRandomAccount();
			Account receiver = bank.getRandomAccount();
			
				//log("Locked transmitter " + transmitter.accountNumber;)
			while(transmitter.equals(receiver))
				receiver = bank.getRandomAccount();
			Account lower = transmitter.accountNumber.compareTo(receiver.accountNumber) < 0 ? transmitter : receiver;
			Account higher = transmitter.accountNumber.compareTo(receiver.accountNumber) < 0 ? receiver : transmitter;
			synchronized(lower) {
				//status = "LOCKED TRANSMITTER " + transmitter.accountNumber + " REQUESTING LOCK ON RECEIVER " + receiver.accountNumber;
				synchronized(higher) {
					//status = "LOCKED RECEIVER " + transmitter.accountNumber;
					BigDecimal amount = new BigDecimal(transmitter.balance.multiply(new BigDecimal(Math.random())).intValue());
					//log("Locked receiver " + receiver.accountNumber);
					log("Starting transfer " + transmitter.accountNumber + " === " + amount + " ===> " + receiver.accountNumber);
					try {
						transmitter.transfer(receiver, amount);
					} catch (Account.YouAreBrokeException yabe) {
						yabe.printStackTrace();
					}
					log("Transfer finished");
					try {
						Thread.sleep(nap);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void log(String message) {
		System.out.println("Manager nr " + number + ": " + message);
	}

}
