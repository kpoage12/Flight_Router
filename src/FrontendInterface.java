/**
 * An interface for a class that implements the functionality of the frontend for the app. The
 * class has a constructor that references the backend and a Scanner instance to read user input.
 * It also has a method that starts the main command loop for the user and each of the sub menus.
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

interface FrontendInterface{
    /**
     * Constructor for the class that accepts reference to the backend and a Scanner instance.
     */
    //public FrontendInterface(BackendInterface backend, Scanner scanner);


    /**
     * This method will display a greeting to the user and its the beginning of
     * following sub-commands
     */
    public void DisplayMainCommand();

    /**
     * this method will display a prompt asking the user to specify and load the
     * file containing the airports edges, and total number of miles
     * @param filePath The path to the CSV file containing the data.
     * @throws FileNotFoundException If the file we are looking for does not exist.
     * @throws IOException           If an I/O error occurs while reading the file.

     */
    void subCommandLoadData(String filePath) throws FileNotFoundException, IOException;



    /**
     * this method will show statistics about the dataset includes the number of airports (nodes),
     * the number of edges (flights), and the total number of miles (sum of all edge weights) in the graph.
     */
    public void subCommandDisplayStatistics();

    /**
     * this method will asks the user for a start and destination airport,
     * then lists the shortest route between those airports, including all airports on the way,
     * the distance for each segment, and the total number of miles from start to destination airport
     * @param startAirPort User enters the starting Airport
     * @param endAirPort User enters the destination Airport.
     */
    public void subCommandDisplayShortestPath(String startAirPort, String endAirPort);


    /**
     * this method will ask the user if they want to
     * exit the app then exits if user wants to exit.
     */
    public void subCommandExit();



}

