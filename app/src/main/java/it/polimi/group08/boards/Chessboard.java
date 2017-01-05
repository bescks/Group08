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
    private GlobalVariables gV = new GlobalVariables();
    private Piece emptyPiece = gV.piece(0, 0);
    private int turnsNum;
    private int movePlayerInt;

    public Piece[][] boardPiece;
    public Piece[][] playerPiece;

    private String boardStr;
    private String boardTypeStr;
    private String vitalityStr;
    private int[][] frozenPiece = new int[2][3];
    private String frozenPieceStr;

    private String unusedSpells;
    private String resultStr;

    public void initBoard() {
//        initializing parameter
        turnsNum = 1;
        movePlayerInt = 1;
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
        setFrozenPieceStr();

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
        turnsNum = 1;
        switch (m) {
            case "W": {
                movePlayerInt = 1;
                break;
            }
            case "B": {
                movePlayerInt = -1;
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
        if (vitalityNum != vitalityStr.indexOf("0")) {
            System.out.println("ERROR:<The  length of vitalityStr is less than number of piece!>");
        }
        if (frozenPiece[0][2] != 0)
            boardPiece[frozenPiece[0][0]][frozenPiece[0][1]].state = "f";
        if (frozenPiece[1][2] != 0)
            boardPiece[frozenPiece[1][0]][frozenPiece[1][1]].state = "f";
        playerPiece[0][2].spells = unusedSpells.substring(0, 4);
        playerPiece[1][2].spells = unusedSpells.substring(4, 8);

    }

    private void refreshBoardStr() {
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
        setFrozenPieceStr();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                boardTypeStr += boardPiece[j][i].getType();
                if (boardPiece[i][j].vitality != 0) vitalityStr += boardPiece[i][j].vitality;
            }
        }
        movePlayerInt *= -1;

        if (movePlayerInt == 1) boardStr += "W";
        else boardStr += "B";
        vitalityStr = (vitalityStr + "0000000000000000").substring(0, 16);
        boardStr = boardStr
                + boardTypeStr
                + vitalityStr
                + frozenPieceStr
                + playerPiece[0][2].spells
                + playerPiece[1][2].spells;
    }

    public String getBoardStr() {
        setResultStr(movePlayerInt);
        return boardStr + resultStr;
    }

    private void setResultStr(int movePlayerInt) {
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

    private void setFrozenPieceStr() {
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

    public boolean isAction(String actionStr) {
        boolean index = false;
        String action = actionStr.substring(0, 1);
        int fromX = Integer.parseInt(actionStr.substring(1, 2)) - 1;
        int fromY = Integer.parseInt(actionStr.substring(2, 3)) - 1;
        int toX = Integer.parseInt(actionStr.substring(3, 4)) - 1;
        int toY = Integer.parseInt(actionStr.substring(4, 5)) - 1;
        switch (action) {
            case "M": {
                Move mov = new Move();
                System.out.println(actionStr);
                print();
                if (mov.isMoved(movePlayerInt, boardPiece, fromX, fromY, toX, toY, emptyPiece)) {
                    refreshBoardStr();
                    index = true;
                }
                System.out.println("after");
                print();
                break;
            }
            case "A": {
                Attack atk = new Attack();
                System.out.println(actionStr);
                if (atk.isAttacked(movePlayerInt, boardPiece, fromX, fromY, toX, toY, emptyPiece)) {
                    refreshBoardStr();
                    index = true;
                }
                print();
                break;
            }
            case "F": {
                Freeze fre = new Freeze();
                System.out.println(actionStr);
                if (fre.isFreezed(movePlayerInt, boardPiece, fromX, fromY, emptyPiece)) {
                    if (movePlayerInt == 1) {
                        frozenPiece[1][0] = fromX;
                        frozenPiece[1][1] = fromX;
                        frozenPiece[1][2] = 3;
                    } else {
                        frozenPiece[0][0] = fromX;
                        frozenPiece[0][1] = fromX;
                        frozenPiece[0][2] = 3;
                    }
                    index = true;
                }
                break;
            }
            case "H": {
                Heal hea = new Heal();
                System.out.println(actionStr);
                if (hea.isHealed(movePlayerInt, boardPiece, fromX, fromY, emptyPiece)) {
                    refreshBoardStr();
                    index = true;
                }
                break;
            }
            case "T": {
                Teleport tel = new Teleport();
                System.out.println(actionStr);
                if (tel.isTeleported(movePlayerInt, boardPiece, fromX, fromY, toX, toY, emptyPiece)) {
                    refreshBoardStr();
                    index = true;
                }
                break;
            }
            case "R": {
                Revive rev = new Revive();
                System.out.println(actionStr);
                if (rev.isRevived(movePlayerInt, playerPiece, boardPiece, fromX, fromY, emptyPiece)) {
                    refreshBoardStr();
                    index = true;
                }
                break;
            }
            default:
                System.out.println("ERROR:<The action is invalid!>");


        }

        return index;
    }

    public Set getAttackCells(int x, int y) {
        Attack atk = new Attack();
        return atk.getAvailCells(boardPiece, x, y);
    }

    public Set getMoveCells(int x, int y) {
        Move mov = new Move();
        return mov.getAvailCells(boardPiece, x, y);

    }

    private void print() {
        System.out.println("*******************************");
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
        System.out.println("*******************************");
    }
}
