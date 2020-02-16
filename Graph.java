import java.util.LinkedList;
import java.util.Stack;

/**
 * A class which implements a graph data structure.
 * @author Sean Kurtz
 * @version 1.0
 */
public class Graph {
	// A reference to the vertex array.
	private String vertex[];
	// A reference to the adjacency matrix array.
	private int edge[][];
	// The maximum number of vertices the graph can hold.
	int maxVertices;
	// The number of vertices in the graph.
	int numberVertices;
	
	/**
	 * Constructs a graph with size given by the parameter.
	 * @param size The maximum number of vertices the graph can hold.
	 */
	public Graph(int size) {
		// Allocate a vertex array.
		vertex = new String[size];
		// Initializes the adjacency matrix to zeroes.
		edge = new int[size][size];
		maxVertices = size;
		numberVertices = 0;
	}
	/**
	 * A method which inserts a new vertex into the graph.
	 * @param vertexNumber The vertex-number of the vertex we are going to be inserting. Basically an index.
	 * @param newVertex The value that the vertex contains.
	 * @return true if we inserted the vertex, false otherwise.
	 */
	public boolean insertVertex(int vertexNumber, String newVertex) {
		// If we are going to go over our max
		if (vertexNumber >= maxVertices) {
			// Return false
			return false;
		}
		// Insert the vertex at vertex number
		vertex[vertexNumber] = newVertex;
		// Increment vertex counter and return true.
		numberVertices++;
		return true;
	}
	/**
	 * A method which inserts a new edge into the graph.
	 * @param fromVertex The vertex the edge is starting from
	 * @param toVertex The vertex the edge is going to.
	 * @param edgeWeight The weight of the edge.
	 * @return true if we inserted the edge, false otherwise.
	 */
	public boolean insertEdge(int fromVertex, int toVertex, int edgeWeight) {
		// If either of the vertices we are trying to connect are null
		if (vertex[fromVertex] == null || vertex[toVertex] == null) { 
			// We cannot insert the edge.
			return false;
		}
		// Insert the edge. This graph structure I have implemented is not directed. So make sure it is symmetric.
		edge[fromVertex][toVertex] = edgeWeight;
		edge[toVertex][fromVertex] = edgeWeight;
		return true;
	}
	/**
	 * A method which traverses the graph depth-first from a given vertex
	 * @param startVertex The vertex to start from.
	 */
	public void traverseDepthFirst(int startVertex) {
		// The vertex number we are on, and the stack to track the vertices.
		int v;
		Stack<Integer> stack = new Stack<Integer>();
		// Keep track of all the vertices we have visited.
		boolean visited[] = new boolean[numberVertices];
		// Initialize all the vertices to having not been visited.
		for (int i = 0; i < numberVertices; i++) {
			if (vertex[i] != null) {
				visited[i] = false;
			}
		}
		// Push the first vertex onto the stack. 
		stack.push(startVertex);
		// Mark it as visited.
		visited[startVertex] = true;
	
		// While its not empty
		while (!stack.isEmpty()) {
			// The vertex we are on is the item on top of stack
			v = stack.pop();
			// Print that vertex' value
			System.out.println(vertex[v]);
			// Move along the rows
			for (int column = 0; column < numberVertices; column++) {
				// If there is an edge we havent seen before
				if (edge[v][column] > 0 && !visited[column]) {
					// Push that location onto stack and set visited.
					stack.push(column);
					visited[column] = true;
				}
			}
		}
	}
	/**
	 * A method which traverses the graph breadth-first from a given vertex.
	 * @param startVertex The vertex to start from.
	 */
	public void traverseBreadthFirst(int startVertex) {
		// The vertex number we are on
		int v;
		// Using a linked list as a queue.
		LinkedList<Integer> queue = new LinkedList<Integer>();
		// Remember where we have visited.
		boolean visited[] = new boolean[numberVertices];
		// Initialize all the vertex' visited values to not being visited.
		for (int i = 0; i < numberVertices; i++) {
			if (vertex[i] != null) {
				visited[i] = false;
			}
		}
		// Add the first vertex to the queue and mark visited.
		queue.add(startVertex);
		visited[startVertex] = true;
		// While the queue isn't empty
		while(queue.size() != 0) {
			// Take the item at front of list and output it.
			v = queue.poll();
			System.out.println(vertex[v]);
			
			// If there is an edge we haven't seen before.
			for (int i = 0; i < numberVertices; i++) {
				if (edge[v][i] > 0 && !visited[i]) {
					// add the location to the queue and mark it as visited.
					queue.add(i);
					visited[i] = true;
				}
			}
		}
	}
	/**
	 * A method that outputs the edges (weights) in the graph.
	 */
	public void outputWeightMatrix() {
		printMatrix(edge);
	}
	/**
	 * A method that outputs the vertices in the graph.
	 */
	public void showAllVertices() {
		for (int i = 0; i < numberVertices; i++) {
			System.out.println(vertex[i]);
		}
	}
	/**
	 * A method that performs a quicksort on the vertices and then outputs them.
	 */
	public void sort() {
		quickSort(vertex, 0, numberVertices - 1);
		showAllVertices();
	}
	/**
	 * A method to perform quicksort on a list of items from a leftIndex to a rightIndex.
	 * @param items The items to sort
	 * @param leftIndex The index to start from
	 * @param rightIndex The index to go to.
	 */
	public static void quickSort(String[] items, int leftIndex, int rightIndex) {
		int i, j,  partitionSize;
		String pivotValue, temp;
		
		partitionSize = rightIndex - leftIndex + 1;
		// If there is only one item to be sorted
		if (partitionSize <= 1) {
			// Return.
			return;
		}
		// Sample the pivot value from the middle of the partition.
		pivotValue = items[(leftIndex + rightIndex) / 2];
		i = leftIndex;
		j = rightIndex;
		
		do {
			// While the thing at i is less than the pivot increment i
			while(items[i].compareTo(pivotValue) < 0)
				i++;
			// While the thing at j is greater than the pivot decrement j
			while(items[j].compareTo(pivotValue) > 0)
				j--;
			// Once that has stopped and the 'pointers' haven't crossed
			if (i <= j) {
				// Swap the items.
				temp = items[i];
				items[i] = items[j];
				items[j] = temp;
				// Move leftIndex up and rightIndex down.
				i++;
				j--;
			}
		} while(i <= j); // 'pointers' have not crossed.
		
		// Sort the left partition
		quickSort(items, leftIndex, j);
		// Sort the right partition.
		quickSort(items, i, rightIndex);
	}
	/**
	 * A method which outputs the minimum spanning tree of the graph. This outputs as a matrix.
	 */
	public void minimumSpanningTree() {
		// Default value for a location with no edge.
		int noEdge = Integer.MAX_VALUE;
		// The number of vertices that have been processed.
		int numVerticesIncluded;
		// The vertices we have included.
		int verticesIncluded[] = new int[numberVertices];
		// A copy of the weights matrix and a matrix that will represent the minimum spanning tree.
		int weightsCopy[][] = new int[numberVertices][numberVertices];
		int mst[][] = new int[numberVertices][numberVertices];
		// Copy the values from the stored weights into our new matrix that we will manipulate.
		for (int i = 0; i < numberVertices; i++) {
			for (int j = 0; j < numberVertices; j++) {
				if (edge[i][j] == 0) {
					weightsCopy[i][j] = noEdge;
				}
				else {
					weightsCopy[i][j] = edge[i][j];
				}
			}
		}
			
		// add vertex 0 to the tree
		verticesIncluded[0] = 0;
		numVerticesIncluded = 1;
		// eliminate the edges to vertex 0
		for (int i = 0; i < numberVertices; i++) {
			weightsCopy[i][0] = noEdge;
		}
		
		
		// While we haven't done all the vertices.
		while(numVerticesIncluded < numberVertices) {

			// Declare a new array so we can return multiple values.
			int[] nums = new int[3];
			// Find the minimum edge weigh.
			findMinEdgeWeight(weightsCopy, verticesIncluded, numVerticesIncluded, nums);
			// Unpack the values.
			int weightsMin = nums[0];
			int rowMin = nums[1];
			int colMin = nums[2];
			
			// Eliminate all the incident paths.
			for (int i = 0; i < numberVertices; i++) {
				weightsCopy[i][colMin] = noEdge;
			}
			// Add the minimum weighted edge to the tree. Symmentrically.
			mst[rowMin][colMin] = weightsMin;
			mst[colMin][rowMin] = weightsMin;
			// Add the vertex to the included list and increment the number of vertices included.
			verticesIncluded[numVerticesIncluded] = colMin;
			numVerticesIncluded++;
		}

		printMatrix(mst);
		
	}
	/**
	 * A method which finds the shortest path length from the source vertex to every other vertex.
	 * @param sourceVertex The vertex we wish to start from
	 */
	public void shortestPath(int sourceVertex) {
		// Create an absurd value to represent noPath.
		int noPath = Integer.MAX_VALUE;
		// Store the minimum path lengths going to the different vertices.
		int[] minPathLengths = new int[numberVertices];
		// Keep track of the places we have been
		boolean[] visited = new boolean[numberVertices];
		
		// Initialize the minimum path lengths to no path for the time being.
		for (int i = 0; i < numberVertices; i++) {
			minPathLengths[i] = noPath;
		}
		
		// Set the minimum path length from sourceVertex to itself to 0.
		minPathLengths[sourceVertex] = 0;
		// For every vertex
		for (int i = 0; i < numberVertices; i++) {
			// Get the vertex with the minimum distance from the vertex that we have been, set that place to visited.
			int minimum = noPath;
			int minVertex = noPath;
			// Go through all the vertices
			for (int k = 0; k < numberVertices; k++) {
				// If the minPathLength of that vertex is less than our minimum and we haven't visited before
				if (minPathLengths[k] < minimum && !visited[k]) {
					// Our minimum is now a new minimum.
					minimum = minPathLengths[k];
					// We have a new minimum vertex.
					minVertex = k;
				}
			}
			
			// We have now completed the vertex we just searched for.
			visited[minVertex] = true;
			
			// For every vertex v
			for (int v = 0; v < numberVertices; v++) {
				// If the edge is not zero and not noPath (both represent absent edges) and we haven't visited before
				if (edge[minVertex][v] > 0 && edge[minVertex][v] < noPath && !visited[v]) {
					// Make sure to take the sum from minPathLengths and the edge value
					int minLength = edge[minVertex][v] + minPathLengths[minVertex];
					// If the newVertex is less than the minimum path length at the location v
					if (minLength < minPathLengths[v]) {
						// Replace the minimum path length with the new vertex
						minPathLengths[v] = minLength;
					}
					
				}
			}
		}
		// Print the path lengths to all the different vertices.
		for (int i = 0; i < numberVertices; i++) {
			System.out.println("Shortest path length from vertex " + sourceVertex + " to vertex " + i + " is "+ minPathLengths[i]);
		}
		
	}
	/**
	 * A method which finds the minimum edge weight and packs the minWeight as well as row and col locations
	 * of that weight into an integer array.
	 * @param numVertices The number of vertices
	 * @param weights The weights of our matrx
	 * @param verticesIncluded The vertices that have already been included in the process of building mst.
	 * @param numVerticesIncluded The number of vertices already completed.
	 * @param nums The values we want to return including minWeight, row number of minWeight, col number of minWeight.
	 */
	private void findMinEdgeWeight(int[][] weights, int[] verticesIncluded, int numVerticesIncluded, int[] nums) {
		// Initialize the values we are searching for to infinity.
		int rowMin = Integer.MAX_VALUE;
		int colMin = Integer.MAX_VALUE;
		int weightMin = Integer.MAX_VALUE;
		// For every vertex included
		for (int i = 0; i < numVerticesIncluded; i++) {
			// get the row number we are going to search
			int row = verticesIncluded[i];
			// For every vertex
			for (int j = 0; j < numberVertices; j++) {
				// if the weight is less than the minimum weight we have found so far.
				if (weights[row][j] < weightMin) {
					// we have a new minimum weight.
					weightMin = weights[row][j];
					// save the location
					rowMin = row;
					colMin = j;
				}
			}
			
		}
		// Pack the values into array to return.
		nums[0] = weightMin;
		nums[1] = rowMin;
		nums[2] = colMin;
	}
	/**
	 * A method which prints a 2d-array/matrix
	 * @param matrix The matrix we wish to print.
	 */
	public void printMatrix(int[][] matrix) {
		// For every row
		for (int i = 0; i < numberVertices; i++) {
			// For every col
			for (int j = 0; j < numberVertices; j++) {
				// If the value is infinite or zero print 0
				if (matrix[i][j] == Integer.MAX_VALUE || matrix[i][j] ==  0) {
					System.out.print("0" + " ");
				}
				// Otherwise there is a real value so print it.
				else {
					System.out.print(matrix[i][j] + " ");
				}
			}
			System.out.println();
		}
	}
}
