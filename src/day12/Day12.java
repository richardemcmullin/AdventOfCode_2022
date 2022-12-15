package day12;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import solver.AdventOfCodeSolver;

public class Day12 extends AdventOfCodeSolver {

	private class Node {

		static int getLocationId(int row, int col) {
			return row * 1000 + col;
		}
		final char height;
		final int row;
		final int col;

		final int id;

		int shortestDistance;
		List<Node> shortestPath = new ArrayList<>();

		Node(int row, int col, char height) {

			this.row = row;
			this.col = col;
			this.height = height;
			this.id = getLocationId(row, col);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("(").append(row)
			.append(",").append(col)
			.append(")").append(height).append(shortestDistance);
			return sb.toString();
		}
	}

	public static void main(String[] args) {
		new Day12().solve();
	}

	Map<Integer, Node> heightMap = new HashMap<>();

	int maxRow = 0;
	int maxCol = 0;

	Node start = null;
	Node end = null;

	@Override
	public void readInput() {

		int row = 0;

		// Read the input until a blank line is encountered
		while (true) {

			String input = scanner.nextLine();

			if (input.isBlank()) {
				break;
			}

			if (maxCol == 0) {
				maxCol = input.length();
			}

			for (int col=0; col<input.length(); col++) {
				char height = input.charAt(col);
				int  locationId = Node.getLocationId(row, col);
				Node node = null;
				if (height == 'S') {
					node = new Node(row, col, 'a');
					start = node;
				}
				else if (height == 'E') {
					node = new Node(row, col, 'z');
					end = node;
				}
				else {
					node = new Node(row, col, height);
				}
				heightMap.put(locationId, node);
			}

			row++;
		}

		maxRow = row;
	}

	@Override
	public void solvePart1() {

		// use Dijkstra's algorithm https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm

		// Create a priority queue of all unvisited sites with a distance of max.
		PriorityQueue<Node> unvisitedNodes = new PriorityQueue<>(
				new Comparator<Node>() {
					@Override
					public int compare(Node n1, Node n2) {
						return n1.shortestDistance - n2.shortestDistance;
					}});

		Map<Integer, Node> unqueuedNodes = new HashMap<>(heightMap);

		Node curNode = unqueuedNodes.remove(start.id);
		curNode.shortestDistance = 0;

		while (curNode != end) {

			// Check each connected node and set the shortest distance.
			// North
			Node node = getNode(curNode.row-1, curNode.col, curNode, unvisitedNodes, unqueuedNodes);
			if (node != null) {
				setShortestDistance(node, curNode);
				unvisitedNodes.add(node);
			}

			// South
			node = getNode(curNode.row+1, curNode.col, curNode, unvisitedNodes, unqueuedNodes);
			if (node != null) {
				setShortestDistance(node, curNode);
				unvisitedNodes.add(node);
			}

			// East
			node = getNode(curNode.row, curNode.col+1, curNode, unvisitedNodes, unqueuedNodes);
			if (node != null) {
				setShortestDistance(node, curNode);
				unvisitedNodes.add(node);
			}

			// West
			node = getNode(curNode.row, curNode.col-1, curNode, unvisitedNodes, unqueuedNodes);
			if (node != null) {
				setShortestDistance(node, curNode);
				unvisitedNodes.add(node);
			}

			curNode = unvisitedNodes.poll();
		}

		System.out.println("Steps " + curNode.shortestDistance);
	}

	@Override
	public void solvePart2() {

		// use Dijkstra's algorithm https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm

		// Make a list of all possible starting points
		List<Node> startingNodes = new ArrayList<>();
		for (Node node: heightMap.values()) {
			if (node.height == 'a') {
				startingNodes.add(node);
			}
		}

		int minDistance = Integer.MAX_VALUE;

		for (Node curNode: startingNodes) {

			curNode.shortestDistance = 0;

			// Create a priority queue of all unvisited sites
			PriorityQueue<Node> unvisitedNodes = new PriorityQueue<>(
					new Comparator<Node>() {
						@Override
						public int compare(Node n1, Node n2) {
							return n1.shortestDistance - n2.shortestDistance;
						}});

			Map<Integer, Node> unqueuedNodes = new HashMap<>(heightMap);

			unqueuedNodes.remove(curNode.id);

			while (curNode != null && curNode != end) {

				// Check each connected node and set the shortest distance.
				// North
				Node node = getNode(curNode.row-1, curNode.col, curNode, unvisitedNodes, unqueuedNodes);
				if (node != null) {
					setShortestDistance(node, curNode);
					unvisitedNodes.add(node);
				}

				// South
				node = getNode(curNode.row+1, curNode.col, curNode, unvisitedNodes, unqueuedNodes);
				if (node != null) {
					setShortestDistance(node, curNode);
					unvisitedNodes.add(node);
				}

				// East
				node = getNode(curNode.row, curNode.col+1, curNode, unvisitedNodes, unqueuedNodes);
				if (node != null) {
					setShortestDistance(node, curNode);
					unvisitedNodes.add(node);
				}

				// West
				node = getNode(curNode.row, curNode.col-1, curNode, unvisitedNodes, unqueuedNodes);
				if (node != null) {
					setShortestDistance(node, curNode);
					unvisitedNodes.add(node);
				}

				curNode = unvisitedNodes.poll();
			}

			if (curNode != null) {
				minDistance = Math.min(minDistance, curNode.shortestDistance);
			}
		}

		System.out.println("Min Steps " + minDistance);
	}

	private Node getNode(int row, int col, Node curNode,
			PriorityQueue<Node> unvisitedNodes, Map<Integer, Node> unqueuedNodes) {

		if (row < 0 || row >= maxRow) {
			return null;
		}
		if (col < 0 || col >= maxCol) {
			return null;
		}

		int locationId = Node.getLocationId(row, col);

		Node node = heightMap.get(locationId);

		// Only steps of height 1 are allowed up, any
		// number of steps down.
		if (node.height - curNode.height > 1) {
			return null;
		}

		if (unqueuedNodes.containsKey(locationId)) {

			node = unqueuedNodes.remove(locationId);

			node.shortestDistance = Integer.MAX_VALUE;
			node.shortestPath.clear();

			return node;
		}

		// Look in the unvisited nodes
		// The unvisited nodes are a prioritized queue
		// sorted by path length from the starting node
		if (unvisitedNodes.remove(node)) {
			return node;
		}

		return null;
	}

	private void setShortestDistance(Node node, Node curNode) {

		if (node.shortestDistance > curNode.shortestDistance+1) {
			node.shortestDistance = curNode.shortestDistance+1;
			node.shortestPath.clear();
			node.shortestPath.addAll(curNode.shortestPath);
			node.shortestPath.add(curNode);
		}
	}

}
