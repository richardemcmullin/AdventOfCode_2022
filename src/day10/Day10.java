package day10;

import java.util.ArrayList;
import java.util.List;

import solver.AdventOfCodeSolver;

public class Day10 extends AdventOfCodeSolver {

	private class Instruction {

		final Operation operation;
		final char      register;
		final int       value;

		Instruction(String s) {

			if (s.startsWith("add")) {
				operation = Operation.ADD;
				register  = s.charAt(3);
				value     = Integer.valueOf(s.substring(5));
				return;
			}

			operation = Operation.NOOP;
			register  = ' ';
			value     = 0;
		}
	}

	private enum Operation {

		NOOP ("noop", 1),
		ADD  ("add",  2);

		final String code;
		final int    cycles;

		Operation(String code, int cycles) {
			this.code = code;
			this.cycles = cycles;
		}
	}

	public static void main(String[] args) {
		new Day10().solve();
	}

	/** List of objects created by parsing the input */
	private List<Instruction> program = new ArrayList<>();

	@Override
	public void readInput() {

		// Read the input until a blank line is encountered
		while (true) {

			String input = scanner.nextLine();

			if (input.isBlank()) {
				break;
			}

			program.add(new Instruction(input));
		}
	}

	@Override
	public void solvePart1() {

		int lastCycles = 0;
		int cycles = 0;

		int registerX = 1;

		int totalSignal = 0;

		for (Instruction instruction: program) {

			cycles += instruction.operation.cycles;

			if ((cycles+20) / 40 != (lastCycles+20) / 40 && cycles < 230) {

				System.out.println("Cycle " + cycles + " register X=" + registerX);

				totalSignal += (cycles/20) * 20 * registerX;
			}

			switch (instruction.operation) {
			case ADD:
				if (instruction.register == 'x') {
					registerX += instruction.value;
				}
				break;

			default:
				break;
			}

			lastCycles = cycles;
		}

		System.out.println("Total Signal " + totalSignal);
	}

	@Override
	public void solvePart2() {

		// Initialize the crt to blanks.
		char [][] crt = new char[6][40];
		for (int row=0; row<6; row++) {
			for (int col=0; col<40; col++) {
				crt[row][col] = ' ';
			}
		}

		int currentInstructionIndex = 0;
		Instruction instruction = program.get(0);

		int instructionEndCycle = instruction.operation.cycles - 1;

		int registerX = 1;

		for (int cycle = 0; cycle<240; cycle++) {

			// Sprite is 3 wide centered on x, so the
			// distance between the cycle modulo and the
			// sprite center must be at most one.
			if (Math.abs(registerX - cycle%40) <= 1) {
				int row = cycle / 40;
				int col = cycle % 40;
				crt[row][col] = '#';
			}

			// At the end of the current instruction cycle
			// execute the instruction and advance to the
			// next instruction.
			if (cycle == instructionEndCycle) {

				switch (instruction.operation) {
				case ADD:
					if (instruction.register == 'x') {
						registerX += instruction.value;
					}
					break;

				default:
					break;
				}

				// Advance to the next instruction
				currentInstructionIndex++;
				if (currentInstructionIndex >= program.size()) {
					break;
				}
				instruction = program.get(currentInstructionIndex);
				instructionEndCycle = cycle + instruction.operation.cycles;
			}
		}

		// Print the crt to display the answer.
		for (int row=0; row<6; row++) {
			for (int col=0; col<40; col++) {
				System.out.print(crt[row][col]);
			}
			System.out.println();
		}
	}

}
