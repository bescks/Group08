package it.polimi.group08.pieces;

/**
 * Created by gengdongjie on 28/12/2016.
 */

public class Dragon extends Piece {
    //  The following is some values to declare piece dragon
    public Dragon(String c) {
        super(6, 3, "+", "flight", 2, 3, "+", c, "0");
        super.imageName ="piece_dragon";
        super.playerY = 1;
        switch (c) {
            case "white":
                super.type = "D";
                super.initPositionX = 0;
                super.initPositionY = 2;
                break;
            case "black":
                super.type = "d";
                super.initPositionX = 5;
                super.initPositionY = 3;
                break;
            default:
                System.out.println("ERROR:<Invalid color for piece Dragon!>");
        }

    }
}
