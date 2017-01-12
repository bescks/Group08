package it.polimi.group08;


import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Set;
import java.util.TreeSet;

import it.polimi.group08.boards.Chessboard;


public class GameActivity extends AppCompatActivity {
    Chessboard chessboard = new Chessboard();
    RelativeLayout rl_activity_game;
    GridLayout gl_cellBackground;
    GridLayout gl_pieceBoard;
    GridLayout gl_action_white;
    GridLayout gl_action_black;
    GridLayout gl_cellBoard;
    GridLayout gl_boarderBoard;
    RelativeLayout rl_vitality_white;
    GridLayout gl_details_white;
    RelativeLayout rl_vitality_black;
    GridLayout gl_details_black;
    RelativeLayout secondLayer;

    TextView tv_vitality_white;
    TextView tv_vitality_black;

    TextView tv_moveRange_white;
    TextView tv_moveRange_black;

    TextView tv_moveType_white;
    TextView tv_moveType_black;

    TextView tv_moveDirections_white;
    TextView tv_moveDirections_black;

    TextView tv_attackRange_white;
    TextView tv_attackRange_black;

    TextView tv_attackStrength_white;
    TextView tv_attackStrength_black;

    TextView tv_attackDirections_white;
    TextView tv_attackDirections_black;

    TextView tv_score_white;
    TextView tv_score_black;

    Chronometer chronometer_white;
    Chronometer chronometer_black;

    long timeWhenStoppedWhite = 0;
    long timeWhenStoppedBlack = 0;

    boolean moveIndex;
    boolean attackIndex;
    boolean teleportIndex;
    boolean actionIndex;

    int fromX;
    int fromY;
    String action = "";
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        rl_activity_game = (RelativeLayout) findViewById(R.id.activity_game);
//      initialize chessboard
        chessboard.initBoard();
//       initialize parameters
//      firstLayer : initialize cellBackground
        gl_cellBackground = (GridLayout) findViewById(R.id.gl_cellBackground);
        initCellBackground();
//      secondlayer :initialization
        gl_pieceBoard = (GridLayout) findViewById(R.id.gl_pieceBoard);
        secondLayer = (RelativeLayout) findViewById(R.id.rl_secondLayer);
        gl_action_white = (GridLayout) findViewById(R.id.gl_action_white);
        gl_action_black = (GridLayout) findViewById(R.id.gl_action_black);
        gl_pieceBoard.getLayoutParams().height = 1080;
        gl_pieceBoard.removeAllViews();
        gl_boarderBoard = (GridLayout) findViewById(R.id.rl_boarderBoard);
        rl_vitality_white = (RelativeLayout) findViewById(R.id.rl_vitality_white);
        rl_vitality_black = (RelativeLayout) findViewById(R.id.rl_vitality_black);
        gl_details_white = (GridLayout) findViewById(R.id.gl_details_white);
        gl_details_black = (GridLayout) findViewById(R.id.gl_details_black);


        for (int i = 0; i < 4; i++) {
            gl_action_white.getChildAt(i).setOnTouchListener(spellOnTouch);
            gl_action_black.getChildAt(i).setOnTouchListener(spellOnTouch);

        }

        tv_vitality_white = (TextView) findViewById(R.id.tv_vitality2_white);
        tv_vitality_black = (TextView) findViewById(R.id.tv_vitality2_black);

        tv_moveRange_white = (TextView) findViewById(R.id.tv_moveRange2_white);
        tv_moveRange_black = (TextView) findViewById(R.id.tv_moveRange2_black);

        tv_moveType_white = (TextView) findViewById(R.id.tv_moveType2_white);
        tv_moveType_black = (TextView) findViewById(R.id.tv_moveType2_black);

        tv_moveDirections_white = (TextView) findViewById(R.id.tv_moveDirections2_white);
        tv_moveDirections_black = (TextView) findViewById(R.id.tv_moveDirections2_black);

        tv_attackRange_white = (TextView) findViewById(R.id.tv_attackRange2_white);
        tv_attackRange_black = (TextView) findViewById(R.id.tv_attackRange2_black);

        tv_attackStrength_white = (TextView) findViewById(R.id.tv_attackStrength2_white);
        tv_attackStrength_black = (TextView) findViewById(R.id.tv_attackStrength2_black);

        tv_attackDirections_white = (TextView) findViewById(R.id.tv_attackDirections2_white);
        tv_attackDirections_black = (TextView) findViewById(R.id.tv_attackDirections2_black);

        tv_score_white = (TextView) findViewById(R.id.tv_score_white);
        tv_score_black = (TextView) findViewById(R.id.tv_score_black);

        chronometer_white = (Chronometer) findViewById(R.id.chronometer_white);
        chronometer_black = (Chronometer) findViewById(R.id.chronometer_black);


//      er layer: initialize cellBoard
        gl_cellBoard = (GridLayout) findViewById(R.id.gl_cellBoard);
        initPieceBoard();
////        chessboard.setBoardWithStr("W",
////                "000000" +
////                        "000000" +
////                        "000000" +
////                        "000000" +
////                        "0000K0" +
////                        "M0000k",
////                "7120000000000000"
////                , "000000",
////                "F0RT0HRT");
//
//        chessboard.setBoardWithStr("W",
//                "000000" +
//                        "g00000" +
//                        "G00000" +
//                        "000000" +
//                        "m00000" +
//                        "M00000", "5777000000000000", "133123", "0HRTFHRT");


        refreshPieceBoard();

        initCellBoard();

        refreshPieceInfo(false, 0, 0);

        gameStart();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
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
                if (chessboard.gV.specialCellsSet().contains("" + (5 - i) + j)) {
                    child.setImageResource(R.drawable.cell_special);
                } else {
                    child.setImageResource(R.drawable.cell);
                }

                child.setScaleType(ImageView.ScaleType.FIT_XY);
                child.setPadding(10, 10, 10, 10);
                child.setLayoutParams(cellBackground_Param);
                gl_cellBackground.addView(child);
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

    private void refreshMoveAttack() {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        if (chessboard.movePlayerInt == 1) {
            if (moveIndex) {
                findViewById(R.id.bn_move_white).getBackground().clearColorFilter();
                findViewById(R.id.bn_move_white).setOnTouchListener(moveAttackOnTouch);
            } else {
                findViewById(R.id.bn_move_white).getBackground().setColorFilter(filter);
                findViewById(R.id.bn_move_white).clearAnimation();
                findViewById(R.id.bn_move_white).setOnTouchListener(null);
            }
            if (attackIndex) {
                findViewById(R.id.bn_attack_white).getBackground().clearColorFilter();
                findViewById(R.id.bn_attack_white).setOnTouchListener(moveAttackOnTouch);
            } else {
                findViewById(R.id.bn_attack_white).getBackground().setColorFilter(filter);
                findViewById(R.id.bn_attack_white).clearAnimation();
                findViewById(R.id.bn_attack_white).setOnTouchListener(null);
            }
            findViewById(R.id.bn_move_black).getBackground().setColorFilter(filter);
            findViewById(R.id.bn_move_black).clearAnimation();
            findViewById(R.id.bn_move_black).setOnTouchListener(null);
            findViewById(R.id.bn_attack_black).getBackground().setColorFilter(filter);
            findViewById(R.id.bn_attack_black).clearAnimation();
            findViewById(R.id.bn_attack_black).setOnTouchListener(null);
        } else {
            if (moveIndex) {
                findViewById(R.id.bn_move_black).getBackground().clearColorFilter();
                findViewById(R.id.bn_move_black).setOnTouchListener(moveAttackOnTouch);
            } else {
                findViewById(R.id.bn_move_black).getBackground().setColorFilter(filter);
                findViewById(R.id.bn_move_black).clearAnimation();
                findViewById(R.id.bn_move_black).setOnTouchListener(null);
            }
            if (attackIndex) {
                findViewById(R.id.bn_attack_black).getBackground().clearColorFilter();
                findViewById(R.id.bn_attack_black).setOnTouchListener(moveAttackOnTouch);
            } else {
                findViewById(R.id.bn_attack_black).getBackground().setColorFilter(filter);
                findViewById(R.id.bn_attack_black).clearAnimation();
                findViewById(R.id.bn_attack_white).setOnTouchListener(null);
            }
            findViewById(R.id.bn_move_white).getBackground().setColorFilter(filter);
            findViewById(R.id.bn_move_white).clearAnimation();
            findViewById(R.id.bn_move_white).setOnTouchListener(null);
            findViewById(R.id.bn_attack_white).getBackground().setColorFilter(filter);
            findViewById(R.id.bn_attack_white).clearAnimation();
            findViewById(R.id.bn_attack_white).setOnTouchListener(null);


        }
    }

    private void refreshSpells() {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        if (chessboard.movePlayerInt == 1) {
            for (int i = 0; i < 4; i++) {
                ((ImageView) gl_action_black.getChildAt(i)).setColorFilter(filter);
                gl_action_black.getChildAt(i).clearAnimation();
                gl_action_black.getChildAt(i).setOnTouchListener(null);
                if (chessboard.playerPiece[chessboard.movePlayerXInt][2].state.equals("n")
                        && !chessboard.playerPiece[chessboard.movePlayerXInt][2].spells.substring(i, i + 1).equals("0")) {
                    ((ImageView) gl_action_white.getChildAt(i)).clearColorFilter();
                    gl_action_white.getChildAt(i).setOnTouchListener(spellOnTouch);
                } else {
                    ((ImageView) gl_action_white.getChildAt(i)).setColorFilter(filter);
                    gl_action_white.getChildAt(i).clearAnimation();
                    gl_action_white.getChildAt(i).setOnTouchListener(null);
                }
            }
        } else {
            for (int i = 0; i < 4; i++) {
                ((ImageView) gl_action_white.getChildAt(i)).setColorFilter(filter);
                gl_action_white.getChildAt(i).clearAnimation();
                gl_action_white.getChildAt(i).setOnTouchListener(null);

                if (chessboard.playerPiece[chessboard.movePlayerXInt][2].state.equals("n")
                        && !chessboard.playerPiece[chessboard.movePlayerXInt][2].spells.substring(i, i + 1).equals("0")) {
                    ((ImageView) gl_action_black.getChildAt(i)).clearColorFilter();
                    gl_action_black.getChildAt(i).setOnTouchListener(spellOnTouch);
                } else {
                    ((ImageView) gl_action_black.getChildAt(i)).setColorFilter(filter);
                    gl_action_white.getChildAt(i).setOnTouchListener(null);
                }
            }
        }
    }

    private void refreshPieceInfo(boolean index, int cellX, int cellY) {
        if (index) {
            if (chessboard.movePlayerInt == 1) {
                tv_vitality_white.setText("" + chessboard.boardPiece[cellX][cellY].vitality + "/" + chessboard.boardPiece[cellX][cellY].getInitVitality());
//                move range
                tv_moveRange_white.setText("" + chessboard.boardPiece[cellX][cellY].getMoveRange());
//                move type
                switch (chessboard.boardPiece[cellX][cellY].getMoveType()) {
                    case "flight": {
                        tv_moveType_white.setText("Flight");
                        break;
                    }
                    case "walk": {
                        tv_moveType_white.setText("Walk");
                        break;
                    }
                    default:
                        tv_moveType_white.setText("/");
                }
//                move Directions
                switch (chessboard.boardPiece[cellX][cellY].getMoveDirections()) {
                    case "+": {
                        tv_moveDirections_white.setText("H+V");
                        break;
                    }
                    case "*": {
                        tv_moveDirections_white.setText("Any");
                        break;
                    }
                    default:
                        tv_moveDirections_white.setText("/");
                }
//                attack range
                if (chessboard.boardPiece[cellX][cellY].getAttackRange() == 0) {
                    tv_attackRange_white.setText("N.A.");
                } else {
                    tv_attackRange_white.setText("" + chessboard.boardPiece[cellX][cellY].getAttackRange());
                }
//                attack Strength
                tv_attackStrength_white.setText("" + chessboard.boardPiece[cellX][cellY].getAttackStrength());
//                attack direcions
                switch (chessboard.boardPiece[cellX][cellY].getAttackDirections()) {
                    case "+": {
                        tv_attackDirections_white.setText("H+V");
                        break;
                    }
                    case "x": {
                        tv_attackDirections_white.setText("Diag");
                        break;
                    }
                    case "": {
                        tv_attackDirections_white.setText("N.A.");
                        break;
                    }
                    default:
                        tv_attackDirections_white.setText("/");
                }
            } else {
                tv_vitality_black.setText("" + chessboard.boardPiece[cellX][cellY].vitality + "/" + chessboard.boardPiece[cellX][cellY].getInitVitality());
//                move range
                tv_moveRange_black.setText("" + chessboard.boardPiece[cellX][cellY].getMoveRange());
//                move type
                switch (chessboard.boardPiece[cellX][cellY].getMoveType()) {
                    case "flight": {
                        tv_moveType_black.setText("Flight");
                        break;
                    }
                    case "walk": {
                        tv_moveType_black.setText("Walk");
                        break;
                    }
                    default:
                        tv_moveType_black.setText("/");
                }
//                move Directions
                switch (chessboard.boardPiece[cellX][cellY].getMoveDirections()) {
                    case "+": {
                        tv_moveDirections_black.setText("H+V");
                        break;
                    }
                    case "*": {
                        tv_moveDirections_black.setText("Any");
                        break;
                    }
                    default:
                        tv_moveDirections_black.setText("/");
                }
//                attack range
                if (chessboard.boardPiece[cellX][cellY].getAttackRange() == 0) {
                    tv_attackRange_black.setText("N.A.");
                } else {
                    tv_attackRange_black.setText("" + chessboard.boardPiece[cellX][cellY].getAttackRange());
                }
//                attack Strength
                tv_attackStrength_black.setText("" + chessboard.boardPiece[cellX][cellY].getAttackStrength());
//                attack direcions
                switch (chessboard.boardPiece[cellX][cellY].getAttackDirections()) {
                    case "+": {
                        tv_attackDirections_black.setText("H+V");
                        break;
                    }
                    case "x": {
                        tv_attackDirections_black.setText("Diag");
                        break;
                    }
                    case "": {
                        tv_attackDirections_black.setText("N.A.");
                        break;
                    }
                    default:
                        tv_attackDirections_black.setText("/");
                }
            }
        } else {
            tv_vitality_white.setText("0/0");
            tv_moveRange_white.setText("/");
            tv_moveType_white.setText("/");
            tv_moveDirections_white.setText("/");
            tv_attackRange_white.setText("/");
            tv_attackStrength_white.setText("/");
            tv_attackDirections_white.setText("/");
            tv_vitality_black.setText("0/0");
            tv_moveRange_black.setText("/");
            tv_moveType_black.setText("/");
            tv_moveDirections_black.setText("/");
            tv_attackRange_black.setText("/");
            tv_attackStrength_black.setText("/");
            tv_attackDirections_black.setText("/");
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            if (chessboard.movePlayerInt == 1) {
//                refresh piece info board
                for (int i = 0; i < rl_vitality_white.getChildCount(); i++) {
                    if (rl_vitality_white.getChildAt(i) instanceof ImageView) {
                        ((ImageView) rl_vitality_black.getChildAt(i)).setColorFilter(filter);
                        ((ImageView) rl_vitality_white.getChildAt(i)).clearColorFilter();
                    }
                }
                for (int i = 0; i < gl_details_white.getChildCount(); i++) {
                    if (gl_details_black.getChildAt(i) instanceof ImageView) {
                        ((ImageView) gl_details_black.getChildAt(i)).setColorFilter(filter);
                        ((ImageView) gl_details_white.getChildAt(i)).clearColorFilter();
                    }
                }
            } else {
//                refresh piece info board
                for (int i = 0; i < rl_vitality_white.getChildCount(); i++) {
                    if (rl_vitality_black.getChildAt(i) instanceof ImageView) {
                        ((ImageView) rl_vitality_white.getChildAt(i)).setColorFilter(filter);
                        ((ImageView) rl_vitality_black.getChildAt(i)).clearColorFilter();
                    }
                }
                for (int i = 0; i < gl_details_white.getChildCount(); i++) {
                    if (gl_details_white.getChildAt(i) instanceof ImageView) {
                        ((ImageView) gl_details_white.getChildAt(i)).setColorFilter(filter);
                        ((ImageView) gl_details_black.getChildAt(i)).clearColorFilter();
                    }
                }
            }
        }
    }

    private void refreshScoreTime() {
        tv_score_white.setText("" + chessboard.playerScore[0] + "/" + chessboard.playerScore[1]);
        tv_score_black.setText("" + chessboard.playerScore[1] + "/" + chessboard.playerScore[0]);
        if (chessboard.movePlayerInt == 1) {
            timeWhenStoppedBlack = SystemClock.elapsedRealtime() - chronometer_black.getBase();
            chronometer_black.stop();
            chronometer_white.setBase(SystemClock.elapsedRealtime() - timeWhenStoppedWhite);
            chronometer_white.start();
        } else {
            timeWhenStoppedWhite = SystemClock.elapsedRealtime() - chronometer_white.getBase();
            chronometer_white.stop();
            chronometer_black.setBase(SystemClock.elapsedRealtime() - timeWhenStoppedBlack);
            chronometer_black.start();
        }
    }

    private boolean cellOnTouch(View v, MotionEvent event) {
        if (gl_pieceBoard.getChildCount() > 36) {
            gl_pieceBoard.removeViews(36, gl_pieceBoard.getChildCount() - 36);
        }

        int cellX = 5 - Integer.parseInt(v.getTag().toString().substring(4, 5));
        int cellY = Integer.parseInt(v.getTag().toString().substring(5, 6));
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                for (int i = 0; i < gl_pieceBoard.getChildCount(); i++) {
                    gl_pieceBoard.getChildAt(i).clearAnimation();
                }
                if (chessboard.boardPiece[cellX][cellY].getTypeInt() == chessboard.movePlayerInt) {
                    animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.touch_down);
                    gl_pieceBoard.getChildAt(6 * cellX + cellY).startAnimation(animation);

                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                refreshPieceInfo(true, cellX, cellY);
                if (action.equals("")) {
                    if (chessboard.boardPiece[cellX][cellY].getTypeInt() == chessboard.movePlayerInt
                            && chessboard.boardPiece[cellX][cellY].state.equals("n")) {
                        if (!chessboard.getMoveCells(cellX, cellY).isEmpty()) {
                            moveIndex = true;
                        }
                        if (!chessboard.getAttackCells(cellX, cellY).isEmpty()) {
                            attackIndex = true;
                        }
                        fromX = cellX;
                        fromY = cellY;
                    } else {
                        moveIndex = false;
                        attackIndex = false;
                    }
                    refreshMoveAttack();
                    break;
                } else {
                    switch (action) {
                        case "M": {
                            if (moveIndex) {
                                if (chessboard.isAction(action + (fromX + 1) + (fromY + 1) + (cellX + 1) + (cellY + 1))) {
                                    actionIndex = true;
                                    moveIndex = false;
                                } else {
                                    moveIndex = false;
                                }
                            }
                            break;
                        }
                        case "A": {
                            if (attackIndex) {
                                if (chessboard.isAction(action + (fromX + 1) + (fromY + 1) + (cellX + 1) + (cellY + 1))) {
                                    actionIndex = true;
                                    attackIndex = false;
                                } else {
                                    actionIndex = false;
                                    attackIndex = false;
                                    action = "";
                                }
                            }
                            break;
                        }
                        case "F": {
                            if (chessboard.isAction(action + (cellX + 1) + (cellY + 1) + 0 + 0)) {
                                actionIndex = true;
                            } else {
                                if (chessboard.movePlayerInt == 1) {
                                    gl_action_white.getChildAt(0).getAnimation().cancel();
                                } else {
                                    gl_action_black.getChildAt(0).getAnimation().cancel();
                                }
                            }
                            break;
                        }
                        case "H": {
                            if (chessboard.isAction(action + (cellX + 1) + (cellY + 1) + 0 + 0)) {
                                actionIndex = true;
                                gl_pieceBoard.getChildAt(6*cellX+cellY).clearAnimation();
                            } else {
                                if (chessboard.movePlayerInt == 1) {
                                    gl_action_white.getChildAt(1).getAnimation().cancel();
                                } else {
                                    gl_action_black.getChildAt(1).getAnimation().cancel();
                                }
                            }
                            break;
                        }
                        case "R": {
                            if (chessboard.isAction(action + (cellX + 1) + (cellY + 1) + 0 + 0)) {
                                actionIndex = true;
                            } else {
                                if (chessboard.movePlayerInt == 1) {
                                    gl_action_white.getChildAt(2).getAnimation().cancel();
                                } else {
                                    gl_action_black.getChildAt(2).getAnimation().cancel();
                                }
                            }
                            break;
                        }
                        case "T": {
                            if (teleportIndex) {
                                if (chessboard.isAction(action + (fromX + 1) + (fromY + 1) + (cellX + 1) + (cellY + 1))) {
                                    actionIndex = true;
                                    teleportIndex = false;
                                } else {
                                    action = "";
                                    if (chessboard.movePlayerInt == 1) {
                                        gl_action_white.getChildAt(3).getAnimation().cancel();
                                    } else {
                                        gl_action_black.getChildAt(3).getAnimation().cancel();
                                    }
                                }
                            } else {
                                fromX = cellX;
                                fromY = cellY;
                                teleportIndex = true;
                            }
                            break;
                        }


                    }
                    if (actionIndex) {
                        action = "";
                        fromX = 0;
                        fromY = 0;
                        moveIndex = false;
                        teleportIndex = false;
                        actionIndex = false;
                        refreshPieceBoard();
                        refreshBoarderBoard();
                        refreshSpells();
                        refreshMoveAttack();
                        refreshPieceInfo(false, 0, 0);
                        refreshScoreTime();
                        if (gameIsEnd()) {
                            chronometer_white.stop();
                            chronometer_black.stop();
                        }
                    } else {
                        if (!(action.equals("T") && teleportIndex)) {
                            action = "";
                            refreshSpells();
                            refreshMoveAttack();
                            refreshPieceBoard();
                            refreshPieceInfo(true, cellX, cellY);
                        }
                    }
                }
            }
        }

        return true;
    }

    private boolean spellOnTouch(View v, MotionEvent event) {
        moveIndex = false;
        attackIndex = false;
        action = "";
        refreshMoveAttack();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if (gl_pieceBoard.getChildCount() > 36) {
                    gl_pieceBoard.removeViews(36, gl_pieceBoard.getChildCount() - 36);
                }
                for (int i = 0; i < gl_pieceBoard.getChildCount(); i++) {
                    gl_pieceBoard.getChildAt(i).clearAnimation();
                }
                if (chessboard.movePlayerInt == 1) {
                    for (int i = 0; i < 4; i++) {
                        gl_action_white.getChildAt(i).clearAnimation();
                    }
                } else {
                    for (int i = 0; i < 4; i++) {
                        gl_action_black.getChildAt(i).clearAnimation();
                    }
                }
                switch (v.getTag().toString()) {
                    case "Heal": {
                        action = "H";
                        System.out.println("Action= H");
                        break;
                    }
                    case "Teleport": {
                        action = "T";
                        System.out.println("Action= T");
                        teleportIndex = false;
                        break;
                    }
                    case "Revive": {
                        action = "R";
                        System.out.println("Action= R");
                        int reviveX;
                        int reviveY;
                        for (int r = 0; r < 8; r++) {
                            reviveX = chessboard.playerPiece[chessboard.movePlayerXInt][r].getInitPositionX();
                            reviveY = chessboard.playerPiece[chessboard.movePlayerXInt][r].getInitPositionY();
                            if (r != 2 && chessboard.playerPiece[chessboard.movePlayerXInt][r].state.equals("d")
                                    && chessboard.boardPiece[reviveX][reviveY].getTypeInt() != chessboard.movePlayerInt) {
                                AppCompatImageView revivePieceImage = new AppCompatImageView(getBaseContext());
                                GridLayout.LayoutParams revivePieceImage_Param = new GridLayout.LayoutParams();
                                revivePieceImage_Param.width = 180;
                                revivePieceImage_Param.height = 180;
                                revivePieceImage_Param.columnSpec = GridLayout.spec(reviveY, 1, 1f);
                                revivePieceImage_Param.rowSpec = GridLayout.spec(5 - reviveX, 1, 1f);
                                revivePieceImage.setLayoutParams(revivePieceImage_Param);
                                revivePieceImage.setPadding(20, 20, 20, 20);
                                Resources res = this.getResources();
                                revivePieceImage.setImageResource(res.getIdentifier(chessboard.playerPiece[chessboard.movePlayerXInt][r].getImageName(), "drawable", this.getPackageName()));
                                if (chessboard.movePlayerInt == 1) {
                                    revivePieceImage.setBackgroundResource(R.drawable.piece_bg_white);
                                } else {
                                    revivePieceImage.setBackgroundResource(R.drawable.piece_bg_black);
                                    revivePieceImage.setRotation(180);
                                }
                                ColorMatrix matrix = new ColorMatrix();
                                matrix.setSaturation(0);
                                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                                revivePieceImage.setColorFilter(filter);
                                gl_pieceBoard.addView(revivePieceImage);
                            }
                        }
                        break;
                    }
                    case "Freeze": {
                        action = "F";
                        System.out.println("Action= F");
                        break;
                    }
                }
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.touch_down);
                v.startAnimation(animation);
                break;
            }
            case MotionEvent.ACTION_UP: {
//                    animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.touch_up);
//                    v.startAnimation(animation);
                break;
            }
        }

        return true;
    }

    private boolean moveAttackOnTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if (chessboard.movePlayerInt == 1) {
                    findViewById(R.id.bn_move_white).clearAnimation();
                    findViewById(R.id.bn_attack_white).clearAnimation();
                } else {
                    findViewById(R.id.bn_move_black).clearAnimation();
                    findViewById(R.id.bn_attack_black).clearAnimation();
                }
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.touch_down);
                v.startAnimation(animation);


                if (gl_pieceBoard.getChildCount() > 36) {
                    gl_pieceBoard.removeViews(36, gl_pieceBoard.getChildCount() - 36);
                }
                Set cellSet = new TreeSet();
                if (v.getTag().equals("Move")) {
                    cellSet = chessboard.getMoveCells(fromX, fromY);
                    action = "M";
                }
                if (v.getTag().equals("Attack")) {
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
                    if (v.getTag().equals("Move")) {
                        target.setImageResource(R.drawable.piece_target_move);
                    }
                    if (v.getTag().equals("Attack")) {
                        target.setImageResource(R.drawable.piece_target_attack);
                    }
                    gl_pieceBoard.addView(target);
                    Animation targetAppear = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.target_appear);
                    target.startAnimation(targetAppear);
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                break;
            }
        }
        return false;
    }

    private boolean gameIsEnd() {

        boolean index = false;
        if (!chessboard.resultStr.equals("")) {
            index = true;
            RelativeLayout rl_game_end = (RelativeLayout) findViewById(R.id.rl_game_end);
            rl_game_end.setBackgroundColor(Color.BLACK);
            rl_game_end.setAlpha((float) 0.9);
            rl_game_end.setClickable(true);

            RelativeLayout.LayoutParams rl_game_end_white_Params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            rl_game_end_white_Params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            rl_game_end_white_Params.setMargins(0, 1210, 0, 0);
            TextView tv_end_white = new TextView(getBaseContext());
            String endMessageWhite = "";
            String endMessageBlack = "";
            switch (chessboard.resultStr) {
                case "WHITE": {
                    endMessageWhite += "YOU WIN!";
                    endMessageBlack += "YOU LOSE!";
                    break;
                }
                case "BLACK": {
                    endMessageWhite += "YOU LOSE!";
                    endMessageBlack += "YOU WIN!";
                    break;
                }
                case "DRAW": {
                    endMessageWhite += "DRAW!";
                    endMessageBlack += "DRAW!";
                    break;
                }
            }
            tv_end_white.setText(endMessageWhite + "\n" + "SCORE: " + chessboard.playerScore[0]);
            tv_end_white.setTextColor(Color.WHITE);
            tv_end_white.setTypeface(Typeface.MONOSPACE);
            tv_end_white.setTextSize(22);
            tv_end_white.setLayoutParams(rl_game_end_white_Params);

            RelativeLayout.LayoutParams rl_game_end_black_Params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            rl_game_end_black_Params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            rl_game_end_black_Params.setMargins(0, 520, 0, 0);
            TextView tv_end_black = new TextView(getBaseContext());
            tv_end_black.setRotation(180);


            tv_end_black.setText(endMessageBlack + "\n" + "SCORE: " + chessboard.playerScore[1]);
            ;
            tv_end_black.setTextColor(Color.WHITE);
            tv_end_black.setTypeface(Typeface.MONOSPACE);
            tv_end_black.setTextSize(22);
            tv_end_black.setLayoutParams(rl_game_end_black_Params);

            rl_activity_game.addView(tv_end_white);
            rl_activity_game.addView(tv_end_black);
            rl_game_end.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.game_end));
            tv_end_white.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.game_end));
            tv_end_black.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.game_end));
        }
        return index;
    }

    private void gameStart() {

        final Animation an_count_down = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.game_start);
        final RelativeLayout rl_game_start = (RelativeLayout) findViewById(R.id.rl_game_start);
        rl_game_start.setBackgroundColor(Color.BLACK);
        rl_game_start.setAlpha((float) 0.9);
        rl_game_start.setClickable(true);

        final RelativeLayout.LayoutParams rl_game_start_Params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        rl_game_start_Params.addRule(RelativeLayout.CENTER_IN_PARENT);
        rl_game_start_Params.height = 200;
        final ImageView iv_count_down3 = new ImageView(getBaseContext());
        iv_count_down3.setLayoutParams(rl_game_start_Params);
        iv_count_down3.setImageResource(R.drawable.count_down_3);

        rl_game_start.addView(iv_count_down3);
        iv_count_down3.startAnimation(an_count_down);
        iv_count_down3.getAnimation().setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv_count_down3.setImageResource(R.drawable.count_down_2);
                iv_count_down3.startAnimation(an_count_down);
                iv_count_down3.getAnimation().setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        iv_count_down3.setImageResource(R.drawable.count_down_1);
                        iv_count_down3.startAnimation(an_count_down);
                        iv_count_down3.getAnimation().setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                rl_activity_game.removeView(rl_game_start);
                                initBoarderBoard();
                                refreshBoarderBoard();
                                refreshSpells();
                                refreshMoveAttack();
                                chronometer_white.start();
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

//    private void storeGameData(Context context) {
//
//        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("data/data/it.polimi.group08.db/databased/gamedata.db", null);
//        Cursor c = db.rawQuery("SELECT count(*) FROM gamedata WHERE type='table' AND name=game_history'", null);
//        if (c.getInt(0) == 0) {
//            String create_table = "create table if not exists Student(name text primary key, code integer)";
//        }
//        String create_table = "create table if not exists Student(name text primary key, code integer)";
//    }

    private View.OnTouchListener moveAttackOnTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            moveAttackOnTouch(view, motionEvent);
            return false;
        }
    };
    private View.OnTouchListener cellOnTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            cellOnTouch(view, motionEvent);
            return false;
        }
    };
    private View.OnTouchListener spellOnTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEevent) {
            spellOnTouch(view, motionEevent);
            return false;
        }
    };
}









