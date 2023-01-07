package day15;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import solver.AdventOfCodeSolver;

public class Day15 extends AdventOfCodeSolver {

	private class Beacon {

		final int x, y;

		Beacon(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return "Beacon(" + x + "," + y + ")";
		}
	}

	private class Range {

		final int start, end;

		Range(int center, int dist) {
			start = center-dist;
			end   = center+dist;
		}
	}

	private class Sensor {

		final int x, y;
		final Beacon closestBeacon;
		final int closestBeaconDistance;

		Sensor(int x, int y, Beacon closestBeacon) {
			this.x = x;
			this.y = y;
			this.closestBeacon = closestBeacon;

			closestBeaconDistance = Math.abs(x-closestBeacon.x) +
					Math.abs(y-closestBeacon.y);
		}

		@Override
		public String toString() {
			return "Sensor(" + x + "," + y + "[" + closestBeacon + "dist:" + closestBeaconDistance + "]";
		}
	}

	public static void main(String[] args) {
		debug = true;
		new Day15().solve();
	}

	/** List of objects created by parsing the input */
	private List<Beacon> beacons = new ArrayList<>();

	private List<Sensor> sensors = new ArrayList<>();

	@Override
	public void readInput() {

		// Read the input until a blank line is encountered
		while (true) {

			String input = scanner.nextLine();

			if (input.isBlank()) {
				break;
			}

			parseInput(input);
		}

		// Sort the sensors by row, col
		Collections.sort(sensors, new Comparator<Sensor>() {

			@Override
			public int compare(Sensor s1, Sensor s2) {

				if (s1.y != s2.y) {
					return s1.y - s2.y;
				}

				return s1.x - s2.x;
			}});

		if (debug) {
			for (Sensor s: sensors) {
				System.out.println(s);
			}
		}

	}

	@Override
	public void solvePart1() {

		int total = 0;

		int y = 2000000;

		// Find a list of all sensors that can see the target row,
		// and the range along the target row that is visible.

		List<Range> excludedRanges = new ArrayList<>();

		for (Sensor sensor: sensors) {

			// If the sensor does not reach the row, then continue
			if (Math.abs(sensor.y-y) > sensor.closestBeaconDistance) {
				continue;
			}

			// Make a range of x coordinates viewable by this sensor, and
			// add it to the list of excluded ranges.
			int dist = sensor.closestBeaconDistance - Math.abs(sensor.y - y);

			excludedRanges.add(new Range(sensor.x, dist));
		}

		// Sort the ranges by starting position
		Collections.sort(excludedRanges, new Comparator<Range>() {
			@Override
			public int compare(Range r1, Range r2) {

				if (r1.start != r2.start) {
					return r1.start - r2.start;
				}
				return r1.end - r2.end;
			}});

		// Calculate the total length of all ranges not including overlaps.
		int start = Integer.MIN_VALUE;
		int end = Integer.MAX_VALUE;

		for (Range range: excludedRanges) {

			if (start == Integer.MIN_VALUE) {
				start = range.start;
				end   = range.end;
				continue;
			}

			// If the next range starts after the end of this range, then close the current range
			if (range.start > end+1) {
				// remove any beacons in the range because they are not excluded
				total += end - start + 1 - beaconCount(start, y, end, y);
				start = range.start;
				end   = range.end;
				continue;
			}

			// Extend the current range
			end = Math.max(end,  range.end);
		}

		if (start != Integer.MAX_VALUE) {
			total += end - start + 1 - beaconCount(start, y, end, y);
		}

		System.out.println("Total " + total);
	}

	@Override
	public void solvePart2() {

		Beacon hiddenBeacon = null;

		int maxCoordinate = 4000000;

		// For each row in the max range, look for a gap that is the missing beacon
		for (int y = 0; y<= maxCoordinate; y++) {

			// Find a list of all sensors that can see the target row,
			// and the range along the target row that is visible.

			List<Range> excludedRanges = new ArrayList<>();

			for (Sensor sensor: sensors) {

				// If the sensor does not reach the row, then continue
				if (Math.abs(sensor.y-y) > sensor.closestBeaconDistance) {
					continue;
				}

				// Make a range of x coordinates viewable by this sensor, and
				// add it to the list of excluded ranges.
				int dist = sensor.closestBeaconDistance - Math.abs(sensor.y - y);

				excludedRanges.add(new Range(sensor.x, dist));
			}

			// Sort the ranges by starting position
			Collections.sort(excludedRanges, new Comparator<Range>() {
				@Override
				public int compare(Range r1, Range r2) {

					if (r1.start != r2.start) {
						return r1.start - r2.start;
					}
					return r1.end - r2.end;
				}});

			// Look for a gap in the range.
			int start = Integer.MIN_VALUE;
			int end = Integer.MAX_VALUE;

			for (Range range: excludedRanges) {

				if (start == Integer.MIN_VALUE) {
					start = range.start;
					end   = range.end;
					continue;
				}

				// Look for a gap in the ranges
				if (range.start > end+1) {

					// Is this gap of interest
					if (end+1 >= 0 && end+1 <= maxCoordinate) {
						hiddenBeacon = new Beacon(end+1, y);
						break;
					}

					start = range.start;
					end   = range.end;
					continue;
				}

				// Extend the current range
				end = Math.max(end,  range.end);
			}

			if (hiddenBeacon != null) {
				break;
			}

		}

		if (hiddenBeacon == null) {
			System.out.println("Hidden Beacon not found");
		}
		else {
			System.out.println("Tuning " + (hiddenBeacon.x*4000000L + hiddenBeacon.y));
		}
	}

	private int beaconCount(int startX, int startY, int endX, int endY) {

		// Count all beacons in the range
		int beaconCount = 0;

		for (Beacon beacon: beacons) {

			if (       beacon.x >= startX && beacon.y >= startY
					&& beacon.x <= endX   && beacon.y <= endY) {

				beaconCount++;
			}
		}
		return beaconCount;
	}

	private void parseInput(String input) {

		String[] objectStrings = input.split(":");

		// Beacon definition is at the end of the line
		String[] coordinateStrings = objectStrings[1].split(",");

		int x = Integer.valueOf(coordinateStrings[0].split("=")[1]);
		int y = Integer.valueOf(coordinateStrings[1].split("=")[1]);

		// Determine if there is a beacon already at these coordinates
		Beacon beacon = null;
		for (Beacon b: beacons) {
			if (b.x == x && b.y == y) {
				beacon = b;
				break;
			}
		}

		if (beacon == null) {
			beacon = new Beacon(x, y);
			beacons.add(beacon);
		}

		// Sensor definition is the start of the line
		coordinateStrings = objectStrings[0].split(",");

		x = Integer.valueOf(coordinateStrings[0].split("=")[1]);
		y = Integer.valueOf(coordinateStrings[1].split("=")[1]);

		sensors.add(new Sensor(x, y, beacon));
	}
}
