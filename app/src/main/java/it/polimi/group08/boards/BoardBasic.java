package it.polimi.group08.boards;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import it.polimi.group08.pieces.Archer;
import it.polimi.group08.pieces.Dragon;
import it.polimi.group08.pieces.Giant;
import it.polimi.group08.pieces.Knight;
import it.polimi.group08.pieces.Mage;
import it.polimi.group08.pieces.Piece;
import it.polimi.group08.pieces.Squire;
import it.polimi.group08.players.Player;




/**
 * Created by lucio on 11/21/2016.
 */

public class BoardBasic {
    public BoardBasic(){
        turnsNum = 1;
        color = "white";

        boardState[0]="00000c".toCharArray();
        boardState[1]="GK00sa".toCharArray();
        boardState[2]="DS00km".toCharArray();
        boardState[3]="MK00sd".toCharArray();
        boardState[4]="AS00kg".toCharArray();
        boardState[5]="C00000".toCharArray();

        for(int i = 0; i<6;i++)
            for(int j = 0 ; j <6; j++)
                boardCells[i][j]='n';
        boardCells[0][0]='s';
        boardCells[0][3]='s';
        boardCells[5][2]='s';
        boardCells[5][5]='s';

        vitalities="567584343343485765";
        frozenPieces="000000";
        unusedSpells="FHRTFHRT";

    }

    /**
     * turnsNum and color record the number of turns and whoose turn
     * boardState, vitalities, frozenPieces, unusedSpells keep track of the board
     * boardCells indicate special cells
     * timers and p1Frozen, p2Frozen keep track of the frozen piece and turns left
     */
    private int turnsNum;
    private String color;

    private char boardState[][] = new char[6][6];
    private char boardCells[][] = new char[6][6];
    private String vitalities;
    private String frozenPieces;
    private String unusedSpells;
    private String argResult;

    private int timer1=0;
    private int timer2=0;
    private Piece p1Frozen = new Piece();
    private Piece p2Frozen = new Piece();

    public String getColor(){
        return color;
    }

    /*
    public int getTurnsNum(){
        return turnsNum;
    }

    public char[][] getBoardState(){
        return boardState;
    }

    public int getTimer1(){
        return timer1;
    }

    public int getTimer2(){
        return timer2;
    }

    public String getUnusedSpells(){
        return unusedSpells;
    }

    public void setTurnsNum(int i){
        turnsNum = i;
    }

    public void setColor(String s){
        color = s;
    }

    public void setTimer1(int i){
        timer1 = i;
    }

    public void setTimer2(int i){
        timer2 = i;
    }
    */

    // boardInitialize is to initialize board in general case
    public String boardInitialize(String arg, Player white, Player black){
        //First of all read the string as a board
        String argNew="";
        if(arg.charAt(0)=='W'||arg.charAt(0)=='w')color ="white";
        else color="black";
        boardState[0]=arg.substring(1,7).toCharArray();
        boardState[1]=arg.substring(7,13).toCharArray();
        boardState[2]=arg.substring(13,19).toCharArray();
        boardState[3]=arg.substring(19,25).toCharArray();
        boardState[4]=arg.substring(25,31).toCharArray();
        boardState[5]=arg.substring(31,37).toCharArray();
        vitalities=arg.substring(37,53);
        frozenPieces=arg.substring(53,59);
        unusedSpells=arg.substring(59,67);
        argNew=arg.substring(67);

        int i = 0,j = 0,k = 0;
        int x,y;
        int pieceRecord[] = {1,1,1,2,1,2,1,1,1,2,1,2};
        white.readPlayer();
        black.readPlayer();

        //Second finish the piece alive list, white and black, six different pieces, there are 12 case in total
        for(j=0;j<6;j++)
            for(i=0;i<6;i++){
                switch (boardState[i][j]){
                    case 'A':{
                        Piece p = new Archer("white",5,1);
                        p.moveTo(i+1,j+1);
                        p.setVitality(vitalities.charAt(k)-48);
                        white.addPiece(p);
                        pieceRecord[0]--;
                        k++;
                    }break;
                    case 'D':{
                        Piece p = new Dragon("white",3,1);
                        p.moveTo(i+1,j+1);
                        p.setVitality(vitalities.charAt(k)-48);
                        white.addPiece(p);
                        pieceRecord[1]--;
                        k++;
                    }break;
                    case 'G':{
                        Piece p = new Giant("white",2,1);
                        p.moveTo(i+1,j+1);
                        p.setVitality(vitalities.charAt(k)-48);
                        white.addPiece(p);
                        pieceRecord[2]--;
                        k++;
                    }break;
                    case 'K':{
                        Piece p;
                        if(pieceRecord[3]==2) p = new Knight("white",2,2);
                        else  p = new Knight("white",4,2);
                        p.moveTo(i+1,j+1);
                        p.setVitality(vitalities.charAt(k)-48);
                        white.addPiece(p);
                        pieceRecord[3]--;
                        k++;
                    }break;
                    case 'M':{
                        Mage m = new Mage("white",4,1);
                        m.moveTo(i+1,j+1);
                        m.setVitality(vitalities.charAt(k)-48);
                        m.setSpellsUnused(unusedSpells.substring(0,4));
                        white.addPiece(m);
                        pieceRecord[4]--;
                        k++;
                    }break;
                    case 'S':{
                        Piece p ;
                        if(pieceRecord[5]==2)p = new Squire("white",3,2);
                        else p = new Squire("white",5,2);
                        p.moveTo(i+1,j+1);
                        p.setVitality(vitalities.charAt(k)-48);
                        white.addPiece(p);
                        pieceRecord[5]--;
                        k++;
                    }break;
                    case 'a':{
                        Piece p = new Archer("black",2,6);
                        p.moveTo(i+1,j+1);
                        p.setVitality(vitalities.charAt(k)-48);
                        black.addPiece(p);
                        pieceRecord[6]--;
                        k++;
                    }break;
                    case 'd':{
                        Piece p = new Dragon("black",4,6);
                        p.moveTo(i+1,j+1);
                        p.setVitality(vitalities.charAt(k)-48);
                        black.addPiece(p);
                        pieceRecord[7]--;
                        k++;
                    }break;
                    case 'g':{
                        Piece p = new Giant("black",5,6);
                        p.moveTo(i+1,j+1);
                        p.setVitality(vitalities.charAt(k)-48);
                        black.addPiece(p);
                        pieceRecord[8]--;
                        k++;
                    }break;
                    case 'k':{
                        Piece p;
                        if(pieceRecord[9]==2)p= new Knight("black",5,5);
                        else p=new Knight("black",3,5);
                        p.moveTo(i+1,j+1);
                        p.setVitality(vitalities.charAt(k)-48);
                        black.addPiece(p);
                        pieceRecord[9]--;
                        k++;
                    }break;
                    case 'm':{
                        Mage m = new Mage("black",3,6);
                        m.moveTo(i+1,j+1);
                        m.setVitality(vitalities.charAt(k)-48);
                        m.setSpellsUnused(unusedSpells.substring(4,8));
                        black.addPiece(m);
                        pieceRecord[10]--;
                        k++;
                    }break;
                    case 's':{
                        Piece p;
                        if(pieceRecord[11]==2)p = new Squire("black",4,5);
                        else p= new Squire("black",2,5);
                        p.moveTo(i+1,j+1);
                        p.setVitality(vitalities.charAt(k)-48);
                        black.addPiece(p);
                        pieceRecord[11]--;
                        k++;
                    }break;
                }
            }

        //third finish the piece dead list, there are still 12 cases
        for(i=0;i<12;i++){
            if(pieceRecord[i]>0) {
                switch (i) {
                    case 0: {
                        Piece p = new Archer("white", 5, 1);
                        p.setState('d');
                        white.addPiece(p);
                    }
                    break;
                    case 1: {
                        Piece p = new Dragon("white", 3, 1);
                        p.setState('d');
                        white.addPiece(p);
                    }
                    break;
                    case 2: {
                        Piece p = new Giant("white", 2, 1);
                        p.setState('d');
                        white.addPiece(p);
                    }
                    case 3: {
                        while(pieceRecord[3]>0) {
                            if (pieceRecord[3] == 2) {
                                Piece p = new Knight("white", 2, 2);
                                p.setState('d');
                                white.addPiece(p);
                                pieceRecord[3]--;
                            } else {
                                Piece p = new Knight("white", 4, 2);
                                p.setState('d');
                                white.addPiece(p);
                                pieceRecord[3]--;
                            }
                        }
                    }
                    break;
                    case 4: {
                        Mage m = new Mage("white", 4, 1);
                        m.setState('d');
                        m.setSpellsUnused(unusedSpells.substring(0, 4));
                        white.addPiece(m);
                    }
                    break;
                    case 5: {
                        while(pieceRecord[5]>0) {
                            if (pieceRecord[5] == 2) {
                                Piece p = new Squire("white", 3, 2);
                                p.setState('d');
                                white.addPiece(p);
                                pieceRecord[5]--;
                            } else {
                                Piece p = new Squire("white", 5, 2);
                                p.setState('d');
                                white.addPiece(p);
                                pieceRecord[5]--;
                            }
                        }
                    }
                    break;
                    case 6: {
                        Piece p = new Archer("black", 2, 6);
                        p.setState('d');
                        black.addPiece(p);
                    }
                    break;
                    case 7: {
                        Piece p = new Dragon("black", 4, 6);
                        p.setState('d');
                        black.addPiece(p);
                    }
                    break;
                    case 8: {
                        Piece p = new Giant("black", 5, 6);
                        p.setState('d');
                        black.addPiece(p);
                    }
                    case 9: {
                        while(pieceRecord[9]>0) {
                            if (pieceRecord[9] == 2) {
                                Piece p = new Knight("black", 5, 5);
                                p.setState('d');
                                black.addPiece(p);
                                pieceRecord[9]--;
                            } else {
                                Piece p = new Knight("black", 3, 5);
                                p.setState('d');
                                black.addPiece(p);
                                pieceRecord[9]--;
                            }
                        }
                    }
                    break;
                    case 10: {
                        Mage m = new Mage("black", 3, 6);
                        m.setState('d');
                        m.setSpellsUnused(unusedSpells.substring(4, 8));
                        black.addPiece(m);
                    }
                    break;
                    case 11: {
                        while(pieceRecord[11]>0) {
                            if (pieceRecord[11] == 2) {
                                Piece p = new Squire("black", 4, 5);
                                p.setState('d');
                                black.addPiece(p);
                                pieceRecord[11]--;
                            } else {
                                Piece p = new Squire("black", 2, 5);
                                p.setState('d');
                                black.addPiece(p);
                                pieceRecord[11]--;
                            }
                        }
                    }
                    break;
                }
            }
        }

        // fourth, frozen pieces and timers need to be recorded in board class
        timer1 = frozenPieces.charAt(2) - 48;
        if(timer1 !=0) {
            x = frozenPieces.charAt(1) - 48;
            y = frozenPieces.charAt(0) - 48;
            p1Frozen = white.choosePieceAlive(x,y);
            p1Frozen.setState('f');
        }
        timer2 = frozenPieces.charAt(5) -48;
        if(timer2 !=0) {
            x = frozenPieces.charAt(4) - 48;
            y = frozenPieces.charAt(3) - 48;
            p2Frozen = black.choosePieceAlive(x,y);
            p2Frozen.setState('f');
        }
        return argNew;
    }

    //getMoveCells returns the set of cells which can be reached
    public Set getMoveCells(Piece p) {
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
        int[][] boardStateInt = new int[6][6];

        Set moveCellsSet = new TreeSet();
        Map moveCellsMapEnemy = new TreeMap();
        Map moveCellsMap = new TreeMap();
        Map moveCellsMapCopy = new TreeMap();
        for (int i = 0; i <= 5; i++) {
            for (int j = 0; j <= 5; j++) {
                if ((boardState[i][j] >= 97) && (boardState[i][j] <= 122)) {
                    if(boardState[i][j]=='c')boardStateInt[i][j]=1;
                    else boardStateInt[i][j] = -1;
                } else if ((boardState[i][j] >= 65) && (boardState[i][j] <= 90)) {
                    if(boardState[i][j]=='C')boardStateInt[i][j]=-1;
                    else boardStateInt[i][j] = 1;
                } else {
                    boardStateInt[i][j] = 0;
                }
            }
        }
//  initialize moveFromx,moveFromY
        int moveFromX = p.getPositionX();
        int moveFromY = p.getPositionY();
        String position = "" + moveFromX + moveFromY;
        String getMoveCells = "";
//  For the piece whose move directions is any, calculate the available  move cells without regard to move block problem
        if (p.getMoveDirections() == 'a') {
            for (int i = 1; i <= 6; i++) {
                for (int j = 1; j <= 6; j++) {
                    if (
                            (Math.pow(moveFromX - i, 2) + Math.pow(moveFromY - j, 2)) <= (2 * Math.pow(p.getMoveRange(), 2))
                                    && (boardStateInt[i - 1][j - 1] * boardStateInt[moveFromX - 1][moveFromY - 1] != 1)
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
            String rCell = "";
            int rCellX;
            int rCellY;
            moveCellsMap.put(position, 0);
            String aroundCell = "";
            for (int i = 1; i <= p.getMoveRange(); i++) {
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
                                if ((rCellX  + i1) >= 1 && (rCellX + i1) <= 6 && (rCellY  + j1) >= 1 && (rCellY  + j1) <= 6) {
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
        else if (p.getMoveDirections() == 's' && p.getMoveType() == 'w') {
            String k = "";
            for (int i = 1; i <= 6; i++) {
                for (int j = 1; j <= 6; j++) {
                    if ((Math.pow(moveFromX - i, 2) + Math.pow(moveFromY - j, 2)) <= (Math.pow(p.getMoveRange(), 2)) && (boardStateInt[i - 1][j - 1] * boardStateInt[moveFromX - 1][moveFromY - 1] != 1)) {
                        k = "" + i + j;
                        moveCellsSet.add(k);
                    }
                }
            }
            if (p.getMoveType() == 'w') {
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
                for (int i = 1; i <= p.getMoveRange(); i++) {
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
                                    if ((moveFromX - 1 + dir[i1][0]) >= 0 && (moveFromX - 1 + dir[i1][0]) <= 5 && (moveFromY - 1 + dir[i1][1]) >= 0 && (moveFromY - 1 + dir[i1][1]) <= 5) {

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
            // For the fly type, move block is not in consideration
        } else if (p.getMoveDirections() == 's' && p.getMoveType() == 'f') {
            int distance;
            String k = "";
            for (int i = 1; i <= 6; i++) {
                for (int j = 1; j <= 6; j++) {
                    if ((Math.pow(moveFromX - i, 2) + Math.pow(moveFromY - j, 2)) <= (Math.pow(p.getMoveRange() - 1, 2) + 1) || (Math.pow(moveFromX - i, 2) + Math.pow(moveFromY - j, 2)) == Math.pow(p.getMoveRange(), 2)) {
                        k = "" + i + j;
                        distance = Math.abs(moveFromX - i) + Math.abs(moveFromY - j);
                        if (boardStateInt[i - 1][j - 1] * boardStateInt[moveFromX - 1][moveFromY - 1] == 0) {
                            moveCellsMap.put(k, distance);
                        } else if (boardStateInt[i - 1][j - 1] * boardStateInt[moveFromX - 1][moveFromY - 1] == -1) {
                            moveCellsMapEnemy.put(k, distance);
                        }
                    }
                }
            }
        }


        /*System.out.println("moveCellsMap=" + moveCellsMap.toString());
        System.out.println("moveCellsSet=" + moveCellsSet.toString());
        System.out.println("moveCellsMapEnemy=" + moveCellsMapEnemy.toString());*/

        moveCellsMap.putAll(moveCellsMapEnemy);
        return moveCellsMap.keySet();
    }

    //judgeMove returns a boolean value which indicate whether the destination can be reached(true) or not(false)
    public boolean judgeMove(Piece p, int moveToX, int moveToY) {
        Set getMoveCells = getMoveCells(p);
        String moveTo = "" + moveToX + moveToY;
        boolean index = false;
        if (getMoveCells.contains(moveTo)) {
            index = true;
        }
        return index;
    }

    //getAttackCells returns the set of cells which can be attacked
    public Set getAttackCells(Piece p) {
        /**
         * The piece, no mather what is the type of its attack direction, have 4 kind of directions to attack.So the maximum number of enemy can be attacked is equal to 4.
         * The algorithm compute from one of the four directions.In the specified direction.
         * If the nearest piece is an enemy piece, this piece together with the distance is be added into attackCellsMap and this must be the only cell can be attacked in this direction.
         * If the nearest piece is a friend piece, there must be no cell can be attacked in this direction.
         */
        int[][] boardStateInt = new int[6][6];
        Map attackCellsMap = new TreeMap();
        for (int i = 0; i <= 5; i++) {
            for (int j = 0; j <= 5; j++) {
                if ((boardState[i][j] >= 97) && (boardState[i][j] <= 122)) {
                    boardStateInt[i][j] = -1;
                } else if ((boardState[i][j] >= 65) && (boardState[i][j] <= 90)) {
                    boardStateInt[i][j] = 1;
                } else {
                    boardStateInt[i][j] = 0;
                }
            }
        }

        int attackFromX = p.getPositionX();
        int attackFromY = p.getPositionY();

        if (p.getAttackDirections() == 's') {

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
            String aCell = "";
            for (int i = 0; i <= 3; i++) {
                for (int j = 1; j <= p.getAttackRange(); j++) {
                    if (((attackFromX + dir[i][0] * j) < 1) || ((attackFromX + dir[i][0] * j) > 6) || ((attackFromY + dir[i][1] * j) < 1) || ((attackFromY + dir[i][1] * j) > 6)) {
                        break;
                    } else if (boardStateInt[attackFromX - 1 + dir[i][0] * j][attackFromY - 1 + dir[i][1] * j] * boardStateInt[attackFromX - 1][attackFromY - 1] == -1) {
                        aCell = "" + (attackFromX + dir[i][0] * j) + (attackFromY + dir[i][1] * j);
                        attackCellsMap.put(aCell, j);
                        break;
                    } else if (boardStateInt[attackFromX - 1 + dir[i][0] * j][attackFromY - 1 + dir[i][1] * j] * boardStateInt[attackFromX - 1][attackFromY - 1] == 1) {
                        break;
                    } else {
                    }
                }
            }
        } else if (p.getAttackDirections() == 'd') {
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
            String aCell = "";
            for (int i = 0; i <= 3; i++) {
                for (int j = 1; j <= p.getAttackRange(); j++) {
                    if (((attackFromX + dir[i][0] * j) < 1) || ((attackFromX + dir[i][0] * j) > 6) || ((attackFromY + dir[i][1] * j) < 1) || ((attackFromY + dir[i][1] * j) > 6)) {
                        break;
                    } else if (boardStateInt[attackFromX - 1 + dir[i][0] * j][attackFromY - 1 + dir[i][1] * j] * boardStateInt[attackFromX - 1][attackFromY - 1] == -1) {
                        aCell = "" + (attackFromX + dir[i][0] * j) + (attackFromY + dir[i][1] * j);
                        attackCellsMap.put(aCell, j);
                        break;
                    } else if (boardStateInt[attackFromX - 1 + dir[i][0] * j][attackFromY - 1 + dir[i][1] * j] * boardStateInt[attackFromX - 1][attackFromY - 1] == 1) {
                        break;
                    } else {
                    }
                }
            }
        }
        //System.out.println("AttackCellsMap=" + attackCellsMap.toString());
        return attackCellsMap.keySet();
    }

    //judgeAttack returns a boolean value which indicate whether the target can be attacked(true) or not (false)
    public boolean judgeAttack(Piece p, int attackToX, int attackToY) {
        Set getAttackCells = getAttackCells(p);
        String attackTo = "" + attackToX + attackToY;
        boolean index = false;
        if (getAttackCells.contains(attackTo)) {
            index = true;
        }
        return index;

    }

    //excuteCombat is to judge combat happens or not(0), if it happens, return combat result(1,2,3)
    public int excuteCombat(Piece p1,Piece p2){
        if(p1.getPieceEnable()&&p2.getPieceEnable()){
            return p1.inCombatWith(p2);
        }
        return 0;
    }

    //excuteFreeze is to judge freeze can happen or not(false), if it can happen, mage cast freeze then return the result (true, false), if not print reason
    public boolean excuteFreeze(Mage m, Piece p){
        if(!m.getPieceEnable()){
            System.out.println("You don't have a mage to cast spell freeze!");
            return false;
        }
        else if(!p.getPieceEnable()){
            System.out.println("You can only choose one opponent's piece to freeze!");
            return false;
        }
        else if(p.getType().equals("Mage")){
            System.out.println("You can't freeze Mage!");
            return false;
        }
        else if(boardCells[p.getPositionX()-1][p.getPositionY()-1]=='s'){
            System.out.println("You can't freeze piece in special cell!");
            return false;
        }
        else if(m.freeze(p)){
            if(p.getColor().equals("white")){
                timer1=3;
                p1Frozen=p;
            }
            else{
                timer2=3;
                p2Frozen=p;
            }
            return true;
        }
        else return false;
    }

    //excuteFreeze is to judge heal can happen or not(false), if it can happen, mage cast heal then return the result (true, false), if not print reason
    public boolean excuteHeal(Mage m, Piece p){
        if(!m.getPieceEnable()){
            System.out.println("You don't have a mage to cast spell heal!");
            return false;
        }
        else if(!p.getPieceEnable()){
            System.out.println("You can only choose your own piece to heal!");
            return false;
        }
        else if(p.getType().equals("Mage")){
            System.out.println("You can not heal mage!");
            return false;
        }
        else if(boardCells[p.getPositionX()-1][p.getPositionY()-1]=='s'){
            System.out.println("You can not heal a piece in special cell!");
            return false;
        }
        else return m.heal(p);
    }

    //excuteFreeze is to judge revive can happen or not(false), if it can happen, mage cast revive then return the result (true, false), if not print reason
    public boolean excuteRevive(Mage m, Piece p,int x, int y){
        if(!m.getPieceEnable()){
            System.out.println("You don't have a mage to cast spell revive!");
            return false;
        }
        else if(!p.getPieceEnable()){
            System.out.println("You can only choose your dead piece to revive!");
            return false;
        }
        else return m.revive(p,x,y);
    }

    //excuteFreeze is to judge teleport can happen or not(false), if it can happen, mage cast teleport then return the result (true, false), if not print reason
    public boolean excuteTeleport(Mage m, Piece p, int x, int y){
        if(!m.getPieceEnable()){
            System.out.println("You don't have a mage to cast spell teleport!");
            return false;
        }
        else if(!p.getPieceEnable()){
            System.out.println("You can only choose your own piece to teleport!");
            return false;
        }
        else if(p.getType().equals("Mage")){
            System.out.println("You can not teleport mage!");
            return false;
        }
        else if(p.getType().equals("Castle")){
            System.out.println("Castle is too big to teleport!");
            return false;
        }
        else if(boardCells[p.getPositionX()-1][p.getPositionY()-1]=='s'){
            System.out.println("You can not teleport your piece in special cell!");
            return false;
        }
        else if ((color.equals("white")&&boardState[x-1][y-1]>='A'&&boardState[x-1][y-1]<'Z')||(color.equals("black")&&boardState[x-1][y-1]>'Z')){
            if((color.equals("white")&&boardState[x-1][y-1]=='C')||(color.equals("black")&&boardState[x-1][y-1]=='c'))return m.teleport(p,x,y);
            else {
                System.out.println("You can not teleport your piece onto your own piece!");
                return false;
            }
        }
        else if((color.equals("white")&&boardState[x-1][y-1]=='m')||(color.equals("black")&&boardState[x-1][y-1]=='M')){
            System.out.println("You can not teleport your piece to opponent's mage!");
            return false;
        }
        else if(boardCells[x-1][y-1]=='s'){
            System.out.println("You can not teleport your piece to special cell!");
            return false;
        }
        else return m.teleport(p,x,y);
    }

    //judgeVictory is to judge game is over(false) or not(true), and print who wins or draw
    public String judgeVictory(){
        //judge special cells occupied victory
        int w =0;
        int b =0;
        if(boardState[0][0]>=65&&boardState[0][0]<=90)w++;
        if(boardState[0][0]>=97&&boardState[0][0]<=122)b++;
        if(boardState[0][3]>=65&&boardState[0][3]<=90)w++;
        if(boardState[0][3]>=97&&boardState[0][3]<=122)b++;
        if(boardState[5][2]>=65&&boardState[5][2]<=90)w++;
        if(boardState[5][2]>=97&&boardState[5][2]<=122)b++;
        if(boardState[5][5]>=65&&boardState[5][5]<=90)w++;
        if(boardState[5][5]>=97&&boardState[5][5]<=122)b++;
        if(w>2){
            System.out.println("White wins!");
            argResult=argResult+"WHITE";
            return argResult;
        }
        if(b>2){
            System.out.println("Black wins!");
            argResult=argResult+"BLACK";
            return argResult;
        }

        //judge no normal piece exist victory or draw
        w=1;
        b=1;
        if(p1Frozen.getPieceEnable())boardState[p1Frozen.getPositionX()-1][p1Frozen.getPositionY()-1]='0';
        if(p2Frozen.getPieceEnable())boardState[p2Frozen.getPositionX()-1][p2Frozen.getPositionY()-1]='0';
        for(int i=0;i<6;i++) {
            for(int j =0;j<6;j++){
                if (boardState[i][j]>=65&&boardState[i][j]<=90) b = 0;
                if (boardState[i][j]>=97&&boardState[i][j]<=122) w = 0;
            }
        }
        if(w==1 && b==1){
            System.out.println("Draw!");
            argResult=argResult+"DRAW";
            return argResult;
        }
        if(w==1) {
            System.out.println("White wins!");
            argResult=argResult+"WHITE";
            return argResult;
        }
        if(b==1) {
            System.out.println("Black wins!");
            argResult=argResult+"BLACK";
            return argResult;
        }
        return argResult;
    }

    //After each action, the board is refreshed according to two players' pieces list
    public void refreshBoard(Player white,Player black){
        int boardV[][]=new int[6][6];
        vitalities="";
        //refresh boardState
        for(int i=0;i<6;i++)
            for(int j=0;j<6;j++){
                boardState[i][j]='0';
                boardV[i][j]=0;
            }
        for(int i = 0; i < white.getPiecesAliveNum();i++){
            if(boardState[white.getPiecesAlive()[i].getPositionX()-1][white.getPiecesAlive()[i].getPositionY()-1]!='C') {
                boardState[white.getPiecesAlive()[i].getPositionX() - 1][white.getPiecesAlive()[i].getPositionY() - 1] = white.getPiecesAlive()[i].getType().charAt(0);
                boardV[white.getPiecesAlive()[i].getPositionX() - 1][white.getPiecesAlive()[i].getPositionY() - 1] = white.getPiecesAlive()[i].getVitality();
            }
            if(white.getPiecesAlive()[i].getState()=='f') p1Frozen=white.getPiecesAlive()[i];
        }
        for(int i = 0; i < black.getPiecesAliveNum();i++){
            if(boardState[black.getPiecesAlive()[i].getPositionX()-1][black.getPiecesAlive()[i].getPositionY()-1]!='c') {
                boardState[black.getPiecesAlive()[i].getPositionX() - 1][black.getPiecesAlive()[i].getPositionY() - 1] = black.getPiecesAlive()[i].getType().toLowerCase().charAt(0);
                boardV[black.getPiecesAlive()[i].getPositionX() - 1][black.getPiecesAlive()[i].getPositionY() - 1] = black.getPiecesAlive()[i].getVitality();
            }
            if(black.getPiecesAlive()[i].getState()=='f') p2Frozen=black.getPiecesAlive()[i];
        }
        //refresh vitalities
        for(int i=0;i<6;i++)
            for(int j=0;j<6;j++){
                if(boardState[j][i]!='0')vitalities=vitalities + boardV[j][i];
            }
        while(vitalities.length()<16)vitalities=vitalities+"0";
        //refresh frozen pieces
        if(timer1!=0)frozenPieces="" + p1Frozen.getPositionY() + p1Frozen.getPositionX() + timer1;
        else frozenPieces="000";
        if(timer2!=0)frozenPieces=frozenPieces + p2Frozen.getPositionY() + p2Frozen.getPositionX() + timer2;
        else frozenPieces=frozenPieces+"000";
        //refresh unusedSpells
        Mage m1 = white.chooseMage();
        Mage m2 = black.chooseMage();
        if(m1.getPieceEnable())unusedSpells=m1.getSpellsUnused();
        else {
            m1=white.chooseMageDead();
            unusedSpells=m1.getSpellsUnused();
        }
        if(m2.getPieceEnable())unusedSpells=unusedSpells+m2.getSpellsUnused();
        else {
            m2=black.chooseMageDead();
            unusedSpells=unusedSpells+m2.getSpellsUnused();
        }
    }

    //turnsOver is to end a turn, and calculate the frozen time left
    public void turnsOver(){
        if(color.equals("white")){
            color = "black";
            if(timer1!=0)timer1--;
            if(timer1==0&&p1Frozen.getPieceEnable()){
                p1Frozen.unFrozen();
                p1Frozen = new Piece();
                frozenPieces="000"+frozenPieces.substring(3);
            }
        }
        else {
            color = "white";
            turnsNum = turnsNum+1;
            if(timer2!=0)timer2--;
            if(timer2==0&&p2Frozen.getPieceEnable()){
                p2Frozen.unFrozen();
                p2Frozen = new Piece();
                frozenPieces=frozenPieces.substring(0,3)+"000";
            }
        }
    }

    //printBoard is to print board and return a String recording board
    public String printBoard(){
        argResult = "";
        argResult=argResult+color.toUpperCase().charAt(0);
        System.out.println(color.toUpperCase().charAt(0));
        for(int i = 0; i<6;i++) {
            for(int j = 0;j<6;j++){
                argResult=argResult+boardState[i][j];
                System.out.print( boardState[i][j]);
            }
            System.out.print("\n");
        }
        argResult=argResult+vitalities;
        System.out.println("Vitalities "+vitalities);
        argResult=argResult+frozenPieces;
        System.out.println("Frozen pieces: "+frozenPieces);
        argResult=argResult+unusedSpells;
        System.out.println("Unused spells: "+unusedSpells);
        return argResult;
    }
}

