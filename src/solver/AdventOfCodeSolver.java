package solver;

import java.util.Scanner;

public abstract class AdventOfCodeSolver {

	public Scanner scanner = new Scanner(System.in);

	public abstract void readInput();

	public void solve() {

		readInput();

		System.out.println("\nSolve Part 1");

		long start = System.currentTimeMillis();

		solvePart1();

		long end = System.currentTimeMillis();

		System.out.println("solved in " + (end-start) + "ms");


		System.out.println("\n\nSolve Part 2");

		start = System.currentTimeMillis();

		solvePart2();

		end = System.currentTimeMillis();

		System.out.println("solved in " + (end-start) + "ms");
	}

	public abstract void solvePart1();

	public abstract void solvePart2();

}
