package it.polimi.group08;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        MyApplication app = ((MyApplication) getApplicationContext());
//      set screen property in MyApplication
//        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        wm.getDefaultDisplay().getMetrics(displayMetrics);
//        app.width = displayMetrics.widthPixels;
//        app.height = displayMetrics.heightPixels;
//        app.density = displayMetrics.density;
//        app.dp = (int) app.density;
//        app.widthDp = ((int) (app.width / app.density));
//        app.heightDp = ((int) (app.height / app.density));

//        TextView tv = (TextView) findViewById(R.id.tv1);
//        tv.append("width =" + app.widthDp + "/" + "height=" + app.heightDp+"dp="+app.dp);
//        Toast.makeText(this, tv.getText(), Toast.LENGTH_SHORT).show();
    }

    public void GameActivity(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        Toast.makeText(this, "GameActivity", Toast.LENGTH_SHORT).show();
    }


}
