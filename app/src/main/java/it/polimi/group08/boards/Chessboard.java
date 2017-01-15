package it.polimi.group08.boards;


import java.util.Set;
import java.util.TreeSet;

import it.polimi.group08.actions.Attack;
import it.polimi.group08.actions.Freeze;
import it.polimi.group08.actions.Heal;
import it.polimi.group08.actions.Move;
import it.polimi.group08.actions.Revive;
import it.polimi.group08.actions.Teleport;
import it.polimi.group08.functions.GlobalVariables;
import it.polimi.group08.pieces.Piece;

/**
 * Created by gengdongjie on 28/12/2016.
 */

public class Chessboard {
    public GlobalVariables gV = new GlobalVariables();
    //  emptyPiece represent the empty cell in chessboard, it's typeInt is 0.
//  While typeInt for white piece is equal to 1, for black piece is equal to -1
    private Piece emptyPiece = gV.piece(0, 0);
    private int turnsNum;
    //  movePlayerInt and movePlayerXInt are used to transfer "W" and "B" into integer.
//  if it is white's turn now ,movePlayerInt = 1, movePlayerXInt = 0;
//  if it is black's turn now ,movePlayerInt = -1, movePlayerXInt = 1;
    public int movePlayerInt;
    public int movePlayerXInt;
    //  array playerscore is the score of two players, it will be refreshed after a valid action
//    playerScore[0] is the score of white player
//    playerScore[1] is the score of black player
    public int[] playerScore = new int[2];
    private int scoreTmp = 0;
    //    boardPiece is the array of class piece, it's dimension is 6*6;
    public Piece[][] boardPiece;
    //    boardPiece is the array of class piece, it's dimension is 2*8;
//    for each player, there are 8 pieces in total. Each piece in the array is represent a non-empty piece.
//    if the piece is dead after an action, the corresponding piece's state in playerPiece will be changed to "d"
    public Piece[][] playerPiece;
    //    boardStr = "W" or "B" + boardType Str + vitalityStr + frozenPieceStr + unusedSpell + resultStr;
    private String boardStr;
    //    boardTypeStr is the String of alive pieces' type in the board
    private String boardTypeStr;
    //    vitalityStr is the String of vitality
    private String vitalityStr;
    //    frozenPiece[0][0] and frozenPiece[0][1] represent the frozen white piece
//    frozenPiece[0][2] represent the left round that the white frozen piece can be defreeze
//    frozenPiece[1][0] and frozenPiece[1][1] represent the frozen black piece
//    frozenPiece[0][2] represent the left round that the black frozen piece can be defreeze
    private int[][] frozenPiece = new int[2][3];
    public String frozenPieceStr;

    private String unusedSpells;
    //    resultStr is represent the result of the game, if the game is not finished, it equal to ""
    private String resultStr;

    //    The following methods' function is straightforwardly represent by their names
    public void initBoard() {
//        initializing parameter
        turnsNum = 1;
        movePlayerInt = 1;
        movePlayerXInt = 0;
        boardTypeStr = "" +
                "000000" +
                "GK00sa" +
                "DS00km" +
                "MK00sd" +
                "AS00kg" +
                "000000";
        vitalityStr = "5675434334345765";

        frozenPiece[0][2] = 0;
        frozenPiece[1][2] = 0;
        frozenPieceStr = "000000";
        refreshFrozenPieceStr();

        unusedSpells = "FHRTFHRT";
//        initializing boardPiece and playerPiece
        initPiece("n");
    }

    private void initPiece(String s) {
        boardPiece = new Piece[6][6];
        playerPiece = new Piece[2][8];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                boardPiece[i][j] = gV.piece(i, j);
                if (!boardPiece[i][j].getType().equals("0")) {
                    playerPiece[boardPiece[i][j].getPlayerX()][boardPiece[i][j].getPlayerY()] = boardPiece[i][j];
                    boardPiece[i][j].state = s;
                }

            }
        }

    }

    public void setBoardWithStr(String m, String b, String v, String f, String u) {
        if (m.length() + b.length() + v.length() + f.length() + u.length() != 67) {
            System.out.println("ERROR:<The length of input String is in correct!>");
        }
        turnsNum = 1;
        boardStr = m + b + v + f + u;
        switch (m) {
            case "W": {
                movePlayerInt = 1;
                movePlayerXInt = 0;
                break;
            }
            case "B": {
                movePlayerInt = -1;
                movePlayerXInt = 1;
                break;
            }
            default:
                System.out.println("ERROR:<The moving player is neither 'w' nor 'B !>");
        }
        boardTypeStr = b;
        vitalityStr = v;
        frozenPieceStr = f;
        if (Integer.parseInt(f.substring(2, 3)) != 0) {
            frozenPiece[0][0] = Integer.parseInt(f.substring(0, 1)) - 1;
            frozenPiece[0][1] = Integer.parseInt(f.substring(1, 2)) - 1;
            frozenPiece[0][2] = Integer.parseInt(f.substring(2, 3));
        }
        if (Integer.parseInt(f.substring(5, 6)) != 0) {
            frozenPiece[1][0] = Integer.parseInt(f.substring(3, 4)) - 1;
            frozenPiece[1][1] = Integer.parseInt(f.substring(4, 5)) - 1;
            frozenPiece[1][2] = Integer.parseInt(f.substring(5, 6));
        }
        unusedSpells = u;
        resultStr = "";
        setPiece();
    }

    private void setPiece() {
        initPiece("d");
        String type;
        Set<String> initPiece = new TreeSet<>();
        int playerX;
        int playerY;
        int vitalityNum = 0;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                type = boardTypeStr.substring(i + 6 * j, i + 6 * j + 1);
                if (type.equals("0")) {
                    boardPiece[i][j] = new Piece();
                } else if (gV.typeToNum().containsKey(type)) {
                    playerX = Integer.parseInt(gV.typeToNum().get(type).toString().substring(0, 1));
                    playerY = Integer.parseInt(gV.typeToNum().get(type).toString().substring(1, 2));
                    if (initPiece.contains(type)) playerY += 2;
                    boardPiece[i][j] = playerPiece[playerX][playerY];
                    boardPiece[i][j].state = "n";
                    boardPiece[i][j].vitality = Integer.parseInt(vitalityStr.substring(vitalityNum, vitalityNum + 1));
                    vitalityNum++;
                    initPiece.add(type);
                } else {
                    System.out.println("ERROR:<boardTypeStr contains invalid character!>");
                }
            }
        }
        if (vitalityNum != vitalityStr.indexOf("0") || (vitalityNum == 17 && vitalityStr.indexOf("0") == -1)) {
            System.out.println("ERROR:<The length of vitalityStr "
                    + vitalityStr.indexOf("0") +
                    " is not equal to number of piece "
                    + vitalityNum + " !>");
        }
        if (frozenPiece[0][2] != 0)
            boardPiece[frozenPiece[0][0]][frozenPiece[0][1]].state = "f";
        if (frozenPiece[1][2] != 0)
            boardPiece[frozenPiece[1][0]][frozenPiece[1][1]].state = "f";
        playerPiece[0][2].spells = unusedSpells.substring(0, 4);
        playerPiece[1][2].spells = unusedSpells.substring(4, 8);

    }

    private void refreshBoardStr() {
        turnsNum++;
        boardStr = "";
        boardTypeStr = "";
        vitalityStr = "";

        if (boardPiece[frozenPiece[0][0]][frozenPiece[0][1]].getTypeInt() != 1) {
            frozenPiece[0][0] = 0;
            frozenPiece[0][1] = 0;
            frozenPiece[0][2] = 0;
        }
        if (boardPiece[frozenPiece[1][0]][frozenPiece[1][1]].getTypeInt() != -1) {
            frozenPiece[1][0] = 0;
            frozenPiece[1][1] = 0;
            frozenPiece[1][2] = 0;
        }

//     refresh frozenPieceStr, movePlayerInt = 1 means last turn is white's turn, so the freTimer for white need to minus 1
        if (movePlayerInt == 1) {
            if (frozenPiece[0][2] > 0) {
                frozenPiece[0][2] -= 1;
                if (frozenPiece[0][2] == 0) {
                    boardPiece[frozenPiece[0][0]][frozenPiece[0][1]].state = "n";
                }
            }
        } else {
            if (frozenPiece[1][2] > 0) {
                frozenPiece[1][2] -= 1;
                if (frozenPiece[1][2] == 0) {
                    boardPiece[frozenPiece[1][0]][frozenPiece[1][1]].state = "n";
                }
            }
        }
        refreshFrozenPieceStr();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                boardTypeStr += boardPiece[j][i].getType();
                if (boardPiece[i][j].vitality != 0) vitalityStr += boardPiece[i][j].vitality;
            }
        }
        movePlayerInt *= -1;
        movePlayerXInt = (int) (0.5 + movePlayerInt * (-0.5));

        if (movePlayerInt == 1) boardStr += "W";
        else boardStr += "B";
        vitalityStr = (vitalityStr + "0000000000000000").substring(0, 16);
        unusedSpells = playerPiece[0][2].spells + playerPiece[1][2].spells;
        boardStr = boardStr
                + boardTypeStr
                + vitalityStr
                + frozenPieceStr
                + unusedSpells;
        refreshResultStr();
    }

    private void refreshFrozenPieceStr() {
        if (frozenPiece[0][2] > 0) {
            frozenPieceStr = "" + (frozenPiece[0][0] + 1) + (frozenPiece[0][1] + 1) + frozenPiece[0][2] + frozenPieceStr.substring(3, 6);
        } else {
            frozenPiece[0][0] = 0;
            frozenPiece[0][1] = 0;
            frozenPieceStr = "000" + frozenPieceStr.substring(3, 6);
        }
        if (frozenPiece[1][2] > 0) {
            frozenPieceStr = frozenPieceStr.substring(0, 3) + (frozenPiece[1][0] + 1) + (frozenPiece[1][1] + 1) + frozenPiece[1][2];
        } else {
            frozenPiece[1][0] = 0;
            frozenPiece[1][1] = 0;
            frozenPieceStr = frozenPieceStr.substring(0, 3) + "000";
        }
    }

    private void refreshResultStr() {
//        according to the number of freeze pieces of two players and how many special cells are occupied by players to calculate the resultStr
        resultStr = "";
        int[] specialCellNum = new int[2];
        for (Object str : gV.specialCellsSet()) {
            int cellX = Integer.parseInt(str.toString().substring(0, 1));
            int cellY = Integer.parseInt(str.toString().substring(1, 2));
            if (boardPiece[cellX][cellY].getTypeInt() != 0) {
                specialCellNum[boardPiece[cellX][cellY].getPlayerX()] += 1;
            }
        }
        if (specialCellNum[0] >= 3) resultStr = "WHITE";
        else if (specialCellNum[1] >= 3) resultStr = "BLACK";
        else {
            int[] freePieceNum = new int[2];
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 8; j++) {
                    if (playerPiece[i][j].state.equals("n")) freePieceNum[i] += 1;
                }
            }
            if (movePlayerInt == 1) {
                if (freePieceNum[0] == 0) {
                    if (freePieceNum[1] == 0) resultStr = "DRAW";
                    else resultStr = "BLACK";
                } else {
                    if (freePieceNum[1] == 0) resultStr = "WHITE";
                }
            } else {
                if (freePieceNum[1] == 0) {
                    if (freePieceNum[0] == 0) resultStr = "DRAW";
                    else resultStr = "WHITE";
                } else {
                    if (freePieceNum[0] == 0) resultStr = "BLACK";
                }
            }
        }
    }

    public boolean isAction(String actionStr) {
        if (actionStr.length() != 5) {
            System.out.println("The length of action is incorrect!");
        }
//        index is used to indict whether the action is executed successfully
//        parse the action into five integers
        boolean index = false;
        String action = actionStr.substring(0, 1);
        int fromX = Integer.parseInt(actionStr.substring(1, 2)) - 1;
        int fromY = Integer.parseInt(actionStr.substring(2, 3)) - 1;
        int toX = Integer.parseInt(actionStr.substring(3, 4)) - 1;
        int toY = Integer.parseInt(actionStr.substring(4, 5)) - 1;
        refreshScoreTmp(action, fromX, fromY, toX, toY);
        print();
        switch (action) {
//            for different action, different classes need to be declared to execute the action
            case "M": {
                Move mov = new Move();
                if (mov.isMoved(movePlayerInt, boardPiece, fromX, fromY, toX, toY, emptyPiece)) {
                    index = true;
                }
                break;
            }
            case "A": {
                Attack atk = new Attack();
                if (atk.isAttacked(movePlayerInt, boardPiece, fromX, fromY, toX, toY, emptyPiece)) {
                    index = true;
                }
                break;
            }
            case "H": {
                Heal hea = new Heal();
                if (hea.isHealed(movePlayerInt, boardPiece, fromX, fromY, emptyPiece)) {
                    index = true;
                }
                break;
            }
            case "T": {
                Teleport tel = new Teleport();
                if (tel.isTeleported(movePlayerInt, boardPiece, fromX, fromY, toX, toY, emptyPiece)) {
                    index = true;
                }
                break;
            }
            case "R": {
                Revive rev = new Revive();
                if (rev.isRevived(movePlayerInt, playerPiece, boardPiece, fromX, fromY, emptyPiece)) {
                    index = true;
                }
                break;
            }
            case "F": {
                Freeze fre = new Freeze();
                if (fre.isFreezed(movePlayerInt, boardPiece, fromX, fromY, emptyPiece)) {
//                    for the action "F", frozenpiece is needed to be refreshed independently
                    if (movePlayerInt == 1) {
                        frozenPiece[1][0] = fromX;
                        frozenPiece[1][1] = fromY;
                        frozenPiece[1][2] = 3;
                    } else {
                        frozenPiece[0][0] = fromX;
                        frozenPiece[0][1] = fromY;
                        frozenPiece[0][2] = 3;
                    }
                    index = true;
                }
                break;
            }
            default:
                System.out.println("ERROR:<The action is invalid!>");
        }
        if (index) {
            print();
            System.out.println("INFO:<The action is executed successfully!>");
            playerScore[movePlayerXInt] += scoreTmp;
            refreshBoardStr();
            refreshPlayerScore();
        } else System.out.println("ERROR:<The action is not executed!>");
        return index;
    }

    private void refreshScoreTmp(String act, int fX, int fY, int tX, int tY) {
        scoreTmp = 0;
//        The following code is used to calculated the score a player can get after the result of the action
        switch (act) {
            case "M": {
                if (boardPiece[tX][tY].getTypeInt() != 0) {
                    scoreTmp = (boardPiece[fX][fY].getInitVitality() + boardPiece[tX][tY].getInitVitality()) * 9;
                }
                break;
            }
            case "A": {
                scoreTmp = (boardPiece[fX][fY].getInitVitality() + boardPiece[tX][tY].vitality) * 10;
                break;
            }
            case "H": {
                scoreTmp = boardPiece[fX][fY].vitality * 15;
                break;
            }
            case "T": {
                if (boardPiece[tX][tY].getTypeInt() != 0) {
                    scoreTmp = boardPiece[fX][fY].getInitVitality() + boardPiece[tX][tY].getInitVitality() * 6;
                }
                break;
            }
            case "R": {
                if (movePlayerInt == 1) {
                    scoreTmp = playerPiece[0][2].vitality * 3;
                } else {
                    scoreTmp = playerPiece[1][2].vitality * 3;
                }
                break;
            }
            case "F": {
                scoreTmp = 100 - boardPiece[fX][fY].vitality * 10;
                break;
            }

        }
    }

    //This method is used to determine whether the game is over. If the game is over, extra points will be added to the winner
    private void refreshPlayerScore() {
        if (resultStr.length() != 0) {
            for (int i = 0; i < 2; i++) {
                if (playerPiece[i][2].state.equals("n")) {
                    playerScore[i] += 500;
                }
                for (int j = 0; j < 4; j++) {
                    if (!playerPiece[i][2].spells.substring(i, i + 1).equals("0")) {
                        playerScore[i] += 100;
                    }
                }
            }
        }
        if (resultStr.equals("WHITE")) {
            playerScore[0] += (playerScore[0] + 2 * Math.abs(playerScore[0] - playerScore[1]));
        } else if (resultStr.equals("BLACK")) {
            playerScore[1] += (playerScore[0] + 2 * Math.abs(playerScore[0] - playerScore[1]));
        }
    }

    public String getBoardStr() {
        return boardStr;
    }

    public String getResultStr() {
        return resultStr;
    }

    public int getTurnsNum() {
        return turnsNum;
    }

    //    This method is used to get all the available pieces that piece in row x and column y can attack
    public Set getAttackCells(int x, int y) {
        Attack atk = new Attack();
        return atk.getAvailCells(boardPiece, x, y);
    }

    //    This method is used to get all the available pieces that piece in row x and column y can movw
    public Set getMoveCells(int x, int y) {
        Move mov = new Move();
        return mov.getAvailCells(boardPiece, x, y);

    }

    //    print all the related parameters and variables in the board
    public void print() {
        System.out.println("********************************");
        if (movePlayerInt == 1) {
            System.out.println("W");
        } else {
            System.out.println("B");
        }
//        System.out.println(movePlayer);
        System.out.println(boardTypeStr.substring(0, 6));
        System.out.println(boardTypeStr.substring(6, 12));
        System.out.println(boardTypeStr.substring(12, 18));
        System.out.println(boardTypeStr.substring(18, 24));
        System.out.println(boardTypeStr.substring(24, 30));
        System.out.println(boardTypeStr.substring(30, 36));
        System.out.println(vitalityStr);
        System.out.println(frozenPieceStr);
        System.out.println(unusedSpells);
        System.out.println(resultStr + "*******************************");
    }
}
