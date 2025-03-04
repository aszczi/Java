import java.util.*;

public class CanvasFill extends Fill{
    public int maxPositionCol;
    public int maxPositionRow;

    public boolean isValidPosition(Position2D position) {
        if( position.getRow() >= 0 && position.getCol() >= 0 && position.getCol() <= maxPositionCol && position.getRow() <= maxPositionRow){
            return true;
        }
        else
        {
            return false;
        }
    }


    @Override
    public void fill(Canvas canvas, List<Position2D> neighbours, Position2D start, int brightness) {
        // Stos do pikselkow
        Deque<Position2D> stack = new ArrayDeque<>();
        stack.push(start);

        // Odwiedzone piksele
        Set<Position2D> visited = new HashSet<>();

        while (!stack.isEmpty()) {
            //Pobieramy rozmiar kanwy
            Position2D maxPos = canvas.getMaxPosition();
            maxPositionCol = maxPos.getCol();
            maxPositionRow = maxPos.getRow();

            Position2D current = stack.pop();

            // Pomijamy już odwiedzone pozycje
            if (visited.contains(current)) {
                continue;
            }

            // Sprawdzamy granice kanwy
            try {
                int currentBrightness = canvas.getBrightness(current);

                // Jeśli jasność piksela wieksza badz rowna jasności wypełnienia, to pomijamy
                if (currentBrightness >= brightness) {
                    continue;
                }

                canvas.setBrightness(current, brightness);
                visited.add(current);

                // Sprawdzamy sąsiadów
                for (Position2D neighbour : neighbours) {
                    // Obliczamy nową pozycję
                    Position2D newPosition = new Position2D(
                            current.getCol() + neighbour.getCol(),
                            current.getRow() + neighbour.getRow()
                    );

                    // Sprawdzamy, czy nowa pozycja mieści się w granicach kanwy
                    if (isValidPosition(newPosition)){
                        stack.push(newPosition);
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) { // lapanie wyjatkow
                continue;
            }
        }
    }
}
