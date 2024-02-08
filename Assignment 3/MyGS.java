import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Stack;

public class MyGS
{
    /**
     * Min is the bottom left point and points is an array of points
     */
    Point min;
    ArrayList<Point> points;

    /**
     * Main method to take in a list of points and print the convex hull that they make
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int i = 0;
        double x;
        double y;
        ArrayList<Point> p = new ArrayList<>();
        while (in.hasNextDouble()) {
            x = in.nextDouble();
            //System.out.println("x"+i+" cord "+x);
            i++;
            if (in.hasNextDouble()) {
                y = in.nextDouble();
                //System.out.println("y" + i + " cord " + y);
                Point a = new Point(x, y);
                p.add(a);
                i++;
            }
        }
        if (i%2 == 1) {
            System.out.println("error, odd number of coordinates entered");
            return;
        }
        MyGS g = new MyGS(p);
        //System.out.println("Convex Hull is "+g.convexHull(p));
        if (p.size() > 0) {
            System.out.println(g.convexHull(p));
        }
    }

    /**
     * Comparator to sort the list of points by angle
     */
    public class PointSortByPolarAngle implements Comparator<Point> {
        @Override
        public int compare(Point p1, Point p2) {
            int comp = compareAngle(min,p1, p2);
            if (comp == 0) {
                return Double.compare(distance(min, p1), distance(min, p2));
            } else if (comp == 2) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    /**
     * Constructor for a Grahams Scan object
     */
    public MyGS(ArrayList<Point> ps) {
        points = ps;
    }

    /**
     * Function to get the second to top entry of the stack S
     */
    public Point nextToTop(Stack<Point> S) {
        Point t = S.peek();
        S.pop();
        Point nextToT = S.peek();
        S.push(t);
        return nextToT;
    }

    /**
     * Function to print the convex hull created by the points in the ArrayList points
     * If there are fewer than 3 points, a hull cannot be created and an empty string is returned
     */
    public String convexHull(ArrayList<Point> points) {
        StringBuilder hull = new StringBuilder();
        int minIndex = minYPoint(points);
        this.min = points.get(minIndex);
        //System.out.println("Min is "+this.min.toString());
        //swap index 0 and the index of the min point
        points.set(minIndex,points.get(0));
        points.set(0,min);
        //sort the points by polar angle
        points.sort(new PointSortByPolarAngle());
        //System.out.println("Array is "+points.toString());

        //remove points that have the same angle as each other
        //index starts at 1 since point 0 is already in its correct spot
        for (int i = 1; i<points.size()-1; i++) {
            if (compareAngle(min, points.get(i),points.get(i+1))==0) {
                points.remove(i+1);
                i--;
            }
        }
        //System.out.println("Array is "+points.toString());

        if (points.size() < 3) {
            return "";
        } else {
            Stack<Point> stack = new Stack<>();
            stack.push(points.get(0));
            stack.push(points.get(1));
            stack.push(points.get(2));

            for (int i = 3; i < points.size(); i++) {
                while (compareAngle(nextToTop(stack), stack.peek(), points.get(i)) != 2) {
                    stack.pop();
                }
                stack.push(points.get(i));
            }

            while (!stack.empty()) {
                Point p = stack.peek();
                hull.insert(0, p.x + " " + p.y + " ");
                stack.pop();
            }

            return hull.toString();
        }
    }

    /**
     * Function to calculate the distance between 2 points
     */
    public double distance(Point a, Point b) {
        return (((a.x - b.x)*(a.x - b.x)) + ((a.y - b.y)*(a.y - b.y)));
    }

    /**
     * Function to compare the angle of 3 points,
     * returning 0 if they are in a line, 1 if they are clockwise, and 2 if they are counterclockwise
     */
    public int compareAngle(Point a, Point b, Point c) {
        double val = ((b.y - a.y)*(c.x-b.x)) - ((b.x-a.x)*(c.y-b.y));

        //the points are in a line
        if (val == 0) {
            return 0;
        }
        //the points are clockwise
        else if (val > 0) {
            return 1;
        }
        //the points are counterclockwise
        else {
            return 2;
        }
    }


    /**
     * Function to find the lowest point in findMin and return its index
     * If multiple points have the same lowest y coordinate, the bottom left one is chosen
     */
    public int minYPoint(ArrayList<Point> findMin) {
        int minIndex = -1;
        double minY = Double.MAX_VALUE;
        double minX = Double.MAX_VALUE;
        for (int i = 0; i<findMin.size(); i++) {
            if (findMin.get(i).y < minY || (findMin.get(i).y == minY && findMin.get(i).x < minX)) {
                minY = findMin.get(i).y;
                minX = findMin.get(i).x;
                minIndex = i;
            }
        }
        return minIndex;
    }

}
