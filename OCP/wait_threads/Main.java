import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		StatefulCalculator calc = new StatefulCalculator();
		Scanner sc = new Scanner(System.in);
		while(true) {
			long napTime = sc.nextLong();
			new Thread(new Bee(calc, napTime)).start();
		}
	}
}
