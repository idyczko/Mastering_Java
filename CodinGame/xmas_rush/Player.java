import java.util.*;
import java.io.*;
import java.util.stream.*;
import java.math.*;
import static java.lang.Math.*;

/**
 * Help the Christmas elves fetch presents in a magical labyrinth!
 **/
class Player {
    public static String[][] tiles = new String[7][7];
    public static Map<String, Point> myItems = new HashMap<>();
    public static List<String> activeQuests = new ArrayList<>();
    public static List<Point> activeItems = new ArrayList<>();
    public static Point p1 = new Point(0,0);
    public static Point p2 = new Point(0,0);
    public static Tree map;
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int [][] items = new int[2][2];
        int [][] players = new int[2][2];
        String pushMove = "";
        String move = "";

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
                String playerTile = in.next();
            }
            int numItems = in.nextInt(); // the total number of items available on board and on player tiles
            for (int i = 0; i < numItems; i++) {
                String itemName = in.next();
                int itemX = in.nextInt();
                int itemY = in.nextInt();
                int itemPlId = in.nextInt();

                if (itemPlId == 0)
                    myItems.put(itemName, new Point(itemX, itemY));
            }
            int numQuests = in.nextInt(); // the total number of revealed quests for both players
            for (int i = 0; i < numQuests; i++) {
                String questItemName = in.next();
                int questPlayerId = in.nextInt();
                if (questPlayerId == 0)
                    activeQuests.add(questItemName);
            }

            activeItems = activeQuests.stream().map(quest -> myItems.get(quest)).collect(Collectors.toList());
            map = new Tree(p1, 20);

            //System.err.println("Paths to active items: ");
            //activeItems.forEach(i -> System.err.println(map.getPath(i)));
            int[] targetItem = new int[] {activeItems.get(0).x, activeItems.get(0).y};
            System.err.println("Target item: " + targetItem[0] + " " + targetItem[1]);
            if(turnType == 0)
                pushMove = getToTheDockingPoint(tiles, players, targetItem);
            else
                move = calculateMove();

            System.out.println(turnType == 0 ? pushMove : ("".equals(move) ? "PASS" : ("MOVE " + move.trim())));

            myItems.clear();
            activeQuests.clear();
            activeItems.clear();
        }
    }

    private static String calculateMove() {
      String move = "";
      Point start = p1;
      int depth = 20;
      while (true) {
        Tree map = new Tree(start, depth);
        String tmpmove = null;
        Point point = null;
        for (Point item : activeItems) {
          String path = map.getPath(item);
          if (path == null)
            continue;
          if (tmpmove == null || path.split(" ").length < tmpmove.split(" ").length) {
            tmpmove = path;
            point = item;
          }
        }
        System.err.println("Move: " + tmpmove);
        if (tmpmove == null)
          return move;
        activeItems.remove(point);
        move += tmpmove;
        depth -= map.reachablePoints.get(point).distanceFromRoot;
        start = point;
      }

    }

    private static String getToTheDockingPoint(String[][] tiles, int[][] players, int[] item) {
        if (item[0] < 0)
            return movePlayerTowardsCenter(players);
        String playerTile = tiles[players[0][0]][players[0][1]];
        String itemTile = tiles[item[0]][item[1]];
        List<Integer[]> docks = getDockingPoints(tiles, players[0][0], players[0][1], item[0], item[1]);
        if (docks.isEmpty())
            return movePlayerTowardsCenter(players);
        for (Integer[] dock : docks)
            System.err.println("Found docking point: " + dock[0] + " " + dock[1]);
        Integer[] closestDock = findClosestDock(docks, item[0], item[1]);
        System.err.println("Closest docking point: " + closestDock[0] + " " + closestDock[1]);
        return calculateDockMove(closestDock, players, item);
    }

    private static String movePlayerTowardsCenter(int[][] players) {
        int xDiff = players[0][0] - 3;
        int yDiff = players[0][1] - 3;
        boolean goHorizontal = abs(xDiff) > abs(yDiff);
        return "PUSH " + (goHorizontal ? (players[0][1] + (xDiff > 0 ? " LEFT" : " RIGHT")) :
            (players[0][0] + (yDiff > 0 ? " UP" : " DOWN")));
    }

    private static String calculateDockMove(Integer[] closestDock, int[][] players, int[] item) {
      if (distance(item[0], item[1], closestDock[0], closestDock[1]) == 0)
        return "PUSH " + ((closestDock[0] + 3) % 7) + " DOWN";
      if (item[0] == players[0][0])
        return escapeColumn(closestDock, item);
      if (item[1] == players[0][1])
        return escapeRow(closestDock, item);

      int xDiff = closestDock[0] - item[0];
      int yDiff = closestDock[1] - item[1];
      boolean goHorizontal = abs(xDiff) != abs(yDiff) ? abs(xDiff) > abs(yDiff) :
            (abs(item[1] - players[0][1]) == 1);
      return "PUSH " + (goHorizontal ? (item[1] + (xDiff > 0 ? " RIGHT" : " LEFT")) :
        (item[0] + (yDiff > 0 ? " DOWN" : " UP")));
    }

    private static String escapeColumn(Integer[] closestDock, int[] item) {
      if (closestDock[0] == item[0])
        return "PUSH " + item[1] + (closestDock[0] > 3 ? " LEFT" : " RIGHT");
      return "PUSH " + item[1] + (closestDock[0] > item[0] ? " RIGHT" : " LEFT");
    }

    private static String escapeRow(Integer[] closestDock, int[] item) {
      if (closestDock[1] == item[1])
        return "PUSH " + item[0] + (closestDock[1] > 3 ? " UP" : " DOWN");
      return "PUSH " + item[0] + (closestDock[1] > item[1] ? " DOWN" : " UP");
    }

    private static Integer[] findClosestDock(List<Integer[]> docks, int x, int y) {
      Integer[] closestDock = null;
      for (Integer[] dock : docks)
        if (closestDock == null || distance(dock[0], dock[1], x, y) < distance(closestDock[0], closestDock[1], x, y))
          closestDock = dock;
      return closestDock;
    }

    private static int distance(int x1, int y1, int x2, int y2) {
      return abs(x1 - x2) + abs(y1 - y2);
    }

    private static String calculateMove(String[][] tiles, int[][] players, int[] item) {
        int [][] visited = new int[7][7];
        return calculateMove(tiles, players, item, players[0][0], players[0][1], "MOVE", visited);
    }

    private static List<Integer[]> getDockingPoints(String[][] tiles, int px, int py, int tx, int ty) {
        List<Integer[]> docks = new ArrayList<>();
        String playerTile = tiles[px][py];
        String itemTile = tiles[tx][ty];
        if (py > 0 && playerTile.charAt(0) == '1' && itemTile.charAt(2) == '1')
          docks.add(new Integer[] {px, py - 1});
        if (py < 6 && playerTile.charAt(2) == '1' && itemTile.charAt(0) == '1')
          docks.add(new Integer[] {px, py + 1});
        if (px > 0 && playerTile.charAt(3) == '1' && itemTile.charAt(1) == '1')
          docks.add(new Integer[] {px - 1, py});
        if (px < 6 && playerTile.charAt(1) == '1' && itemTile.charAt(3) == '1')
          docks.add(new Integer[] {px + 1, py});
        return docks;
    }

    private static String keepItemClose(int[][] players, int[] item) {

        if (item[0] < 0)
            return "PUSH " + ((players[0][0] + 1)%7) + " DOWN";
        int xDistance = players[0][0] - item[0];
        int yDistance = players[0][1] - item[1];
        if (xDistance == 0 && abs(yDistance) != 1)
            return "PUSH " + item[1] + " LEFT";
        if (yDistance == 0 && abs(xDistance) != 1)
            return "PUSH " + item[1] + " DOWN";
        if (abs(xDistance) > 1)
            return "PUSH " + item[1] + " " + (xDistance < 0 ? "LEFT" : "RIGHT");
        if (abs(yDistance) > 1)
            return "PUSH " + item[0] + " " + (yDistance < 0 ? "UP" : "DOWN");

        return "PUSH " + players[1][0] + " DOWN";
    }

    private static String calculateMove(String[][] tiles, int[][] players, int[] item,
                                        int x, int y, String parent, int[][] visited) {
        if (visited[x][y] == 1)
            return null;

        if (!parent.equals("MOVE") && !parentReachable(parent, tiles[x][y]))
            return null;

        visited[x][y] = 1;
        System.err.println(x + " " + y + " " + tiles[x][y]);

        if (item[0] == x && item[1] == y)
            return parent;

        List<String> paths = new ArrayList<>();
        paths.add(tiles[x][y].charAt(3) == '1' ? ("RIGHT".equals(parent) ? null :
            (x == 0 ? null : calculateMove(tiles, players, item, x - 1, y, "LEFT", visited))) : null);
        paths.add(tiles[x][y].charAt(1) == '1' ? ("LEFT".equals(parent) ? null :
            (x == 6 ? null : calculateMove(tiles, players, item, x + 1, y, "RIGHT", visited))) : null);
        paths.add(tiles[x][y].charAt(0) == '1' ? ("DOWN".equals(parent) ? null :
            (y == 0 ? null : calculateMove(tiles, players, item, x, y - 1, "UP", visited))) : null);
        paths.add(tiles[x][y].charAt(2) == '1' ? ("UP".equals(parent) ? null :
            (y == 6 ? null : calculateMove(tiles, players, item, x, y + 1, "DOWN", visited))) : null);
        String result = null;
        for (String path : paths) {
          if (path != null)
            if (result == null || path.length() < result.length())
              result = path;
        }

        return result != null ? (parent + " " + result) : null;
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

      String getPath(Point p) {
        return reachablePoints.containsKey(p) ? getPath(p, "") : null;
      }
      String getPath(Point p, String path) {
        Node node = reachablePoints.get(p);
        return "".equals(node.parentMove) ? path : getPath(getParentPoint(p, node.parentMove), node.parentMove + " " + path);
      }
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

      Point(int[] point) {
        this.x = point[0];
        this.y = point[1];
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
