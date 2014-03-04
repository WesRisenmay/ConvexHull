import javafx.geometry.Point2D;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by Wes on 2/18/14.
 */
public class ConvexHull {
    public static void main(String args[]) {
        //This is how you create a list of test data to be run
        LinkedList<TestNumbers> testNumbersList = new LinkedList<TestNumbers>();
        testNumbersList.add(new TestNumbers(10,2000000));
        testNumbersList.add(new TestNumbers(100,2500000));
        testNumbersList.add(new TestNumbers(1000,1200000));
        testNumbersList.add(new TestNumbers(10000,600000));
        testNumbersList.add(new TestNumbers(100000,300000));
        testNumbersList.add(new TestNumbers(1000000,100000));

        //This is how you run the speed tests
       // System.out.println(testSpeed(testNumbersList));

        //This is how you test for correctness
        GeneratedTestData testDataCircle = new GeneratedTestData(20, DataType.circle);
        GeneratedTestData testDataRandom = new GeneratedTestData(20, DataType.random);
        System.out.println(TestCorrectness.test(testDataCircle.getPoints() , bruteForceConvexHull(testDataCircle.getPoints())));
        System.out.println(TestCorrectness.test(testDataRandom.getPoints() , bruteForceConvexHull(testDataRandom.getPoints())));

        //System.out.println(TestCorrectness.test(testDataCircle.getPoints() , quickHull(testDataCircle.getPoints())));
        //System.out.println(TestCorrectness.test(testDataRandom.getPoints() , quickHull(testDataRandom.getPoints())));
    }

    public static LinkedList<Point2D> bruteForceConvexHull(Point2D points[]) {
        LinkedList<Point2D> convexHull = new LinkedList<Point2D>();
        //Implement algorithm, add convex hull points by convexHull.add(Point2D)
        double curr_x_prod = 0, prev_x_prod = 0;
        boolean on_hull = true;
        for(Point2D a: points) {
            for(Point2D b: points) {
                for(Point2D c: points) {
                    if(c != a && c != b) {
                        curr_x_prod = ((b.getX() - a.getX())*(c.getY() - a.getY()) - (b.getY() - a.getY())*(c.getY() - a.getY()));
                        if(curr_x_prod != prev_x_prod) {
                            on_hull = false;
                            break;
                        }
                        prev_x_prod = curr_x_prod;
                    }
                }
                if(on_hull) {
                    if(!convexHull.contains(a)) {
                        convexHull.add(a);
                    }
                    if(!convexHull.contains(b)) {
                        convexHull.add(b);
                    }
                }
                else {
                    on_hull = true;
                }
            }
        }
        for(Point2D point: points) {
            System.out.print(point.toString());
        }
        System.out.println("\n\n" + convexHull.toString());

        return convexHull;
    }

    public static LinkedList<Point2D> quickHull(Point2D points[]) {
        LinkedList<Point2D> convexHull = new LinkedList<Point2D>();

        //Implement algorithm, add convex hull points by convexHull.add(Point2D)

        return convexHull;
    }



    public static String testSpeed(LinkedList<TestNumbers> testNumbersList) {
        String total = "";
        for(TestNumbers t: testNumbersList) {
            //Generate Data
            GeneratedTestData circle = new GeneratedTestData(t.n, DataType.circle);
            GeneratedTestData random = new GeneratedTestData(t.n, DataType.random);

            Timestamp lastTime = new Timestamp(new Date().getTime());
            for(int i = 0; i < t.timesToRun; i++) {
                bruteForceConvexHull(circle.getPoints());
            }
            Timestamp time = new Timestamp(new Date().getTime());
            total += "Brute force took " + (time.getTime()-lastTime.getTime()) + " milliseconds to run " + t.timesToRun + " times " +
                    "with n of size " + t.n + ", averaging " + ((time.getTime()-lastTime.getTime())/t.timesToRun) + " milliseconds with a circle as input.\n";

            lastTime = new Timestamp(new Date().getTime());
            for(int i = 0; i < t.timesToRun; i++) {
                bruteForceConvexHull(random.getPoints());
            }
            time = new Timestamp(new Date().getTime());
            total += "Brute force took " + (time.getTime()-lastTime.getTime()) + " milliseconds to run " + t.timesToRun + " times " +
                    "with n of size " + t.n + ", averaging " + ((time.getTime()-lastTime.getTime())/t.timesToRun) + " milliseconds with random input.\n";


            lastTime = new Timestamp(new Date().getTime());
            for(int i = 0; i < t.timesToRun; i++) {
                quickHull(circle.getPoints());
            }
            time = new Timestamp(new Date().getTime());
            total += "Quick hull took " + (time.getTime()-lastTime.getTime()) + " milliseconds to run " + t.timesToRun + " times " +
                    "with n of size " + t.n + ", averaging " + ((time.getTime()-lastTime.getTime())/t.timesToRun) + " milliseconds with a circle as input.\n";

            lastTime = new Timestamp(new Date().getTime());
            for(int i = 0; i < t.timesToRun; i++) {
                quickHull(random.getPoints());
            }
            time = new Timestamp(new Date().getTime());
            total += "Quick hull took " + (time.getTime()-lastTime.getTime()) + " milliseconds to run " + t.timesToRun + " times " +
                    "with n of size " + t.n + ", averaging " + ((time.getTime()-lastTime.getTime())/t.timesToRun) + " milliseconds with random input.\n\n";
        }

        return total;
    }


}