import java.util.List;

/**
 * Interfejs programu wspĂłĹbieĹźnie wyznaczajÄcego wartoĹci przekazanej funkcji.
 */
public interface ParallelCalculations {
    /**
     * Metoda wyznacza wartoĹÄ funkcji function w (size)x(size) punktach. FunkcjÄ
     * naleĹźy wyliczyÄ dla wszystkich par agrumentĂłw z zakresu od 0 do size-1
     * uzywajÄc w celu przypieszenia obliczeĹ threads wÄtkĂłw. Metoda zwraca wynik
     * w postaci listy list wartoĹci. JeĹli referencja do listy list to
     * <code>foo</code> a <code>bar</code> to referencja do funkcji oraz:
     * <pre>
     * double v1 = foo.get(row).get(col);
     * double v2 = bar( row, col );
     * </pre>
     * to wartoĹci <code>v1</code> i <code>v2</code> sÄ identyczne.
     *
     * @param function funkcja dwĂłch argumentĂłw do stablicowania
     * @param size liczba wierszy/kolumn
     * @param threads liczba wÄtkĂłw, ktĂłrych naleĹźy uĹźyÄ w obliczeniach
     * @return lista list wartoĹci.
     */
    List<List<Double>> map(Function function, int size, int threads);
}