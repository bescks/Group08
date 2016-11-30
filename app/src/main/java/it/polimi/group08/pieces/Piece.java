package it.polimi.group08.pieces;

/**
 * Created by lucio on 11/21/2016.
 * This class is used to express a standard piece, its attributes and its methods.
 */

public class Piece {
    public Piece(){
        pieceEnable = false;
        type = "00000";
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

    /**
     *  type is to indicate which type of pieces the object is, it can be Archer, Dragon, Giant, Knight, Mage, Squire and Castle(extended)
     * pieceEnable is to indicate this piece is exist(True) or not(False)
     * state is to indicate the piece is normal(n), frozen(f) or dead(d)
     * color is to indicate the piece is belonging to white or black
     * positionX and positionY is to record the piece is in which row(1 - 6) and which column(1 - 6)
     * initial... are to record the initial position and full vitality
     * other attributes are designed as the requirements
     */

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

    /**
     * From here, following are the basic methods to return and set all the attributes
     */

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

    public int getInitialVitality(){
        return initialVitality;
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


    public void setInitialVitality(int iV){
        initialVitality=iV;
    }

    public void setState(char c) {
        state = c;
    }

    public void setVitality(int v){
        vitality = v;
    }

    public void setMoveRange(int mR){
        moveRange=mR;
    }

    public void setMoveDirections(char mD){
        moveDirections=mD;
    }

    public void setMoveType(char mT){
        moveType=mT;
    }

    public void setAttackRange(int aR){
        attackRange=aR;
    }

    public void setAttackStrength(int aS){
        attackStrength=aS;
    }

    public void setAttackDirections(char aD){
        attackDirections=aD;
    }



    /**
     * attack is useless, but need to be overridden by child class. Because some pieces like mage and squire can not attack
     * moveTo is to move a piece to (x,y), all the methods in class piece are just actions, ability judgement methods are in package board
     * station is an addition method for new piece Castle
     * attacked, frozen, unfrozen, healed, revived, teleported are the passive methods, they are also just passive actions, the active actions are in correspond child classes
     * inCombatWith is used as A.inCombatWith(B), use the return value to indicate result 3(all died), 1(A died), 2(B died), it is only action, judgement is not included
     * chooseAction is reserved for view design
     */

    public boolean attack(Piece p){
        return false;
    }

    public void moveTo(int x, int y) {
        positionX = x;
        positionY = y;
    }

    public boolean station(Castle p){
        if(p.getGarrison().getPieceEnable())return false;
        else {
            p.stationed(this);
            return true;
        }
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

    public void revived(int x, int y) {
        vitality = initialVitality;
        positionX=x;
        positionY=y;
        state = 'n';
    }

    public void teleported(int x, int y) {
        positionX=x;
        positionY=y;
    }

    public int inCombatWith(Piece op) {
        boolean self = false;
        boolean opponent = false;
        if(this.state=='f'&&op.getState()=='f')return 3;
        else if(state=='f') return 1;
        else if(op.getState()=='f')return 2;
        while ((!self) && (!opponent)) {
            self = this.attacked(op.getAttackStrength());
            opponent = op.attacked(attackStrength);
        }
        if(self && opponent) return 3;
        else if(self) return 1;
        else return 2;
    }

    public boolean chooseActions(char a) {
        return (a=='M');
    }

}