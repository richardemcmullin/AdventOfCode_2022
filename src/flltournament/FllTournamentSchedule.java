package flltournament;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class FllTournamentSchedule {

	static enum Round {

		PRACTICE1 ("Practice", 1, "Practice Round 1"),
		PRACTICE2 ("Practice", 2, "Practice Round 2"),
		TABLE1    ("Table",    1, "Table Round 1"),
		TABLE2    ("Table",    2, "Table Round 2"),
		TABLE3    ("Table",    3, "Table Round 3");

		String type, desc;
		int roundNumber;

		Round(String type, int roundNumber, String desc) {
			this.type        = type;
			this.roundNumber = roundNumber;
			this.desc        = desc;
		}
	}

	public static void main(String[] args) {

		Scanner inputScanner = new Scanner(System.in);

		System.out.println("Paste tournament master (.csv) contents and hit enter");


		List<String> timeline = new ArrayList<>();
		List<String> matches = new ArrayList<>();

		Map<Integer, List<String>> teamSchedules = new TreeMap<>();

		while (true) {

			String input = inputScanner.nextLine();

			if (input.isEmpty()) {
				inputScanner.close();
				break;
			}

			String[] inputStrings = input.split(",");

			if (inputStrings[0].startsWith("Time:")) {
				for (String s: inputStrings) {
					timeline.add(s);
				}
				continue;
			}

			if (inputStrings[0].startsWith("Match")) {
				for (String s: inputStrings) {
					matches.add(s);
				}
				continue;
			}

			List<String> teamSchedule = new ArrayList<>();

			for (String s: inputStrings) {
				teamSchedule.add(s);
			}

			int team = Integer.valueOf(inputStrings[0].split(" - ")[0]);

			teamSchedules.put(team, teamSchedule);
		}

		// Use the FLL Tournament headings.
		// Date			Begin Time	End Time	Type		Round	Description		Room		Team #
		// 2016-12-11	9:30 AM		9:35 AM		Table		1		Table Round 1	Table A		8
		System.out.println("Date,Begin Time,End Time,Type,Round,Description,Room,Team #");

		for (Map.Entry<Integer, List<String>> entry: teamSchedules.entrySet()) {

			Round round = Round.PRACTICE1;

			int team = entry.getKey();
			List<String> teamSchedule = entry.getValue();

			for (int col=0; col<teamSchedule.size(); col++) {

				String activity = teamSchedule.get(col);

				// table names end with A or B
				if (activity.endsWith(" A") || activity.endsWith(" B")) {

					// Write out an FLL tournament entry for every table entry
					StringBuilder sb = new StringBuilder();
					sb.append("2022-12-17")
					.append(",").append(timeline.get(col))
					.append(",").append(timeline.get(col+1))
					.append(",").append(round.type)
					.append(",").append(round.roundNumber)
					.append(",").append(round.desc)
					.append(",").append(activity)
					.append(",").append(team);

					System.out.println(sb.toString());

					int nextRound = Math.min(Round.values().length-1, round.ordinal()+1);

					round = Round.values()[nextRound];
				}

				if (activity.startsWith("Judging")) {

					String room = activity.split(" ")[1];

					// Write out 3 entries, 5 minutes apart for the Judging
					StringBuilder sb = new StringBuilder();
					sb.append("2022-12-17")
					.append(",").append(timeline.get(col))
					.append(",").append(timeline.get(col+1))
					.append(",").append("Project")
					.append(",").append(1)
					.append(",").append("Project Judging")
					.append(",").append(room)
					.append(",").append(team);

					System.out.println(sb.toString());

					sb.setLength(0);
					sb.append("2022-12-17")
					.append(",").append(timeline.get(col+1))
					.append(",").append(timeline.get(col+2))
					.append(",").append("Robot")
					.append(",").append(1)
					.append(",").append("Robot Design Judging")
					.append(",").append(room)
					.append(",").append(team);

					System.out.println(sb.toString());

					sb.setLength(0);
					sb.append("2022-12-17")
					.append(",").append(timeline.get(col+2))
					.append(",").append(timeline.get(col+3))
					.append(",").append("Core")
					.append(",").append(1)
					.append(",").append("Core Values Judging")
					.append(",").append(room)
					.append(",").append(team);

					System.out.println(sb.toString());
				}
			}
		}
	}

}
