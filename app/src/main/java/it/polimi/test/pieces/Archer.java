package it.polimi.test.pieces;

/**
 * Created by lucio on 11/21/2016.
 */

public class Archer extends Piece {
    public Archer(String c, int x, int y) {
        super(5, 2, 'a', 'w', 3, 2, 's', c, x, y,"Archer");
    }

    public boolean chooseActions(char a) {
        return (a == 'A' || a == 'M');
    }

    public boolean attack(Piece p){
        return p.attacked(super.getAttackStrength());
    }
}
