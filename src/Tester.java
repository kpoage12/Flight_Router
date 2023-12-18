import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Tester {
    public static void main(String[] args) {

        PlaceholderMap map = new PlaceholderMap();

        DijkstraGraph<String, Integer> graph = new DijkstraGraph(map);

        Backend backend = new Backend(graph);

        Scanner scan = new Scanner(System.in);
        Frontend frontend = new Frontend(backend, scan);
        frontend.DisplayMainCommand();
    }
}
