package it.polimi.group08.pieces;

/**
 * Created by gengdongjie on 28/12/2016.
 */
public class Squire extends Piece {

    public Squire(String c, int x1, int y1, int x2, int y2) {

        super(3, 1, "+", "walk", 0, 1, "", c, "0");
        super.imageName ="piece_squire";
        super.twinIndex = true;
        super.initPositionX = x1;
        super.initPositionY = y1;
        super.twinPositionX = x2;
        super.twinPositionY = y2;
        switch (c) {
            case "white": {
                if (y1 == 2) {
                    super.playerY = 5;
                    super.twinPlayerY = 7;
                }
                if (y1 == 4) {
                    super.playerY = 7;
                    super.twinPlayerY = 5;
                }
                super.type = "S";
                break;
            }
            case "black": {
                if (y1 == 3) {
                    super.playerY = 5;
                    super.twinPlayerY = 7;
                }
                if (y1 == 1) {
                    super.playerY = 7;
                    super.twinPlayerY = 5;
                }
                super.type = "s";
                break;
            }
        }

    }
}