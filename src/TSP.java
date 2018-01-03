
import java.util.ArrayList;

/***************************************************************
* 
*                 O---O
*                 | X |
*                 O---O
* 
* Assuming each vertex of a Hamiltonian graph is connected to
* every node, the number of permutations that can be traversed
* from a single point is equal to (N - 1)!
* 
*       e.g     N = 4
* 
*               0 > 1 > 2 > 3
*               0 > 1 > 3 > 2
*               0 > 2 > 1 > 3
*               0 > 2 > 3 > 1
*               0 > 3 > 1 > 2
*               0 > 3 > 2 > 1
* 
* General Objectives:
* 
*    1. Generate a permutation of N items where N is equal to
*       the number of vertex in the graph.
* 
*    2. Filter the result set, discarding permutations that
*       do not start with the value 0. This is done to minimize
*       the problem set, because the permutation 0123 is
*       equivalent to the permutation 1230. If a traveler
*       traveled A > B > C > D > A, this is the same as traveling
*       the path B > C > D > A > B.
* 
*    3. Based on the generated set of permutations, construct a
*       3-D array containing the x-y coordinates that will
*       represent each vertex.
* 
*       e.g
*                   A   B   C   D
*               A   x   1   2   3
*               B   1   x   4   5
*               C   2   4   x   6
*               D   3   5   6   x
* 
*               The rows represent the source, while the columns
*               represent the destination.
* 
*               The permutation 0123 represents 0 > 1 > 2 > 3 > 0,
*               or A > B > C > D > A.
* 
*               Going from A to B, we look at the row representing
*               A, and the column representing B. The indices for
*               these two are 0 and 1. Effectively, using these as
*               an x-y coordinate gives us the value at intersection
*               A-B, which is 1. This is where the entire framework
*               of this solution lies in. The next path, which is
*               B to C, represent indices 1 and 2. We obtain the
*               coordinate [1, 2] which gives the values 4. This
*               is repeated until the last vertex in the permutation.
*               Doing this, we now obtain the necessary data to
*               optimize our solution.
* 
*               A > B > C > D > A   [A, B][B, C][C, D][D, A]    [0, 1][1, 2][2, 3][3, 0]
*               A > B > D > C > A   [A, B][B, D][D, C][C, A]    [0, 1][1, 3][3, 2][2, 0]
*               A > C > B > D > A   [A, C][C, B][B, D][D, A]    [0, 2][2, 1][1, 3][3, 0]
*               A > C > D > B > A   [A, C][C, D][D, B][B, A]    [0, 2][2, 3][3, 1][1, 0]
*               A > D > B > C > A   [A, D][D, B][B, C][C, A]    [0, 3][3, 1][1, 2][2, 0]
*               A > D > C > B > A   [A, D][D, C][C, B][B, A]    [0, 3][3, 2][2, 1][1, 0]
* 
*    4. Fetch the data for every coordinate in each permutation,
*       and add them up. You will now have (N - 1)! results.
*       The lowest result is the optimized path.
* 
*       e.g
*               A > B > C > D > A   [0, 1][1, 2][2, 3][3, 0]    14
*               A > B > D > C > A   [0, 1][1, 3][3, 2][2, 0]    28
*               A > C > B > D > A   [0, 2][2, 1][1, 3][3, 0]    42
*               A > C > D > B > A   [0, 2][2, 3][3, 1][1, 0]    56
*               A > D > B > C > A   [0, 3][3, 1][1, 2][2, 0]    70
*               A > D > C > B > A   [0, 3][3, 2][2, 1][1, 0]    84
* 
*               Optimized = 14
* 
***************************************************************/

public class TSP
{
    private static ArrayList<String> combinations = new ArrayList();

    private static void permutation(String prefix, String str)
    {
        int n = str.length();
        
        if (n == 0 && prefix.charAt(0) == '0')
        {
            combinations.add(prefix);
        }
        else
        {
            for (int i = 0; i < n; i++)
            {
                permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n));
            }
        }
    }
    
    public static void solve(int[][] matrix, int[][][] coordinates)
    {
        int n = matrix[0].length;
        int m = factorial(n - 1);
        
        String str = "";
        
        for (int i = 0; i < n; i++)
        {
            str += i;
        }
        
        combinations.clear();
        permutation("", str);
        
        for (int i = 0; i < m; i++)
        {
            for (int j = 0; j < n - 1; j++)
            {
                coordinates[i][j][0] = combinations.get(i).charAt(j) - '0';
                coordinates[i][j][1] = combinations.get(i).charAt(j + 1) - '0';
            }
            
            coordinates[i][n - 1][0] = combinations.get(i).charAt(n - 1) - '0';
            coordinates[i][n - 1][1] = combinations.get(i).charAt(0) - '0';
        }
    }
    
    public static int optimize(int[][] matrix)
    {
        int n = matrix[0].length;
        int m = factorial(n - 1);
        
        int[][][] coordinates = new int[m][n][2];
        
        solve(matrix, coordinates);
        //consoleOutput(matrix);
        
        int optimized = 0;
        int temp = 0;
        
        for (int i = 0; i < m; i++)
        {
            for (int j = 0; j < n; j++)
            {
                int x = coordinates[i][j][0];
                int y = coordinates[i][j][1];
                
                temp += matrix[x][y];
            }
            
            if (optimized == 0 || optimized > temp)
            {
                optimized = temp;
            }
        }
        
        return optimized;
    }
    
    private static int factorial(int n)
    {
        return ((n == 0 || n == 1) ? 1 : n * factorial(n - 1));
    }
    
    private static void consoleOutput(int[][] matrix)
    {
        int n = matrix[0].length;
        
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                System.out.print(matrix[i][j] + "\t");
            }
            
            System.out.println();
        }
    }
}
