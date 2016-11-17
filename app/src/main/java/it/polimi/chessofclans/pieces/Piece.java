package it.polimi.chessofclans.pieces;

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


    private String attackDestination;

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

    public void died() {
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

    public String getAttackCells() {
        String getAttackCells = "";
        int[][] oneOfCells = new int[4 * attackRange][2];
        for (int i = 1; i <= attackRange; i++) {
            if (attackDirections == '/') {
//                 upward attack
                oneOfCells[4 * i - 4][0] = Integer.valueOf(position.substring(0, 1)) - i;
                oneOfCells[4 * i - 4][1] = Integer.valueOf(position.substring(1, 2));
//                downward attack
                oneOfCells[4 * i - 3][0] = Integer.valueOf(position.substring(0, 1)) + i;
                oneOfCells[4 * i - 3][1] = Integer.valueOf(position.substring(1, 2));
//                left attack
                oneOfCells[4 * i - 2][0] = Integer.valueOf(position.substring(0, 1));
                oneOfCells[4 * i - 2][1] = Integer.valueOf(position.substring(1, 2)) - i;
//                right attack
                oneOfCells[4 * i - 1][0] = Integer.valueOf(position.substring(0, 1));
                oneOfCells[4 * i - 1][1] = Integer.valueOf(position.substring(1, 2)) + i;
                System.out.println("执行了 ");
            } else if (attackDirections == 'd') {
//                    upward left attack
                oneOfCells[4 * i - 4][0] = Integer.valueOf(position.substring(0, 1)) - i;
                oneOfCells[4 * i - 4][1] = Integer.valueOf(position.substring(1, 2)) - i;
//                downward left attack
                oneOfCells[4 * i - 3][0] = Integer.valueOf(position.substring(0, 1)) + i;
                oneOfCells[4 * i - 3][1] = Integer.valueOf(position.substring(1, 2)) - i;
//                downward right attack
                oneOfCells[4 * i - 2][0] = Integer.valueOf(position.substring(0, 1)) + i;
                oneOfCells[4 * i - 2][1] = Integer.valueOf(position.substring(1, 2)) + i;
//                right attack
                oneOfCells[4 * i - 1][0] = Integer.valueOf(position.substring(0, 1)) - i;
                oneOfCells[4 * i - 1][1] = Integer.valueOf(position.substring(1, 2)) + i;
            }
        }
        for (int i = 0; i < 4 * attackRange; i++) {
            System.out.println("cells[" + i + "]=" + oneOfCells[i][0] + oneOfCells[i][1]);
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < attackRange; j++) {
//                    System.out.println("cells["+(i+4*j)+"][0]="+oneOfCells[i +4*j][0]);
//                    System.out.println("cells["+(i+4*j)+"][1]="+oneOfCells[i +4*j][1]);

                if ((oneOfCells[i + 4 * j][0] > -1)
                        && (oneOfCells[(i + 4 * j)][0] < 6)
                        && (oneOfCells[(i + 4 * j)][1] > -1)
                        && (oneOfCells[(i + 4 * j)][1] < 6)) {
                    getAttackCells = getAttackCells + oneOfCells[i + 4 * j][0] + oneOfCells[i + 4 * j][1];
                } else {
                    getAttackCells = getAttackCells + "xx";
                }
            }
        }
        return getAttackCells;
    }


    public String getMoveCells() {

        return "4";
    }


}

