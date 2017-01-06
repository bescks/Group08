package it.polimi.group08.pieces;

/**
 * Created by gengdongjie on 28/12/2016.
 */

public class Archer extends Piece {

    public Archer(String c) {
        super(5, 2, "*", "walk", 3, 2, "+", c, "0");
        super.imageName ="piece_archer";
        super.playerY=3;
        switch (c) {
            case "white":
                super.type = "A";
                super.initPositionX = 0;
                super.initPositionX = 5;
                break;
            case "black":
                super.type = "a";
                super.initPositionX = 5;
                super.initPositionX = 5;
                break;
            default:
                System.out.println("ERROR:<Invalid color for piece Archer!>");
        }
    }
}