package models;

import java.util.HashMap;
import java.util.Map;

public class Board {

    public static class BoardRow {

        int number;
        final int size;
        public BoardCell[] boardCells;

        BoardRow(BoardRow boardRow) {
            this.size = boardRow.size;
            this.number = boardRow.number;
            boardCells = new BoardCell[size];
            for (int i = 0; i < size; i++) {
                boardCells[i] = new BoardCell(boardRow.boardCells[i]);
            }
        }

        BoardRow(int number, int size) {
            this.size = size;
            this.number = number;
            boardCells = new BoardCell[size];
            for (int i = 0; i < size; i++) {
                boardCells[i] = new BoardCell(number, i);
            }
        }
    }

    public static class BoardCell {

        int row;
        int col;
        public Bead bead;
        Map<NeighborType, BoardCell> neighbors;

        @Override
        public String toString() {
            return "BoardCell{" +
                    "row=" + row +
                    ", col=" + col +
                    ", bead=" + bead +
                    '}';
        }

        BoardCell(int row, int col) {
            this.row = row;
            this.col = col;
            bead = null;
            neighbors = new HashMap<>();
        }

        BoardCell(BoardCell boardCell) {
            this.row = boardCell.row;
            this.col = boardCell.col;
            if (boardCell.bead != null)
                bead = new Bead(boardCell.bead);
            neighbors = new HashMap<>();
        }

        void addNeighbor(BoardCell cell, NeighborType type) {
            this.neighbors.put(type, cell);
            cell.neighbors.put(type.reverse(), this);
        }

        void removeNeighbors() {
            for (NeighborType type : neighbors.keySet()) {
                BoardCell cell = neighbors.get(type);
                cell.neighbors.remove(type.reverse());
            }
            neighbors = new HashMap<>();
        }

    }

    public enum NeighborType {
        right, left, topRight, topLeft, downRight, downLeft;

        public NeighborType reverse() {
            return switch (this) {
                case right -> left;
                case left -> right;
                case topRight -> downLeft;
                case topLeft -> downRight;
                case downRight -> topLeft;
                case downLeft -> topRight;
            };
        }

    }

    private final BoardRow[] rows;
    private static final int totalRowsCount = 9;

    Board(Player black, Player white) {
        this.rows = new BoardRow[totalRowsCount];
        int startRowSize = 5;
        int i;
        for (i = 0; i < totalRowsCount - 4; i++) {
            rows[i] = new BoardRow(i, startRowSize + i);
        }
        for (; i < totalRowsCount; i++) {
            rows[i] = new BoardRow(i, startRowSize + totalRowsCount - i - 1);
        }

//        left and right neighbor
        for (i = 0; i < totalRowsCount; i++) {
            for (int j = 0; j < rows[i].size - 1; j++) {
                rows[i].boardCells[j].addNeighbor(rows[i].boardCells[j + 1], NeighborType.right);
            }
        }

//        down right and top left neighbor
        for (i = 0; i < totalRowsCount - 5; i++) {
            for (int j = 0; j < rows[i].size; j++) {
                rows[i].boardCells[j].addNeighbor(rows[i + 1].boardCells[j + 1], NeighborType.downRight);
            }
        }
        for (; i < totalRowsCount - 1; i++) {
            for (int j = 0; j < rows[i].size - 1; j++) {
                rows[i].boardCells[j].addNeighbor(rows[i + 1].boardCells[j], NeighborType.downRight);
            }
        }

//        down left and top right
        for (i = 0; i < totalRowsCount - 5; i++) {
            for (int j = 0; j < rows[i].size; j++) {
                rows[i].boardCells[j].addNeighbor(rows[i + 1].boardCells[j], NeighborType.downLeft);
            }
        }
        for (; i < totalRowsCount - 1; i++) {
            for (int j = 1; j < rows[i].size; j++) {
                rows[i].boardCells[j].addNeighbor(rows[i + 1].boardCells[j - 1], NeighborType.downLeft);
            }
        }

//        clear center of board
        rows[4].boardCells[4].removeNeighbors();


//        most outer beads
        for (i = 0; i < 4; i++) {
            rows[0].boardCells[i].bead = new Bead(BeadType.Totts, black);
        }
        for (i = 1; i < 5; i++) {
            rows[i].boardCells[0].bead = new Bead(BeadType.Totts, white);
        }
        for (; i < totalRowsCount; i++) {
            rows[i].boardCells[0].bead = new Bead(BeadType.Totts, black);
        }
        for (i = 1; i < rows[totalRowsCount - 1].size; i++) {
            rows[totalRowsCount - 1].boardCells[i].bead = new Bead(BeadType.Totts, white);
        }
        for (i = 0; i < 4; i++) {
            rows[i].boardCells[rows[i].size - 1].bead = new Bead(BeadType.Totts, white);
        }
        for (; i < totalRowsCount - 1; i++) {
            rows[i].boardCells[rows[i].size - 1].bead = new Bead(BeadType.Totts, black);
        }


//
        for (i = 1; i < rows[1].size - 1; i++) {
            rows[1].boardCells[i].bead = new Bead(BeadType.Tzarras, black);
        }
        for (i = 2; i < 5; i++) {
            rows[i].boardCells[1].bead = new Bead(BeadType.Tzarras, white);
        }
        for (; i < totalRowsCount - 1; i++) {
            rows[i].boardCells[1].bead = new Bead(BeadType.Tzarras, black);
        }
        for (i = 2; i < rows[totalRowsCount - 2].size - 1; i++) {
            rows[totalRowsCount - 2].boardCells[i].bead = new Bead(BeadType.Tzarras, white);
        }
        for (i = 1; i < 4; i++) {
            rows[i].boardCells[rows[i].size - 2].bead = new Bead(BeadType.Tzarras, white);
        }
        for (; i < totalRowsCount - 2; i++) {
            rows[i].boardCells[rows[i].size - 2].bead = new Bead(BeadType.Tzarras, black);
        }


        for (i = 2; i < rows[1].size - 2; i++) {
            rows[2].boardCells[i].bead = new Bead(BeadType.Tzaars, black);
        }
        for (i = 3; i < 5; i++) {
            rows[i].boardCells[2].bead = new Bead(BeadType.Tzaars, white);
        }
        for (; i < totalRowsCount - 2; i++) {
            rows[i].boardCells[2].bead = new Bead(BeadType.Tzaars, black);
        }
        for (i = 3; i < rows[totalRowsCount - 3].size - 2; i++) {
            rows[totalRowsCount - 3].boardCells[i].bead = new Bead(BeadType.Tzaars, white);
        }
        for (i = 2; i < 4; i++) {
            rows[i].boardCells[rows[i].size - 3].bead = new Bead(BeadType.Tzaars, white);
        }
        for (; i < totalRowsCount - 3; i++) {
            rows[i].boardCells[rows[i].size - 3].bead = new Bead(BeadType.Tzaars, black);
        }

        rows[3].boardCells[3].bead = new Bead(BeadType.Totts, black);
        rows[3].boardCells[4].bead = new Bead(BeadType.Totts, white);
        rows[4].boardCells[3].bead = new Bead(BeadType.Totts, white);
        rows[4].boardCells[5].bead = new Bead(BeadType.Totts, black);
        rows[5].boardCells[3].bead = new Bead(BeadType.Totts, black);
        rows[5].boardCells[4].bead = new Bead(BeadType.Totts, white);
    }

    Board(Board board) {
        this.rows = new BoardRow[totalRowsCount];
        int i;
        for (i = 0; i < totalRowsCount; i++) {
            rows[i] = new BoardRow(board.rows[i]);
        }

        //        left and right neighbor
        for (i = 0; i < totalRowsCount; i++) {
            for (int j = 0; j < rows[i].size - 1; j++) {
                rows[i].boardCells[j].addNeighbor(rows[i].boardCells[j + 1], NeighborType.right);
            }
        }

//        down right and top left neighbor
        for (i = 0; i < totalRowsCount - 5; i++) {
            for (int j = 0; j < rows[i].size; j++) {
                rows[i].boardCells[j].addNeighbor(rows[i + 1].boardCells[j + 1], NeighborType.downRight);
            }
        }
        for (; i < totalRowsCount - 1; i++) {
            for (int j = 0; j < rows[i].size - 1; j++) {
                rows[i].boardCells[j].addNeighbor(rows[i + 1].boardCells[j], NeighborType.downRight);
            }
        }

//        down left and top right
        for (i = 0; i < totalRowsCount - 5; i++) {
            for (int j = 0; j < rows[i].size; j++) {
                rows[i].boardCells[j].addNeighbor(rows[i + 1].boardCells[j], NeighborType.downLeft);
            }
        }
        for (; i < totalRowsCount - 1; i++) {
            for (int j = 1; j < rows[i].size; j++) {
                rows[i].boardCells[j].addNeighbor(rows[i + 1].boardCells[j - 1], NeighborType.downLeft);
            }
        }

//        clear center of board
        rows[4].boardCells[4].removeNeighbors();

    }

    public void print() {
        for (int i = 0; i < totalRowsCount; i++) {
            printPlayer(i);
            System.out.println(" ");
        }
    }

    public void printMore() {
        for (int i = 0; i < totalRowsCount; i++) {
            printPlayer(i);
            printType(i);
            System.out.println(" ");
        }
    }

    void printComplete() {
        for (int i = 0; i < totalRowsCount; i++) {
            printPlayer(i);
            printType(i);
            printHeight(i);
            System.out.println(" ");
        }
        System.out.println(" ");
    }

    private void printHeight(int i) {
        int diff = totalRowsCount - rows[i].size;
        for (int j = 0; j < diff; j++) {
            System.out.print(" ");
        }

        for (int j = 0; j < rows[i].size; j++) {
            Bead bead = rows[i].boardCells[j].bead;
            if (bead == null) {
                System.out.print("X ");
                continue;
            }
            System.out.print(bead.getHeight() + " ");
        }


        for (int j = 0; j < diff; j++) {
            System.out.print(" ");
        }
    }

    private void printType(int i) {

        int diff = totalRowsCount - rows[i].size;
        for (int j = 0; j < diff; j++) {
            System.out.print(" ");
        }

        for (int j = 0; j < rows[i].size; j++) {
            Bead bead = rows[i].boardCells[j].bead;
            if (bead == null) {
                System.out.print("X ");
                continue;
            }
            switch (bead.getType()) {
                case Tzaars -> System.out.print("A ");
                case Tzarras -> System.out.print("B ");
                case Totts -> System.out.print("C ");
            }

        }


        for (int j = 0; j < diff; j++) {
            System.out.print(" ");
        }
    }

    private void printPlayer(int i) {
        int diff = totalRowsCount - rows[i].size;
        for (int j = 0; j < diff; j++) {
            System.out.print(" ");
        }
        for (int j = 0; j < rows[i].size; j++) {
            Bead bead = rows[i].boardCells[j].bead;
            if (bead == null) {
                System.out.print("X ");
                continue;
            }
            if (bead.getPlayer().getType() == PlayerType.black) {
                System.out.print("B ");
            } else {
                System.out.print("W ");
            }

        }

        for (int j = 0; j < diff; j++) {
            System.out.print(" ");
        }
    }

    public BoardRow[] getRows() {
        return rows;
    }

    static boolean isTherePath(Board.BoardCell start, Board.BoardCell target) {
        for (Board.NeighborType type : start.neighbors.keySet()) {
            Board.BoardCell cell = start.neighbors.get(type);
            while (cell != null && cell.bead == null) {
                cell = cell.neighbors.get(type);
            }
            if (cell != null) {
                if (target.row == cell.row && target.col == cell.col) {
                    return true;
                }
            }
        }
        return false;
    }

}
