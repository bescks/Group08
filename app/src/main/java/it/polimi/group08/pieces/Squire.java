package it.polimi.group08.pieces;

/**
 * Created by lucio on 11/21/2016.
 * chooseActions is reserved for view design
 */

public class Squire extends Piece {
    public Squire(String c, int x, int y) {
        super(3, 1, 's', 'w', 0, 1, 'n', c, x, y,"Squire");
    }

    public boolean chooseActions(char a) {
        return (a == 'M');
    }
}
