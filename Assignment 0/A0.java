//Christopher Flippen
//CMSC 401 - Trial Assignment - Spring 2020
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class A0
{
    //Main method that uses a scanner to read in input line-by-line
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine())
        {
            parseString(sc.nextLine());
        }
        sc.close();
    }

    //Method to call the respective methods depending on which command character is used
    public static void parseString(String input)
    {
        //hasValidCharacters checks if the line contains numbers
        if (!hasValidCharacters(input, 1))
        {
            System.out.println("error");
            return;
        }
        //Getting the command character and running the methods for whichever character is used
        String commandChar = input.trim().charAt(0)+"";
        if (commandChar.equalsIgnoreCase("A"))
        {
            optionA(input.substring(1));
        }
        else if (commandChar.equalsIgnoreCase("B"))
        {
            optionB(input.substring(1));
        }
        //If there isn't a valid command character, print error
        else
        {
            System.out.println("error");
        }
    }

    //Method to check if the line contains any non-numeric characters
    public static boolean hasValidCharacters(String input, int start)
    {
        if (input.trim().equals(""))
        {
            return false;
        }
        //Scanner used to check each part of the input line
        Scanner sc = new Scanner(input.substring(start));
        while (sc.hasNext())
        {
            if (!isInteger(sc.next().trim()))
            {
                sc.close();
                return false;
            }
        }
        sc.close();
        return true;
    }

    //Checks if a String is a number, used for hasValidCharacters
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

    //Takes the input line and converts it to an ArrayList of the inputs
    public static ArrayList<Integer> toArrayList(String input)
    {
        ArrayList<Integer> numbers = new ArrayList<>();
        Scanner sc = new Scanner(input);
        while (sc.hasNext())
        {
            numbers.add(Integer.parseInt(sc.next()));
        }
        return numbers;
    }

    public static void optionA(String input)
    {
        ArrayList<Integer> numbers = toArrayList(input);
        //there needs to be at least one number to take the product of and two numbers for the indices of the product
        if (numbers.size()<3)
        {
            System.out.println("error");
            return;
        }
        int last = numbers.get(numbers.size()-1);
        int secondToLast = numbers.get(numbers.size()-2);
        for (int i = 0; i<numbers.size(); i++)
        {
            if (numbers.get(i) < 0 || numbers.get(i) > 1000)
            {
                System.out.println("error");
                return;
            }
        }
        //Checking for valid indexes
        if (secondToLast > last || secondToLast > numbers.size() || last > numbers.size()
                || last < 1 || secondToLast < 1)
        {
            System.out.println("error");
            return;
        }
        int product = 1;
        //secondToLast-1 and i<last since the input index starts at 1
        for (int i = secondToLast-1; i<last; i++)
        {
            product*=numbers.get(i);
        }
        System.out.println(product);
    }

    public static void optionB(String input)
    {
        ArrayList<Integer> numbers = toArrayList(input);
        //option B should only have one number
        if (numbers.size()!=1)
        {
            System.out.println("error");
            return;
        }
        else
        {
            int fact = numbers.get(0);
            //checking that fact is within the correct range
            if (fact > 100 || fact < 1)
            {
                System.out.println("error");
                return;
            }
            BigInteger factBig = new BigInteger(fact+"");
            System.out.println(factorial(factBig));
        }
    }

    //BigInteger is used since 100! is too large to fit in an integer or long
    public static BigInteger factorial(BigInteger i)
    {
        if (i.compareTo(BigInteger.ZERO)==0)
        {
            return BigInteger.ONE;
        }
        return i.multiply(factorial(i.subtract(BigInteger.ONE)));
    }

}
