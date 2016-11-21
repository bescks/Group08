package it.polimi.test.pieces;

/**
 * Created by lucio on 11/21/2016.
 */

public class Piece {
    public Piece(){
        pieceEnable = false;
    }
    public Piece(int iV, int mR, char mD, char mT, int aR, int aS, char aD, String c, int x, int y, String t) {
        state = 'n';
        color = c;

        initialVitality = iV;
        vitality = iV;
        initialPositionX = x;
        initialPositionY = y;
        positionX = x;
        positionY = y;

        moveRange = mR;
        moveDirections = mD;
        moveType = mT;
        attackRange = aR;
        attackStrength = aS;
        attackDirections = aD;

        type = t;
        pieceEnable = true;
    }

    private String type;
    private boolean pieceEnable;

    private char state;
    private String color;

    private int initialVitality;
    private int vitality;
    private int initialPositionX;
    private int initialPositionY;
    private int positionX;
    private int positionY;

    private int moveRange;
    private char moveDirections;
    private char moveType;
    private int attackRange;
    private int attackStrength;
    private char attackDirections;


    public String getType(){
        return type;
    }

    public boolean getPieceEnable(){
        return pieceEnable;
    }

    public char getState() {
        return state;
    }

    public String getColor() {
        return color;
    }



    public int getVitality(){
        return vitality;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public int getInitialPositionX() {
        return initialPositionX;
    }

    public int getInitialPositionY() {
        return initialPositionY;
    }



    public int getMoveRange(){
        return moveRange;
    }

    public char getMoveDirections(){
        return moveDirections;
    }

    public char getMoveType(){
        return moveType;
    }

    public int getAttackRange(){
        return  attackRange;
    }

    public int getAttackStrength(){
        return attackStrength;
    }

    public char getAttackDirections(){
        return attackDirections;
    }



    public boolean attack(Piece p){
        return false;
    }

    public void moveTo(int x, int y) {
        positionX = x;
        positionY = y;
    }

    public boolean attacked(int damage) {
        vitality = vitality - damage;
        if (vitality <= 0){
            vitality = 0;
            state = 'd';
            return true;
        }
        else return false;
    }

    public void frozen() {
        state = 'f';
    }

    public void unFrozen() {
        state = 'n';
    }

    public void healed() {
        vitality = initialVitality;
    }

    public void revived() {
        vitality = initialVitality;
        positionX=initialPositionX;
        positionY=initialPositionY;
        state = 'n';
    }

    public void teleported(int x, int y) {
        positionX=x;
        positionY=y;
    }

    public int inCombatWith(Piece op) {
        boolean self = false;
        boolean opponent = false;
        while ((!self) && (!opponent)) {
            if(op.getState()=='n')self=this.attacked(op.getAttackStrength());
            if(state=='n')opponent=op.attacked(attackStrength);
        }
        if(self && opponent) return 3;
        else if(self) return 1;
        else return 2;
    }

    public boolean chooseActions(char a) {
        return (a=='M');
    }

}