package battleship.ship;

import battleship.Misc.Coordinate;

public class PartOfShip {

    private Coordinate coordinate;
    private char currentState;

    public PartOfShip(Coordinate coordinate, char symbolOfShips) {
        this.coordinate = coordinate;
        currentState = symbolOfShips;
    }

    public int getX() {
        return coordinate.getX();
    }

    public int getY() {
        return coordinate.getY();
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public char getCurrentState() {
        return currentState;
    }

    public void setCurrentState(char newState) {
        currentState = newState;
    }
}
