package battleship.ship;

import battleship.Misc.Coordinate;

public class Ship {

    private char symbolShip;
    private boolean isVertical;
    private PartOfShip[] partOfShips;

    public Ship(char symbolShip, Coordinate beginCoordinate, int length, boolean isVertical) {
        this.symbolShip = symbolShip;
        this.isVertical = isVertical;

        partOfShips = new PartOfShip[length];
        for (int i = 0; i < partOfShips.length; i++) {
            Coordinate coordinate;
            if (this.isVertical) {
                coordinate = new Coordinate(beginCoordinate.getX(), beginCoordinate.getY() + i);
            } else {
                coordinate = new Coordinate(beginCoordinate.getX() + i, beginCoordinate.getY());
            }
            partOfShips[i] = new PartOfShip(coordinate, symbolShip);
        }
    }

    public int getLength() {
        return partOfShips.length;
    }

    public PartOfShip getPartOfShips(int number) {
        return partOfShips[number];
    }

    public PartOfShip[] getPartOfShips() { return partOfShips;}

    public boolean isPlaceFree(Coordinate coordinate) {
        for (int i = 0; i < partOfShips.length; i++) {
            PartOfShip currentPart = partOfShips[i];
            Coordinate currentCoordinateOfPart = currentPart.getCoordinate();
            int currentX = currentCoordinateOfPart.getX();
            int currentY = currentCoordinateOfPart.getY();
            Coordinate leftSide = new Coordinate(currentX - 1, currentY);
            Coordinate rightSide = new Coordinate(currentX + 1, currentY);
            Coordinate topSide = new Coordinate(currentX, currentY - 1);
            Coordinate bottomSide = new Coordinate(currentX, currentY + 1);
            if (currentCoordinateOfPart.equals(coordinate) || leftSide.equals(coordinate) ||
                    rightSide.equals(coordinate) || topSide.equals(coordinate) || bottomSide.equals(coordinate)) {
                return false;
            }
        }
        return true;
    }

    public boolean isPlaceFree(Ship anotherShip) {
        PartOfShip[] partOfAnotherShip = anotherShip.getPartOfShips();
        for (int i = 0; i < partOfAnotherShip.length; i++) {
            if (!isPlaceFree(partOfAnotherShip[i].getCoordinate())) {
                return false;
            }
        }
        return true;
    }

    public boolean maybeHit(Coordinate coordinate) {
        for (int i = 0; i < partOfShips.length; i++) {
            if (partOfShips[i].getCoordinate().equals(coordinate)) {
                partOfShips[i].setCurrentState('X');
                return true;
            }
        }
        return false;
    }

    public boolean isAlive() {
        for (int i = 0; i < partOfShips.length; i++) {
            if (partOfShips[i].getCurrentState() == symbolShip)
                return true;
        }
        return false;
    }
}
