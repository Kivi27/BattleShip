package battleship.ship;

public enum SetOfShips {
    Carrier("Aircraft Carrier", 5),
    Battleship("Battleship", 4),
    Submarine("Submarine", 3),
    Cruiser("Cruiser", 3),
    Destroyer("Destroyer", 2);

    private String name;
    private int countPart;

    SetOfShips(String name, int countPart) {
        this.name = name;
        this.countPart = countPart;
    }

    public String getName() {
        return name;
    }

    public int getCountPart() {
        return countPart;
    }

}
