package minesweeper;

public class Cell {

    private char type;
    private int x;
    private int y;
    private boolean isVisited;

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited() {
        isVisited = true;
    }

    public Cell(int x, int y, char type) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public void setType(char type) {
        this.type = type;
    }

    public char getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isMine() {
        return type == 'X';
    }

    public boolean isFree() {
        return  type != 'X';
    }

    @Override
    public String toString() {
        return String.valueOf(type);
    }
}
