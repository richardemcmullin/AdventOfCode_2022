package day14;

import java.util.ArrayList;
import java.util.List;

import solver.AdventOfCodeSolver;

public class Day14 extends AdventOfCodeSolver {

	private class Point {

		int x, y;

		Point(String coordinateString) {

			String[] xyStrings = coordinateString.split(",");
			x = Integer.valueOf(xyStrings[0]);
			y = Integer.valueOf(xyStrings[1]);
		}
	}

	private class RockWall {

		List<Point> wallPoints = new ArrayList<>();

		RockWall(String s) {

			String[] coordinateStrings = s.split(" -> ");

			for (String coordinateString : coordinateStrings) {
				wallPoints.add(new Point(coordinateString));
			}
		}
	}

	public static void main(String[] args) {
		//debug = true;
		new Day14().solve();
	}

	/** List of objects created by parsing the input */
	private List<RockWall> rockWalls = new ArrayList<>();

	int minRockCol = Integer.MAX_VALUE;
	int maxRockCol = 0, maxRockRow = 0;

	@Override
	public void readInput() {

		// Read the input until a blank line is encountered
		while (true) {

			String input = scanner.nextLine();

			if (input.isBlank()) {
				break;
			}

			rockWalls.add(new RockWall(input));
		}

		for (RockWall rockWall:rockWalls) {

			for (Point p: rockWall.wallPoints) {
				minRockCol = Math.min(minRockCol, p.x);
				maxRockCol = Math.max(maxRockCol, p.x);
				maxRockRow = Math.max(maxRockRow, p.y);
			}
		}

		if (debug) {
			System.out.println("X range: " + minRockCol + ".." + maxRockCol);
			System.out.println("Y range: 0.." + maxRockRow);
		}
	}

	@Override
	public void solvePart1() {

		// Initialize the map
		char[][] map = new char[maxRockRow+1][maxRockCol-minRockCol+1];

		clearMap(map);

		// calculate the feed column
		int feedCol = 500-minRockCol;

		map[0][feedCol] = '+';

		plotWalls(feedCol, map);

		if (debug) {
			printMap(map);
		}

		int sand = 0;

		while (true) {

			// Drop a sand into the map.
			if (!dropSand(feedCol, map)) {
				break;
			}
			sand++;
		}

		if (debug) {
			printMap(map);
		}

		System.out.println("Total " + sand);
	}

	@Override
	public void solvePart2() {

		int floor = maxRockRow+2;
		int minX  = 500 - floor - 1;
		int maxX  = 500 + floor + 1;

		// Initialize the map
		char[][] map = new char[floor+1][maxX-minX+1];

		clearMap(map);

		// calculate the feed column
		int feedCol = 500-minX;

		map[0][feedCol] = '+';

		plotWalls(feedCol, map);

		// Draw the floor
		for (int col=0; col<map[0].length; col++) {
			map[floor][col] = '#';
		}

		if (debug) {
			printMap(map);
		}

		int sand = 0;

		while (true) {

			if (!dropSand(feedCol, map)) {
				break;
			}
			sand++;
		}

		if (debug) {
			printMap(map);
		}

		System.out.println("Total " + sand);
	}

	private void clearMap(char[][] map) {

		for (int row = 0; row < map.length; row++) {
			for (int col=0; col<map[0].length; col++) {
				map[row][col] = '.';
			}
		}
	}

	/**
	 * Drop a sand into the map at the specified column
	 * @return {@code true} if a sand was placed, {@code false} otherwise.
	 */
	private boolean dropSand(int col, char[][] map) {

		int row = 0;

		// If the current starting point is already filled, then
		// a new sand cannot be placed
		if (map[row][col] == 'o') {
			return false;
		}

		while (true) {

			// Straight
			// if there are no more rows, then fall into the abyss
			if (row == map.length-1) {
				return false;
			}

			if (map[row+1][col] == '.') {
				row++;
				continue;
			}

			// Left
			// If off the left of the map, then abyss
			if (col == 0) {
				return false;
			}

			if (map[row+1][col-1] == '.') {
				row++;
				col--;
				continue;
			}

			// Right
			// If off the right of the map, then abyss
			if (col == map[0].length-1) {
				return false;
			}

			if (map[row+1][col+1] == '.') {
				row++;
				col++;
				continue;
			}

			// If there is no spot to the left or right,
			// then place the sand here
			map[row][col] = 'o';

			return true;
		}
	}

	private void plotWalls(int feedCol, char[][] map) {

		// The feed column is column 500.
		int colOffset = feedCol - 500;

		for (RockWall rockWall:rockWalls) {

			Point prevPoint = null;

			for (Point p: rockWall.wallPoints) {

				if (prevPoint == null) {
					prevPoint = p;
				}

				for (int col=Math.min(p.x, prevPoint.x)+colOffset; col <= Math.max(p.x, prevPoint.x)+colOffset; col++) {
					for (int row=Math.min(p.y, prevPoint.y); row <= Math.max(p.y, prevPoint.y); row++) {
						map[row][col] = '#';
					}
				}

				prevPoint = p;
			}
		}

	}


	private void printMap(char[][] m) {

		for (int row = 0; row < m.length; row++) {
			for (int col=0; col<m[0].length; col++) {
				System.out.print(m[row][col]);
			}
			System.out.println();
		}
	}

}
