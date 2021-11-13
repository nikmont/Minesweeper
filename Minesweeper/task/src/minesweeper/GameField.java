package minesweeper;

import java.util.Random;

public class GameField {

    private static final int FIELD_SIZE = 9;
    static Cell[][] minefield = new Cell[FIELD_SIZE][FIELD_SIZE];
    static Cell[][] userfield = new Cell[FIELD_SIZE][FIELD_SIZE];
    static int numOfMines;
    static int markCount = 0;

    public GameField(int numOfMines) {
        GameField.numOfMines = numOfMines;

        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                minefield[i][j] = new Cell(i, j, '.');
                userfield[i][j] = new Cell(i, j, '.');
            }
        }
    }

    public void createMines(int startX, int startY) {
        Random rand = new Random();

        for (int i = 0; i < numOfMines; i++) {
            int mineXaxis = rand.nextInt(9);
            int mineYaxis = rand.nextInt(9);

            if (minefield[mineXaxis][mineYaxis].isMine() || (mineXaxis == startX & mineYaxis == startY)) { //if the cell has mine inside or this is start point
                i--;
            } else {
                minefield[mineXaxis][mineYaxis].setType('X');
            }

            if (countMinesAround(startX, startY) > 0) {
                i--;
                minefield[mineXaxis][mineYaxis].setType('.');
            }
        }
    }

    public void setCellNeighbors() {
        int mines;

        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (minefield[i][j].isFree()) {
                    mines = countMinesAround(i, j);
                    if (mines == 0) {
                        minefield[i][j].setType('/');
                    } else {
                        minefield[i][j].setType((char) (mines + '0'));
                    }
                }
            }
        }
    }

    public boolean checkCellForMine(int x, int y) {
        return minefield[x][y].getType() == 'X';
    }

    public void exploreMines() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (minefield[i][j].getType() == 'X') {
                    userfield[i][j].setType('X');
                }
            }
        }
    }

    public void makeTurn(int x, int y, String type) {
        if (type.equals("free")) {
            exploreCell(x, y);
        } else if (type.equals("mine")) {
            setMark(x, y);
        } else {
            System.out.println("Incorrect type");
        }
    }

    public void setMark(int x, int y) {

        if (userfield[x][y].getType() == '*') {
            userfield[x][y].setType('.');
            markCount--;
        } else {
            userfield[x][y].setType('*');
            markCount++;
        }
    }

    public void exploreCell(int x, int y) {

        if (userfield[x][y].isVisited()) {
            return;
        }

        userfield[x][y].setVisited();

        if (isEmpty(x, y)) {
            if (!isMinesAround(x, y)) {
                //recur neighbors explore
                for (int xoff = -1; xoff <= 1; xoff++) {
                    for (int yoff = -1; yoff <= 1; yoff++) {
                        int i = x + xoff;
                        int j = y + yoff;
                        if (i > -1 && i < FIELD_SIZE && j > -1 && j < FIELD_SIZE) { //bound check
                            userfield[x][y].setType(minefield[x][y].getType());
                            exploreCell(i, j);
                        }
                    }
                }
            } else {
                userfield[x][y].setType(minefield[x][y].getType());
            }
        }
    }

    private boolean isEmpty(int x, int y) {
        char cell = userfield[x][y].getType();
        return cell == '.' || cell == '*';
    }

    private boolean isMinesAround(int x, int y) {
        return countMinesAround(x, y) != 0;
    }

    public int countMinesAround(int x, int y) {
        int minesAround = 0;

        for (int xoff = -1; xoff <= 1; xoff++) {
            for (int yoff = -1; yoff <= 1; yoff++) {
                int i = x + xoff;
                int j = y + yoff;
                if (i > -1 && i < FIELD_SIZE && j > -1 && j < FIELD_SIZE) {
                    if (minefield[i][j].isMine()) {
                        minesAround++;
                    }
                }
            }
        }
        return minesAround;
    }

    public boolean isValidCell(int x, int y) {
        return (userfield[x][y].getType() == '.' || userfield[x][y].getType() == '*');
    }

    public boolean isOver() {
        return isAllCellsExplored() || isAllMinesMarked();
    }

    private boolean isAllCellsExplored() {
        int numOfUnexplored = 0;

        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (userfield[i][j].getType() == '.') {
                    numOfUnexplored++;
                    if (!minefield[i][j].isMine()) {
                        return false;
                    }
                }
            }
        }

        return numOfMines == numOfUnexplored;
    }

    private boolean isAllMinesMarked() {

        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {

                if (userfield[i][j].getType() == '*') {
                    if (!minefield[i][j].isMine()) {
                        return false;
                    }
                }
            }
        }

        return numOfMines == markCount;
    }

    public void printUserField() {
        System.out.print("\n │123456789│\n" +
                "—│—————————");
        for (int i = 0; i < FIELD_SIZE; i++) {
            System.out.print("│\n" + (i + 1) + "|");
            for (int j = 0; j < FIELD_SIZE; j++) {
                System.out.print(userfield[i][j]);
            }
        }
        System.out.println("|\n—│—————————│");
    }
}
