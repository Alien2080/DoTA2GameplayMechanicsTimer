package com.android_app.alien.dota2gameplaymechanicstimer;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.view.WindowManager;

import android.widget.Button;
import android.widget.LinearLayout;

import android.widget.TextView;

import static android.R.attr.button;
import static com.android_app.alien.dota2gameplaymechanicstimer.R.drawable.roshan;
import static com.android_app.alien.dota2gameplaymechanicstimer.R.id.game_start_button;
import static com.android_app.alien.dota2gameplaymechanicstimer.R.id.hero1_1_button;
import static com.android_app.alien.dota2gameplaymechanicstimer.R.id.hero1_2_button;
import static com.android_app.alien.dota2gameplaymechanicstimer.R.id.hero1_3_button;
import static com.android_app.alien.dota2gameplaymechanicstimer.R.id.hero1_layout;
import static com.android_app.alien.dota2gameplaymechanicstimer.R.id.hero1_textview;
import static com.android_app.alien.dota2gameplaymechanicstimer.R.id.hero2_1_button;
import static com.android_app.alien.dota2gameplaymechanicstimer.R.id.hero2_2_button;
import static com.android_app.alien.dota2gameplaymechanicstimer.R.id.hero2_3_button;
import static com.android_app.alien.dota2gameplaymechanicstimer.R.id.hero2_layout;
import static com.android_app.alien.dota2gameplaymechanicstimer.R.id.hero3_layout;
import static com.android_app.alien.dota2gameplaymechanicstimer.R.id.hero4_layout;
import static com.android_app.alien.dota2gameplaymechanicstimer.R.id.hero5_layout;

public class MainActivity extends AppCompatActivity {

    //respawn times in milliseconds. Roshan respawns between 8 and 11 minutes, correct to version 7.01
    static int ROSHAN_MIN_RESPAWN = (8 * 60 * 1000);
    static int ROSHAN_MAX_RESPAWN = (11 * 60 * 1000);
    //Aegis is reclaimed 5 minutes after being picked up
    static int AEGIS_RECLAIM_TIME = (5 * 60 * 1000);

    TextView hero1_textview;
    TextView hero2_textview;
    TextView hero3_textview;
    TextView hero4_textview;
    TextView hero5_textview;

    Button hero1_1_button;
    Button hero1_2_button;
    Button hero1_3_button;

    Button hero2_1_button;
    Button hero2_2_button;
    Button hero2_3_button;

    Button hero3_1_button;
    Button hero3_2_button;
    Button hero3_3_button;

    Button hero4_1_button;
    Button hero4_2_button;
    Button hero4_3_button;

    Button hero5_1_button;
    Button hero5_2_button;
    Button hero5_3_button;

    LinearLayout hereos_selected_layout;
    LinearLayout hero1_layout;
    LinearLayout hero2_layout;
    LinearLayout hero3_layout;
    LinearLayout hero4_layout;
    LinearLayout hero5_layout;

    //    ListView selected_hero_list_view;
    TextView game_time_text_view;
    Button roshan_respawn_button;
    Button aegis_reclaim_button;
    Button select_hero_button;
    long gameStartTime;
    long roshanDeadTime;
    long roshanEarliestRespawnTime;
    long roshanLatestRespawnTime;
    long aegisReclaimTime;
    long hero1UltTime = 0;
    long hero2UltTime = 0;
    long hero3UltTime = 0;
    long hero4UltTime = 0;
    long hero5UltTime = 0;

    int[] ultTimes = new int[5 * 3];

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

            long millisHero1Ult = hero1UltTime - System.currentTimeMillis();
            int secondsHero1Ult = (int) (millisHero1Ult / 1000);
            int minutesHero1Ult = secondsHero1Ult / 60;
            secondsHero1Ult = secondsHero1Ult % 60;

            long millisHero2Ult = hero2UltTime - System.currentTimeMillis();
            int secondsHero2Ult = (int) (millisHero2Ult / 1000);
            int minutesHero2Ult = secondsHero2Ult / 60;
            secondsHero2Ult = secondsHero2Ult % 60;

            long millisHero3Ult = hero3UltTime - System.currentTimeMillis();
            int secondsHero3Ult = (int) (millisHero3Ult / 1000);
            int minutesHero3Ult = secondsHero3Ult / 60;
            secondsHero3Ult = secondsHero3Ult % 60;

            long millisHero4Ult = hero4UltTime - System.currentTimeMillis();
            int secondsHero4Ult = (int) (millisHero4Ult / 1000);
            int minutesHero4Ult = secondsHero4Ult / 60;
            secondsHero4Ult = secondsHero4Ult % 60;

            long millisHero5Ult = hero5UltTime - System.currentTimeMillis();
            int secondsHero5Ult = (int) (millisHero5Ult / 1000);
            int minutesHero5Ult = secondsHero5Ult / 60;
            secondsHero5Ult = secondsHero5Ult % 60;

            if (System.currentTimeMillis() <= hero1UltTime) {
                hero1_1_button.setText(String.format("%d:%02d", minutesHero1Ult, secondsHero1Ult));
                hero1_2_button.setText(String.format("%d:%02d", minutesHero1Ult, secondsHero1Ult));
                hero1_3_button.setText(String.format("%d:%02d", minutesHero1Ult, secondsHero1Ult));
            } else {
                hero1_1_button.setText("Level 1");
                hero1_2_button.setText("Level 2");
                hero1_3_button.setText("Level 3");
            }

            if (System.currentTimeMillis() <= hero2UltTime) {
                hero2_1_button.setText(String.format("%d:%02d", minutesHero2Ult, secondsHero2Ult));
                hero2_2_button.setText(String.format("%d:%02d", minutesHero2Ult, secondsHero2Ult));
                hero2_3_button.setText(String.format("%d:%02d", minutesHero2Ult, secondsHero2Ult));
            } else {
                hero2_1_button.setText("Level 1");
                hero2_2_button.setText("Level 2");
                hero2_3_button.setText("Level 3");
            }
            if (System.currentTimeMillis() <= hero3UltTime) {
                hero3_1_button.setText(String.format("%d:%02d", minutesHero3Ult, secondsHero3Ult));
                hero3_2_button.setText(String.format("%d:%02d", minutesHero3Ult, secondsHero3Ult));
                hero3_3_button.setText(String.format("%d:%02d", minutesHero3Ult, secondsHero3Ult));
            } else {
                hero3_1_button.setText("Level 1");
                hero3_2_button.setText("Level 2");
                hero3_3_button.setText("Level 3");
            }
            if (System.currentTimeMillis() <= hero4UltTime) {
                hero4_1_button.setText(String.format("%d:%02d", minutesHero4Ult, secondsHero4Ult));
                hero4_2_button.setText(String.format("%d:%02d", minutesHero4Ult, secondsHero4Ult));
                hero4_3_button.setText(String.format("%d:%02d", minutesHero4Ult, secondsHero4Ult));
            } else {
                hero4_1_button.setText("Level 1");
                hero4_2_button.setText("Level 2");
                hero4_3_button.setText("Level 3");
            }
            if (System.currentTimeMillis() <= hero5UltTime) {
                hero5_1_button.setText(String.format("%d:%02d", minutesHero5Ult, secondsHero5Ult));
                hero5_2_button.setText(String.format("%d:%02d", minutesHero5Ult, secondsHero5Ult));
                hero5_3_button.setText(String.format("%d:%02d", minutesHero5Ult, secondsHero5Ult));
            } else {
                hero5_1_button.setText("Level 1");
                hero5_2_button.setText("Level 2");
                hero5_3_button.setText("Level 3");
            }

            aegis_reclaim_button.setText("aegis reclaim\n" + String.format("%d:%02d", minutesAegis, secondsAegis));
            if (System.currentTimeMillis() <= roshanEarliestRespawnTime) {
                roshan_respawn_button.setText("roshan respawn\n" + String.format("%d:%02d", minutesMin, secondsMin) + " and " + String.format("%d:%02d", minutesMax, secondsMax));
                // roshanTimerHandler.postDelayed(this, 500);
            } else if (System.currentTimeMillis() > roshanEarliestRespawnTime && System.currentTimeMillis() < roshanLatestRespawnTime) {
                roshan_respawn_button.setText("roshan respawn\nNow and " + String.format("%d:%02d", minutesMax, secondsMax));
                //  roshanTimerHandler.postDelayed(this, 500);
            } else if (System.currentTimeMillis() >= roshanLatestRespawnTime) {
                roshan_respawn_button.setText("roshan respawn\nNow");
                if (!played) {
                    mediaplayer.start();
                    played = true;
                }
                if (System.currentTimeMillis() < aegisReclaimTime) {
                    // roshanTimerHandler.postDelayed(this, 500);
                }
            }
            if (System.currentTimeMillis() >= aegisReclaimTime) {
                aegis_reclaim_button.setText("aegis reclaim\n done");
            }
            timerHandler.postDelayed(this, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null){
            gameStartTime = savedInstanceState.getLong("gameStartTime");
            timerHandler.postDelayed(timerRunnable, 0);
        }

        setContentView(R.layout.activity_main);

        //to stop the screen from turning off, will cause battery drain
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        hero1_textview = (TextView) findViewById(R.id.hero1_textview);
        hero2_textview = (TextView) findViewById(R.id.hero2_textview);
        hero3_textview = (TextView) findViewById(R.id.hero3_textview);
        hero4_textview = (TextView) findViewById(R.id.hero4_textview);
        hero5_textview = (TextView) findViewById(R.id.hero5_textview);

        hero1_1_button = (Button) findViewById(R.id.hero1_1_button);
        hero1_2_button = (Button) findViewById(R.id.hero1_2_button);
        hero1_3_button = (Button) findViewById(R.id.hero1_3_button);
        hero2_1_button = (Button) findViewById(R.id.hero2_1_button);
        hero2_2_button = (Button) findViewById(R.id.hero2_2_button);
        hero2_3_button = (Button) findViewById(R.id.hero2_3_button);
        hero3_1_button = (Button) findViewById(R.id.hero3_1_button);
        hero3_2_button = (Button) findViewById(R.id.hero3_2_button);
        hero3_3_button = (Button) findViewById(R.id.hero3_3_button);
        hero4_1_button = (Button) findViewById(R.id.hero4_1_button);
        hero4_2_button = (Button) findViewById(R.id.hero4_2_button);
        hero4_3_button = (Button) findViewById(R.id.hero4_3_button);
        hero5_1_button = (Button) findViewById(R.id.hero5_1_button);
        hero5_2_button = (Button) findViewById(R.id.hero5_2_button);
        hero5_3_button = (Button) findViewById(R.id.hero5_3_button);

        hereos_selected_layout = (LinearLayout) findViewById(R.id.hereos_selected_layout);
        hero1_layout = (LinearLayout) findViewById(R.id.hero1_layout);
        hero2_layout = (LinearLayout) findViewById(R.id.hero2_layout);
        hero3_layout = (LinearLayout) findViewById(R.id.hero3_layout);
        hero4_layout = (LinearLayout) findViewById(R.id.hero4_layout);
        hero5_layout = (LinearLayout) findViewById(R.id.hero5_layout);

        //roshanDeadTime = 0;
        //roshanEarliestRespawnTime = 0;
        //roshanLatestRespawnTime = 0;
        //aegisReclaimTime = 0;
        played = true;
        game_time_text_view = (TextView) findViewById(R.id.game_time_text_view);
        roshan_respawn_button = (Button) findViewById(R.id.roshan_respawn_button);
        aegis_reclaim_button = (Button) findViewById(R.id.aegis_reclaim_button);
        select_hero_button = (Button) findViewById(R.id.select_hero_button);
        Button gameStartbutton = (Button) findViewById(R.id.game_start_button);
        mediaplayer = MediaPlayer.create(this, R.raw.ff7victory);


        //this places the string literal into the res/values/strings.xml file where they all should be for easy of translation (among other reasons)
        gameStartbutton.setText(getString(R.string.game_timer_button_start));
        gameStartbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                if (b.getText().equals("Pause")) {
                    timerHandler.removeCallbacks(timerRunnable);
                    b.setText("Resume");
                } else if (b.getText().equals("New Game")) {
                    gameStartTime = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);
                    b.setText("Pause");
                } else if (b.getText().equals("Resume")) {
                    timerHandler.postDelayed(timerRunnable, 0);
                    b.setText("Pause");
                }
            }
        });
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        gameStartTime = savedInstanceState.getLong("gameStartTime");
        timerHandler.postDelayed(timerRunnable, 0);
    }

    //called when roshan_timer_button is clicked
    public void roshanTimer(View view) {
        played = false;
        roshanDeadTime = System.currentTimeMillis();
        roshanEarliestRespawnTime = roshanDeadTime + ROSHAN_MIN_RESPAWN;
        roshanLatestRespawnTime = roshanDeadTime + ROSHAN_MAX_RESPAWN;
        aegisReclaimTime = roshanDeadTime + AEGIS_RECLAIM_TIME;
        //  timerHandler.postDelayed(timerRunnable, 0);
    }

    //called when aegis_button is clicked
    public void aegisTimer(View view) {
        aegisReclaimTime = System.currentTimeMillis() + AEGIS_RECLAIM_TIME;
        // timerHandler.postDelayed(timerRunnable, 0);
    }

    //called when select_hero_button is clicked
    public void heroSelect(View view) {
        Intent intent = new Intent(this, HeroPickerActivity.class);
        startActivity(intent);
    }

    //called when hero1 ultiamte button is clicked
    public void hero1_1UltTimer(View view) {
        hero1UltTime = System.currentTimeMillis() + ultTimes[0];
    }

    //called when hero1 ultiamte button is clicked
    public void hero1_2UltTimer(View view) {
        hero1UltTime = System.currentTimeMillis() + ultTimes[1];
    }

    //called when hero1 ultiamte button is clicked
    public void hero1_3UltTimer(View view) {
        hero1UltTime = System.currentTimeMillis() + ultTimes[2];
    }
    //called when hero1 ultiamte button is clicked
    public void hero2_1UltTimer(View view) {
        hero2UltTime = System.currentTimeMillis() + ultTimes[3];
    }

    //called when hero1 ultiamte button is clicked
    public void hero2_2UltTimer(View view) {
        hero2UltTime = System.currentTimeMillis() + ultTimes[4];
    }

    //called when hero1 ultiamte button is clicked
    public void hero2_3UltTimer(View view) {
        hero2UltTime = System.currentTimeMillis() + ultTimes[5];
    }
    //called when hero1 ultiamte button is clicked
    public void hero3_1UltTimer(View view) {
        hero3UltTime = System.currentTimeMillis() + ultTimes[6];
    }

    //called when hero1 ultiamte button is clicked
    public void hero3_2UltTimer(View view) {
        hero3UltTime = System.currentTimeMillis() + ultTimes[7];
    }

    //called when hero1 ultiamte button is clicked
    public void hero3_3UltTimer(View view) {
        hero3UltTime = System.currentTimeMillis() + ultTimes[8];
    }
    //called when hero1 ultiamte button is clicked
    public void hero4_1UltTimer(View view) {
        hero4UltTime = System.currentTimeMillis() + ultTimes[9];
    }

    //called when hero1 ultiamte button is clicked
    public void hero4_2UltTimer(View view) {
        hero4UltTime = System.currentTimeMillis() + ultTimes[10];
    }

    //called when hero1 ultiamte button is clicked
    public void hero4_3UltTimer(View view) {
        hero4UltTime = System.currentTimeMillis() + ultTimes[11];
    }
    //called when hero1 ultiamte button is clicked
    public void hero5_1UltTimer(View view) {
        hero5UltTime = System.currentTimeMillis() + ultTimes[12];
    }

    //called when hero1 ultiamte button is clicked
    public void hero5_2UltTimer(View view) {
        hero5UltTime = System.currentTimeMillis() + ultTimes[13];
    }

    //called when hero1 ultiamte button is clicked
    public void hero5_3UltTimer(View view) {
        hero5UltTime = System.currentTimeMillis() + ultTimes[14];
    }

    // invoked when the activity may be temporarily destroyed, save the instance state here
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putLong("gameStartTime", gameStartTime);
        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause (){
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
    }
    @Override
    public void onResume (){
        super.onResume();
        if (gameStartTime != 0) {
            timerHandler.postDelayed(timerRunnable, 0);
        }
        //this use a constant 5 but should really use the MAX_SELECTED_HEROES static int
        String[] selectedHeroes;
        //gets String array of selected heroes from hero select screen
        Intent intent = getIntent();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selectedHeroes = intent.getStringArrayExtra("selectedHeroes");
            ultTimes = intent.getIntArrayExtra("selectedHeroesUltTimes");
            if(selectedHeroes[0] != null) {
                hero1_textview.setText(selectedHeroes[0]);
                hero1_layout.setVisibility(View.VISIBLE);
                hereos_selected_layout.setVisibility(View.VISIBLE);
            }else{
                hereos_selected_layout.setVisibility(View.INVISIBLE);
                hero1_layout.setVisibility(View.INVISIBLE);
            }
            if(selectedHeroes[1] != null) {
                hero2_textview.setText(selectedHeroes[1]);
                hero2_layout.setVisibility(View.VISIBLE);
            }else{
                hero2_layout.setVisibility(View.INVISIBLE);
            }
            if(selectedHeroes[2] != null) {
                hero3_textview.setText(selectedHeroes[2]);
                hero3_layout.setVisibility(View.VISIBLE);
            }else{
                hero3_layout.setVisibility(View.INVISIBLE);
            }
            if(selectedHeroes[3] != null) {
                hero4_textview.setText(selectedHeroes[3]);
                hero4_layout.setVisibility(View.VISIBLE);
            }else{
                hero4_layout.setVisibility(View.INVISIBLE);
            }
            if(selectedHeroes[4] != null) {
                hero5_textview.setText(selectedHeroes[4]);
                hero5_layout.setVisibility(View.VISIBLE);
            }else{
                hero5_layout.setVisibility(View.INVISIBLE);
            }
        }else{
            hereos_selected_layout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        //now getIntent() should always return the last received intent
    }
}


