import java.util.Arrays;

public class LCS
{
    //This code follows pseudocode from the slides for the logic and
    //https://www.geeksforgeeks.org/printing-longest-common-subsequence/?ref=rp
    //for printing out the LCS
    public static void findLCS(int[] X, int[] Y)
    {
        int m = X.length;
        int n = Y.length;

        int[][] B = new int[m+1][n+1];

        int i; int j;
        for ( i = 0; i <= m; i++)
        {
            for ( j = 0; j<=n; j++)
            {
                if (i == 0 || j == 0)
                {
                    B[i][j] = 0;
                }
                else if (X[i-1] == Y[j-1])
                {
                    B[i][j] = B[i-1][j-1] + 1;
                }
                else
                {
                    B[i][j] = Math.max(B[i-1][j], B[i][j-1]);
                }
            }
        }

        int currentIndex = B[m][n];
        System.out.println("The table is: ");
        System.out.println("   Y "+Arrays.toString(Y));
        for (int a = 0; a<B.length ; a++)
        {
            if (a > 0) {
                System.out.println(X[a-1]+" "+Arrays.toString(B[a]));
            }
            else
            {
                System.out.println("X "+Arrays.toString(B[a]));
            }
        }
        System.out.println("Length of subsequence is "+currentIndex);

        int[] LCS = new int[currentIndex];

        i = m; j = n;

        while ( i > 0 && j > 0)
        {
            if (X[i-1] == Y[j-1])
            {
                LCS[currentIndex-1] = X[i-1];

                i--;
                j--;
                currentIndex--;
            }
            else if (B[i-1][j] > B[i][j-1])
            {
                i--;
            }
            else
            {
                j--;
            }
        }

        System.out.print("LCS of "+ Arrays.toString(X)+" and "+Arrays.toString(Y)+" is "+Arrays.toString(LCS));
    }

    public static void main(String[] agrs)
    {
        int[] s1 = {0,1,0,1,1,0,1,1,0,1,1,1,0,1};
        int[] s2 = {1,0,0,1,0,1,0,1,0,0,1,1,0,1,1};
        findLCS(s1,s2);
    }
}
