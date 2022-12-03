package templateDay;

import java.util.ArrayList;
import java.util.List;

import solver.AdventOfCodeSolver;

public class TemplateDay extends AdventOfCodeSolver {

	private class DayInnerClass {

		DayInnerClass(String s) {

			// Parse the input string for one line into some object
		}
	}

	public static void main(String[] args) {
		new TemplateDay().solve();
	}

	/** List of objects created by parsing the input */
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
