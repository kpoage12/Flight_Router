import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Scanner;

/**
 * This class contains a variety of JUnit tests for the frontend functionality
 */
public class FrontendDeveloperTests {

    /**
     * This method tests the DisplayMainCommand() method
     */
    @Test
    public void testDisplayMainCommand() {
        TextUITester tester = new TextUITester("SmallFlight.dot\n3\n");
        Scanner scanner = new Scanner(System.in);
        Frontend testFront = new Frontend(new BackendPlaceholder(), scanner);
        testFront.DisplayMainCommand();
        String output = tester.checkOutput();
        scanner.close();
        Assertions.assertTrue(output.startsWith("Welcome to Flight Router!"));

    }

    /**
     * This method tests the loading of the sub commands after receiving user input
     */
    @Test
    public void testSubCommandLoadData() {
        TextUITester tester = new TextUITester("invalid.dot\n3\n");
        Scanner scanner = new Scanner(System.in);
        Frontend testFront = new Frontend(new BackendPlaceholder(), scanner);
        testFront.DisplayMainCommand();
        String output = tester.checkOutput();
        scanner.close();
        Assertions.assertFalse(output.contains("File could not be loaded"));

    }

    /**
     * This method tests the behavior of the displayed statistics of flight data
     */
    @Test
    public void testSubCommandDisplayStatistics() {
        TextUITester tester = new TextUITester("SmallFlight.dot\n1\n3\n");
        Scanner scanner = new Scanner(System.in);
        Frontend testFront = new Frontend(new BackendPlaceholder(), scanner);
        testFront.DisplayMainCommand();
        String output = tester.checkOutput();

        Assertions.assertTrue(output.contains("Dataset statistics:"));

    }


    /**
     * This method tests the shortest path method of frontend
     */
    @Test
    public void testSubCommandDisplayShortestPath() {
        TextUITester tester = new TextUITester("SmallFlight.dot\n3\n");
        Scanner scanner = new Scanner(System.in);
        Frontend testFront = new Frontend(new BackendPlaceholder(), scanner);
        testFront.DisplayMainCommand();
        String output = tester.checkOutput();

        Assertions.assertFalse(output.contains("What is your starting airport?"));

    }

    /**
     * This method tests subCommandExit
     */
    @Test
    public void testSubCommandExit() {
        TextUITester tester = new TextUITester("SmallFlight.dot\n3\n");
        Scanner scanner = new Scanner(System.in);
        Frontend testFront = new Frontend(new BackendPlaceholder(), scanner);
        testFront.DisplayMainCommand();
        String output = tester.checkOutput();

        Assertions.assertTrue(output.contains("Thank you for using Flight Router"));

    }

    /**
     * This method tests integration functionality by checking to see if the main command is displayed
     */
    @Test
    public void integrationTestWelcomeMessage() {
        TextUITester tester = new TextUITester("flights.dot\n3\n");
        Scanner sc = new Scanner(System.in);
        PlaceholderMap map = new PlaceholderMap();
        DijkstraGraph<String, Integer> graph = new DijkstraGraph(map);
        Backend backTest = new Backend(graph);
        Frontend frontTest = new Frontend(backTest, sc);
        frontTest.DisplayMainCommand();
        String out = tester.checkOutput();

        assertTrue(out.startsWith("Welcome to Flight Router!"));
    }

    /**
     * This method tests integration functionality by checking an invalid file
     */
    @Test
    public void integrationBackendTest2() {
        TextUITester tester = new TextUITester("invalid.dot\n3\n");
        Scanner sc = new Scanner(System.in);
        PlaceholderMap map = new PlaceholderMap();
        DijkstraGraph<String, Integer> graph = new DijkstraGraph(map);
        Backend backTest = new Backend(graph);
        Frontend frontTest = new Frontend(backTest, sc);
        frontTest.DisplayMainCommand();
        String out = tester.checkOutput();

        assertTrue(out.contains("Error loading file"));
    }


}


