package com.android_app.alien.dota2gameplaymechanicstimer;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Handler;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;

import android.widget.Button;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import static android.R.attr.button;
import static com.android_app.alien.dota2gameplaymechanicstimer.R.drawable.roshan;
import static com.android_app.alien.dota2gameplaymechanicstimer.R.id.game_start_button;
import static com.android_app.alien.dota2gameplaymechanicstimer.R.id.hero1_1_button;
import static com.android_app.alien.dota2gameplaymechanicstimer.R.id.hero1_2_button;
import static com.android_app.alien.dota2gameplaymechanicstimer.R.id.hero1_3_button;
import static com.android_app.alien.dota2gameplaymechanicstimer.R.id.hero1_layout;
import static com.android_app.alien.dota2gameplaymechanicstimer.R.id.hero1_textview;
import static com.android_app.alien.dota2gameplaymechanicstimer.R.id.hero1_textview_overlay;
import static com.android_app.alien.dota2gameplaymechanicstimer.R.id.hero1_time_textview;
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
    int buttonWidth;

    TextView hero1_textview;
    TextView hero2_textview;
    TextView hero3_textview;
    TextView hero4_textview;
    TextView hero5_textview;

    TextView hero1_textview_overlay;
    TextView hero2_textview_overlay;
    TextView hero3_textview_overlay;
    TextView hero4_textview_overlay;
    TextView hero5_textview_overlay;

    TextView hero1_time_textview;
    TextView hero2_time_textview;
    TextView hero3_time_textview;
    TextView hero4_time_textview;
    TextView hero5_time_textview;

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

    LinearLayout hereos_selected_layout_overlay;
    LinearLayout hero1_layout_overlay;
    LinearLayout hero2_layout_overlay;
    LinearLayout hero3_layout_overlay;
    LinearLayout hero4_layout_overlay;
    LinearLayout hero5_layout_overlay;

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
    double hero1CDFactor = 1;
    double hero2CDFactor = 1;
    double hero3CDFactor = 1;
    double hero4CDFactor = 1;
    double hero5CDFactor = 1;

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
                hero1_time_textview.setText(String.format("%d:%02d", minutesHero1Ult, secondsHero1Ult));
                hero1_layout_overlay.setVisibility(View.VISIBLE);
                hero1_layout.setVisibility(View.INVISIBLE);
            } else {
                hero1_layout_overlay.setVisibility(View.INVISIBLE);
                hero1_layout.setVisibility(View.VISIBLE);
            }
            if (System.currentTimeMillis() <= hero2UltTime) {
                hero2_time_textview.setText(String.format("%d:%02d", minutesHero2Ult, secondsHero2Ult));
                hero2_layout_overlay.setVisibility(View.VISIBLE);
                hero2_layout.setVisibility(View.INVISIBLE);
            } else {
                hero2_layout_overlay.setVisibility(View.INVISIBLE);
                hero2_layout.setVisibility(View.VISIBLE);
            }
            if (System.currentTimeMillis() <= hero3UltTime) {
                hero3_time_textview.setText(String.format("%d:%02d", minutesHero3Ult, secondsHero3Ult));
                hero3_layout_overlay.setVisibility(View.VISIBLE);
                hero3_layout.setVisibility(View.INVISIBLE);
            } else {
                hero3_layout_overlay.setVisibility(View.INVISIBLE);
                hero3_layout.setVisibility(View.VISIBLE);
            }
            if (System.currentTimeMillis() <= hero4UltTime) {
                hero4_time_textview.setText(String.format("%d:%02d", minutesHero4Ult, secondsHero4Ult));
                hero4_layout_overlay.setVisibility(View.VISIBLE);
                hero4_layout.setVisibility(View.INVISIBLE);
            } else {
                hero4_layout_overlay.setVisibility(View.INVISIBLE);
                hero4_layout.setVisibility(View.VISIBLE);
            }
            if (System.currentTimeMillis() <= hero5UltTime) {
                hero5_time_textview.setText(String.format("%d:%02d", minutesHero5Ult, secondsHero5Ult));
                hero5_layout_overlay.setVisibility(View.VISIBLE);
                hero5_layout.setVisibility(View.INVISIBLE);
            } else {
                hero5_layout_overlay.setVisibility(View.INVISIBLE);
                hero5_layout.setVisibility(View.VISIBLE);
            }
            aegis_reclaim_button.setText("aegis reclaim\n" + String.format("%d:%02d", minutesAegis, secondsAegis));
            if (System.currentTimeMillis() <= roshanEarliestRespawnTime) {
                roshan_respawn_button.setText("roshan respawn\n" + String.format("%d:%02d", minutesMin, secondsMin) + " and " + String.format("%d:%02d", minutesMax, secondsMax));
            } else if (System.currentTimeMillis() > roshanEarliestRespawnTime && System.currentTimeMillis() < roshanLatestRespawnTime) {
                roshan_respawn_button.setText("roshan respawn\nNow and " + String.format("%d:%02d", minutesMax, secondsMax));
            } else if (System.currentTimeMillis() >= roshanLatestRespawnTime) {
                roshan_respawn_button.setText("roshan respawn\nNow");
                if (!played) {
                    mediaplayer.start();
                    played = true;
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

        if (savedInstanceState != null) {
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

        hero1_textview_overlay = (TextView) findViewById(R.id.hero1_textview_overlay);
        hero2_textview_overlay = (TextView) findViewById(R.id.hero2_textview_overlay);
        hero3_textview_overlay = (TextView) findViewById(R.id.hero3_textview_overlay);
        hero4_textview_overlay = (TextView) findViewById(R.id.hero4_textview_overlay);
        hero5_textview_overlay = (TextView) findViewById(R.id.hero5_textview_overlay);

        hero1_time_textview = (TextView) findViewById(R.id.hero1_time_textview);
        hero2_time_textview = (TextView) findViewById(R.id.hero2_time_textview);
        hero3_time_textview = (TextView) findViewById(R.id.hero3_time_textview);
        hero4_time_textview = (TextView) findViewById(R.id.hero4_time_textview);
        hero5_time_textview = (TextView) findViewById(R.id.hero5_time_textview);

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

        hereos_selected_layout_overlay = (LinearLayout) findViewById(R.id.hereos_selected_layout_overlay);
        hero1_layout_overlay = (LinearLayout) findViewById(R.id.hero1_layout_overlay);
        hero2_layout_overlay = (LinearLayout) findViewById(R.id.hero2_layout_overlay);
        hero3_layout_overlay = (LinearLayout) findViewById(R.id.hero3_layout_overlay);
        hero4_layout_overlay = (LinearLayout) findViewById(R.id.hero4_layout_overlay);
        hero5_layout_overlay = (LinearLayout) findViewById(R.id.hero5_layout_overlay);

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
        hero1UltTime = System.currentTimeMillis() + (long) (ultTimes[0] * hero1CDFactor);
    }

    //called when hero1 ultiamte button is clicked
    public void hero1_2UltTimer(View view) {
        hero1UltTime = System.currentTimeMillis() + (long) (ultTimes[1] * hero1CDFactor);
    }

    //called when hero1 ultiamte button is clicked
    public void hero1_3UltTimer(View view) {
        hero1UltTime = System.currentTimeMillis() + (long) (ultTimes[2] * hero1CDFactor);
    }

    //called when hero1 ultiamte button is clicked
    public void hero2_1UltTimer(View view) {
        hero2UltTime = System.currentTimeMillis() + (long) (ultTimes[3] * hero2CDFactor);
    }

    //called when hero1 ultiamte button is clicked
    public void hero2_2UltTimer(View view) {
        hero2UltTime = System.currentTimeMillis() + (long) (ultTimes[4] * hero2CDFactor);
    }

    //called when hero1 ultiamte button is clicked
    public void hero2_3UltTimer(View view) {
        hero2UltTime = System.currentTimeMillis() + (long) (ultTimes[5] * hero2CDFactor);
    }

    //called when hero1 ultiamte button is clicked
    public void hero3_1UltTimer(View view) {
        hero3UltTime = System.currentTimeMillis() + (long) (ultTimes[6] * hero3CDFactor);
    }

    //called when hero1 ultiamte button is clicked
    public void hero3_2UltTimer(View view) {
        hero3UltTime = System.currentTimeMillis() + (long) (ultTimes[7] * hero3CDFactor);
    }

    //called when hero1 ultiamte button is clicked
    public void hero3_3UltTimer(View view) {
        hero3UltTime = System.currentTimeMillis() + (long) (ultTimes[8] * hero3CDFactor);
    }

    //called when hero1 ultiamte button is clicked
    public void hero4_1UltTimer(View view) {
        hero4UltTime = System.currentTimeMillis() + (long) (ultTimes[9] * hero4CDFactor);
    }

    //called when hero1 ultiamte button is clicked
    public void hero4_2UltTimer(View view) {
        hero4UltTime = System.currentTimeMillis() + (long) (ultTimes[10] * hero4CDFactor);
    }

    //called when hero1 ultiamte button is clicked
    public void hero4_3UltTimer(View view) {
        hero4UltTime = System.currentTimeMillis() + (long) (ultTimes[11] * hero4CDFactor);
    }

    //called when hero1 ultiamte button is clicked
    public void hero5_1UltTimer(View view) {
        hero5UltTime = System.currentTimeMillis() + (long) (ultTimes[12] * hero5CDFactor);
    }

    //called when hero1 ultiamte button is clicked
    public void hero5_2UltTimer(View view) {
        hero5UltTime = System.currentTimeMillis() + (long) (ultTimes[13] * hero5CDFactor);
    }

    //called when hero1 ultiamte button is clicked
    public void hero5_3UltTimer(View view) {
        hero5UltTime = System.currentTimeMillis() + (long) (ultTimes[14] * hero5CDFactor);
    }

    // invoked when the activity may be temporarily destroyed, save the instance state here
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putLong("gameStartTime", gameStartTime);
        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
    }

    @Override
    public void onResume() {
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
            if (selectedHeroes != null) {
                if (selectedHeroes[0] != null) {
                    hero1_textview.setText(selectedHeroes[0]);
                    hero1_textview_overlay.setText(selectedHeroes[0]);
                    hero1_layout.setVisibility(View.VISIBLE);
                    hereos_selected_layout.setVisibility(View.VISIBLE);
                } else {
                    hereos_selected_layout.setVisibility(View.INVISIBLE);
                    hero1_layout.setVisibility(View.INVISIBLE);
                }
                if (selectedHeroes[1] != null) {
                    hero2_textview.setText(selectedHeroes[1]);
                    hero2_textview_overlay.setText(selectedHeroes[1]);
                    hero2_layout.setVisibility(View.VISIBLE);
                } else {
                    hero2_layout.setVisibility(View.INVISIBLE);
                }
                if (selectedHeroes[2] != null) {
                    hero3_textview.setText(selectedHeroes[2]);
                    hero3_textview_overlay.setText(selectedHeroes[2]);
                    hero3_layout.setVisibility(View.VISIBLE);
                } else {
                    hero3_layout.setVisibility(View.INVISIBLE);
                }
                if (selectedHeroes[3] != null) {
                    hero4_textview.setText(selectedHeroes[3]);
                    hero4_textview_overlay.setText(selectedHeroes[3]);
                    hero4_layout.setVisibility(View.VISIBLE);
                } else {
                    hero4_layout.setVisibility(View.INVISIBLE);
                }
                if (selectedHeroes[4] != null) {
                    hero5_textview.setText(selectedHeroes[4]);
                    hero5_textview_overlay.setText(selectedHeroes[4]);
                    hero5_layout.setVisibility(View.VISIBLE);
                } else {
                    hero5_layout.setVisibility(View.INVISIBLE);
                }
            } else {
                hereos_selected_layout.setVisibility(View.INVISIBLE);
            }
        } else {
            hereos_selected_layout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        //now getIntent() should always return the last received intent
    }

    public void adjustCDReduction1(View view) {
        buildCDDIalog(view, 1);
    }

    public void adjustCDReduction2(View view) {
        buildCDDIalog(view, 2);
    }

    public void adjustCDReduction3(View view) {
        buildCDDIalog(view, 3);
    }

    public void adjustCDReduction4(View view) {
        buildCDDIalog(view, 4);
    }

    public void adjustCDReduction5(View view) {
        buildCDDIalog(view, 5);
    }

    public void buildCDDIalog(View view, final int heronumber) {
        // Build an AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        // String array for alert dialog multi choice items
        String[] modifiers = new String[]{
                "Octerine Core",
                "Talent 10%",
                "Talent 12%",
                "Talent 15%",
                "Talent 20%",
                "aghanim scepter, does nothing ATM!",
                "KOTL chakra magic, does nothing!!"
        };

        // Boolean array for initial selected items
        final boolean[] checkedModifiers = new boolean[]{
                false, // Octerine
                false, // 10%
                false, // 12%
                false, // 15%
                false, // 20%
                false, // aghs
                false  // kotl
        };

        // Convert the color array to list
        final List<String> modifiersList = Arrays.asList(modifiers);

        // Set multiple choice items for alert dialog
                /*
                    AlertDialog.Builder setMultiChoiceItems(CharSequence[] items, boolean[]
                    checkedItems, DialogInterface.OnMultiChoiceClickListener listener)
                        Set a list of items to be displayed in the dialog as the content,
                        you will be notified of the selected item via the supplied listener.
                 */
                /*
                    DialogInterface.OnMultiChoiceClickListener
                    public abstract void onClick (DialogInterface dialog, int which, boolean isChecked)

                        This method will be invoked when an item in the dialog is clicked.

                        Parameters
                        dialog The dialog where the selection was made.
                        which The position of the item in the list that was clicked.
                        isChecked True if the click checked the item, else false.
                 */
        builder.setMultiChoiceItems(modifiers, checkedModifiers, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                // Update the current focused item's checked status
                checkedModifiers[which] = isChecked;

                // Get the current focused item
                String currentItem = modifiersList.get(which);

                // Notify the current action
                Toast.makeText(getApplicationContext(),
                        currentItem + " " + isChecked, Toast.LENGTH_SHORT).show();
            }
        });

        // Specify the dialog is not cancelable
        builder.setCancelable(false);

        // Set a title for alert dialog
        builder.setTitle("Cooldown Reduction Modifiers");

        // Set the positive/yes button click listener
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click positive button
                double cooldownModifier = 1;
                if (checkedModifiers[0]) {
                    cooldownModifier = (cooldownModifier * 0.75);
                }
                if (checkedModifiers[1]) {
                    cooldownModifier = (cooldownModifier * 0.9);
                }
                if (checkedModifiers[2]) {
                    cooldownModifier = (cooldownModifier * 0.88);
                }
                if (checkedModifiers[3]) {
                    cooldownModifier = (cooldownModifier * 0.85);
                }
                if (checkedModifiers[4]) {
                    cooldownModifier = (cooldownModifier * 0.80);
                }
                if (heronumber == 1) {
                    hero1CDFactor = cooldownModifier;
                }
                if (heronumber == 2) {
                    hero2CDFactor = cooldownModifier;
                }
                if (heronumber == 3) {
                    hero3CDFactor = cooldownModifier;
                }
                if (heronumber == 4) {
                    hero4CDFactor = cooldownModifier;
                }
                if (heronumber == 5) {
                    hero5CDFactor = cooldownModifier;
                }
            }
        });

        // Set the neutral/cancel button click listener
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click the neutral button
            }
        });

        AlertDialog dialog = builder.create();
        // Display the alert dialog on interface
        dialog.show();
    }
}



