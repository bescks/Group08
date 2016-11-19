package it.polimi.chessofclans.board;
import it.polimi.chessofclans.pieces.*;
import java.util.Scanner;
/**
 * Created by lucio on 11/17/2016.
 */

public class BoardBasic {
    public BoardBasic(){
        turnsNum = 1;
        color = "white";

        piecesAliveW[0]= new Giant("white",2,1);
        piecesAliveW[1]= new Dragon("white",3,1);
        piecesAliveW[2]= new Mage("white",4,1);
        piecesAliveW[3]= new Archer("white",5,2);
        piecesAliveW[4]= new Knight("white",2,2);
        piecesAliveW[5]= new Squire("white",3,2);
        piecesAliveW[6]= new Knight("white",4,2);
        piecesAliveW[7]= new Squire("white",5,2);

        piecesAliveB[0]= new Giant("black",5,6);
        piecesAliveB[1]= new Dragon("black",4,6);
        piecesAliveB[2]= new Mage("black",3,6);
        piecesAliveB[3]= new Archer("black",2,6);
        piecesAliveB[4]= new Knight("black",5,5);
        piecesAliveB[5]= new Squire("black",4,5);
        piecesAliveB[6]= new Knight("black",3,5);
        piecesAliveB[7]= new Squire("black",2,5);

        piecesAliveNumW=8;
        piecesAliveNumB=8;
        piecesDeadNumW=0;
        piecesDeadNumB=0;

        boardState[0]="000000".toCharArray();
        boardState[1]="GK00sa".toCharArray();
        boardState[2]="DS00km".toCharArray();
        boardState[3]="MK00sd".toCharArray();
        boardState[4]="AS00kg".toCharArray();
        boardState[5]="000000".toCharArray();

        boardCells[0][0].setCellState('s');
        boardCells[0][3].setCellState('s');
        boardCells[5][2].setCellState('s');
        boardCells[5][5].setCellState('s');

        /*
        vitalities="5675434334345765";
        frozenPieces="000000";
        unusedSpells="FHRTFHRT";
        action="00000";
        */
    }


    private int turnsNum;
    private String color;
    private int piecesAliveNumW;
    private int piecesAliveNumB;
    private int piecesDeadNumW;
    private int piecesDeadNumB;
    private Piece piecesAliveW[]=new Piece[8];
    private Piece piecesAliveB[]=new Piece[8];
    private Piece piecesDeadW[]=new Piece[8];
    private Piece piecesDeadB[]=new Piece[8];
    private char boardState[][] = new char[6][6];
    private Cell boardCells[][] = new Cell[6][6];


    private String action;
    private char act;
    private int x1;
    private int y1;
    private Piece piecechosen;
    private int x2;
    private int y2;
    private Piece pieceTarget;
    private Piece pieceTargetDead;
    /*
    private Piece pieces[][]= new Piece[6][6];
    private String vitalities;
    private String frozenPieces;
    private String unusedSpells;
    */

    private void readAction(){
        Scanner sc = new Scanner(System.in);
        action = sc.nextLine();
        act=action.charAt(0);
        x1=action.charAt(1);
        y1=action.charAt(2);
        x2=action.charAt(3);
        y2=action.charAt(4);
    }

    public void actionExecution(){
        boolean ctrl;
        do{
            readAction();
            ctrl = false;
            switch (act){
                case 'M':{

                }break;
                case 'A':{

                }break;
                case 'F':{
                    ctrl=choosePiece();
                    if(ctrl){
                        if(!piecechosen.getType().equals("Mage")) {
                            System.out.println("Only Mage can cast spell freeze!");
                            ctrl = false;
                        }
                    }
                    else System.out.println("It's not your piece!");
                    if(ctrl) ctrl=excuteFreeze();
                    ctrl = !ctrl;
                }break;
                case 'H':{
                    ctrl=choosePiece();
                    if(ctrl){
                        if(!piecechosen.getType().equals("Mage")) {
                            System.out.println("Only Mage can cast spell heal!");
                            ctrl = false;
                        }
                    }
                    else System.out.println("It's not your piece!");
                    if(ctrl)ctrl = excuteHeal();
                    ctrl = !ctrl;
                }break;
                case 'R':{
                    ctrl=choosePiece();
                    if(ctrl){
                        if(!piecechosen.getType().equals("Mage")) {
                            System.out.println("Only Mage can cast spell revive!");
                            ctrl = false;
                        }
                    }
                    else System.out.println("It's not your piece!");
                    if(ctrl)ctrl = excuteRevive();
                    if(ctrl){
                        piecechosen=pieceTargetDead;
                        excutePieceChoosenRevived();
                        excuteCombat();
                    }
                    ctrl = !ctrl;
                }break;
                case 'T':{
                    ctrl=choosePiece();
                    if(ctrl){
                        pieceTarget=piecechosen;
                        ctrl = excuteTeleport();
                    }
                    else System.out.println("It's not your piece!");
                    if(ctrl){
                        piecechosen=pieceTarget;
                        excuteCombat();
                    }
                    ctrl = !ctrl;
                }break;
                default:{
                    System.out.println("It's not correct action!");
                }break;
            }
        }while(ctrl);
    }

    private boolean choosePiece(){
        boolean ctrl = false;
        if (color.equals("white")) {
            for (int i = 0; i < piecesAliveNumW; i++) {
                if (x1 == piecesAliveW[i].getPositionX() && y1 ==piecesAliveW[i].getPositionY()){
                    piecechosen = piecesAliveW[i];
                    ctrl = true;
                    break;
                }
            }
        }
        else {
            for (int i = 0; i < piecesAliveNumB; i++) {
                if (x1 == piecesAliveB[i].getPositionX() && y1 ==piecesAliveB[i].getPositionY()){
                    piecechosen = piecesAliveB[i];
                    ctrl = true;
                    break;
                }
            }
        }
        return ctrl;
    }

    private void excutePieceChoosenDead(){
        int deadNum=8;
        if(color.equals("white")){
            for(int i=0;i<piecesAliveNumW-1;i++){
                if((piecechosen.getPositionX()==piecesAliveW[i].getPositionX()&&piecechosen.getPositionY()==piecesAliveW[i].getPositionY())||i>deadNum) {
                    piecesAliveW[i]=piecesAliveW[i+1];
                    deadNum=i;
                }
            }
            piecesAliveNumW--;
            piecesDeadW[piecesDeadNumW]=piecechosen;
            piecesDeadNumW++;
            boardState[piecechosen.getPositionX()-1][piecechosen.getPositionY()-1]=0;
        }
        else{
            for(int i=0;i<piecesAliveNumB-1;i++){
                if((piecechosen.getPositionX()==piecesAliveB[i].getPositionX()&&piecechosen.getPositionY()==piecesAliveB[i].getPositionY())||i>deadNum) {
                    piecesAliveB[i]=piecesAliveB[i+1];
                    deadNum=i;
                }
            }
            piecesAliveNumB--;
            piecesDeadB[piecesDeadNumB]=piecechosen;
            piecesDeadNumB++;
            boardState[piecechosen.getPositionX()-1][piecechosen.getPositionY()-1]=0;
        }
    }

    private void excutePieceTargetDead(){
        int deadNum=8;
        if(color.equals("black")){
            for(int i=0;i<piecesAliveNumW-1;i++){
                if((pieceTarget.getPositionX()==piecesAliveW[i].getPositionX()&&pieceTarget.getPositionY()==piecesAliveW[i].getPositionY())||i>deadNum) {
                    piecesAliveW[i]=piecesAliveW[i+1];
                    deadNum=i;
                }
            }
            piecesAliveNumW--;
            piecesDeadW[piecesDeadNumW]=pieceTarget;
            piecesDeadNumW++;
            boardState[pieceTarget.getPositionX()-1][pieceTarget.getPositionY()-1]=0;
        }
        else{
            for(int i=0;i<piecesAliveNumB-1;i++){
                if((pieceTarget.getPositionX()==piecesAliveB[i].getPositionX()&&pieceTarget.getPositionY()==piecesAliveB[i].getPositionY())||i>deadNum) {
                    piecesAliveB[i]=piecesAliveB[i+1];
                    deadNum=i;
                }
            }
            piecesAliveNumB--;
            piecesDeadB[piecesDeadNumB]=pieceTarget;
            piecesDeadNumB++;
            boardState[pieceTarget.getPositionX()-1][pieceTarget.getPositionY()-1]=0;
        }
    }

    private void excutePieceChoosenRevived(){
        int revivedNum=8;
        if(color.equals("white")){
            for(int i=0;i<piecesDeadNumW-1;i++){
                if((piecechosen.getType().equals(piecesAliveW[i].getType()))||i>revivedNum) {
                    piecesAliveW[i]=piecesAliveW[i+1];
                    revivedNum=i;
                }
            }
            piecesAliveNumW++;
            piecesAliveW[piecesAliveNumW]=piecechosen;
            piecesDeadNumW--;
            boardState[piecechosen.getPositionX()-1][piecechosen.getPositionY()-1]=piecechosen.getType().charAt(0);
        }
        else{
            for(int i=0;i<piecesDeadNumB-1;i++){
                if((piecechosen.getType().equals(piecesAliveB[i].getType()))||i>revivedNum) {
                    piecesDeadB[i]=piecesDeadB[i+1];
                    revivedNum=i;
                }
            }
            piecesAliveNumB++;
            piecesAliveB[piecesAliveNumB]=piecechosen;
            piecesDeadNumB--;
            boardState[piecechosen.getPositionX()-1][piecechosen.getPositionY()-1]=piecechosen.getType().toLowerCase().charAt(0);
        }
    }

    private void excuteCombat(){
        boolean ctrl = false;
        int combatResult;
        if(color.equals("white")){
            for (int i = 0; i < piecesAliveNumB; i++) {
                if (x2 == piecesAliveB[i].getPositionX() && y2 ==piecesAliveB[i].getPositionY()){
                    pieceTarget = piecesAliveB[i];
                    ctrl = true;
                    break;
                }
            }
        }
        else {
            for (int i = 0; i < piecesAliveNumW; i++) {
                if (x2 == piecesAliveW[i].getPositionX() && y2 == piecesAliveW[i].getPositionY()) {
                    pieceTarget = piecesAliveW[i];
                    ctrl = true;
                    break;
                }
            }
        }
        if(ctrl) {
            combatResult= piecechosen.inCombatWith(pieceTarget);
            if(combatResult==0){
                excutePieceChoosenDead();
                excutePieceTargetDead();
            }
            else if(combatResult==1) excutePieceChoosenDead();
            else excutePieceTargetDead();
        }
    }

    private boolean excuteFreeze(){
        boolean ctrl =false;
        if(color.equals("white")){
            for (int i = 0; i < piecesAliveNumB; i++) {
                if (x2 == piecesAliveB[i].getPositionX() && y2 ==piecesAliveB[i].getPositionY()){
                    pieceTarget = piecesAliveB[i];
                    ctrl = true;
                    break;
                }
            }
        }
        else{
            for (int i = 0; i < piecesAliveNumW; i++) {
                if (x2 == piecesAliveW[i].getPositionX() && y2 ==piecesAliveW[i].getPositionY()){
                    pieceTarget = piecesAliveW[i];
                    ctrl = true;
                    break;
                }
            }
        }
        if(!ctrl){
            System.out.println("You can only choose opponent's piece to freeze!");
            return false;
        }
        else if(pieceTarget.getType().equals("Mage")){
            System.out.println("You can not freeze Mage!");
            return false;
        }
        else if(boardCells[pieceTarget.getPositionX()-1][pieceTarget.getPositionY()-1].getCellState()=='s'){
            System.out.println("You can not freeze piece in special cell!");
            return false;
        }
        else {
            Mage m = (Mage) piecechosen;
            return m.freeze(pieceTarget);
        }
    }

    private boolean excuteHeal(){
        boolean ctrl = false;
        if (color.equals("white")) {
            for (int i = 0; i < piecesAliveNumW; i++) {
                if (x2 == piecesAliveW[i].getPositionX() && y2 ==piecesAliveW[i].getPositionY()){
                    pieceTarget = piecesAliveW[i];
                    ctrl = true;
                    break;
                }
            }
        }
        else {
            for (int i = 0; i < piecesAliveNumB; i++) {
                if (x2 == piecesAliveB[i].getPositionX() && y2 ==piecesAliveB[i].getPositionY()){
                    pieceTarget = piecesAliveB[i];
                    ctrl = true;
                    break;
                }
            }
        }
        if(!ctrl){
            System.out.println("You can only choose your own piece to heal!");
            return false;
        }
        else if(pieceTarget.getType().equals("Mage")){
            System.out.println("You can not heal Mage!");
            return false;
        }
        else if(boardCells[pieceTarget.getPositionX()-1][pieceTarget.getPositionY()-1].getCellState()=='s'){
            System.out.println("You can not heal piece in special cell!");
            return false;
        }
        else {
            Mage m = (Mage) piecechosen;
            return m.heal(pieceTarget);
        }
    }

    private boolean excuteRevive(){
        boolean ctrl = false;
        if (color.equals("white")) {
            for (int i = 0; i < piecesDeadNumW; i++) {
                if (x2 == piecesDeadW[i].getInitialPositionX() && y2 ==piecesDeadW[i].getInitialPositionY()){
                    pieceTargetDead = piecesDeadW[i];
                    ctrl = true;
                    break;
                }
            }
        }
        else {
            for (int i = 0; i < piecesDeadNumB; i++) {
                if (x2 == piecesDeadB[i].getInitialPositionX() && y2 ==piecesDeadB[i].getInitialPositionY()){
                    pieceTargetDead = piecesDeadB[i];
                    ctrl = true;
                    break;
                }
            }
        }
        if(!ctrl){
            System.out.println("That piece is still alive!");
            return false;
        }
        ctrl = false;
        if (color.equals("white")) {
            for (int i = 0; i < piecesAliveNumW; i++) {
                if (x2 == piecesAliveW[i].getPositionX() && y2 ==piecesAliveW[i].getPositionY()){
                    pieceTarget = piecesAliveW[i];
                    ctrl = true;
                    break;
                }
            }
        }
        else {
            for (int i = 0; i < piecesAliveNumB; i++) {
                if (x2 == piecesAliveB[i].getPositionX() && y2 ==piecesAliveB[i].getPositionY()){
                    pieceTarget = piecesAliveB[i];
                    ctrl = true;
                    break;
                }
            }
        }
        if(ctrl){
            System.out.println("The initial position is occupied by your own piece!");
            return false;
        }
        else {
            Mage m = (Mage) piecechosen;
            return m.revive(pieceTargetDead);
        }
    }

    private boolean excuteTeleport(){
        boolean ctrl = false;
        if (color.equals("white")) {
            for (int i = 0; i < piecesAliveNumW; i++) {
                if (piecesAliveW[i].getType().equals("Mage"))
                {
                    piecechosen=piecesAliveW[i];
                    ctrl = true;
                }
            }
        }
        else {
            for (int i = 0; i < piecesAliveNumB; i++) {
                if (piecesAliveB[i].getType().equals("Mage")){
                    piecechosen=piecesAliveB[i];
                    ctrl = true;
                }
            }
        }
        if(!ctrl){
            System.out.println("You don't have a mage to cast spell teleport!");
            return false;
        }
        else if(pieceTarget.getType().equals("Mage")){
            System.out.println("You can not teleport mage!");
            return false;
        }
        else if(boardCells[x1-1][y1-1].getCellState()=='s'){
            System.out.println("You can not teleport piece in special cell!");
            return false;
        }
        if ((color.equals("white")&&boardState[x2-1][y2-1]!='0'&&boardState[x2-1][y2-1]<'Z')||(color.equals("black")&&boardState[x2-1][y2-1]>'Z')){
            System.out.println("You can not teleport your piece onto your own piece!");
            return false;
        }
        else if((color.equals("white")&&boardState[x2-1][y2-1]=='m')||(color.equals("black")&&boardState[x2-1][y2-1]=='M')){
            System.out.println("You can not teleport your piece to opponent's mage!");
            return false;
        }
        else if(boardCells[x2-1][y2-1].getCellState()=='s'){
            System.out.println("You can not teleport your piece to special cell!");
            return false;
        }
        else{
            Mage m = (Mage) piecechosen;
            return m.teleport(pieceTarget,x2,y2);
        }
    }

    public boolean judgeVictory(){
        int w =0;
        int b =0;
        if(boardState[0][0]=='1')w++;
        if(boardState[0][0]=='2')b++;
        if(boardState[0][3]=='1')w++;
        if(boardState[0][3]=='2')b++;
        if(boardState[5][2]=='1')w++;
        if(boardState[5][2]=='2')b++;
        if(boardState[5][5]=='1')w++;
        if(boardState[5][5]=='2')b++;
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
                if (boardState[i][j]=='1') b = 0;
                if (boardState[i][j]=='2') w = 0;
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

    public void turnsOver(){
        if(color.equals("white")){
            color = "black";
        }
        else {
            color = "white";
            turnsNum = turnsNum+1;
        }
    }
}
