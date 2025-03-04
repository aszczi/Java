/**
 * Interfejs dostępu do pamięci RAM.
 */
public interface Memory {
    /**
     * Pisanie danej value pod adres address.
     *
     * @param address adres komórki pamięci. Od 0 do size() - 1 włącznie.
     * @param value   wartość do pisania. Od 0 do 255 włącznie.
     */
    void set(int address, short value);

    /**
     * Odczyt danej spod adresu address.
     *
     * @param address adres komórki pamięci do odczytu. Od 0 do size() - 1 włącznie.
     * @return zawartość komórki pamięci o adresie address. Od 0 do 255 włącznie.
     */
    short get(int address);

    /**
     * Rozmiar pamięci.
     *
     * @return liczba bajtów w pamięci.
     */
    short size();
}
