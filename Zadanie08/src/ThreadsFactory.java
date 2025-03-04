/**
 * Interfejs fabryki wÄtkĂłw. KaĹźda z metod dostarcza tylko
 * jednego wÄtku. KaĹźdÄ z metod wolno wywoĹaÄ tylko jeden raz.
 */
public interface ThreadsFactory {
    /**
     * Metoda zwraca obiekt-wątek w stanie NEW (nieuruchomiony). Wątek przeznaczony
     * wyłcznie do odczytu danych rozpoczynając od pozycji 0.
     *
     * @param run kod do wykonania w wątku
     * @return watek, który przekazany kod wykona
     */
    Thread leftReadOnlyThread(Runnable run);

    /**
     * Metoda zwraca obiekt-watek w stanie NEW (nieuruchomiony). Watek przeznaczony
     * wyłacznie do odczytu danych rozpoczynając od maksymalnej pozycji w tablicy.
     *
     * @param run kod do wykonania w watku
     * @return watek, który przekazany kod wykona
     */
    Thread rightReadOnlyThread(Runnable run);

    /**
     * Watek, który ma prawa do zapisu.
     *
     * @param run kod do realizacji w watku
     * @return watek, w którym kod zostanie wykonany
     */
    Thread writeOnlyThread(Runnable run);
}