public class FastFinder implements FindSth {

    @Override
    public Position2D tryToFind(Locator locator) {

        int beginCol = locator.minCol(); //poczatek zakresu kolumn
        int endCol = locator.maxCol();  //koniec zakeesu kolumn
        int beginRow = locator.minRow(); //poczatek zakresu rzedow
        int endRow = locator.maxRow(); //koniec zakresu rzedow

        while (beginRow <= endRow && beginCol <= endCol) {
            //zaczynamy od srodkowych punktow
            int middleCol = (beginCol + endCol) / 2;
            int middleRow = (beginRow + endRow) / 2;

            Position2D currentPosition = new Position2D(middleCol, middleRow);

            try {
                locator.here(currentPosition);
                //Jak znajdziemy pozycje i nie wyrzuci wyjątku to ją zwracamy
                return currentPosition;
            } catch (Locator.ColumnToHighException e) {
                endCol = middleCol - 1;
            } catch (Locator.ColumnToLowException e) {
                beginCol = middleCol + 1;
            } catch (Locator.RowToHighException e) {
                endRow = middleRow - 1;
            } catch (Locator.RowToLowException e) {
                beginRow = middleRow + 1;
            } //dodajemy badz odejmujemy 1 by uniknac zapetlenia na tym samym wyniku
              // np gdy begin=24 end=25 niepozwoliloby nam uzyskac szukanego 25
        }
        return null; //gdyby pozycja byla poza zakresem
    }
}