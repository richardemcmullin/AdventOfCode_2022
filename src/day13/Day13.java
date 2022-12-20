package day13;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import solver.AdventOfCodeSolver;

public class Day13 extends AdventOfCodeSolver {

	private class Packet {

		List<Object> values = new ArrayList<>();

		Packet(List<Character> charsArrayList) {

			// A packet always begins with a '['
			// remove the starting bracket.
			char c = charsArrayList.remove(0);
			int number = -1;

			// Parse this packet one character at a
			// time until the closing bracket is encountered
			while (true) {

				c = charsArrayList.remove(0);

				switch (c) {
				case '[':
					// Put the bracket back at the start of the list
					// and add this sub-packet to the values list
					charsArrayList.add(0, c);
					values.add(new Packet(charsArrayList));
					break;

				case ',':
				case ']':
					if (number >= 0) {
						values.add(Integer.valueOf(number));
						number = -1;
					}
					break;

				default:
					int digit = c - '0';
					if (digit >=0 && digit <= 9) {
						if (number < 0) {
							number = digit;
						}
						else {
							number = number * 10 + digit;
						}
					}
				}

				// Stop parsing this packet when the bracket closes
				if (c == ']') {
					break;
				}
			}
		}

		Packet(Object value) {
			this.values.add(value);
		}

		public int compareTo(Packet other) {

			// Compare all of the values up to the size of the shorter list
			int minSize = Math.min(this.values.size(), other.values.size());

			for (int i=0; i<minSize; i++) {

				Object value      = this.values.get(i);
				Object otherValue = other.values.get(i);

				// If both values are integers, then compare the integer values
				if (value instanceof Integer && otherValue instanceof Integer) {

					int thisInt = (Integer) value;
					int otherInt = (Integer) otherValue;

					// If the integers are equal, continue to the next value
					if (thisInt == otherInt) {
						continue;
					}

					return thisInt - otherInt;
				}

				// If one sub-packet is an integer, and one is a packet, then
				// convert the integer to a packet and compare the packets.
				if (value instanceof Integer) {
					value = new Packet(value);
				}

				if (otherValue instanceof Integer) {
					otherValue = new Packet(otherValue);
				}

				int compare = ((Packet) value).compareTo((Packet)otherValue);
				if (compare != 0) {
					return compare;
				}
			}

			// Once all of the values have been compared,
			// compare the lengths of the packets
			return this.values.size() - other.values.size();
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append('[');
			boolean first = true;
			for (Object value:values) {
				if (first) {
					first = false;
				}
				else {
					sb.append(',');
				}
				if (value instanceof Packet) {
					sb.append(value);
				}
				else {
					sb.append(value);
				}
			}
			sb.append(']');
			return sb.toString();
		}
	}


	private class Pair {

		Packet left, right;

		Pair(List<String> pairStrings) {

			left  = new Packet(toCharArrayList(pairStrings.get(0)));
			right = new Packet(toCharArrayList(pairStrings.get(1)));
		}

		@Override
		public String toString() {
			return "Pair\nleft : " + left + "\nright:" + right;
		}

		private List<Character> toCharArrayList(String s) {

			List<Character> chars = new ArrayList<>();
			for (char c: s.toCharArray()) {
				chars.add(c);
			}

			return chars;
		}
	}

	public static void main(String[] args) {
		new Day13().solve();
	}

	/** List of objects created by parsing the input */
	private List<Pair> pairs = new ArrayList<>();

	@Override
	public void readInput() {

		List<String> inputPair = new ArrayList<>();

		// Read the input until a blank line is encountered
		while (true) {

			String input = scanner.nextLine();

			if (input.isBlank()) {
				break;
			}

			inputPair.clear();

			while (!input.isBlank()) {
				inputPair.add(input);
				input = scanner.nextLine();
			}

			pairs.add(new Pair(inputPair));
		}
	}

	@Override
	public void solvePart1() {

		int total = 0;

		int index = 1;

		for (Pair pair: pairs) {

			if (debug) {
				System.out.println(pair);
				System.out.println(pair.left.compareTo(pair.right) < 0);
			}

			if (pair.left.compareTo(pair.right) < 0) {
				total += index;
			}
			index++;
		}

		System.out.println("Total " + total);
	}

	@Override
	public void solvePart2() {

		List<Packet> allPackets = new ArrayList<>();

		Packet two = new Packet(new Packet(2));
		Packet six = new Packet(new Packet(6));

		allPackets.add(two);
		allPackets.add(six);

		for (Pair pair: pairs) {

			allPackets.add(pair.left);
			allPackets.add(pair.right);
		}

		Collections.sort(allPackets, new Comparator<Packet>() {

			@Override
			public int compare(Packet p1, Packet p2) {

				return p1.compareTo(p2);
			}});

		int total = (allPackets.indexOf(two)+1) * (allPackets.indexOf(six)+1);

		System.out.println("Total " + total);
	}

}
