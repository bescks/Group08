package it.polimi.group08;


import android.app.Application;

import it.polimi.group08.functions.FontsOverride;

/**
 * Created by gengdongjie on 24/12/2016.
 */

public class MyApplication extends Application {

//    private WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
//    private DisplayMetrics dm = new DisplayMetrics();

    boolean musicIndex;
    boolean sfxIndex;

    @Override
    public void onCreate() {
        super.onCreate();
        //  Set font for whole app
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/Supercell-Magic_5.ttf");

    }

    public MyApplication() {

    }

}



