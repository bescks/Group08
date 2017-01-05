package it.polimi.group08;


import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Set;
import java.util.TreeSet;

import it.polimi.group08.boards.Chessboard;

import static it.polimi.group08.R.drawable.cell;

public class GameActivity extends AppCompatActivity {
    Chessboard chessboard = new Chessboard();
    GridLayout gl_cellBackground;
    GridLayout gl_pieceBoard;

    GridLayout gl_cellBoard;
    RelativeLayout secondLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        AppCompatImageView iv_move_white = new AppCompatImageView(getBaseContext());
        iv_move_white.setColorFilter(new ColorMatrixColorFilter(cm));

//      initialize chessboard
        chessboard.initBoard();
//      firstLayer : initialize cellBackground
        gl_cellBackground = (GridLayout) findViewById(R.id.gl_cellBackground);
        initCellBackground();
//      secondlayer :initialization
        secondLayer = (RelativeLayout) findViewById(R.id.rl_secondLayer);
        gl_pieceBoard = (GridLayout) findViewById(R.id.gl_pieceBoard);
        gl_pieceBoard.getLayoutParams().height = 1080;
        for (int i = 0; i < gl_pieceBoard.getChildCount(); i++) {
            View child = gl_pieceBoard.getChildAt(i);
            child.getLayoutParams().width = 180;
            child.getLayoutParams().height = 180;
        }
//        initializing 2nd GridLayout

//      thirdlayer layer: initialize cellBoard
        gl_cellBoard = (GridLayout) findViewById(R.id.gl_cellBoard);
        initCellBoard();


    }


    public void initCellBackground() {

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                GridLayout.LayoutParams cellBackground_Param = new GridLayout.LayoutParams();
                AppCompatImageView child = new AppCompatImageView(this);
                cellBackground_Param.width = 180;
                cellBackground_Param.height = 180;
                cellBackground_Param.columnSpec = GridLayout.spec(j, 1);
                cellBackground_Param.rowSpec = GridLayout.spec(i, 1);
                String tag = "bg_cell" + j + i;
                child.setTag(tag);
                child.setImageResource(cell);
                child.setScaleType(ImageView.ScaleType.FIT_XY);
                child.setLayoutParams(cellBackground_Param);
                gl_cellBackground.addView(child);
            }
        }
    }

    public void initCellBoard() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                GridLayout.LayoutParams cellBoard_Param = new GridLayout.LayoutParams();
                AppCompatImageView child = new AppCompatImageView(getBaseContext());
                cellBoard_Param.width = 180;
                cellBoard_Param.height = 180;
                cellBoard_Param.columnSpec = GridLayout.spec(j, 1);
                cellBoard_Param.rowSpec = GridLayout.spec(i, 1);
                String tag = "cell" + i + j;
                child.setTag(tag);
                child.setClickable(true);
                child.setOnClickListener(cellOnClick);
                child.setLayoutParams(cellBoard_Param);
                gl_cellBoard.addView(child);
            }
        }
    }

    private View.OnClickListener cellOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View cell) {
            System.out.println(gl_pieceBoard.getChildCount());
            if (gl_pieceBoard.getChildCount() > 36) {
                gl_pieceBoard.removeViews(36, gl_pieceBoard.getChildCount() - 36);
            }
            int cellX = 5 - Integer.parseInt(cell.getTag().toString().substring(4, 5));
            int cellY = Integer.parseInt(cell.getTag().toString().substring(5, 6));
            Set moveSet = new TreeSet();
            if (chessboard.boardPiece[cellX][cellY].getTypeInt() != 0) {
                moveSet = chessboard.getMoveCells(cellX, cellY);
                for (Object moveCell : moveSet) {
                    int moveCellX = Integer.parseInt(moveCell.toString().substring(0, 1));
                    int moveCellY = Integer.parseInt((moveCell.toString().substring(1, 2)));
                    GridLayout.LayoutParams cellBoard_Param = new GridLayout.LayoutParams();
                    AppCompatImageView target = new AppCompatImageView(getBaseContext());
                    cellBoard_Param.width = 180;
                    cellBoard_Param.height = 180;
                    cellBoard_Param.columnSpec = GridLayout.spec(moveCellY, 1, 1f);
                    cellBoard_Param.rowSpec = GridLayout.spec(5 - moveCellX, 1, 1f);
                    target.setContentDescription("target");
                    target.setPadding(30, 30, 30, 30);
                    target.setTag("targetCell");
                    target.setImageResource(R.drawable.piece_target);
                    target.setLayoutParams(cellBoard_Param);
                    gl_pieceBoard.addView(target);
                }
            }

//            GridLayout.LayoutParams cellBoard_Param = new GridLayout.LayoutParams();
//            AppCompatImageView target = new AppCompatImageView(getBaseContext());
//            cellBoard_Param.width = 180;
//            cellBoard_Param.height = 180;
//            cellBoard_Param.columnSpec = GridLayout.spec(cellY, 1, 1f);
//            cellBoard_Param.rowSpec = GridLayout.spec(5 - cellX, 1, 1f);
//            target.setContentDescription("target");
//            target.setPadding(30, 30, 30, 30);
//            target.setTag("newcell");
//            target.setImageResource(R.drawable.piece_target);
//            target.setLayoutParams(cellBoard_Param);
//            gl_pieceBoard.addView(target);


        }
    };


}








