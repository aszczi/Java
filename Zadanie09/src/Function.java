/**
 * Interfejs funkcji dwĂłch argumentĂłw.
 */
public interface Function {
    /**
     * Metoda zwraca wartoĹÄ funkcji wyliczonÄ na podstawie wartoĹci dwĂłch
     * parametrĂłw row i col. Czas wyznaczania wartoĹci funkcji nie bÄdzie staĹy.
     * MoĹźe zaleĹźeÄ od argumentĂłw wywoĹania, moĹźe byÄ wrÄcz losowy. Funkcja
     * bezpieczna w programach wielowÄtkowych.
     *
     * @param row parametr pierwszy
     * @param col parametr drugi
     * @return wartoĹÄ funkcji
     */
    double get(int row, int col);
}