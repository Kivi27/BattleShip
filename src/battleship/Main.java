package battleship;

public class Main {


    public static void main(String[] args) {

        Player player1 = new Player("Player 1", 'O');
        Player player2 = new Player("Player 2",'O');

        player1.createSquadron();
        player2.createSquadron();


        System.out.println("The game starts!\n");
        while (player1.isHaveAliveShips() && player2.isHaveAliveShips()) {
            player1.loopShoot(player2);
            player2.loopShoot(player1);
        }
    }
}
