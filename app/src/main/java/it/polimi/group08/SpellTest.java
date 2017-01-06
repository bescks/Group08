package it.polimi.group08;

import it.polimi.group08.boards.Chessboard;

/**
 * Created by gengdongjie on 06/01/2017.
 */

public class SpellTest {
    public static void main(String[] arg) {
        Chessboard board = new Chessboard();
        board.setBoardWithStr("W",
                        "000000" +
                        "g00000" +
                        "G00000" +
                        "000000" +
                        "m00000" +
                        "M00000", "5777000000000000", "133123", "FHRTFHRT");
        board.print();
        board.isAction("T1312");
        board.print();


    }


}
