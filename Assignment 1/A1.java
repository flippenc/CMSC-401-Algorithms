public class A1
{
    public static void main(String[] args)
    {
        int[] find = new int[args.length];
        for (int i = 0; i<args.length; i++)
        {
            if (isInteger(args[i]))
            {
                find[i]=Integer.parseInt(args[i]);
            }
            else
            {
                System.out.println("error");
            }
        }
        findMajorityElement(find);
    }

    public static boolean isInteger(String check)
    {
        try
        {
            Integer.parseInt(check);
            return true;
        }
        catch (Exception NumberFormatException)
        {
            return false;
        }
    }

    public static void findMajorityElement(int[] find)
    {
        if (find.length == 1)
        {
            System.out.println(find[0]);
            return;
        }
        if (find.length == 0)
        {
            System.out.println("none");
            return;
        }
        int candidate = find[0];
        int candidateCount = 1;
        for (int i = 1; i<find.length; i++)
        {
            if (candidate==find[i])
            {
                candidateCount++;
            }
            else
            {
                candidateCount--;
                if (candidateCount==0)
                {
                    candidate = find[i];
                }
            }
        }
        System.out.println(candidate);
    }
}
