package it.polimi.group08.boards;

/**
 * Created by lucio on 11/21/2016.
 * Cell class is reserved for view design
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