import java.util.Dictionary;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Wes on 2/18/14.
 */
public class ConvexHull {
    public static void main(String args[]) {
        //This is how you generate data
        GeneratedTestData generated = new GeneratedTestData(100, DataType.random);
        generated.printArray(true);
        generated = new GeneratedTestData(100, DataType.circle);
        generated.printArray(true);
        generated = new GeneratedTestData(99, DataType.circle);
        generated.printArray(true);

        //This is how you create a list test data to be run
        LinkedList<TestNumber> testNumbersList = new LinkedList<TestNumber>();
        testNumbersList.add(new TestNumber(10,50));
        testNumbersList.add(new TestNumber(100,25));
        testNumbersList.add(new TestNumber(1000,12));
        testNumbersList.add(new TestNumber(10000,6));
        testNumbersList.add(new TestNumber(100000,3));
        testNumbersList.add(new TestNumber(1000000,1));

        //This is how you run the speed tests
        testSpeed(testNumbersList);
    }



    public static String testSpeed(LinkedList<TestNumber> testNumbersList) {
        String total = "";
        for(TestNumber t: testNumbersList) {
            //Run brute force
            //Record data in total
            //Run quickhull
            //record data in total
        }

        return total;
    }


}