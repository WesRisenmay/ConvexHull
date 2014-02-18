
/**
 * Created by Wes on 2/18/14.
 */
public class ConvexHull {
    public static void main(String args[]) {
        GeneratedTestData generated = new GeneratedTestData(100, DataType.random);
        generated.printArray(true);
        generated = new GeneratedTestData(100, DataType.circle);
        generated.printArray(true);
        generated = new GeneratedTestData(99, DataType.circle);
        generated.printArray(true);
    }
}
