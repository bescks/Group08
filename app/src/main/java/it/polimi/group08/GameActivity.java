package it.polimi.group08;


import android.content.res.Resources;
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
    GridLayout gl_ui_white;
    GridLayout gl_ui_black;
    GridLayout gl_cellBoard;
    RelativeLayout secondLayer;
    boolean[] moveIndex = new boolean[2];
    boolean[] attackIndex = new boolean[2];
    int fromX;
    int fromY;
    String action = "";
    ImageView emptyPiece;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
//      initialize chessboard
        chessboard.initBoard();
//       initialize parameters
        moveIndex[0] = false;
        moveIndex[1] = false;
        attackIndex[0] = false;
        attackIndex[1] = false;
//      firstLayer : initialize cellBackground
        gl_cellBackground = (GridLayout) findViewById(R.id.gl_cellBackground);
        initCellBackground();
//      secondlayer :initialization
        gl_pieceBoard = (GridLayout) findViewById(R.id.gl_pieceBoard);
        secondLayer = (RelativeLayout) findViewById(R.id.rl_secondLayer);
        gl_ui_white = (GridLayout) findViewById(R.id.gl_ui_white);
        gl_ui_black = (GridLayout) findViewById(R.id.gl_ui_black);
        gl_pieceBoard.getLayoutParams().height = 1080;
        gl_pieceBoard.removeAllViews();
        initPieceBoard();
        refreshPieceBoard();
//      secondLayer: 2nd GridLayout
        for (int i = 4; i < gl_ui_white.getChildCount(); i++) {
            gl_ui_white.getChildAt(i).setOnClickListener(actionOnClick);
            gl_ui_black.getChildAt(i).setOnClickListener(actionOnClick);
        }
//      thirdlayer layer: initialize cellBoard
        gl_cellBoard = (GridLayout) findViewById(R.id.gl_cellBoard);
        initCellBoard();
        refreshAction();
    }

    private void initCellBackground() {

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

    private void initCellBoard() {
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
            if (gl_pieceBoard.getChildCount() > 36) {
                gl_pieceBoard.removeViews(36, gl_pieceBoard.getChildCount() - 36);
            }
            moveIndex[0] = false;
            attackIndex[0] = false;
            moveIndex[1] = false;
            attackIndex[1] = false;
            int cellX = 5 - Integer.parseInt(cell.getTag().toString().substring(4, 5));
            int cellY = Integer.parseInt(cell.getTag().toString().substring(5, 6));
            System.out.println("action=" + action);
            if (action.equals("")) {
                if (chessboard.boardPiece[cellX][cellY].getTypeInt() == chessboard.movePlayerInt
                        && chessboard.boardPiece[cellX][cellY].state.equals("n")) {
                    if (!chessboard.getMoveCells(cellX, cellY).isEmpty()) {
                        moveIndex[chessboard.boardPiece[cellX][cellY].getPlayerX()] = true;
                    }
                    if (!chessboard.getAttackCells(cellX, cellY).isEmpty()) {
                        attackIndex[chessboard.boardPiece[cellX][cellY].getPlayerX()] = true;
                    }
                    fromX = cellX;
                    fromY = cellY;
                }
                refreshAction();
            } else {
                System.out.println("action=" + action);
                System.out.println("fromX=" + fromX);
                System.out.println("fromY=" + fromY);
                System.out.println("cellX=" + cellX);
                System.out.println("cellY=" + cellY);

                if (chessboard.isAction(action + (fromX + 1) + (fromY + 1) + (cellX + 1) + (cellY + 1))) {
                    refreshPieceBoard();
                    refreshAction();
                }
                action = "";
            }

        }
    };

    private View.OnClickListener actionOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (gl_pieceBoard.getChildCount() > 36) {
                gl_pieceBoard.removeViews(36, gl_pieceBoard.getChildCount() - 36);
            }
            Set cellSet = new TreeSet();
            if (view.getTag().equals("move")) {
                System.out.println("jinlaile");
                cellSet = chessboard.getMoveCells(fromX, fromY);
                action = "M";
            }
            if (view.getTag().equals("attack")) {
                cellSet = chessboard.getAttackCells(fromX, fromY);
                action = "A";
            }
            for (Object cell : cellSet) {
                int cellX = Integer.parseInt(cell.toString().substring(0, 1));
                int cellY = Integer.parseInt((cell.toString().substring(1, 2)));
                GridLayout.LayoutParams cellBoard_Param = new GridLayout.LayoutParams();
                AppCompatImageView target = new AppCompatImageView(getBaseContext());
                cellBoard_Param.width = 180;
                cellBoard_Param.height = 180;
                cellBoard_Param.columnSpec = GridLayout.spec(cellY, 1, 1f);
                cellBoard_Param.rowSpec = GridLayout.spec(5 - cellX, 1, 1f);
                target.setLayoutParams(cellBoard_Param);
                target.setPadding(30, 30, 30, 30);
                if (chessboard.movePlayerInt == -1) {
                    target.setRotation(180);
                }
                if (view.getTag().equals("move")) {
                    target.setImageResource(R.drawable.piece_target_move);
                }
                if (view.getTag().equals("attack")) {
                    target.setImageResource(R.drawable.piece_target_attack);
                }
                gl_pieceBoard.addView(target);
            }

        }
    };

    private void refreshAction() {
        if (moveIndex[0]) {
            viewIsEnabled((ImageView) findViewById(R.id.iv_move_white), true);
            findViewById(R.id.tv_move_white).setClickable(true);
        } else {
            viewIsEnabled((ImageView) findViewById(R.id.iv_move_white), false);
            findViewById(R.id.tv_move_white).setClickable(false);
        }
        if (attackIndex[0]) {
            viewIsEnabled((ImageView) findViewById(R.id.iv_attack_white), true);
            findViewById(R.id.tv_attack_white).setClickable(true);
        } else {
            viewIsEnabled((ImageView) findViewById(R.id.iv_attack_white), false);
            findViewById(R.id.tv_attack_white).setClickable(false);
        }
        if (moveIndex[1]) {
            viewIsEnabled((ImageView) findViewById(R.id.iv_move_black), true);
            findViewById(R.id.tv_move_black).setClickable(true);
        } else {
            viewIsEnabled((ImageView) findViewById(R.id.iv_move_black), false);
            findViewById(R.id.tv_move_black).setClickable(false);
        }
        if (attackIndex[1]) {
            viewIsEnabled((ImageView) findViewById(R.id.iv_attack_black), true);
            findViewById(R.id.tv_attack_black).setClickable(true);
        } else {
            viewIsEnabled((ImageView) findViewById(R.id.iv_attack_black), false);
            findViewById(R.id.tv_attack_black).setClickable(false);
        }

    }

    private void viewIsEnabled(ImageView imageview, boolean enableIndex) {
        if (enableIndex) {
            imageview.setClickable(true);
            imageview.clearColorFilter();
        } else {
            imageview.setClickable(false);
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            imageview.setColorFilter(new ColorMatrixColorFilter(matrix));

        }
    }

    private void initPieceBoard() {
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 6; c++) {
                AppCompatImageView emptyPieceImage = new AppCompatImageView(getBaseContext());
                GridLayout.LayoutParams emptyPieceImage_Param = new GridLayout.LayoutParams();
                emptyPieceImage_Param.width = 180;
                emptyPieceImage_Param.height = 180;
                emptyPieceImage_Param.columnSpec = GridLayout.spec(c, 1, 1f);
                emptyPieceImage_Param.rowSpec = GridLayout.spec(5 - r, 1, 1f);
                emptyPieceImage.setLayoutParams(emptyPieceImage_Param);
                emptyPieceImage.setPadding(0, 6, 0, 12);
                emptyPieceImage.setTag("000");
                gl_pieceBoard.addView(emptyPieceImage);
            }
        }
    }

    private void refreshPieceBoard() {
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 6; c++) {
                AppCompatImageView pieceImage = (AppCompatImageView) gl_pieceBoard.getChildAt(c + 6 * r);
                if (!pieceImage.getTag().toString().substring(0, 1).equals(chessboard.boardPiece[r][c].getType())) {
                    pieceImage.setTag(chessboard.boardPiece[r][c].getType() + r + c);
                    if (chessboard.boardPiece[r][c].getTypeInt() == 1) {
                        pieceImage.setBackgroundResource(R.drawable.piece_bg_white);
                        Resources res = this.getResources();
                        pieceImage.setImageResource(res.getIdentifier(chessboard.boardPiece[r][c].getImageName(), "drawable", this.getPackageName()));
                    } else if (chessboard.boardPiece[r][c].getTypeInt() == -1) {
                        pieceImage.setBackgroundResource(R.drawable.piece_bg_black);
                        Resources res = this.getResources();
                        pieceImage.setImageResource(res.getIdentifier(chessboard.boardPiece[r][c].getImageName(), "drawable", this.getPackageName()));
                        pieceImage.setRotation(180);
                    } else {
                        pieceImage.setImageResource(0);
                        pieceImage.setBackgroundResource(0);
                    }

                }
            }
        }
    }
}









