package it.polimi.group08.pieces;

/**
 * Created by gengdongjie on 28/12/2016.
 */

public class Piece {
//    the type of empty piece is 0, others are g,d,m,a,k,s,G,D,M,A,K,S
    String type = "0";
//    typeInt is used to transfer white and black to integer
//  0 means the piece is an empty piece
//  1 means the piece is belong to white
//  -1 means the piece is belong to black
    private int typeInt = 0;
//  state is to indicate the piece is normal(n), frozen(f) or dead(d), it's initial value is d, after initializing chessboard in class chessboard, the state will be changed according to the boardstring
    public String state = "d";
    private int initialVitality = 0;
    public int vitality = 0;
    private int moveRange = 0;
//  * means the direction is any, + means the direction is horiz + vert, x means the direction is diagnal
    private String moveDirections = "";
    private String moveType = "";
    private int attackRange = 0;
    private int attackStrength = 0;
    private String attackDirections = "";
//    initpositionX means the initial x-coordniate of this piece
//    initpositionY means the initial y-coordniate of this piece
//    initpositionX and initpositionY is to record the piece is in which row(0 - 5) and which column(0 - 5)
    int initPositionX = 0;
    int initPositionY = 0;
//    twinPositionX means the x-coordniate of this piece's twin piece
//    twinPositionY means the y-coordniate of this piece's twin piece

    int twinPositionX = 0;
    int twinPositionY = 0;

//   playerX means the initial x-coordniate of this piece in playerPiece.
//   For white, playerX of all pieces is equal to 0. For black, layerX of all pieces is equal to 1.
//   playerY means the initial y-coordniate of this piece in playerPiece, the playerY is different according to different pieces
//   piece giant's playerX is equal to 0, dragon is equal to 1, mage is equal to 2, archer is equal to 3, knight is equal to 4 or 6, squire is equal to 5 or 7
//    actually, playerX and playerY transform the different players and different colors into different integers
    private int playerX = 0;
    int twinPlayerY = 0;
    int playerY = 0;
//    twinIndex is an index which indict whether the piece has a twin piece or not
    boolean twinIndex = false;
//    string spell is used to represent the spells of this piece. For mage, it's initial value is FHRT, for other pieces who cannot cast a spell, this viriable is equal to ""
    public String spells = "";
//    imagename is used to represent the imagename of this piece in drawable
    String imageName = "";
//    isNorImg is a boolean variable, when the piece is frozen, the image of this piece is replaced by it's frozen image and isNorImg is equal to false
//    in other situation, the value is always equal to true
    public boolean isNorImg = true;

    public Piece(int iV, int mR, String mD, String mT, int aR, int aS, String aD, String c, String t) {

        if (c.equals("white")) {
            playerX = 0;
            typeInt = 1;
        } else {
            playerX = 1;
            typeInt = -1;
        }
//      The following variables' meaning are straightforward represent by their name
        initialVitality = iV;
        vitality = iV;
        moveRange = mR;
        moveDirections = mD;
        moveType = mT;
        attackRange = aR;
        attackStrength = aS;
        attackDirections = aD;
//      type is the abbreviation of piece's name, it can be g, d, m, a, k, s, G, D, M, A, K, S
        type = t;
    }

    public Piece() {

    }

    /**
     * From here, following are the basic methods to return the attributes who do not need to be changed after initialized
     * * other attributes are designed as the requirements
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

    public int getInitVitality() {
        return initialVitality;
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