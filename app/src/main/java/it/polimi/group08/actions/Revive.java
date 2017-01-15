package it.polimi.group08.actions;

import it.polimi.group08.pieces.Piece;

/**
 * Created by gengdongjie on 30/12/2016.
 */

public class Revive {

    public boolean isRevived(int movPlayerInt, Piece[][] playerPiece, Piece[][] piece, int fromX, int fromY, Piece emptyPiece) {
//        index  indict the action is invalid or valid
        boolean index = false;
        Piece pieceMage = getPieceMage(movPlayerInt, piece, emptyPiece);
        Piece revivedPiece = emptyPiece;
//        search the playerpiece according to the initial position and get revived piece
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                if (playerPiece[i][j].getInitPositionX() == fromX && playerPiece[i][j].getInitPositionY() == fromY)
                    revivedPiece = playerPiece[i][j];
            }
        }
//            the following error is different situations that the action cannot be executed
        if (pieceMage.getTypeInt() == 0) {
//            the typeInt is 0 means there is no piece mage in the board piece
            System.out.println("ERROR:<Your mage is dead!>");
        } else if (!pieceMage.spells.substring(2, 3).equals("R")) {
            System.out.println("ERROR:<The revive spell has been casted!>");
        } else if (!revivedPiece.state.equals("d")) {
            System.out.println("ERROR:<The cannot choose a alive piece " + revivedPiece.getType() + " to revive!>");
        } else if (revivedPiece.getTypeInt() == 0) {
            System.out.println("ERROR:<Your cannot choose an empty " + revivedPiece.getType() + " piece to revive!>");
        } else if (revivedPiece.getTypeInt() != movPlayerInt) {
            System.out.println("ERROR:<Your cannot choose an enemy piece to revive!>");
        } else if (!revivedPiece.isTwin()) {
            if (piece[fromX][fromY].getTypeInt() == movPlayerInt) {
                System.out.println("ERROR:<The revive cell is occupied by friendly piece " + piece[fromX][fromY].getType() + " !>");
            } else if (piece[fromX][fromY].getPlayerY() == 2) {
                System.out.println("ERROR:<The revive cell is occupied by enemy mage " + piece[fromX][fromY].getType() + " !>");
            } else index = true;
        } else if (revivedPiece.isTwin()) {
//            the initial cells of the revivedPiece and it's twin piece are all occupied by friendly piece
            if ((piece[fromX][fromY].getTypeInt() == movPlayerInt || piece[fromX][fromY].getPlayerY() == 2)
                    && (piece[revivedPiece.getTwinX()][revivedPiece.getTwinY()].getTypeInt() == movPlayerInt
                    || piece[revivedPiece.getTwinX()][revivedPiece.getTwinY()].getPlayerY() == 2)) {
                System.out.println("ERROR:<The revive cell and it's twin cell are all occupied by friendly piece or enemy mage !>");
            } else index = true;
        }
        if (index) {
            pieceMage.spells = pieceMage.spells.replace('R', '0');
//            if the initial cell of revived piece cannot be revived to, the revived piece will be equal to it's twin piece
            if (revivedPiece.isTwin() && (piece[fromX][fromY].getTypeInt() == movPlayerInt
                    || piece[fromX][fromY].getPlayerY() == 2)) {
                revivedPiece = playerPiece[revivedPiece.getPlayerX()][revivedPiece.getTwinPlayerY()];
            }
            fromX = revivedPiece.getInitPositionX();
            fromY = revivedPiece.getInitPositionY();
            revivedPiece.state = "n";
            revivedPiece.setInitVitality();
//            the following situation is that the revived piece are in combat directly after revive
//            according to the different state of two pieces, the combat situation is different
            if (piece[fromX][fromY].getTypeInt() == 0) {
                piece[fromX][fromY] = revivedPiece;
            } else if (piece[fromX][fromY].state.equals("f")) {
                piece[fromX][fromY].state = "d";
                piece[fromX][fromY] = revivedPiece;
            } else {
                while (piece[fromX][fromY].vitality > 0 && revivedPiece.vitality > 0) {
                    piece[fromX][fromY].vitality -= revivedPiece.getAttackStrength();
                    revivedPiece.vitality -= piece[fromX][fromY].getAttackStrength();
                }
                if (revivedPiece.vitality <= 0) {
                    revivedPiece.state = "d";
                    if (piece[fromX][fromY].vitality <= 0) {
                        piece[fromX][fromY].state = "d";
                        piece[fromX][fromY] = emptyPiece;
                    }
                } else {
                    piece[fromX][fromY].state = "d";
                    piece[fromX][fromY] = revivedPiece;

                }
            }
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
