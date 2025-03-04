import java.util.List;
import java.util.Map;

/**
 * Kasa bazowa narzÄdzia do kompresji ciÄgĂłw znakĂłw.
 */
public abstract class Compression {
    /**
     * Dostarcza dane do przetworzenia. CaĹy tekst dostarczany jest w postaci
     * pojedynczego wywoĹania tej metody. CiÄg zawiera sĹowa i dodatkowe znaki. CiÄg
     * nie zawiera cyfr.
     *
     * @param input ciÄg do kompresji
     */
    abstract public void input(String input);

    /**
     * Histogram wystÄpieĹ sĹĂłw. Klucz to sĹowo, wartoĹÄ to liczba jego wystÄpieĹ w
     * ciÄgu przekazanym za pomocÄ input.
     *
     * @return mapa z histogramem liczby wystÄpieĹ sĹowa
     */
    abstract public Map<String, Integer> histogram();

    /**
     * Lista sĹĂłw (sĹownik) uĹźytych do zmniejszenia rozmiaru oryginalnej wiadomoĹci.
     * Liczba sĹĂłw wskazuje na liczbÄ bitĂłw uĹźytych do ich zakodowania. KolejnoĹÄ
     * sĹĂłw na liĹcie odpowiada wartoĹci kodu. Np. dla czterech sĹĂłw pierwsze
     * kodowane jest za pomocÄ 00. Brak rozwiÄzania (wynik kodowania dĹuĹźszy od oryginaĹu)
     * sygnalizowany jest za pomocÄ sĹownika o rozmiarze 0.
     *
     * @return sĹownik
     */
    abstract public List<String> code();

    /**
     * Zakodowany ciÄg wejĹciowy, czeĹÄ sĹow zamieniona na liczby binarne.
     *
     * @return ciÄg wynikowy
     */
    abstract public String output();

    /**
     * Metoda dekoduje ciag z liczbami binarnymi zamiast sĹĂłw na podstawie
     * dostarczonego kodu (sĹownika).
     *
     * @param input ciÄg do zdekodowania
     * @param code  lista sĹĂłw wchodzÄcych w skĹad sĹownika
     * @return zdekodowany ciÄg
     */
    abstract public String decode(String input, List<String> code);
}