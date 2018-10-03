
public class Bee implements Runnable{
	private static int ordinal = 0;
	private int number;
	private long napTime;
	private StatefulCalculator calc;

	public Bee(StatefulCalculator calc, long napTime) {
		this.calc = calc;
		this.napTime = napTime;
		this.number = ordinal++;
	}

	@Override
	public void run() {
		while(true) {
			System.out.println("Thread " + number + " starts execution...");
			try {
				System.out.println("Calculation result: " + calc.calculate(number)  + " Should be: " + number * 45);
				Thread.sleep(napTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
