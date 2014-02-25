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
                int radius = count/2;
                double degrees = 0;
                for(int i = 0; i < count; i++) {
                    points[i] = new Point2D(radius*Math.cos(degrees), radius*Math.sin(degrees));
                    degrees += 360/count;
                }
                break;
            case random:
                int rangeMin = -1*(count/2), rangeMax = count/2;
                int randomX = 0;
                int randomY = 0;
                for(int i = 0; i < count; i++) {
                    randomX = rangeMin + (int)(Math.random() * ((rangeMax - rangeMin) + 1));
                    randomY = rangeMin + (int)(Math.random() * ((rangeMax - rangeMin) + 1));
                    points[i] = new Point2D(randomX, randomY);
                }
                break;
        }
    }

    public Point2D[] getPoints() {
        return points;
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

