package battleship;

import battleship.Misc.Coordinate;
import battleship.ship.SetOfShips;
import battleship.ship.Ship;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {

    String name;
    char symbolPlayer;
    List<Ship> ships;
    GameField gameField;

    public Player(String name, char symbolPlayer) {
        this.name = name;
        this.symbolPlayer = symbolPlayer;
        ships = new ArrayList<>();
        gameField = new GameField();
    }

    private boolean checkLengthOfNewShip(int needLength, int currentLength, String nameOfShip) {
        if (needLength != currentLength) {
            System.out.println("Error! Wrong length of the " + nameOfShip + "! Try again:");
            return false;
        }
        return true;
    }

    private boolean checkDiagonalPosition(Coordinate firstCoordination, Coordinate secondCoordination) {
        if (firstCoordination.getY() != secondCoordination.getY() &&
                firstCoordination.getX() != secondCoordination.getX()) {
            System.out.println("Error! Wrong ship location! Try again:");
            return false;
        }
        return true;
    }

    private boolean checkCloseToAnother(Ship anotherShip) {
        for (int i = 0; i < ships.size(); i++) {
            if (!ships.get(i).isPlaceFree(anotherShip)) {
                System.out.println("Error! You placed it too close to another one. Try again:");
                return false;
            }
        }
        return true;
    }

    public GameField getGameField() {
        return gameField;
    }

    public static boolean checkCoordinateInField(Coordinate coordinate) {
        return coordinate.getX() <= GameField.SizeOfField - 1 && coordinate.getY() <= GameField.SizeOfField - 1;
    }

    private Ship tryShot(Coordinate shootCoordination) {
        for (int i = 0; i < ships.size(); i++) {
            if (ships.get(i).maybeHit(shootCoordination)) {
                return ships.get(i);
            }
        }
        return null;
    }

    private void oneShoot(Player anotherPlayer, Coordinate shootCoordination) {
        Ship shipDamage = anotherPlayer.tryShot(shootCoordination);
        GameField anotherField = anotherPlayer.getGameField();
        if (shipDamage != null) {
            anotherField.updateField(shipDamage);

            if (shipDamage.isAlive()) {
                System.out.println("\nYou hit a ship!\n");
            } else {
                if (anotherPlayer.isHaveAliveShips()) {
                    System.out.println("You sank a ship! Specify a new target:");
                } else {
                    System.out.println("\nYou sank the last ship. You won. Congratulations!\n");
                }
            }
        } else {
            anotherField.updateField(shootCoordination, 'M');
            System.out.println("\nYou missed!\n");
        }
    }

    public void loopShoot(Player anotherPlayer) {
        Scanner scanner = new Scanner(System.in);
        GameField anotherField = anotherPlayer.getGameField();

        anotherField.showFogOfWar();
        GameField.printSplit();
        gameField.showField();
        System.out.println(name + ", it's your turn");
        boolean isNotHaveError = false;
        while (!isNotHaveError) {
            Coordinate shootCoordination = convertCoordinate(scanner.nextLine());
            System.out.println();

            isNotHaveError = checkCoordinateInField(shootCoordination);

            if (isNotHaveError) {
                oneShoot(anotherPlayer, shootCoordination);
            } else {
                System.out.println("\nError! You entered the wrong coordinates! Try again:");
            }
        }
        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();
    }


    public static Coordinate convertCoordinate(String coord) {
        int y = coord.charAt(0) - 'A';
        int x = Integer.parseInt( coord.substring(1)) - 1;
        return new Coordinate(x, y);
    }

    private Coordinate findBeginCoordinate(Coordinate firstCoordinate, Coordinate secondCoordinate, boolean isVertical) {

        Coordinate beginCoordinate;
        if (isVertical) {
            beginCoordinate = firstCoordinate.getY() < secondCoordinate.getY() ? firstCoordinate : secondCoordinate;
        } else {
            beginCoordinate = firstCoordinate.getX() < secondCoordinate.getX() ? firstCoordinate : secondCoordinate;
        }

        return beginCoordinate;
    }

    private Coordinate findEndCoordinate(Coordinate firstCoordinate, Coordinate secondCoordinate, boolean isVertical) {
        Coordinate endCoordinate;
        if (isVertical) {
            endCoordinate = firstCoordinate.getY() < secondCoordinate.getY() ?  secondCoordinate  : firstCoordinate;
        } else {
            endCoordinate = firstCoordinate.getX() < secondCoordinate.getX() ? secondCoordinate : firstCoordinate;
        }
        return endCoordinate;
    }

    private int calculationLengthShip(Coordinate begin, Coordinate end, boolean isVertical) {
        int lengthShip;
        if (isVertical) {
            lengthShip = end.getY() - begin.getY();
        } else {
            lengthShip = end.getX() - begin.getX();
        }
        return lengthShip + 1;
    }

    public boolean isHaveAliveShips() {
        for (Ship oneShip : ships) {
            if (oneShip.isAlive())
                return true;
        }
        return false;
    }

    public Ship getShip(int indx) {
        return ships.get(indx);
    }

    public Ship[] getAllShip() {
        Ship[] returnShips = new Ship[ships.size()];
        ships.toArray(returnShips);
        return returnShips;
    }

    public Ship createShip(Coordinate firstCoordinate, Coordinate secondCoordinate) {
        boolean isSheepVertical = firstCoordinate.getY() != secondCoordinate.getY();
        Coordinate beginCoordinate = findBeginCoordinate(firstCoordinate, secondCoordinate, isSheepVertical);
        Coordinate endCoordinate = findEndCoordinate(firstCoordinate, secondCoordinate, isSheepVertical);
        int lengthShip = calculationLengthShip(beginCoordinate, endCoordinate, isSheepVertical);

        return new Ship(symbolPlayer, beginCoordinate, lengthShip, isSheepVertical);
    }

    public void createSquadron() {
        Scanner scanner = new Scanner(System.in);
        SetOfShips[] allShips = new SetOfShips[] {SetOfShips.Carrier, SetOfShips.Battleship, SetOfShips.Submarine,
                SetOfShips.Cruiser, SetOfShips.Destroyer};

        System.out.println(name + ", place your ships on the game field\n");
        gameField.showField();
        for (int i = 0; i < allShips.length; i++) {
            String currentShip = allShips[i].getName();
            int currentCountParts = allShips[i].getCountPart();

            boolean isHaveError = false;
            Ship newShip = null;
            System.out.println("\nEnter the coordinates of the " + currentShip + " (" + currentCountParts + " cells):");
            while (!isHaveError) {
                String coordinate = scanner.nextLine();
                String[] twoCoordinate = coordinate.split(" ");
                Coordinate firstCoordinate = convertCoordinate(twoCoordinate[0]);
                Coordinate secondCoordinate = convertCoordinate(twoCoordinate[1]);
                newShip = createShip(firstCoordinate, secondCoordinate);
                isHaveError = checkLengthOfNewShip(currentCountParts, newShip.getLength(), currentShip);
                isHaveError &= checkDiagonalPosition(firstCoordinate, secondCoordinate);
                isHaveError &= checkCloseToAnother(newShip);
            }
            ships.add(newShip);
            gameField.updateField(newShip);
            gameField.showField();
            System.out.println();
        }
        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();
    }
}
