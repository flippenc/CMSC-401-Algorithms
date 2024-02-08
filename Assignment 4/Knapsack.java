import java.util.ArrayList;

public class Knapsack
{
    //store which items are used to fill the backpack
    ArrayList<KnapsackItem> backpack;

    public Knapsack()
    {
        backpack = new ArrayList<>();
    }

    //private class used to make visualizing the resulting backpack easier
    private class KnapsackItem
    {
        int value;
        int weight;

        public KnapsackItem(int w, int v)
        {
            weight = w;
            value = v;
        }

        @Override
        public String toString()
        {
            return "{"+value+", "+ weight+"}";
        }
    }

    public void Knapsack(int capacity, int[] weights, int[] values)
    {
        int numItems = values.length;

        int i, w = 0;
        //B functions the same way as on the lecture slides
        //entry [i][j] represents the max value that can be obtained
        // when choosing from the first i objects with a capacity of j
        int[][] B = new int[numItems+1][capacity+1];

        for (i = 0; i<=numItems; i++)
        {
            for (w = 0; w<=capacity; w++)
            {
                //we can't store anything if we have weight 0, so these cells are 0
                if (i == 0 || w == 0)
                {
                    B[i][w] = 0;
                }
                else if (weights[i-1] <= w)
                {
                    B[i][w] = Math.max(values[i-1] + B[i-1][w - weights[i-1]], B[i-1][w]);
                }
                else
                {
                    B[i][w] = B[i-1][w];
                }
            }
        }
        //the bottom right of our table will be the max value
        int result = B[numItems][capacity];
        System.out.println(result);

        w = capacity;
        //now we find out which items make up the max value
        for (i = numItems; i > 0 && result > 0; i--)
        {
            //if this item appears in the solution,
            // subtract it from the result and the capacity and add it to the item list
            if (result != B[i-1][w])
            {
                KnapsackItem a = new KnapsackItem(weights[i-1],values[i-1]);
                backpack.add(a);
                result-=values[i-1];
                w-=weights[i-1];
            }
        }

    }

    public static void main(String[] args)
    {
        int[] values = {30, 35,32,38,40,41,45,46,50,55,60,80};
        int[] weights = {5, 6, 7, 8, 9, 10,11,12,13,15,16,20};
        int capacity = 100;
        Knapsack k = new Knapsack();
        k.Knapsack(capacity, weights, values);
        for (int i = 0; i<k.backpack.size(); i++)
        {
            System.out.println(k.backpack.get(i).toString());
        }
    }
}
