/**
 * Interfejs dostepu do jednowymiarowej tablicy
 */
public interface Array {
    /**
     * Rozmiar tablicy. Dozwolone indeksy od zero do size()-1.
     *
     * @return rozmiar tablicy
     */
    public int size();

    /**
     * Zawartość tablicy na pozycji position.
     *
     * @param position położenie w tablicy
     * @return zapisana w tablicy wartość
     */
    public int get(int position);

    /**
     * Ustawia 0 na wskazanej pozycji tablicy.
     *
     * @param position pozycja w tablicy, w której zapisane zostanie 0.
     */
    public void set0(int position);
}