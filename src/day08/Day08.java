package day08;

import java.util.ArrayList;
import java.util.List;

import solver.AdventOfCodeSolver;

public class Day08 extends AdventOfCodeSolver {

	public static void main(String[] args) {
		new Day08().solve();

		// debug = true;
	}

	/** List of objects created by parsing the input */
	List<List<Integer>> treeHeights = new ArrayList<>();

	char[][] visibility;

	private int rows = 0, cols = 0;

	@Override
	public void readInput() {

		// Read the input until a blank line is encountered
		while (true) {

			String input = scanner.nextLine();

			if (input.isBlank()) {
				break;
			}

			List<Integer> row = new ArrayList<>();
			for (char c: input.toCharArray()) {
				row.add(c - '0');
			}
			treeHeights.add(row);
		}

		rows = treeHeights.size();
		cols = treeHeights.get(0).size();

		System.out.println("rows = " + rows);
		System.out.println("cols = " + cols);
	}

	@Override
	public void solvePart1() {

		visibility = new char[rows][cols];

		for (int row=0; row < rows; row++) {

			visibility[row] = new char[cols];

			for (int col=0; col < cols; col++) {
				visibility[row][col] = '.';
			}
		}

		int total = 0;

		int max = -1;

		// Look for visibility in each row.
		for (int row = 0; row < rows; row++) {

			List<Integer> rowHeights = treeHeights.get(row);

			// Scan from left
			max = -1;
			for (int col=0; col < cols; col++) {

				int treeHeight = rowHeights.get(col);

				if (treeHeight > max) {
					visibility[row][col] = 'v';
					max = treeHeight;
				}
			}

			// Scan from right
			max = -1;
			for (int col=cols-1; col >= 0; col--) {

				int treeHeight = rowHeights.get(col);

				if (treeHeight > max) {
					visibility[row][col] = 'v';
					max = treeHeight;
				}
			}
		}

		// Look for visibility in each col.
		for (int col=0; col < cols; col++) {

			// Scan from top
			max = -1;
			for (int row = 0; row < rows; row++) {

				List<Integer> rowHeights = treeHeights.get(row);

				int treeHeight = rowHeights.get(col);

				if (treeHeight > max) {
					visibility[row][col] = 'v';
					max = treeHeight;
				}
			}

			// Scan from bottom
			max = -1;
			for (int row = rows-1; row >= 0; row--) {

				List<Integer> rowHeights = treeHeights.get(row);

				int treeHeight = rowHeights.get(col);

				if (treeHeight > max) {
					visibility[row][col] = 'v';
					max = treeHeight;
				}
			}
		}

		if (debug) {
			// Print the visibility map
			for (int row = 0; row < rows; row++) {

				for (int col = 0; col < cols; col++) {

					System.out.print(visibility[row][col]);

					if (visibility[row][col] == 'v') {
						total++;
					}

				}
				System.out.println();
			}
		}

		System.out.println("Total visible " + total);
	}

	@Override
	public void solvePart2() {

		int maxScenicScore = 0;

		for (int row=1; row<rows-1; row++) {
			for (int col=1; col<cols-1; col++) {

				// Calculate the viewing distance in each direction.
				int treeHeight = treeHeights.get(row).get(col);

				// Up
				int up = 0;
				for (int r=row-1; r>=0; r--) {
					up++;
					if (treeHeights.get(r).get(col) >= treeHeight) {
						break;
					}
				}

				// Down
				int down = 0;
				for (int r=row+1; r<rows; r++) {
					down++;
					if (treeHeights.get(r).get(col) >= treeHeight) {
						break;
					}
				}

				// Left
				int left = 0;
				for (int c=col-1; c>=0; c--) {
					left++;
					if (treeHeights.get(row).get(c) >= treeHeight) {
						break;
					}
				}

				// Right
				int right = 0;
				for (int c=col+1; c<cols; c++) {
					right++;
					if (treeHeights.get(row).get(c) >= treeHeight) {
						break;
					}
				}

				int scenicScore = up * down * left * right;

				maxScenicScore = Math.max(maxScenicScore, scenicScore);
			}
		}

		System.out.println("Max scenic score " + maxScenicScore);
	}

}
