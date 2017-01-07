package it.polimi.group08;

import it.polimi.group08.boards.Chessboard;

/**
 * Created by gengdongjie on 01/01/2017.
 */

public class TestFile {
    public String turnTest(String str) {
        Chessboard chessboard = new Chessboard();
        chessboard.setBoardWithStr(
                str.substring(0, 1),
                str.substring(1, 37),
                str.substring(37, 53),
                str.substring(53, 59),
                str.substring(59, 67));
        for (int i = 0; i < (str.length() - 67) / 5; i++) {
            chessboard.isAction(str.substring(67 + 5 * i, 72 + 5 * i));
        }
        System.out.println("*************************");
        System.out.println(chessboard.getPlayerScore()[0]);
        System.out.println(chessboard.getPlayerScore()[1]);
        return chessboard.getBoardStr();


    }
}

