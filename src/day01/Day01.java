package day01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Day01 {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		// Store all of the input calories in an array of elves
		List<List<Integer>> elves = new ArrayList<>();

		// Read the input until a double blank line is encountered
		while (true) {

			String input = scanner.nextLine();

			if (input.isBlank()) {
				break;
			}
			else {

				// Make a new elf
				List<Integer> elf = new ArrayList<>();
				elves.add(elf);
				elf.add(Integer.valueOf(input));

				// Read all of the remaining entries for the elf
				while (true) {

					input = scanner.nextLine();

					if (input.isBlank()) {
						// end of this elf
						break;
					}
					elf.add(Integer.valueOf(input));
				}
			}
		}

		solvePart1(elves);
		solvePart2(elves);
	}

	private static void solvePart1(List<List<Integer>> elves) {

		int maxCalories = 0;

		for (List<Integer> elf: elves) {

			int elfCalories = 0;

			for (int calories: elf) {
				elfCalories += calories;
			}

			maxCalories = Math.max(elfCalories, maxCalories);
		}

		System.out.println("Part 1\n" 
				+ "Max elf calories " + maxCalories);
	}

	private static void solvePart2(List<List<Integer>> elves) {

		List<Integer> elvesCalories = new ArrayList<>();

		for (List<Integer> elf: elves) {

			int elfCalories = 0;

			for (int calories: elf) {
				elfCalories += calories;
			}

			elvesCalories.add(elfCalories);
		}

		Collections.sort(elvesCalories);

		int top3Calories = 
				elvesCalories.get(elvesCalories.size()-1)
				+ elvesCalories.get(elvesCalories.size()-2)
				+ elvesCalories.get(elvesCalories.size()-3);

		System.out.println("\n\nPart 2\n" 
				+ "Max 3 elf calories " + top3Calories);
	}

}
