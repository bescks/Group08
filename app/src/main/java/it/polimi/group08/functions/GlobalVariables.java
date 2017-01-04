package it.polimi.group08.functions;


import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import it.polimi.group08.pieces.Archer;
import it.polimi.group08.pieces.Dragon;
import it.polimi.group08.pieces.Giant;
import it.polimi.group08.pieces.Knight;
import it.polimi.group08.pieces.Mage;
import it.polimi.group08.pieces.Piece;
import it.polimi.group08.pieces.Squire;

/**
 * Created by gengdongjie on 29/12/2016.
 */

public class GlobalVariables {
    private Set<String> specialCells = new TreeSet<>();
    private Map<String, String> typeToNum = new TreeMap<>();
    private Piece piece;
    private Piece emptyPiece = new Piece();

    public GlobalVariables() {


//          specialCells
        specialCells.add("00");
        specialCells.add("30");
        specialCells.add("25");
        specialCells.add("55");
//          typeToNum
//        black
        typeToNum.put("G", "00");
        typeToNum.put("D", "01");
        typeToNum.put("M", "02");
        typeToNum.put("A", "03");
        typeToNum.put("K", "04");
        typeToNum.put("S", "05");
//        white
        typeToNum.put("g", "10");
        typeToNum.put("d", "11");
        typeToNum.put("m", "12");
        typeToNum.put("a", "13");
        typeToNum.put("k", "14");
        typeToNum.put("s", "15");
    }

    public Piece piece(int i, int j) {
        if (i == 0 && j == 1) {
            piece = new Giant("white");
        } else if (i == 0 && j == 2) {
            piece = new Dragon("white");
        } else if (i == 0 && j == 3) {
            piece = new Mage("white");
        } else if (i == 0 && j == 4) {
            piece = new Archer("white");
        } else if (i == 1 && j == 1) {
            piece = new Knight("white", 1, 1, 1, 3);
        } else if (i == 1 && j == 2) {
            piece = new Squire("white", 1, 2, 1, 4);
        } else if (i == 1 && j == 3) {
            piece = new Knight("white", 1, 3, 1, 1);
        } else if (i == 1 && j == 4) {
            piece = new Squire("white", 1, 4, 1, 2);
        } else if (i == 4 && j == 1) {
            piece = new Squire("black", 4, 1, 4, 3);
        } else if (i == 4 && j == 2) {
            piece = new Knight("black", 4, 2, 4, 4);
        } else if (i == 4 && j == 3) {
            piece = new Squire("black", 4, 3, 4, 1);
        } else if (i == 4 && j == 4) {
            piece = new Knight("black", 4, 4, 4, 2);
        } else if (i == 5 && j == 1) {
            piece = new Archer("black");
        } else if (i == 5 && j == 2) {
            piece = new Mage("black");
        } else if (i == 5 && j == 3) {
            piece = new Dragon("black");
        } else if (i == 5 && j == 4) {
            piece = new Giant("black");
        } else {
            piece = emptyPiece;
        }
        return piece;
    }

    public Set specialCellsSet() {
        return specialCells;
    }

    public Map typeToNum() {
        return typeToNum;
    }
}
