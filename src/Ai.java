import models.Action;
import models.Game;
import models.Player;
import models.PlayerType;

import java.util.ArrayList;

public class Ai extends Player {

    private int doneActions = 0;
    private int maxDepth = 100;

    public Ai(PlayerType type) {
        super(type);
    }

    @Override
    public Action forceAttack(Game game) {
        if (doneActions == 0 && getType() == PlayerType.white) {
            int maxValue = Integer.MIN_VALUE;
            Action bestAction = null;
            ArrayList<Action> actions = getAllActions(game.getBoard());
            for (Action action : actions) {
                if (action.getType() == Action.ActionType.attack) {
                    Game copyGame = game.copy();
                    if (copyGame.applyActionTwo(this, action, true)) {
                        continue;
                    }
                    Player winner = copyGame.getWinner();
                    if (winner != null) {
                        if (winner.getType() == getType()) {
                            return action;
                        }
                    } else {
                        int temp = Math.max(maxValue, minForceAttack(game, 0));
                        if (temp > maxValue) {
                            maxValue = temp;
                            bestAction = action;
                        }
                    }
                }
            }
            doneActions++;
            return bestAction;
        } else {
            int maxValue = Integer.MIN_VALUE;
            Action bestAction = null;
            ArrayList<Action> actions = getAllActions(game.getBoard());
            for (Action action : actions) {
                if (action.getType() == Action.ActionType.attack) {
                    Game copyGame = game.copy();
                    if (copyGame.applyActionTwo(this, action, true)) {
                        continue;
                    }
                    Player winner = copyGame.getWinner();
                    if (winner != null) {
                        if (winner.getType() == getType()) {
                            return action;
                        }
                    } else {
                        int temp = Math.max(maxValue, maxSecondMove(game, 0));
                        if (temp > maxValue) {
                            maxValue = temp;
                            bestAction = action;
                        }
                    }
                }
            }
            doneActions++;
            return bestAction;
        }
    }

    private int eval(Game game) {
        return 0;
    }

    @Override
    public Action secondAction(Game game) {
        int maxValue = Integer.MIN_VALUE;
        Action bestAction = null;
        ArrayList<Action> actions = getAllActions(game.getBoard());
        for (Action action : actions) {
            Game copyGame = game.copy();
            if (copyGame.applyActionTwo(this, action, false)) {
                continue;
            }
            Player winner = copyGame.getWinner();
            if (winner != null) {
                if (winner.getType() == getType()) {
                    return action;
                }
            } else {
                int temp = Math.max(maxValue, minForceAttack(game, 0));
                if (temp > maxValue) {
                    maxValue = temp;
                    bestAction = action;
                }
            }
        }
        doneActions++;
        return bestAction;
    }

    private int maxForceAttack(Game game, int depth) {
        if (depth == maxDepth) {
            return eval(game);
        }

        int maxValue = Integer.MIN_VALUE;
        ArrayList<Action> actions = getAllActions(game.getBoard());
        for (Action action : actions) {
            if (action.getType() == Action.ActionType.attack) {
                Game copyGame = game.copy();
                if (copyGame.applyActionTwo(this, action, true)) {
                    continue;
                }
                Player winner = copyGame.getWinner();
                if (winner != null) {
                    if (winner.getType() == getType()) {
                        return Integer.MAX_VALUE;
                    }
                } else {
                    maxValue = Math.max(maxValue, maxSecondMove(copyGame, depth + 1));
                }
            }
        }
        return maxValue;
    }

    private int maxSecondMove(Game game, int depth) {
        if (depth == maxDepth) {
            return eval(game);
        }

        int maxValue = Integer.MIN_VALUE;
        ArrayList<Action> actions = getAllActions(game.getBoard());
        for (Action action : actions) {
            Game copyGame = game.copy();
            if (copyGame.applyActionTwo(this, action, false)) {
                continue;
            }
            Player winner = copyGame.getWinner();
            if (winner != null) {
                if (winner.getType() == getType()) {
                    return Integer.MAX_VALUE;
                }
            } else {
                maxValue = Math.max(maxValue, minForceAttack(game, depth + 1));
            }
        }
        return maxValue;
    }

    private int minForceAttack(Game game, int depth) {
        if (depth == maxDepth) {
            return eval(game);
        }

        int minValue = Integer.MAX_VALUE;
        ArrayList<Action> actions = getAllActions(game.getBoard());
        for (Action action : actions) {
            if (action.getType() == Action.ActionType.attack) {
                Game copyGame = game.copy();
                if (copyGame.applyActionTwo(this, action, true)) {
                    continue;
                }
                Player winner = copyGame.getWinner();
                if (winner != null) {
                    if (winner.getType() == getType().reverse()) {
                        return Integer.MIN_VALUE;
                    }
                } else {
                    minValue = Math.min(minValue, minSecondMove(game, depth + 1));
                }
            }
        }
        return minValue;

    }

    private int minSecondMove(Game game, int depth) {
        if (depth == maxDepth) {
            return eval(game);
        }

        int minValue = Integer.MAX_VALUE;
        ArrayList<Action> actions = getAllActions(game.getBoard());
        for (Action action : actions) {
            Game copyGame = game.copy();
            if (copyGame.applyActionTwo(this, action, false)) {
                continue;
            }
            Player winner = copyGame.getWinner();
            if (winner != null) {
                if (winner.getType() == getType().reverse()) {
                    return Integer.MIN_VALUE;
                }
            } else {
                minValue = Math.min(minValue, maxForceAttack(game, depth + 1));
            }
        }

        return minValue;
    }
}
