package day05;

import java.util.ArrayList;
import java.util.List;

import solver.AdventOfCodeSolver;

public class Day05 extends AdventOfCodeSolver {

	private class Move {

		final int boxCount;
		final int fromStack;
		final int toStack;

		Move(String s) {

			// Parse: move 10 from 2 to 7

			String[] strings = s.split(" ");
			boxCount  = Integer.valueOf(strings[1]);
			fromStack = Integer.valueOf(strings[3]);
			toStack   = Integer.valueOf(strings[5]);
		}
	}

	public static void main(String[] args) {
		new Day05().solve();
	}

	/** List of objects created by parsing the input */
	private List<Move> moves = new ArrayList<>();

	private List<List<Character>> stacks = new ArrayList<>();

	@Override
	public void readInput() {

		// Read the first line.  The number of stacks is the length
		// of the line / 4 + 1
		String input = scanner.nextLine();

		int stackCount = input.length() / 4 + 1;

		// Initialize all of the stacks to empty
		for (int i=0; i<stackCount; i++) {
			stacks.add(new ArrayList<Character>());
		}

		// Read the input for the initial stack configurations
		while (true) {

			// Put the boxes in the stacks
			for (int i=0; i<stackCount; i++) {

				List<Character> stack = stacks.get(i);

				char box = input.charAt(i*4 + 1);

				// Skip any columns that have no box
				if (box == ' ') {
					continue;
				}

				stack.add(box);
			}

			input = scanner.nextLine();

			// Skip the stack number line
			if (input.charAt(1) == '1') {
				input = scanner.nextLine();
			}

			// Once a blank line is encountered, the stacks are loaded
			if (input.isBlank()) {
				break;
			}
		}

		// Read the input for moves
		while (true) {

			input = scanner.nextLine();

			if (input.isBlank()) {
				break;
			}

			moves.add(new Move(input));
		}
	}

	@Override
	public void solvePart1() {

		// Make copy of the stacks to not destroy the input.
		List<List<Character>> stacks = copyStacks();

		for (Move move: moves) {

			List<Character> fromStack = stacks.get(move.fromStack-1);
			List<Character> toStack   = stacks.get(move.toStack-1);

			// Move the requested number of boxes

			List<Character> movedBoxes = new ArrayList<>();

			for (int i=0; i<move.boxCount; i++) {
				// Move the boxes one at a time so they end up in reverse order
				movedBoxes.add(0, fromStack.remove(0));
			}

			// Add the moved stack to the top of the to stack
			toStack.addAll(0, movedBoxes);
		}

		System.out.print("Top Boxes ");
		for (List<Character> stack: stacks) {
			if (!stack.isEmpty()) {
				System.out.print(stack.get(0));
			}
		}
		System.out.println();
	}

	@Override
	public void solvePart2() {

		// Make copy of the stacks to not destroy the input
		List<List<Character>> stacks = copyStacks();

		for (Move move: moves) {

			List<Character> fromStack = stacks.get(move.fromStack-1);
			List<Character> toStack   = stacks.get(move.toStack-1);

			// Move the requested number of boxes

			List<Character> movedBoxes = new ArrayList<>();

			for (int i=0; i<move.boxCount; i++) {
				// Move the boxes all together instead of one at a time.
				movedBoxes.add(fromStack.remove(0));
			}

			// Add the moved stack to the top of the to stack
			toStack.addAll(0, movedBoxes);
		}

		System.out.print("Top Boxes ");
		for (List<Character> stack: stacks) {
			if (!stack.isEmpty()) {
				System.out.print(stack.get(0));
			}
		}
		System.out.println();
	}

	private List<List<Character>> copyStacks() {

		List<List<Character>> copyStacks = new ArrayList<>();

		for (List<Character> stack: stacks) {
			List<Character> copyStack = new ArrayList<>();
			copyStack.addAll(stack);
			copyStacks.add(copyStack);
		}
		return copyStacks;
	}

}
