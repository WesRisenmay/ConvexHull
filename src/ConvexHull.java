import javafx.geometry.Point2D;

import java.awt.*;
import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;


/**
 * Created by Wes on 2/18/14.
 */


public class ConvexHull extends Application {

    private LinkedList<Point2D> q_hull = new LinkedList<Point2D>(); //required for quickhull algorithm

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
        //GeneratedTestData testDataCircle = new GeneratedTestData(20, DataType.circle);
        //GeneratedTestData testDataRandom = new GeneratedTestData(8, DataType.random);
        //bruteForceConvexHull(testDataRandom.getPoints());
        //System.out.println(TestCorrectness.test(testDataCircle.getPoints() , bruteForceConvexHull(testDataCircle.getPoints())));
        //System.out.println(TestCorrectness.test(testDataRandom.getPoints() , bruteForceConvexHull(testDataRandom.getPoints())));

        //System.out.println(TestCorrectness.test(testDataCircle.getPoints() , quickHull(testDataCircle.getPoints())));
        //System.out.println(TestCorrectness.test(testDataRandom.getPoints() , quickHull(testDataRandom.getPoints())));
        launch(args);
    }


    public static LinkedList<Point2D> bruteForceConvexHull(Point2D points[]) {
        LinkedList<Point2D> convexHull = new LinkedList<Point2D>();

        double curr_x_prod, prev_x_prod;
        boolean on_hull;
        for(Point2D a: points) {
            for(Point2D b: points) {
                if(a != b) {
                prev_x_prod = Math.PI;
                on_hull = true;
                for(Point2D c: points) {
                    if(c != a && c != b) {
                        curr_x_prod = ((a.getX() - b.getX())*(b.getY() - c.getY()) - (b.getX() - c.getX())*(a.getY() - b.getY()));
                        if(curr_x_prod > 0) {
                            curr_x_prod = 1;
                        }
                        else if(curr_x_prod < 0) {
                            curr_x_prod = -1;
                        }
                        else {
                            curr_x_prod = 0;
                        }
                        if (curr_x_prod == prev_x_prod || prev_x_prod == Math.PI || curr_x_prod == 0) {
                            prev_x_prod = curr_x_prod;
                        }
                        else {
                            on_hull = false;
                            break;
                        }
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
                }
            }
        }

        return convexHull;
    }
    @Override public void start(Stage stage) {
        stage.setTitle("Scatter Chart Sample");
        final NumberAxis xAxis = new NumberAxis(-22, 22, 1);
        final NumberAxis yAxis = new NumberAxis(-22, 22, 1);
        final ScatterChart<Number,Number> sc = new
                ScatterChart<Number,Number>(xAxis,yAxis);
        xAxis.setLabel("X-Axis");
        yAxis.setLabel("Y-Axis");
        sc.setTitle("Convex Hull Plot");

        GeneratedTestData testDataRandom = new GeneratedTestData(20, DataType.random);
        //LinkedList<Point2D> convexHull = bruteForceConvexHull(testDataRandom.getPoints());
        quickHull(testDataRandom.getPoints());

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("AllPoints");
        for(Point2D point: testDataRandom.getPoints()) {
            series1.getData().add(new XYChart.Data(point.getX(), point.getY()));
        }

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Hull Points");

        for(Point2D point: q_hull) {
            series2.getData().add(new XYChart.Data(point.getX(), point.getY()));
        }

        sc.getData().addAll(series1, series2);
        Scene scene  = new Scene(sc, 600, 600);
        stage.setScene(scene);
        stage.show();
    }


    private Point2D [] max_min(Point2D generated_points[]) {
        Point2D[] min_max = new Point2D[2];
        Point2D min_x_pt = generated_points[0];
        Point2D max_x_pt = generated_points[0];
        for(Point2D pt: generated_points) {
            if(pt.getX() > max_x_pt.getX()) {
                max_x_pt = pt;
            }
            else if(pt.getX() < min_x_pt.getX()) {
                min_x_pt = pt;
            }
        }
        min_max[0] = min_x_pt;
        min_max[1] = max_x_pt;
        return min_max;
    }

    private double get_determinant(Point2D a,Point2D b,Point2D c) {
        //x1y2 + x3y1 + x2y3 - x3y2 -x3y1 -x1y3
        double det = a.getX()*b.getY() + c.getX()*a.getY() + b.getX()*c.getY() - c.getX()*b.getY() - b.getX()*a.getY() - a.getX()*c.getY();
        return det;
    }

    private LinkedList<Point2D> get_subset(Point2D[] edge, LinkedList<Point2D> point_set, int side) {
        LinkedList<Point2D> subset = new LinkedList<Point2D>();
        double curr_side;
        for(Point2D pt: point_set) {
            curr_side = get_determinant(edge[0], edge[1], pt);
            if(pt != edge[0] && pt != edge[1]) {
                if(side > 0 && curr_side > 0) {
                    subset.add(pt);
                }
                if(side < 0 && curr_side < 0) {
                    subset.add(pt);
                }
                if(Math.abs(curr_side) == 0) {
                    subset.add(pt);
                }
            }
        }
        return subset;
    }

    private Point2D get_pivot_point(Point2D[] edge, LinkedList<Point2D> subset) {
        double max_distance = 0, curr_distance;
        Point2D pivot_point = new Point2D(Math.PI, Math.PI);
        for(Point2D pt: subset) {
            curr_distance = Math.abs(get_determinant(edge[0], edge[1], pt));
            if((curr_distance > max_distance || curr_distance == 0) && !q_hull.contains(pt)) {
                max_distance = curr_distance;
                pivot_point = pt;
            }
        }
        return pivot_point;
    }

    private void dome(Point2D[] edge, LinkedList<Point2D> point_set, boolean upper) {
        Point2D pivot;
        Point2D[] right_edge = new Point2D[2];
        Point2D[] left_edge = new Point2D[2];
        LinkedList<Point2D> right_subset;
        LinkedList<Point2D> left_subset;

        if(point_set.size() > 0) {
            pivot = get_pivot_point(edge, point_set);
            if(pivot.getX() != Math.PI && pivot.getY() != Math.PI) {
                q_hull.add(pivot);
                if( edge[0].getX() > edge[1].getX()) {
                    right_edge[0] = pivot;
                    right_edge[1] = edge[0];
                    left_edge[0] = pivot;
                    left_edge[1] = edge[1];
                }
                else {
                    right_edge[0] = pivot;
                    right_edge[1] = edge[1];
                    left_edge[0] = pivot;
                    left_edge[1] = edge[0];
                }
                if(upper) {
                    right_subset = get_subset(right_edge, point_set, 1);
                    left_subset = get_subset(left_edge, point_set, -1);
                }
                else {
                    right_subset = get_subset(right_edge, point_set, -1);
                    left_subset = get_subset(left_edge, point_set, 1);
                }
                if(right_subset.size() > 0) {
                    dome(right_edge, right_subset, upper);
                }
                if(left_subset.size() > 0) {
                    dome(left_edge, left_subset, upper);
                }
            }
        }
    }

    public void quickHull(Point2D points[]) {
        LinkedList<Point2D> generated_points = new LinkedList<Point2D>(Arrays.asList(points));
        Point2D[] base_edge = max_min(points);
        LinkedList<Point2D> upper_hull = get_subset(base_edge, generated_points, 1);
        LinkedList<Point2D> lower_hull = get_subset(base_edge, generated_points, -1);

        q_hull.add(base_edge[0]);
        q_hull.add(base_edge[1]);

        dome(base_edge, upper_hull, true);
        dome(base_edge, lower_hull, false);
    }

    public String testSpeed(LinkedList<TestNumbers> testNumbersList) {
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