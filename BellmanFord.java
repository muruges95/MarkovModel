package sg.edu.nus.cs2020;

public class BellmanFord implements IBellmanFord{
	
	// Stores the Adjacency Matrix of the current Graph to run Bellman-Ford with
	private AdjacencyMatrixGraph m_amg;
	
	// Tracks who is the current parent vertex, for each vertex in the graph
	private int[] m_predecessors;
	
	// Store the total distance of each vertex from the source vertex
	private int[] m_distanceFromSource;
	
	// Stores the source and destination vertices chosen for this iteration
	private int m_source;
	private int m_destination;
	
	
	public BellmanFord(AdjacencyMatrixGraph amg) {
		m_amg = amg;	
	}
	
	// Construct the shortest path from source to destination
	public void constructPath(int source, int destination) {
		if (m_amg == null){ // in case the adjacency matrix passed was null
			throw new NullPointerException("Given adjacency matrix is null.");
		}
		int numVertices = m_amg.getNumberOfVertices();
		m_source = source;
		m_destination = destination;
		m_distanceFromSource = new int[numVertices];
		m_predecessors = new int[numVertices];
		// Initialize all points on graph to have max distance from source
		for (int v = 0; v < numVertices; v++){
			m_distanceFromSource[v] = Integer.MAX_VALUE;
			m_predecessors[v] = -1;
		}
		// only have the source node itself have a distance 0 from itself
		m_distanceFromSource[source] = 0;
		int currentWeight = 0;
		// do relaxation of every edge
		// need to V - 1 times of relaxation in the worst case
		// when we have the longest possible path without a cycle involving
		// all the vertices
		for (int i = 1; i < numVertices; i++){ // for each iteration
			for (int j = 0; j < numVertices; j++){ // for every possible start of edge
				for (int k =0; k < numVertices; k++){ // for every possible end of edge
					currentWeight = m_amg.getEdgeWeight(j, k);
					// if new est. dist less than old est. dist
					if (currentWeight < m_distanceFromSource[k] - m_distanceFromSource[j]){
						// update dist and predecessor
						m_distanceFromSource[k] = m_distanceFromSource[j] + currentWeight;
						m_predecessors[k] = j;
					}
				}
			}
		}
		// By the end we would have gotten actual distance from source
		// and the predecessor of every point involved
		
	}
	
	// Checks if at least 1 negative cycle exists in the Graph
	public boolean hasNegativeCycle() {
		// first need to fill in the dist from source array with some source and destination
		constructPath(0, 0);
		int numVertices = m_amg.getNumberOfVertices();
		// now we check if it is possible to further reduce the dist from source to a node even after going through V - 1 iterations
		// of relaxing every edge in the graph. It is not possible if there are no negative cycles as if the edge is a positive number
		// the new predicted dist from source has to be higher for the destination node than the previous value. 
		for (int j = 0; j < numVertices; j++){
			for (int k = 0; k < numVertices; k++){
				if (m_amg.getEdgeWeight(j, k) < m_distanceFromSource[k] - m_distanceFromSource[j]){
					return true;
				}
			}
		}
		return false;
	}
	
	// Produces the String for printing the shortest path
	public String getPath() {
		
		int currentVertex = this.m_destination;
		
		StringBuilder prepender = new StringBuilder();
		StringBuilder resultHolder = new StringBuilder("" + this.m_destination);
		
		while (currentVertex != this.m_source) {
			
			currentVertex = this.m_predecessors[currentVertex];
			
			prepender.append(currentVertex);
			prepender.append(" -> ");
			prepender.append(resultHolder.toString());
			resultHolder.replace(0, resultHolder.length(), prepender.toString());
			prepender.delete(0, prepender.length());
		}
		
		return resultHolder.toString();
	}
	
	public static void main(String[] args) {
		
		// Example use
//		AdjacencyMatrixGraph amg = new AdjacencyMatrixGraph("testFileIncremental.txt");
//		BellmanFord bf = new BellmanFord(amg);
//		
//		System.out.println("Running Bellman-Ford on testFileIncremental...");
//		System.out.println("Has negative cycle(s): " + bf.hasNegativeCycle());
//		System.out.println("Shortest path: " + bf.getPath());
//		for (int i = 0; i < amg.getGraphMatrix().length;i++){
//			for (int k = 0; k < amg.getGraphMatrix()[i].length;k++){
//				System.out.print(amg.getGraphMatrix()[i][k] + ", ");
//			}
//			System.out.println("\n");
//		}
		
		int[] arr = new int[127];
		System.out.print(arr[126]++);
		System.out.print(arr[126]++);
		
	}

}
