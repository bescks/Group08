package it.polimi.test.pieces;

/**
 * Created by lucio on 11/21/2016.
 */

public class Squire extends Piece {
    public Squire(String c, int x, int y) {
        super(3, 1, 's', 'w', 0, 1, 'n', c, x, y,"Squire");
    }

    public boolean chooseActions(char a) {
        return (a == 'M');
    }
}
