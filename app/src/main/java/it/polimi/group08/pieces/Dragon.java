package it.polimi.group08.pieces;

/**
 * Created by lucio on 11/21/2016.
 * Dragon can attack, so the method attack in piece is overridden
 * chooseAction is reserved for view design
 */

public class Dragon extends Piece {
    public Dragon(String c, int x, int y) {
        super(6, 3, 's', 'f', 2, 3, 's', c, x, y,"Dragon");
    }

    public boolean chooseActions(char a) {
        return (a == 'A' || a == 'M');
    }

    public boolean attack(Piece p){
        return p.attacked(super.getAttackStrength());
    }
}