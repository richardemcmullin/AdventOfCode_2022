package day07;

import java.util.ArrayList;
import java.util.List;

import solver.AdventOfCodeSolver;

public class Day07 extends AdventOfCodeSolver {

	private class Directory {

		final Directory parent;

		final String name;

		final List<File>      files          = new ArrayList<>();
		final List<Directory> subDirectories = new ArrayList<>();

		private long totalSize = -1;

		Directory(Directory parent, String name) {
			this.parent = parent;
			this.name   = name;
		}

		void addFile(String[] fileInfo) {

			String name = fileInfo[1];

			// If the file already exists, then do nothing
			for (File f: files) {
				if (f.name.equals(name)) {
					return;
				}
			}

			files.add(new File(this, fileInfo));
		}

		void addSubDirectory(String name) {
			if (!subDirectories.contains(name)) {
				subDirectories.add(new Directory(this, name));
			}
		}

		long getSize() {

			if (totalSize >= 0) {
				return totalSize;
			}

			int totalSize = 0;
			for (File file: files) {
				totalSize += file.size;
			}
			for (Directory dir: subDirectories) {
				totalSize += dir.getSize();
			}

			// Cache the total size once it is calculated.
			this.totalSize = totalSize;

			return totalSize;
		}
	}

	private class File {

		final Directory parent;
		final long size;
		final String name;

		File (Directory parent, String[] fileInfo) {
			this.size   = Long.valueOf(fileInfo[0]);
			this.name   = fileInfo[1];
			this.parent = parent;
		}
	}

	public static void main(String[] args) {
		new Day07().solve();
	}

	/** List of objects created by parsing the input */
	private Directory root = new Directory(null, "/");

	private long usedStorage = 0;

	@Override
	public void readInput() {

		String    command = "";
		Directory currentDirectory = root;

		// Read the input until a blank line is encountered
		while (true) {

			String input = scanner.nextLine();

			if (input.isBlank()) {
				break;
			}

			String[] inputStrings = input.split(" ");

			// The input is either a command or a response
			if (inputStrings[0].equals("$")) {

				command = inputStrings[1];

				switch (command) {
				case "cd":
					String targetDirectory = inputStrings[2];

					switch (targetDirectory) {
					case "/":
						currentDirectory = root;
						break;
					case "..":
						currentDirectory = currentDirectory.parent;
						break;
					default:
						// Switch to the target subdirectory
						for (Directory d: currentDirectory.subDirectories) {
							if (d.name.equals(targetDirectory)) {
								currentDirectory = d;
							}
						}
					}

					break;

				case "ls":
					// there is no action on a list command.
					break;
				}
			}
			else {

				if (command.equals("ls")) {

					// add entry to the current directory
					boolean isDirectory = inputStrings[0].equals("dir");

					if (isDirectory) {
						currentDirectory.addSubDirectory(inputStrings[1]);
					}
					else {
						currentDirectory.addFile(inputStrings);
					}
				}
			}
		}

		// Calculate all directory sizes
		usedStorage = root.getSize();

		System.out.println("Total filesystem used storage = " + usedStorage);
	}

	@Override
	public void solvePart1() {
		System.out.println("Total under 100k " + sumUnder100k(root));
	}

	@Override
	public void solvePart2() {

		long totalSpace          = 70000000;
		long requiredUpdateSpace = 30000000;

		long freeSpace = totalSpace - usedStorage;

		long spaceNeeded = requiredUpdateSpace - freeSpace;

		System.out.println("Smallest deleteable directory " +
				getSmallestDeletableDirectorySize(root, spaceNeeded));
	}

	private long getSmallestDeletableDirectorySize(Directory dir, long spaceNeeded) {

		long smallestDeletableSize = Long.MAX_VALUE;

		if (dir.totalSize >= spaceNeeded) {
			smallestDeletableSize = dir.totalSize;
		}
		else {
			return smallestDeletableSize;
		}

		for (Directory subDir: dir.subDirectories) {

			long subDirSmallestDeletableSize = getSmallestDeletableDirectorySize(subDir, spaceNeeded);

			if (subDirSmallestDeletableSize < smallestDeletableSize) {
				smallestDeletableSize = subDirSmallestDeletableSize;
			}
		}

		return smallestDeletableSize;
	}

	private long sumUnder100k(Directory dir) {

		long total = 0;

		if (dir.totalSize < 100000) {
			total += dir.totalSize;
		}

		for (Directory d: dir.subDirectories) {
			total += sumUnder100k(d);
		}

		return total;
	}

}
