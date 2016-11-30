package it.polimi.test.pieces;

/**
 * Created by lucio on 11/21/2016.
 */

public class Giant extends Piece {
    public Giant(String c, int x, int y) {
        super(5, 2, 's', 'w', 1, 4, 's', c, x, y,"Giant");
    }

    public boolean chooseActions(char a) {
        return (a == 'A' || a == 'M');
    }

    public boolean attack(Piece p){
        return p.attacked(super.getAttackStrength());
    }
}