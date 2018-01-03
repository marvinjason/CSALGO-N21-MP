
import java.util.ArrayList;
import java.util.Scanner;

public class KnapSackProblem
{
    private static Scanner input = new Scanner(System.in);
    
    public static void main(String[] args)
    {
        int num, thresh;
        
        System.out.print("Number of Items: ");
        
        num = input.nextInt();
        ArrayList<Integer> weight = new ArrayList();
        ArrayList<Integer> cost = new ArrayList();
        
        System.out.print("Threshold (Maximum Weight): ");
        
        thresh = input.nextInt();
        
        System.out.println(num + " Weight Values: ");
        
        for (int i = 0; i < num; i++)
        {
            weight.add(input.nextInt());
        }
        
        System.out.println(num + " Cost Values: ");
        
        for (int i = 0; i < num; i++)
        {
            cost.add(input.nextInt());
        }
        
        KnapSackProblem object = new KnapSackProblem();
        
        //object.generate(thresh, num, weight, cost);
    }
    
    public void generate(int threshold, ArrayList<Integer> id, ArrayList<Integer> weight, ArrayList<Integer> cost)
    {
        ArrayList<Integer> tempList = new ArrayList();
        ArrayList<Integer> optimizedList = new ArrayList();
        int n = id.size();
        int w = 0;
        int c = 0;
        int optimizedW = 0;
        int optimizedC = 0;
        
        for (int i = 1; i < (1 << n); i++)
        {
            String str = Integer.toBinaryString(i);
            
            for (int j = str.length() - 1, k = n - 1; j >= 0; j--, k--)
            {
                if (str.charAt(j) == '1')
                {
                    w += weight.get(k);
                    c += cost.get(k);
                    tempList.add(id.get(k));
                }
            }
            
            if (w <= threshold)
            {
                if (optimizedW == 0 && optimizedC == 0)
                {
                    optimizedW = w;
                    optimizedC = c;
                    optimizedList.clear();
                    optimizedList.addAll(tempList);
                }
                else if (optimizedW <= w)
                {
                    if (optimizedC <= c)
                    {
                        optimizedW = w;
                        optimizedC = c;
                        optimizedList.clear();
                        optimizedList.addAll(tempList);
                    }
                }
            }
            
            w = 0;
            c = 0;
            tempList.clear();
        }
    }
}
