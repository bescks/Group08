package it.polimi.test;
import it.polimi.test.boards.*;
import it.polimi.test.pieces.*;
import it.polimi.test.players.*;
import java.util.Scanner;
/**
 * Created by lucio on 11/21/2016.
 */

public class Test {
    public static void main(String [] args) {
        Player white = new Player("white");
        Player black = new Player("black");
        BoardBasic board = new BoardBasic();

        String action;
        char act;
        int x1, y1, x2, y2;
        Piece p1, p2;
        Mage m;
        boolean ctrl;

        while (board.judgeVictory()) {
            do {
                board.printBoard();
                Scanner sc = new Scanner(System.in);
                action = sc.nextLine();
                act = action.charAt(0);
                x1 = action.charAt(1) - 48;
                y1 = action.charAt(2) - 48;
                x2 = action.charAt(3) - 48;
                y2 = action.charAt(4) - 48;
                ctrl = true;

                switch (act) {
                    case 'M': {
                        if(board.getColor().equals("white")){
                            p1=white.choosePieceAlive(x1,y1);
                            p2=black.choosePieceAlive(x2,y2);
                            if(board.judgeMove(p1,x2,y2)&&p1.getState()!='f'){
                                p1.moveTo(x2,y2);
                                ctrl = false;
                                if(board.excuteCombat(p1,p2)==1)white.pieceDie(p1);
                                else if(board.excuteCombat(p1,p2)==2)black.pieceDie(p2);
                                else if(board.excuteCombat(p1,p2)==3){
                                    white.pieceDie(p1);
                                    black.pieceDie(p2);
                                }
                            }
                            else ctrl = true;
                        }
                        else {
                            p1=black.choosePieceAlive(x1,y1);
                            p2=white.choosePieceAlive(x2,y2);
                            if(board.judgeMove(p1,x2,y2)&&p1.getState()!='f'){
                                p1.moveTo(x2,y2);
                                ctrl = false;
                                if(board.excuteCombat(p1,p2)==1)black.pieceDie(p1);
                                else if(board.excuteCombat(p1,p2)==2)white.pieceDie(p2);
                                else if(board.excuteCombat(p1,p2)==3) {
                                    black.pieceDie(p1);
                                    white.pieceDie(p2);
                                }
                            }
                            else ctrl =true;
                        }
                        if(ctrl)System.out.println("Move failed!");
                    }
                    break;
                    case 'A': {
                        if(board.getColor().equals("white")){
                            p1=white.choosePieceAlive(x1,y1);
                            p2=black.choosePieceAlive(x2,y2);
                            if(board.judgeAttack(p1,x2,y2)&&p1.getState()!='f'){
                                ctrl = false;
                                if(p1.attack(p2))black.pieceDie(p2);
                            }
                            else ctrl = true;
                        }
                        else {
                            p1=black.choosePieceAlive(x1,y1);
                            p2=white.choosePieceAlive(x2,y2);
                            if(board.judgeAttack(p1,x2,y2)&&p1.getState()!='f'){
                                ctrl = false;
                                if(p1.attack(p2))white.pieceDie(p2);
                            }
                            else ctrl =true;
                        }
                        if(ctrl)System.out.println("Attack failed!");
                    }
                    break;
                    case 'F': {
                        if(board.getColor().equals("white")){
                            m=white.chooseMage();
                            p2=black.choosePieceAlive(x1,y1);
                        }
                        else {
                            m=black.chooseMage();
                            p2=white.choosePieceAlive(x1,y1);
                        }
                        ctrl = !board.excuteFreeze(m,p2);
                    }
                    break;
                    case 'H': {
                        if(board.getColor().equals("white")){
                            m=white.chooseMage();
                            p2=white.choosePieceAlive(x1,y1);
                        }
                        else {
                            m=black.chooseMage();
                            p2=black.choosePieceAlive(x1,y1);
                        }
                        ctrl = !board.excuteHeal(m,p2);
                    }
                    break;
                    case 'R': {
                        if(board.getColor().equals("white")){
                            m=white.chooseMage();
                            p2=white.choosePieceDead(x1,y1);
                            ctrl = !board.excuteRevive(m,p2);
                            if(!ctrl)white.pieceRevived(p2);
                        }
                        else {
                            m=black.chooseMage();
                            p2=black.choosePieceDead(x1,y1);
                            ctrl = !board.excuteRevive(m,p2);
                            if(!ctrl)white.pieceRevived(p2);
                        }


                    }
                    break;
                    case 'T': {
                        if(board.getColor().equals("white")){
                            m=white.chooseMage();
                            p1=white.choosePieceAlive(x1,y1);
                            p2=black.choosePieceAlive(x2,y2);
                            ctrl = !board.excuteTeleport(m,p1,x2,y2);
                            if(board.excuteCombat(p1,p2)==1)white.pieceDie(p1);
                            else if(board.excuteCombat(p1,p2)==2)black.pieceDie(p2);
                            else if(board.excuteCombat(p1,p2)==3){
                                white.pieceDie(p1);
                                black.pieceDie(p2);
                            }
                        }
                        else {
                            m=black.chooseMage();
                            p1=black.choosePieceAlive(x1,y1);
                            p2=white.choosePieceAlive(x2,y2);
                            ctrl = !board.excuteTeleport(m,p1,x2,y2);
                            if(board.excuteCombat(p1,p2)==1)black.pieceDie(p1);
                            else if(board.excuteCombat(p1,p2)==2)white.pieceDie(p2);
                            else if(board.excuteCombat(p1,p2)==3){
                                black.pieceDie(p1);
                                white.pieceDie(p2);
                            }
                        }
                    }
                    break;
                    default: {
                        System.out.println("It's not correct action!");
                    }
                    break;
                }
            } while (ctrl);
            board.refreshBoard(white,black);
            board.turnsOver();
        }

    }
}
