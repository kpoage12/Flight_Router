import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class tests the Backend class and ShortestPathResult classes and makes sure they function as expected
 */
public class BackendDeveloperTests {

    /**
     * This method tests the readDataFromFile() method in the Backend class and makes sure the data associated with the
     * graph is correct
     */
    @Test
    public void testReadDataFromFile(){
        // Surround with a try/catch to make sure the readDataFromFile() method doesn't throw an exception
        try {
            // Create the map and graph that should be used in the backend class
            PlaceholderMap map = new PlaceholderMap();
            DijkstraGraph<String, Integer> graph = new DijkstraGraph(map);
            // Create a backend object
            Backend backend = new Backend(graph);
            // Read the data from a test file
            backend.readDataFromFile("src/SmallFlight.dot");

            // Check that the graph now contains the correct number of nodes and edges represented in the SmallFlight
            // file
            int numberOfNodes = graph.getNodeCount();
            int numberOfEdges = (graph.getEdgeCount()/2);

            Assertions.assertEquals(numberOfEdges, 10);
            Assertions.assertEquals(numberOfNodes, 11);

        }
        // Fails if an exception is thrown in readDataFromFile() method
        catch(IOException e){
            e.printStackTrace();
            System.out.println("The file was not found");
            Assertions.fail();
        }
    }

    /**
     * This method checks the functionality of the getDatasetStatistics method from the backend class which should
     * return the number of flights, airports, and total mileage of the dataset
     */
    @Test
    public void testGetDatasetStatistics(){
        // Surround with a try/catch to make sure the readDataFromFile() method doesn't throw an exception
        try {
            // Create the map and graph that should be used in the backend class
            PlaceholderMap map = new PlaceholderMap();
            DijkstraGraph<String, Integer> graph = new DijkstraGraph(map);
            // Create a backend object
            Backend backend = new Backend(graph);
            // Read the data from a test file
            backend.readDataFromFile("src/SmallFlight.dot");

            String actual = backend.getDatasetStatistics();
            String expected = "The number of airports in the graph is 11, the number of flights is 10, and the total miles is 9937.";

            Assertions.assertEquals(actual, expected);
        }
        // Fails if an exception is thrown in readDataFromFile() method
        catch (IOException e){
            e.printStackTrace();
            System.out.println("The file was not found");
            Assertions.fail();
        }

    }

    /**
     * This method checks the functionality of the getShortestRoute() method in the backend class and the getRoute()
     * method in the ShortestPathResult class which should return a list of airports from the start to end airport that
     * make the shortest trip
     */
    @Test
    public void testGetShortestRouteAndGetRoute(){
        // Surround with a try/catch to make sure the readDataFromFile() method doesn't throw an exception
        try {
            // Create the map and graph that should be used in the backend class
            PlaceholderMap map = new PlaceholderMap();
            DijkstraGraph<String, Integer> graph = new DijkstraGraph(map);
            // Create a backend object
            Backend backend = new Backend(graph);
            // Read the data from a test file
            backend.readDataFromFile("src/SmallFlight.dot");

            // Check if the shortestPathResult correctly returns the airports along the route
            ShortestPathResult<String, Integer> shortestPathResult = backend.getShortestRoute("BWI", "STL");
            String shortestPathActual =  shortestPathResult.getRoute().toString();
            String shortestPathExpected = "[BWI, DFW, BNA, DAL, STL]";

            Assertions.assertEquals(shortestPathExpected, shortestPathActual);

        }
        // Fails if an exception is thrown in readDataFromFile() method
        catch (IOException e){
            e.printStackTrace();
            System.out.println("The file was not found");
            Assertions.fail();
        }

    }

    /**
     * This method checks the functionality of the getShortestRoute() method in the backend class and the
     * getMilesPerSegment() method in the ShortestPathResult class which should return a list of the miles per flight
     * taken along the shortest route.
     */
    @Test
    public void testGetShortestRouteAndGetMilesPerSegment(){
        // Surround with a try/catch to make sure the readDataFromFile() method doesn't throw an exception
        try {
            // Create the map and graph that should be used in the backend class
            PlaceholderMap map = new PlaceholderMap();
            DijkstraGraph<String, Integer> graph = new DijkstraGraph(map);
            // Create a backend object
            Backend backend = new Backend(graph);
            // Read the data from a test file
            backend.readDataFromFile("src/SmallFlight.dot");

            // Check if the shortestPathResult class correctly returns the miles per segment along each flight in the
            // route
            ShortestPathResult<String, Integer> shortestPathResult = backend.getShortestRoute("BWI", "STL");
            String milesPerSegmentActual =  shortestPathResult.getMilesPerSegment().toString();
            String milesPerSegmentExpected = "[1216, 631, 623, 546]";
            Assertions.assertEquals(milesPerSegmentActual, milesPerSegmentExpected);
        }
        // Fails if an exception is thrown in readDataFromFile() method
        catch (IOException e){
            e.printStackTrace();
            System.out.println("The file was not found");
            Assertions.fail();
        }

    }

    /**
     * This method checks the functionality of the getShortestRoute() method in the backend class and the
     * getTotalMiles() method in the ShortestPathResult class which should return a double with the cost of all flights
     * taken along the path from the start airport and destination airport.
     */
    @Test
    public void testGetShortestRouteAndGetTotalMiles(){
        // Surround with a try/catch to make sure the readDataFromFile() method doesn't throw an exception
        try {
            // Create the map and graph that should be used in the backend class
            PlaceholderMap map = new PlaceholderMap();
            DijkstraGraph<String, Integer> graph = new DijkstraGraph(map);
            // Create a backend object
            Backend backend = new Backend(graph);
            // Read the data from a test file
            backend.readDataFromFile("src/SmallFlight.dot");

            // Check if the shortestPathResult correctly calculates the total mileage along the route
            ShortestPathResult<String, Integer> shortestPathResult = backend.getShortestRoute("BWI", "STL");
            double shortestPathCost = (double) shortestPathResult.getTotalMiles();
            Assertions.assertEquals(shortestPathCost, 3016.0);

        }
        // Fails if an exception is thrown in readDataFromFile() method
        catch (IOException e){
            e.printStackTrace();
            System.out.println("The file was not found");
            Assertions.fail();
        }

    }

    /**
     * This method tests the functionality of the frontend class when given a backend reference. It tests if the
     * DisplayMainCommand() prints the expected response and exits the app when prompted
     */
    @Test
    public void testIntegrationDisplayCommandLoopAndExitApp(){
        // Create a TextUITester to prompt the frontend
            TextUITester invalidPrompt = new TextUITester("src/SmallFlight.dot\n3\n");

            // Create the map and graph that should be used in the backend class
            PlaceholderMap map = new PlaceholderMap();
            DijkstraGraph<String, Integer> graph = new DijkstraGraph(map);
            // Create a backend object
            Backend backend = new Backend(graph);
            Scanner scan = new Scanner(System.in);
            // Create a frontend object
            Frontend frontend = new Frontend(backend, scan);
            // Check if the string output equals the expected output
            frontend.DisplayMainCommand();
            String actual = invalidPrompt.checkOutput();
            scan.close();

            String expected = "Welcome to Flight Router! Please enter the name of the file you would like to load\n" +
                    "File was loaded.\n" +
                    "1: Show statistics\n" +
                    "2: Find shortest route\n" +
                    "3: Exit the application\n" +
                    "Thank you for using Flight Router\n" +
                    "Please select a valid number\n";

            Assertions.assertEquals(expected, actual);

    }

    /**
     * This method tests the functionality of the frontend class when given a backend reference. It tests if the
     * subCommandDisplayStatistics() prints the expected response after loading a file with the subCommandloadData()
     * method
     */
    @Test
    public void testIntegrationLoadFileAndSubCommandDisplayStatistics() {
            // Create a TextUITester to prompt the frontend
            TextUITester flights = new TextUITester("src/SmallFlight.dot\n2\nBWI\nSTL\n3\n");
            // Create the map and graph that should be used in the backend class
            PlaceholderMap map = new PlaceholderMap();
            DijkstraGraph<String, Integer> graph = new DijkstraGraph(map);
            // Create a backend object
            Backend backend = new Backend(graph);
            Scanner scan = new Scanner(System.in);
            // Create a frontend object
            Frontend frontend = new Frontend(backend, scan);

            // Check if the string output equals the expected output
            frontend.DisplayMainCommand();
            String actual = flights.checkOutput();
            scan.close();

            String expected = "Welcome to Flight Router! Please enter the name of the file you would like to load\n" +
                    "File was loaded.\n" +
                    "1: Show statistics\n" +
                    "2: Find shortest route\n" +
                    "3: Exit the application\n" +
                    "What is your starting airport?\n" +
                    "What is your destination airport?\n" +
                    "Shortest path from BWI to STL:\n" +
                    "BWI\n" +
                    "The flight to the next airport is 1216 miles.\n" +
                    "DFW\n" +
                    "The flight to the next airport is 631 miles.\n" +
                    "BNA\n" +
                    "The flight to the next airport is 623 miles.\n" +
                    "DAL\n" +
                    "The flight to the next airport is 546 miles.\n" +
                    "STL\n" +
                    "1: Show statistics\n" +
                    "2: Find shortest route\n" +
                    "3: Exit the application\n" +
                    "Thank you for using Flight Router\n" +
                    "Please select a valid number\n";

            Assertions.assertEquals(expected, actual);
    }

    /**
     * This method tests the functionality of the Frontend classes subCommandLoadDataFromFile() and subCommandExit()
     * methods
     */
    @Test
    public void BackendFrontendTestSubCommandExit(){

        TextUITester flights = new TextUITester("src/SmallFlight.dot\n3\n");
        // Create the map and graph that should be used in the backend class
        PlaceholderMap map = new PlaceholderMap();
        DijkstraGraph<String, Integer> graph = new DijkstraGraph(map);
        // Create a backend object
        Backend backend = new Backend(graph);
        Scanner scan = new Scanner(System.in);
        // Create a frontend object
        Frontend frontend = new Frontend(backend, scan);

        frontend.DisplayMainCommand();
        String actual = flights.checkOutput();
        scan.close();

        String expected = "Welcome to Flight Router! Please enter the name of the file you would like to load\n" +
                "File was loaded.\n" +
                "1: Show statistics\n" +
                "2: Find shortest route\n" +
                "3: Exit the application\n" +
                "Thank you for using Flight Router\n" +
                "Please select a valid number\n";

        Assertions.assertEquals(expected, actual);

    }

    /**
     * This method checks the functionality of the Frontend when given an unknown file
     */
    @Test
    public void BackendFrontendTestIncorrectFile(){
        TextUITester flights = new TextUITester("src/s.dot\n3\n");
        // Create the map and graph that should be used in the backend class
        PlaceholderMap map = new PlaceholderMap();
        DijkstraGraph<String, Integer> graph = new DijkstraGraph(map);
        // Create a backend object
        Backend backend = new Backend(graph);
        Scanner scan = new Scanner(System.in);
        // Create a frontend object
        Frontend frontend = new Frontend(backend, scan);
        frontend.DisplayMainCommand();
        String actual = flights.checkOutput();
        scan.close();

        Assertions.assertTrue(actual.contains("Error loading file"));

    }

}


