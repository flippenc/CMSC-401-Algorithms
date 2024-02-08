import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MyMST
{
    //z is the weight of the MSTs
    private static int z = Integer.MAX_VALUE;

    public static void main(String[] args)
    {
        StringBuilder inputGraph = new StringBuilder();

        for (String input : args)
        {
            inputGraph.append(input).append(" ");
        }

        String[] edgeData = inputGraph.toString().trim().split(",");

        ArrayList<Edge> edges = new ArrayList<>();
        ArrayList<String> vertices = new ArrayList<>();

        for (int i = 0; i < edgeData.length; i++)
        {
            edgeData[i] = edgeData[i].trim();
            String[] currentEdgeData = edgeData[i].split(" ");
            Edge currentEdge = new Edge();

            currentEdge.setSource(currentEdgeData[0]);
            if (!vertices.contains(currentEdgeData[0]))
            {
                vertices.add(currentEdgeData[0]);
            }

            currentEdge.setDestination(currentEdgeData[1]);
            if (!vertices.contains(currentEdgeData[1]))
            {
                vertices.add(currentEdgeData[1]);
            }

            currentEdge.setWeight(Integer.parseInt(currentEdgeData[2]));

            edges.add(currentEdge);
        }

        //Lists of all the MSTs of this graph, the restricted, and fixed edge sets used for finding all MSTs
        ArrayList<ArrayList<Edge>> allMSTs = new ArrayList<>();
        ArrayList<Edge> R = new ArrayList<>();
        ArrayList<Edge> F = new ArrayList<>();

        //Find all of the MSTs
        findAllMST(F, R, edges, vertices, allMSTs);

        //Print all of the MSTs
        for (ArrayList<Edge> currentMST : allMSTs)
        {
            for (int i = 0; i < currentMST.size() - 1; i++)
            {
                System.out.print(currentMST.get(i)+", ");
            }
            System.out.println(currentMST.get(currentMST.size() - 1));
        }

    }

    //Our graph is defined by a list of edges
    public static class Edge implements Comparable<Edge>
    {
        private String source;
        private String destination;
        private int weight;
        //Restricted is used when finding all MSTs - the algorithm marks edges as fixed or restricted
        private boolean restricted = false;

        public boolean getRestricted()
        {
            return restricted;
        }

        public void setRestricted(boolean b)
        {
            restricted = b;
        }

        public int getWeight()
        {
            return weight;
        }

        public void setWeight(int w)
        {
            weight = w;
        }

        public String getSource()
        {
            return source;
        }

        public void setSource(String s)
        {
            source = s;
        }

        public String getDestination()
        {
            return destination;
        }

        public void setDestination(String d)
        {
            destination = d;
        }

        public String toString()
        {
            return getSource()+" "+getDestination();
        }

        @Override
        public int compareTo(Edge edge)
        {
            return this.weight - edge.weight;
        }
    }

    //The Disjoint set data structure allows us to implement Prim's algorithm
    //I used https://www.geeksforgeeks.org/union-find/ to help me understand how the Disjoint Set data structure works
    public static class DisjointSet
    {
        private String parent;
        private int rank;

        public DisjointSet(String p, int r)
        {
            parent = p;
            rank = r;
        }

        public String getParent()
        {
            return parent;
        }

        public void setParent(String p)
        {
            parent = p;
        }

        public int getRank()
        {
            return rank;
        }

        public void setRank(int r)
        {
            rank = r;
        }

    }

    //Find the subset of element search - used for Prim's algorithm
    public static String findParent(HashMap<String, DisjointSet> sets, String search)
    {
        if (!sets.get(search).getParent().equals(search))
        {
            sets.get(search).setParent(findParent(sets, sets.get(search).getParent()));
        }
        return sets.get(search).getParent();
    }

    //Make a union of the sets containing vertices v1 and v2
    public static void DisjointSetUnion(HashMap<String, DisjointSet> sets, String v1, String v2)
    {
        String v1Parent = findParent(sets, v1);
        String v2Parent = findParent(sets, v2);

        if (sets.get(v1Parent).getRank() < sets.get(v2Parent).getRank())
        {
            sets.get(v1Parent).setParent(v2Parent);
        }
        else if (sets.get(v1Parent).getRank() > sets.get(v2Parent).getRank())
        {
            sets.get(v2Parent).setParent(v1Parent);
        }
        else
        {
            sets.get(v2Parent).setParent(v1Parent);
            sets.get(v1Parent).setRank(sets.get(v1Parent).getRank()+1);
        }

    }

    //Find the sum of the weights of the edges of the graph Graph
    public static int getGraphWeight(ArrayList<Edge> Graph)
    {
        int totalWeight = 0;
        for (Edge e : Graph)
        {
            totalWeight += e.getWeight();
            if (e.getRestricted())
            {
                //We can't include invalid edges, so make the weight "infinite" if the edge is restricted
                return Integer.MAX_VALUE;
            }
        }
        return totalWeight;
    }

    //Modified version of Prim's algorithm to find an MST with fixed and restricted edges F and R
    public static ArrayList<Edge> findMST(ArrayList<Edge> F, ArrayList<Edge> R, ArrayList<Edge> edges, ArrayList<String> vertices)
    {
        ArrayList<Edge> MSTUnsorted = new ArrayList<>();
        //To ensure that our result is in the same order as the input, our resulting MST will be constructed by removing
        //edges that don't appear in MSTUnsorted from MST
        ArrayList<Edge> MST = new ArrayList<>(edges);

        //number of edges in our current MST and number of vertices in the graph
        int currentMSTEdges = 0;
        int V = vertices.size();

        ArrayList<Edge> candidates = new ArrayList<>(edges);
        DisjointSet edgeSubsets;

        HashMap<String, DisjointSet> subsets = new HashMap<>(V);
        for (String vertex : vertices)
        {
            edgeSubsets = new DisjointSet(vertex, 0);
            subsets.put(vertex, edgeSubsets);
        }

        //Make sure all restricted edges are marked as such
        for (Edge candidate : R)
        {
            int candidateIndex = candidates.indexOf(candidate);
            candidates.get(candidateIndex).setRestricted(true);
        }

        //Add all the edges from the fixed set
        for (Edge candidate : F)
        {
            String current1 = findParent(subsets, candidate.getSource());
            candidates.remove(candidate);
            String current2 = findParent(subsets, candidate.getDestination());
            candidates.remove(candidate);

            if (!current1.equals(current2))
            {
                MSTUnsorted.add(currentMSTEdges,candidate);
                currentMSTEdges++;
                DisjointSetUnion(subsets, current1, current2);
            }
        }

        //Sort the edges in increasing edge weight order
        Collections.sort(candidates);

        //filterCandidates is used to move restricted edges to the end of the list
        //we want to see if we can make an MST with the current F and R sets, so we will run through all of the allowed
        //edges first before we try adding restricted ones. if we have any restricted edges in our resulting tree,
        //then there is no MST that can be made with the current configuration of F and R
        ArrayList<Edge> filterCandidates = new ArrayList<>();
        for (Edge candidate : candidates)
        {
            if (candidate.getRestricted())
            {
                filterCandidates.add(candidate);
            }
        }
        candidates.removeIf(filterCandidates::contains);
        candidates.addAll(filterCandidates);

        //Add edges until we have enough to make a spanning tree (all trees have V-1 edges)
        int i = 0;
        while (currentMSTEdges < V - 1)
        {
            Edge currentEdge = candidates.get(i);
            i++;

            //current vertices incident to this edge
            String current1 = findParent(subsets, currentEdge.getSource());
            String current2 = findParent(subsets, currentEdge.getDestination());

            if (!current1.equals(current2))
            {
                MSTUnsorted.add(currentMSTEdges, currentEdge);
                currentMSTEdges++;
                DisjointSetUnion(subsets, current1, current2);
            }
        }

        //From MSTUnsorted, we want to construct an MST with elements in the same order as the original graph
        for (int j = 0; j < MST.size(); j++)
        {
            Edge current = MST.get(j);

            if (!MSTUnsorted.contains(current))
            {
                MST.remove(current);
                j--;
            }
        }
        return MST;
    }

    //This algorithm implements the algorithm from http://www.nda.ac.jp/~yamada/paper/enum-mst.pdf for finding all MSTs
    public static void findAllMST(ArrayList<Edge> F, ArrayList<Edge> R, ArrayList<Edge> edges, ArrayList<String> vertices, ArrayList<ArrayList<Edge>> allMSTs)
    {
        ArrayList<Edge> startingMST = findMST(F, R, edges, vertices);
        int currentZ = getGraphWeight(startingMST);
        //currentZ shouldn't be < 0 since we assumed our graph has positive weight vertices but it could be if there is
        //an integer overflow
        //terminate this subproblem if it is not valid
        if (currentZ > z || currentZ < 0)
        {
            return;
        }
        else //currentZ <= z
        {
            z = currentZ;
            allMSTs.add(startingMST);

            ArrayList<Edge> Fnew = new ArrayList<>(F);
            ArrayList<Edge> Rnew = new ArrayList<>(R);
            //Make all the subproblems that we need to solve by updating F and R
            for ( int i = 0; i < startingMST.size(); i++)
            {
                //Update F and R based on the MST we found and following the rules from the
                // http://www.nda.ac.jp/~yamada/paper/enum-mst.pdf algorithm
                if (i > 0)
                {
                    Rnew.get(Rnew.size() -1).setRestricted(false);
                    Fnew.add(Rnew.get(Rnew.size() -1 ));
                    Rnew.remove(Rnew.size()-1);
                }

                //find the next edge in F not in our MST
                while (Fnew.contains(startingMST.get(i)))
                {
                    i++;
                }
                Rnew.add(startingMST.get(i));
                findAllMST(Fnew, Rnew, edges, vertices, allMSTs);
                for (Edge candidate : edges)
                {
                    if (!Rnew.contains(candidate))
                    {
                        candidate.setRestricted(false);
                    }
                }
            }
        }
    }
}
