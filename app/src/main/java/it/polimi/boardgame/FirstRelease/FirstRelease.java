package it.polimi.boardgame.FirstRelease;
import java.util.Scanner;


public class FirstRelease {
    public static int judge(String board, String frozen){
        int w =0;
        int b =0;
        char s1=board.charAt(0);
        char s2=board.charAt(3);
        char s3=board.charAt(32);
        char s4=board.charAt(35);
        if(s1<'Z' && s1>='A')w++;
        if(s1<'z' && s1>='a')b++;
        if(s2<'Z' && s2>='A')w++;
        if(s2<'z' && s2>='a')b++;
        if(s3<'Z' && s3>='A')w++;
        if(s3<'z' && s3>='a')b++;
        if(s4<'Z' && s4>='A')w++;
        if(s4<'z' && s4>='a')b++;
        if(w>2)return 1;
        if(b>2)return 2;

        w=1;
        b=1;
        int f1=36;
        int f2=36;
        s1=frozen.charAt(0);
        s2=frozen.charAt(1);
        s3=frozen.charAt(3);
        s4=frozen.charAt(4);
        if(s1!='0')f1 = (s1-1)*6+(s2-1);
        if(s3!='0')f2 = (s3-1)*6+(s4-1);

        for(int i=0;i<36;i++){
            if(i!=f1 && i!=f2 && board.charAt(i)!='0') {
                if (board.charAt(i) < 'Z') b = 0;
                if (board.charAt(i) > 'Z') w = 0;
            }
        }
        if(w==1 && b==1)return 3;
        if(w==1) return 1;
        if(b==1) return 2;

        return 0;
    }
    /*public static char choosePiece(int p, String board, char color){
        char c=board.charAt(p);
        if(c == '0'){
            System.out.println("There is no piece!");

        }
        if(color == 'w')
        return c;
    }*/
    public static void main(String argv[]) {
        /*
        Giant G = new Giant('w',21);
        Dragon D = new Dragon('w',31)；
        Mage M = new Mage('w',41);
        Archer A = new Archer('w',51);
        Knight K1 = new Knight('w',22);
        Squire S1 = new Squire('w',32);
        Knight K2 = new Knight('w',42);
        Squire S2 = new Squire('w',52);

        Giant g = new Giant('b',56);
        Dragon d = new Dragon('b',46);
        Mage m = new Mage('b',36);
        Archer a = new Archer('b',26);
        Knight k1 = new Knight('b',55);
        Squire s1 = new Squire('b',45);
        Knight k2 = new Knight('b',35);
        Squire s2 = new Squire('b',25);
        */
        BoardState turn = new BoardState();

        int win = 0;
        int p = 0;
        char c1 = '0';
        char c2 = '0';
        char pi = '0';

        Scanner sc = new Scanner(System.in);
        String action;

        System.out.print("Game starts!");
        while (win == 0) {
            char color = turn.getColor();
            int turnsNum = turn.getTurnsNum();
            String board = turn.getBoard();
            String frozen = turn.getFrozen();
            String vitalities = turn.getVitalities();
            String spells = turn.getSpells();

            System.out.println("\nTurns: " + turnsNum);
            System.out.println(board.substring(0,6));
            System.out.println(board.substring(6,12));
            System.out.println(board.substring(12,18));
            System.out.println(board.substring(18,24));
            System.out.println(board.substring(24,30));
            System.out.println(board.substring(30));
            System.out.println("Vitalities: " + vitalities);
            System.out.println("Frozen: " + frozen);
            System.out.println("Spells: " + spells);
            if(color == 'w')System.out.println("It is white's turn!\n Actions:");
            else System.out.println("It is black's turn!\n Actions:");
            action = sc.nextLine();

            c1=action.charAt(1);
            c2=action.charAt(2);
            p=(c1-1)*6+(c2-1);

            //pi=choosePiece(p, board, color);
            //chooseAction();
            //chooseTarget(int p);
            win = judge(turn.getBoard(), turn.getFrozen());
        }
        if(win == 1) System.out.print("White wins!");
        if(win == 2) System.out.print("Black wins!");
        if(win == 3) System.out.print("Tie!");
    }
}
/*
 class Piece {
     public Piece(){

     }
     public Piece(int v,int mR, char mD, char mT, int aR, int aS, char aD){
         this.vitality = v;
         this.moveRange = mR;
         this.moveDirections = mD;
         this.moveType = mT;
         this.attackRange = aR;
         this.attackStrength = aS;
         this.attackDirections = aD;
     }
     private int vitality;
     private int moveRange;
     private char moveDirections;
     private char moveType;
     private int attackRange;
     private int attackStrength;
     private char attackDirections;
     public void move(int from, int to){

    }
    public void attacked (int damage){

    }
    public void healed (){

    }
    public void frozed(){

    }
    public void teleported(int to){

    }
    public void inCombat(int v,int s){

    }
}
 class Giant extends Piece {
     public Giant(char c, int p){
         super(5,2,'s','w',1,4,'s');
         this.color = c;
         this.position = p;
         this.state = 'n';
     }
     private char color;
     private char state;
     private int position;
     public void attack(int from, int to){

    }
}
 class Dragon extends Piece {
     public Dragon(char c, int p){
         super(6,3,'s','f',2,3,'s');
         this.color = c;
         this.position = p;
         this.state = 'n';
     }
     private char color;
     private char state;
     private int position;
     public void attack(int from, int to){

    }
}
 class Mage extends Piece {
     public Mage(char c, int p){
         super(7,1,'a','w',0,2,'n');
         this.color = c;
         this.position = p;
         this.state ='n';
         this.spellFroze = 1;
         this.spellHeal = 1;
         this.spellRevive = 1;
         this.spellTeleport = 1;
     }
     private char color;
     private char state;
     private int position;
    private int spellFroze;
    private int spellHeal;
    private int spellRevive;
    private int spellTeleport;
    public void froze(int p){

    }
    public void heal(int p){

    }
    public void revive(int p){

    }
    public void teleport(int from, int to){

    }
}
 class Archer extends Piece {
     public Archer(char c, int p){
         super(5,2,'a','w',3,2,'s');
         this.color = c;
         this.position = p;
         this.state = 'n';
     }
     private char color;
     private char state;
     private int position;
    public void attack(int from, int to){

    }
}
 class Squire extends Piece {
     public Squire(char c, int p){
         super(3,1,'s','w',0,1,'n');
         this.color = c;
         this.position = p;
         this.state = 'n';
     }
     private char color;
     private char state;
     private int position;
 }
 class Knight extends Piece {
     public Knight(char c, int p){
         super(4,1,'a','w',1,2,'d');
         this.color = c;
         this.position = p;
         this.state = 'n';
     }
     private char color;
     private char state;
     private int position;
    public void attack(int from, int to){

    }
}
*/
 class BoardState{
     public BoardState(){
        this.color = 'w';
        this.turnsNum = 1;
        this.board =
                          "000000"
                        + "GK00sa"
                        + "DS00km"
                        + "MK00sd"
                        + "AS00kg"
                        + "000000";
        this.vitalities = "5675434334345765";
        this.frozenPieces = "000000";
        this.spells = "FHRTFHRT";
     }
     private char color;
     private int turnsNum;
     private String board;
     private String vitalities;
     private String frozenPieces;
     private String spells;

     public char getColor(){
         return this.color;
     }
     public int getTurnsNum(){
         return this.turnsNum;
     }
     public String getBoard(){
         return this.board;
     }
     public String getVitalities() {
         return this.vitalities;
     }
     public String getFrozen() {
         return this.frozenPieces;
     }
     public String getSpells(){
         return this.spells;
     }
     public void changeColor(){
         if(this.color == 'w')this.color ='b';
         else if(this.color == 'b')this.color = 'w';
     }
     public void changeTurnsNum(){
         this.turnsNum++;
     }
     public void changeBoard(String s){
         this.board = s;
     }
     public void changeVitalities(String s){
         this.vitalities = s;
     }
     public void changeFrozen(String s){
         this.frozenPieces = s;
     }
     public void changeSpells(String s){
         this.spells = s;
     }
}

