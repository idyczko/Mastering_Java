import java.util.*;

public class Robot {


	public static void main(String[] args) {
		 boolean[][] grid = {{true, true, true, true}, {true, true, true, true}, {true, false, false, true}, {true, false, false, true}};
		 int start_x = 0;
		 int start_y = 0;
		 int finish_x = grid.length - 1;
		 int finish_y = grid[0].length - 1; // we assume the matrix is non empty and all rows have the same lenght
		 findPath(grid, start_x, start_y, new ArrayList<String>());
	}

	private static boolean findPath(boolean[][] grid, int agent_x, int agent_y, List<String> path) {

		if (!grid[agent_x][agent_y])
			return false;

		if (agent_x == (grid.length - 1) && agent_y == (grid[0].length - 1)) {
			path.forEach(dir -> System.out.print(dir + " "));
			return true;
		}

		if (agent_x < grid.length - 1) {
			agent_x++;
			path.add("RIGHT");

			if (findPath(grid, agent_x, agent_y, path))
				return true;

			agent_x--;
			path.remove(path.size() - 1);
		}

		if (agent_y < grid[0].length - 1) {
			agent_y++;
			path.add("DOWN");

			if (findPath(grid, agent_x, agent_y, path))
				return true;

			agent_y--;
			path.remove(path.size() - 1);
		}

		return false;
	}
	
}
