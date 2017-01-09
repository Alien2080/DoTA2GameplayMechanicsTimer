package com.android_app.alien.dota2gameplaymechanicstimer;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import static com.android_app.alien.dota2gameplaymechanicstimer.R.drawable.roshan;
import static com.android_app.alien.dota2gameplaymechanicstimer.R.id.hero_list_view;

public class MainActivity extends AppCompatActivity {

    //respawn times in milliseconds. Roshan respawns between 8 and 11 minutes, correct to version 7.01
    static int ROSHAN_MIN_RESPAWN = (8 * 60 * 1000);
    static int ROSHAN_MAX_RESPAWN = (11 * 60 * 1000);
    //Aegis is reclaimed 5 minutes after being picked up
    static int AEGIS_RECLAIM_TIME = (5 * 60 * 1000);

    ListView selected_hero_list_view;
    TextView game_time_text_view;
    Button roshan_respawn_button;
    Button aegis_reclaim_button;
    long gameStartTime = 0;
    long roshanDeadTime;
    long roshanEarliestRespawnTime;
    long roshanLatestRespawnTime;
    long aegisReclaimTime;

    //used to play the roshan respawn sound effect
    MediaPlayer mediaplayer;
    //used to determine if the roshan respwn sound effect has played
    Boolean played;

    //Game timer timer
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

    //Roshan and Aegis timer
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
        setContentView(R.layout.activity_main);

        //to stop the screen from turning off, will cause battery drain
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //gets String array of selected heroes from hero select screen
        Intent intent = getIntent();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String[] selectedHeroes = new String[5];
            selectedHeroes[0] = null;
            selectedHeroes = intent.getStringArrayExtra("selectedHeroes");
            selected_hero_list_view = (ListView) findViewById(R.id.selected_hero_list_view);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, selectedHeroes);
            // Assign adapter to ListView
            selected_hero_list_view.setAdapter(adapter);
        }

        roshanDeadTime = 0;
        roshanEarliestRespawnTime = 0;
        roshanLatestRespawnTime = 0;
        aegisReclaimTime = 0;
        played = true;
        game_time_text_view = (TextView) findViewById(R.id.game_time_text_view);
        roshan_respawn_button = (Button) findViewById(R.id.roshan_respawn_button);
        aegis_reclaim_button = (Button) findViewById(R.id.aegis_reclaim_button);
        Button gameStartbutton = (Button) findViewById(R.id.game_start_button);
        mediaplayer = MediaPlayer.create(this, R.raw.ff7victory);

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
    }

    //called when roshan_timer_button is clicked
    public void roshanTimer(View view) {
        played = false;
        roshanDeadTime = System.currentTimeMillis();
        roshanEarliestRespawnTime = roshanDeadTime + ROSHAN_MIN_RESPAWN;
        roshanLatestRespawnTime = roshanDeadTime + ROSHAN_MAX_RESPAWN;
        aegisReclaimTime = roshanDeadTime + AEGIS_RECLAIM_TIME;
        roshanTimerHandler.postDelayed(roshanTimerRunnable, 0);
    }

    //called when aegis_button is clicked
    public void aegisTimer(View view) {
        aegisReclaimTime = System.currentTimeMillis() + AEGIS_RECLAIM_TIME;
        roshanTimerHandler.postDelayed(roshanTimerRunnable, 0);
    }

    //called when select_hero_button is clicked
    public void heroSelect(View view) {
        Intent intent = new Intent(this, HeroPickerActivity.class);
        startActivity(intent);
    }

}


