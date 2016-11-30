package it.polimi.test.pieces;

/**
 * Created by lucio on 11/21/2016.
 */

public class Knight extends Piece {
    public Knight(String c, int x, int y) {
        super(4, 1, 'a', 'w', 1, 2, 'd', c, x, y,"Knight");
    }

    public boolean chooseActions(char a) {
        return (a == 'A' || a == 'M');
    }

    public boolean attack(Piece p){
        return p.attacked(super.getAttackStrength());
    }
}
