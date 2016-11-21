package it.polimi.test.boards;
import it.polimi.test.pieces.*;
import it.polimi.test.players.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by lucio on 11/21/2016.
 */

public class BoardBasic {
    public BoardBasic(){
        turnsNum = 1;
        color = "white";

        boardState[0]="000000".toCharArray();
        boardState[1]="GK00sa".toCharArray();
        boardState[2]="DS00km".toCharArray();
        boardState[3]="MK00sd".toCharArray();
        boardState[4]="AS00kg".toCharArray();
        boardState[5]="000000".toCharArray();

        for(int i = 0; i<6;i++)
            for(int j = 0 ; j <6; j++)
                boardCells[i][j]='n';
        boardCells[0][0]='s';
        boardCells[0][3]='s';
        boardCells[5][2]='s';
        boardCells[5][5]='s';

        vitalities="5675434334345765";
        frozenPieces="000000";
        unusedSpells="FHRTFHRT";

    }

    private int turnsNum;
    private String color;

    private char boardState[][] = new char[6][6];
    private char boardCells[][] = new char[6][6];
    private String vitalities;
    private String frozenPieces;
    private String unusedSpells;
    private int timer1=0;
    private int timer2=0;
    private Piece p1Frozen = new Piece();
    private Piece p2Frozen = new Piece();

    public  String getColor(){
        return color;
    }

    public char[][] getBoardState(){
        return boardState;
    }



    public Set getMoveCells(Piece p) {
        int[][] boardStateInt = new int[6][6];
        Set moveCellsSet = new TreeSet();
        Map moveCellsMapEnemy = new TreeMap();
        Map moveCellsMap = new TreeMap();
        Map moveCellsMapCopy = new TreeMap();
//  initialize boardState
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
//  initialize moveFromx,moveFromY
        int moveFromX = p.getPositionX();
        int moveFromY = p.getPositionY();
        String position = "" + moveFromX + moveFromY;
        String getMoveCells = "";
//  For the piece whose move directions is any ,the available  move cells without move block condition
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
//  remove cells in the situation of move block
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


        System.out.println("moveCellsMap=" + moveCellsMap.toString());
        System.out.println("moveCellsSet=" + moveCellsSet.toString());
        System.out.println("moveCellsMapEnemy=" + moveCellsMapEnemy.toString());
        moveCellsMap.putAll(moveCellsMapEnemy);
        return moveCellsMap.keySet();
    }

    public boolean judgeMove(Piece p, int moveToX, int moveToY) {
        Set getMoveCells = getMoveCells(p);
        String moveTo = "" + moveToX + moveToY;
        boolean index = false;
        if (getMoveCells.contains(moveTo)) {
            index = true;
        }
        return index;
    }

    public Set getAttackCells(Piece p) {
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

        return attackCellsMap.keySet();
    }

    public boolean judgeAttack(Piece p, int attackToX, int attackToY) {
        Set getAttackCells = getAttackCells(p);
        String attackTo = "" + attackToX + attackToY;
        boolean index = false;
        if (getAttackCells.contains(attackTo)) {
            index = true;
        }
        return index;

    }

    public int excuteCombat(Piece p1,Piece p2){
        int combatResult;
        if(p1.getPieceEnable()&&p2.getPieceEnable()){
            return combatResult = p1.inCombatWith(p2);
        }
        return 0;
    }

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

    public boolean excuteRevive(Mage m, Piece p){
        if(!m.getPieceEnable()){
            System.out.println("You don't have a mage to cast spell revive!");
            return false;
        }
        else if(!p.getPieceEnable()){
            System.out.println("You can only choose your dead piece to revive!");
            return false;
        }
        else return m.revive(p);
    }

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
        else if(boardCells[p.getPositionX()-1][p.getPositionY()-1]=='s'){
            System.out.println("You can not teleport your piece in special cell!");
            return false;
        }
        else if ((color.equals("white")&&boardState[x-1][y-1]!='0'&&boardState[x-1][y-1]<'Z')||(color.equals("black")&&boardState[x-1][y-1]>'Z')){
            System.out.println("You can not teleport your piece onto your own piece!");
            return false;
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



    public boolean judgeVictory(){
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
            return false;
        }
        if(b>2){
            System.out.println("Black wins!");
            return false;
        }

        w=1;
        b=1;
        for(int i=0;i<6;i++) {
            for(int j =0;j<6;j++){
                if (boardState[i][j]>=65&&boardState[i][j]<=90) b = 0;
                if (boardState[i][j]>=97&&boardState[i][j]<=122) w = 0;
            }
        }
        if(w==1 && b==1){
            System.out.println("Tie!");
            return false;
        }
        if(w==1) {
            System.out.println("White wins!");
            return false;
        }
        if(b==1) {
            System.out.println("Black wins!");
            return false;
        }

        return true;
    }

    public void refreshBoard(Player white,Player black){
        int boardV[][]=new int[6][6];
        vitalities="";
        for(int i=0;i<6;i++)
            for(int j=0;j<6;j++){
                boardState[i][j]='0';
                boardV[i][j]=0;
            }
        for(int i = 0; i < white.getPiecesAliveNum();i++){
            boardState[white.getPiecesAlive()[i].getPositionX()-1][white.getPiecesAlive()[i].getPositionY()-1]=white.getPiecesAlive()[i].getType().charAt(0);
            boardV[white.getPiecesAlive()[i].getPositionX()-1][white.getPiecesAlive()[i].getPositionY()-1]=white.getPiecesAlive()[i].getVitality();
        }
        for(int i = 0; i < black.getPiecesAliveNum();i++){
            boardState[black.getPiecesAlive()[i].getPositionX()-1][black.getPiecesAlive()[i].getPositionY()-1]=black.getPiecesAlive()[i].getType().toLowerCase().charAt(0);
            boardV[black.getPiecesAlive()[i].getPositionX()-1][black.getPiecesAlive()[i].getPositionY()-1]=black.getPiecesAlive()[i].getVitality();
        }
        for(int i=0;i<6;i++)
            for(int j=0;j<6;j++){
                if(boardState[j][i]!='0')vitalities=vitalities + boardV[j][i];
            }
        if(p1Frozen.getPieceEnable())frozenPieces="" + p1Frozen.getPositionX() + p1Frozen.getPositionY() + timer1;
        else frozenPieces="000";
        if(p2Frozen.getPieceEnable())frozenPieces=frozenPieces + p2Frozen.getPositionX() + p2Frozen.getPositionY() + timer2;
        else frozenPieces=frozenPieces+"000";
        Mage m1 = white.chooseMage();
        Mage m2 = black.chooseMage();
        if(m1.getPieceEnable())unusedSpells=m1.getSpellsUnused();
        else unusedSpells="0000";
        if(m2.getPieceEnable())unusedSpells=unusedSpells+m2.getSpellsUnused();
        else unusedSpells=unusedSpells+"0000";
    }

    public void turnsOver(){
        if(color.equals("white")){
            color = "black";
            if(timer2!=0)timer2--;
            else if(p2Frozen.getPieceEnable()){
                p2Frozen.unFrozen();
                p2Frozen = new Piece();
            }
        }
        else {
            color = "white";
            turnsNum = turnsNum+1;
            if(timer1!=0)timer1--;
            else if(p1Frozen.getPieceEnable()){
                p1Frozen.unFrozen();
                p1Frozen = new Piece();
            }
        }
    }

    public void printBoard(){
        System.out.println("Turn on "+color+"!");
        if(boardState[0][0]=='0')boardState[0][0]='1';
        if(boardState[0][3]=='0')boardState[0][3]='1';
        if(boardState[5][2]=='0')boardState[5][2]='1';
        if(boardState[5][5]=='0')boardState[5][5]='1';
        for(int i = 0; i<6;i++) {
            for(int j = 0;j<6;j++){
                System.out.print("  " + boardState[i][j] + "  ");
            }
            System.out.print("\n\n\n");
        }
        System.out.println("Vitalities "+vitalities);
        System.out.println("Frozen pieces: "+frozenPieces);
        System.out.println("Unused spells: "+unusedSpells);
        System.out.println("Enter action:");
    }
}

