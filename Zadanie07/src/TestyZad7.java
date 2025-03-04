import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TestyZad7 {

    private static class TestCase {
        int testNum;
        int colToFind;
        int rowToFind;
        int minCol;
        int maxCol;
        int minRow;
        int maxRow;

        TestCase(int testNum, int colToFind, int rowToFind, int minCol, int maxCol, int minRow, int maxRow) {
            this.testNum = testNum;
            this.colToFind = colToFind;
            this.rowToFind = rowToFind;
            this.minCol = minCol;
            this.maxCol = maxCol;
            this.minRow = minRow;
            this.maxRow = maxRow;
        }

        private int runTest(ExecutorService executor, long timeout, TimeUnit unit) {
            Callable<Integer> task = () -> {
                Locator locator = getLocator(minCol, maxCol, minRow, maxRow, new Position2D(colToFind, rowToFind));
                FastFinder ff = new FastFinder();
                Position2D result = ff.tryToFind(locator);
                if (result.col() == colToFind && result.row() == rowToFind) {
                    return 1;
                } else {
                    announceError(testNum, "zwrócono złą pozycję");
                    return 0;
                }
            };

            Future<Integer> future = executor.submit(task);
            try {
                return future.get(timeout, unit);
            } catch (TimeoutException e) {
                announceError(testNum, "timeout");
                future.cancel(true); // Przerwanie zadania
                return 0;
            } catch (InterruptedException | ExecutionException e) {
                announceError(testNum, "exception");
                return 0;
            }
        }

    }

    private static void announceError(int testNum, String msg) {
        System.out.println("! failed on: test" + testNum + " (" + msg + ")");
    }

    public static void main(String[] args) {
        int passed = 0;
        int all = 0;

        // Definicja wszystkich przypadków testowych
        List<TestCase> testCases = new ArrayList<>();
        testCases.add(new TestCase(1, 5, 5, 0, 10, 0, 10));
        testCases.add(new TestCase(2, 0, 0, 0, 10, 0, 10));
        testCases.add(new TestCase(3, 10, 10, 0, 10, 0, 10));
        testCases.add(new TestCase(4, 0, 10, 0, 10, 0, 10));
        testCases.add(new TestCase(5, 10, 0, 0, 10, 0, 10));
        testCases.add(new TestCase(6, 0, 0, 0, 1, 0, 1));
        testCases.add(new TestCase(7, 1, 1, 0, 1, 0, 1));
        testCases.add(new TestCase(8, 0, 1, 0, 1, 0, 1));
        testCases.add(new TestCase(9, 1, 0, 0, 1, 0, 1));
        testCases.add(new TestCase(10, -50, -100, -5000, 5000, -2000, 2000));
        testCases.add(new TestCase(11, -50, -100, -5000, 5000, -2000, 2000));
        testCases.add(new TestCase(12, 10, 20, 10, 10, 20, 20));

        // Tworzenie ExecutorService z pulą wątków i daemon threads
        ExecutorService executor = Executors.newFixedThreadPool(testCases.size(), r -> {
            Thread t = new Thread(r);
            t.setDaemon(true); // Ustawienie wątku jako daemon
            return t;
        });

        // Definiowanie timeoutu dla każdego testu
        long timeout = 100;
        TimeUnit unit = TimeUnit.MILLISECONDS;

        // Iteracja przez wszystkie przypadki testowe
        try {
            for (TestCase tc : testCases) {
                all++;
                passed += tc.runTest(executor, timeout, unit);
            }
        } finally {
            // Zakończenie ExecutorService
            executor.shutdown();
            try {
                // Oczekiwanie na zakończenie istniejących zadań
                if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                    // Jeśli nie zakończą się w ciągu sekundy, wymuś zakończenie
                }
            } catch (InterruptedException ie) {
                // Jeśli aktualny wątek został przerwany, wymuś zakończenie
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }

        // Podsumowanie testów
        System.out.println("\n\nTesting ended.\n" + passed + "/" + all + " tests passed.");

    }

    private static Locator getLocator(int minCol, int maxCol, int minRow, int maxRow, Position2D correct) {
        return new Locator() {
            @Override
            public int maxRow() {
                return maxRow;
            }

            @Override
            public int minRow() {
                return minRow;
            }

            @Override
            public int maxCol() {
                return maxCol;
            }

            @Override
            public int minCol() {
                return minCol;
            }

            @Override
            public void here(Position2D position) throws ColumnToHighException, ColumnToLowException, RowToHighException, RowToLowException {
                if (position.col() < correct.col()) throw new ColumnToLowException();
                else if (position.col() > correct.col()) throw new ColumnToHighException();
                else if (position.row() < correct.row()) throw new RowToLowException();
                else if (position.row() > correct.row()) throw new RowToHighException();
            }
        };
    }

}