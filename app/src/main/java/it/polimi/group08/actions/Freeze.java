package it.polimi.group08.actions;


import it.polimi.group08.functions.GlobalVariables;
import it.polimi.group08.pieces.Piece;


/**
 * Created by gengdongjie on 30/12/2016.
 */

public class Freeze {

    public boolean isFreezed(int movPlayerInt, Piece[][] piece, int fromX, int fromY, Piece emptyPiece) {
//        index  indict the action is invalid or valid
        boolean index = false;
        GlobalVariables gV = new GlobalVariables();
        Piece pieceMage = getPieceMage(movPlayerInt, piece, emptyPiece);
        if (pieceMage.getTypeInt() == 0) {
//            the typeInt is 0 means there is no piece mage in the board piece
           System.out.println("ERROR:<Your mage is dead!>");
        } else if (!pieceMage.spells.substring(0, 1).equals("F")) {
           System.out.println("ERROR:<Spell freeze has been casted!>");
        } else if (piece[fromX][fromY].getTypeInt() == movPlayerInt) {
           System.out.println("ERROR:<You cannot choose a friendly piece " + piece[fromX][fromY].getType() + " to freeze!>");
        } else if (piece[fromX][fromY].getPlayerY() == 2) {
           System.out.println("ERROR:<You cannot freeze enemy mage " + piece[fromX][fromY].getType() + " !>");
        } else if (gV.specialCellsSet().contains("" + fromX + fromY)) {
           System.out.println("ERROR:<You cannot choose a special cell " + (fromX + 1) + (fromY + 1) + " to freeze!>");
        } else {
            index = true;
//           set the freeze spell to 0
            pieceMage.spells = pieceMage.spells.replace('F', '0');
//            freeze the target piece
            piece[fromX][fromY].state = "f";
        }
        return index;
    }
// the following class is to get the class piece mage in the array class piece
    private Piece getPieceMage(int movPlayerInt, Piece[][] piece, Piece emptyPiece) {
        Piece pieceMage = emptyPiece;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (piece[i][j].getPlayerY() == 2 && piece[i][j].getTypeInt() == movPlayerInt) {
                    pieceMage = piece[i][j];
                }
            }
        }
        return pieceMage;
    }

}

