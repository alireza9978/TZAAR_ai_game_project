package models;

import static models.Board.isTherePath;

public class Game {

    private final Player white;
    private final Player black;
    private final Board board;

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
//        board.printComplete();
        return winner;
    }

    public Player getWinner() {
        boolean oneWhite = false, twoWhite = false, threeWhite = false;
        boolean oneBlack = false, twoBlack = false, threeBlack = false;
        for (Board.BoardRow row : board.getRows()) {
            for (Board.BoardCell cell : row.boardCells) {
                Bead bead = cell.bead;
                if (bead != null) {
                    if (bead.getPlayer().getType() == PlayerType.white) {
                        switch (bead.getType()) {
                            case Tzaars -> oneWhite = true;
                            case Tzarras -> twoWhite = true;
                            case Totts -> threeWhite = true;
                        }
                    } else {
                        switch (bead.getType()) {
                            case Tzaars -> oneBlack = true;
                            case Tzarras -> twoBlack = true;
                            case Totts -> threeBlack = true;
                        }
                    }
                }
            }
        }
        if (!(oneWhite && twoWhite && threeWhite)) {
            return black;
        }
        if (!(oneBlack && twoBlack && threeBlack)) {
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
            Bead start = board.getRows()[action.getStart().row].boardCells[action.getStart().col].bead;
            if (action.getType() == Action.ActionType.reinforce) {
                board.getRows()[action.getStart().row].boardCells[action.getStart().col].bead = null;
                board.getRows()[action.getTarget().row].boardCells[action.getTarget().col].bead.addBead(start);
            } else {
                Bead target = board.getRows()[action.getTarget().row].boardCells[action.getTarget().col].bead;
                if (start.getHeight() < target.getHeight()) {
                    return true;
                }
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
            Bead start = board.getRows()[action.getStart().row].boardCells[action.getStart().col].bead;
            if (action.getType() == Action.ActionType.reinforce) {
                board.getRows()[action.getStart().row].boardCells[action.getStart().col].bead = null;
                board.getRows()[action.getTarget().row].boardCells[action.getTarget().col].bead.addBead(start);
            } else {
                Bead target = board.getRows()[action.getTarget().row].boardCells[action.getTarget().col].bead;
                if (start.getHeight() < target.getHeight()) {
                    return true;
                }
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

    public Player getWhite() {
        return white;
    }

    public Player getBlack() {
        return black;
    }
}
