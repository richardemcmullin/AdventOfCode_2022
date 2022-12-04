package day04;

import java.util.ArrayList;
import java.util.List;

import solver.AdventOfCodeSolver;

public class Day04 extends AdventOfCodeSolver {

	private class Elf {

		final int min;
		final int max;

		Elf(String s) {

			String[] range = s.split("-");

			min = Integer.valueOf(range[0]);
			max = Integer.valueOf(range[1]);
		}
	}

	private class ElfPair {

		final Elf elf1;
		final Elf elf2;

		ElfPair(String s) {

			String[] ranges = s.split(",");
			elf1 = new Elf(ranges[0]);
			elf2 = new Elf(ranges[1]);
		}
	}

	public static void main(String[] args) {
		new Day04().solve();
	}

	private List<ElfPair> elfPairs = new ArrayList<>();

	@Override
	public void readInput() {

		// Read the input until a blank line is encountered
		while (true) {

			String input = scanner.nextLine();

			if (input.isBlank()) {
				break;
			}

			elfPairs.add(new ElfPair(input));
		}
	}

	@Override
	public void solvePart1() {

		int total = 0;

		for (ElfPair elfPair: elfPairs) {

			// If the minimums or maximums are the same, then
			// the ranges must subset each other
			if (       elfPair.elf1.min == elfPair.elf2.min
					|| elfPair.elf1.max == elfPair.elf2.max) {
				total++;
			}
			else if (elfPair.elf1.min < elfPair.elf2.min) {

				if (elfPair.elf2.max < elfPair.elf1.max) {
					total++;
				}
			}
			else {

				if (elfPair.elf1.max < elfPair.elf2.max) {
					total++;
				}
			}

		}

		System.out.println("Subsets " + total);
	}

	@Override
	public void solvePart2() {

		int total = 0;

		for (ElfPair elfPair: elfPairs) {

			// If the minimums or maximums are the same, then
			// the ranges must overlap
			if (       elfPair.elf1.min == elfPair.elf2.min
					|| elfPair.elf1.max == elfPair.elf2.max) {
				total++;
			}
			else if (elfPair.elf1.min < elfPair.elf2.min) {

				if (elfPair.elf1.max >= elfPair.elf2.min) {
					total++;
				}
			}
			else {

				if (elfPair.elf2.max >= elfPair.elf1.min) {
					total++;
				}
			}

		}

		System.out.println("Overlaps " + total);
	}

}
