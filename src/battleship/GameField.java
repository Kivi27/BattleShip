package battleship;

import battleship.Misc.Coordinate;
import battleship.ship.PartOfShip;
import battleship.ship.Ship;

public class GameField {

    public static final int SizeOfField = 10;
    private static final char symbolEmptyCell = '~';
    private static final char beginSideLetter = 'A';

    private char[][] field;

    public GameField() {
        field = new char[SizeOfField][SizeOfField];

        for (int i = 0; i < SizeOfField; i++) {
            for (int j = 0; j < SizeOfField; j++) {
                field[i][j] = symbolEmptyCell;
            }
        }

    }

    private void printTopHead() {
        System.out.print("  ");
        for (int i = 0; i < SizeOfField; i++) {
            System.out.print(i + 1 + " ");
        }
        System.out.println();
    }
    public void updateField(Ship... ships) {
        for (int i = 0; i < ships.length; i++) {
            Ship currentShip = ships[i];
            for (int j = 0; j < currentShip.getLength(); j++) {
                PartOfShip partOfShip = currentShip.getPartOfShips(j);
                field[partOfShip.getY()][partOfShip.getX()] = partOfShip.getCurrentState();
            }
        }
    }

    public void updateField(Coordinate coordinate, char symbol) {
        field[coordinate.getY()][coordinate.getX()] = symbol;
    }

    public static void printSplit() {
        System.out.println("-".repeat(SizeOfField * 2));
    }

    public void showField() {
        printTopHead();

        for (int i = 0; i < SizeOfField; i++) {
            char bottomLetter = (char) (beginSideLetter + i);
            System.out.print(bottomLetter + " ");
            for (int j = 0; j < SizeOfField; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }

    }

    public void showFogOfWar() {
        printTopHead();
        for (int i = 0; i < SizeOfField; i++) {
            char bottomLetter = (char) (beginSideLetter + i);
            System.out.print(bottomLetter + " ");
            for (int j = 0; j < SizeOfField; j++) {
                char printOneCell = field[i][j];

                if (printOneCell == 'O') {
                    printOneCell = symbolEmptyCell;
                }
                System.out.print(printOneCell + " ");
            }
            System.out.println();
        }
    }


}
