package minesweeper;

import java.util.Scanner;

public class GameController {
    static Scanner sc = new Scanner(System.in);
    private static GameField game;
    private static int turnCounter;

    public static void init() {
        turnCounter = 0;
        System.out.print("How many mines do you want on the field? ");
        int minesCount = Integer.parseInt(sc.nextLine());
        game = new GameField(minesCount);
        game.printUserField();
    }

    public static void start() {
        int x,y;
        String type;

        while (!game.isOver()) {
            System.out.print("Set/unset mines marks or claim a cell as free: ");
            String userInput = sc.nextLine();
            String[] input = userInput.split("\\s");

            y = Integer.parseInt(input[0]) - 1;
            x = Integer.parseInt(input[1]) - 1;
            type = input[2];

            //generate map
            turnCounter++;
            if (turnCounter == 1) {
                game.createMines(x, y);
                game.setCellNeighbors();
            }

            if (type.equals("free")) {
                if (game.checkCellForMine(x, y)) {
                    game.exploreMines();
                    game.printUserField();
                    System.out.println("You stepped on a mine and failed!");
                    return;
                }
            }

            if (game.isValidCell(x, y)) {
                game.makeTurn(x, y, type);
                game.printUserField();
            } else {
                System.out.println("This cell is not free!");
            }

        }
        System.out.println("Congratulations! You found all mines!");
    }
}
