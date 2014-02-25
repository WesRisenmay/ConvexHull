import javafx.geometry.Point2D;

import java.util.LinkedList;

/**
 * Created by Wes on 2/25/14.
 */
public class TestCorrectness {

    //http://stackoverflow.com/questions/8721406/how-to-determine-if-a-point-is-inside-a-2d-convex-polygon
    private static boolean contains(Point2D test, Point2D points[]) {
        int i;
        int j;
        boolean result = false;
        for (i = 0, j = points.length - 1; i < points.length; j = i++) {
            if ((points[i].getY() > test.getY()) != (points[j].getY() > test.getY()) &&
                    (test.getX() < (points[j].getX() - points[i].getX()) * (test.getY() - points[i].getY()) / (points[j].getY()-points[i].getY()) + points[i].getY())) {
                result = !result;
            }
        }
        return result;
    }

    public static boolean test(Point2D points[], LinkedList<Point2D> convexHull) {
        boolean totalCorrectness = true;
        Point2D[] convexHullArray = convexHull.toArray(new Point2D[convexHull.size()]);
        for(Point2D current: points) {
            totalCorrectness = totalCorrectness && contains(current, convexHullArray);
        }

        return totalCorrectness;
    }
}
