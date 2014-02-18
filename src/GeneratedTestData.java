import javafx.geometry.Point2D;

import java.util.Random;

/**
 * Created by Wes on 2/18/14.
 * This class will be provided with an array size N, and it will return several different types of arrays of points to test out several
 * different cases.
 */
public class GeneratedTestData {
    private Point2D points[];

    /*
    INPUT:
        count- size of array
        type = type of data wanted in the array
     */
    public GeneratedTestData(int count, DataType type) {
        points = new Point2D[count];
        switch(type) {
            case circle:
                int increment = count/(count/2);
                Point2D lastPoint = new Point2D(-1*count/2, 0);
                points[0] = lastPoint;
                for(int i = 1; i < count; i++) {
                    if((lastPoint.getY() < 0 || lastPoint.getY() == 0) && (i < count/2)) {
                        lastPoint = new Point2D(lastPoint.getX() + increment, -1*(lastPoint.getY()) + increment);
                        points[i] = lastPoint;
                    }
                    else if((lastPoint.getY() < 0 || lastPoint.getY() == 0) && (i > count/2)) {
                        lastPoint = new Point2D(lastPoint.getX() + increment, -1*(lastPoint.getY()) - increment);
                        points[i] = lastPoint; //Not sure if this halving will work, need to test with odd counts
                    }
                    else {
                        lastPoint = new Point2D(lastPoint.getX(), -1*(lastPoint.getY()));
                        points[i] = lastPoint;
                    }
                }
                break;
            case random:
                Random r = new Random();
                int rangeMin = -1*(count/2), rangeMax = count/2;
                int randomX = 0;
                int randomY = 0;
                for(int i = 0; i < count; i++) {
                    randomX = rangeMin + (rangeMax - rangeMin) * r.nextInt();
                    randomY = rangeMin + (rangeMax - rangeMin) * r.nextInt();
                    points[i] = new Point2D(randomX, randomY);
                }
                break;
        }
    }

    public String printArray(boolean output) {
        String total = "";
        for(int i = 0; i < points.length; i++) {
            total += points[i].toString() + "\n";
        }

        if(output) {
        System.out.println(total);
        }

        return total;
    }
}

