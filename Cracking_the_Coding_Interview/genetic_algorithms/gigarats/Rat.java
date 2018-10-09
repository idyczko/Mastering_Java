
public class Rat implements Comparable{
	public int birthGeneration;
	public int deathGeneration;
	public int weight;
	public Sex sex;

	public Rat(int birthGeneration, int deathGeneration, int weight, Sex sex) {
		this.birthGeneration = birthGeneration;
		this.deathGeneration = deathGeneration;
		this.weight = weight;
		this.sex = sex;
	}

	public int lifespan() {
		return this.deathGeneration - this.birthGeneration;
	}

	@Override
	public int compareTo(Object r) {
		return this.weight - ((Rat) r).weight;
	}

	public enum Sex {M, F};
}
