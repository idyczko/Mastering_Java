import java.util.*;
import java.io.*;
import java.math.*;
import static java.lang.Math.*;

/**
 * Help the Christmas elves fetch presents in a magical labyrinth!
 **/
class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int [][] items = new int[2][2];
        int [][] players = new int[2][2];
        String[][] tiles = new String[7][7];
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
                players[i][0] = playerX;
                players[i][1] = playerY;
                String playerTile = in.next();
            }
            int numItems = in.nextInt(); // the total number of items available on board and on player tiles
            for (int i = 0; i < numItems; i++) {
                String itemName = in.next();
                int itemX = in.nextInt();
                int itemY = in.nextInt();
                int itemPlId = in.nextInt();
                items[itemPlId][0] = itemX;
                items[itemPlId][1] = itemY;
            }
            int numQuests = in.nextInt(); // the total number of revealed quests for both players
            for (int i = 0; i < numQuests; i++) {
                String questItemName = in.next();
                int questPlayerId = in.nextInt();
            }

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");
            if(turnType == 0)
                pushMove = getToTheDockingPoint(tiles, players, items);
            else
                move = calculateMove(tiles, players, items);
            System.out.println(turnType == 0 ? pushMove : (move == null ? "PASS" : move)); // PUSH <id> <direction> | MOVE <direction> | PASS
        }
    }

    private static String getToTheDockingPoint(String[][] tiles, int[][] players, int[][] items) {
        String playerTile = tiles[players[0][0]][players[0][1]];
        String itemTile = tiles[items[0][0]][items[0][1]];
        List<Integer[]> docks = getDockingPoints(tiles, players[0][0], players[0][1], items[0][0], items[0][1]);
        if (docks.isEmpty())
            return movePlayerTowardsCenter(players);
        for (Integer[] dock : docks)
            System.err.println("Found docking point: " + dock[0] + " " + dock[1]);
        Integer[] closestDock = findClosestDock(docks, items[0][0], items[0][1]);
        System.err.println("Closest docking point: " + closestDock[0] + " " + closestDock[1]);
        return calculateDockMove(closestDock, players, items);
    }

    private static String movePlayerTowardsCenter(int[][] players) {
        int xDiff = players[0][0] - 3;
        int yDiff = players[0][1] - 3;
        boolean goHorizontal = abs(xDiff) > abs(yDiff);
        return "PUSH " + (goHorizontal ? (players[0][1] + (xDiff > 0 ? " LEFT" : " RIGHT")) :
            (players[0][0] + (yDiff > 0 ? " UP" : " DOWN")));
    }

    private static String calculateDockMove(Integer[] closestDock, int[][] players, int[][] items) {
      if (distance(items[0][0], items[0][1], closestDock[0], closestDock[1]) == 0)
        return "PUSH " + ((closestDock[0] + 3) % 7) + " DOWN";
      if (items[0][0] == players[0][0])
        return escapeColumn(closestDock, items);
      if (items[0][1] == players[0][1])
        return escapeRow(closestDock, items);

      int xDiff = closestDock[0] - items[0][0];
      int yDiff = closestDock[1] - items[0][1];
      boolean goHorizontal = abs(xDiff) != abs(yDiff) ? abs(xDiff) > abs(yDiff) :
            (abs(items[0][1] - players[0][1]) == 1);
      return "PUSH " + (goHorizontal ? (items[0][1] + (xDiff > 0 ? " RIGHT" : " LEFT")) :
        (items[0][0] + (yDiff > 0 ? " DOWN" : " UP")));
    }

    private static String escapeColumn(Integer[] closestDock, int[][] items) {
      if (closestDock[0] == items[0][0])
        return "PUSH " + items[0][1] + (closestDock[0] > 3 ? " LEFT" : " RIGHT");
      return "PUSH " + items[0][1] + (closestDock[0] > items[0][0] ? " RIGHT" : " LEFT");
    }

    private static String escapeRow(Integer[] closestDock, int[][] items) {
      if (closestDock[1] == items[0][1])
        return "PUSH " + items[0][0] + (closestDock[1] > 3 ? " UP" : " DOWN");
      return "PUSH " + items[0][0] + (closestDock[1] > items[0][1] ? " DOWN" : " UP");
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

    private static String keepItemClose(int[][] players, int[][] items) {

        if (items[0][0] < 0)
            return "PUSH " + ((players[0][0] + 1)%7) + " DOWN";
        int xDistance = players[0][0] - items[0][0];
        int yDistance = players[0][1] - items[0][1];
        if (xDistance == 0 && abs(yDistance) != 1)
            return "PUSH " + items[0][1] + " LEFT";
        if (yDistance == 0 && abs(xDistance) != 1)
            return "PUSH " + items[0][1] + " DOWN";
        if (abs(xDistance) > 1)
            return "PUSH " + items[0][1] + " " + (xDistance < 0 ? "LEFT" : "RIGHT");
        if (abs(yDistance) > 1)
            return "PUSH " + items[0][0] + " " + (yDistance < 0 ? "UP" : "DOWN");

        return "PUSH " + players[1][0] + " DOWN";
    }

    private static String calculateMove(String[][] tiles, int[][] players, int[][] items) {
        int [][] visited = new int[7][7];
        return calculateMove(tiles, players, items, players[0][0], players[0][1], "MOVE", visited);
    }

    private static String calculateMove(String[][] tiles, int[][] players, int[][] items,
                                        int x, int y, String parent, int[][] visited) {
        if (visited[x][y] == 1)
            return null;

        if (!parent.equals("MOVE") && parentUnreachable(parent, tiles[x][y]))
            return null;

        visited[x][y] = 1;
        System.err.println(x + " " + y + " " + tiles[x][y]);

        if (items[0][0] == x && items[0][1] == y)
            return parent;

        List<String> paths = new ArrayList<>();
        paths.add(tiles[x][y].charAt(3) == '1' ? ("RIGHT".equals(parent) ? null :
            (x == 0 ? null : calculateMove(tiles, players, items, x - 1, y, "LEFT", visited))) : null);
        paths.add(tiles[x][y].charAt(1) == '1' ? ("LEFT".equals(parent) ? null :
            (x == 6 ? null : calculateMove(tiles, players, items, x + 1, y, "RIGHT", visited))) : null);
        paths.add(tiles[x][y].charAt(0) == '1' ? ("DOWN".equals(parent) ? null :
            (y == 0 ? null : calculateMove(tiles, players, items, x, y - 1, "UP", visited))) : null);
        paths.add(tiles[x][y].charAt(2) == '1' ? ("UP".equals(parent) ? null :
            (y == 6 ? null : calculateMove(tiles, players, items, x, y + 1, "DOWN", visited))) : null);
        String result = null;
        for (String path : paths) {
          if (path != null)
            if (result == null || path.length() < result.length())
              result = path;
        }

        return result != null ? (parent + " " + result) : null;
    }

    public static boolean parentUnreachable(String parent, String tile) {
      return parent.equals("DOWN") && tile.charAt(0) == '0'||
          parent.equals("LEFT") && tile.charAt(1) == '0' ||
          parent.equals("UP") && tile.charAt(2) == '0' ||
          parent.equals("RIGHT") && tile.charAt(3) == '0';
    }
}
