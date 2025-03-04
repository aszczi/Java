/**
 * Interfejs lokalizatora
 */
public interface Locator {
    /**
     * WyjÄtek zgĹaszanny, gdy podana kolumna jest duĹźa
     */
    class ColumnToHighException extends Exception {
        private static final long serialVersionUID = 1338640189615772849L;
    };

    /**
     * WyjÄtek zgĹaszany, gdy podana kolumna jest za maĹa
     */
    class ColumnToLowException extends Exception {
        private static final long serialVersionUID = -1149972781727632717L;
    }

    /**
     * WyjÄtek zgĹaszany, gdy podany wiersz jest za duĹźy
     */
    class RowToHighException extends Exception {
        private static final long serialVersionUID = -7339887856127998936L;
    }

    /**
     * WyjÄtek zgĹaszany, gdy podany wiersz jest za maĹy
     */
    class RowToLowException extends Exception {
        private static final long serialVersionUID = -1932358281879377610L;
    }

    /**
     * Informacja o maksymalnym, poprawnym indeksie wiersza.
     *
     * @return maksymalna wartoĹÄ jakÄ moĹźna przypisaÄ do wiersza
     */
    int maxRow();

    /**
     * Informacja o minimalnym, poprawnym indeksie wiersza
     *
     * @return najmniejsza wartoĹÄ jakÄ moĹźna przypisaÄ do wiersza
     */
    int minRow();

    /**
     * Informacja o maksymalnym, poprawnym indeksie kolumny.
     *
     * @return maksymalna wartoĹÄ jakÄ moĹźna przypisaÄ do kolumny
     */
    int maxCol();

    /**
     * Informacja o minimalnym, poprawnym indeksie kolumny
     *
     * @return najmniejsza wartoĹÄ jakÄ moĹźna przypisaÄ do kolumny
     */
    int minCol();

    /**
     * Test, czy to podana pozycja jest tą poszukiwaną. W przypadku pudła zgłaszany
     * jest wyjątek informujący o powodzie porażki. Z uwagi na to, że można
     * jednocześnie zgłosił tylko jeden wyjątek, w przypadku jednoczesnego błędnego
     * podania złej kolumny i złego wiersza zgłaszany jest jeden z dwóch możliwych
     * wyjątków. Wybór zgłaszanego wyjątku odbywa się losowo. Znalezienie
     * poszukiwanego miejsca można rozpoznać, bo wtedy metoda nie zgłosi żadnego
     * wyjąku.
     *
     *
     * @param position pozycja
     * @throws ColumnToHighException kolumna za duża
     * @throws ColumnToLowException  kolumna za mała
     * @throws RowToHighException    wiersz za duży
     * @throws RowToLowException     wiersz za mały
     */
    void here(Position2D position)
            throws ColumnToHighException, ColumnToLowException, RowToHighException, RowToLowException;
}






