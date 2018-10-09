import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import static java.lang.Math.random;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.Map;

public class RatExperiment {
	public static final int GOAL_MASS = 1000;
	public static final int MAX_GEN = 500;
	public static final int MALES = 10;
	public static final int FEMALES = 10;
	public static final double M_WEIGHT_MEAN = 325;
	public static final double F_WEIGHT_MEAN = 250;
	public static final double M_WEIGHT_DEV = 125;
	public static final double F_WEIGHT_DEV = 50;
	public static final double GEN_OF_LIVING_MEAN = 30;
	public static final double GEN_OF_LIVING_DEV = 6;
	public static final double PUPS_MEAN = 6;
	public static final double PUPS_DEV = 2;

	public static int gen = 0;
	public List<Rat> males = new ArrayList<>(MALES);
	public List<Rat> females = new ArrayList<>(FEMALES);

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
		while(gen < 500)
			re.nextGen();
	}

	private void nextGen() {
		gen++;
		males = males.stream().filter(m -> m.deathGeneration < gen).collect(Collectors.toList());
		females = females.stream().filter(f -> f.deathGeneration < gen).collect(Collectors.toList());
		List<Rat> offspring = new ArrayList<>();
		females.forEach(fem -> offspring.addAll(mate(fem, chooseMale())));
		Map<Boolean, List<Rat>> map = offspring.stream().collect(Collectors.partitioningBy(rat -> Rat.Sex.M.equals(rat.sex)));
		females.addAll(map.get(false));
		males.addAll(map.get(true));
		cutOffPopulation();
		System.out.println("Generation " + gen + ":\nFittest female: " + females.get(0).weight + "\nFittest male: " + males.get(0).weight);
	}

	private void cutOffPopulation() {
		Collections.sort(males, Collections.reverseOrder());
		Collections.sort(females, Collections.reverseOrder());
		males = males.subList(0, Math.min(10, males.size()));
		females = females.subList(0, Math.min(10, females.size()));
	}

	private List<Rat> mate(Rat r1, Rat r2) {
		int pups =(int) nr(PUPS_MEAN, PUPS_DEV);
		List<Rat> litter = new ArrayList<>(pups);
		for (int i = 0; i<pups; i++) {
			Rat pup = recombinate(r1, r2);
			if (random() <= 0.01)
				mutate(pup);
			litter.add(pup);
		}
		return litter;
	}

	private Rat chooseMale() {
		Collections.sort(males, Collections.reverseOrder());
		int maleRank = 0;
		while(random() <= 0.3)
			maleRank++;
		return males.get(maleRank < males.size() ? maleRank : (males.size() - 1));
	}

	private Rat recombinate(Rat r1, Rat r2) {
		Rat.Sex sex = random() >= 0.5 ? Rat.Sex.M : Rat.Sex.F;
		return new Rat(gen, gen + (((int) nr(GEN_OF_LIVING_MEAN, GEN_OF_LIVING_DEV) + r1.lifespan() + r2.lifespan())/3), Rat.Sex.M.equals(sex) ? ((int) nr(M_WEIGHT_MEAN, M_WEIGHT_DEV)) : ((int) nr(F_WEIGHT_MEAN, F_WEIGHT_DEV)), sex);
	}

	private void mutate(Rat r) {
		r.deathGeneration = r.birthGeneration +(int) nr(0.7, 0.6)*r.lifespan();
		r.weight *= nr(0.85, 0.35); 
	}

	private double nr(double mean, double dev) {
		return (nextGaussian() - 0.5)*dev*2 + mean;
	}
}
