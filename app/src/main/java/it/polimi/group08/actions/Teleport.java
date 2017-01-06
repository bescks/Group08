package it.polimi.group08.actions;


import it.polimi.group08.functions.GlobalVariables;
import it.polimi.group08.pieces.Piece;

/**
 * Created by gengdongjie on 30/12/2016.
 */

public class Teleport {

    public boolean isTeleported(int movPlayerInt, Piece[][] piece, int fromX, int fromY, int toX, int toY, Piece emptyPiece) {
        boolean index = false;
        GlobalVariables gV = new GlobalVariables();
        Piece pieceMage = getPieceMage(movPlayerInt, piece, emptyPiece);

        if (pieceMage.getTypeInt() == 0) {
           System.out.println("ERROR:<Your mage is dead!>");
        } else if (!pieceMage.spells.substring(3, 4).equals("T")) {
           System.out.println("ERROR:<Spell heal has been casted!>");
        } else if (piece[fromX][fromY].getTypeInt() != movPlayerInt) {
           System.out.println("ERROR:<You cannot choose an enemy piece " + piece[fromX][fromY].getType() + " to teleport!>");
        } else if (piece[toX][toY].getTypeInt() == movPlayerInt) {
           System.out.println("ERROR:<You cannot teleport your piece " + piece[fromX][fromY].getType() + " to a friendly piece " + piece[toX][toY].getType() + " !>");
        } else if (piece[fromX][fromY].getPlayerY() == 2) {
           System.out.println("ERROR:<You cannot teleport your own mage " + piece[fromX][fromY].getType() + " !>");
        } else if (piece[toX][toY].getPlayerY() == 2) {
           System.out.println("ERROR:<You cannot teleport your piece to enemy mage " + piece[toX][toY].getType() + " !>");
        } else if (gV.specialCellsSet().contains("" + fromX + fromY)) {
           System.out.println("ERROR:<You cannot choose a special cell " + (fromX + 1) + (fromY + 1) + " to teleport!>");
        } else if (gV.specialCellsSet().contains("" + toX + toY)) {
           System.out.println("ERROR:<You cannot teleport your piece to an special cell " + (toX + 1) + (toY + 1) + " !>");
        } else {
            index = true;
            pieceMage.spells = pieceMage.spells.replace('T', '0');
            if (piece[toX][toY].getTypeInt() == 0) {
                piece[toX][toY] = piece[fromX][fromY];
                piece[fromX][fromY] = emptyPiece;
            } else {
                switch (piece[fromX][fromY].state) {
                    case "f": {
                        piece[fromX][fromY].state = "d";
                        piece[fromX][fromY] = emptyPiece;
                        if (piece[toX][toY].state.equals("f")) {
                            piece[toX][toY].state = "d";
                            piece[toX][toY] = emptyPiece;
                        }
                        break;
                    }
                    case "n": {
                        if (piece[toX][toY].state.equals("f")) {
                            piece[toX][toY].state = "d";
                            piece[toX][toY] = piece[fromX][fromY];
                            piece[fromX][fromY] = emptyPiece;
                        } else {
                            while (piece[fromX][fromY].vitality > 0 && piece[toX][toY].vitality > 0) {
                                piece[fromX][fromY].vitality -= piece[toX][toY].getAttackStrength();
                                piece[toX][toY].vitality -= piece[fromX][fromY].getAttackStrength();
                            }
                            if (piece[fromX][fromY].vitality <= 0) {
                                piece[fromX][fromY].state = "d";
                                piece[fromX][fromY] = emptyPiece;
                            }
                            if (piece[toX][toY].vitality <= 0) {
                                piece[toX][toY].state = "d";
                                piece[toX][toY] = piece[fromX][fromY];
                                piece[fromX][fromY] = emptyPiece;
                            }
                        }
                        break;
                    }
                }

            }
        }
        return index;
    }

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

