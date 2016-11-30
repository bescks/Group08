package it.polimi.group08.players;

import it.polimi.group08.pieces.Archer;
import it.polimi.group08.pieces.Dragon;
import it.polimi.group08.pieces.Giant;
import it.polimi.group08.pieces.Knight;
import it.polimi.group08.pieces.Mage;
import it.polimi.group08.pieces.Piece;
import it.polimi.group08.pieces.Squire;

/**
 * Created by lucio on 11/21/2016.
 * Player class exists for Object-Oriented design
 * This class provides pieces list to ease operations for piece select, piece management
 */

public class Player {
    //Constructor is to initialize the player
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

    //readPlayer is to indicate a new player is going to be read, the pieces list must be empty
    public void readPlayer(){
        piecesAliveNum = 0;
        piecesDeadNum = 0;
        piecesAlive = new Piece[8];
        piecesDead = new Piece[8];
    }

    //addPiece is to add new piece to a player's pieces list
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


     // Each player has a name(white or black), two pieces list(alive or dead), two pieces number(alive or dead)

    String name;
    private int piecesAliveNum;
    private int piecesDeadNum;
    private Piece piecesAlive[];
    private Piece piecesDead[];

    //the following four methods are basic "get" method
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


    /**
     * printPieceslist is used for test output
    public void printPieceslist(){
        System.out.println("Color: " + name);
        for (int i = 0; i < piecesAliveNum; i++) {
            System.out.print("Piece Alive no."+i+" "+piecesAlive[i].getType()+" "+piecesAlive[i].getState()+" "+piecesAlive[i].getVitality()+" "+piecesAlive[i].getPositionX()+","+piecesAlive[i].getPositionY());
            if(piecesAlive[i].getType().equals("Mage")){
                Mage m = (Mage)piecesAlive[i];System.out.print(" "+m.getSpellsUnused());
            }
            System.out.println();
        }
        for (int i = 0; i < piecesDeadNum; i++) {
            System.out.print("Piece Dead no."+i+" "+piecesDead[i].getType()+" "+piecesDead[i].getState()+" "+piecesDead[i].getVitality()+" "+piecesDead[i].getPositionX()+","+piecesDead[i].getPositionY());
            if(piecesDead[i].getType().equals("Mage")){
                Mage m = (Mage)piecesDead[i];System.out.print(" "+m.getSpellsUnused());
            }
            System.out.println();
        }
    }
    */

    //choosePieceAlive is to select a piece according to its position, in order to act
    public Piece choosePieceAlive(int x, int y) {
        Piece p = new Piece();
        for (int i = 0; i < piecesAliveNum; i++) {
            if (piecesAlive[i].getType().equals("Castle")&&x == piecesAlive[i].getPositionX() && y == piecesAlive[i].getPositionY()){
                p = piecesAlive[i];
                return p;
            }
        }
        for (int i = 0; i < piecesAliveNum; i++) {
            if (x == piecesAlive[i].getPositionX() && y == piecesAlive[i].getPositionY())
                p = piecesAlive[i];
        }
        return p;
    }

    //choosePieceDead is to select a piece dead according to its initial position in order to be revived
    public Piece choosePieceDead(int x, int y){
        Piece p = new Piece();
        for (int i = 0; i < piecesDeadNum; i++) {
            if (x == piecesDead[i].getInitialPositionX() && y == piecesDead[i].getInitialPositionY())
                p = piecesDead[i];
        }
        return p;
    }

    //chooseTwin is to select a twin dead of knight or squire to be revived in its twin's cell
    public Piece chooseTwin(int x, int y){
        Piece p = new Piece();
        String name="";
        for (int i = 0; i < piecesAliveNum; i++) {
            if (x == piecesAlive[i].getInitialPositionX() && y == piecesAlive[i].getInitialPositionY())
                name = piecesAlive[i].getType();
        }
        for (int i = 0; i < piecesDeadNum; i++) {
            if (name.equals(piecesDead[i].getType()))
                p = piecesDead[i];
        }
        return p;
    }

    //chooseMage is to judge mage is alive and select it to cast spell
    public Mage chooseMage(){
        Mage m = new Mage();
        for(int i=0;i < piecesAliveNum; i ++){
            if(piecesAlive[i].getType().equals("Mage"))
                m=(Mage) piecesAlive[i];
        }
        return m;
    }

    //chooseMageDead is to choose dead mage to refresh board recording unusedSpells
    public Mage chooseMageDead(){
        Mage m = new Mage();
        for(int i=0;i < piecesDeadNum; i ++){
            if(piecesDead[i].getType().equals("Mage"))
                m=(Mage) piecesDead[i];
        }
        return m;
    }

    //pieceDie deal the case that a piece die, then it will go to dead list from alive list
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

    //pieceRevived deal the case that a piece is revived, then it will go to alive list from dead list
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
