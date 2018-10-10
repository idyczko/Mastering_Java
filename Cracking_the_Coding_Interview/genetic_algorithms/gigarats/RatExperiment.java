import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import static java.lang.Math.random;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.Map;

public class RatExperiment {
	public static final int GOAL_MASS = 1000;
	public static final int MAX_GEN = 50000;
	public static final int MALES = 10;
	public static final int FEMALES = 10;
	public static final double M_WEIGHT_MEAN = 325;
	public static final double F_WEIGHT_MEAN = 250;
	public static final double M_WEIGHT_DEV = 50;
	public static final double F_WEIGHT_DEV = 50;
	public static final double GEN_OF_LIVING_MEAN = 30;
	public static final double GEN_OF_LIVING_DEV = 6;
	public static final double PUPS_MEAN = 8;
	public static final double PUPS_DEV = 1;
	public static final double MUTATION_ODDS = 0.01;
	public static final double MUTATION_MEAN = 0.9;
	public static final double MUTATION_DEV = 0.1;

	public static int gen = 0;
	public static List<Rat> males = new ArrayList<>(MALES);
	public static List<Rat> females = new ArrayList<>(FEMALES);

	private Random rand = new Random();

	private double nextGaussian() {return rand.nextGaussian();}
	
	private RatExperiment() {
	
		for (int i = 0; i < MALES; i++)
			males.add(new Rat(gen, gen + (int) nr(GEN_OF_LIVING_MEAN, GEN_OF_LIVING_DEV), (int) nr(M_WEIGHT_MEAN, M_WEIGHT_DEV), Rat.Sex.M));
	
		for (int i = 0; i < FEMALES; i++)
			females.add(new Rat(gen, gen + (int) nr(GEN_OF_LIVING_MEAN, GEN_OF_LIVING_DEV), (int) nr(F_WEIGHT_MEAN, F_WEIGHT_DEV), Rat.Sex.F));
		
	}

	public static void experiment() {
		RatExperiment re = new RatExperiment();
		while(gen < MAX_GEN && !(females.get(0).weight >= 1000 || males.get(0).weight >= 1000))
			re.nextGen();
	}

	private void nextGen() {
		gen++;
		grimReaper();
		List<Rat> offspring = new ArrayList<>();
		females.forEach(fem -> offspring.addAll(mate(fem, chooseMale())));
		Map<Boolean, List<Rat>> map = offspring.stream().collect(Collectors.partitioningBy(rat -> Rat.Sex.M.equals(rat.sex)));
		females.addAll(map.get(false));
		males.addAll(map.get(true));
		cutOffPopulation();
		log();
	}

	private void log() {
		//System.out.println("Males:");
		//males.forEach(m -> System.out.println("Birth generation: " + m.birthGeneration + "\tDeath generation: " + m.deathGeneration + "\tWeight: " + m.weight));
		//System.out.println("Females:");
		//females.forEach(f -> System.out.println("Birth generation: " + f.birthGeneration + "\tDeath generation: " + f.deathGeneration + "\tWeight: " + f.weight));
		System.out.println("Generation " + gen + ":\nFittest male: " + males.get(0).weight + "\nFittest female: " + females.get(0).weight);
	
	}

	private void grimReaper() {
		males = males.stream().filter(m -> m.deathGeneration > gen).collect(Collectors.toList());
		females = females.stream().filter(f -> f.deathGeneration > gen).collect(Collectors.toList());
	}

	private void cutOffPopulation() {
		Collections.sort(males, Collections.reverseOrder());
		Collections.sort(females, Collections.reverseOrder());
		males = males.subList(0, Math.min(10, males.size()));
		females = females.subList(0, Math.min(10, females.size()));
	}

	private List<Rat> mate(Rat mom, Rat dad) {
		int pups =Math.max((int) nr(PUPS_MEAN, PUPS_DEV), 0);
		List<Rat> litter = new ArrayList<>(pups);
		for (int i = 0; i<pups; i++) {
			Rat pup = recombinate(mom, dad);
			if (random() <= 0.01)
				mutate(pup);
			litter.add(pup);
		}
		return litter;
	}

	private Rat chooseMale() {
		Collections.sort(males, Collections.reverseOrder());
		int maleRank = 0;
		while(random() <= 0.1)
			maleRank++;
		return males.get(maleRank < males.size() ? maleRank : (males.size() - 1));
	}

	private Rat recombinate(Rat mom, Rat dad) {
		Rat.Sex sex = random() >= 0.5 ? Rat.Sex.M : Rat.Sex.F;
		return new Rat(gen, gen + (((int) nr(GEN_OF_LIVING_MEAN, GEN_OF_LIVING_DEV) + mom.lifespan() + dad.lifespan())/3), inheritWeight(mom, dad, sex), sex);
	}

	private int inheritWeight(Rat mom, Rat dad, Rat.Sex sex) {
		double momFactor = Rat.Sex.M.equals(sex) ? 0.1 : 0.9;
		double dadFactor = Rat.Sex.M.equals(sex) ? 0.9 : 0.1;
		return (int) (mom.weight*momFactor) + (int) (dad.weight*dadFactor);
	}

	private void mutate(Rat r) {
		r.deathGeneration = r.birthGeneration +(int) nr(0.8, 0.2)*r.lifespan();
		r.weight *= nr(MUTATION_MEAN, MUTATION_DEV); 
	}

	private double nr(double mean, double dev) {
		return (nextGaussian() - 0.5)*dev*2 + mean;
	}
}
