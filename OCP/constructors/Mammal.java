
public class Mammal {
	
	Mammal(int i) {}

	Number getWeight() {
		Number n = new Integer(10);
		return n;
	}

	public static void main(String[] args) {
		Elephant e = new Elephant(10);
	}
}

class Elephant extends Mammal {

	@Override
	Integer getWeight() {
		return (Integer) super.getWeight();
	}

	Elephant() {
		this(1);
	}

	Elephant(int i) {
		this(i, 10);
	}

	Elephant(int i, int j) {
		super(i);
	}
}
