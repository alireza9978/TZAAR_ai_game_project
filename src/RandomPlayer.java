import models.Action;
import models.Game;
import models.Player;
import models.PlayerType;

import java.util.ArrayList;
import java.util.Random;

public class RandomPlayer extends Player {

    public RandomPlayer(PlayerType type) {
        super(type);
    }

    @Override
    public Action forceAttack(Game game) {
        ArrayList<Action> actions = getAllActions(game.getBoard());
        Random random = new Random();
        while (!actions.isEmpty()) {
            Action action = actions.get(random.nextInt(actions.size()));
            if (action.getType() == Action.ActionType.attack) {
                return action;
            } else {
                actions.remove(action);
            }
        }
        return null;
    }

    @Override
    public Action secondAction(Game game) {
        ArrayList<Action> actions = getAllActions(game.getBoard());
        Random random = new Random();
        return actions.get(random.nextInt(actions.size()));
    }


}
