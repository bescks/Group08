package it.polimi.group08;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.TextView;

public class HighScoreActivity extends AppCompatActivity {
    MyApplication app;
    MediaPlayer backgroundMusic;
    Animation animationLeft;
    Animation animationRight;


    @Override
    protected void onStop() {
        super.onPause();
        musicStart(false);

    }

    @Override
    protected void onResume() {
        super.onResume();
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
        setContentView(R.layout.activity_high_score);

        app = ((MyApplication) getApplicationContext());
        backgroundMusic = MediaPlayer.create(this, R.raw.high_score_background);
        backgroundMusic.setAudioStreamType(AudioManager.STREAM_MUSIC);
        musicStart(true);
        GridLayout gl_high_score = (GridLayout) findViewById(R.id.gl_high_score);

        for (int i = 0; i < 12; i++) {
            Resources res = this.getResources();
            GridLayout.LayoutParams iv_ranking_Params = new GridLayout.LayoutParams();
            AppCompatImageView iv_ranking = new AppCompatImageView(getBaseContext());
            iv_ranking_Params.width = 240;
            iv_ranking_Params.height = 120;
            iv_ranking_Params.columnSpec = GridLayout.spec(0, 1);
            iv_ranking_Params.setGravity(Gravity.CENTER);
            iv_ranking_Params.rowSpec = GridLayout.spec(i, 1);
            iv_ranking.setImageResource(R.drawable.high_score_rank1);
            iv_ranking.setImageResource(res.getIdentifier("high_score_rank" + (i + 1), "drawable", this.getPackageName()));
            iv_ranking.setPadding(10, 10, 10, 10);
            iv_ranking.setLayoutParams(iv_ranking_Params);
            gl_high_score.addView(iv_ranking);
            animationLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_from_left);
            animationLeft.setDuration(500 + i * 200);
            iv_ranking.startAnimation(animationLeft);

            GridLayout.LayoutParams tv_ranking_Params = new GridLayout.LayoutParams();
            TextView tv_ranking = new TextView(getBaseContext());
            tv_ranking_Params.width = 240;
            tv_ranking_Params.height = 120;
            tv_ranking.setGravity(Gravity.CENTER);
            tv_ranking_Params.columnSpec = GridLayout.spec(0, 1);
            tv_ranking_Params.setGravity(Gravity.CENTER);
            tv_ranking_Params.rowSpec = GridLayout.spec(i, 1);
            tv_ranking.setLayoutParams(tv_ranking_Params);
            tv_ranking.setTypeface(Typeface.MONOSPACE);
            tv_ranking.setTextSize(10);
            String ranking = "" + (i + 1);
            tv_ranking.setText(ranking);
            tv_ranking.setTextColor(Color.WHITE);
            gl_high_score.addView(tv_ranking);
            tv_ranking.startAnimation(animationLeft);

            GridLayout.LayoutParams tv_score_Params = new GridLayout.LayoutParams();
            TextView tv_score = new TextView(getBaseContext());
            tv_score_Params.width = 270;
            tv_score_Params.height = 120;
            tv_score.setGravity(Gravity.CENTER);
            tv_score_Params.columnSpec = GridLayout.spec(1, 1);
            tv_score_Params.setGravity(Gravity.CENTER);
            tv_score_Params.rowSpec = GridLayout.spec(i, 1);
            tv_score.setLayoutParams(tv_score_Params);
            tv_score.setTypeface(Typeface.MONOSPACE);
            tv_score.setText("0");
            tv_score.setTextColor(Color.BLACK);
            gl_high_score.addView(tv_score);
            animationRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_from_right);
            animationRight.setDuration(500 + i * 200);
            tv_score.startAnimation(animationRight);

            GridLayout.LayoutParams tv_winner_Params = new GridLayout.LayoutParams();
            TextView tv_winner = new TextView(getBaseContext());
            tv_winner_Params.width = 270;
            tv_winner_Params.height = 120;
            tv_winner.setGravity(Gravity.CENTER);
            tv_winner_Params.columnSpec = GridLayout.spec(2, 1);
            tv_winner_Params.setGravity(Gravity.CENTER);
            tv_winner_Params.rowSpec = GridLayout.spec(i, 1);
            tv_winner.setLayoutParams(tv_winner_Params);
            tv_winner.setTypeface(Typeface.MONOSPACE);
            tv_winner.setText("/");
            tv_winner.setTextColor(Color.BLACK);
            gl_high_score.addView(tv_winner);
            tv_winner.startAnimation(animationRight);

            GridLayout.LayoutParams tv_time_Params = new GridLayout.LayoutParams();
            TextView tv_time = new TextView(getBaseContext());
            tv_time_Params.width = 300;
            tv_time_Params.height = 120;
            tv_time.setGravity(Gravity.CENTER);
            tv_time_Params.columnSpec = GridLayout.spec(3, 1);
            tv_time_Params.setGravity(Gravity.CENTER);
            tv_time_Params.rowSpec = GridLayout.spec(i, 1);
            tv_time.setLayoutParams(tv_time_Params);
            tv_time.setTypeface(Typeface.MONOSPACE);
            tv_time.setTextSize(7);
            tv_time.setText(R.string.iniTimeStamp);
            tv_time.setTextColor(Color.BLACK);
            gl_high_score.addView(tv_time);
            tv_time.startAnimation(animationRight);

        }

        SQLiteDatabase db = openOrCreateDatabase("group08", Context.MODE_PRIVATE, null);
        String sql1 = "CREATE TABLE IF NOT EXISTS game_history (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,player_name String, player_score int,board_str String,win_player String,timestamp DATETIME DEFAULT CURRENT_TIMESTAMP )";
        db.execSQL(sql1);
        Cursor cur = db.rawQuery("SELECT * FROM game_history ORDER BY player_score DESC", null);
        int i = 0;
        if (cur.moveToFirst()) {
            do {
                ((TextView) gl_high_score.getChildAt(5 * i + 2)).setText(cur.getString(cur.getColumnIndex("player_score")));
                ((TextView) gl_high_score.getChildAt(5 * i + 3)).setText(cur.getString(cur.getColumnIndex("win_player")));
                ((TextView) gl_high_score.getChildAt(5 * i + 4)).setText(cur.getString(cur.getColumnIndex("timestamp")));
                i++;
            }
            while (cur.moveToNext() && i < 12);
        }
        cur.close();
        db.close();


    }

    private void musicStart(boolean index) {
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


}
