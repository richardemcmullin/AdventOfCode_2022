package day11;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import solver.AdventOfCodeSolver;

public class Day11 extends AdventOfCodeSolver {

	private class Monkey {

		List<Long> items = new ArrayList<>();
		char operation = ' ';
		int operationValue = 0;
		int divisor;
		int trueMonkeyIndex;
		int falseMonkeyIndex;
		int inspectionCount = 0;

		Monkey(List<String> monkeyStrings) {

			// Items
			String itemString = monkeyStrings.get(1);
			String[] strings = itemString.split(":");
			strings = strings[1].trim().split(", ");
			for (String s: strings) {
				items.add(Long.valueOf(s));
			}
			// Operation
			String operationString = monkeyStrings.get(2);
			strings = operationString.split("= ");
			strings = strings[1].split(" ");
			operation = strings[1].charAt(0);
			if (strings[2].equals("old")) {
				operationValue = -1;
			}
			else {
				operationValue = Integer.valueOf(strings[2]);
			}
			// Test
			divisor = Integer.valueOf(
					monkeyStrings.get(3).split("by ")[1]);
			// True
			trueMonkeyIndex = Integer.valueOf(
					monkeyStrings.get(4).split("monkey ")[1]);
			// False
			falseMonkeyIndex = Integer.valueOf(
					monkeyStrings.get(5).split("monkey ")[1]);
		}

		Monkey(Monkey monkey) {
			this.items.addAll(monkey.items);
			this.operation = monkey.operation;
			this.operationValue = monkey.operationValue;
			this.divisor = monkey.divisor;
			this.trueMonkeyIndex = monkey.trueMonkeyIndex;
			this.falseMonkeyIndex = monkey.falseMonkeyIndex;
		}

		void inspect() {

			long worry = items.remove(0);

			if (operation == '+') {
				if (operationValue < 0) {
					worry += worry;
				}
				else {
					worry += operationValue;
				}
			}
			else {
				if (operationValue < 0) {
					worry *= worry;
				}
				else {
					worry *= operationValue;
				}
			}
			worry /= 3;
			items.add(0, worry);
			inspectionCount++;
		}

		void inspect2() {

			long worry = items.remove(0);

			if (operation == '+') {
				if (operationValue < 0) {
					worry += worry;
				}
				else {
					worry += operationValue;
				}
			}
			else {
				if (operationValue < 0) {
					worry *= worry;
				}
				else {
					worry *= operationValue;
				}
			}
			worry %= worryModulo;
			items.add(0, worry);
			inspectionCount++;
		}

		int nextMonkey(long worry) {
			if (worry%divisor == 0) {
				return trueMonkeyIndex;
			}
			return falseMonkeyIndex;
		}

	}

	private static int worryModulo = 0;

	public static void main(String[] args) {
		debug = true;
		new Day11().solve();
	}

	/** List of objects created by parsing the input */
	private List<Monkey> inputMonkeys = new ArrayList<>();

	@Override
	public void readInput() {

		List<String> monkeyStrings = new ArrayList<>();

		// Read the input until a blank line is encountered
		while (true) {

			String input = scanner.nextLine();

			if (input.isBlank()) {
				break;
			}

			while (!input.isBlank()) {

				monkeyStrings.add(input);

				input = scanner.nextLine();

				if (input.isBlank()) {
					break;
				}
			}

			// A blank line separates all monkeys

			inputMonkeys.add(new Monkey(monkeyStrings));

			monkeyStrings.clear();
		}
	}

	@Override
	public void solvePart1() {

		// Copy the input to not destroy the input on part 1
		List<Monkey> monkeys = new ArrayList<>();
		for (Monkey monkey: inputMonkeys) {
			monkeys.add(new Monkey(monkey));
		}

		for (int round=1; round<=20; round++) {
			for (Monkey monkey: monkeys) {
				while (!monkey.items.isEmpty()) {
					monkey.inspect();
					long worry = monkey.items.remove(0);
					int nextMonkeyIndex = monkey.nextMonkey(worry);
					Monkey nextMonkey = monkeys.get(nextMonkeyIndex);
					nextMonkey.items.add(worry);
				}
			}

			// At the end of the round, print the monkeys items
			if (debug) {
				System.out.println("\nRound " + round);
				for (int i=0; i<monkeys.size(); i++) {
					System.out.println("Monkey " + i + " : " + monkeys.get(i).items);
				}
			}
		}

		// Sort the monkeys by decreasing activity
		monkeys.sort(new Comparator<>() {
			@Override
			public int compare(Monkey m1, Monkey m2) {
				return m2.inspectionCount - m1.inspectionCount;
			}});

		System.out.println("Monkey Business = " + monkeys.get(0).inspectionCount * monkeys.get(1).inspectionCount);
	}

	@Override
	public void solvePart2() {

		// Copy the input to not destroy the input
		List<Monkey> monkeys = new ArrayList<>();
		for (Monkey monkey: inputMonkeys) {
			monkeys.add(new Monkey(monkey));
		}

		// Set the modulo for the worry to the multiple of all of the worry divisors
		worryModulo = 1;
		for (Monkey monkey: monkeys) {
			worryModulo *= monkey.divisor;
		}

		System.out.println("Worry modulo " + worryModulo);

		for (int round=1; round<=10000; round++) {
			for (Monkey monkey: monkeys) {
				while (!monkey.items.isEmpty()) {
					monkey.inspect2();
					long worry = monkey.items.remove(0);
					int nextMonkeyIndex = monkey.nextMonkey(worry);
					Monkey nextMonkey = monkeys.get(nextMonkeyIndex);
					nextMonkey.items.add(worry);
				}
			}

			// At the end of certain round, print the inspection count
			if (debug) {
				if (round==1 || round == 20 || round%1000 == 0) {
					System.out.println("\nRound " + round);
					for (int i=0; i<monkeys.size(); i++) {
						System.out.println("Monkey " + i + " inspected " + monkeys.get(i).inspectionCount);
					}
				}
			}
		}

		// Sort the monkeys by decreasing activity
		monkeys.sort(new Comparator<>() {
			@Override
			public int compare(Monkey m1, Monkey m2) {
				return m2.inspectionCount - m1.inspectionCount;
			}});

		System.out.println("Monkey Business = " + 1L * monkeys.get(0).inspectionCount * monkeys.get(1).inspectionCount);
	}

}
