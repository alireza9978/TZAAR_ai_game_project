import models.Game;
import models.Player;
import models.PlayerType;

public class Main {

    public static void main(String[] args) {
        RandomPlayer whitePlayer = new RandomPlayer(PlayerType.white);
        Ai blackPlayer = new Ai(PlayerType.black);
        Game game = new Game(whitePlayer, blackPlayer);
        Player player = game.play();
        System.out.println(player.getType());
    }

}
