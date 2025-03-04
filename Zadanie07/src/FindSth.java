/**
 * Bardzo uniwersalny interfejs poszukiwania czegokolwiek
 */
public interface FindSth {
    /**
     * Zlecenie próby znalezienia czegoś.
     *
     * @param locator interfejs umożliwiający poszukiwania
     * @return położenie, w którym znaleziono coś
     */
    Position2D tryToFind(Locator locator);
}






