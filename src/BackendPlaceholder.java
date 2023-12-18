import java.io.IOException;
import java.util.List;

/**
 * This is a placeholder class for Backend functionality
 * @param <NodeType>
 * @param <EdgeType>
 */
public class BackendPlaceholder<NodeType, EdgeType extends Number> implements BackendInterface<NodeType, EdgeType> {

    // GraphADT<String, Integer> graph; // instance of GraphADT

    /**
     * Reads the relevant data from the .dot file
     * @param filePath The path to the file containing the dataset.
     * @throws IOException if data is invalid
     */
    @Override
    public void readDataFromFile(String filePath) throws IOException {
        // Placeholder implementation
    }

    /**
     * Returns the shortest path from
     * @param startAirport      The starting airport.
     * @param destinationAirport The destination airport.
     * @return the shortest path result as an object
     */
    @Override
    public ShortestPathResult getShortestRoute(Object startAirport, Object destinationAirport){
        return null; // Placeholder implementation
    }

    /**
     * This method gets statistics about the flight graph
     * @return a formatted string containing dataset statistics
     */
    @Override
    public String getDatasetStatistics() {
        return null; // Placeholder implementation
    }

}

