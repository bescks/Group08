package it.polimi.group08;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.RelativeLayout;

public class GameActivity extends AppCompatActivity {

    GridLayout gl_cellBackground;
    GridLayout gl_pieceBoard;
    GridLayout gl_cellBoard;
    RelativeLayout secondLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        MyApplication app = ((MyApplication) getApplicationContext());
//      firstLayer : initialize cellBackground
        gl_cellBackground = (GridLayout) findViewById(R.id.gl_cellBackground);
        gl_cellBackground.getLayoutParams().height = app.width;
        for (int i = 0; i < 36; i++) {
            View child = gl_cellBackground.getChildAt(i);
            child.getLayoutParams().width = app.width / 6;
            child.getLayoutParams().height = app.width / 6;
        }
//      secondlayer :initialization
        secondLayer = (RelativeLayout) findViewById(R.id.rl_secondLayer);
        gl_pieceBoard = (GridLayout) findViewById(R.id.gl_pieceBoard);
        gl_pieceBoard.getLayoutParams().height = app.width;
        for (int i = 0; i < gl_pieceBoard.getChildCount(); i++) {
            View child = gl_pieceBoard.getChildAt(i);
            child.getLayoutParams().width = app.width / 6;
            child.getLayoutParams().height = app.width / 6;
        }
//        initializing 2nd GridLayout

//      thirdlayer layer: initialize cellBoard
        gl_cellBoard = (GridLayout) findViewById(R.id.gl_cellBoard);
        gl_cellBoard.getLayoutParams().height = app.width;
        for (int i = 0; i < gl_cellBoard.getChildCount(); i++) {
            View child = gl_cellBoard.getChildAt(i);
            child.getLayoutParams().width = app.width / 6;
            child.getLayoutParams().height = app.width / 6;
        }

    }

    public void cellIsSelected(View view) {
        GridLayout.LayoutParams param = new GridLayout.LayoutParams();
        System.out.println(view.getTag());

    }


//        initializing first RelativeLayout
//        RelativeLayout rL2 = (RelativeLayout) findViewById(rl_game_2ndLayer);
//        RelativeLayout.LayoutParams RL2_Params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        RL2_Params.setMargins(0, 200, 0, 500);
//        rL2.setLayoutParams(RL2_Params);
//        LinearLayout RL1LL1 = (LinearLayout) findViewById(R.id.RL1LL1);
//        LinearLayout RL1LL2 = (LinearLayout) findViewById(R.id.RL1LL2);
//        LinearLayout RL1LL3 = (LinearLayout) findViewById(R.id.RL1LL3);
//        LinearLayout.LayoutParams RL1LL1Params = (LinearLayout.LayoutParams) RL1LL1.getLayoutParams();
//        LinearLayout.LayoutParams RL1LL2Params = (LinearLayout.LayoutParams) RL1LL2.getLayoutParams();
//        LinearLayout.LayoutParams RL1LL3Params = (LinearLayout.LayoutParams) RL1LL3.getLayoutParams();
//        float totalWeight=RL1LL1Params.weight+RL1LL2Params.weight+RL1LL3Params.weight;


//        initializing second RelativeLayout
//        LinearLayout RL2LL1 = (LinearLayout) findViewById(R.id.RL2LL1);
//        RelativeLayout.LayoutParams RL2LL1Params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//        RL2LL1Params.setMargins(0,(int) (app.height*RL1LL3Params.weight/totalWeight),0,(int) (app.height*RL1LL1Params.weight/totalWeight));
//        RL2LL1.setLayoutParams(RL2LL1Params);

//        Toast.makeText(this, "layout=" + RL2LL1.getLayoutParams(), Toast.LENGTH_SHORT).show();


}



