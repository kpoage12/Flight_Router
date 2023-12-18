import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements the BackendInterface and stores a file of flights and airports in a DijkstraGraph and uses the
 * ShortestPathResult class to store the data from the shortest flight route from one airport to another. It also
 * creates a string with data from the dataset used to create the graph
 */
public class Backend<NodeType, EdgeType extends Number> implements BackendInterface{

    private DijkstraGraph graph; // The graph we are storing the airports (nodes) and flights (edges) in
    private int totalMiles = 0; // The total mileage in all flights in the dataset

    /**
     * Creates a new Backend object
     * @param graph the graph to store the dataset in
     */
    Backend(GraphADT<NodeType, EdgeType> graph){
        this.graph = (DijkstraGraph) graph;
    }

    /**
     * Reads data from a file and updates the graph with the data.
     *
     * @param filePath The path to the file containing the dataset.
     * @throws IOException If there's an issue reading the file.
     */
    @Override
    public void readDataFromFile(String filePath) throws IOException {
        // Make sure the file exists and if not, throw an IOException
        try {
            // Create the file and a scanner to read from the file
            File file = new File(filePath);
            Scanner fileScanner = new Scanner(file);
            // Keep reading from the scanner until the file doesn't have a line
            while (fileScanner.hasNextLine()){
                String line = fileScanner.nextLine().trim(); // The current line in the file
                // Use regex to see if the current line is an edge in the file
                if (line.matches("\"[A-Z][A-Z][A-Z]......[A-Z][A-Z][A-Z].*")){
                    // The starting airport should be from indexes 1-4 in the line
                    String airportFrom = line.substring(1,4);
                    // The destination airport should be from indexes 10-13 in the line
                    String airportTo = line.substring(10,13);
                    // Check if the graph contains a node with the data from either airport
                    if (!graph.containsNode(airportFrom)){
                        // If it does not contain this node, insert it into the graph as a node
                        graph.insertNode(airportFrom);
                    }
                    if (!graph.containsNode(airportTo)){
                        // If it does not contain this node, insert it into the graph as a node
                        graph.insertNode(airportTo);
                    }
                    // The weight should be from index 22 to index length-2 in the line
                    int weight = Integer.parseInt(line.substring(22, line.length()-2));
                    totalMiles+=weight; // Update the total miles with this new edge
                    // Because the edges are undirected, we must insert two new edges, one from the start airport to
                    // the end airport, and one from the end airport to the start airport
                    graph.insertEdge(airportFrom, airportTo, weight);
                    graph.insertEdge(airportTo, airportFrom, weight);
                }
            }
        }
        // Throw an exception if the file isn't found
        catch(Exception e){
            throw new IOException("File not found");
        }
    }


    /**
     * Gets the shortest route from a start to a destination airport in the dataset.
     *
     * @param startAirport      The starting airport.
     * @param destinationAirport The destination airport.
     * @return An instance of ShortestPathResult containing the shortest path information.
     */
    @Override
    public ShortestPathResult getShortestRoute(Object startAirport, Object destinationAirport) {
        // Create and return a new ShortestPathResult with the current graph, start airport, and destination airport
        return new ShortestPathResult(this.graph, startAirport, destinationAirport);
    }

    /**
     * Gets a string with statistics about the dataset.
     *
     * @return A string containing the number of nodes (airports), the number of edges (flights),
     * and the total miles (sum of weights) for all edges in the graph.
     */
    @Override
    public String getDatasetStatistics() {
        // Return a string with all the data from the graph
        String statistics = "The number of airports in the graph is " + graph.getNodeCount() + ", the number of flights " +
                "is " + (graph.getEdgeCount()/2) + ", and the total miles is " + this.totalMiles + ".";
        return statistics;
    }

    /**
     * Runs the program
     */
    public static void main(String[] args) {

        PlaceholderMap map = new PlaceholderMap();

        DijkstraGraph<String, Integer> graph = new DijkstraGraph(map);

        Backend backend = new Backend(graph);

        Scanner scan = new Scanner(System.in);
        Frontend frontend = new Frontend(backend, scan);
        frontend.DisplayMainCommand();
    }

}
