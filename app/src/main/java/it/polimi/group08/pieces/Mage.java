package it.polimi.group08.pieces;

/**
 * Created by gengdongjie on 28/12/2016.
 */

public class Mage extends Piece {
    //  The following is some values to declare piece mage
    public Mage(String c) {
        super(7, 1, "*", "walk", 0, 2, "", c, "0");
        super.imageName ="piece_mage";
        super.spells="FHRT";
        super.playerY=2;
        switch (c) {
            case "white":
                super.type = "M";
                super.initPositionX = 0;
                super.initPositionY = 3;
                break;
            case "black":
                super.type = "m";
                super.initPositionX = 5;
                super.initPositionY = 2;
                break;
            default:
                System.out.println("ERROR:<Invalid color for piece Mage!>");
        }
    }

}