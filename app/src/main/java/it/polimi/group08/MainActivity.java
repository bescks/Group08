package it.polimi.group08;


import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    MyApplication app;
    MediaPlayer mp;
    MediaPlayer backgroundMusic;
    Animation animation;

    @Override
    protected void onStop() {
        super.onPause();
        backgroundMusic.pause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        musicStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        backgroundMusic.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = ((MyApplication) getApplicationContext());
        backgroundMusic = MediaPlayer.create(this, R.raw.main_background);
        backgroundMusic.setAudioStreamType(AudioManager.STREAM_MUSIC);
        musicStart();
        findViewById(R.id.activity_main).getBackground().setAlpha(230);
        findViewById(R.id.bn_new_game).getBackground().setAlpha(150);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_from_left);
        findViewById(R.id.bn_new_game).startAnimation(animation);
        findViewById(R.id.tv_music).startAnimation(animation);
        findViewById(R.id.tv_sfx).startAnimation(animation);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_from_right);
        findViewById(R.id.bn_high_scores).getBackground().setAlpha(150);
        findViewById(R.id.bn_high_scores).startAnimation(animation);
        findViewById(R.id.tb_sfx).startAnimation(animation);
        findViewById(R.id.tb_music).startAnimation(animation);


        findViewById(R.id.tb_sfx).setOnClickListener(onClickListener);
        findViewById(R.id.tb_music).setOnClickListener(onClickListener);


        ((ToggleButton) findViewById(R.id.tb_music)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    app.musicIndex = true;
                    backgroundMusic.start();
                } else {
                    app.musicIndex = false;
                    backgroundMusic.pause();
                }
            }

        });
        ((ToggleButton) findViewById(R.id.tb_sfx)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                app.sfxIndex = isChecked;
            }

        });

    }


    private void musicStart() {
        if (app.musicIndex) {
            backgroundMusic.start();
        } else {
            backgroundMusic.pause();
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (app.sfxIndex) {
                mp = MediaPlayer.create(getBaseContext(), R.raw.main_menu_click);
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
    };

    public void GameActivity(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        if (app.sfxIndex) {
            mp = MediaPlayer.create(getBaseContext(), R.raw.main_menu_click);
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

    public void HighScoreActivity(View view) {
        Intent intent = new Intent(this, HighScoreActivity.class);
        startActivity(intent);
        if (app.sfxIndex) {
            mp = MediaPlayer.create(getBaseContext(), R.raw.main_menu_click);
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

}
