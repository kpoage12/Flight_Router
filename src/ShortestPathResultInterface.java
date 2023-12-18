import java.util.List;

public interface ShortestPathResultInterface<NodeType, EdgeType> {
    // Constructor (commented for reference)
    // ShortestPathResult(GraphADT<NodeType, EdgeType> graph, NodeType startAirport, NodeType destinationAirport)

    // Getter method to retrieve the route as a list of airports
    List<NodeType> getRoute();

    // Getter method to retrieve the list of miles to travel for each segment of the route
    List<EdgeType> getMilesPerSegment();

    // Getter method to retrieve the total miles for the route
    EdgeType getTotalMiles();
}
