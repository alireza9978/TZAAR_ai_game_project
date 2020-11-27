package models;

import java.util.ArrayList;

public abstract class Player {

    private final PlayerType type;

    @Override
    public String toString() {
        return "Player{" +
                "type=" + type +
                '}';
    }

    public Player(PlayerType type) {
        this.type = type;
    }

    public PlayerType getType() {
        return type;
    }

    public abstract Action forceAttack(Game game);

    public abstract Action secondAction(Game game);

    public ArrayList<Action> getAllActions(Board board) {
        ArrayList<Action> actions = new ArrayList<>();
        for (Board.BoardRow row : board.getRows()) {
            for (Board.BoardCell cell : row.boardCells) {
                if (cell != null && cell.bead != null && cell.bead.getPlayer().getType() == type) {
                    actions.addAll(cell.bead.getActions(cell));
                }
            }
        }
        actions.add(new Action(Action.ActionType.nothing, null, null));
        return actions;
    }


}
