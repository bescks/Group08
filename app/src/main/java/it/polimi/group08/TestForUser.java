package it.polimi.group08;


import it.polimi.group08.boards.BoardBasic;
import it.polimi.group08.pieces.Mage;
import it.polimi.group08.pieces.Piece;
import it.polimi.group08.players.Player;

/**
 * Created by lucio on 11/28/2016.
 * TurnTest is the entrance of program, which provide a process for turn test
 */

public class TestForUser{
//    public static void main(String [] args) {
//        System.out.println(TestForUser.turnTest("W" +
//                "000000" +
//                "GK00sa" +
//                "DS00km" +
//                "MK00sd" +
//                "AS00kg" +
//                "000000" +
//                "5675434334345765" +
//                "000000" +
//                "FHRTFHRT" +
//                "M1343" +
//                "M5242" +
//                "A4353" +
//                "M4243" +
//                "F6400"));
//
//    }

        public  static String turnTest(String arg){
        //initialize players, board
        Player white = new Player("white");
        Player black = new Player("black");
        BoardBasic board = new BoardBasic();

        //initialize control parameters
        String action;
        char act;
        int x1, y1, x2, y2;
        int result;
        Piece p1, p2;
        Mage m;
        boolean ctrl;

        //provide a test case, initial board and actions

        arg=board.boardInitialize(arg,white,black);
        //board.refreshBoard(white,black);
        //board.printBoard();
        //white.printPieceslist();
        //black.printPieceslist();

        while(arg.length()>4) {
            //for each instruction read action and positions
            action = arg.substring(0,5);
            act = action.charAt(0);
            x1 = action.charAt(2) - 48;
            y1 = action.charAt(1) - 48;
            x2 = action.charAt(4) - 48;
            y2 = action.charAt(3) - 48;
            switch (act) {
                case 'M': {
                    if (board.getColor().equals("white")) {
                        // if white move a piece, select piece moved and select the piece in target cell(maybe not exist)
                        p1 = white.choosePieceAlive(x1, y1);
                        p2 = black.choosePieceAlive(x2, y2);
                        // judge move can happen or not, if true, handle combat case
                        if (board.judgeMove(p1, x2, y2) && p1.getState() != 'f') {
                            p1.moveTo(x2, y2);
                            result = board.excuteCombat(p1, p2);
                            if (result == 1) white.pieceDie(p1);
                            else if (result == 2) black.pieceDie(p2);
                            else if (result == 3) {
                                white.pieceDie(p1);
                                black.pieceDie(p2);
                            }
                        }
                    } else {
                        // if black move a piece, select piece moved and select the piece in target cell(maybe not exist)
                        p1 = black.choosePieceAlive(x1, y1);
                        p2 = white.choosePieceAlive(x2, y2);
                        // judge move can happen or not, if true, handle combat case
                        if (board.judgeMove(p1, x2, y2) && p1.getState() != 'f') {
                            p1.moveTo(x2, y2);
                            result = board.excuteCombat(p1, p2);
                            if (result == 1) black.pieceDie(p1);
                            else if (result == 2) white.pieceDie(p2);
                            else if (result == 3) {
                                black.pieceDie(p1);
                                white.pieceDie(p2);
                            }
                        }
                    }
                }break;
                case 'A': {
                    if (board.getColor().equals("white")) {
                        // if white attacks, select piece to attack, and select the target piece
                        p1 = white.choosePieceAlive(x1, y1);
                        p2 = black.choosePieceAlive(x2, y2);
                        // judge and excute attack
                        if (board.judgeAttack(p1, x2, y2) && p1.getState() != 'f') {
                            if (p1.attack(p2)) black.pieceDie(p2);
                        }
                    } else {
                        // if black attacks, select piece to attack, and select the target piece
                        p1 = black.choosePieceAlive(x1, y1);
                        p2 = white.choosePieceAlive(x2, y2);
                        // judge and excute attack
                        if (board.judgeAttack(p1, x2, y2) && p1.getState() != 'f') {
                            if (p1.attack(p2)) white.pieceDie(p2);
                        }
                    }
                }break;
                case 'F': {
                    // if freezes, select mage and target piece
                    if (board.getColor().equals("white")) {
                        m = white.chooseMage();
                        p2 = black.choosePieceAlive(x1, y1);
                    } else {
                        m = black.chooseMage();
                        p2 = white.choosePieceAlive(x1, y1);
                    }
                    // judge and excute freeze
                    board.excuteFreeze(m, p2);
                }break;
                case 'H': {
                    // if heal, select mage and target piece
                    if (board.getColor().equals("white")) {
                        m = white.chooseMage();
                        p2 = white.choosePieceAlive(x1, y1);
                    } else {
                        m = black.chooseMage();
                        p2 = black.choosePieceAlive(x1, y1);
                    }
                    // judge and excute heal
                    board.excuteHeal(m, p2);
                }break;
                case 'R': {
                    if(board.getColor().equals("white")){
                        //if white revives, select mage and target piece in alive list, if target exist, it can not be revived
                        m=white.chooseMage();
                        p1=white.choosePieceAlive(x1,y1);
                        if(p1.getPieceEnable())break;
                        // select target in dead list, and select opponent's piece in that initial position which is prepare for the case that combat may happen
                        p1=white.choosePieceDead(x1,y1);
                        p2=black.choosePieceAlive(x1,y1);
                        // if the dead piece doesn't exist, then its twin may be dead, select its twin
                        if(!p1.getPieceEnable())p1=white.chooseTwin(x1,y1);
                        // judge and excute revive
                        ctrl = board.excuteRevive(m,p1,x1,y1);
                        if(ctrl) white.pieceRevived(p1);
                        // judge and excute combat
                        if(p1.getState()=='n') {
                            result = board.excuteCombat(p1, p2);
                            if (result == 1) white.pieceDie(p1);
                            else if (result == 2) black.pieceDie(p2);
                            else if (result == 3) {
                                white.pieceDie(p1);
                                black.pieceDie(p2);
                            }
                        }
                    }
                    else {
                        //if black revives, select mage and target piece in alive list, if target exist, it can not be revived
                        m=black.chooseMage();
                        p1=black.choosePieceAlive(x1,y1);
                        if(p1.getPieceEnable())break;
                        // select target in dead list, and select opponent's piece in that initial position which is prepare for the case that combat may happen
                        p1=black.choosePieceDead(x1,y1);
                        p2=white.choosePieceAlive(x1,y1);
                        // if the dead piece doesn't exist, then its twin may be dead, select its twin
                        if(!p1.getPieceEnable())p1=black.chooseTwin(x1,y1);
                        // judge and excute revive
                        ctrl = board.excuteRevive(m,p1,x1,y1);
                        if(ctrl)black.pieceRevived(p1);
                        // judge and excute combat
                        if(p1.getState()=='n') {
                            result = board.excuteCombat(p1, p2);
                            if (result == 1) black.pieceDie(p1);
                            else if (result == 2) white.pieceDie(p2);
                            else if (result == 3) {
                                black.pieceDie(p1);
                                white.pieceDie(p2);
                            }
                        }
                    }
                }
                break;
                case 'T': {
                    if(board.getColor().equals("white")) {
                        // if white teleport, select mage, select piece to teleport, select opponent's piece maybe in the destination for combat
                        m = white.chooseMage();
                        p1 = white.choosePieceAlive(x1, y1);
                        p2 = black.choosePieceAlive(x2, y2);
                        //judge and excute teleport and combat
                        ctrl = board.excuteTeleport(m, p1, x2, y2);
                        if(ctrl) {
                            result = board.excuteCombat(p1, p2);
                            if (result == 1) white.pieceDie(p1);
                            else if (result == 2) black.pieceDie(p2);
                            else if (result == 3) {
                                white.pieceDie(p1);
                                black.pieceDie(p2);
                            }
                        }
                    }
                    else {
                        // if black teleport, select mage, select piece to teleport, select opponent's piece maybe in the destination for combat
                        m=black.chooseMage();
                        p1=black.choosePieceAlive(x1,y1);
                        p2=white.choosePieceAlive(x2,y2);
                        //judge and excute teleport and combat
                        ctrl = board.excuteTeleport(m,p1,x2,y2);
                        if(ctrl){
                            result = board.excuteCombat(p1, p2);
                            if (result == 1) black.pieceDie(p1);
                            else if (result == 2) white.pieceDie(p2);
                            else if (result == 3) {
                                black.pieceDie(p1);
                                white.pieceDie(p2);
                            }
                        }
                    }
                }
                break;
                default: {
                    System.out.println("It's not correct action!");
                }break;
            }
            //after each action, refresh board, turn over, judge victory
            board.refreshBoard(white, black);
            board.turnsOver();
            arg=arg.substring(5);
        }
        //at the end, refresh the board, print board, and return value
        board.refreshBoard(white, black);
            board.printBoard();
            return board.judgeVictory();
    }
}
