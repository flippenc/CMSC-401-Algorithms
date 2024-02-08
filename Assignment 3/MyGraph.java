import java.util.ArrayList;
import java.util.Arrays;

public class MyGraph
{
    /**
     * the order of a graph is the number of nodes it has
     * the graph itself is stored in an adjacency list
     **/
    int order;
    ArrayList<Integer>[] adjacency;

    public MyGraph()
    {}

    /**
     * Initialize this graph with an adjacency matrix
     */
    public void initialize(int[][] adjacencyMatrix) {
        order = adjacencyMatrix.length;
        adjacency = new ArrayList[order];
        for (int i = 0; i < order; i++) {
            adjacency[i] = new ArrayList<>();
        }

        for (int i = 0; i < order; i++) {
            for (int j = 0; j < order; j++) {
                if (adjacencyMatrix[i][j]==1) {
                    adjacency[i].add(j);
                }
            }
        }
    }

    /**
     * Returns the out degree of the node id
     */
    public int outDegree(int id) {
        if (id >= adjacency.length) {
            return -1;
        } else {
            return adjacency[id].size();
        }
    }

    /**
     * Method to create a string of the breadth first search for vertex id
     * This implementation is based on code from:
     * https://www.geeksforgeeks.org/breadth-first-search-or-bfs-for-a-graph/
     */
    public String BFS(int id) {
        boolean[] visited = new boolean[order];
        ArrayList<Integer> queue = new ArrayList<>();
        //search stores the traversal in breadth first order
        StringBuilder search = new StringBuilder();

        for (int i = 0; i<order; i++) {
            visited[i] = false;
        }
        visited[id] = true;
        queue.add(id);

        int currentElement;
        while (queue.size() > 0) {
            currentElement = queue.get(0);
            queue.remove(0);
            search.append(currentElement).append(" ");

            //look through all of the vertices adjacent to the current one and add them to the queue
            for (int n : adjacency[currentElement]) {
                if (!visited[n]) {
                    visited[n] = true;
                    queue.add(n);
                }
            }
        }

        return search.toString().trim();
    }

    /**
     * Method to create a string of the depth first search for vertex id
     * This implementation is based on code from:
     * https://www.geeksforgeeks.org/depth-first-search-or-dfs-for-a-graph/
     */
    public String DFS(int id) {
        boolean[] visited = new boolean[order];
        StringBuilder search = new StringBuilder();
        DFSHelper(id, visited,search);
        return search.toString().trim();
    }

    /**
     * DFS helper recursively finds the depth first search for vertex id
     * visited keeps track of which vertices have been visited and search stores the current string
     */
    private void DFSHelper(int id, boolean[] visited, StringBuilder search)
    {
        visited[id] = true;
        search.append(id).append(" ");
        for (int i : adjacency[id])
        {
            if (!visited[i])
            {
                DFSHelper(i, visited, search);
            }
        }
    }

    /**
     * Find how many hops it takes to get from point id1 to id2
     * return -1 if there is no path between them
     */
    public int hops(int id1, int id2)
    {
        if (id1 == id2) {
            return 0;
        }

        boolean[] visited = new boolean[order];
        int[] distance = new int[order];
        Arrays.fill(distance, Integer.MAX_VALUE);
        ArrayList<Integer> queue = new ArrayList<>();
        visited[id1] = true;
        distance[id1] = 0;
        queue.add(id1);

        while (!queue.isEmpty()) {
            int currentVertex = queue.get(0);
            queue.remove(0);
            for (int i : adjacency[currentVertex]) {
                if (!visited[i]) {
                    visited[i] = true;
                    distance[i] = distance[currentVertex] + 1;
                    queue.add(i);
                    if (i == id2) {
                        return distance[i];
                    }
                }
            }
        }
        return -1;
    }
}
