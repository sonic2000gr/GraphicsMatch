package com.example.sonic.graphicsmatch;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


class TimerRunnable  implements Runnable {
    Handler timerHandler;
    ImageView[] imageView;

    private int shapes[] = { R.mipmap.cherry,
                             R.mipmap.lemon,
                             R.mipmap.bar,
                             R.mipmap.raspberry,
                             R.mipmap.seven,
                             R.mipmap.bell };

    Game thegame;

    public TimerRunnable(ImageView[] image, Game game) {
        timerHandler = new Handler();
        imageView = image;
        thegame = game;
    }

    public Handler getHandler() {
        return timerHandler;
    }

    @Override
    public void run() {
        int[] s = thegame.spin();
        setShapes(s);
        timerHandler.postDelayed(this, 100);
    }

    public void setShapes(int s[]) {
        int i;
        for (i = 0; i < s.length; i++)
            imageView[i].setImageResource(shapes[s[i]]);
    }
};


public class MainActivity extends AppCompatActivity {

    ImageView[] image = new ImageView[3];
    TimerRunnable timerRunnable;
    Handler timerHandler;
    Boolean spinning;
    int[] s = new int[3];

    private Game thegame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        thegame = new Game();
        image[0] = findViewById(R.id.imageView1);
        image[1] = findViewById(R.id.imageView2);
        image[2] = findViewById(R.id.imageView3);
        timerRunnable = new TimerRunnable(image, thegame);
        timerHandler = timerRunnable.getHandler();
        spinning = false;
        if (savedInstanceState != null) {
            s = savedInstanceState.getIntArray("shapes");
            spinning = savedInstanceState.getBoolean("spinning");
            thegame.setScore(savedInstanceState.getInt("score"));
            thegame.setShapes(s);
            timerRunnable.setShapes(s);
        }
        if (spinning) {
            drawScreen(false);
            spinning = false;
        } else {
            drawScreen(true);
        }
    }

    public void onPlayClick(View v) {
        Button playButton = (Button)findViewById(R.id.button);
        if (playButton.getText().equals("Spin")) {
            timerHandler.postDelayed(timerRunnable, 0);
            playButton.setText("Stop");
            spinning = true;
        } else {
            timerHandler.removeCallbacks(timerRunnable);
            playButton.setText("Spin");
            drawScreen(false);
            spinning = false;
        }
    }

    public void drawScreen(boolean viewOnly) {
        int i;
        TextView score = findViewById(R.id.txtscore);
        score.setText("Score: "+thegame.getScore(viewOnly));
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("score", thegame.getScore(true));
        savedInstanceState.putBoolean("spinning", spinning);
        savedInstanceState.putIntArray("shapes", thegame.getShapes());
    }

    @Override
    public void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
        Button playButton = (Button)findViewById(R.id.button);
        playButton.setText("Spin");
    }
}
