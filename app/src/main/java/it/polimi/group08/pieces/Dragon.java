package it.polimi.group08.pieces;

/**
 * Created by gengdongjie on 28/12/2016.
 */

public class Dragon extends Piece {

    public Dragon(String c) {
        super(6, 3, "+", "flight", 2, 3, "+", c, "0");
        super.playerY = 1;
        switch (c) {
            case "white":
                super.type = "D";
                super.initPositionX = 0;
                super.initPositionX = 2;
                break;
            case "black":
                super.type = "d";
                super.initPositionX = 5;
                super.initPositionX = 3;
                break;
            default:
                System.out.println("ERROR:<Invalid color for piece dragon!>");
        }

    }
}
