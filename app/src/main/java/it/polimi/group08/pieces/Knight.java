package it.polimi.group08.pieces;

/**
 * Created by gengdongjie on 28/12/2016.
 */
public class Knight extends Piece {

    public Knight(String c, int x1, int y1, int x2, int y2) {

        super(4, 1, "*", "walk", 1, 2, "x", c, "0");
        super.imageName ="piece_knight";
        super.twinIndex = true;
        super.initPositionX = x1;
        super.initPositionY = y1;
        super.twinPositionX = x2;
        super.twinPositionY = y2;

        switch (c) {
            case "white": {
                if (y1 == 1) {
                    super.playerY = 4;
                    super.twinPlayerY = 6;
                }
                if (y1 == 3) {
                    super.playerY = 6;
                    super.twinPlayerY = 4;
                }
                super.type = "K";
                break;
            }

            case "black": {
                if (y1 == 4) {
                    super.playerY = 4;
                    super.twinPlayerY = 6;
                }
                if (y1 == 2) {
                    super.playerY = 6;
                    super.twinPlayerY = 4;
                }
                super.type = "k";
                break;
            }
            default:
                System.out.println("ERROR:<Invalid color for piece Knight!>");
        }
    }
}

