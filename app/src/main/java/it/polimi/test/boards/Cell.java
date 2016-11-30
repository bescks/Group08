package it.polimi.test.boards;

/**
 * Created by lucio on 11/21/2016.
 */

public class Cell {
    public Cell(){
        cellState = 'n';
    }
    private char cellState;
    public char getCellState(){
        return cellState;
    }
    public void setCellState(char c){
        cellState = c;
    }
}