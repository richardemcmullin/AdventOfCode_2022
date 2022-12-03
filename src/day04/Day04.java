package day04;

import java.util.ArrayList;
import java.util.List;

import solver.AdventOfCodeSolver;

public class Day04 extends AdventOfCodeSolver {

	private class DayInnerClass {

		DayInnerClass(String s) {
		}
	}

	public static void main(String[] args) {
		new Day04().solve();
	}

	private List<DayInnerClass> dayClasses = new ArrayList<>();

	@Override
	public void readInput() {

		// Read the input until a blank line is encountered
		while (true) {

			String input = scanner.nextLine();

			if (input.isBlank()) {
				break;
			}

			dayClasses.add(new DayInnerClass(input));
		}
	}

	@Override
	public void solvePart1() {

		int total = 0;

		for (DayInnerClass dayClass: dayClasses) {

		}
		System.out.println("Total " + total);
	}

	@Override
	public void solvePart2() {

		System.out.println("Not yet implemented");
	}

}
