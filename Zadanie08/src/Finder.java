import java.util.List;

/**
 * Interfejs poszukiwacza
 */
public interface Finder {
    /**
     * Ustawia dostęp do fabryki watkow
     *
     * @param factory fabryka watkow
     */
    void setThreadsFactory(ThreadsFactory factory);

    /**
     * Ustawia dostęp do tablicy, którą należy przeszukać
     *
     * @param array tablica do przeszukania
     */
    void setArray(Array array);

    /**
     * Uruchamia poszukiwania w tablicy wartości value.
     *
     * @param value wartość poszukiwana w tablicy
     * @return lista położeń, w których znaleziono value
     */
    List<Integer> start(int value);
}