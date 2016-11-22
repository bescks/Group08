package it.polimi.test.pieces;

/**
 * Created by lucio on 11/21/2016.
 */

public class Mage extends Piece {
    public Mage(){
        super();
        freezeEnable = false;
        healEnable = false;
        reviveEnable = false;
        teleportEnable = false;
    }
    public Mage(String c, int x, int y) {
        super(7, 1, 'a', 'w', 0, 2, 'n', c, x, y,"Mage");
        freezeEnable = true;
        healEnable = true;
        reviveEnable = true;
        teleportEnable = true;
    }

    private boolean freezeEnable;
    private boolean healEnable;
    private boolean reviveEnable;
    private boolean teleportEnable;

    public String getSpellsUnused(){
        String spellsUnused="";
        if(freezeEnable)spellsUnused=spellsUnused+"F";
        else spellsUnused=spellsUnused+"0";
        if(healEnable)spellsUnused=spellsUnused+"H";
        else spellsUnused=spellsUnused+"0";
        if(reviveEnable)spellsUnused=spellsUnused+"R";
        else spellsUnused=spellsUnused+"0";
        if(teleportEnable)spellsUnused=spellsUnused+"T";
        else spellsUnused=spellsUnused+"0";
        return spellsUnused;
    }

    public void setSpellsUnused(String s){
        if(s.charAt(0)=='0')freezeEnable=false;
        if(s.charAt(1)=='0')healEnable=false;
        if(s.charAt(2)=='0')reviveEnable=false;
        if(s.charAt(3)=='0')teleportEnable=false;
    }

    public boolean chooseActions(char a) {
        return (a == 'M'||a=='F'||a=='H'||a=='R'||a=='T');
    }

    public boolean freeze(Piece p){
        if(freezeEnable) {
            freezeEnable = false;
            p.frozen();
            return true;
        }
        else {
            System.out.println("Spell froze is used!");
            return false;
        }
    }

    public boolean heal(Piece p){
        if(healEnable) {
            healEnable = false;
            p.healed();
            return true;
        }
        else {
            System.out.println("Spell heal is used!");
            return false;
        }
    }

    public boolean revive(Piece p){
        if(reviveEnable) {
            reviveEnable = false;
            p.revived();
            return true;
        }
        else {
            System.out.println("Spell revive is used!");
            return false;
        }
    }

    public boolean teleport(Piece p, int x, int y){
        if(teleportEnable) {
            teleportEnable = false;
            p.teleported(x,y);
            return true;
        }
        else {
            System.out.println("Spell teleport is used!");
            return false;
        }
    }
}