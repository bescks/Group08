package it.polimi.group08.actions;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import it.polimi.group08.pieces.Piece;


/**
 * Created by gengdongjie on 28/12/2016.
 */


public class Attack {
    public boolean isAttacked(int movPlayerInt, Piece[][] piece, int fromX, int fromY, int toX, int toY, Piece emptyPiece) {
//        index  indict the action is invalid or valid
        boolean index = false;
//        invalid action: choose an empty piece to attack
        if (piece[fromX][fromY].getTypeInt() == 0) {
            System.out.println("ERROR:<You choose an empty piece in " + fromX + fromY + " !>");
//            invalid action: choose an enemy piece to attack
        } else if (piece[fromX][fromY].getTypeInt() != movPlayerInt) {
            System.out.println("ERROR:<You choose an enemy piece " + piece[fromX][fromY].getType() + " in " + fromX + fromY + " !>");
//            invalid action: choose a frozen piece
        } else if (piece[fromX][fromY].state.equals("f")) {
            System.out.println("ERROR:<You choose an frozen piece " + piece[fromX][fromY].getType() + " !>");
        } else {
//            invalid action: attack an empty piece
            if (piece[toX][toY].getTypeInt() == 0) {
                System.out.println("ERROR:<Piece " + piece[fromX][fromY].getType() + " cannot attack an empty piece " + piece[toX][toY].getType() + " !>");
//            invalid action: attack an friendly piece
            } else if (piece[toX][toY].getTypeInt() == movPlayerInt) {
                System.out.println("ERROR:<Piece " + piece[fromX][fromY].getType() + " cannot attack friendly piece " + piece[toX][toY].getType() + " !>");
            } else {
//              return the result whether the target is in attack range
                if (isInRange(piece, fromX, fromY, toX, toY)) {
                    index = true;
                    piece[toX][toY].vitality -= piece[fromX][fromY].getAttackStrength();
                    if (piece[toX][toY].vitality <= 0) {
                        piece[toX][toY].vitality = 0;
                        piece[toX][toY].state = "d";
                        piece[toX][toY] = emptyPiece;
                    }
                }
            }
        }
        return index;
    }


    public Set getAvailCells(Piece[][] piece, int fromX, int fromY) {

/*The piece, no mather what is the type of its attack direction, have 4 kind of directions to attack.So the maximum number of enemy can be attacked is equal to 4.
The algorithm compute from one of the four directions.In the specified direction.
If the nearest non-empty piece is an enemy piece, this piece together with the distance is be added into attackCellsMap and this must be the only cell can be attacked in this direction.
If the nearest non-empty  piece is a friend piece, there must be no cell can be attacked in this direction.*/


        Map<String, Integer> attackCellsMap = new TreeMap<>();
//          '+' means attack Direction is horizontal plus vertical
        switch (piece[fromX][fromY].getAttackDirections()) {
            case "+": {
                int[][] dir = new int[4][2];
//            upward attack
                dir[0][0] = -1;
                dir[0][1] = 0;
//            right attack
                dir[1][0] = 0;
                dir[1][1] = 1;
//            downward attack
                dir[2][0] = 1;
                dir[2][1] = 0;
//            left attack
                dir[3][0] = 0;
                dir[3][1] = -1;
                String aCell;
                for (int i = 0; i <= 3; i++) {
                    for (int j = 1; j <= piece[fromX][fromY].getAttackRange(); j++) {
                        if (((fromX + dir[i][0] * j) < 0) || ((fromX + dir[i][0] * j) > 5) || ((fromY + dir[i][1] * j) < 0) || ((fromY + dir[i][1] * j) > 5)) {
                            break;
                        } else if (piece[fromX + dir[i][0] * j][fromY + dir[i][1] * j].getTypeInt() * piece[fromX][fromY].getTypeInt() == -1) {
                            aCell = "" + (fromX + dir[i][0] * j) + (fromY + dir[i][1] * j);
                            attackCellsMap.put(aCell, j);
                            break;
                        } else if (piece[fromX + dir[i][0] * j][fromY + dir[i][1] * j].getTypeInt() * piece[fromX][fromY].getTypeInt() == 1) {
                            break;
                        }
                    }
                }
                break;
            }
//          'x' means attack Direction is diagnal
            case "x": {
                int[][] dir = new int[4][2];
//            upward right attack
                dir[0][0] = -1;
                dir[0][1] = 1;
//            downward right attack
                dir[1][0] = 1;
                dir[1][1] = 1;
//            downward left attack
                dir[2][0] = 1;
                dir[2][1] = -1;
//            upward right attack
                dir[3][0] = -1;
                dir[3][1] = -1;
                String aCell;
                for (int i = 0; i <= 3; i++) {
                    for (int j = 1; j <= piece[fromX][fromY].getAttackRange(); j++) {
                        if (((fromX + dir[i][0] * j) < 0) || ((fromX + dir[i][0] * j) > 5) || ((fromY + dir[i][1] * j) < 0) || ((fromY + dir[i][1] * j) > 5)) {
                            break;
                        } else if (piece[fromX + dir[i][0] * j][fromY + dir[i][1] * j].getTypeInt() * piece[fromX][fromY].getTypeInt() == -1) {
                            aCell = "" + (fromX + dir[i][0] * j) + (fromY + dir[i][1] * j);
                            attackCellsMap.put(aCell, j);
                            break;
                        } else if (piece[fromX + dir[i][0] * j][fromY + dir[i][1] * j].getTypeInt() * piece[fromX][fromY].getTypeInt() == 1) {
                            break;
                        }
                    }
                }
                break;
            }
            case "": {
                break;
            }
            default:
                System.out.println("ERROR:<Piece " + piece[fromX][fromY].getType() + " has an invalid attackDirections " + piece[fromX][fromY].getAttackDirections() + " !>");
        }
        return attackCellsMap.keySet();
    }


    private boolean isInRange(Piece[][] piece, int fromX, int fromY, int toX, int toY) {
//        index inddict wether the target piece in attack range
        boolean index = false;
        if (piece[fromX][fromY].getAttackRange() == 0) {
//            mage and squire cannot attack, so the attach range is 0
            System.out.println("ERROR:<Piece " + piece[fromX][fromY].getType() + " do not have attack action!>");
        } else {
//            get all the available pieces that can be attack
            Set getAttackCells = getAvailCells(piece, fromX, fromY);

//            if the target piece in the available set, this piece can be attacked
            if (getAttackCells.contains("" + toX + toY)) {
                index = true;
            } else {
                System.out.println("ERROR:<The Target piece " + piece[toX][toY].getType() + " is out of piece " + piece[fromX][fromY].getType() + " attack range!>");
            }
        }
        return index;
    }

}





