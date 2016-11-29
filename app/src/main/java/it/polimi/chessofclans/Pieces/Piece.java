package it.polimi.chessofclans.Pieces;

/**
 * Created by lucio on 11/17/2016.
 */

public class Piece {
    public Piece(){

    }
    public Piece(int v,int mR, char mD, char mT, int aR, int aS, char aD,char c, int p){
        this.vitality = v;
        this.vitalityLeft = v;
        this.moveRange = mR;
        this.moveDirections = mD;
        this.moveType = mT;
        this.attackRange = aR;
        this.attackStrength = aS;
        this.attackDirections = aD;
        this.state = 'n';
        this.color = c;
        this.position = p;
    }

    private int vitality;
    private int vitalityLeft;
    private int moveRange;
    private char moveDirections;
    private char moveType;
    private int attackRange;
    private int attackStrength;
    private char attackDirections;
    private char state;
    private char color;
    private int ini;
    private int position;

    public int getAttackRange(){
        return attackRange;
    }
    public int attack(){
        return attackStrength;
    }
    public void attacked (int damage){
        this.vitalityLeft=this.vitalityLeft-damage;
        if(this.vitalityLeft<0)this.vitalityLeft=0;
    }
    public char getAttackDirections(){
        return attackDirections;
    }
    public char getColor(){
        return color;
    }
    public void healed (){
        this.vitalityLeft=this.vitality;
    }
    public void frozed(){
        this.state='f';
    }
    public void unFrozen(){
        this.state='n';
    }
    public void died(){
        if(this.vitalityLeft==0)this.state='d';
    }
    public void revived(){
        this.state='n';
    }
    public void teleported(int to){

    }
    public void inCombat(int v,int s){

    }
}
