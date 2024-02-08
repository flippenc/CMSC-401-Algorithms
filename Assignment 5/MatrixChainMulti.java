import java.util.Arrays;

public class MatrixChainMulti
{
    public static void main(String[] args)
    {
        int[] P = {5, 10, 3, 12, 5, 50, 6, 2};
        System.out.println("A is 5x10, B is 10x3, C is 3x12, D is 12x5, E is 5x50, F is 50x6, G is 6x2");
        MatrixChainMulti a = new MatrixChainMulti();
        a.findChain(P);
    }

    char label = 'A';

    //This code follows pseudocode from the slides for the logic and
    // https://www.geeksforgeeks.org/printing-matrix-chain-multiplication-a-space-optimized-solution/?ref=rp
    // in order to print out the parenthesis
    public void findChain(int[] P)
    {
        int n = P.length;

        int[][] m = new int[n][n];

        for (int L = 2; L < n; L++)
        {
            for (int i = 1; i < n-L+1; i++)
            {
                int j = i+L-1;
                m[i][j] = Integer.MAX_VALUE;
                for (int k = i; k <= j-1; k++)
                {
                    int q = m[i][k] + m[k+1][j] + P[i-1]*P[k]*P[j];
                    if (q < m[i][j])
                    {
                        //m[i][j] stores the number of multiplications
                        m[i][j] = q;
                        //m[j][i] stores where to split the matrices
                        m[j][i] = k;
                    }
                }
            }
        }

        //m[0] is just a row of 0s, so we start at index 1
        for (int a = 1; a < m.length; a++)
        {
            System.out.println(Arrays.toString(m[a]));
        }

        System.out.println("The optimal number of multiplications is: "+m[1][n-1]);

        System.out.println("The optimal way to parenthesize the matrices is: ");
        printParenthesis(1,n-1, m);
    }

    public void printParenthesis(int i, int j, int[][] paren)
    {
        if (i == j)
        {
            System.out.print(label++);
            return;
        }

        System.out.print('(');
        printParenthesis(i, paren[j][i], paren);
        printParenthesis(paren[j][i]+1, j, paren);
        System.out.print(')');
    }

}
