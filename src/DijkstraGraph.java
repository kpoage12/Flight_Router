import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes. This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
        extends BaseGraph<NodeType, EdgeType>
        implements GraphADT<NodeType, EdgeType> {

    /**
     * While searching for the shortest path between two nodes, a SearchNode
     * contains data about one specific path between the start node and another
     * node in the graph. The final node in this path is stored in its node
     * field. The total cost of this path is stored in its cost field. And the
     * predecessor SearchNode within this path is referened by the predecessor
     * field (this field is null within the SearchNode containing the starting
     * node in its node field).
     *
     * SearchNodes are Comparable and are sorted by cost so that the lowest cost
     * SearchNode has the highest priority within a java.util.PriorityQueue.
     */
    protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;

        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }

        public int compareTo(SearchNode other) {
            if (cost > other.cost)
                return +1;
            if (cost < other.cost)
                return -1;
            return 0;
        }
    }

    /**
     * Constructor that sets the map that the graph uses.
     * @param map the map that the graph uses to map a data object to the node
     *        object it is stored in
     */
    public DijkstraGraph(MapADT<NodeType, Node> map) {
        super(map);
    }

    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations. The
     * SearchNode that is returned by this method is represents the end of the
     * shortest path that is found: it's cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references represent
     * all of the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *                                or when either start or end data do not
     *                                correspond to a graph node
     */

    protected SearchNode computeShortestPath(NodeType start, NodeType end) {

        // Check if either the start data or end data refer to a node in the graph. If not, throw an exception
        if (!this.containsNode(start) || !this.containsNode(end)){
            throw new NoSuchElementException("Must refer to a node in the graph!");
        }

        // Create the starting search node from the start data
        SearchNode startSearchNode = new SearchNode(nodes.get(start), 0, null);

        // Create a map to store the visited nodes
        PlaceholderMap<Node, Double> map = new PlaceholderMap();

        // Create a priority queue to store all the node paths
        PriorityQueue<SearchNode> pq = new PriorityQueue<>();

        // Add the start search node to the priority queue
        pq.add(startSearchNode);

        // Initialize a final search node
        SearchNode found = null;

        // Keep looking through the priority queue until it is empty
        while (!pq.isEmpty()){
            // Take the top priority (cheapest cost) search node
            SearchNode current = pq.poll();
            // If the current node is equal to the end data, it is the first time we are seeing this data and will
            // be the cheapest way to get to the end node. Break the loop and return the found search node
            if (current.node.data.equals(end)){
                found = current;
                break;
            }
            // If the map contains a key already, the current search node has a higher cost and we should move on to the
            // next search node in the priority queue
            if (map.containsKey(current.node)){
                continue;
            }
            // Otherwise, this is the first time visiting this node, and it should be added to the map and marked as
            // visited
            map.put(current.node, current.cost);

            // Loop through all the edges leaving the current node
            for (int i = 0; i<current.node.edgesLeaving.size(); i++){
                Node node = current.node.edgesLeaving.get(i).successor;
                Double cost = current.cost + (int)(current.node.edgesLeaving.get(i).data);
                // Create a new search node with the total cost and add it to the priority queue
                SearchNode addNode = new SearchNode(node, cost, current);
                pq.add(addNode);
            }


        }
        // If the found search node is still null, then there is no path from the start node to the end node, meaning
        // we throw an exception
        if (found==null){
            throw new NoSuchElementException("No path from start to end!");
        }
        // return the found search node
        return found;
    }

    /**
     * Returns the list of data values from nodes along the shortest path
     * from the node with the provided start value through the node with the
     * provided end value. This list of data values starts with the start
     * value, ends with the end value, and contains intermediary values in the
     * order they are encountered while traversing this shorteset path. This
     * method uses Dijkstra's shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     */
    public List<NodeType> shortestPathData(NodeType start, NodeType end) {
        // Create the search node from the start to end data
        SearchNode current = computeShortestPath(start, end);
        ArrayList list = new ArrayList();
        // loop through the predecessors until we reach the start node
        while (current != null) {
            list.add(0, current.node.data);
            current = current.predecessor;
        }
        // return the list
        return list;
    }

    /**
     * Returns the cost of the path (sum over edge weights) of the shortest
     * path freom the node containing the start data to the node containing the
     * end data. This method uses Dijkstra's shortest path algorithm to find
     * this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     */
    public double shortestPathCost(NodeType start, NodeType end) {
        // Return the cost of the search node from the start to end data
        SearchNode current = computeShortestPath(start, end);
        return current.cost;
    }


    /**
     * This test makes use of an example that we traced through in lecture, and confirms that the results of the
     * implementation matches what we previously computed by hand.
     */
    @Test
    public void test1(){
        //Create map to pass to DijkstraGraph
        PlaceholderMap map = new PlaceholderMap();

        //Create the graph and insert all nodes and connecting edges
        DijkstraGraph<String, Integer> graph = new DijkstraGraph(map);

        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");

        graph.insertEdge("A", "B", 1);
        graph.insertEdge("B", "F", 3);
        graph.insertEdge("A", "C", 2);
        graph.insertEdge("C", "D", 2);
        graph.insertEdge("D", "E", 2);
        graph.insertEdge("E", "F", 2);

        // Create a search node to see the shortest path between two nodes
        DijkstraGraph<String, Integer>.SearchNode current = graph.computeShortestPath("A", "F");
        //Create an arraylist to pass all the values in the shortest path
        ArrayList<String>  pathData = new ArrayList<>();
        while (current != null) {
            pathData.add(0, current.node.data);
            current = current.predecessor;
        }
        //Check if the data matches what should be passed into the search node
        Assertions.assertEquals("[A, B, F]", pathData.toString());
        //Check if the cost is equal to what we computed by hand
        Assertions.assertEquals(4, graph.shortestPathCost("A", "F"));

    }

    /**
     * This test makes use of the same example that we traced through in lecture, but tests if two different nodes
     * in the graph return the expected shortest path
     */
    @Test
    public void test2(){
        //Create map to pass to DijkstraGraph
        PlaceholderMap map = new PlaceholderMap();

        //Create the graph and insert all nodes and connecting edges
        DijkstraGraph<String, Integer> graph = new DijkstraGraph(map);

        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");

        graph.insertEdge("A", "B", 1);
        graph.insertEdge("B", "F", 3);
        graph.insertEdge("A", "C", 2);
        graph.insertEdge("C", "D", 2);
        graph.insertEdge("D", "E", 2);
        graph.insertEdge("E", "F", 2);

        // Create a search node to see the shortest path between two nodes
        DijkstraGraph<String, Integer>.SearchNode current = graph.computeShortestPath("C", "E");
        //Create an arraylist to pass all the values in the shortest path
        ArrayList<String>  pathData = new ArrayList<>();
        while (current != null) {
            pathData.add(0, current.node.data);
            current = current.predecessor;
        }
        //Check if the data matches what should be passed into the search node
        Assertions.assertEquals("[C, D, E]", pathData.toString());
        //Check if the cost is equal to what we computed by hand
        Assertions.assertEquals(4, graph.shortestPathCost("C", "E"));
    }

    /**
     * This test checks if a NoSuchElement exception is thrown when computing the shortest path between two nodes
     * that arent connected
     */
    @Test
    public void test3(){
        //Create map to pass to DijkstraGraph
        PlaceholderMap map = new PlaceholderMap();

        //Create the graph and insert all nodes and connecting edges
        DijkstraGraph<String, Integer> graph = new DijkstraGraph(map);

        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");

        graph.insertEdge("A", "B", 1);
        graph.insertEdge("B", "C", 1);
        graph.insertEdge("D", "C", 1);

        // Check if the NoSuchElementException is thrown
        Assertions.assertThrows(NoSuchElementException.class, () -> graph.computeShortestPath("A", "D"));
    }



}

