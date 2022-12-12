package day09;

import java.util.ArrayList;
import java.util.List;

import solver.AdventOfCodeSolver;

public class Day09 extends AdventOfCodeSolver {

	private class Move {

		final char direction;
		final int distance;

		Move(String s) {

			String[] strings = s.split(" ");

			direction = strings[0].charAt(0);
			distance  = Integer.valueOf(strings[1]);
		}
	}

	private class Position {

		int row;
		int col;

		Position(int row, int col) {
			this.row = row;
			this.col = col;
		}

		void moveToward(Position target) {

			// If within one, then do not move
			if (       Math.abs(row - target.row) <= 1
					&& Math.abs(col - target.col) <= 1) {
				return;
			}

			if (row == target.row) {

				// Move towards the target
				if (target.col > col) {
					col++;
				}
				else {
					col--;
				}

			}
			else if (col == target.col) {

				// Move towards the target
				if (target.row > row) {
					row++;
				}
				else {
					row--;
				}

			}
			else {

				if (target.row > row) {

					row++;

					if (target.col > col) {
						col++;
					}
					else {
						col--;
					}
				}
				else {

					row--;

					if (target.col > col) {
						col++;
					}
					else {
						col--;
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		debug = true;
		new Day09().solve();
	}

	/** List of objects created by parsing the input */
	private List<Move> moves = new ArrayList<>();

	@Override
	public void readInput() {

		// Read the input until a blank line is encountered
		while (true) {

			String input = scanner.nextLine();

			if (input.isBlank()) {
				break;
			}

			moves.add(new Move(input));
		}
	}

	@Override
	public void solvePart1() {

		int mapSize = 400;

		int total = 0;

		char[][] visited = new char[mapSize][mapSize];

		for (char[] row: visited) {
			for (int col=0; col<row.length; col++) {
				row[col] = ' ';
			}
		}

		Position head = new Position(mapSize/2, mapSize/2);
		Position tail = new Position(mapSize/2, mapSize/2);

		for (Move move: moves) {

			for (int i=0; i<move.distance; i++) {

				switch (move.direction) {

				case 'U':
					head.row--;
					break;

				case 'D':
					head.row++;
					break;

				case 'L':
					head.col--;
					break;

				case 'R':
					head.col++;
					break;

				default:
					System.out.println("Unknown direction " + move.direction);
				}

				tail.moveToward(head);
				visited[tail.row][tail.col] = '*';
			}
		}

		for (char[] row: visited) {

			for (char c: row) {
				if (c == '*') {
					total++;
				}
			}
			if (debug) {
				System.out.println(String.valueOf(row));
			}
		}

		System.out.println("Visited " + total);
	}

	@Override
	public void solvePart2() {

		int mapSize = 400;

		int total = 0;

		char[][] visited = new char[mapSize][mapSize];

		for (char[] row: visited) {
			for (int col=0; col<row.length; col++) {
				row[col] = ' ';
			}
		}

		List<Position> knots = new ArrayList<>();
		for (int i = 0; i<10; i++) {
			knots.add(new Position(mapSize/2, mapSize/2));
		}

		Position head = knots.get(0);
		Position tail = knots.get(9);

		for (Move move: moves) {

			for (int i=0; i<move.distance; i++) {

				switch (move.direction) {

				case 'U':
					head.row--;
					break;

				case 'D':
					head.row++;
					break;

				case 'L':
					head.col--;
					break;

				case 'R':
					head.col++;
					break;

				default:
					System.out.println("Unknown direction " + move.direction);
				}

				for (int knot=1; knot<10; knot++) {
					knots.get(knot).moveToward(knots.get(knot-1));
				}
				visited[tail.row][tail.col] = '*';
			}
		}

		for (char[] row: visited) {

			for (char c: row) {
				if (c == '*') {
					total++;
				}
			}
			if (debug) {
				System.out.println(String.valueOf(row));
			}
		}

		System.out.println("Visited " + total);
	}

}
