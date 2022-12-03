package day03;

import java.util.ArrayList;
import java.util.List;

import solver.AdventOfCodeSolver;

public class Day03 extends AdventOfCodeSolver {

	private class Rucksack {

		final String compartment1;
		final String compartment2;
		final String allItems;

		Rucksack(String s) {
			compartment1 = s.substring(0, s.length()/2);
			compartment2 = s.substring(s.length()/2);
			allItems     = s;
		}
	}

	public static void main(String[] args) {
		new Day03().solve();
	}

	private List<Rucksack> rucksacks = new ArrayList<>();

	@Override
	public void readInput() {

		// Read the input until a blank line is encountered
		while (true) {

			String input = scanner.nextLine();

			if (input.isBlank()) {
				break;
			}

			rucksacks.add(new Rucksack(input));
		}
	}

	@Override
	public void solvePart1() {

		int totalPriority = 0;

		for (Rucksack rucksack: rucksacks) {

			char item = ' ';

			// Look for an element that is common between
			// the two compartments of each rucksack
			for (char c: rucksack.compartment1.toCharArray()) {
				if (rucksack.compartment2.contains(String.valueOf(c))) {
					item = c;
					break;
				}
			}

			if (item >= 'a' && item <= 'z') {
				totalPriority += item - 'a' + 1;
			}
			else {
				totalPriority += item - 'A' + 27;
			}

		}
		System.out.println("Total Priority " + totalPriority);
	}

	@Override
	public void solvePart2() {

		int totalPriority = 0;

		int group = 0;

		while (group*3 < rucksacks.size()) {

			Rucksack groupRucksack1 = rucksacks.get(group*3);
			Rucksack groupRucksack2 = rucksacks.get(group*3+1);
			Rucksack groupRucksack3 = rucksacks.get(group*3+2);

			char item = ' ';

			// Look for an element that is common in each set of three rucksacks.
			for (char c: groupRucksack1.allItems.toCharArray()) {

				// Check if the item is in rucksack 2
				if (!groupRucksack2.allItems.contains(String.valueOf(c))) {
					continue;
				}

				// Check if the item is in rucksack 3
				if (!groupRucksack3.allItems.contains(String.valueOf(c))) {
					continue;
				}

				// If it is the common item, then break.

				item = c;
				break;
			}

			if (item >= 'a' && item <= 'z') {
				totalPriority += item - 'a' + 1;
			}
			else {
				totalPriority += item - 'A' + 27;
			}

			group++;
		}

		System.out.println("Total Priority " + totalPriority);
	}

}
