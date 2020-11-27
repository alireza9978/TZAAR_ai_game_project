package models;

import java.util.ArrayList;

public class Bead {

    private BeadType type;
    private final Player player;
    private int height = 1;

    @Override
    public String toString() {
        return "Bead{" +
                "type=" + type +
                ", player=" + player +
                ", height=" + height +
                '}';
    }

    public Bead(BeadType type, Player player) {
        this.type = type;
        this.player = player;
    }

    public Bead(Bead bead) {
        this.type = bead.type;
        this.player = bead.player;
        this.height = bead.height;
    }

    public void addBead(Bead target) {
        this.height += target.height;
        this.type = target.type;
    }

    public ArrayList<Action> getActions(Board.BoardCell cell) {
        ArrayList<Action> actions = new ArrayList<>();

        for (Board.NeighborType type : cell.neighbors.keySet()) {
            Board.BoardCell temp = cell.neighbors.get(type);
            while (temp != null && temp.bead == null) {
                temp = temp.neighbors.get(type);
            }
            if (temp != null) {
                if (temp.bead.getPlayer().getType() == player.getType()) {
                    actions.add(new Action(Action.ActionType.reinforce, cell, temp));
                } else {
                    if (temp.bead.getHeight() <= getHeight()) {
                        actions.add(new Action(Action.ActionType.attack, cell, temp));
                    }
                }

            }
        }

        return actions;
    }

    public BeadType getType() {
        return type;
    }

    public Player getPlayer() {
        return player;
    }

    public int getHeight() {
        return height;
    }

}
