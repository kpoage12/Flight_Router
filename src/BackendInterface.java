import java.io.IOException;

public interface BackendInterface<NodeType, EdgeType extends Number> {
    // Constructor (commented for reference)
    // IndividualBackendInterface(GraphADT<NodeType, EdgeType> graph)

    /**
     * Reads data from a file and updates the graph with the data.
     *
     * @param filePath The path to the file containing the dataset.
     * @throws IOException If there's an issue reading the file.
     */
    void readDataFromFile(String filePath) throws IOException;

    /**
     * Gets the shortest route from a start to a destination airport in the dataset.
     *
     * @param startAirport      The starting airport.
     * @param destinationAirport The destination airport.
     * @return An instance of ShortestPathResult containing the shortest path information.
     */
    ShortestPathResult<NodeType, EdgeType> getShortestRoute(NodeType startAirport, NodeType destinationAirport);

    /**
     * Gets a string with statistics about the dataset.
     *
     * @return A string containing the number of nodes (airports), the number of edges (flights),
     * and the total miles (sum of weights) for all edges in the graph.
     */
    String getDatasetStatistics();
}
