import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class FastParallelCalculations implements ParallelCalculations {
    @Override
    public List<List<Double>> map(Function function, int size, int threads) {

        int[] counter = {0}; // Współdzielony licznik segmentów danych
        int segmentSize = Math.max(1, size / (threads * 4));
        int totalSegments = (size * size + segmentSize - 1) / segmentSize;

        List<List<Double>> result = new ArrayList<>(size);  // Inicjalizaujemy liste wynikową

        ExecutorService executor = Executors.newFixedThreadPool(threads);
        CompletionService<Void> completionService = new ExecutorCompletionService<>(executor);

        for (int i = 0; i < size; i++) {
            List<Double> row = new ArrayList<>(size);
            for (int j = 0; j < size; j++) {
                row.add(0.0);
            }
            result.add(row);
        }

        // Startujemy wątki
        for (int t = 0; t < threads; t++) {
            completionService.submit(() -> {
                while (true) {
                    int segmentId;
                    // Pobieramy kolejny segmentu danych do przetworzenia
                    synchronized (counter) {
                        if (counter[0] >= totalSegments) {
                            break;
                        }
                        segmentId = counter[0]++;
                    }

                    // Zakres dla segmentu danych
                    int startIndex = segmentId * segmentSize;
                    int endIndex = Math.min(startIndex + segmentSize, size * size);

                    for (int index = startIndex; index < endIndex; index++) {
                        int row = index / size;
                        int col = index % size;
                        double value = function.get(row, col);
                        result.get(row).set(col, value);
                    }
                }
                return null;
            });
        }

        // Czekamy na zakończenie wszystkich wątków
        try {
            for (int i = 0; i < threads; i++) {
                completionService.take().get();
            }
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } finally {
            executor.shutdown();
            try {
                executor.awaitTermination(1, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        return result;
    }
}
