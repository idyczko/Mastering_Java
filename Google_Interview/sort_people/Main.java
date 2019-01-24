import java.util.*;
import java.util.stream.*;

public class Main {

	public static void main(String[] args) {
	
		List<Person> people = new ArrayList<>();
		people.add(new Person(200, 0));
		people.add(new Person(145, 2));
		people.add(new Person(168, 0));
		people.add(new Person(150, 0));
		people.add(new Person(153, 0));
		people.add(new Person(170, 0));
		people.add(new Person(189, 1));
		people.add(new Person(195, 1));
		people.add(new Person(175, 3));
		people.add(new Person(185, 3));

		print(people);
		people = reorganize(people);
		print(people);
	}

	private static List<Person> reorganize(List<Person> people) {
		List<Person> organized = new ArrayList<>();
		int rank = 0;
		while (!people.isEmpty()) {
			final int finalRank = rank;
			List<Person> peopleOfRank = people.stream().filter(p -> p.rank == finalRank).collect(Collectors.toList());
			rank++;
			people.removeAll(peopleOfRank);
			peopleOfRank.stream().sorted((p1, p2) -> p1.height - p2.height).forEach(p -> insertIntoOrganized(organized, p));
		}
		return organized;
	}

	private static void insertIntoOrganized(List<Person> organized, Person p) {
		List<Integer> indices = new ArrayList<>();
		for (int i = 0; i < organized.size(); i++) {
			if (organized.get(i).height > p.height)
				indices.add(i);
		}
		if (indices.size() <= p.rank)
			organized.add(p);
		else {
			int index = indices.get(p.rank);
			organized.add(index, p);
		}
	}

	private static void print(List<Person> people) {
		System.out.println();
		people.forEach(item -> System.out.print("\t" + item.height));
		System.out.println();
		people.forEach(item -> System.out.print("\t" + item.rank));
	} 

	public static class Person {
		int height;
		int rank;

		public Person(int h, int r) {
			this.height = h;
			this.rank = r;
		}
	}
}
