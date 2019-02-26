import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Main{

  private static final String TAG = "///////////////////////////////////// %s /////////////////////////////////////";
  private static final String RESULT_FILENAME = "result.out";
  private static int R;
  private static int C;
  private static int L;
  private static int H;
  private static int LIM;
  private static boolean concurrent = false;
  private static char[][] pizza;
  private static boolean log = false;
  private static boolean saveResult = false;

  private static final ExecutorService pool = Executors.newFixedThreadPool(8);

  public static void main(String[] args) {
    log |= option(args, "-l");
    concurrent |= option(args, "-c");
    saveResult |= option(args, "-s");

    Scanner in = new Scanner(System.in);

    R = in.nextInt();
    C = in.nextInt();
    L = in.nextInt();
    H = in.nextInt();

    if (option(args, "-lim")) {
      for (int i = 0; i < args.length; i++)
        if (args[i].equals("-lim")) {
          LIM = Integer.valueOf(args[i+1]);
          break;
        }
    } else {
      LIM = H;
    }
    pizza = new char[R][C];
    readPizza(in);

    log(String.format(TAG, "Pizza"));
    if (log) {
      print(pizza);
    }

    Set<Slice> orderedPickSolution = createSolutionByOrderedPick();

    log(String.format(TAG, "Ordered Pick Solution"));

    if(log) {
      orderedPickSolution.forEach(System.out::println);
      String[][] slicedPizzaByOrderedPick = markPizza(orderedPickSolution);
      print(slicedPizzaByOrderedPick);
    }

    Set<Slice> expansionSolution = searchSolutionSpaceByExpansion(orderedPickSolution);

    if (saveResult) {
      saveResultToFile(expansionSolution);
    }

    log(String.format(TAG, "Expansion Solution"));
    if(log) {
      expansionSolution.forEach(System.out::println);
      String[][] slicedPizzaByExpansion = markPizza(expansionSolution);
      print(slicedPizzaByExpansion);
    }

    System.out.println("Solution score: " +  score(expansionSolution) + " pizza size: " + (C*R));
  }

  private static void saveResultToFile(Set<Slice> expansionSolution) {
    Path path = Paths.get(".", RESULT_FILENAME);
    List<String> results = expansionSolution.stream().map(Slice::toSaveString).collect(Collectors.toList());
    results.add(0, String.valueOf(expansionSolution.size()));
    try {
      Files.write(path, results);
    } catch (IOException e){
      System.out.println(e.getMessage());
    }
  }

  private static Set<Slice> createSolutionByOrderedPick() {
    Set<Slice> initSlices = concurrent ? computeLimitedSlicesConcurrently(pizza, L, H, Math.max(Math.min(H, LIM), 2*L)) : computeLimitedSlices(pizza, L, H, Math.max(Math.min(H, LIM), 2*L));
    System.out.println("Finished");
    PriorityQueue<Slice> priorityQueue = new PriorityQueue<>(R * C, Comparator.comparingInt(Slice::size));
    priorityQueue.addAll(initSlices);

    Set<Slice> solution = new HashSet<>(Arrays.asList(priorityQueue.poll()));
    while(!priorityQueue.isEmpty()) {
      Slice slice = priorityQueue.poll();
      boolean intersects = solution.stream().anyMatch(s -> intersect(slice, s));
      if (!intersects)
        solution.add(slice);
    }

    if (log)
      initSlices.forEach(System.out::println);

    return solution;
  }

  private static Set<Slice> searchSolutionSpaceByExpansion(Set<Slice> initSlices) {
    Set<Slice> nonExpandable = new HashSet<>();
    while (!initSlices.isEmpty()) {
      Slice slice = chooseSliceForExpansion(initSlices);
      initSlices.remove(slice);
      Slice expanded = expand(slice, nonExpandable, initSlices);

      if (expanded.equals(slice) || expanded.size() > H) {
        nonExpandable.add(slice);
        continue;
      }

      initSlices.add(expanded);
    }

    return nonExpandable;
  }

  private static Slice chooseSliceForExpansion(Set<Slice> slices) {
    if (slices.isEmpty())
      throw new IllegalStateException("Trying to choose from empty set.");
    return slices.stream().findAny().get();
  }

  private static Slice expand(Slice slice, Set<Slice> s1, Set<Slice> s2) {
    Slice expanded = null;
    try {
      expanded = slice.expandLeft();
    } catch(IllegalStateException e) {
    }

    if (expanded == null || intersect(expanded, s1, s2)) {
      try {
        expanded = slice.expandRight();
      } catch(IllegalStateException e) {
      }
    }
    if (expanded == null || intersect(expanded, s1, s2)) {
      try {
        expanded = slice.expandUp();
      } catch(IllegalStateException e) {
      }
    }
    if (expanded == null || intersect(expanded, s1, s2)) {
      try {
        expanded = slice.expandDown();
      } catch(IllegalStateException e) {
      }
    }
    return expanded != null && !intersect(expanded, s1, s2) ? expanded : slice;
  }

  private static boolean intersect(Slice slice, Set... sets) {
    for (Set<Slice> set : sets)
      if (set.stream().anyMatch(s -> intersect(s, slice)))
        return true;
    return false;
  }

  private static Set<Slice> computeLimitedSlices(char[][] pizza, int L, int H, int limit) {
    Set<Slice> slices = new HashSet<>();
    for (int i = 0; i < R; i++) {
      for (int j = 0; j < C; j++) {
        log("Cell: " + (i*C + j) +"/" + (R*C));
        slices.addAll(generateLimitedSlicesForCenter(i, j, pizza, limit));
      }
    }

    return slices;
  }

  private static Set<Slice> computeLimitedSlicesConcurrently(char[][] pizza, int L, int H, int limit) {
    Map<Slice, Object> slices = new ConcurrentHashMap<>();
    for (int i = 0; i < R; i++) {
      final int I = i;
      pool.execute(() -> computeLimitedSlicesForRow(pizza, L, H, limit, I, slices));
    }

    pool.shutdown();
    try {
      pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    } catch (InterruptedException e) {
    }

    return new HashSet<> (slices.keySet());
  }

  private static void computeLimitedSlicesForRow(char[][] pizza, int L, int H, int limit, int i,   Map<Slice, Object> slices) {
    Set<Slice> rowSlices = new HashSet<>();
    for (int j = 0; j < C; j++) {
      log("Cell: " + (i*C + j) +"/" + (R*C));
      rowSlices.addAll(generateLimitedSlicesForCenter(i, j, pizza, limit));
    }
    rowSlices.forEach(s -> slices.put(s, ""));
  }

  private static Set<Slice> computeAllFeasibleSlicesForCenter(int i, int j, char[][] pizza) {
    return generateLimitedSlicesForCenter(i, j, pizza, H);
  }

  private static Set<Slice> computeMinimalSlicesForCenter(int i, int j, char[][] pizza) {
    return generateLimitedSlicesForCenter(i, j, pizza, 2*L);
  }

  private static Set<Slice> generateLimitedSlicesForCenter(int i, int j, char[][] pizza, int limit) {
    Set<Slice> slices = new HashSet<>();
    Slice slice = new Slice(new Point(j, i), new Point(j, i));
    expandSliceRecursively(slice, slices, limit);
    return slices;
  }

  private static void expandSliceRecursively(Slice slice, Set<Slice> slices, int expansionLimit) {
    if (slice.size() >= 2*L && slice.isAllowed()) {
      slices.add(slice);
      return;
    }
    if (slice.size() >= expansionLimit)
      return;

    try {
      Slice left = slice.expandLeft();
      expandSliceRecursively(left, slices, expansionLimit);
    } catch(IllegalStateException e) {
    }
    try {
      Slice right = slice.expandRight();
      expandSliceRecursively(right, slices, expansionLimit);
    } catch(IllegalStateException e) {
    }
    try {
      Slice up = slice.expandUp();
      expandSliceRecursively(up, slices, expansionLimit);
    } catch(IllegalStateException e) {
    }
    try {
      Slice down = slice.expandDown();
      expandSliceRecursively(down, slices, expansionLimit);
    } catch(IllegalStateException e) {
    }

  }

  private static boolean intersect(Slice s1, Slice s2) {
    return intersectHorizontal(s1, s2) && intersectVertical(s1, s2);

  }

  private static boolean intersectHorizontal(Slice s1, Slice s2) {
    if (s1.upperLeft.x < s2.upperLeft.x) {
      return s2.upperLeft.x <= s1.lowerRight.x;
    }

    if (s1.upperLeft.x > s2.upperLeft.x) {
      return s1.upperLeft.x <= s2.lowerRight.x;
    }

    return true;
  }

  private static boolean intersectVertical(Slice s1, Slice s2) {
    if (s1.upperLeft.y < s2.upperLeft.y) {
      return s2.upperLeft.y <= s1.lowerRight.y;
    }

    if (s1.upperLeft.y > s2.upperLeft.y) {
      return s1.upperLeft.y <= s2.lowerRight.y;
    }

    return true;
  }

  private static int score(Set<Slice> slices) {
    return slices.stream().mapToInt(s -> s.size()).sum();
  }

  private static void readPizza(Scanner sc) {
    String line = sc.nextLine();
    for(int i = 0; i < R; i++) {
      line = sc.nextLine();
      char[] chars = line.toCharArray();
      for (int j = 0; j < chars.length; j++) {
        pizza[i][j] = chars[j];
      }
    }
  }

  private static String[][] markPizza(Set<Slice> solution) {
    String[][] slicedPizza = new String[R][C];
    for (int i = 0; i < C; i++)
      for (int j = 0; j < R; j++)
        slicedPizza[j][i] = "" + pizza[j][i];
    int index = 0;
    for (Slice slice : solution) {
      for (int i = slice.upperLeft.y; i <= slice.lowerRight.y; i++)
        for (int j = slice.upperLeft.x; j <= slice.lowerRight.x; j++)
          slicedPizza[i][j] = String.valueOf(index);
      index++;
    }
    return slicedPizza;
  }

  private static boolean option(String[] args, String op) {
    return Arrays.stream(args).anyMatch(a -> a.equals(op));
  }

  private static void print(String[][] pizza) {
    for (String[] row : pizza) {
      for (String ing : row)
        System.out.print(ing + "\t");
      System.out.println();
    }
  }

  private static void print(char[][] pizza) {
    for (char[] row : pizza) {
      for (char ing : row)
        System.out.print(ing + " ");
      System.out.println();
    }
  }

  private static void log(String str) {
    if (log)
      System.out.println(str);
  }

  private static class Slice {
    Point upperLeft;
    Point lowerRight;

    Slice (Point p1, Point p2) {
      int minX = p1.x > p2.x ? p2.x : p1.x;
      int maxX = p1.x > p2.x ? p1.x : p2.x;
      int minY = p1.y > p2.y ? p2.y : p1.y;
      int maxY = p1.y > p2.y ? p1.y : p2.y;
      this.upperLeft = new Point(minX, minY);
      this.lowerRight = new Point(maxX, maxY);

    }

    int size() {
      return (1 + lowerRight.x - upperLeft.x)*(1 + lowerRight.y - upperLeft.y);
    }

    boolean isAllowed() {
      int ms = 0, ts = 0;
      for (int j = this.upperLeft.x; j <= this.lowerRight.x; j++)
        for (int i = this.upperLeft.y; i <= this.lowerRight.y; i++) {
          if (pizza[i][j] == 'M')
            ms++;
          else
            ts++;
        }

      return (ms >= L) && (ts >= L) && (size() <= H);
    }

    @Override
    public String toString() {
      return "upperLeft: " + upperLeft.toString() + " lowerRight: " + lowerRight.toString() + " size: " + this.size();
    }

    public String toSaveString() {
      return upperLeft.x + " " + upperLeft.y + " " + lowerRight.x + " " + lowerRight.y;
    }

    Slice expandLeft() {
      return new Slice(upperLeft.move(-1, 0), lowerRight);
    }

    Slice expandRight() {
      return new Slice(upperLeft, lowerRight.move(1, 0));
    }

    Slice expandUp() {
      return new Slice(upperLeft.move(0, -1), lowerRight);
    }

    Slice expandDown() {
      return new Slice(upperLeft, lowerRight.move(0, 1));
    }

    @Override
    public int hashCode() {
      return 31 * upperLeft.hashCode() + 37 * lowerRight.hashCode();
    }

    @Override
    public boolean equals(Object o) {
      Slice s = (Slice) o;
      return this.upperLeft.equals(s.upperLeft) && this.lowerRight.equals(s.lowerRight);
    }
  }

  private static class Point {
    final int x;
    final int y;

    Point(int x, int y) {
      this.x = x;
      this.y = y;
    }

    Point move(int hor, int ver) {
      if (this.x + hor < 0 || this.x + hor >= C || this.y + ver < 0 || this.y + ver >= R)
        throw new IllegalStateException();
      return new Point(x + hor, y + ver);
    }

    Point cp() {
      return new Point(x, y);
    }

    @Override
    public String toString() {
      return "x: " + x + " y: " + y;
    }

    @Override
    public int hashCode() {
      return 31 * x + 13 * y;
    }

    @Override
    public boolean equals(Object o) {
      Point p = (Point) o;
      return x == p.x && y == p.y;
    }
  }
}