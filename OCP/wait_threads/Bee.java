
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
				synchronized(calc) {
					System.out.println("Thread " + number  + " obtained and locked necessary resource...");
					if (number == 7)
						calc.wait();
					System.out.println("Thread " + number + " calculation result: " + calc.calculate(number)  + " Should be: " + number * 45);
					System.out.println("Thread " + number + " releasing resoruces... Entering cooling off mode...");
					if (number == 1 && Math.random() >= 0.5) {
						System.out.println("Thread " + number +  " notifies all!");
						calc.notifyAll();
					}
				}
				Thread.sleep(napTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
