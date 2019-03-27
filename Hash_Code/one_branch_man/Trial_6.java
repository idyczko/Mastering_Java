package net.codestrikes.adapter;
import net.codestrikes.sdk.Area;
import net.codestrikes.sdk.BotBase;
import net.codestrikes.sdk.*;
import net.codestrikes.sdk.MoveCollection;
import net.codestrikes.sdk.RoundContext;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.*;
import java.util.stream.*;

public class PlayerBot extends BotBase {
  		private static final int MANA = 12;
  private static final Map<String, Integer> MOVES_COST;
  private static final Map<String, Integer> HIT_DAMAGE;
  private static final Map<String, Map<String, Integer>> payMatrix = new HashMap<String, Map<String, Integer>>();
  private static final Set<String> possibleMoves = new HashSet<>();
  private static final Set<String> opMoves = new HashSet<>();
  private static final List<String> sortedMoves;
  private static int[][] matrix;
  private static final Map<String, Area> areaMapping = new HashMap<>();
  private static final Map<Area, String> myMapping = new HashMap<>();
  private static final Set<String> taboo = new HashSet<>();
  private static String lastMove = "";

  static {
    Map<String, Integer> temp = new HashMap<>();
    temp.put("AHK", 4);
    temp.put("AHP", 3);
    temp.put("AUP", 2);
    temp.put("ALK", 1);
    temp.put("DHK", 4);
    temp.put("DHP", 4);
    temp.put("DUP", 4);
    temp.put("DLK", 4);
    areaMapping.put("HK", Area.HookKick);
    areaMapping.put("HP", Area.HookPunch);
    areaMapping.put("UP", Area.UppercutPunch);
    areaMapping.put("LK", Area.LowKick);
    myMapping.put(Area.HookKick, "HK");
    myMapping.put(Area.HookPunch, "HP");
    myMapping.put(Area.UppercutPunch, "UP");
    myMapping.put(Area.LowKick, "LK");
    MOVES_COST = Collections.unmodifiableMap(temp);
    Map<String, Integer> temp2 = new HashMap<>();
    temp2.put("AHK", 10);
    temp2.put("AHP", 6);
    temp2.put("AUP", 3);
    temp2.put("ALK", 1);
    HIT_DAMAGE = Collections.unmodifiableMap(temp2);

    fillPossibleMoves();
    sortedMoves = new ArrayList<>(possibleMoves);
    Collections.sort(sortedMoves);
    matrix = new int[sortedMoves.size()][sortedMoves.size()];
    for (int i = 0; i < sortedMoves.size(); i++) {
      for (int j = 0; j < sortedMoves.size(); j++) {
        matrix[i][j] = result(sortedMoves.get(i), sortedMoves.get(j));
      }
    }
  }

		public MoveCollection nextMove(RoundContext context) {
          /*
            Move[] ats= context.getLastOpponentMoves().getAttacks();
            Area[] atsAr =  Arrays.stream(ats).map(a -> Area.valueOf(a.toString())).toArray(Area[]::new);
          	Move[] blk= context.getLastOpponentMoves().getDefences();
            Area[] blkAr =  Arrays.stream(ats).map(a -> Area.valueOf(a.toString())).toArray(Area[]::new);

			List<String> attacks = Arrays.stream(atsAr).map(a -> "A"+ myMapping.get(a)).filter(a -> a!=null && a.length() == 3).collect(Collectors.toList());
          	Set<String> blocks = Arrays.stream(blkAr).map(a ->"D"+ myMapping.get(a)).filter(a -> a!=null && a.length() == 3).collect(Collectors.toSet());

          	String opMove = encode(attacks) + " " + encode(blocks);
            if (sortedMoves.contains(opMove))
          		opMoves.add(opMove);*/
          if (!"".equals(lastMove)) {
			int result = context.getMyDamage() - context.getOpponentDamage();
            Set<String> possibleOpMoves = new HashSet<>();
            for (int i = 0; i < sortedMoves.size(); i++) {
            	if (lastMove.equals(sortedMoves.get(i))) {
                	for (int j = 0; j < sortedMoves.size(); j++) {
                    	if (matrix[i][j] == result) {
                        	possibleOpMoves.add(sortedMoves.get(j));
                        }
                    }
                }
            }
            opMoves.addAll(possibleOpMoves);
          	//if (context.getMyDamage() < context.getOpponentDamage()) {
            	//taboo.add(lastMove);
            //}
          }

          Set<String> nasheq = findNashEquilibrium(sortedMoves, matrix, calculateTaboo());
          	String move = nasheq.stream().findAny().orElse("AHKAHPALKAUPAUP ");
          	lastMove = move;
          	List<String> at = new ArrayList<>();
          	Set<String> bl = new HashSet<>();
          	decode(move, at, bl);
          	for (String attack : at) {
            	context.getMyMoves().addAttack(areaMapping.get(attack.substring(1)));
            }

          	for (String block : bl) {
            	context.getMyMoves().addDefence(areaMapping.get(block.substring(1)));
            }
			return context.getMyMoves();
		}

  private static Set<String> calculateTaboo() {
    Set<String> taboo = new HashSet<>();
    for (int i = 0; i < sortedMoves.size(); i++) {
      for (int j = 0; j < sortedMoves.size(); j++) {
        if (opMoves.contains(sortedMoves.get(j)) && matrix[i][j] < 0) {
          taboo.add(sortedMoves.get(j));
        }
      }
    }
    return taboo;
  }

  private static Set<String> findNashEquilibrium(List<String> moves, int[][] matrix, Set<String> taboo) {
    int[] mins = new int[matrix.length];
    int[] maxs = new int[matrix.length];
    int[] avgs = new int[matrix.length];
    int[] wins = new int[matrix.length];
    int[] losses = new int[matrix.length];
    int[] pointsWon = new int[matrix.length];
    int[] pointsLost = new int[matrix.length];
    for (int i = 0; i < matrix.length; i++) {
      if (taboo.contains(moves.get(i)))
        continue;

      int min = 100000;
      int max = -100000;
      int accu = 0;
      int win = 0;
      int lose = 0;
      int pW = 0;
      int pL = 0;
      for (int j = 0; j < matrix[i].length; j++) {
        if (matrix[i][j] > 0) {
          win++;
          pW += matrix[i][j];
        }
        if (matrix[i][j] < 0) {
          lose++;
          pL -= matrix[i][j];
        }

        accu += matrix[i][j];
        min = min > matrix[i][j] ? matrix[i][j] : min;
        max = max < matrix[i][j] ? matrix[i][j] : max;
      }
      mins[i] = min;
      maxs[i] = max;
      avgs[i] = accu;
      wins[i] = win;
      losses[i] = lose;
      pointsWon[i] = pW;
      pointsLost[i] = pL;
    }
    /*for (int i = 0; i < moves.size(); i++) {
      System.out.println(moves.get(i) + "\t\ttmin: " + mins[i] + "\tmax: " + maxs[i] +"\twins: " + wins[i] +"\tlosses: " + losses[i] +"\tpointsWon: " + pointsWon[i] +"\tpointsLost: " + pointsLost[i]);
    }*/

    int reff = 10000;
    Set<String> equi = new HashSet<>();
    for (int i = 0; i < mins.length; i++) {
      if (taboo.contains(moves.get(i)))
        continue;

      if (reff > pointsLost[i]) {
        reff = pointsLost[i];
        equi = new HashSet<>();
      }

      if (reff == pointsLost[i]) {
        equi.add(moves.get(i));
      }
    }
    String best = "";
     for (String move : equi) {
     	if ("".equals(best) || damage(best) < damage(move)) {
        	best = move;
        }
     }
    //System.out.println("Losses: " + reff + "/"+moves.size());
    return Collections.singleton(best);
  }

  private static int damage(String move) {
  	List<String> attacks = new ArrayList<>();
    decode(move, attacks, new HashSet<>());
    return attacks.stream().mapToInt(a -> HIT_DAMAGE.get(a)).sum();
  }

  private static void fillPossibleMoves() {
    fillPossibleMoves(possibleMoves, new ArrayList<>(), new HashSet<>());
  }

  private static void fillPossibleMoves(Set<String> possibleMoves, List<String> attacks, Set<String> blocks) {
    int movesCost = moveCost(attacks, blocks);
    if (movesCost > MANA)
      return;

    if(!attacks.isEmpty() || !blocks.isEmpty())
      possibleMoves.add(encode(attacks) + " " + encode(blocks));

    if (!blocks.contains("DHK")) {
      blocks.add("DHK");
      fillPossibleMoves(possibleMoves, attacks, blocks);
      blocks.remove("DHK");
    }

    if (!blocks.contains("DHP")) {
      blocks.add("DHP");
      fillPossibleMoves(possibleMoves, attacks, blocks);
      blocks.remove("DHP");
    }

    if (!blocks.contains("DUP")) {
      blocks.add("DUP");
      fillPossibleMoves(possibleMoves, attacks, blocks);
      blocks.remove("DUP");
    }

    if (!blocks.contains("DLK")) {
      blocks.add("DLK");
      fillPossibleMoves(possibleMoves, attacks, blocks);
      blocks.remove("DLK");
    }

    attacks.add("AHK");
    fillPossibleMoves(possibleMoves, attacks, blocks);
    attacks.remove("AHK");

    attacks.add("AHP");
    fillPossibleMoves(possibleMoves, attacks, blocks);
    attacks.remove("AHP");

    attacks.add("AUP");
    fillPossibleMoves(possibleMoves, attacks, blocks);
    attacks.remove("AUP");

    attacks.add("ALK");
    fillPossibleMoves(possibleMoves, attacks, blocks);
    attacks.remove("ALK");
  }

  private static String encode(Collection<String> col) {
    List<String> list = new ArrayList<>(col);
    Collections.sort(list);
    return list.stream().collect(Collectors.joining());
  }

  private static void decode(String code, List<String> attacks, Set<String> blocks) {
    String[] moves = code.split(" ");
    for (int i = 0; i < moves[0].length(); i+=3) {
      attacks.add(moves[0].substring(i, i+3));
    }
    if (moves.length > 1)
      for (int i = 0; i < moves[1].length(); i+=3) {
        blocks.add(moves[1].substring(i, i+3));
      }
  }

  private static int moveCost(String move) {
    List<String> pAt = new ArrayList<>();
    Set<String> pBl = new HashSet<>();
    decode(move, pAt, pBl);
    return moveCost(pAt, pBl);
  }

  private static int moveCost(List<String> attacks, Set<String> blocks) {
      return attacks.stream().mapToInt(a -> MOVES_COST.get(a)).sum() + blocks.stream().mapToInt(b -> MOVES_COST.get(b)).sum();
  }

  private static int result(String p1, String p2) {
    List<String> p1At = new ArrayList<>();
    List<String> p2At = new ArrayList<>();
    Set<String> p1Bl = new HashSet<>();
    Set<String> p2Bl = new HashSet<>();
    decode(p1, p1At, p1Bl);
    decode(p2, p2At, p2Bl);
    p1Bl.forEach(bl -> p1At.add(bl));
    p2Bl.forEach(bl -> p2At.add(bl));
    return result(p1At, p2At);
  }

  private static int result(List<String> p1, List<String> p2) {
    for (String move : p1) {
      if (move.startsWith("D"))
        cockBlock(move, p2);
    }

    for (String move : p2) {
      if (move.startsWith("D"))
        cockBlock(move, p1);
    }

    return p1.stream().filter(move -> move.startsWith("A")).mapToInt(move -> HIT_DAMAGE.get(move)).sum()
      - p2.stream().filter(move -> move.startsWith("A")).mapToInt(move -> HIT_DAMAGE.get(move)).sum();
  }

  private static void cockBlock(String block, List<String> moves) {
    List<String> temp = moves.stream().filter(move -> move.startsWith("D") || !move.substring(1).equals(block.substring(1))).collect(Collectors.toList());
    moves.clear();
    moves.addAll(temp);
  }
}
