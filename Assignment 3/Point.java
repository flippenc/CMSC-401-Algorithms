public class Point
{
    public double x;
    public double y;

    public Point() {
        x = 0;
        y = 0;
    }

    public Point(double xCord, double yCord) {
        x = xCord;
        y = yCord;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(double xCord) {
        this.x = xCord;
    }

    public void setY(double yCord) {
        this.y = yCord;
    }

    @Override
    public String toString() {
        return x+" "+y+" ";
    }
}
