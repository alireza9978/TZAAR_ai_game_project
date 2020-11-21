package models;

public class Action {

    public enum ActionType {
        reinforce, attack, nothing
    }

    private ActionType type;
    private Board.BoardCell start;
    private Board.BoardCell target;

    public Action(ActionType type, Board.BoardCell start, Board.BoardCell target) {
        this.type = type;
        this.start = start;
        this.target = target;
    }

    public ActionType getType() {
        return type;
    }

    public void setType(ActionType type) {
        this.type = type;
    }

    public Board.BoardCell getStart() {
        return start;
    }

    public void setStart(Board.BoardCell start) {
        this.start = start;
    }

    public Board.BoardCell getTarget() {
        return target;
    }

    public void setTarget(Board.BoardCell target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "Action{" +
                "type=" + type +
                ", start=" + start +
                ", target=" + target +
                '}';
    }
}
