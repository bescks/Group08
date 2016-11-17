package it.polimi.chessofclans.Board;
import it.polimi.chessofclans.Pieces.*;
import java.util.Scanner;
/**
 * Created by lucio on 11/17/2016.
 */

public class BoardBasic {
    public BoardBasic(){
        turnsNum = 1;
        color = "white";
        pst[0]="000000".toCharArray();
        pst[1]="GK00sa".toCharArray();
        pst[2]="DS00km".toCharArray();
        pst[3]="MK00sd".toCharArray();
        pst[4]="AS00kg".toCharArray();
        pst[5]="000000".toCharArray();
        vitalities="5675434334345765";
        frozenPieces="000000";
        unusedSpells="FHRTFHRT";
        action="00000";

        pieces[1][0]= new Giant();
        pieces[2][0]= new Dragon();
        pieces[3][0]= new Mage();
        pieces[4][0]= new Archer();
        pieces[1][1]= new Knight();
        pieces[2][1]= new Squire();
        pieces[3][1]= new Knight();
        pieces[4][1]= new Squire();

        pieces[4][5]= new Giant();
        pieces[3][5]= new Dragon();
        pieces[2][5]= new Mage();
        pieces[1][5]= new Archer();
        pieces[4][4]= new Knight();
        pieces[3][4]= new Squire();
        pieces[2][4]= new Knight();
        pieces[1][4]= new Squire();
    }

    private int turnsNum;
    private String color;
    private char pst[][] = new char[6][6];
    private Piece pieces[][]= new Piece[6][6];
    private String vitalities;
    private String frozenPieces;
    private String unusedSpells;
    private String action;

    private char charPieceChosen = '0';
    private Piece piecechosen = new Piece();
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private char act;


    public void printBoard(){
        System.out.println("\nTurns: " + turnsNum);
        System.out.println(pst[0].toString());
        System.out.println(pst[1].toString());
        System.out.println(pst[2].toString());
        System.out.println(pst[3].toString());
        System.out.println(pst[4].toString());
        System.out.println(pst[5].toString());
        System.out.println("Vitalities: " + vitalities);
        System.out.println("Frozen pieces: " + frozenPieces);
        System.out.println("Spells unused: " + unusedSpells);
        if(color.compareTo("white")==0)System.out.println("It is white's turn!\n Actions:");
        else System.out.println("It is black's turn!\n Action:");
    }
    public void readAction(){
        Scanner sc = new Scanner(System.in);
        action = sc.nextLine();
    }
    public void choosePiece(){
        Scanner sc = new Scanner(System.in);
        boolean ctrl =false;
        do{
            ctrl = false;
            x1=action.charAt(1);
            y1=action.charAt(2);
            if(x1<0||x1>5||y1<0||y1>5){
                System.out.println("It's not on the board!\nRe-enter the Action:");;
                action = sc.nextLine();
                ctrl =true;
            }
            else if(pst[x1][y1]=='0'){
                System.out.println("There is no piece!\nRe-enter the Action:");
                action = sc.nextLine();
                ctrl =true;
            }
            else if(((color.compareTo("white")==0)&&(pst[x1][y1]>'Z'))||((color.compareTo("white")!=0)&&(pst[x1][y1]<'Z'))){
                System.out.print("It's not your piece!\nRe-enter the Action:");
                action = sc.nextLine();
                ctrl =true;
            }
        }while(ctrl);
        charPieceChosen = pst[x1][y1];
        piecechosen = pieces[x1][y1];
    }
    public void chooseAction(){
        Scanner sc = new Scanner(System.in);
        boolean ctrl = false;
        do{
            ctrl = false;
            act=action.charAt(0);
            if(act!='A'&&act!='M'&&act!='F'&&act!='H'&&act!='R'&&act!='T'){
                System.out.print("Action is not correct!\nRe-enter the Action:");
                action = sc.nextLine();
                ctrl =true;
            }
            else if((act=='F'||act=='H'||act=='R'||act=='T')&&(charPieceChosen!='M'&&charPieceChosen!='m')){
                System.out.print("This piece can not cast spells!\nRe-enter the Action:");
                action = sc.nextLine();
                ctrl =true;
            }
            else if (act=='A'&& (charPieceChosen=='M'||charPieceChosen=='m'||charPieceChosen=='s'||charPieceChosen=='S')){
                System.out.print("Squire or Mage can not attack!\nRe-enter the Action:");
                action = sc.nextLine();
                ctrl =true;
            }
        }while(ctrl);
    }
    public void chooseTarget(){
        boolean ctrl = false;
        Scanner sc = new Scanner(System.in);
        switch (act){
            case 'M':;{
                x2=action.charAt(3);
                y2=action.charAt(4);

            }break;
            case 'A': {
                do{
                    x2=action.charAt(3);
                    y2=action.charAt(4);
                    ctrl = attackRangeJudgement(piecechosen.getAttackDirections(),piecechosen.getAttackRange());
                    if(ctrl){
                        pieces[x2][y2].attacked(piecechosen.attack());
                        ctrl = false;
                    }
                    else{
                        System.out.print("Target not correct!\nRe-enter the Action:");
                        action = sc.nextLine();
                        ctrl =true;
                    }
                }while (ctrl);
            }break;
            case 'F': break;
            case 'H': break;
            case 'R': break;
            case 'T': break;
        }
    }
    private boolean attackRangeJudgement(char aD,int aR){
        boolean attackPossibility = false;
        int x;
        int y;
        int i;
        if(aD=='d'){
            if(x1>0&&x1<6&&y1>0&&y1<6){
                if(pieces[x1-1][y1-1].getColor()!='0'&&pieces[x1-1][y1-1].getColor()!=color.charAt(0)){
                    if(x2==(x1-1)&&y2==(y1-1)) attackPossibility=true;
                }
            }
            if(x1>=0&&x1<5&&y1>=0&&y1<5){
                if(pieces[x1+1][y1+1].getColor()!='0'&&pieces[x1+1][y1+1].getColor()!=color.charAt(0)){
                    if(x2==(x1+1)&&y2==(y1+1)) attackPossibility=true;
                }
            }
            if(x1>0&&x1<6&&y1>=0&&y1<5){
                if(pieces[x1-1][y1+1].getColor()!='0'&&pieces[x1-1][y1+1].getColor()!=color.charAt(0)){
                    if(x2==(x1-1)&&y2==(y1+1)) attackPossibility=true;
                }
            }
            if(x1>=0&&x1<5&&y1>0&&y1<6){
                if(pieces[x1+1][y1-1].getColor()!='0'&&pieces[x1+1][y1-1].getColor()!=color.charAt(0)){
                    if(x2==(x1+1)&&y2==(y1-1)) attackPossibility=true;
                }
            }
        }
        else {
            for(i=1;i<=aR;i++){
                x=x1-i;
                y=y1;
                if(x>=0){
                    if(pieces[x][y].getColor()!='0'&&pieces[x][y].getColor()!=color.charAt(0)) {
                        if (x2 == x && y2 == y) attackPossibility = true;
                        break;
                    }
                }
            }
            for(i=1;i<=aR;i++){
                x=x1+i;
                y=y1;
                if(x<6){
                    if(pieces[x][y].getColor()!='0'&&pieces[x][y].getColor()!=color.charAt(0)) {
                        if (x2 == x && y2 == y) attackPossibility = true;
                        break;
                    }
                }
            }
            for(i=1;i<=aR;i++){
                x=x1;
                y=y1-i;
                if(y>=0){
                    if(pieces[x][y].getColor()!='0'&&pieces[x][y].getColor()!=color.charAt(0)) {
                        if (x2 == x && y2 == y) attackPossibility = true;
                        break;
                    }
                }
            }
            for(i=1;i<=aR;i++){
                x=x1;
                y=y1+i;
                if(y<6){
                    if(pieces[x][y].getColor()!='0'&&pieces[x][y].getColor()!=color.charAt(0)) {
                        if (x2 == x && y2 == y) attackPossibility = true;
                        break;
                    }
                }
            }
        }
        return attackPossibility;
    }





    public int judge(){
        int w =0;
        int b =0;
        if(pst[0][0]<'Z' && pst[0][0]>='A')w++;
        if(pst[0][0]<'z' && pst[0][0]>='a')b++;
        if(pst[0][3]<'Z' && pst[0][3]>='A')w++;
        if(pst[0][3]<'z' && pst[0][3]>='a')b++;
        if(pst[5][2]<'Z' && pst[5][2]>='A')w++;
        if(pst[5][2]<'z' && pst[5][2]>='a')b++;
        if(pst[5][5]<'Z' && pst[5][5]>='A')w++;
        if(pst[5][5]<'z' && pst[5][5]>='a')b++;
        if(w>2)return 1;
        if(b>2)return 2;

        w=1;
        b=1;
        char f1='0';
        char f2='0';
        int x1=0;
        int x2=0;
        int y1=0;
        int y2=0;
        if(frozenPieces.charAt(0)!='0'){
            x1=frozenPieces.charAt(0)-1;
            y1=frozenPieces.charAt(1)-1;
            f1=pst[x1][y1];
            pst[x1][y1]='0';
        }
        if(frozenPieces.charAt(3)!='0'){
            x2=frozenPieces.charAt(3)-1;
            y2=frozenPieces.charAt(4)-1;
            f2=pst[x2][y2];
            pst[x2][y2]='0';
        }

        for(int i=0;i<6;i++) {
            for(int j =0;j<6;j++){
                if(pst[i][j]!='0'){
                    if (pst[i][j]< 'Z') b = 0;
                    if (pst[i][j]> 'Z') w = 0;
                }
            }
        }
        if(w==1 && b==1)return 3;
        if(w==1) return 1;
        if(b==1) return 2;

        return 0;
    }
    public void turnsOver(){
        if(color.compareTo("white")==0){
            color = "black";
        }
        else {
            color = "white";
            turnsNum = turnsNum+1;
        }
    }


}
