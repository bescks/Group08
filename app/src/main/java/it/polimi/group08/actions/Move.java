package it.polimi.group08.actions;


import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import it.polimi.group08.pieces.Piece;

/**
 * Created by gengdongjie on 28/12/2016.
 */

public class Move {

    public boolean isMoved(int movPlayerInt, Piece[][] piece, int fromX, int fromY, int toX, int toY, Piece emptyPiece) {
        boolean index = false;

        if (piece[fromX][fromY].getTypeInt() == 0) {
            System.out.println("ERROR:<You choose an empty piece " + piece[fromX][fromY].getType() + " !>");
        } else if (piece[fromX][fromY].getTypeInt() != movPlayerInt) {
            System.out.println("ERROR:<You choose an enemy piece " + piece[fromX][fromY].getType() + " !>");
        } else if (piece[fromX][fromY].state.equals("f")) {
            System.out.println("ERROR:<You choose an frozen piece " + piece[fromX][fromY].getType() + " !>");
        } else {
            if (piece[toX][toY].getTypeInt() == movPlayerInt) {
                System.out.println("ERROR:<You cannot move on friendly piece " + piece[toX][toY].getType() + " !>");
            } else if (isInRange(piece, fromX, fromY, toX, toY)) {
                index = true;
                if (piece[toX][toY].getTypeInt() == 0) {
                    piece[toX][toY] = piece[fromX][fromY];
                    piece[fromX][fromY] = emptyPiece;
                } else {
                    // in combat
                    switch (piece[toX][toY].state) {
                        case "n": {
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
                            break;
                        }
                        case "f": {
                            piece[toX][toY] = piece[fromX][fromY];
                            piece[fromX][fromY] = emptyPiece;
                            break;
                        }
                    }
                }
            }
        }
        return index;
    }

    public Set getAvailCells(Piece[][] piece, int fromX, int fromY) {
/**
 *     According to all the pieces' position, initializing boardState in a 6*6 dimension array. White pieces is set to 1,black pieces is set to -1 and empty cell is set to 0.Multiply the values of two pieces in boardState and compare  the result.
 *     The result is equal to 1 means the two pieces from same player.
 *     The result is equal to -1 means the two pieces from different player.
 *     The result is equal to 0 means the one of the pieces is not be occupied..
 *     moveCellsSet record cells that piece can reach, while the "move block" is not be considered.
 *     moveCellsMapEnemy record distance and corresponded enemy that piece can combat with.
 *     moveCellsMap record cells that piece can reach, while the "move block" is be considered.
 *     moveCellsMapCopy, which is used for recursive searching, is just a copy of Map moveCellsMap
 */


        Set<String> moveCellsSet = new TreeSet<>();
        Map<String, Integer> moveCellsMapEnemy = new TreeMap<>();
        Map<String, Integer> moveCellsMap = new TreeMap<>();
        Map<String, Integer> moveCellsMapCopy = new TreeMap<>();

        Piece movPiece = piece[fromX][fromY];
        String position = "" + (fromX) + (fromY);

        switch (movPiece.getMoveDirections()) {
            case "*": {
                //  For the piece whose move directions is any, calculate the available  move cells without regard to move block problem
                switch (movPiece.getMoveType()) {
                    case "walk": {
                        for (int i = 0; i < 6; i++) {
                            for (int j = 0; j < 6; j++) {
                                if (
                                        (Math.pow(fromX - i, 2) + Math.pow(fromY - j, 2)) <= (2 * Math.pow(movPiece.getMoveRange(), 2))
                                                && (piece[i][j].getTypeInt() * piece[fromX][fromY].getTypeInt() != 1)
                                        ) {
                                    String k = "" + i + j;
                                    moveCellsSet.add(k);
                                }
                            }
                        }
/**
 *    The Following code is designed to remove cells in the situation of move block
 *    First Step, excluding cells occupied by the same player and considering all the left cells that the piece can reach within moveRange=1, set integer i = 1).
 *    Second Step, for all of the cells got in the first step,the cells occupied by enemy pieces are add into  moveCellsMapEnemy and others together with integer i are add into moveCellsMap.
 *    Third Step, for all of cells in moveCellsMap, set i=i+1 and repeat the first and second step until i is equals to the moveRange of this piece.
 *    Finally, all the cells in moveCellsMap and mobeCellsMapEnemy is the piece can be reached.
 */
                        String rCell;
                        int rCellX;
                        int rCellY;
                        moveCellsMap.put(position, 0);
                        String aroundCell;
                        for (int i = 1; i <= movPiece.getMoveRange(); i++) {
                            moveCellsMapCopy.clear();
                            moveCellsMapCopy.putAll(moveCellsMap);
                            for (Object o : moveCellsMapCopy.keySet()) {
                                rCell = "" + o;
                                rCellX = Integer.valueOf(rCell.substring(0, 1));
                                rCellY = Integer.valueOf(rCell.substring(1, 2));
                                for (int i1 = -1; i1 <= 1; i1++) {
                                    for (int j1 = -1; j1 <= 1; j1++) {
                                        aroundCell = "" + (rCellX + i1) + (rCellY + j1);
                                        if (moveCellsSet.contains(aroundCell)) {
                                            if ((rCellX + i1) >= 0 && (rCellX + i1) < 6 && (rCellY + j1) >= 0 && (rCellY + j1) < 6) {
                                                if (moveCellsMap.get(aroundCell) == null) {
                                                    if (piece[fromX][fromY].getTypeInt() * piece[rCellX + i1][rCellY + j1].getTypeInt() == -1) {
                                                        if (!moveCellsMapEnemy.containsKey(aroundCell)) {
                                                            moveCellsMapEnemy.put(aroundCell, i);
                                                        }
                                                    } else {
                                                        moveCellsMap.put(aroundCell, i);
                                                    }
                                                } else {
                                                    if (piece[fromX][fromY].getTypeInt() * piece[rCellX + i1][rCellY + j1].getTypeInt() == -1) {
                                                        if (!moveCellsMapEnemy.containsKey(aroundCell) || (Integer.valueOf(moveCellsMap.get(aroundCell).toString()) > i)) {
                                                            moveCellsMapEnemy.put(aroundCell, i);
                                                        }
                                                    } else if ((Integer.valueOf(moveCellsMap.get(aroundCell).toString()) > i)) {
                                                        moveCellsMap.put(aroundCell, i);
                                                    }
//                                        else {System.out.println("repetitive cells");}
                                                }
                                            }
                                        }
//                            else {System.out.println("Drop");}
                                    }
                                }
                            }
                            moveCellsMap.remove(position);
                        }
                        break;
                    }
                    case "flight": {
                        System.out.println("ERROR:<Piece " + movPiece.getType() + " cannot flight in any directions!>");
                        break;
                    }
                    default: {
                        System.out.println("ERROR:<Piece " + movPiece.getType() + " has an invalid moveType " + movPiece.getMoveType() + " !>");
                    }
                }
                break;
            }
            case "+": {
                switch (movPiece.getMoveType()) {
                    case "walk": {
                        //  remove cells in the situation of move block
                        String k;
                        for (int i = 0; i < 6; i++) {
                            for (int j = 0; j < 6; j++) {
                                if ((Math.pow(fromX - i, 2) + Math.pow(fromY - j, 2)) <= (Math.pow(movPiece.getMoveRange(), 2)) && (piece[i][j].getTypeInt() * piece[fromX][fromY].getTypeInt() != 1)) {
                                    k = "" + i + j;
                                    moveCellsSet.add(k);
                                }
                            }
                        }
                        String rCell;
                        int rCellX;
                        int rCellY;
                        moveCellsMap.put(position, 0);
                        String aroundCell;
                        int[][] dir = new int[4][2];
                        dir[0][0] = -1;
                        dir[0][1] = 0;
                        dir[1][0] = 0;
                        dir[1][1] = 1;
                        dir[2][0] = 1;
                        dir[2][1] = 0;
                        dir[3][0] = 0;
                        dir[3][1] = -1;
                        for (int i = 1; i <= movPiece.getMoveRange(); i++) {
                            moveCellsMapCopy.clear();
                            moveCellsMapCopy.putAll(moveCellsMap);
                            for (Object o : moveCellsMapCopy.keySet()) {
                                rCell = "" + o;
                                rCellX = Integer.valueOf(rCell.substring(0, 1));
                                rCellY = Integer.valueOf(rCell.substring(1, 2));
                                for (int i1 = 0; i1 <= 3; i1++) {
                                    for (int j1 = 0; j1 <= 1; j1++) {
                                        aroundCell = "" + (rCellX + dir[i1][0]) + (rCellY + dir[i1][1]);
                                        if (moveCellsSet.contains(aroundCell)) {
                                            if ((fromX + dir[i1][0]) >= 0 && (fromX + dir[i1][0]) < 6 && (fromY + dir[i1][1]) >= 0 && (fromY + dir[i1][1]) < 6) {

                                                if (moveCellsMap.get(aroundCell) == null) {
                                                    if (piece[fromX][fromY].getTypeInt() * piece[rCellX + dir[i1][0]][rCellY + dir[i1][1]].getTypeInt() == -1) {
                                                        moveCellsMapEnemy.put(aroundCell, i);
                                                    } else {
                                                        moveCellsMap.put(aroundCell, i);
                                                    }
                                                } else {
                                                    if (piece[fromX][fromY].getTypeInt() * piece[rCellX + dir[i1][0]][rCellY + dir[i1][1]].getTypeInt() == -1) {
                                                        if (!moveCellsMapEnemy.containsKey(aroundCell) || (Integer.valueOf(moveCellsMap.get(aroundCell).toString()) > i)) {
                                                            moveCellsMapEnemy.put(aroundCell, i);
                                                        }
                                                    } else if ((Integer.valueOf(moveCellsMap.get(aroundCell).toString()) > i)) {
                                                        moveCellsMap.put(aroundCell, i);
                                                    }
//                                            else {System.out.println("repetitive cells");}
                                                }
                                            }
                                        }
//                            else {System.out.println("Drop");}
                                    }
                                }
                            }
                            moveCellsMap.remove(position);
                        }
                        break;
                    }
                    case "flight": {
                        // For the fly type, move block is not in consideration
                        int distance;
                        String k;
                        for (int i = 0; i < 6; i++) {
                            for (int j = 0; j < 6; j++) {
                                if ((Math.pow(fromX - i, 2) + Math.pow(fromY - j, 2)) <= (Math.pow(movPiece.getMoveRange() - 1, 2) + 1) || (Math.pow(fromX - i, 2) + Math.pow(fromY - j, 2)) == Math.pow(movPiece.getMoveRange(), 2)) {
                                    k = "" + i + j;
                                    distance = Math.abs(fromX - i) + Math.abs(fromY - j);
                                    if (piece[i][j].getTypeInt() * piece[fromX][fromY].getTypeInt() == 0) {
                                        moveCellsMap.put(k, distance);
                                    } else if (piece[i][j].getTypeInt() * piece[fromX][fromY].getTypeInt() == -1) {
                                        moveCellsMapEnemy.put(k, distance);
                                    }
                                }
                            }
                        }
                        break;
                    }
                    default:
                        System.out.println("ERROR:<Piece " + movPiece.getType() + " has an invalid moveType " + movPiece.getMoveType() + " !>");
                }
                break;
            }
            default:
                System.out.println("ERROR:<Piece " + movPiece.getType() + " has an invalid moveDirections " + movPiece.getMoveDirections() + " !>");
        }
        /*System.out.println("moveCellsMap=" + moveCellsMap.toString());
        System.out.println("moveCellsSet=" + moveCellsSet.toString());
        System.out.println("moveCellsMapEnemy=" + moveCellsMapEnemy.toString());*/

        moveCellsMap.putAll(moveCellsMapEnemy);
        return moveCellsMap.keySet();
    }

    private boolean isInRange(Piece[][] piece, int fromX, int fromY, int toX, int toY) {
        boolean index = false;
        if (piece[fromX][fromY].getMoveRange() == 0) {
            System.out.println("ERROR:<Piece " + piece[fromX][fromY].getType() + " do not have move action!>");
        } else {
            Set getMoveCells = getAvailCells(piece, fromX, fromY);
            if (getMoveCells.contains("" + toX + toY)) {
                index = true;
            } else {
                System.out.println("ERROR:<The Target cell " + piece[toX][toY].getType() + " is out of piece " + piece[fromX][fromY].getType() + " move range!>");
            }
        }
        return index;
    }


}
