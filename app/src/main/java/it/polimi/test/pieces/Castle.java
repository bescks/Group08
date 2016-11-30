package it.polimi.test.pieces;

/**
 * Created by lucio on 11/22/2016.
 */

public class Castle extends Piece{
    public Castle(String c, int x, int y) {
        super(8, 1, 's', 'w', 0, 0, 'n', c, x, y,"Castle");
        garrison = new Piece();
    }

    public boolean chooseActions(char a) {
        return (a == 'M');
    }

    private Piece garrison;

    public Piece getGarrison(){
        return garrison;
    }

    public void stationed(Piece p){
        garrison=p;
        super.setMoveRange(0);
        super.setMoveDirections('n');
        super.setMoveType('n');
        if(p.getAttackDirections()=='n'){
            super.setInitialVitality(super.getInitialVitality()+1);
            super.setVitality(super.getVitality()+1);
        }
        else {
            super.setAttackDirections(p.getAttackDirections());
            super.setAttackRange(p.getAttackRange()+1);
            super.setAttackStrength(p.getAttackStrength()+1);
        }
    }

    public int inCombatWith(Piece op) {
        System.out.println("Castle can't be the target of combat!");
        return 4;
    }

    public boolean attacked(int damage) {
        super.setVitality(super.getVitality() - damage);
        if (super.getVitality() <= 0){
            super.setVitality(0);
            super.setState('d');
            garrison.setVitality(0);
            garrison.setState('d');
            garrison = new Piece();
            return true;
        }
        else return false;
    }
}
