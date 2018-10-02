public class Camel implements Rideable {
	int weight = 2;
	public String getGait() { return " mph, lope";}
	void go(int speed) {
		++speed;
		weight++;
		int walkrate = speed * weight;
		System.out.println(walkrate + getGait());
	}

	public static void main(String[] args) {
		new Camel().go(8);
	}
}
