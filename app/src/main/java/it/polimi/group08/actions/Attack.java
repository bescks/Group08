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
        boolean index = false;
        if (piece[fromX][fromY].getTypeInt() == 0) {
            System.out.println("ERROR:<You choose an empty piece in " + fromX + fromY + " !>");
        } else if (piece[fromX][fromY].getTypeInt() != movPlayerInt) {
            System.out.println("ERROR:<You choose an enemy piece " + piece[fromX][fromY].getType() + " in " + fromX + fromY + " !>");
        } else if (piece[fromX][fromY].state.equals("f")) {
            System.out.println("ERROR:<You choose an frozen piece " + piece[fromX][fromY].getType() + " !>");
        } else {
            if (piece[toX][toY].getTypeInt() == 0) {
                System.out.println("ERROR:<Piece " + piece[fromX][fromY].getType() + " cannot attack an empty piece " + piece[toX][toY].getType() + " !>");
            } else if (piece[toX][toY].getTypeInt() == movPlayerInt) {
                System.out.println("ERROR:<Piece " + piece[fromX][fromY].getType() + " cannot attack friendly piece " + piece[toX][toY].getType() + " !>");
            } else {
                index = true;
                if (isInRange(piece, fromX, fromY, toX, toY)) {
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
If the nearest piece is an enemy piece, this piece together with the distance is be added into attackCellsMap and this must be the only cell can be attacked in this direction.
If the nearest piece is a friend piece, there must be no cell can be attacked in this direction.*/


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
            default:
                System.out.println("ERROR:<Piece " + piece[fromX][fromY].getType() + " has an invalid attackDirections " + piece[fromX][fromY].getAttackDirections() + " !>");
        }
        //System.out.println("AttackCellsMap=" + attackCellsMap.toString());
        return attackCellsMap.keySet();
    }


    private boolean isInRange(Piece[][] piece, int fromX, int fromY, int toX, int toY) {
        boolean index = false;
        if (piece[fromX][fromY].getAttackRange() == 0) {
            System.out.println("ERROR:<Piece " + piece[fromX][fromY].getType() + " do not have attack action!>");
        } else {
            Set getAttackCells = getAvailCells(piece, fromX, fromY);
            if (getAttackCells.contains("" + toX + toY)) {
                index = true;
            } else {
                System.out.println("ERROR:<The Target piece " + piece[toX][toY].getType() + " is out of piece " + piece[fromX][fromY].getType() + " attack range!>");
            }
        }
        return index;
    }

}





