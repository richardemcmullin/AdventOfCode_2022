package day02;

import java.util.ArrayList;
import java.util.List;

import solver.AdventOfCodeSolver;

public class Day02 extends AdventOfCodeSolver {

	private class Round {

		final char elfChoice;
		final char yourChoice;

		Round(String s) {
			elfChoice = s.charAt(0);
			yourChoice = s.charAt(2);
		}
	}

	public static void main(String[] args) {
		new Day02().solve();
	}

	private List<Round> rounds = new ArrayList<>();

	@Override
	public void readInput() {

		// Read the input until a blank line is encountered
		while (true) {

			String input = scanner.nextLine();

			if (input.isBlank()) {
				break;
			}

			rounds.add(new Round(input));
		}
	}

	@Override
	public void solvePart1() {

		int totalScore = 0;

		for (Round round: rounds) {

			// Add the score for the round
			// 1, 2, or 3 for your choice
			// 0, 3, 6 for loss/tie/win

			switch (round.elfChoice) {
			case 'A':
				switch(round.yourChoice) {
				case 'X':	totalScore += 1 + 3; 	break;
				case 'Y':	totalScore += 2 + 6; 	break;
				case 'Z':	totalScore += 3 + 0; 	break;
				}
				continue;

			case 'B':
				switch(round.yourChoice) {
				case 'X':	totalScore += 1 + 0; 	break;
				case 'Y':	totalScore += 2 + 3; 	break;
				case 'Z':	totalScore += 3 + 6; 	break;
				}
				continue;

			case 'C':
				switch(round.yourChoice) {
				case 'X':	totalScore += 1 + 6; 	break;
				case 'Y':	totalScore += 2 + 0; 	break;
				case 'Z':	totalScore += 3 + 3; 	break;
				}
				continue;
			}
		}

		System.out.println("Total Score " + totalScore);
	}

	@Override
	public void solvePart2() {

		int totalScore = 0;

		for (Round round: rounds) {

			// Add the score for the round
			// 1, 2, or 3 for your choice
			// 0, 3, 6 for loss/tie/win

			// To solve the second part, take the solution
			// from the first part and sort by loss/win/draw
			// and then change the case to be X, Y, Z in order

			switch (round.elfChoice) {
			case 'A':
				switch(round.yourChoice) {
				case 'X':	totalScore += 3 + 0; 	break;
				case 'Y':	totalScore += 1 + 3; 	break;
				case 'Z':	totalScore += 2 + 6; 	break;
				}
				continue;

			case 'B':
				switch(round.yourChoice) {
				case 'X':	totalScore += 1 + 0; 	break;
				case 'Y':	totalScore += 2 + 3; 	break;
				case 'Z':	totalScore += 3 + 6; 	break;
				}
				continue;

			case 'C':
				switch(round.yourChoice) {
				case 'X':	totalScore += 2 + 0; 	break;
				case 'Y':	totalScore += 3 + 3; 	break;
				case 'Z':	totalScore += 1 + 6; 	break;
				}
				continue;
			}
		}

		System.out.println("Total Score " + totalScore);

	}

}
