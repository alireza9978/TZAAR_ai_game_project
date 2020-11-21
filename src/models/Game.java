package models;

import static models.Board.isTherePath;

public class Game {

    private Player white;
    private Player black;
    private Board board;

    public Game(Player whitePlayer, Player blackPlayer) {
        this.white = whitePlayer;
        this.black = blackPlayer;
        this.board = new Board(black, white);
    }

    public Game(Player white, Player black, Board board) {
        this.white = white;
        this.black = black;
        this.board = board;
    }

    public Player play() {
        Player winner;
        Action action = white.forceAttack(copy());
        if (action == null) {
            return black;
        }
        if (applyAction(white, action, true)) {
            winner = black;
            return winner;
        }
        winner = getWinner();
        if (winner != null) {
            return winner;
        }
        while (true) {
            action = black.forceAttack(copy());
            if (action == null) {
                winner = white;
                break;
            }
            if (applyAction(black, action, true)) {
                winner = white;
                break;
            }
            winner = getWinner();
            if (winner != null) {
                break;
            }
            action = black.secondAction(copy());
            if (action == null) {
                winner = white;
                break;
            }
            if (applyAction(black, action, false)) {
                winner = white;
                break;
            }
            winner = getWinner();
            if (winner != null) {
                break;
            }
            action = white.forceAttack(copy());
            if (action == null) {
                winner = black;
                break;
            }
            if (applyAction(white, action, true)) {
                winner = black;
                break;
            }
            winner = getWinner();
            if (winner != null) {
                break;
            }
            action = white.secondAction(copy());
            if (action == null) {
                winner = black;
                break;
            }
            if (applyAction(white, action, false)) {
                winner = black;
                break;
            }
            winner = getWinner();
            if (winner != null) {
                break;
            }
        }
        board.printComplete();
        return winner;
    }

    public Player getWinner() {
        boolean one = false, two = false, three = false;
        for (Bead bead : white.getAllBead()) {
            switch (bead.getType()) {
                case Tzaars:
                    one = true;
                    break;
                case Tzarras:
                    two = true;
                    break;
                case Totts:
                    three = true;
                    break;
            }
        }
        if (!(one && two && three)) {
            return black;
        }
        one = false;
        two = false;
        three = false;
        for (Bead bead : black.getAllBead()) {
            switch (bead.getType()) {
                case Tzaars:
                    one = true;
                    break;
                case Tzarras:
                    two = true;
                    break;
                case Totts:
                    three = true;
                    break;
            }
        }
        if (!(one && two && three)) {
            return white;
        }
        return null;
    }

    private boolean applyAction(Player player, Action action, boolean attack) {
        System.out.println(action);
        if (attack) {
            if (action.getType() != Action.ActionType.attack) {
                return true;
            } else {
                if (action.getStart().bead.getPlayer().getType() != player.getType() ||
                        action.getTarget().bead.getPlayer().getType() == player.getType()) {
                    return true;
                }
            }
        } else {
            if (action.getType() == Action.ActionType.nothing) {
                board.printComplete();
                return false;
            } else if (action.getType() == Action.ActionType.reinforce) {
                if (action.getStart().bead.getPlayer().getType() != player.getType() ||
                        action.getTarget().bead.getPlayer().getType() != player.getType()) {
                    return true;
                }
            } else {
                if (action.getStart().bead.getPlayer().getType() != player.getType() ||
                        action.getTarget().bead.getPlayer().getType() == player.getType()) {
                    return true;
                }
            }
        }

        if (isTherePath(action.getStart(), action.getTarget())) {
            if (action.getType() == Action.ActionType.reinforce) {
                Bead start = board.getRows()[action.getStart().row].boardCells[action.getStart().col].bead;
                player.removeBead(start);
                board.getRows()[action.getStart().row].boardCells[action.getStart().col].bead = null;
                board.getRows()[action.getTarget().row].boardCells[action.getTarget().col].bead.addBead(start);
            } else {
                Bead start = board.getRows()[action.getStart().row].boardCells[action.getStart().col].bead;
                Bead target = board.getRows()[action.getTarget().row].boardCells[action.getTarget().col].bead;
                if (start.getHeight() < target.getHeight()) {
                    return true;
                }
                player.removeBead(start);
                board.getRows()[action.getStart().row].boardCells[action.getStart().col].bead = null;
                board.getRows()[action.getTarget().row].boardCells[action.getTarget().col].bead = start;
            }
            board.printComplete();
            return false;
        } else
            return true;
    }

    public boolean applyActionTwo(Player player, Action action, boolean attack) {
        if (attack) {
            if (action.getType() != Action.ActionType.attack) {
                return true;
            } else {
                if (action.getStart().bead.getPlayer().getType() != player.getType() ||
                        action.getTarget().bead.getPlayer().getType() == player.getType()) {
                    return true;
                }
            }
        } else {
            if (action.getType() == Action.ActionType.nothing) {
                return false;
            } else if (action.getType() == Action.ActionType.reinforce) {
                if (action.getStart().bead.getPlayer().getType() != player.getType() ||
                        action.getTarget().bead.getPlayer().getType() != player.getType()) {
                    return true;
                }
            } else {
                if (action.getStart().bead.getPlayer().getType() != player.getType() ||
                        action.getTarget().bead.getPlayer().getType() == player.getType()) {
                    return true;
                }
            }
        }

        if (isTherePath(action.getStart(), action.getTarget())) {
            if (action.getType() == Action.ActionType.reinforce) {
                Bead start = board.getRows()[action.getStart().row].boardCells[action.getStart().col].bead;
                player.removeBead(start);
                board.getRows()[action.getStart().row].boardCells[action.getStart().col].bead = null;
                board.getRows()[action.getTarget().row].boardCells[action.getTarget().col].bead.addBead(start);
            } else {
                Bead start = board.getRows()[action.getStart().row].boardCells[action.getStart().col].bead;
                Bead target = board.getRows()[action.getTarget().row].boardCells[action.getTarget().col].bead;
                if (start.getHeight() < target.getHeight()) {
                    return true;
                }
                player.removeBead(start);
                board.getRows()[action.getStart().row].boardCells[action.getStart().col].bead = null;
                board.getRows()[action.getTarget().row].boardCells[action.getTarget().col].bead = start;
            }
            return false;
        } else
            return true;
    }

    public Game copy() {
        return new Game(white, black, new Board(board));
    }

    public Board getBoard() {
        return board;
    }

    public void setWhite(Player white) {
        this.white = white;
    }

    public void setBlack(Player black) {
        this.black = black;
    }
}
