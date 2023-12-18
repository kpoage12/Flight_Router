import java.util.ArrayList;
import java.util.List;

/**
 * This class holds the data of the shortest route between two airports
 */
public class ShortestPathResult<NodeType, EdgeType extends Number> implements ShortestPathResultInterface{

    private DijkstraGraph graph; // The graph of all airports and flights
    private NodeType start; // The starting airport
    private NodeType destination; // The destination airport

    /**
     * Creates a new ShortestPathResult object
     * @param graph the graph with all airports and flights
     * @param startAirport the start airport
     * @param destinationAirport the destination airport
     */
    ShortestPathResult(GraphADT<NodeType, EdgeType> graph, NodeType startAirport, NodeType destinationAirport){
        this.graph = (DijkstraGraph) graph;
        this.start = startAirport;
        this.destination = destinationAirport;
    }

    /**
     * Getter method to retrieve the route as a list of airports
     * @return the route as a list of airports
     */
    @Override
    public List getRoute() {
        return graph.shortestPathData(start, destination);
    }

    /**
     * Getter method to retrieve the list of miles to travel for each segment of the route
     * @return the list of miles to travel for each segment of the route
     */
    public List getMilesPerSegment() {
        // Create list to return
        ArrayList<Integer> list = new ArrayList<>();
        // Create a searchNode to traverse each edge along the route
        DijkstraGraph.SearchNode searchNode = graph.computeShortestPath(start, destination);
        // Loop through until we reach the start node
        while (searchNode.predecessor!=null){
            DijkstraGraph.SearchNode predecessor = searchNode.predecessor; // The predecessor of the current node
            // Find the edge between the two nodes and store its weight
            Integer edge = (Integer) graph.getEdge(predecessor.node.data, searchNode.node.data);
            // Add the edge at the beginning of the list
            list.add(0, edge);
            searchNode = predecessor;
        }
        return list;
    }

    /**
     * Getter method to retrieve the total miles for the route
     * @return the total mileage along the route
     */
    @Override
    public Object getTotalMiles() {
        return graph.shortestPathCost(start, destination);
    }
}
