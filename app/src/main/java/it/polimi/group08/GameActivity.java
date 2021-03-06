package it.polimi.group08;


import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Set;
import java.util.TreeSet;

import it.polimi.group08.boards.Chessboard;
import it.polimi.group08.pieces.Piece;

import static it.polimi.group08.R.drawable.cell;


public class GameActivity extends AppCompatActivity {
    MyApplication app;
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
    RelativeLayout rl_combat;

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

    // the two variables are used in the timer for two player
    long timeWhenStoppedWhite = 0;
    long timeWhenStoppedBlack = 0;
    //moveindex indict whether the piece have available cells that can be moved on
    boolean moveIndex;
    //moveindex indict whether the piece have available cells that can be attacked
    boolean attackIndex;
    //teleportIndex indict that when the action teleported is selected, whether the selected piece is the first piece or the second piece
    boolean teleportIndex;
    //actionIndex indict that whether the action is valid or invalid
    boolean actionIndex;


    int vitalityLeft;
    int vitalityRight;

    //    for action "A" ,"M", "F" , they need two coordinates for two pieces, fromX and fromY represent the coordinate of the first piece
    int fromX;
    int fromY;
    String action = "";
    Animation animation;

    MediaPlayer mp;
    MediaPlayer backgroundMusic;

    @Override
    protected void onStop() {
        super.onPause();
        musicStart(false);

    }

    @Override
    protected void onResume() {
        super.onResume();
//        hide navigation bar and set the mode to immersive mode
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        musicStart(true);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        musicStart(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //        hide navigation bar and set the mode to immersive mode
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        app = ((MyApplication) getApplicationContext());

        rl_activity_game = (RelativeLayout) findViewById(R.id.activity_game);
//      initialize chessboard
        chessboard.initBoard();
//       initialize parameters
//      firstLayer : initialize cellBackground
        gl_cellBackground = (GridLayout) findViewById(R.id.gl_cellBackground);
        initCellBackground();
//      initialization of secondLayer
//        initialize different viriables
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

        rl_combat = (RelativeLayout) findViewById(R.id.rl_combat);

//        set setOnTouchListener to all the spells Imageview
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


//      initialize cellBoard
        gl_cellBoard = (GridLayout) findViewById(R.id.gl_cellBoard);
        initPieceBoard();
        chessboard.initBoard();

//      chessboard.setBoardWithStr("W",
//                        "000000" +
//                        "g00000" +
//                        "G00000" +
//                        "000000" +
//                        "m00000" +
//                        "M00000", "5777000000000000", "133123", "0HRTFHRT");


        refreshPieceBoard();
        initCellBoard();
        refreshPieceInfo(false, 0, 0);
        backgroundMusic = MediaPlayer.create(this, R.raw.game_background);
        backgroundMusic.setAudioStreamType(AudioManager.STREAM_MUSIC);
        musicStart(true);
        gameStart();
    }

    //    cellbackground is the layout for the background of the chessboard, it contains images of grass and special cells
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
                    child.setImageResource(cell);
                }

                child.setScaleType(ImageView.ScaleType.FIT_XY);
                child.setPadding(10, 10, 10, 10);
                child.setLayoutParams(cellBackground_Param);
                gl_cellBackground.addView(child);
            }
        }
    }

    //    pieceBoard is the layout for the pieces of the chessboard, it contains sixteen images pieces in total
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

    //    cellboard is the layout to get the position where the user touch or click , it always in the top the activity and cannot be covered by other layout during the playing
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

    //   boarderboard is the layout to flash the boarder of the piece . For example, now it is white's turn, all the white player's piece will have a white flash boarder
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

    //    after an valid action, the pieceboard is needed to be refreshed
    private void refreshPieceBoard() {
//        the strategy is that, compare the imageview in the pieceboard and the piece in chessboard.boardPiece one by one in a sequentially way
//        if the piece in chessboard.boardPiece is the same as it is in last action, this imageview will be ignored
//        if  the piece in chessboard.boardPiece is changed , the imageview will be changed as the piece's position and state
        if (gl_pieceBoard.getChildCount() > 36) {
            gl_pieceBoard.removeViews(36, gl_pieceBoard.getChildCount() - 36);
        }
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 6; c++) {
                Resources res = this.getResources();
                AppCompatImageView pieceImage = (AppCompatImageView) gl_pieceBoard.getChildAt(c + 6 * r);
                pieceImage.clearAnimation();
                //                if the piece's state is "n", change the piece's image to it's normal image
                if (pieceImage.getTag().toString().substring(0, 1).equals(chessboard.boardPiece[r][c].getType())) {
                    if (chessboard.boardPiece[r][c].state.equals("n") && !chessboard.boardPiece[r][c].isNorImg) {
                        pieceImage.setImageResource(res.getIdentifier(chessboard.boardPiece[r][c].getImageName(), "drawable", this.getPackageName()));
                        chessboard.boardPiece[r][c].isNorImg = true;
                        //                if the piece's state is "f", change the piece's image to it's frozen image
                    } else if (chessboard.boardPiece[r][c].state.equals("f") && chessboard.boardPiece[r][c].isNorImg) {
                        pieceImage.setImageResource(res.getIdentifier(chessboard.boardPiece[r][c].getImageName() + "_frozen", "drawable", this.getPackageName()));
                        chessboard.boardPiece[r][c].isNorImg = false;
                    }
                } else {
                    pieceImage.setTag(chessboard.boardPiece[r][c].getType() + r + c);
                    if (chessboard.boardPiece[r][c].getTypeInt() == 1) {
                        pieceImage.setBackgroundResource(R.drawable.piece_bg_white);
                        //                if the piece's state is "n", change the piece's image to it's normal image
                        if (chessboard.boardPiece[r][c].state.equals("n")) {
                            pieceImage.setImageResource(res.getIdentifier(chessboard.boardPiece[r][c].getImageName(), "drawable", this.getPackageName()));
                        } else {
                            //                if the piece's state is "f", change the piece's image to it's frozen image
                            pieceImage.setImageResource(res.getIdentifier(chessboard.boardPiece[r][c].getImageName() + "_frozen", "drawable", this.getPackageName()));
                        }
                        pieceImage.setRotation(0);
                    } else if (chessboard.boardPiece[r][c].getTypeInt() == -1) {
                        pieceImage.setBackgroundResource(R.drawable.piece_bg_black);
                        if (chessboard.boardPiece[r][c].state.equals("n")) {
                            pieceImage.setImageResource(res.getIdentifier(chessboard.boardPiece[r][c].getImageName(), "drawable", this.getPackageName()));
                        } else {
                            pieceImage.setImageResource(res.getIdentifier(chessboard.boardPiece[r][c].getImageName() + "_frozen", "drawable", this.getPackageName()));
                        }
                        pieceImage.setRotation(180);
                    } else {
                        pieceImage.setImageResource(0);
                        pieceImage.setBackgroundResource(0);
                    }
                }
            }
        }
    }

    //    after a valid action the boarder of pieces are needed to be refreshed
    private void refreshBoarderBoard() {
//        the strategy is that, if now it is white's turn, all the black piece's boarder will be cleared and  all the white pieces will be add boarders
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

    //    after the user select a piece or select an action , the move and attack button need to be refreshed
    private void refreshMoveAttack() {
//     For move button, the strategy is that, only when the user select it's own piece and meanwhile the piece have pieces that can move to, the move button will be activated
//     For attack button, the strategy is that, only when the user select it's own piece and meanwhile the piece have pieces that can be attacked, the attack button will be activated
//        after an invalid action or an action that are not attack and move, both move button and attack button will be deactivated
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
//        the strategy is that, if it is white player's turn, all of your spell will be disabled
//        for white player, if the white mage is dead, all the white's spells button will be disabled
//        if white mage is alive, disabled corresponding spell according to the unused spell

        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        if (chessboard.movePlayerInt == 1) {
            for (int i = 0; i < 4; i++) {
                gl_action_black.getChildAt(i).getBackground().setColorFilter(filter);
                gl_action_black.getChildAt(i).clearAnimation();
                gl_action_black.getChildAt(i).setOnTouchListener(null);
                if (chessboard.playerPiece[chessboard.movePlayerXInt][2].state.equals("n")
                        && !chessboard.playerPiece[chessboard.movePlayerXInt][2].spells.substring(i, i + 1).equals("0")) {
                    gl_action_white.getChildAt(i).getBackground().clearColorFilter();
                    gl_action_white.getChildAt(i).setOnTouchListener(spellOnTouch);
                } else {
                    gl_action_white.getChildAt(i).getBackground().setColorFilter(filter);
                    gl_action_white.getChildAt(i).clearAnimation();
                    gl_action_white.getChildAt(i).setOnTouchListener(null);
                }
            }
        } else {
            for (int i = 0; i < 4; i++) {
                gl_action_white.getChildAt(i).getBackground().setColorFilter(filter);
                gl_action_white.getChildAt(i).clearAnimation();
                gl_action_white.getChildAt(i).setOnTouchListener(null);

                if (chessboard.playerPiece[chessboard.movePlayerXInt][2].state.equals("n")
                        && !chessboard.playerPiece[chessboard.movePlayerXInt][2].spells.substring(i, i + 1).equals("0")) {
                    gl_action_black.getChildAt(i).getBackground().clearColorFilter();
                    gl_action_black.getChildAt(i).setOnTouchListener(spellOnTouch);
                } else {
                    gl_action_black.getChildAt(i).getBackground().setColorFilter(filter);
                    gl_action_white.getChildAt(i).setOnTouchListener(null);
                }
            }
        }
    }

    private void refreshPieceInfo(boolean index, int cellX, int cellY) {
//        after the user clicked a piece. the details of the piece will be showed in one of the two players area
//        the strategy is that, if now it is white player's turn , the piece information only be showed in white player's area
        if (index) {
            if (chessboard.movePlayerInt == 1) {
                if (chessboard.boardPiece[cellX][cellY].state.equals("f")) {
                    if (chessboard.boardPiece[cellX][cellY].getTypeInt() == 1) {
                        tv_vitality_white.setText("" + chessboard.boardPiece[cellX][cellY].vitality + "/" + chessboard.boardPiece[cellX][cellY].getInitVitality() + "(" + chessboard.frozenPieceStr.substring(2, 3) + ")");
                    } else {
                        tv_vitality_white.setText("" + chessboard.boardPiece[cellX][cellY].vitality + "/" + chessboard.boardPiece[cellX][cellY].getInitVitality() + "(" + chessboard.frozenPieceStr.substring(5, 6) + ")");
                    }
                } else {
                    tv_vitality_white.setText("" + chessboard.boardPiece[cellX][cellY].vitality + "/" + chessboard.boardPiece[cellX][cellY].getInitVitality());
                }

//                move range
                String moverange = "" + chessboard.boardPiece[cellX][cellY].getMoveRange();
                tv_moveRange_white.setText(moverange);
//                move type
                switch (chessboard.boardPiece[cellX][cellY].getMoveType()) {
                    case "flight": {
                        tv_moveType_white.setText(R.string.Flight);
                        break;
                    }
                    case "walk": {
                        tv_moveType_white.setText(R.string.Walk);
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
                        tv_moveDirections_white.setText(R.string.Any);
                        break;
                    }
                    default:
                        tv_moveDirections_white.setText("/");
                }
//                attack range
                if (chessboard.boardPiece[cellX][cellY].getAttackRange() == 0) {
                    tv_attackRange_white.setText("N.A.");
                } else {
                    String attackRange = "" + chessboard.boardPiece[cellX][cellY].getAttackRange();
                    tv_attackRange_white.setText(attackRange);
                }
//                attack Strength
                String attackStrength = "" + chessboard.boardPiece[cellX][cellY].getAttackStrength();
                tv_attackStrength_white.setText(attackStrength);
//                attack direcions
                switch (chessboard.boardPiece[cellX][cellY].getAttackDirections()) {
                    case "+": {
                        tv_attackDirections_white.setText("H+V");
                        break;
                    }
                    case "x": {
                        tv_attackDirections_white.setText(R.string.Diag);
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
                if (chessboard.boardPiece[cellX][cellY].state.equals("f")) {
                    if (chessboard.boardPiece[cellX][cellY].getTypeInt() == 1) {
                        tv_vitality_black.setText("" + chessboard.boardPiece[cellX][cellY].vitality + "/" + chessboard.boardPiece[cellX][cellY].getInitVitality() + "(" + chessboard.frozenPieceStr.substring(2, 3) + ")");
                    } else {
                        tv_vitality_black.setText("" + chessboard.boardPiece[cellX][cellY].vitality + "/" + chessboard.boardPiece[cellX][cellY].getInitVitality() + "(" + chessboard.frozenPieceStr.substring(5, 6) + ")");
                    }
                } else {
                    tv_vitality_black.setText("" + chessboard.boardPiece[cellX][cellY].vitality + "/" + chessboard.boardPiece[cellX][cellY].getInitVitality());
                }
//                move range
                String moveRange = "" + chessboard.boardPiece[cellX][cellY].getMoveRange();
                tv_moveRange_black.setText(moveRange);
//                move type
                switch (chessboard.boardPiece[cellX][cellY].getMoveType()) {
                    case "flight": {
                        tv_moveType_black.setText(R.string.Flight);
                        break;
                    }
                    case "walk": {
                        tv_moveType_black.setText(R.string.Walk);
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
                        tv_moveDirections_black.setText(R.string.Any);
                        break;
                    }
                    default:
                        tv_moveDirections_black.setText("/");
                }
//                attack range
                if (chessboard.boardPiece[cellX][cellY].getAttackRange() == 0) {
                    tv_attackRange_black.setText("N.A.");
                } else {
                    String attackRange = "" + chessboard.boardPiece[cellX][cellY].getAttackRange();
                    tv_attackRange_black.setText(attackRange);
                }
//                attack Strength
                String attackStrength = "" + chessboard.boardPiece[cellX][cellY].getAttackStrength();
                tv_attackStrength_black.setText(attackStrength);
//                attack direcions
                switch (chessboard.boardPiece[cellX][cellY].getAttackDirections()) {
                    case "+": {
                        tv_attackDirections_black.setText("H+V");
                        break;
                    }
                    case "x": {
                        tv_attackDirections_black.setText(R.string.Diag);
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
//        according the round number, action and time refresh the round number, score and timer
        String round = "Round:" + (chessboard.getTurnsNum() + 1) / 2;
        ((TextView) findViewById(R.id.tv_round_white)).setText(round);
        ((TextView) findViewById(R.id.tv_round_black)).setText(round);
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
//        get the position the touched cell
        int cellX = 5 - Integer.parseInt(v.getTag().toString().substring(4, 5));
        int cellY = Integer.parseInt(v.getTag().toString().substring(5, 6));
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
//                if action is not revive, firstly remove all the redundant view in gl_pieceBoard
                if (!action.equals("R")) {
                    if (gl_pieceBoard.getChildCount() > 36) {
                        gl_pieceBoard.removeViews(36, gl_pieceBoard.getChildCount() - 36);
                    }
                }
//                if action is not attack and move, firstly set moveIndex and attackIndex
                if (!action.equals("M") && !action.equals("A")) {
                    moveIndex = false;
                    attackIndex = false;
                }
//                clear the animtion of pieces
                for (int i = 0; i < gl_pieceBoard.getChildCount(); i++) {
                    gl_pieceBoard.getChildAt(i).clearAnimation();
                }
//                get the summon music according the piece's type
                if (chessboard.boardPiece[cellX][cellY].getTypeInt() == chessboard.movePlayerInt) {
                    animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.touch_down);
                    gl_pieceBoard.getChildAt(6 * cellX + cellY).startAnimation(animation);
                    if (action.equals("")) {
                        switch (chessboard.boardPiece[cellX][cellY].getPlayerY()) {
                            case 0: {
                                playSfx(R.raw.piece_giant_summon);
                                break;
                            }
                            case 1: {
                                playSfx(R.raw.piece_dragon_summon);
                                break;
                            }
                            case 2: {
                                playSfx(R.raw.piece_mage_summon);
                                break;
                            }
                            case 3: {
                                playSfx(R.raw.piece_archer_summon);
                                break;
                            }
                            case 4: {
                                playSfx(R.raw.piece_knight_summon);
                                break;
                            }
                            case 5: {
                                playSfx(R.raw.piece_squire_summon);
                                break;
                            }
                            case 6: {
                                playSfx(R.raw.piece_knight_summon);
                                break;
                            }
                            case 7: {
                                playSfx(R.raw.piece_squire_summon);
                                break;
                            }
                        }
                    }

                } else if (chessboard.boardPiece[cellX][cellY].getTypeInt() == 0) {
                    playSfx(R.raw.touch_piece_empty);
                } else {
                    playSfx(R.raw.touch_piece_oppoent);
                }
                refreshPieceInfo(true, cellX, cellY);
                break;
            }
            case MotionEvent.ACTION_UP: {
                actionIndex = false;
//                if the action is null, set fromX and fromY
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
                    }
//                    refresh move and attack button
                    refreshMoveAttack();
                } else {
                    boolean combatIndex = false;
                    switch (action) {
                        case "M": {
                            if (moveIndex) {
//                              before the action is executed, set combat variables
                                int combatMovePlayerInt = 0;
                                int combatAtkStrL = 0;
                                int combatVitalityL = 0;
                                int combatTypeIntL = 0;
                                int combatAtkStrR = 0;
                                int combatVitalityR = 0;
                                int combatTypeIntR = 0;
                                if (chessboard.boardPiece[fromX][fromY].getTypeInt() * chessboard.boardPiece[cellX][cellY].getTypeInt() == -1) {
//                                  if the action is executed successfully, the action can trigger combat, then set combatIndex =true;
                                    combatIndex = true;
                                    combatMovePlayerInt = chessboard.movePlayerInt;
                                    combatAtkStrL = chessboard.boardPiece[fromX][fromY].getAttackStrength();
                                    combatVitalityL = chessboard.boardPiece[fromX][fromY].vitality;
                                    combatTypeIntL = chessboard.boardPiece[fromX][fromY].getPlayerY();

                                    if (!chessboard.boardPiece[cellX][cellY].state.equals("f")) {
                                        combatAtkStrR = chessboard.boardPiece[cellX][cellY].getAttackStrength();
                                    }
                                    combatVitalityR = chessboard.boardPiece[cellX][cellY].vitality;
                                    combatTypeIntR = chessboard.boardPiece[cellX][cellY].getPlayerY();
                                }
                                if (chessboard.isAction(action + (fromX + 1) + (fromY + 1) + (cellX + 1) + (cellY + 1))) {
                                    actionIndex = true;
                                    if (combatIndex) {
                                        combatAnimation(combatMovePlayerInt, combatAtkStrL, combatVitalityL, combatTypeIntL, combatAtkStrR, combatVitalityR, combatTypeIntR);
                                    } else {
                                        playSfx(R.raw.action_move);
                                    }
                                }
                                moveIndex = false;
                            }
                            break;
                        }
                        case "A": {
                            if (attackIndex) {
                                if (chessboard.isAction(action + (fromX + 1) + (fromY + 1) + (cellX + 1) + (cellY + 1))) {
                                    actionIndex = true;
                                    vitalityChange("-" + chessboard.boardPiece[fromX][fromY].getAttackStrength(), cellX, cellY);
//                                    get attack music according the piece's type
                                    switch (chessboard.boardPiece[fromX][fromY].getPlayerY()) {
                                        case 0: {
                                            playSfx(R.raw.piece_giant_attack);
                                            break;
                                        }
                                        case 1: {
                                            playSfx(R.raw.piece_dragon_attack);
                                            break;
                                        }
                                        case 2: {
                                            playSfx(R.raw.piece_mage_attack);
                                            break;
                                        }
                                        case 3: {
                                            playSfx(R.raw.piece_archer_attack);
                                            break;
                                        }
                                        case 4: {
                                            playSfx(R.raw.piece_knight_attack);
                                            break;
                                        }
                                        case 5: {
                                            playSfx(R.raw.piece_squire_attack);
                                            break;
                                        }
                                        case 6: {
                                            playSfx(R.raw.piece_knight_attack);
                                            break;
                                        }
                                        case 7: {
                                            playSfx(R.raw.piece_squire_attack);
                                            break;
                                        }
                                    }
                                }
                                attackIndex = false;
                            }
                            break;
                        }
                        case "F": {
                            if (chessboard.isAction(action + (cellX + 1) + (cellY + 1) + 0 + 0)) {
                                actionIndex = true;
                                playSfx(R.raw.spell_freeze);
                            } else {
                                if (chessboard.boardPiece[cellX][cellY].getTypeInt() == chessboard.movePlayerInt) {
                                    gl_pieceBoard.getChildAt(6 * cellX + cellY).clearAnimation();
                                }
                                if (chessboard.movePlayerInt == 1) {
                                    gl_action_white.getChildAt(0).clearAnimation();
                                } else {
                                    gl_action_black.getChildAt(0).clearAnimation();
                                }
                            }
                            break;
                        }
                        case "H": {
                            String vitalityHeal = "+" + (chessboard.boardPiece[cellX][cellY].getInitVitality() - chessboard.boardPiece[cellX][cellY].vitality);
                            if (chessboard.isAction(action + (cellX + 1) + (cellY + 1) + 0 + 0)) {
                                vitalityChange(vitalityHeal, cellX, cellY);
                                actionIndex = true;
                                gl_pieceBoard.getChildAt(6 * cellX + cellY).clearAnimation();
                                playSfx(R.raw.spell_heal);
                            } else {
                                if (chessboard.movePlayerInt == 1) {
                                    gl_action_white.getChildAt(1).clearAnimation();
                                } else {
                                    gl_action_black.getChildAt(1).clearAnimation();
                                }
                            }
                            break;
                        }
                        case "R": {
//                              before the action is executed, set combat variables
                            int combatMovePlayerInt = 0;
                            int combatAtkStrL = 0;
                            int combatVitalityL = 0;
                            int combatTypeIntL = 0;
                            int combatAtkStrR = 0;
                            int combatVitalityR = 0;
                            int combatTypeIntR = 0;
                            if (chessboard.boardPiece[cellX][cellY].getTypeInt() * chessboard.movePlayerInt == -1) {
                                Piece revivedPiece = new Piece();
                                for (int i = 0; i < 2; i++) {
                                    for (int j = 0; j < 8; j++) {
                                        if (chessboard.playerPiece[i][j].getInitPositionX() == cellX && chessboard.playerPiece[i][j].getInitPositionY() == cellY)
                                            revivedPiece = chessboard.playerPiece[i][j];
                                    }
                                }
//                                  if the action is executed successfully, the action can trigger combat, then set combatIndex =true;
                                combatIndex = true;
                                combatMovePlayerInt = chessboard.movePlayerInt;
                                combatAtkStrL = revivedPiece.getAttackStrength();
                                combatVitalityL = revivedPiece.getInitVitality();
                                combatTypeIntL = revivedPiece.getPlayerY();

                                if (!chessboard.boardPiece[cellX][cellY].state.equals("f")) {
                                    combatAtkStrR = chessboard.boardPiece[cellX][cellY].getAttackStrength();
                                }
                                combatVitalityR = chessboard.boardPiece[cellX][cellY].vitality;
                                combatTypeIntR = chessboard.boardPiece[cellX][cellY].getPlayerY();
                            }

                            if (chessboard.isAction(action + (cellX + 1) + (cellY + 1) + 0 + 0)) {
                                if (combatIndex) {
                                    combatAnimation(combatMovePlayerInt, combatAtkStrL, combatVitalityL, combatTypeIntL, combatAtkStrR, combatVitalityR, combatTypeIntR);
                                }
                                actionIndex = true;
                                playSfx(R.raw.spell_revive);
                            } else {
                                if (chessboard.movePlayerInt == 1) {
                                    gl_action_white.getChildAt(2).clearAnimation();
                                } else {
                                    gl_action_black.getChildAt(2).clearAnimation();
                                }
                            }
                            break;
                        }
                        case "T": {
//                              before the action is executed, set combat variables
                            int combatMovePlayerInt = 0;
                            int combatAtkStrL = 0;
                            int combatVitalityL = 0;
                            int combatTypeIntL = 0;
                            int combatAtkStrR = 0;
                            int combatVitalityR = 0;
                            int combatTypeIntR = 0;
                            if (teleportIndex) {
                                if (chessboard.boardPiece[fromX][fromY].getTypeInt() * chessboard.boardPiece[cellX][cellY].getTypeInt() == -1) {
//                                  if the action is executed successfully, the action can trigger combat, then set combatIndex =true;
                                    combatIndex = true;
                                    combatMovePlayerInt = chessboard.movePlayerInt;
                                    if (!chessboard.boardPiece[fromX][fromY].state.equals("f")) {
                                        combatAtkStrL = chessboard.boardPiece[fromX][fromY].getAttackStrength();
                                    }
                                    combatVitalityL = chessboard.boardPiece[fromX][fromY].vitality;
                                    combatTypeIntL = chessboard.boardPiece[fromX][fromY].getPlayerY();


                                    if (!chessboard.boardPiece[cellX][cellY].state.equals("f")) {
                                        combatAtkStrR = chessboard.boardPiece[cellX][cellY].getAttackStrength();
                                    }
                                    combatVitalityR = chessboard.boardPiece[cellX][cellY].vitality;
                                    combatTypeIntR = chessboard.boardPiece[cellX][cellY].getPlayerY();
                                    if (chessboard.boardPiece[fromX][fromY].state.equals("f") && chessboard.boardPiece[cellX][cellY].state.equals("f")) {
                                        combatAtkStrL = combatVitalityR;
                                        combatAtkStrR = combatVitalityL;
                                    }
                                }
                                if (chessboard.isAction(action + (fromX + 1) + (fromY + 1) + (cellX + 1) + (cellY + 1))) {
                                    if (combatIndex) {
                                        combatAnimation(combatMovePlayerInt, combatAtkStrL, combatVitalityL, combatTypeIntL, combatAtkStrR, combatVitalityR, combatTypeIntR);
                                    }
                                    actionIndex = true;
                                    playSfx(R.raw.spell_teleport);
                                } else {
                                    if (chessboard.movePlayerInt == 1) {
                                        gl_action_white.getChildAt(3).clearAnimation();
                                    } else {
                                        gl_action_black.getChildAt(3).clearAnimation();
                                    }
                                }
                                teleportIndex = false;
                            } else {
                                fromX = cellX;
                                fromY = cellY;
                                teleportIndex = true;
                            }
                            break;
                        }


                    }
                    if (actionIndex) {
                        if (!combatIndex) {
                            action = "";
                            moveIndex = false;
                            attackIndex = false;
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
                        }
                    } else {
                        if (!(action.equals("T") && teleportIndex)) {
                            playSfx(R.raw.action_invalid);
                            action = "";
                            moveIndex = false;
                            attackIndex = false;
                            refreshPieceBoard();
                            refreshSpells();
                            refreshMoveAttack();
                            refreshPieceInfo(true, cellX, cellY);
                        }
                    }
                }
                break;
            }
        }

        return true;
    }

    private boolean spellOnTouch(View v, MotionEvent event) {
//      if the spell is actived and pressed the action will be equal to the spell's  corresponding  action
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                moveIndex = false;
                attackIndex = false;
                refreshMoveAttack();
                refreshSpells();
                playSfx(R.raw.button_click);
                if (gl_pieceBoard.getChildCount() > 36) {
                    gl_pieceBoard.removeViews(36, gl_pieceBoard.getChildCount() - 36);
                }
                for (int i = 0; i < gl_pieceBoard.getChildCount(); i++) {
                    gl_pieceBoard.getChildAt(i).clearAnimation();
                }
                for (int i = 0; i < 4; i++) {
                    gl_action_white.getChildAt(i).clearAnimation();
                    gl_action_black.getChildAt(i).clearAnimation();

                }
                v.setOnTouchListener(null);
                switch (v.getTag().toString()) {
                    case "Heal": {
                        action = "H";
                        break;
                    }
                    case "Teleport": {
                        action = "T";
                        teleportIndex = false;
                        break;
                    }
                    case "Revive": {
//                        if the spell revive is pressed, all the dead pieces will be show in the pieceboard
                        action = "R";
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
                        break;
                    }
                }
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.touch_down);
                v.startAnimation(animation);
                break;
            }
            case MotionEvent.ACTION_UP: {
                break;
            }
        }

        return true;
    }

    private boolean moveAttackOnTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                playSfx(R.raw.button_click);
//                clear all the redundant view in gl_pieceboard
                if (gl_pieceBoard.getChildCount() > 36) {
                    gl_pieceBoard.removeViews(36, gl_pieceBoard.getChildCount() - 36);
                }

                if (chessboard.movePlayerInt == 1) {
                    findViewById(R.id.bn_attack_white).clearAnimation();
                    findViewById(R.id.bn_move_white).clearAnimation();
                } else {
                    findViewById(R.id.bn_attack_black).clearAnimation();
                    findViewById(R.id.bn_move_black).clearAnimation();
                }

                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.touch_down);
                v.startAnimation(animation);
                v.setOnTouchListener(null);
                Set cellSet = new TreeSet();
//                set action
                if (v.getTag().equals("Move")) {
                    cellSet = chessboard.getMoveCells(fromX, fromY);
                    action = "M";
                }
                if (v.getTag().equals("Attack")) {
                    cellSet = chessboard.getAttackCells(fromX, fromY);
                    action = "A";
                }
//                the following code is used to show the available attack cell and move cell
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

    private void gameStart() {
//        this method is used to show the beginning animation, the count down animation and start music
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
        playSfx(R.raw.game_start_countdown_3);
        iv_count_down3.getAnimation().setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv_count_down3.setImageResource(R.drawable.count_down_2);
                iv_count_down3.startAnimation(an_count_down);
                playSfx(R.raw.game_start_countdown_2);
                iv_count_down3.getAnimation().setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        iv_count_down3.setImageResource(R.drawable.count_down_1);
                        iv_count_down3.startAnimation(an_count_down);
                        playSfx(R.raw.game_start_countdown_1);
                        iv_count_down3.getAnimation().setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                playSfx(R.raw.game_start);
//                                after aniumation, remove the view
                                rl_activity_game.removeView(rl_game_start);
//                                initialize the board
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

    private boolean gameIsEnd() {
//        if game is end a new layout and end animation will be show, the exctra points will be added to the winner
        boolean index = false;
        if (!chessboard.getResultStr().equals("")) {
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
            switch (chessboard.getResultStr()) {
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
            tv_end_black.setTextColor(Color.WHITE);
            tv_end_black.setTypeface(Typeface.MONOSPACE);
            tv_end_black.setTextSize(22);
            tv_end_black.setLayoutParams(rl_game_end_black_Params);

            rl_activity_game.addView(tv_end_white);
            rl_activity_game.addView(tv_end_black);
            rl_game_end.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.game_end));
            tv_end_white.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.game_end));
            tv_end_black.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.game_end));
            playSfx(R.raw.game_end);
            storeGameData();
        }
        return index;
    }

    private void storeGameData() {
//        after the gameIsEnd(), this method will store game data to database
        int highScore = Math.max(chessboard.playerScore[0], chessboard.playerScore[1]);
        String playerName = "";
//        open database
        SQLiteDatabase db = openOrCreateDatabase("group08", Context.MODE_PRIVATE, null);
//        open table
        String sql1 = "CREATE TABLE IF NOT EXISTS game_history (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,player_name String, player_score int,board_str String,win_player String,timestamp DATETIME DEFAULT CURRENT_TIMESTAMP )";
        db.execSQL(sql1);
//        store data
        String sql2 = "INSERT INTO game_history (player_name,player_score,board_str,win_player) Values ('" + playerName + "'," + highScore + " ,'" + chessboard.getBoardStr() + "','" + chessboard.getResultStr() + "')";
        db.execSQL(sql2);
        db.close();
    }

    private void playSfx(int resId) {
        if (app.sfxIndex) {
            mp = MediaPlayer.create(getBaseContext(), resId);
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.stop();
                    mediaPlayer.release();

                }
            });
        }
    }

    private void musicStart(boolean index) {
//        play background music, index = false background will be stop no matther what the value of app.musicIndex
        if (index) {
            if (app.musicIndex) {
                backgroundMusic.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mediaPlayer.setLooping(true);
                        mediaPlayer.start();
                    }
                });
            }
        } else {
            backgroundMusic.stop();
        }
    }

    private void vitalityChange(String vitalityChange, int r, int c) {
//        when a piece was attacked, the number how much vitality will be diminished show be showed in it's cell
//        when a piece was healed, the number how much vitality will be added show be showed in it's cell
        final TextView tv_vitalityChange = new TextView(getBaseContext());

        tv_vitalityChange.setText(vitalityChange);
        tv_vitalityChange.setTextColor(Color.WHITE);
        tv_vitalityChange.setTextSize(16);
        tv_vitalityChange.setTypeface(Typeface.MONOSPACE);
        tv_vitalityChange.setGravity(Gravity.CENTER);

        final GridLayout.LayoutParams tv_vitalityChange_Params = new GridLayout.LayoutParams();
        tv_vitalityChange_Params.width = GridLayout.LayoutParams.WRAP_CONTENT;
        tv_vitalityChange_Params.height = GridLayout.LayoutParams.WRAP_CONTENT;
        tv_vitalityChange_Params.columnSpec = GridLayout.spec(c, 1, 1f);
        tv_vitalityChange_Params.rowSpec = GridLayout.spec(5 - r, 1, 1f);
        tv_vitalityChange_Params.setGravity(Gravity.CENTER);
        tv_vitalityChange.setLayoutParams(tv_vitalityChange_Params);

        Animation an_vitalityChange;

        if (chessboard.movePlayerInt == -1) {
            an_vitalityChange = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.attack_minusvitality_white);
        } else {
            tv_vitalityChange.setRotation(180);
            an_vitalityChange = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.attack_minusvitality_black);
        }

        gl_boarderBoard.addView(tv_vitalityChange);

        an_vitalityChange.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                gl_boarderBoard.post(new Runnable() {
                    public void run() {
                        // it works without the runOnUiThread, but all UI updates must
                        // be done on the UI thread
                        runOnUiThread(new Runnable() {
                            public void run() {
                                gl_boarderBoard.removeView(tv_vitalityChange);
                            }
                        });
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        tv_vitalityChange.startAnimation(an_vitalityChange);
    }

    private void combatAnimation(int movePlayerInt, final int atkStrengthL, int vitalityL, int typeL, final int atkStrengthR, int vitalityR, int typeR) {
//       For the action teleport, move and revive, the combat may be happen
        vitalityLeft = vitalityL;
        vitalityRight = vitalityR;
        final int musicAttackLeft;
        final int musicAttackRight;
        final int musicWinLeft;
        final int musicWinRight;

//        bing the rl_combat to the front
        rl_combat.setClickable(true);
        rl_combat.setBackgroundColor(Color.BLACK);
        rl_combat.getBackground().setAlpha(200);
        if (movePlayerInt == -1) rl_combat.setRotation(180);
        else rl_combat.setRotation(0);
//        initialize combat animation
        final Animation an_combatAppear = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.combat_appear);
        final Animation an_combatDisappear = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.combat_disppear);
        final Animation an_combatWinnerLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.combat_winner_left);
        final Animation an_combatWinnerRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.combat_winner_right);
        final Animation an_combatLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.combat_translate_left);
        final Animation an_combatRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.combat_translate_right);
        final Animation an_combatVitalityChangeWhite = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.attack_minusvitality_white);


        final Animation an_combatDead = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.combat_dead);
        final Animation an_combatDeadDisppear = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.combat_dead_disappear);
        final Animation an_combatShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.combat_shake);

//        add combat layout and set blackground color white
        final FrameLayout fl_combat = new FrameLayout(getBaseContext());
        RelativeLayout.LayoutParams fl_combat_Paramas = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 360);
        fl_combat_Paramas.addRule(RelativeLayout.CENTER_IN_PARENT);
        fl_combat.setLayoutParams(fl_combat_Paramas);
        fl_combat.setBackgroundColor(Color.WHITE);

//        initialize left piece image
        final AppCompatImageView iv_combat_piece_left = new AppCompatImageView(getBaseContext());
        FrameLayout.LayoutParams iv_combat_piece_left_Params = new FrameLayout.LayoutParams(360, FrameLayout.LayoutParams.MATCH_PARENT);
        iv_combat_piece_left.setPadding(6, 6, 6, 6);
        iv_combat_piece_left_Params.gravity = Gravity.CENTER_VERTICAL | Gravity.START;
        iv_combat_piece_left.setLayoutParams(iv_combat_piece_left_Params);
//        set imageResource and sfx music according to the piece's type
        switch (typeL) {
            case 0: {
                iv_combat_piece_left.setImageResource(R.drawable.piece_giant_combat_left);
                musicAttackLeft = R.raw.piece_giant_attack;
                musicWinLeft = R.raw.piece_giant_summon;
                break;
            }
            case 1: {
                iv_combat_piece_left.setImageResource(R.drawable.piece_dragon_combat_left);
                musicAttackLeft = R.raw.piece_dragon_attack;
                musicWinLeft = R.raw.piece_dragon_summon;
                break;
            }
            case 2: {
                iv_combat_piece_left.setImageResource(R.drawable.piece_mage_combat_left);
                musicAttackLeft = R.raw.piece_mage_attack;
                musicWinLeft = R.raw.piece_mage_summon;
                break;
            }
            case 3: {
                iv_combat_piece_left.setImageResource(R.drawable.piece_archer_combat_left);
                musicAttackLeft = R.raw.piece_archer_attack;
                musicWinLeft = R.raw.piece_archer_summon;
                break;
            }
            case 4: {
                iv_combat_piece_left.setImageResource(R.drawable.piece_knight_combat_left);
                musicAttackLeft = R.raw.piece_knight_attack;
                musicWinLeft = R.raw.piece_knight_summon;
                break;
            }
            case 5: {
                iv_combat_piece_left.setImageResource(R.drawable.piece_squire_combat_left);
                musicAttackLeft = R.raw.piece_squire_attack;
                musicWinLeft = R.raw.piece_squire_summon;
                break;
            }
            case 6: {
                iv_combat_piece_left.setImageResource(R.drawable.piece_knight_combat_left);
                musicAttackLeft = R.raw.piece_knight_attack;
                musicWinLeft = R.raw.piece_knight_summon;
                break;
            }
            case 7: {
                iv_combat_piece_left.setImageResource(R.drawable.piece_squire_combat_left);
                musicAttackLeft = R.raw.piece_squire_attack;
                musicWinLeft = R.raw.piece_squire_summon;
                break;
            }
            default:
                musicAttackLeft = 0;
                musicWinLeft = 0;

        }

//        initialize right piece image
        final AppCompatImageView iv_combat_piece_right = new AppCompatImageView(getBaseContext());
        FrameLayout.LayoutParams iv_combat_piece_right_Params = new FrameLayout.LayoutParams(360, FrameLayout.LayoutParams.MATCH_PARENT);
        iv_combat_piece_right.setPadding(6, 6, 6, 6);
        iv_combat_piece_right_Params.gravity = Gravity.CENTER_VERTICAL | Gravity.END;
        iv_combat_piece_right.setLayoutParams(iv_combat_piece_right_Params);
//        set imageResource and sfx music according to the piece's type
        switch (typeR) {
            case 0: {
                iv_combat_piece_right.setImageResource(R.drawable.piece_giant_combat_right);
                musicAttackRight = R.raw.piece_giant_attack;
                musicWinRight = R.raw.piece_giant_summon;
                break;
            }
            case 1: {
                iv_combat_piece_right.setImageResource(R.drawable.piece_dragon_combat_right);
                musicAttackRight = R.raw.piece_dragon_attack;
                musicWinRight = R.raw.piece_dragon_summon;
                break;
            }
            case 2: {
                iv_combat_piece_right.setImageResource(R.drawable.piece_mage_combat_right);
                musicAttackRight = R.raw.piece_mage_attack;
                musicWinRight = R.raw.piece_mage_summon;
                break;
            }
            case 3: {
                iv_combat_piece_right.setImageResource(R.drawable.piece_archer_combat_right);
                musicAttackRight = R.raw.piece_archer_attack;
                musicWinRight = R.raw.piece_archer_summon;
                break;
            }
            case 4: {
                iv_combat_piece_right.setImageResource(R.drawable.piece_knight_combat_right);
                musicAttackRight = R.raw.piece_knight_attack;
                musicWinRight = R.raw.piece_knight_summon;
                break;
            }
            case 5: {
                iv_combat_piece_right.setImageResource(R.drawable.piece_squire_combat_right);
                musicAttackRight = R.raw.piece_squire_attack;
                musicWinRight = R.raw.piece_squire_summon;
                break;
            }
            case 6: {
                iv_combat_piece_right.setImageResource(R.drawable.piece_knight_combat_right);
                musicAttackRight = R.raw.piece_knight_attack;
                musicWinRight = R.raw.piece_knight_summon;
                break;
            }
            case 7: {
                iv_combat_piece_right.setImageResource(R.drawable.piece_squire_combat_right);
                musicAttackRight = R.raw.piece_squire_attack;
                musicWinRight = R.raw.piece_squire_summon;
                break;
            }
            default:
                musicAttackRight = 0;
                musicWinRight = 0;

        }
//        after the right piece's attack, a TextView will be showed on the left piece
        final TextView tv_vitality_left = new TextView(getBaseContext());
        FrameLayout.LayoutParams tv_vitality_left_Params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        tv_vitality_left_Params.setMargins(360, 0, 0, 0);
        tv_vitality_left_Params.gravity = Gravity.START | Gravity.CENTER_VERTICAL;
        tv_vitality_left.setLayoutParams(tv_vitality_left_Params);
        tv_vitality_left.setTextColor(Color.parseColor("#ffcc0000"));
        tv_vitality_left.setTypeface(Typeface.MONOSPACE);
//        after the left piece's attack, a TextView will be showed on the right piece
        final TextView tv_vitality_right = new TextView(getBaseContext());
        FrameLayout.LayoutParams tv_vitality_right_Params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        tv_vitality_right_Params.setMargins(0, 0, 360, 0);
        tv_vitality_right_Params.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
        tv_vitality_right.setLayoutParams(tv_vitality_right_Params);
        tv_vitality_right.setTextColor(Color.parseColor("#ffcc0000"));
        tv_vitality_right.setTypeface(Typeface.MONOSPACE);

        rl_combat.addView(fl_combat);
        fl_combat.addView(iv_combat_piece_left);
        fl_combat.addView(iv_combat_piece_right);

        an_combatAppear.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv_combat_piece_left.startAnimation(an_combatLeft);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
//        after the left piece's attack, the right piece will attack back
        an_combatLeft.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                vitalityRight -= atkStrengthL;
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                iv_combat_piece_right.startAnimation(an_combatRight);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                playSfx(musicAttackLeft);
                tv_vitality_right.setText("" + (vitalityRight + atkStrengthL) + "-" + atkStrengthL);
                fl_combat.addView(tv_vitality_right);
                tv_vitality_right.startAnimation(an_combatVitalityChangeWhite);
                iv_combat_piece_right.startAnimation(an_combatShake);
            }
        });
//        according th vitality of two pieces, the left piece need to decide whether it should attack ledt piece
        an_combatRight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                vitalityLeft -= atkStrengthR;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                if both pieces are live the combat animation wil go on
                if (vitalityLeft > 0 && vitalityRight > 0) {
                    iv_combat_piece_left.startAnimation(an_combatLeft);
//                    the following is the different situation that the combat is over
//                    both pieces are dead
                } else if (vitalityLeft <= 0 && vitalityRight <= 0) {
                    iv_combat_piece_left.startAnimation(an_combatDead);
                    iv_combat_piece_right.startAnimation(an_combatDeadDisppear);
//                    left peice is dead
                } else if (vitalityLeft <= 0 && vitalityRight > 0) {
                    iv_combat_piece_left.startAnimation(an_combatDeadDisppear);
                    iv_combat_piece_right.startAnimation(an_combatWinnerRight);
//                    right piece is dead
                } else if (vitalityLeft > 0 && vitalityRight <= 0) {
                    iv_combat_piece_left.startAnimation(an_combatWinnerLeft);
                    iv_combat_piece_right.startAnimation(an_combatDeadDisppear);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                playSfx(musicAttackRight);
                tv_vitality_left.setText("" + (vitalityLeft + atkStrengthR) + "-" + atkStrengthR);
                fl_combat.addView(tv_vitality_left);
                tv_vitality_left.startAnimation(an_combatVitalityChangeWhite);
                iv_combat_piece_left.startAnimation(an_combatShake);
            }
        });
//        after the animation vitality change, the textview will be removed
        an_combatVitalityChangeWhite.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rl_combat.post(new Runnable() {
                    public void run() {
                        // it works without the runOnUiThread, but all UI updates must
                        // be done on the UI thread
                        runOnUiThread(new Runnable() {
                            public void run() {
                                fl_combat.removeViewAt(2);

                            }
                        });
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
//        if the winner is left, the left piece will be translated to center
        an_combatWinnerLeft.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                playSfx(musicWinLeft);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rl_combat.startAnimation(an_combatDisappear);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
//        if the winner is right, the right piece will be translated to center
        an_combatWinnerRight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                playSfx(musicWinRight);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rl_combat.startAnimation(an_combatDisappear);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
//        if the piece is dead, the piece will be scaled to 0
        an_combatDead.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rl_combat.startAnimation(an_combatDisappear);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
//        if the combat is over, the combat layoput will be scaled to 0
        an_combatDisappear.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rl_combat.post(new Runnable() {
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                rl_combat.removeView(fl_combat);
                                rl_combat.setClickable(false);
                                rl_combat.setBackgroundColor(Color.TRANSPARENT);
                                rl_combat.getBackground().setAlpha(255);
                            }
                        });
                    }
                });

                action = "";
                moveIndex = false;
                attackIndex = false;
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
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rl_combat.startAnimation(an_combatAppear);

    }

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









