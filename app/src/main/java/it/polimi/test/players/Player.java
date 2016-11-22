package it.polimi.test.players;
import it.polimi.test.pieces.*;
import it.polimi.test.boards.*;
/**
 * Created by lucio on 11/21/2016.
 */

public class Player {
    public Player(String s) {
        name = s;
        piecesAliveNum = 8;
        piecesDeadNum = 0;
        piecesAlive = new Piece[8];
        piecesDead = new Piece[8];

        if (s.equals("white")) {
            piecesAlive[0] = new Giant("white", 2, 1);
            piecesAlive[1] = new Dragon("white", 3, 1);
            piecesAlive[2] = new Mage("white", 4, 1);
            piecesAlive[3] = new Archer("white", 5, 1);
            piecesAlive[4] = new Knight("white", 2, 2);
            piecesAlive[5] = new Squire("white", 3, 2);
            piecesAlive[6] = new Knight("white", 4, 2);
            piecesAlive[7] = new Squire("white", 5, 2);
        } else {
            piecesAlive[0] = new Giant("black", 5, 6);
            piecesAlive[1] = new Dragon("black", 4, 6);
            piecesAlive[2] = new Mage("black", 3, 6);
            piecesAlive[3] = new Archer("black", 2, 6);
            piecesAlive[4] = new Knight("black", 5, 5);
            piecesAlive[5] = new Squire("black", 4, 5);
            piecesAlive[6] = new Knight("black", 3, 5);
            piecesAlive[7] = new Squire("black", 2, 5);
        }
    }

    public void readPlayer(){
        piecesAliveNum = 0;
        piecesDeadNum = 0;
        piecesAlive = new Piece[8];
        piecesDead = new Piece[8];
    }

    public void addPiece(Piece p){
        if(p.getState()=='d'){
            piecesDead[piecesDeadNum]=p;
            piecesDeadNum++;
        }
        else {
            piecesAlive[piecesAliveNum]=p;
            piecesAliveNum++;
        }
    }

    String name;
    private int piecesAliveNum;
    private int piecesDeadNum;
    private Piece piecesAlive[];
    private Piece piecesDead[];

    public int getPiecesAliveNum(){
        return piecesAliveNum;
    }

    public Piece[] getPiecesAlive(){
        return piecesAlive;
    }

    public int getPiecesDeadNum(){
        return piecesDeadNum;
    }

    public Piece[] getPiecesDead(){
        return piecesDead;
    }

    public Piece choosePieceAlive(int x, int y) {
        Piece p = new Piece();
        for (int i = 0; i < piecesAliveNum; i++) {
            if (x == piecesAlive[i].getPositionX() && y == piecesAlive[i].getPositionY())
                p = piecesAlive[i];
        }
        return p;
    }

    public Piece choosePieceDead(int x, int y){
        Piece p = new Piece();
        for (int i = 0; i < piecesAliveNum; i++) {
            if (x == piecesAlive[i].getPositionX() && y == piecesAlive[i].getPositionY())
                p = piecesAlive[i];
        }
        if(p.getPieceEnable()){
            System.out.println("The initial position is occupied by your own piece!");
            p = new Piece();
            return p;
        }
        else p = new Piece();
        for (int i = 0; i < piecesDeadNum; i++) {
            if (x == piecesDead[i].getInitialPositionX() && y == piecesDead[i].getInitialPositionY())
                p = piecesDead[i];
        }
        return p;
    }

    public Mage chooseMage(){
        Mage m = new Mage();
        for(int i=0;i < piecesAliveNum; i ++){
            if(piecesAlive[i].getType().equals("Mage"))
                m=(Mage) piecesAlive[i];
        }
        return m;
    }

    public void pieceDie(Piece p) {
        int index = piecesAliveNum;
        for (int i = 0; i < piecesAliveNum - 1; i++) {
            if ((p.getPositionX() == piecesAlive[i].getPositionX() && p.getPositionY() == piecesAlive[i].getPositionY()) || i > index) {
                piecesAlive[i] = piecesAlive[i + 1];
                index = i;
            }
        }
        piecesDead[piecesDeadNum] = p;
        piecesAliveNum--;
        piecesDeadNum++;
    }

    public void pieceRevived(Piece p) {
        int index = piecesDeadNum;
        for (int i = 0; i < piecesDeadNum - 1; i++) {
            if ((p.getType().equals(piecesDead[i].getType())) || i > index) {
                piecesDead[i] = piecesDead[i + 1];
                index = i;
            }
        }
        piecesAlive[piecesAliveNum] = p;
        piecesAliveNum++;
        piecesDeadNum--;
    }
}
