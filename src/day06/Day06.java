package day06;

import java.util.ArrayList;
import java.util.List;

import solver.AdventOfCodeSolver;

public class Day06 extends AdventOfCodeSolver {

	public static void main(String[] args) {
		new Day06().solve();
	}

	List<Character> signal;

	@Override
	public void readInput() {

		// Read the input until a blank line is encountered
		while (true) {

			String input = scanner.nextLine();

			if (input.isBlank()) {
				break;
			}

			// Convert the input to a char array
			signal = new ArrayList<>(input.length());

			for (char c: input.toCharArray()) {
				signal.add(c);
			}
		}
	}

	@Override
	public void solvePart1() {

		int markerEnd = 0;

		for (int i=0; i<signal.size(); i++) {

			if (isUniqueSignal(signal, i, i+4)) {
				markerEnd = i+4;
				break;
			}
		}

		System.out.println("First marker after char " + markerEnd);
	}

	@Override
	public void solvePart2() {

		int startOfMessage = 0;

		for (int i=0; i<signal.size()-14; i++) {

			if (isUniqueSignal(signal, i, i+14)) {
				startOfMessage = i+14;
				break;
			}
		}

		System.out.println("First start-of-message after char " + startOfMessage);
	}

	private boolean isUniqueSignal(List<Character> signal, int start, int end) {

		while (start < end-1) {

			char c = signal.get(start);

			start++;

			if (signal.subList(start, end).contains(c)) {
				return false;
			}
		}
		return true;
	}

}
