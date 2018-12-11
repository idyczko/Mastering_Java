import java.util.*;
import java.io.*;
import java.util.stream.*;
import java.math.*;
import static java.lang.Math.*;

/**
 * Help the Christmas elves fetch presents in a magical labyrinth!
 **/
public class Player {
    public static String[][] tiles = new String[7][7];
    public static Map<String, Point> myItems = new HashMap<>();
    public static Map<String, Point> activeItems = new HashMap<>();
    public static Map<String, Point> enemyItems = new HashMap<>();
    public static Point p1 = new Point(0,0);
    public static Point p2 = new Point(0,0);
    public static String myPreviousTile;
    public static String myTile;
    public static Tree map;
    public static boolean escapeTile = false;

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int [][] items = new int[2][2];
        int [][] players = new int[2][2];
        String pushMove = "";
        String move = "";
        int deadlock = 0;

        // game loop
        while (true) {
            int turnType = in.nextInt();
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    tiles[j][i] = in.next();
                }
            }
            for (int i = 0; i < 2; i++) {
                int numPlayerCards = in.nextInt(); // the total number of quests for a player (hidden and revealed)
                int playerX = in.nextInt();
                int playerY = in.nextInt();

                Point pl = i == 0 ? p1 : p2;
                pl.x = playerX;
                pl.y = playerY;
                myPreviousTile = myTile;
                myTile = in.next();
                if (turnType == 0 && myTile.equals(myPreviousTile))
                  deadlock++;
                else
                  deadlock = 0;
            }
            int numItems = in.nextInt(); // the total number of items available on board and on player tiles
            for (int i = 0; i < numItems; i++) {
                String itemName = in.next();
                int itemX = in.nextInt();
                int itemY = in.nextInt();
                int itemPlId = in.nextInt();

                if (itemPlId == 0)
                    myItems.put(itemName, new Point(itemX, itemY));
                else
                    enemyItems.put(itemName, new Point(itemX, itemY));
            }
            int numQuests = in.nextInt(); // the total number of revealed quests for both players
            for (int i = 0; i < numQuests; i++) {
                String questItemName = in.next();
                int questPlayerId = in.nextInt();
                if (questPlayerId == 0)
                    activeItems.put(questItemName, myItems.get(questItemName));
            }

            map = new Tree(p1, 20);
            if(turnType == 0)
                pushMove = getToTheDockingPoint();
            else
                move = calculateMove();

            System.out.println(turnType == 0 ? pushMove : ("".equals(move) ? "PASS" : ("MOVE " + move.trim())));

            enemyItems.clear();
            myItems.clear();
            activeItems.clear();
        }
    }

    private static String calculateMove() {
      String move = "";
      Point start = p1;
      int depth = 20;
      while (true) {
        Tree map = new Tree(start, depth);

        String itemMove = null;
        String itemName = null;
        Point point = null;
        for (Map.Entry<String, Point> item : activeItems.entrySet()) {
          log("Active item: " + item.getValue().x + " " + item.getValue().y);
          String path = map.getPath(item.getValue());
          if (path == null)
            continue;
          if (itemMove == null || path.split(" ").length < itemMove.split(" ").length) {
            itemMove = path;
            itemName = item.getKey();
            point = item.getValue();
          }
        }
        log("Move: " + itemMove);
        if (itemMove == null) {
          if ("".equals(move) && escapeTile) {
            log("Escaping tile!");
            Point p = map.findClosestReachable(new ArrayList<>(activeItems.values()));
            if (p.equals(p1)) {
              Optional<Point> opt = map.reachablePoints.keySet().stream().filter(po -> !po.equals(p1)).findFirst();
              if (opt.isPresent())
                p = opt.get();
            }
            if (p == null || p.equals(p1))
              return null;

            move = map.getPath(p);
            if (move != null)
              escapeTile = false;
            return move;
          } else {
            escapeTile = false;
            return move;
          }
        }
        activeItems.remove(itemName);
        myItems.remove(itemName);
        move += itemMove;
        depth -= map.reachablePoints.get(point).distanceFromRoot;
        start = point;
      }

    }

    private static String getToTheDockingPoint() {
        Point target = null;
        List<Point> docks = null;
        for (Point item : activeItems.values())
          if (item.x >= 0 && !(docks = getDockingPoints(item)).isEmpty()) {
            target = item;
            break;
          }

        if (docks == null || docks.isEmpty()) {
          if (!(p1.x == 0 || p1.x == 6 || p1.y == 0 || p1.y == 6))
            escapeTile = true;
          return moveTowardsCenter(p1);
        }

        log("Target point: " + target.x + " " + target.y);

        for (Point dock : docks)
            log("Found docking point: " + dock.x + " " + dock.y);

        Point closestDock = findClosestPoint(target, docks);
        log("Closest docking point: " + closestDock.x + " " + closestDock.y);
        return calculateDockMove(closestDock, target);
    }

    private static String moveTowardsCenter(Point p) {
        int xDiff = p.x - 3;
        int yDiff = p.y - 3;
        boolean goHorizontal = abs(xDiff) > abs(yDiff);
        return "PUSH " + (goHorizontal ? (p.y + (xDiff > 0 ? " LEFT" : " RIGHT")) :
            (p.x + (yDiff > 0 ? " UP" : " DOWN")));
    }

    private static String calculateDockMove(Point dock, Point item) {
      if (taxiDistance(item, dock) == 0)
        return "PUSH " + ((dock.x + 3) % 7) + " DOWN";

      if (item.x == p1.x)
        return escapeColumn(dock, item);

      if (item.y == p1.y)
        return escapeRow(dock, item);

      int xDiff = dock.x - item.x;
      int yDiff = dock.y - item.y;
      boolean goHorizontal = abs(xDiff) != abs(yDiff) ? abs(xDiff) > abs(yDiff) :
            (abs(item.y - p1.y) == 1);

      return "PUSH " + (goHorizontal ? (item.y + (xDiff > 0 ? " RIGHT" : " LEFT")) :
        (item.x + (yDiff > 0 ? " DOWN" : " UP")));
    }

    private static String escapeColumn(Point dock, Point item) {
      if (dock.x == item.x)
        return "PUSH " + item.y + (dock.x > 3 ? " LEFT" : " RIGHT");

      return "PUSH " + item.y + (dock.x > item.x ? " RIGHT" : " LEFT");
    }

    private static String escapeRow(Point dock, Point item) {
      if (dock.y == item.y)
        return "PUSH " + item.x + (dock.y > 3 ? " UP" : " DOWN");

      return "PUSH " + item.x + (dock.y > item.y ? " DOWN" : " UP");
    }

    private static Point findClosestPoint(Point target, List<Point> points) {
      Point closest = null;
      for (Point point : points)
        if (closest == null || taxiDistance(point, target) < taxiDistance(closest, target))
          closest = point;
      return closest;
    }

    private static int taxiDistance(Point p1, Point p2) {
      return abs(p1.x - p2.x) + abs(p1.y - p2.y);
    }

    private static int distance(int x1, int y1, int x2, int y2) {
      return abs(x1 - x2) + abs(y1 - y2);
    }

    private static List<Point> getDockingPoints(Point target) {
        List<Point> docks = new ArrayList<>();
        String playerTile = getTile(p1);
        String itemTile = getTile(target);
        if (p1.y > 0 && playerTile.charAt(0) == '1' && itemTile.charAt(2) == '1')
          docks.add(getPoint(p1, "UP"));
        if (p1.y < 6 && playerTile.charAt(2) == '1' && itemTile.charAt(0) == '1')
          docks.add(getPoint(p1, "DOWN"));
        if (p1.x > 0 && playerTile.charAt(3) == '1' && itemTile.charAt(1) == '1')
          docks.add(getPoint(p1, "LEFT"));
        if (p1.x < 6 && playerTile.charAt(1) == '1' && itemTile.charAt(3) == '1')
          docks.add(getPoint(p1, "RIGHT"));
        return docks;
    }

    public static boolean parentReachable(String parentMove, String tile) {
      return parentMove.equals("DOWN") && tile.charAt(0) == '0'||
          parentMove.equals("LEFT") && tile.charAt(1) == '0' ||
          parentMove.equals("UP") && tile.charAt(2) == '0' ||
          parentMove.equals("RIGHT") && tile.charAt(3) == '0';
    }

    private static String getTile(Point p) {
      return tiles[p.x][p.y];
    }

    private static Point getPoint(Point p, String directive) {
      int y = "UP".equals(directive) ? -1 : "DOWN".equals(directive) ? 1 : 0;
      int x = "RIGHT".equals(directive) ? 1 : "LEFT".equals(directive) ? -1 : 0;
      return new Point(p.x + x, p.y + y);
    }

    private static Point getParentPoint(Point p, String parentDirective) {
      int y = "UP".equals(parentDirective) ? 1 : "DOWN".equals(parentDirective) ? -1 : 0;
      int x = "RIGHT".equals(parentDirective) ? -1 : "LEFT".equals(parentDirective) ? 1 : 0;
      return new Point(p.x + x, p.y + y);
    }

    private static void log(String s) {
      System.err.println(s);
    }

    public static class Tree {
      Map<Point, Node> reachablePoints = new HashMap<>();

      Tree(Point start, int depth) {
        Queue<QueueInfo> queue = new LinkedList<>();
        queue.add(new QueueInfo("", start, 0));
        while (!queue.isEmpty()) {
          QueueInfo qi = queue.poll();
          if (qi.depth > depth)
            return;

          if (reachablePoints.containsKey(qi.p))
            continue;

          reachablePoints.put(qi.p, new Node(qi.parentMove, qi.depth));
          String tile = getTile(qi.p);
          if (qi.p.y > 0 && tile.charAt(0) == '1' && tiles[qi.p.x][qi.p.y - 1].charAt(2) == '1')
            queue.add(new QueueInfo("UP", new Point(qi.p.x, qi.p.y -1), qi.depth + 1));
          if (qi.p.x < 6 && tile.charAt(1) == '1' && tiles[qi.p.x + 1][qi.p.y].charAt(3) == '1')
            queue.add(new QueueInfo("RIGHT", new Point(qi.p.x + 1, qi.p.y), qi.depth + 1));
          if (qi.p.y < 6 && tile.charAt(2) == '1' && tiles[qi.p.x][qi.p.y + 1].charAt(0) == '1')
            queue.add(new QueueInfo("DOWN", new Point(qi.p.x, qi.p.y + 1), qi.depth + 1));
          if (qi.p.x > 0 && tile.charAt(3) == '1' && tiles[qi.p.x - 1][qi.p.y].charAt(1) == '1')
            queue.add(new QueueInfo("LEFT", new Point(qi.p.x - 1, qi.p.y), qi.depth + 1));
        }
      }

      Point findClosestReachable(List<Point> points) {
        Point minPoint = null;
        for (Point reachable : reachablePoints.keySet())
          for (Point point : points)
            if (minPoint == null || taxiDistance(point, reachable) < taxiDistance(point, minPoint))
              minPoint = reachable;
        return minPoint;
      }

      String getPath(Point p) {
        return reachablePoints.containsKey(p) ? getPath(p, "") : null;
      }

      String getPath(Point p, String path) {
        Node node = reachablePoints.get(p);
        return "".equals(node.parentMove) ? path : getPath(getParentPoint(p, node.parentMove), node.parentMove + " " + path);
      }
    }

    private static boolean areTilesCompatible(Point p1, Point p2){
      if (taxiDistance(p1, p2) != 1)
        return false;
      boolean verticallyAligned = abs(p1.x - p2.x) != 0;
      boolean p1IsFirst = verticallyAligned ? p1.x < p2.x : p1.y < p2.y;
      return verticallyAligned ? (p1IsFirst ? (getTile(p1).charAt(Direction.RIGHT.ordinal()) == '1' && getTile(p2).charAt(Direction.LEFT.ordinal()) == '1') :
                                              (getTile(p1).charAt(Direction.LEFT.ordinal()) == '1' && getTile(p2).charAt(Direction.RIGHT.ordinal()) == '1')) :
                                (p1IsFirst ? (getTile(p1).charAt(Direction.DOWN.ordinal()) == '1' && getTile(p2).charAt(Direction.UP.ordinal()) == '1') :
                                              (getTile(p1).charAt(Direction.UP.ordinal()) == '1' && getTile(p2).charAt(Direction.DOWN.ordinal()) == '1'));
    }

    public enum Direction {
      UP, RIGHT, DOWN, LEFT;
    }

    public static class QueueInfo {
      String parentMove;
      Point p;
      int depth;

      QueueInfo(String parentMove, Point p, int depth){
        this.parentMove = parentMove;
        this.p = p;
        this.depth = depth;
      }
    }

    public static class Node {
      int distanceFromRoot;
      String parentMove;

      Node (String parentMove, int distanceFromRoot) {
        this.parentMove = parentMove;
        this.distanceFromRoot = distanceFromRoot;
      }

    }

    public static class Point {
      int x;
      int y;

      Point(int x, int y) {
        this.x = x;
        this.y = y;
      }

      @Override
      public boolean equals(Object o) {
        Point p = (Point) o;
        return this.x == p.x && this.y == p.y;
      }

      @Override
      public int hashCode(){
        return this.x * 13 + this.y * 19;
      }

    }
}
