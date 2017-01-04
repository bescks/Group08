package it.polimi.group08;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//        GridLayout grid = (GridLayout) findViewById(R.id.Cells);
//        for(int i = 0 ; i <6 ; i++){
//            View child = grid.getChildAt(i);
//            child.getLayoutParams().width = 100;
//            child.getLayoutParams().height = 100;
//        }
    }
}
