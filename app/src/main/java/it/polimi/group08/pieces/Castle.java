package it.polimi.group08.pieces;

/**
 * Created by lucio on 11/22/2016.
 * Castle is a new kind of piece we added to prove the design is extensible
 * The rules:
 * Castle can not attack, but it can be attacked
 * Castle can not combat with other pieces, and other pieces can not combat with castle too
 * Castle can be stationed by another piece named garrison. after stationing, Castle lose its ability to move, and the garrison can not leave the castle
 * if garrison can attack, then castle get the ability to attack with its garrison's attackDirection, and attackStrength and attackRange will be upgraded by 1
 * if garrison can not attack, then castle's vitality will e upgraded by 1
 * When castle dies, the garrison in it will also die
 *
 * Castle has a piece type attribute to record its garrison
 * Castle can attack, so attack method is overridden
 * After attacked, castle and garrison may die, so attacked is different from standard piece, it should be overridden
 * Castle can be stationed, it upgrades according to its garrison's type
 * chooseAction is reserved
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
            System.out.println("Castle attack upgrade! "+ super.getAttackRange() + super.getAttackStrength() + super.getAttackDirections());
        }
    }

    public boolean attack(Piece p){
        if(garrison.getPieceEnable()) return p.attacked(super.getAttackStrength());
        else return false;
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
