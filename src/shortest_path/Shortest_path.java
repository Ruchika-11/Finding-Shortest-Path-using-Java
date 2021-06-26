package shortest_path;
import java.util.*;

public class Shortest_path {

    private int distances[];
    private int distancematrix[][];
    private int numberofvertices;
    private Set<Integer> settled;
    private Set<Integer> unsettled;
    private int adjacencyMatrix[][];
    public static final int MAX_VALUE = 999;
    
    public Shortest_path(int numberofvertices)
    {
        this.numberofvertices = numberofvertices;
        distances = new int[numberofvertices + 1];
        distancematrix = new int[numberofvertices + 1][numberofvertices + 1];
        settled = new HashSet<Integer>();
        unsettled = new HashSet<Integer>();
        adjacencyMatrix = new int[numberofvertices + 1][numberofvertices + 1];
    }
    
    public void BellmanFordEvaluation(int source, int adjacencymatrix[][])
    {
        boolean cycle = false;
        for (int node = 1; node <= numberofvertices; node++)
        {
            distances[node] = MAX_VALUE;
        }
 
        distances[source] = 0;
        for (int node = 1; node <= numberofvertices - 1; node++)
        {
            for (int sourcenode = 1; sourcenode <= numberofvertices; sourcenode++)
            {
                for (int destinationnode = 1; destinationnode <= numberofvertices; destinationnode++)
                {
                    if (adjacencymatrix[sourcenode][destinationnode] != MAX_VALUE)
                    {
                        if (distances[destinationnode] > distances[sourcenode] 
                                + adjacencymatrix[sourcenode][destinationnode])
                            distances[destinationnode] = distances[sourcenode]
                                + adjacencymatrix[sourcenode][destinationnode];
                    }
                }
            }
        }
        for (int sourcenode = 1; sourcenode <= numberofvertices; sourcenode++)
        {
            for (int destinationnode = 1; destinationnode <= numberofvertices; destinationnode++)
            {
                if (adjacencymatrix[sourcenode][destinationnode] != MAX_VALUE)
                {
                    if (distances[destinationnode] > distances[sourcenode]
                           + adjacencymatrix[sourcenode][destinationnode])
                    {
                        System.out.println("The Graph contains negative egde cycle");
                        System.out.println("Hence, Single Source Shortest Path cannot be found.");
                        cycle = true;
                    }  
                }
            }
        }
        if(!cycle)
        {
            System.out.println("The Shorted Path from Source Node to all the Nodes are: ");
            for (int vertex = 1; vertex <= numberofvertices; vertex++)
            {
                System.out.println("Distance of Source Node " + source + " to Destination Node "
                          + vertex + " is " + distances[vertex]);
            } 
        }
        System.out.println("_____________________________________________________");
    }
    public void floydwarshall(int adjacencymatrix[][])
    {
        for (int source = 1; source <= numberofvertices; source++)
        {
            for (int destination = 1; destination <= numberofvertices; destination++)
            {
                distancematrix[source][destination] = adjacencymatrix[source][destination];
            }
        }
        for (int intermediate = 1; intermediate <= numberofvertices; intermediate++)
        {
            for (int source = 1; source <= numberofvertices; source++)
            {
                for (int destination = 1; destination <= numberofvertices; destination++)
                {
                    if (distancematrix[source][intermediate] + distancematrix[intermediate][destination]
                         < distancematrix[source][destination])
                        distancematrix[source][destination] = distancematrix[source][intermediate] 
                            + distancematrix[intermediate][destination];
                }
            }
        }
        System.out.print("   |");
        for (int source = 1; source <= numberofvertices; source++)
            System.out.print("\t" + source);
        System.out.println();
        System.out.println("---|--------------------------------");
        for (int source = 1; source <= numberofvertices; source++)
        {
            System.out.print(source + "  |" + "\t");
            for (int destination = 1; destination <= numberofvertices; destination++)
            {
                System.out.print(distancematrix[source][destination] + "\t");
            }
            System.out.println();
        }
        System.out.println("_____________________________________________________");
    }
    public void dijkstra_algorithm(int adjacency_matrix[][], int source)
    {
        int evaluationNode;
        for (int i = 1; i <= numberofvertices; i++)
            for (int j = 1; j <= numberofvertices; j++)
                adjacencyMatrix[i][j] = adjacency_matrix[i][j];
 
        for (int i = 1; i <= numberofvertices; i++)
        {
            distances[i] = Integer.MAX_VALUE;
        }
        unsettled.add(source);
        distances[source] = 0;		
        while (!unsettled.isEmpty())
        {
            evaluationNode = getNodeWithMinimumDistanceFromUnsettled();
            unsettled.remove(evaluationNode);
            settled.add(evaluationNode);
            evaluateNeighbours(evaluationNode);
        } 
    }
    private int getNodeWithMinimumDistanceFromUnsettled()
    {
        int min, node;
        Iterator<Integer> iterator = unsettled.iterator();
        node = iterator.next();
        min = distances[node];
        for (int i = 1; i <= distances.length; i++)
        {
            if (unsettled.contains(i))
            {
                if (distances[i] <= min)
                {
                    min = distances[i];
                    node = i;			
                }
            }
        }
        return node;
    }
    private void evaluateNeighbours(int evaluationNode)
    {
        int edgeDistance, newDistance;
        for (int destinationNode = 1; destinationNode <= numberofvertices; destinationNode++)
        {
            if (!settled.contains(destinationNode))
            {
                if (adjacencyMatrix[evaluationNode][destinationNode] != Integer.MAX_VALUE)
                {
                    edgeDistance = adjacencyMatrix[evaluationNode][destinationNode];
                    newDistance = distances[evaluationNode] + edgeDistance;
                    if (newDistance < distances[destinationNode])
                    {
                        distances[destinationNode] = newDistance;
                    }
                    unsettled.add(destinationNode);
                }
            }
        }
    }
    public static void main(String[] args) {
        int adjacency_matrix[][];
        int number_of_vertices, source, shortest_path_type, count = 0;
        Scanner scan = new Scanner(System.in);
        System.out.println("*-*-*-*-*- FINDING SHORTEST PATH *-*-*-*-*-");
        try
        {
            boolean exit = true;
            while(exit)
            {
                System.out.println("\nEnter the action you want to perform:");
                System.out.println("1. Single Source Shortest Path\n2. All Pair Shortest Path\n3. Exit");
                shortest_path_type = scan.nextInt();
                if (shortest_path_type == 3)
                {
                    break;
                }
                System.out.print("\nEnter the number of vertices:\t");
                number_of_vertices = scan.nextInt();
                adjacency_matrix = new int[number_of_vertices + 1][number_of_vertices + 1];
                System.out.println();
                System.out.println("Enter the Weighted Matrix for the graph");
                for (int i = 1; i <= number_of_vertices; i++)
                {
                    for (int j = 1; j <= number_of_vertices; j++)
                    {
                        adjacency_matrix[i][j] = scan.nextInt();
                        if (i == j)
                        {
                            adjacency_matrix[i][j] = 0;
                            continue;
                        }
                        if (adjacency_matrix[i][j] == 0)
                        {
                            adjacency_matrix[i][j] =  MAX_VALUE;
                        }
                    } 
                } 
                switch(shortest_path_type){
                    case 1:
                        System.out.print("Enter the Source Node:\t");
                        source = scan.nextInt();
                        System.out.println();
                        for (int i = 1; i <= number_of_vertices; i++)
                        {
                            for (int j = 1; j <= number_of_vertices; j++)
                            {
                                if (adjacency_matrix[i][j] < 0)
                                {
                                    count = count + 1;
                                }
                            }
                        }
                        if (count > 0)
                        {
                            // bellman ford
                            System.out.println("\n----- Using \"Bellman-Ford Algorithm\" -----\n");
                            Shortest_path bellmanford = new Shortest_path(number_of_vertices);
                            bellmanford.BellmanFordEvaluation(source, adjacency_matrix);
                        }
                        else
                        {
                            // dijkstra's 
                            System.out.println("\n----- Using \"Dijkstra's Algorithm\" -----\n");
                            Shortest_path dijkstrasAlgorithm = new Shortest_path(number_of_vertices);
                            dijkstrasAlgorithm.dijkstra_algorithm(adjacency_matrix, source);

                            System.out.println("The Shorted Path from Source Node to all the Nodes are: ");
                            for (int i = 1; i <= dijkstrasAlgorithm.distances.length - 1; i++)
                            {
                                System.out.println("Distance of Source Node " + source  + " to Distination Node " 
                                        + i + " is "+ dijkstrasAlgorithm.distances[i]);
                            }
                            System.out.println("_____________________________________________________");
                        }
                        break;
                            
                    case 2:
                        System.out.println("\n----- Using \"Floyd-Warshall Algorithm\" -----\n");
                        System.out.println("The Final Distance Matrix of the Graph is:\n");
                        Shortest_path floydwarshall = new Shortest_path(number_of_vertices);
                        floydwarshall.floydwarshall(adjacency_matrix);
                        break;
                        
                    default : 
                        System.out.println("Please enter a valid choice");
                }
            }
        } 
        catch (InputMismatchException inputMismatch)
        {
            System.out.println("Wrong Input Format");
        }
        scan.close();
    }  
}

