import javafx.geometry.Point2D;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.LinkedList;

/**
 * Created by Wes on 2/25/14.
 */
public class TestCorrectness {
    public static boolean test(Point2D points[], LinkedList<Point2D> convexHull) {
        boolean totalCorrectness = true;
        Path2D.Double cHull = new Path2D.Double();
        Point2D[] convexHullArray = convexHull.toArray(new Point2D[convexHull.size()]);

        Point2D [] circularArray = getCircularArray(convexHullArray);

        cHull.moveTo(circularArray[0].getX(), circularArray[0].getY());
        for(int i = 1; i < circularArray.length; i++) {
            cHull.lineTo(circularArray[i].getX(), circularArray[i].getY());
        }
        cHull.closePath();


        for(Point2D current: points) {
            totalCorrectness = totalCorrectness && cHull.contains(current.getX(), current.getY());
            System.out.println(cHull.contains(current.getX(), current.getY()) + " " + current.getX() + " " + current.getY());
        }

        return totalCorrectness;
    }

    public static Point2D findLeftmostPoint(LinkedList<Point2D> cHull) {
        double smallestX = cHull.getFirst().getX();
        Point2D leftMostPoint = cHull.getFirst();
        for(Point2D current: cHull) {
            if(current.getX() < smallestX) {
                smallestX = current.getX();
                leftMostPoint = current;
            }
        }

        return leftMostPoint;
    }

    public static Point2D findRightmostPoint(LinkedList<Point2D> cHull) {
        double largestX = cHull.getFirst().getX();
        Point2D rightMostPoint = cHull.getFirst();
        for(Point2D current: cHull) {
            if(current.getX() > largestX) {
                largestX = current.getX();
                rightMostPoint = current;
            }
        }

        return rightMostPoint;
    }

    public static Point2D findBottommostPoint(LinkedList<Point2D> cHull) {
        double smallestY = cHull.getFirst().getY();
        Point2D bottomMostPoint = cHull.getFirst();
        for(Point2D current: cHull) {
            if(current.getY() < smallestY) {
                smallestY = current.getY();
                bottomMostPoint = current;
            }
        }

        return bottomMostPoint;
    }

    public static Point2D findTopmostPoint(LinkedList<Point2D> cHull) {
        double largestY = cHull.getFirst().getY();
        Point2D topMostPoint = cHull.getFirst();
        for(Point2D current: cHull) {
            if(current.getY() > largestY) {
                largestY = current.getY();
                topMostPoint = current;
            }
        }

        return topMostPoint;
    }

    public static Point2D [] getCircularArray(Point2D [] cHull) {
        LinkedList<Point2D> cHullList = new LinkedList<Point2D>();
        for(Point2D current: cHull) {
            cHullList.add(current);
        }

        Point2D top = findTopmostPoint(cHullList);
        Point2D left = findLeftmostPoint(cHullList);
        Point2D bottom = findBottommostPoint(cHullList);
        Point2D right = findRightmostPoint(cHullList);

        Point2D [] circularArray = new Point2D[cHull.length];
        LinkedList<Point2D> subList = new LinkedList<Point2D>();
        int currentPosition = 0;

        //top of the array
        circularArray[currentPosition] = left;
        currentPosition++;
        for(Point2D current: cHull) {
            if(current.getX() > left.getX() && current.getX() < top.getX() && current.getY() > left.getY() && current.getY() < top.getY())
                subList.add(current);
            else if(current.getX() > top.getX() && current.getX() < right.getX() && current.getY() < top.getY() && current.getY() > right.getY())
                subList.add(current);
        }
        if(left != top && right != top)
            subList.add(top);

        while(!subList.isEmpty()) {
            Point2D current = findLeftmostPoint(subList);
            subList.remove(current);
            circularArray[currentPosition] = current;
            currentPosition++;
        }

        circularArray[currentPosition] = right;
        currentPosition++;

        //bottom of the array
        for(Point2D current: cHull) {
            if(current.getX() > left.getX() && current.getX() < bottom.getX() && current.getY() < left.getY() && current.getY() > bottom.getY())
                subList.add(current);
            else if(current.getX() > bottom.getX() && current.getX() < right.getX() && current.getY() > bottom.getY() && current.getY() < right.getY())
                subList.add(current);
        }
        if(left != bottom && right != bottom)
            subList.add(bottom);

        while(!subList.isEmpty()) {
            Point2D current = findRightmostPoint(subList);
            subList.remove(current);
            circularArray[currentPosition] = current;
            currentPosition++;
        }

        return circularArray;
    }
}
