package it.polimi.group08.pieces;

/**
 * Created by lucio on 11/21/2016.
 * Giant can attack, so the method attack in piece is overridden
 * chooseAction is reserved for view design
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