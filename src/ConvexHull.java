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
        System.out.println(testSpeed(testNumbersList));

        //This is how you test for correctness
        GeneratedTestData testDataCircle = new GeneratedTestData(100000, DataType.circle);
        GeneratedTestData testDataRandom = new GeneratedTestData(100000, DataType.random);
        System.out.println(TestCorrectness.test(testDataCircle.getPoints() , bruteForceConvexHull(testDataCircle.getPoints())));
        System.out.println(TestCorrectness.test(testDataRandom.getPoints() , bruteForceConvexHull(testDataRandom.getPoints())));

        System.out.println(TestCorrectness.test(testDataCircle.getPoints() , quickHull(testDataCircle.getPoints())));
        System.out.println(TestCorrectness.test(testDataRandom.getPoints() , quickHull(testDataRandom.getPoints())));
    }

    public static LinkedList<Point2D> bruteForceConvexHull(Point2D points[]) {
        LinkedList<Point2D> convexHull = new LinkedList<Point2D>();

        //Implement algorithm, add convex hull points by convexHull.add(Point2D)

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