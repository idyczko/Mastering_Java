import java.util.*;
import java.util.stream.*;
import java.util.function.*;

public class Main {

  public static void main(String[] args){
    String[] str = {"Alastor", "Bob", "Susan", "Marty", "Charles"};
    Stream.of(str).sorted().forEach(System.out::println);
    Stream.of(new String[]{"Alastor", "Bob", "Susan", "Marty", "Charles"}).sorted().forEach(System.out::println);
    Stream.of("Alastor", "Bob", "Susan", "Marty", "Charles").sorted().forEach(System.out::println);
    Arrays.stream(str).sorted().forEach(System.out::println);
    Stream<String> stream = Arrays.stream(str);
    stream.sorted().forEach(System.out::println);
    //stream.forEach(System.out::println); //BOOM, IllegalStateException - stream has already been operated upon or closed.
    Supplier<Stream<String>> streamSupplier = () -> Arrays.stream(str);

    /* The operation below won't print anything to stdout, as it lacks terminal operation.
    */
    streamSupplier.get().filter(name -> name.charAt(0) > 'A' && name.charAt(0) < 'S').sorted((s1,s2) -> {
      System.out.println("Comparing pair: "+s1 +" and " + s2);
      return s1.compareTo(s2);
    });

    /*This stream operation is ended with terminal operation, so the calculations
    * will be performed. Also, note how we filter before sorting - sorting is a
    * stateful operation, so it needs full outcome of filtering before it is performed.
    */
    streamSupplier.get().filter(name -> {
      System.out.println("Filtering: " + name);
      return name.charAt(0) > 'A' && name.charAt(0) < 'S';}).sorted((s1,s2) -> {
      System.out.println("Comparing pair: " + s1 +" and " + s2);
      return s1.compareTo(s2);
    }).forEach(System.out::println);

    /*After sorting, everything is resolved "vertically". Check out the outcome logs.
    *It will first map one String to Object, then back to String, next to int and
    * print it to the stdout. After that it starts with next element of the stream.
    */
    streamSupplier.get().filter(name -> {
      System.out.println("Filtering: " + name);
      return name.charAt(0) > 'A' && name.charAt(0) < 'S';}).sorted((s1,s2) -> {
      System.out.println("Comparing pair: "+s1 +" and " + s2);
      return s1.compareTo(s2);
    }).map(item -> {
      System.out.println("Mapping " + item +" to Object");
      return (Object) item;
    }).map(item -> {
      System.out.println("Mapping " + item +" back to String");
      return (String) item;
    }).mapToInt(item -> {
      System.out.println("Mapping " + item +" to primitive int");
      return item.charAt(0);
    }).forEach(System.out::println);

    //Arrays are covariant, so generic types are not allowed in arrays. It would lead to unsafe operations.
    List<Optional/*Kind of Maybe*/<String>> optionals = new ArrayList<>();
    optionals.add(streamSupplier.get().filter(item -> item.charAt(0) < 'C').findAny());
    optionals.add(streamSupplier.get().filter(item -> item.charAt(0) == 'M').findAny());
    optionals.add(streamSupplier.get().filter(item -> item.charAt(0) > 'Z').findAny());

    Arrays.stream(optionals.toArray()/*Objects*/).map(item -> (Optional<String>) item/*Unsafe downcasting*/)
    .filter(item -> item.isPresent()).forEach(item -> System.out.println(item.get()));

    //Some boolean pipeline operations.
    System.out.println(streamSupplier.get().anyMatch(item -> item.startsWith("A")) + " " + streamSupplier.get().noneMatch(item -> item.endsWith("d")));

    //COLLECTORS - such complicated creatures... So mysterious...
    //Mapping String on occurrences :D Cool, eh? :D
    Map<String, Long> collect = streamSupplier.get().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    List<Person> people = prepareTestingList();

    //List of filtered elements
    List<Person> teens = people.stream().filter(person -> person.age > 10 && person.age <20).collect(Collectors.toList());
    System.out.println(teens);

    //People grouped by age
    Map<Integer, List<Person>> peopleByAge = people.stream().collect(Collectors.groupingBy(person -> person.age));

    //Occurrences by age
    Map<Integer, Integer> occurencesByAge = people.stream().collect(Collectors.groupingBy(Person::getAge, Collectors.summingInt(i -> 1)));
    occurencesByAge.forEach((k, v) -> System.out.println(v + " of " + k + "-year olds"));

    //Entirely new map:
    /*Note, that the following map build will cause IllegalStateException, because
    * there are records in people list, that reference names with the same letter count,
    * therefore causing situation, where two different records are associated with the exact
    * same key. Stream API for creating maps will not allow that at runtime. If you want to
    * handle this situation customly, you have to specify merge function. Pairs Alastor and
    * Charles, as well as Susan and Marty will have to be handled!
    */
    //Map<Integer, String> letterCountToNames = people.stream().collect(Collectors.toMap(p -> p.name.length(), p -> p.name));// BOOM! IllegalStateException!

    //Creating entirely new map with merging function
    Map<Integer, String> letterCountToNamesMerging = people.stream().collect(Collectors.toMap(p -> p.name.length(), p -> p.name, (name1, name2) -> name1 + ", " + name2));

    //Collectors also have some functions for statistical analysis:
    IntSummaryStatistics statistics = people.stream().collect(Collectors.summarizingInt(person -> person.age));
    System.out.println(statistics);
  }

  public static List<Person> prepareTestingList() {
    List<Person> people = new ArrayList<>();
    people.add(new Person(12, "Sarah"));
    people.add(new Person(18, "Tom"));
    people.add(new Person(18, "Sam"));
    people.add(new Person(121233, "Sauron"));
    people.add(new Person(28, "Fineas"));
    people.add(new Person(95, "Henry"));
    people.add(new Person(45, "Albus"));
    people.add(new Person(18, "Frank"));
    return people;
  }

  public static class Person {
    public int age;
    public String name;

    public String getName() {
      return this.name;
    }

    public int getAge() {
      return this.age;
    }

    public Person(int age, String name) {
      this.age = age;
      this.name = name;
    }

    @Override
    public String toString() {
      return this.name;
    }
  }
}
