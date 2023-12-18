import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class consists of the Frontend functionality of the project
 */
public class Frontend implements FrontendInterface {

    private BackendInterface backend; // instance of backend
    private Scanner input; // Scanner for user input

    /**
     * Frontend constructor method instantiates fields
     * @param backend instance of backend
     * @param input the scanner to use
     */
    public Frontend(BackendInterface backend, Scanner input) {
        this.backend = backend;
        this.input = input;
    }

    /**
     * This method displays the imain command of the interactive project
     */
    @Override
    public void DisplayMainCommand() {
        startCommandLoop();
    }
    /**
     * This method is for loading the file path
     * @param filePath The path to the CSV file containing the data.
     * @throws FileNotFoundException if file is not found
     * @throws IOException if file data is invalid
     */
    @Override
    public void subCommandLoadData(String filePath) throws FileNotFoundException {
        try {
            backend.readDataFromFile(filePath);
        }
        catch(IOException e) {
            throw new FileNotFoundException("File could not be found");
        }
    }

    /**
     * This method calls the backend's getDatasetStatistics method, prints out a formatted String
     */
    @Override
    public void subCommandDisplayStatistics() {
        String displayStats = backend.getDatasetStatistics();
        System.out.println("Dataset statistics:\n" + displayStats);
    }

    /**
     * This method prints out the shortest path from a start to a destination airport
     * @param startAirport User enters the starting Airport
     * @param endAirport User enters the destination Airport.
     */
    @Override
    public void subCommandDisplayShortestPath(String startAirport, String endAirport) {
        try {
            ShortestPathResult<String, Integer> shortestPath = backend.getShortestRoute(startAirport, endAirport);
            System.out.println("Shortest path from " + startAirport + " to " + endAirport + ":");
            ArrayList<String> path = (ArrayList<String>) shortestPath.getRoute();
            ArrayList<Integer> miles = (ArrayList<Integer>) shortestPath.getMilesPerSegment();
            for (int i=0; i<path.size()-1; i++){
                System.out.println(path.get(i));
                System.out.println("The flight to the next airport is " + miles.get(i) + " miles.");
            }
            System.out.println(path.get(path.size()-1));
        }
        catch(NoSuchElementException e) {
            System.out.println("No path found");
        }
    }

    /**
     * This method exits the loop
     */
    @Override
    public void subCommandExit() {
        System.out.println("Thank you for using Flight Router");
    }

    /**
     * Helper method to loop through different user input cases
     */
    private void startCommandLoop() {
        while (true) {
            System.out.println("Welcome to Flight Router! Please enter the name of the file you would like to load");
            String file = input.nextLine();
            try {
                subCommandLoadData(file);
                System.out.println("File was loaded.");
                break;
            } catch (FileNotFoundException e) {
                System.out.println("File could not be loaded");
            }
        }
        boolean keepRunning = true;
        while (keepRunning) {
            System.out.println("1: Show statistics");
            System.out.println("2: Find shortest route");
            System.out.println("3: Exit the application");

            int user = Integer.parseInt(input.nextLine());

            switch (user) {
                case 1: // Show statistics
                    subCommandDisplayStatistics();
                    continue;
                case 2: // Find Shortest Route
                    System.out.println("What is your starting airport?");
                    String startAirport = input.nextLine();
                    System.out.println("What is your destination airport?");
                    String destAirport = input.nextLine();
                    subCommandDisplayShortestPath(startAirport, destAirport);
                    continue;
                case 3://Exit the application
                    subCommandExit();
                    keepRunning = false;
                    continue;
                default:
                    System.out.println("Please select a valid number");
            }
        }

    }

}



