package it.polimi.group08;


import android.content.res.Resources;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    GridLayout gl_boarderBoard;
    RelativeLayout secondLayer;
    boolean[] moveIndex = new boolean[2];
    boolean[] attackIndex = new boolean[2];
    int fromX;
    int fromY;
    String action = "";
    ImageView emptyPiece;
    Animation animation;

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
        gl_boarderBoard = (GridLayout) findViewById(R.id.rl_boarderBoard);


        for (int i = 0; i < 4; i++) {
            gl_ui_white.getChildAt(i).setOnTouchListener(spellOnTouch);
            gl_ui_black.getChildAt(i).setOnTouchListener(spellOnTouch);

        }
        for (int i = 4; i < gl_ui_white.getChildCount(); i++) {
            gl_ui_white.getChildAt(i).setClickable(true);
            gl_ui_white.getChildAt(i).setOnClickListener(actionOnClick);
            gl_ui_black.getChildAt(i).setClickable(true);
            gl_ui_black.getChildAt(i).setOnClickListener(actionOnClick);
        }
//      thirdlayer layer: initialize cellBoard
        gl_cellBoard = (GridLayout) findViewById(R.id.gl_cellBoard);
        initPieceBoard();
        refreshPieceBoard();
        initBoarderBoard();
        refreshBoarderBoard();
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
                child.setPadding(10, 10, 10, 10);
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
                child.setLayoutParams(cellBoard_Param);
                child.setOnTouchListener(cellOnTouch);
                gl_cellBoard.addView(child);

            }
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
                emptyPieceImage.setPadding(20, 20, 20, 20);
                emptyPieceImage.setTag("000");
                gl_pieceBoard.addView(emptyPieceImage);
            }
        }
    }

    private void initBoarderBoard() {
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 6; c++) {
                AppCompatImageView boarderImage = new AppCompatImageView(getBaseContext());
                GridLayout.LayoutParams boarderImage_Param = new GridLayout.LayoutParams();
                boarderImage_Param.width = 180;
                boarderImage_Param.height = 180;
                boarderImage_Param.columnSpec = GridLayout.spec(c, 1, 1f);
                boarderImage_Param.rowSpec = GridLayout.spec(5 - r, 1, 1f);
                boarderImage.setLayoutParams(boarderImage_Param);
                boarderImage.setPadding(0, 0, 0, 0);
                gl_boarderBoard.addView(boarderImage);
            }

        }
    }

    private void refreshBoarderBoard() {
        Animation boardAm = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.boarder_flash);
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 6; c++) {
                AppCompatImageView boarderImage = (AppCompatImageView) gl_boarderBoard.getChildAt(c + 6 * r);
                if (chessboard.boardPiece[r][c].getTypeInt() == chessboard.movePlayerInt) {
                    if (chessboard.movePlayerInt == 1) {
                        boarderImage.setImageResource(R.drawable.piece_boarder_white);
                    } else if (chessboard.boardPiece[r][c].getTypeInt() == -1) {
                        boarderImage.setImageResource(R.drawable.piece_boarder_black);
                    }
                    boarderImage.startAnimation(boardAm);
                } else {
                    boarderImage.setImageResource(0);
                    boarderImage.animate().cancel();
                }
            }
        }
    }


    private View.OnClickListener actionOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (gl_pieceBoard.getChildCount() > 36) {
                gl_pieceBoard.removeViews(36, gl_pieceBoard.getChildCount() - 36);
            }
            System.out.println("actiononclik=" + action);
            Set cellSet = new TreeSet();
            if (view.getTag().equals("move")) {
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
                Animation targetAppear = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.target_appear);
                target.startAnimation(targetAppear);
            }

        }
    };
    private View.OnTouchListener cellOnTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (gl_pieceBoard.getChildCount() > 36) {
                gl_pieceBoard.removeViews(36, gl_pieceBoard.getChildCount() - 36);
            }
            moveIndex[0] = false;
            attackIndex[0] = false;
            moveIndex[1] = false;
            attackIndex[1] = false;
            int cellX = 5 - Integer.parseInt(v.getTag().toString().substring(4, 5));
            int cellY = Integer.parseInt(v.getTag().toString().substring(5, 6));
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    if (chessboard.boardPiece[cellX][cellY].getTypeInt() == chessboard.movePlayerInt) {
                        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.touch_down);
                        gl_pieceBoard.getChildAt(6 * cellX + cellY).startAnimation(animation);
                    }
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    if (chessboard.boardPiece[cellX][cellY].getTypeInt() == chessboard.movePlayerInt) {
                        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.touch_up);
                        gl_pieceBoard.getChildAt(6 * cellX + cellY).startAnimation(animation);
                    }
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
                        if (action.equals("M") || action.equals("A") || action.equals("T")) {
                            if (chessboard.isAction(action + (fromX + 1) + (fromY + 1) + (cellX + 1) + (cellY + 1))) {
                                refreshAction();
                                refreshPieceBoard();
                                refreshBoarderBoard();
                            } else {
                                fromX = cellX;
                                fromY = cellY;
                            }
                        } else {
                            if (chessboard.isAction(action + (cellX + 1) + (cellY + 1) + 0 + 0)) {
                                refreshAction();
                                refreshPieceBoard();
                                refreshBoarderBoard();
                            } else {
                                fromX = cellX;
                                fromY = cellY;
                            }
                        }
                        action = "";
                    }
                    break;
                }
            }


            return true;
        }
    };

    private View.OnTouchListener spellOnTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (gl_pieceBoard.getChildCount() > 36) {
                gl_pieceBoard.removeViews(36, gl_pieceBoard.getChildCount() - 36);
            }
            switch (v.getTag().toString()) {
                case "Heal": {
                    action = "H";
                    break;
                }
                case "Teleport": {
                    action = "T";
                    break;
                }
                case "Revive": {
                    action = "R";
                    break;
                }
                case "Freeze": {
                    action = "F";
                    break;
                }
            }
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.touch_down);
                    v.startAnimation(animation);
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.touch_up);
                    v.startAnimation(animation);
                    break;
                }
            }
            return true;
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
                        pieceImage.setRotation(0);
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









