package it.polimi.group08.pieces;

/**
 * Created by gengdongjie on 28/12/2016.
 */

public class Piece {

    String type = "0";
    private int typeInt = 0;
    public String state = "d";
    private int initialVitality = 0;
    public int vitality = 0;
    private int moveRange = 0;
    private String moveDirections = "";
    private String moveType = "";
    private int attackRange = 0;
    private int attackStrength = 0;
    private String attackDirections = "";
    int initPositionX = 0;
    int initPositionY = 0;
    int twinPositionX = 0;
    int twinPositionY = 0;
    int twinPlayerY = 0;
    private int playerX = 0;
    int playerY = 0;
    boolean twinIndex = false;
    public String spells = "";
    String imageName = "";

    public Piece(int iV, int mR, String mD, String mT, int aR, int aS, String aD, String c, String t) {

        if (c.equals("white")) {
            playerX = 0;
            typeInt = 1;
        } else {
            playerX = 1;
            typeInt = -1;
        }

        initialVitality = iV;
        vitality = iV;

        moveRange = mR;
        moveDirections = mD;
        moveType = mT;
        attackRange = aR;
        attackStrength = aS;
        attackDirections = aD;
        type = t;
    }

    public Piece() {

    }

    /**
     * type is to indicate which type of pieces the object is, it can be Archer, Dragon, Giant, Knight, Mage, Squire and Castle(extended)
     * pieceEnable is to indicate this piece is exist(True) or not(False)
     * state is to indicate the piece is normal(n), frozen(f) or dead(d)
     * color is to indicate the piece is belonging to white or black
     * positionX and positionY is to record the piece is in which row(1 - 6) and which column(1 - 6)
     * initial... are to record the initial position and full vitality
     * other attributes are designed as the requirements
     */

    /**
     * From here, following are the basic methods to return and set all the attributes
     */

    public String getType() {
        return type;
    }

    public int getTypeInt() {
        return typeInt;
    }

    public void setInitVitality() {
        vitality = initialVitality;
    }

    public int getInitPositionX() {
        return initPositionX;
    }

    public int getInitPositionY() {
        return initPositionY;
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public boolean isTwin() {
        return twinIndex;
    }

    public int getTwinX() {
        return twinPositionX;
    }

    public int getTwinY() {

        return twinPositionY;
    }

    public int getTwinPlayerY()

    {
        return twinPlayerY;
    }

    public int getMoveRange() {
        return moveRange;
    }

    public String getMoveDirections() {
        return moveDirections;
    }

    public String getMoveType() {
        return moveType;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public int getAttackStrength() {
        return attackStrength;
    }

    public String getAttackDirections() {
        return attackDirections;
    }

    public String getImageName() {
        return imageName;
    }
}