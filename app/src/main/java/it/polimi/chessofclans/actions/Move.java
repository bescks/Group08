package it.polimi.chessofclans.actions;

/**
 * Created by gengdongjie on 18/11/2016.
 */

public class Move {
    private int layout[][] = new int[6][6];
    private int fromX, fromY, toX, toY;
    private int moveRange;
    private String showCells;

    public Move(char array[][], String p1, String p2) {
        for (int i = 0; i <= 5; i++) {
            for (int j = 0; j <= 5; j++) {
                if ((array[i][j] >= 65) && (array[i][j] <= 90)) {
                    layout[i][j] = 1;
                } else if ((array[i][j] >= 97) && (array[i][j] <= 122)) {
                    layout[i][j] = -1;
                } else {
                    layout[i][j] = 0;
                }
            }
        }
        fromX = Integer.valueOf(p1.substring(0, 1));
        fromY = Integer.valueOf(p1.substring(1, 2));
        toX = Integer.valueOf(p2.substring(0, 1));
        toY = Integer.valueOf(p2.substring(1, 2));

    }

    public String showCells() {
        return showCells;
    }
}
