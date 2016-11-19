package it.polimi.chessofclans.pieces;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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


    private int attackFromX;
    private int attackFromY;
    private int attackToX;
    private int attackToy;


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

//    public String getAttackCells() {
//        String getAttackCells = "";
//        int[][] oneOfCells = new int[4 * attackRange][2];
//        for (int i = 1; i <= attackRange; i++) {
//            if (attackDirections == '/') {
////                 upward attack
//                oneOfCells[4 * i - 4][0] = Integer.valueOf(position.substring(0, 1)) - i;
//                oneOfCells[4 * i - 4][1] = Integer.valueOf(position.substring(1, 2));
////                right attack
//                oneOfCells[4 * i - 3][0] = Integer.valueOf(position.substring(0, 1));
//                oneOfCells[4 * i - 3][1] = Integer.valueOf(position.substring(1, 2)) + i;
////                downward attack
//                oneOfCells[4 * i - 2][0] = Integer.valueOf(position.substring(0, 1)) + i;
//                oneOfCells[4 * i - 2][1] = Integer.valueOf(position.substring(1, 2));
////                left attack
//                oneOfCells[4 * i - 1][0] = Integer.valueOf(position.substring(0, 1));
//                oneOfCells[4 * i - 1][1] = Integer.valueOf(position.substring(1, 2)) - i;
//            } else if (attackDirections == 'd') {
////                upward right attack
//                oneOfCells[4 * i - 4][0] = Integer.valueOf(position.substring(0, 1)) - i;
//                oneOfCells[4 * i - 4][1] = Integer.valueOf(position.substring(1, 2)) + i;
////                downward right attack
//                oneOfCells[4 * i - 3][0] = Integer.valueOf(position.substring(0, 1)) + i;
//                oneOfCells[4 * i - 3][1] = Integer.valueOf(position.substring(1, 2)) + i;
////                downward left attack
//                oneOfCells[4 * i - 2][0] = Integer.valueOf(position.substring(0, 1)) + i;
//                oneOfCells[4 * i - 2][1] = Integer.valueOf(position.substring(1, 2)) - i;
////                upward left attack
//                oneOfCells[4 * i - 1][0] = Integer.valueOf(position.substring(0, 1)) - i;
//                oneOfCells[4 * i - 1][1] = Integer.valueOf(position.substring(1, 2)) - i;
//
//
//            }
//        }
//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < attackRange; j++) {
////                    System.out.println("cells["+(i+4*j)+"][0]="+oneOfCells[i +4*j][0]);
////                    System.out.println("cells["+(i+4*j)+"][1]="+oneOfCells[i +4*j][1]);
//
//                if ((oneOfCells[i + 4 * j][0] >= 1)
//                        && (oneOfCells[(i + 4 * j)][0] <= 6)
//                        && (oneOfCells[(i + 4 * j)][1] >= 1)
//                        && (oneOfCells[(i + 4 * j)][1] <= 6)) {
//                    getAttackCells = getAttackCells + oneOfCells[i + 4 * j][0] + oneOfCells[i + 4 * j][1];
//                } else {
//                    getAttackCells = getAttackCells + "xx";
//                }
//            }
//        }
//        return getAttackCells;
//    }

    public String getMoveCells(String boardState) {
        int[][] boardStateInt = new int[6][6];
        Set moveCellsSet = new HashSet();
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

        int moveFromX = Integer.valueOf(position.substring(0, 1));
        int moveFromY = Integer.valueOf(position.substring(1, 2));
        String getMoveCells = "";
        if (moveDirections == 'a') {
            for (int i = 1; i <= 6; i++) {
                for (int j = 1; j <= 6; j++) {
                    if (
                            ((Math.pow(moveFromX - i, 2) + Math.pow(moveFromY - j, 2)) <= (2 * Math.pow(moveRange, 2)))
                                    && ((Math.pow(moveFromX - i, 2) + Math.pow(moveFromY - j, 2)) != 0)
                            ) {
                        if (boardStateInt[i - 1][j - 1] * boardStateInt[moveFromX - 1][moveFromY - 1] != 1) {
                            String k=""+i+j;
                            moveCellsSet.add(k);
//                            getMoveCells = getMoveCells + i + j;
                        }
                    }
                }
            }
//            judgetment of move block
            for (int i = 1; i <= moveRange; i++) {
                Iterator it=moveCellsSet.iterator();
                int cellsX;
                int cellsY;
                while(it.hasNext()){
                    cellsX=Integer.valueOf(it.next().toString().substring(0,1));
                    cellsY=Integer.valueOf(it.next().toString().substring(1,2));
                    if ((Math.pow(cellsX-moveFromX,2)+Math.pow(cellsY-moveFromY,2))<=2){

                    }

                }

            }


        } else if (moveDirections == '/') {
            for (int i = 1; i <= 6; i++) {
                for (int j = 1; j <= 6; j++) {
                    if (((Math.abs(moveFromX - i) + Math.abs(moveFromY - j)) <= moveRange) && (Math.abs(moveFromX - i) + Math.abs(moveFromY - j)) != 0) {
                        if (boardStateInt[i - 1][j - 1] * boardStateInt[moveFromX - 1][moveFromY - 1] != 1) {
                            String k=""+i+j;
                            moveCellsSet.add(k);
                        }
                    }
                }
            }
        } else {
            getMoveCells = "";
            System.out.println("[Error]The moveDirections is illegal!");
        }


        return getMoveCells;
    }

    public boolean judgeMove(String boardState, int moveToX, int moveToY) {
        String getMoveCells = getMoveCells(boardState);
        String moveTo = "" + moveToX + moveToY;

        boolean index = false;
        for (int i = 0; i < (getMoveCells.length() / 2); i++) {
            System.out.println(getMoveCells.substring(2 * i, 2 * i + 2));
            System.out.println(moveTo);
            if (getMoveCells.substring(2 * i, 2 * i + 2).equals(moveTo)) {

                index = true;
            }
        }
        return index;

    }

    public String getAttackCells(String boardState) {
        int[][] boardStateInt = new int[6][6];
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
        String getAttackCells = "";

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
            for (int i = 0; i <= 3; i++) {
                for (int j = 1; j <= attackRange; j++) {
                    if (((attackFromX + dir[i][0] * j) < 1) || ((attackFromX + dir[i][0] * j) > 6) || ((attackFromY + dir[i][1] * j) < 1) || ((attackFromY + dir[i][1] * j) > 6)) {
//                        System.out.println(attackFromX + dir[i][0] * j);
//                        System.out.println(attackFromX + dir[i][1] * j);
//                        System.out.println("1i="+i+" j="+j);
                        break;
                    } else if (boardStateInt[attackFromX - 1 + dir[i][0] * j][attackFromY - 1 + dir[i][1] * j] * boardStateInt[attackFromX - 1][attackFromY - 1] == -1) {
//                        System.out.println("2i="+i+" j="+j);
                        getAttackCells = getAttackCells + (attackFromX + dir[i][0] * j) + (attackFromY + dir[i][1] * j);
                        break;
                    } else if (boardStateInt[attackFromX - 1 + dir[i][0] * j][attackFromY - 1 + dir[i][1] * j] * boardStateInt[attackFromX - 1][attackFromY - 1] == 1) {
//                        System.out.println("3i="+i+" j="+j);
                        break;
                    } else {
//                        System.out.println("4i="+i+" j="+j);
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
            for (int i = 1; i <= attackRange; i++) {
                for (int j = 0; j <= 3; j++) {
                    if (((attackFromX + dir[j][0] * i) < 1) || ((attackFromX + dir[j][0] * i) > 6) || ((attackFromY + dir[j][1] * i) < 1) || ((attackFromY + dir[j][1] * i) > 6)) {
                        break;
                    } else if (boardStateInt[attackFromX - 1 + dir[j][0] * i][attackFromY - 1 + dir[j][1] * i] * boardStateInt[attackFromX - 1][attackFromY - 1] == -1) {
                        getAttackCells = getAttackCells + (attackFromX + dir[i][0] * j) + (attackFromY + dir[i][1] * j);
                        break;
                    } else if (boardStateInt[attackFromX - 1 + dir[j][0] * i][attackFromY - 1 + dir[j][1] * i] * boardStateInt[attackFromX - 1][attackFromY - 1] == 1) {
                        break;
                    } else {
                    }
                }
            }
        }

        return getAttackCells;
    }

    public boolean judgeAttack(String boardState, int attackToX, int attackToY) {
        String getAttackCells = getAttackCells(boardState);
        String attackTo = "" + attackToX + attackToY;

        boolean index = false;
        for (int i = 0; i < (getAttackCells.length() / 2); i++) {
            System.out.println(getAttackCells.substring(2 * i, 2 * i + 2));
            System.out.println(attackTo);
            if (getAttackCells.substring(2 * i, 2 * i + 2).equals(attackTo)) {

                index = true;
            }
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

