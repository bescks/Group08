package it.polimi.chessofclans.pieces;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by gengdongjie on 17/11/2016.
 */

public class Piece {


    private int initialVitality;
    private int vitality;
    private int moveRange;
    private char moveDirections;
    private char moveType;
    private int attackRange;
    private int attackStrength;
    private char attackDirections;

    private char state;
    private char color;
    private String position;
    private String initialPosition;


    public Piece(int iV, int mR, char mD, char mT, int aR, int aS, char aD, char c, String iP) {
        initialVitality = iV;
        vitality = iV;
        moveRange = mR;
        moveDirections = mD;
        moveType = mT;
        attackRange = aR;
        attackStrength = aS;
        attackDirections = aD;
        state = 'n';
        color = c;
        position = iP;
        initialPosition = iP;
    }

    public char getState() {
        return state;
    }

    public int getPositionX() {
        return Integer.valueOf(position.substring(0, 1));
    }

    public int getPositionY() {
        return Integer.valueOf(position.substring(1, 2));
    }

    public void moved(String destiantion) {
        position = destiantion;
    }

    public void attacked(int damage) {
        vitality = vitality - damage;
        if (vitality < 0) vitality = 0;
    }

    public void healed() {
        vitality = initialVitality;
    }

    public void frozen() {
        state = 'f';
    }

    public void unFrozen() {
        state = 'n';
    }

    public void dead() {
        vitality = 0;
        state = 'd';
    }

    public void revived() {
        vitality = initialVitality;
        state = 'n';
    }

    public void teleported(String destination) {
        position = destination;
    }

    public void inCombat(int ememyVitality, int ememyAttackStrength, char frozen) {
        // when the ememy is frozen, frozen = true
        if ((frozen != 'f') && (state != 'f')) {
            while (vitality > 0 && ememyVitality > 0) {
                vitality = vitality - ememyAttackStrength;
                if (vitality < 0) {
                    vitality = 0;
                    state = 'd';
                }
                ememyVitality = ememyVitality - attackStrength;
                if (ememyVitality < 0) {
                    ememyVitality = 0;
                }
            }

        } else if ((frozen != 'f') && (state == 'f')) {
            vitality = 0;
            state = 'd';
        }
    }


    public Set getMoveCells(String boardState) {
        int[][] boardStateInt = new int[6][6];
        Set moveCellsSet = new TreeSet();
        Map moveCellsMapEnemy = new TreeMap();
        Map moveCellsMap = new TreeMap();
        Map moveCellsMapCopy = new TreeMap();
//  initialize boardState
        for (int i = 0; i <= 5; i++) {
            for (int j = 0; j <= 5; j++) {
                if ((boardState.charAt(6 * i + j) >= 97) && (boardState.charAt(6 * i + j) <= 122)) {
                    boardStateInt[i][j] = -1;
                } else if ((boardState.charAt(6 * i + j) >= 65) && (boardState.charAt(6 * i + j) <= 90)) {
                    boardStateInt[i][j] = 1;
                } else {
                    boardStateInt[i][j] = 0;
                }
            }
        }
//  initialize moveFromx,moveFromY
        int moveFromX = Integer.valueOf(position.substring(0, 1));
        int moveFromY = Integer.valueOf(position.substring(1, 2));
        String getMoveCells = "";
//  For the piece whose move directions is any ,the available  move cells without move block condition
        if (moveDirections == 'a') {
            for (int i = 1; i <= 6; i++) {
                for (int j = 1; j <= 6; j++) {
                    if (

                            (Math.pow(moveFromX - i, 2) + Math.pow(moveFromY - j, 2)) <= (2 * Math.pow(moveRange, 2))
                                    && (boardStateInt[i - 1][j - 1] * boardStateInt[moveFromX - 1][moveFromY - 1] != 1)
                            ) {
                        String k = "" + i + j;
                        moveCellsSet.add(k);
                    }
                }
            }
//  remove cells in the situation of move block
            String rCell = "";
            int rCellX;
            int rCellY;
            moveCellsMap.put(position, 0);
            String aroundCell = "";
            for (int i = 1; i <= moveRange; i++) {
                moveCellsMapCopy.clear();
                moveCellsMapCopy.putAll(moveCellsMap);
                Iterator it = moveCellsMapCopy.keySet().iterator();
                while (it.hasNext()) {
                    rCell = "" + it.next();
                    rCellX = Integer.valueOf(rCell.substring(0, 1));
                    rCellY = Integer.valueOf(rCell.substring(1, 2));
                    for (int i1 = -1; i1 <= 1; i1++) {
                        for (int j1 = -1; j1 <= 1; j1++) {
                            aroundCell = "" + (rCellX + i1) + (rCellY + j1);
                            if (moveCellsSet.contains(aroundCell)) {
                                if (((moveFromX - 1 + i1) >= 0 && (moveFromX - 1 + j1) <= 5) && ((moveFromY - 1 + i1) >= 0 && (moveFromY - 1 + j1) <= 5)) {
                                    if (moveCellsMap.get(aroundCell) == null) {
                                        if (boardStateInt[moveFromX - 1][moveFromY - 1] * boardStateInt[rCellX - 1 + i1][rCellY - 1 + j1] == -1) {
                                            if (!moveCellsMapEnemy.containsKey(aroundCell)) {
                                                moveCellsMapEnemy.put(aroundCell, i);
                                            }
                                        } else {
                                            moveCellsMap.put(aroundCell, i);
                                        }
                                    } else {
                                        if (boardStateInt[moveFromX - 1][moveFromY - 1] * boardStateInt[rCellX - 1 + i1][rCellY - 1 + j1] == -1) {
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
        }
//  For the piece whose move directions is any ,the available  move cells without move block condition
        else if (moveDirections == '/') {
            for (int i = 1; i <= 6; i++) {
                for (int j = 1; j <= 6; j++) {
                    if ((Math.pow(moveFromX - i, 2) + Math.pow(moveFromY - j, 2)) <= (Math.pow(moveRange, 2)) && (boardStateInt[i - 1][j - 1] * boardStateInt[moveFromX - 1][moveFromY - 1] != 1)) {
                        String k = "" + i + j;
                        moveCellsSet.add(k);
                    }
                }
            }
            if (moveType == 'w') {
//  remove cells in the situation of move block
                String rCell = "";
                int rCellX;
                int rCellY;
                moveCellsMap.put(position, 0);
                String aroundCell = "";
                int[][] dir = new int[4][2];
                dir[0][0] = -1;
                dir[0][1] = 0;
                dir[1][0] = 0;
                dir[1][1] = 1;
                dir[2][0] = 1;
                dir[2][1] = 0;
                dir[3][0] = 0;
                dir[3][1] = -1;
                for (int i = 1; i <= moveRange; i++) {
                    moveCellsMapCopy.clear();
                    moveCellsMapCopy.putAll(moveCellsMap);
                    Iterator it = moveCellsMapCopy.keySet().iterator();
                    while (it.hasNext()) {
                        rCell = "" + it.next();
                        rCellX = Integer.valueOf(rCell.substring(0, 1));
                        rCellY = Integer.valueOf(rCell.substring(1, 2));
                        for (int i1 = 0; i1 <= 3; i1++) {
                            for (int j1 = 0; j1 <= 1; j1++) {
                                aroundCell = "" + (rCellX + dir[i1][0]) + (rCellY + dir[i1][1]);
                                if (moveCellsSet.contains(aroundCell)) {
                                    if (((moveFromX - 1 + dir[i1][0]) >= 0 && (moveFromX - 1 + dir[i1][1]) <= 5) && ((moveFromY - 1 + dir[i1][0]) >= 0 && (moveFromY - 1 + dir[i1][1]) <= 5)) {

                                        if (moveCellsMap.get(aroundCell) == null) {
                                            if (boardStateInt[moveFromX - 1][moveFromY - 1] * boardStateInt[rCellX - 1 + dir[i1][0]][rCellY - 1 + dir[i1][1]] == -1) {
                                                moveCellsMapEnemy.put(aroundCell, i);
                                            } else {
                                                moveCellsMap.put(aroundCell, i);
                                            }
                                        } else {
                                            if (boardStateInt[moveFromX - 1][moveFromY - 1] * boardStateInt[rCellX - 1 + dir[i1][0]][rCellY - 1 + dir[i1][1]] == -1) {
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
            }
        }

        moveCellsMap.putAll(moveCellsMapEnemy);
        System.out.println("moveCellsMap=" + moveCellsMap.toString());
        System.out.println("moveCellsSet=" + moveCellsSet.toString());
        System.out.println("moveCellsMapEnemy=" + moveCellsMapEnemy.toString());
        return moveCellsSet;
    }

    public boolean judgeMove(String boardState, int moveToX, int moveToY) {
        Set getMoveCells = getMoveCells(boardState);
        String moveTo=""+moveToX+moveToY;
        boolean index = false;
        if (getMoveCells.contains(moveTo)){
            index =true;
        }
        return index;

    }

    public Set getAttackCells(String boardState) {
        int[][] boardStateInt = new int[6][6];
        Map attackCellsMap = new TreeMap();
        for (int i = 0; i <= 5; i++) {
            for (int j = 0; j <= 5; j++) {
                if ((boardState.charAt(6 * i + j) >= 97) && (boardState.charAt(6 * i + j) <= 122)) {
                    boardStateInt[i][j] = -1;
                } else if ((boardState.charAt(6 * i + j) >= 65) && (boardState.charAt(6 * i + j) <= 90)) {
                    boardStateInt[i][j] = 1;
                } else {
                    boardStateInt[i][j] = 0;
                }
            }
        }

        int attackFromX = Integer.valueOf(position.substring(0, 1));
        int attackFromY = Integer.valueOf(position.substring(1, 2));

        if (attackDirections == '/') {

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
            String aCell="";
            for (int i = 0; i <= 3; i++) {
                for (int j = 1; j <= attackRange; j++) {

                    if (((attackFromX + dir[i][0] * j) < 1) || ((attackFromX + dir[i][0] * j) > 6) || ((attackFromY + dir[i][1] * j) < 1) || ((attackFromY + dir[i][1] * j) > 6)) {
                        break;
                    } else if (boardStateInt[attackFromX - 1 + dir[i][0] * j][attackFromY - 1 + dir[i][1] * j] * boardStateInt[attackFromX - 1][attackFromY - 1] == -1) {
                        aCell = ""+ (attackFromX + dir[i][0] * j) + (attackFromY + dir[i][1] * j);
                        attackCellsMap.put(aCell,j);

                        break;
                    } else if (boardStateInt[attackFromX - 1 + dir[i][0] * j][attackFromY - 1 + dir[i][1] * j] * boardStateInt[attackFromX - 1][attackFromY - 1] == 1) {
                        break;
                    } else {
                    }
                }
            }


        } else if (attackDirections == 'd') {
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
            String aCell="";
            for (int i = 1; i <= attackRange; i++) {
                for (int j = 0; j <= 3; j++) {
                    if (((attackFromX + dir[j][0] * i) < 1) || ((attackFromX + dir[j][0] * i) > 6) || ((attackFromY + dir[j][1] * i) < 1) || ((attackFromY + dir[j][1] * i) > 6)) {
                        break;
                    } else if (boardStateInt[attackFromX - 1 + dir[j][0] * i][attackFromY - 1 + dir[j][1] * i] * boardStateInt[attackFromX - 1][attackFromY - 1] == -1) {
                        aCell = ""+ (attackFromX + dir[i][0] * j) + (attackFromY + dir[i][1] * j);
                        attackCellsMap.put(aCell,j);
                        break;
                    } else if (boardStateInt[attackFromX - 1 + dir[j][0] * i][attackFromY - 1 + dir[j][1] * i] * boardStateInt[attackFromX - 1][attackFromY - 1] == 1) {
                        break;
                    } else {
                    }
                }
            }
        }

        return attackCellsMap.keySet();
    }

    public boolean judgeAttack(String boardState, int attackToX, int attackToY) {
        Set getAttackCells = getMoveCells(boardState);
        String moveTo=""+attackToX+attackToY;
        boolean index = false;
        if (getAttackCells.contains(moveTo)){
            index =true;
        }
        return index;

    }



    public boolean chooseActions(char action) {
        if (action == 'M' || action == 'A') {
            return true;
        } else {
            return false;
        }

    }


}

