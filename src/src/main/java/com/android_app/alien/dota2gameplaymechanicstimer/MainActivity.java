package com.android_app.alien.dota2gameplaymechanicstimer;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import static com.android_app.alien.dota2gameplaymechanicstimer.R.drawable.roshan;

public class MainActivity extends AppCompatActivity {
    static int ROSHAN_MIN_RESPAWN = (1 * 30 * 1000);  //respawn times in milliseconds. Roshan respawns between 8 and 11 minutes, correct to version 7.01
    static int ROSHAN_MAX_RESPAWN = (2 * 30 * 1000);
    static int AEGIS_RECLAIM_TIME = (2 * 35 * 1000); //Aegis is reclaimed 5 minutes after being picked up
    TextView game_time_text_view;
    long gameStartTime = 0;
    Button roshan_respawn_button;
    Button aegis_reclaim_button;
    long roshanDeadTime;
    long roshanEarliestRespawnTime;
    long roshanLatestRespawnTime;
    long aegisReclaimTime;
    MediaPlayer mediaplayer;
    Boolean played;

    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - gameStartTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            game_time_text_view.setText(String.format("%02d:%02d", minutes, seconds));
            timerHandler.postDelayed(this, 500);
        }
    };

    //runs without a timer by reposting this handler at the end of the runnable
    Handler roshanTimerHandler = new Handler();
    Runnable roshanTimerRunnable = new Runnable() {

        @Override
        public void run() {
            long millisMin = roshanEarliestRespawnTime - System.currentTimeMillis();
            int secondsMin = (int) (millisMin / 1000);
            int minutesMin = secondsMin / 60;
            secondsMin = secondsMin % 60;

            long millisMax = roshanLatestRespawnTime - System.currentTimeMillis();
            int secondsMax = (int) (millisMax / 1000);
            int minutesMax = secondsMax / 60;
            secondsMax = secondsMax % 60;

            long millisAegis = aegisReclaimTime - System.currentTimeMillis();
            int secondsAegis = (int) (millisAegis / 1000);
            int minutesAegis = secondsAegis / 60;
            secondsAegis = secondsAegis % 60;

            aegis_reclaim_button.setText("aegis reclaim\n" + String.format("%d:%02d", minutesAegis, secondsAegis));
            if (System.currentTimeMillis() <= roshanEarliestRespawnTime) {
                roshan_respawn_button.setText("roshan respawn\n" + String.format("%d:%02d", minutesMin, secondsMin) + " and " + String.format("%d:%02d", minutesMax, secondsMax));
                roshanTimerHandler.postDelayed(this, 500);
            } else if (System.currentTimeMillis() > roshanEarliestRespawnTime && System.currentTimeMillis() < roshanLatestRespawnTime) {
                roshan_respawn_button.setText("roshan respawn\nNow and " + String.format("%d:%02d", minutesMax, secondsMax));
                roshanTimerHandler.postDelayed(this, 500);
            } else if (System.currentTimeMillis() >= roshanLatestRespawnTime) {
                roshan_respawn_button.setText("roshan respawn\nNow");
                if (!played) {
                    mediaplayer.start();
                    played = true;
                }
                if (System.currentTimeMillis() < aegisReclaimTime) {
                    roshanTimerHandler.postDelayed(this, 500);
                }
            }
            if (System.currentTimeMillis() >= aegisReclaimTime) {
                aegis_reclaim_button.setText("aegis reclaim\n done");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        roshanDeadTime = 0;
        roshanEarliestRespawnTime = 0;
        roshanLatestRespawnTime = 0;
        aegisReclaimTime = 0;
        played = true;
        
        setContentView(R.layout.activity_main);

        //to stop the screen from turning off, will cause battery drain
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        game_time_text_view = (TextView) findViewById(R.id.game_time_text_view);
        roshan_respawn_button = (Button) findViewById(R.id.roshan_respawn_button);
        aegis_reclaim_button = (Button) findViewById(R.id.aegis_reclaim_button);



        if (savedInstanceState != null) {
            aegis_reclaim_button.setText("test");
        }



        Button gameStartbutton = (Button) findViewById(R.id.game_start_button);
        gameStartbutton.setText(getString(R.string.game_timer_button_start));
        gameStartbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                if (b.getText().equals("stop")) {
                    timerHandler.removeCallbacks(timerRunnable);
                    b.setText(getString(R.string.game_timer_button_start));
                } else {
                    gameStartTime = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);
                    b.setText("stop");
                }
            }
        });
        mediaplayer = MediaPlayer.create(this, R.raw.ff7victory);
    }

    @Override
    public void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
        Button b = (Button) findViewById(R.id.game_start_button);
        b.setText("start");
    }

    //called when roshan timer button is clicked
    public void roshanTimer(View view) {
        played = false;
        roshanDeadTime = System.currentTimeMillis();
        roshanEarliestRespawnTime = roshanDeadTime + ROSHAN_MIN_RESPAWN;
        roshanLatestRespawnTime = roshanDeadTime + ROSHAN_MAX_RESPAWN;
        aegisReclaimTime = roshanDeadTime + AEGIS_RECLAIM_TIME;
        roshanTimerHandler.postDelayed(roshanTimerRunnable, 0);
    }

    public void aegisTimer(View view) {
        aegisReclaimTime = System.currentTimeMillis() + AEGIS_RECLAIM_TIME;
        roshanTimerHandler.postDelayed(roshanTimerRunnable, 0);
    }

}


