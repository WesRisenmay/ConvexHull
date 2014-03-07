import javafx.geometry.Point2D;

import java.awt.*;
import java.sql.Timestamp;
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

/*
def brute_force(points):
    convex_hull = []
    for pt_a in points:
        for pt_b in points:
            on_hull = True
            prev_cross_prod = None
            if pt_a != pt_b:
                for pt_c in points:
                    if pt_c != pt_a and pt_c != pt_b:
                        v1 = [pt_a[0] - pt_b[0], pt_a[1] - pt_b[1]]
                        v2 = [pt_b[0] - pt_c[0], pt_b[1] - pt_c[1]]
                        cross_prod = v1[0]*v2[1] - v2[0]*v1[1]
                        if cross_prod > 0:
                            cross_prod = 1
                        else:
                            cross_prod = -1
                        if cross_prod == prev_cross_prod or type(prev_cross_prod) is type(None) or abs(cross_prod) == 0:
                            prev_cross_prod = cross_prod
                        else:
                            on_hull = False
                            break
                if on_hull and (pt_a not in convex_hull):
                    convex_hull.append(pt_a)
                if on_hull and (pt_b not in convex_hull):
                    convex_hull.append(pt_b)
    return convex_hull
 */
    public static LinkedList<Point2D> bruteForceConvexHull(Point2D points[]) {
        LinkedList<Point2D> convexHull = new LinkedList<Point2D>();

        double curr_x_prod, prev_x_prod;
        boolean on_hull;
        for(Point2D a: points) {
            for(Point2D b: points) {
                prev_x_prod = Math.PI;
                on_hull = true;
                for(Point2D c: points) {
                    if(c != a && c != b) {
                        curr_x_prod = ((a.getX() - b.getX())*(b.getY() - c.getY()) - (b.getX() - c.getX())*(a.getY() - b.getY()));
                        if(curr_x_prod > 0) {
                            curr_x_prod = 1;
                        }
                        else if(curr_x_prod == 0) {
                            curr_x_prod = 0;
                        }
                        else {
                            curr_x_prod = -1;
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

        for(Point2D point: points) {
            System.out.print(point.toString());
        }
        System.out.println("\n\n" + convexHull.toString());

        return convexHull;
    }
    @Override public void start(Stage stage) {
        stage.setTitle("Scatter Chart Sample");
        final NumberAxis xAxis = new NumberAxis(-10, 10, 1);
        final NumberAxis yAxis = new NumberAxis(-10, 10, 1);
        final ScatterChart<Number,Number> sc = new
                ScatterChart<Number,Number>(xAxis,yAxis);
        xAxis.setLabel("X-Axis");
        yAxis.setLabel("Y-Axis");
        sc.setTitle("Convex Hull Plot");

        GeneratedTestData testDataRandom = new GeneratedTestData(12, DataType.random);
        LinkedList<Point2D> convexHull = bruteForceConvexHull(testDataRandom.getPoints());

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("AllPoints");
        for(Point2D point: testDataRandom.getPoints()) {
         series1.getData().add(new XYChart.Data(point.getX(), point.getY()));
        }
        /*
        series1.getData().add(new XYChart.Data(4.2, 193.2));
        series1.getData().add(new XYChart.Data(2.8, 33.6));
        series1.getData().add(new XYChart.Data(6.2, 24.8));
        series1.getData().add(new XYChart.Data(1, 14));
        series1.getData().add(new XYChart.Data(1.2, 26.4));
        series1.getData().add(new XYChart.Data(4.4, 114.4));
        series1.getData().add(new XYChart.Data(8.5, 323));
        series1.getData().add(new XYChart.Data(6.9, 289.8));
        series1.getData().add(new XYChart.Data(9.9, 287.1));
        series1.getData().add(new XYChart.Data(0.9, -9));
        series1.getData().add(new XYChart.Data(3.2, 150.8));
        series1.getData().add(new XYChart.Data(4.8, 20.8));
        series1.getData().add(new XYChart.Data(7.3, -42.3));
        series1.getData().add(new XYChart.Data(1.8, 81.4));
        series1.getData().add(new XYChart.Data(7.3, 110.3));
        series1.getData().add(new XYChart.Data(2.7, 41.2));
        */

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Hull Points");

        for(Point2D point: convexHull) {
            series2.getData().add(new XYChart.Data(point.getX(), point.getY()));
        }
        /*
        series2.getData().add(new XYChart.Data(5.2, 229.2));
        series2.getData().add(new XYChart.Data(2.4, 37.6));
        series2.getData().add(new XYChart.Data(3.2, 49.8));
        series2.getData().add(new XYChart.Data(1.8, 134));
        series2.getData().add(new XYChart.Data(3.2, 236.2));
        series2.getData().add(new XYChart.Data(7.4, 114.1));
        series2.getData().add(new XYChart.Data(3.5, 323));
        series2.getData().add(new XYChart.Data(9.3, 29.9));
        series2.getData().add(new XYChart.Data(8.1, 287.4));
        */

        sc.getData().addAll(series1, series2);
        Scene scene  = new Scene(sc, 500, 400);
        stage.setScene(scene);
        stage.show();
    }
    /*
    Canvas canvas = new Canvas(300, 300);
final GraphicsContext gc = canvas.getGraphicsContext2D();
gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

gc.setFill(Color.BLACK);
gc.setFont(Font.getDefault());
gc.fillText("hello   world!", 15, 50);

gc.setLineWidth(5);
gc.setStroke(Color.PURPLE);

gc.strokeOval(10, 60, 30, 30);
gc.strokeOval(60, 60, 30, 30);
gc.strokeRect(30, 100, 40, 40);

root.getChildren().add(canvas);
primaryStage.setScene(scene);
primaryStage.show();
     */

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