package it.polimi.group08.pieces;


/**
 * Created by gengdongjie on 28/12/2016.
 */

public class Giant extends Piece {

    public Giant(String c) {
        super(5, 2, "+", "walk", 1, 4, "+", c, "0");
        super.playerY=0;
        switch (c) {
            case "white":
                super.type = "G";
                super.initPositionX = 0;
                super.initPositionX = 1;
                break;
            case "black":
                super.type = "g";
                super.initPositionX = 5;
                super.initPositionX = 4;
                break;
            default:
                System.out.println("ERROR:<Invalid color for piece Giant!>");
        }

    }
}

