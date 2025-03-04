import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test {
    public static void main(String[] args) {
        // Test 1: Small fixed array
        runTest(new int[]{1, 2, 3, 7, 4, 7, 5}, 7, "Test 1");


        // Test 2: Array with no target value
         runTest(new int[]{1, 2, 3, 4, 5, 6}, 7, "Test 2");

        // Test 3: Array with all values the same
        runTest(new int[]{7, 7, 7, 7, 7}, 7, "Test 3");

        // Test 4: Large array with single target
        runTest(generateArray(1000, 7, 1), 7, "Test 4");

        // Test 5-100: Randomized tests
        for (int i = 5; i <= 100; i++) {
            int[] array = generateRandomArray(100, 10);
            int target = new Random().nextInt(10);
            runTest(array, target, "Test " + i);
        }
    }

    private static void runTest(int[] array, int target, String testName) {
        System.out.println(testName + ":");
        Array testArray = new ArrayImpl(array);
        ThreadsFactory factory = new SimpleThreadFactory();

        ParallelFinder finder = new ParallelFinder();
        finder.setArray(testArray);
        finder.setThreadsFactory(factory);

        List<Integer> result = finder.start(target);
        System.out.println("Indexes of value " + target + ": " + result);
        System.out.println();
    }

    private static int[] generateArray(int size, int target, int targetCount) {
        int[] array = new int[size];
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(10);
        }

        // Place targetCount targets randomly
        for (int i = 0; i < targetCount; i++) {
            int index = random.nextInt(size);
            array[index] = target;
        }

        return array;
    }

    private static int[] generateRandomArray(int size, int maxValue) {
        int[] array = new int[size];
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(maxValue);
        }

        return array;
    }
}