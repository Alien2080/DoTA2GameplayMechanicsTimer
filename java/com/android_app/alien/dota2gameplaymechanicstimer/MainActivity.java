package com.android_app.alien.dota2gameplaymechanicstimer;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Handler;
import android.os.Bundle;
//import android.text.Layout;
//import android.util.Log;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
//import android.view.ViewGroup;
//import android.view.ViewGroup.LayoutParams;
//import android.view.WindowManager;

import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;
import java.util.prefs.Preferences;

import static android.R.attr.button;
import static android.R.attr.name;
import static android.R.id.list;
import static android.os.Build.VERSION_CODES.M;
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
    // Boolean array for initial selected items
    final boolean[] checkedModifiers = new boolean[]{
            false, // Octarine
            false, // Talent
            false, // Aghs
    };
    final boolean[] optionsChecked = {
            false,
            false
    };
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
    TextView ultimate_timer_textview;
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
    ImageView hero1_aghs_imageview;
    ImageView hero1_OC_imageview;
    ImageView hero1_talent_imageview;
    ImageView hero2_aghs_imageview;
    ImageView hero2_OC_imageview;
    ImageView hero2_talent_imageview;
    ImageView hero3_aghs_imageview;
    ImageView hero3_OC_imageview;
    ImageView hero3_talent_imageview;
    ImageView hero4_aghs_imageview;
    ImageView hero4_OC_imageview;
    ImageView hero4_talent_imageview;
    ImageView hero5_aghs_imageview;
    ImageView hero5_OC_imageview;
    ImageView hero5_talent_imageview;
    TextView game_time_text_view;
    Button roshan_respawn_button;
    Button aegis_reclaim_button;
    Button select_hero_button;
    Button game_start_button;
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
    String[] selectedHeroes;
    boolean roshanSoundOption;
    boolean screenAlwaysOnOption;

    int listStyle;
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
            if (selectedHeroes != null) {
                if (selectedHeroes[0] != null) {
                    if (System.currentTimeMillis() <= hero1UltTime) {
                        hero1_time_textview.setText(String.format("%d:%02d", minutesHero1Ult, secondsHero1Ult));
                        hero1_layout_overlay.setVisibility(View.VISIBLE);
                        hero1_layout.setVisibility(View.INVISIBLE);
                    } else {
                        hero1_layout_overlay.setVisibility(View.INVISIBLE);
                        hero1_layout.setVisibility(View.VISIBLE);
                    }
                }
                if (selectedHeroes[1] != null) {
                    if (System.currentTimeMillis() <= hero2UltTime) {
                        hero2_time_textview.setText(String.format("%d:%02d", minutesHero2Ult, secondsHero2Ult));
                        hero2_layout_overlay.setVisibility(View.VISIBLE);
                        hero2_layout.setVisibility(View.INVISIBLE);
                    } else {
                        hero2_layout_overlay.setVisibility(View.INVISIBLE);
                        hero2_layout.setVisibility(View.VISIBLE);
                    }
                }
                if (selectedHeroes[2] != null) {
                    if (System.currentTimeMillis() <= hero3UltTime) {
                        hero3_time_textview.setText(String.format("%d:%02d", minutesHero3Ult, secondsHero3Ult));
                        hero3_layout_overlay.setVisibility(View.VISIBLE);
                        hero3_layout.setVisibility(View.INVISIBLE);
                    } else {
                        hero3_layout_overlay.setVisibility(View.INVISIBLE);
                        hero3_layout.setVisibility(View.VISIBLE);
                    }
                }
                if (selectedHeroes[3] != null) {
                    if (System.currentTimeMillis() <= hero4UltTime) {
                        hero4_time_textview.setText(String.format("%d:%02d", minutesHero4Ult, secondsHero4Ult));
                        hero4_layout_overlay.setVisibility(View.VISIBLE);
                        hero4_layout.setVisibility(View.INVISIBLE);
                    } else {
                        hero4_layout_overlay.setVisibility(View.INVISIBLE);
                        hero4_layout.setVisibility(View.VISIBLE);
                    }
                }
                if (selectedHeroes[4] != null) {
                    if (System.currentTimeMillis() <= hero5UltTime) {
                        hero5_time_textview.setText(String.format("%d:%02d", minutesHero5Ult, secondsHero5Ult));
                        hero5_layout_overlay.setVisibility(View.VISIBLE);
                        hero5_layout.setVisibility(View.INVISIBLE);
                    } else {
                        hero5_layout_overlay.setVisibility(View.INVISIBLE);
                        hero5_layout.setVisibility(View.VISIBLE);
                    }
                }
            }
            aegis_reclaim_button.setText("aegis reclaim\n" + String.format("%d:%02d", minutesAegis, secondsAegis));
            if (System.currentTimeMillis() <= roshanEarliestRespawnTime) {
                roshan_respawn_button.setText("roshan respawn\n" + String.format("%d:%02d", minutesMin, secondsMin) + " and " + String.format("%d:%02d", minutesMax, secondsMax));
            } else if (System.currentTimeMillis() > roshanEarliestRespawnTime && System.currentTimeMillis() < roshanLatestRespawnTime) {
                roshan_respawn_button.setText("roshan respawn\nNow and " + String.format("%d:%02d", minutesMax, secondsMax));
            } else if (System.currentTimeMillis() >= roshanLatestRespawnTime) {
                roshan_respawn_button.setText("roshan respawn\nNow");
                if (roshanSoundOption && !played) {
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

        // Restore preferences
        SharedPreferences settings = getSharedPreferences("optionsFile", 0);
        roshanSoundOption = settings.getBoolean("roshOption", false);
        screenAlwaysOnOption = settings.getBoolean("screenOnOption", false);

        if (savedInstanceState != null) {
            gameStartTime = savedInstanceState.getLong("gameStartTime");
            timerHandler.postDelayed(timerRunnable, 0);
        }

        setContentView(R.layout.activity_main);

        //to stop the screen from turning off, will cause battery drain
        if (screenAlwaysOnOption) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
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

        ultimate_timer_textview = (TextView) findViewById(R.id.ultimate_timer_textview);

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

        hero1_aghs_imageview = (ImageView) findViewById(R.id.hero1_aghs_imageview);
        hero1_OC_imageview = (ImageView) findViewById(R.id.hero1_OC_imageview);
        hero1_talent_imageview = (ImageView) findViewById(R.id.hero1_talent_imageview);

        hero2_aghs_imageview = (ImageView) findViewById(R.id.hero2_aghs_imageview);
        hero2_OC_imageview = (ImageView) findViewById(R.id.hero2_OC_imageview);
        hero2_talent_imageview = (ImageView) findViewById(R.id.hero2_talent_imageview);

        hero3_aghs_imageview = (ImageView) findViewById(R.id.hero3_aghs_imageview);
        hero3_OC_imageview = (ImageView) findViewById(R.id.hero3_OC_imageview);
        hero3_talent_imageview = (ImageView) findViewById(R.id.hero3_talent_imageview);

        hero4_aghs_imageview = (ImageView) findViewById(R.id.hero4_aghs_imageview);
        hero4_OC_imageview = (ImageView) findViewById(R.id.hero4_OC_imageview);
        hero4_talent_imageview = (ImageView) findViewById(R.id.hero4_talent_imageview);

        hero5_aghs_imageview = (ImageView) findViewById(R.id.hero5_aghs_imageview);
        hero5_OC_imageview = (ImageView) findViewById(R.id.hero5_OC_imageview);
        hero5_talent_imageview = (ImageView) findViewById(R.id.hero5_talent_imageview);

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
                game_start_button = (Button) v;
                if (game_start_button.getText().equals("Pause")) {
                    timerHandler.removeCallbacks(timerRunnable);
                    game_start_button.setText("Resume");
                } else if (game_start_button.getText().equals("New Game")) {
                    gameStartTime = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);
                    game_start_button.setText("Pause");
                } else if (game_start_button.getText().equals("Resume")) {
                    timerHandler.postDelayed(timerRunnable, 0);
                    game_start_button.setText("Pause");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * On selecting action bar icons
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_settings:
                settingsDialog();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Launching new activity
     */
    private void settingsDialog() {
        // Build an AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final String[] optionsList = {
                "Play a sound effect when Roshan is guaranteed to have respawned",
                "Keep screen on always, will use more battery"
        };

        if (roshanSoundOption == true) {
            optionsChecked[0] = true;
        } else {
            optionsChecked[0] = false;
        }
        if (screenAlwaysOnOption == true) {
            optionsChecked[1] = true;
        } else {
            optionsChecked[1] = false;
        }

        final List<String> modifiersList = Arrays.asList(optionsList);

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
        builder.setMultiChoiceItems(optionsList, optionsChecked, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                // Update the current focused item's checked status
                optionsChecked[which] = isChecked;

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
        builder.setTitle("Options");

        // Set the positive/yes button click listener
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click positive button
                if (optionsChecked[0]) {
                    roshanSoundOption = true;
                } else {
                    roshanSoundOption = false;
                }
                if (optionsChecked[1]) {
                    screenAlwaysOnOption = true;
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                } else {
                    screenAlwaysOnOption = false;
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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
        if (selectedHeroes != null) {
            selectedHeroes[0] = null;
            selectedHeroes[1] = null;
            selectedHeroes[2] = null;
            selectedHeroes[3] = null;
            selectedHeroes[4] = null;
            hero1UltTime = 0;
            hero2UltTime = 0;
            hero3UltTime = 0;
            hero4UltTime = 0;
            hero5UltTime = 0;
        }

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
    public void onStop() {
        super.onStop();
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences("optionsFile", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("roshOption", roshanSoundOption);
        editor.putBoolean("screenOnOption", screenAlwaysOnOption);
        // Commit the edits!
        editor.commit();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (gameStartTime != 0 && game_start_button.getText() == "Pause") {
            timerHandler.postDelayed(timerRunnable, 0);
        }
        //this use a constant 5 but should really use the MAX_SELECTED_HEROES static int

        String[] selectedAbilities;
        //gets String array of selected heroes from hero select screen
        Intent intent = getIntent();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selectedHeroes = intent.getStringArrayExtra("selectedHeroes");
            ultTimes = intent.getIntArrayExtra("selectedHeroesUltTimes");
            selectedAbilities = intent.getStringArrayExtra("abilityNames");
            if (selectedHeroes != null) {
                select_hero_button.setText("Reset Heroes");
                if (selectedHeroes[0] != null) {
                    hero1_textview.setText(selectedHeroes[0] + "\n" + selectedAbilities[0]);
                    hero1_textview_overlay.setText(selectedHeroes[0] + "\n" + selectedAbilities[0]);
                    hero1_layout.setVisibility(View.VISIBLE);
                    hereos_selected_layout.setVisibility(View.VISIBLE);
                    ultimate_timer_textview.setVisibility(View.VISIBLE);
                } else {
                    hereos_selected_layout.setVisibility(View.INVISIBLE);
                    hero1_layout.setVisibility(View.INVISIBLE);
                    ultimate_timer_textview.setVisibility(View.INVISIBLE);
                    hero1_OC_imageview.setVisibility(View.INVISIBLE);
                    hero1_aghs_imageview.setVisibility(View.INVISIBLE);
                    hero1_talent_imageview.setVisibility(View.INVISIBLE);
                    hero2_OC_imageview.setVisibility(View.INVISIBLE);
                    hero2_aghs_imageview.setVisibility(View.INVISIBLE);
                    hero2_talent_imageview.setVisibility(View.INVISIBLE);
                    hero3_OC_imageview.setVisibility(View.INVISIBLE);
                    hero3_aghs_imageview.setVisibility(View.INVISIBLE);
                    hero3_talent_imageview.setVisibility(View.INVISIBLE);
                    hero4_OC_imageview.setVisibility(View.INVISIBLE);
                    hero4_aghs_imageview.setVisibility(View.INVISIBLE);
                    hero4_talent_imageview.setVisibility(View.INVISIBLE);
                    hero5_OC_imageview.setVisibility(View.INVISIBLE);
                    hero5_aghs_imageview.setVisibility(View.INVISIBLE);
                    hero5_talent_imageview.setVisibility(View.INVISIBLE);
                }
                if (selectedHeroes[1] != null) {
                    hero2_textview.setText(selectedHeroes[1] + "\n" + selectedAbilities[1]);
                    hero2_textview_overlay.setText(selectedHeroes[1] + "\n" + selectedAbilities[1]);
                    hero2_layout.setVisibility(View.VISIBLE);
                } else {
                    hero2_layout.setVisibility(View.INVISIBLE);
                }
                if (selectedHeroes[2] != null) {
                    hero3_textview.setText(selectedHeroes[2] + "\n" + selectedAbilities[2]);
                    hero3_textview_overlay.setText(selectedHeroes[2] + "\n" + selectedAbilities[2]);
                    hero3_layout.setVisibility(View.VISIBLE);
                } else {
                    hero3_layout.setVisibility(View.INVISIBLE);
                }
                if (selectedHeroes[3] != null) {
                    hero4_textview.setText(selectedHeroes[3] + "\n" + selectedAbilities[3]);
                    hero4_textview_overlay.setText(selectedHeroes[3] + "\n" + selectedAbilities[3]);
                    hero4_layout.setVisibility(View.VISIBLE);
                } else {
                    hero4_layout.setVisibility(View.INVISIBLE);
                }
                if (selectedHeroes[4] != null) {
                    hero5_textview.setText(selectedHeroes[4] + "\n" + selectedAbilities[4]);
                    hero5_textview_overlay.setText(selectedHeroes[4] + "\n" + selectedAbilities[4]);
                    hero5_layout.setVisibility(View.VISIBLE);
                } else {
                    hero5_layout.setVisibility(View.INVISIBLE);
                }
            } else {
                select_hero_button.setText("Select Enemy Heroes");
                hereos_selected_layout.setVisibility(View.INVISIBLE);
                ultimate_timer_textview.setVisibility(View.INVISIBLE);
                hero1_OC_imageview.setVisibility(View.INVISIBLE);
                hero1_aghs_imageview.setVisibility(View.INVISIBLE);
                hero1_talent_imageview.setVisibility(View.INVISIBLE);
                hero2_OC_imageview.setVisibility(View.INVISIBLE);
                hero2_aghs_imageview.setVisibility(View.INVISIBLE);
                hero2_talent_imageview.setVisibility(View.INVISIBLE);
                hero3_OC_imageview.setVisibility(View.INVISIBLE);
                hero3_aghs_imageview.setVisibility(View.INVISIBLE);
                hero3_talent_imageview.setVisibility(View.INVISIBLE);
                hero4_OC_imageview.setVisibility(View.INVISIBLE);
                hero4_aghs_imageview.setVisibility(View.INVISIBLE);
                hero4_talent_imageview.setVisibility(View.INVISIBLE);
                hero5_OC_imageview.setVisibility(View.INVISIBLE);
                hero5_aghs_imageview.setVisibility(View.INVISIBLE);
                hero5_talent_imageview.setVisibility(View.INVISIBLE);
            }
        } else {
            hereos_selected_layout.setVisibility(View.INVISIBLE);
            ultimate_timer_textview.setVisibility(View.INVISIBLE);
            hero1_OC_imageview.setVisibility(View.INVISIBLE);
            hero1_aghs_imageview.setVisibility(View.INVISIBLE);
            hero1_talent_imageview.setVisibility(View.INVISIBLE);
            hero2_OC_imageview.setVisibility(View.INVISIBLE);
            hero2_aghs_imageview.setVisibility(View.INVISIBLE);
            hero2_talent_imageview.setVisibility(View.INVISIBLE);
            hero3_OC_imageview.setVisibility(View.INVISIBLE);
            hero3_aghs_imageview.setVisibility(View.INVISIBLE);
            hero3_talent_imageview.setVisibility(View.INVISIBLE);
            hero4_OC_imageview.setVisibility(View.INVISIBLE);
            hero4_aghs_imageview.setVisibility(View.INVISIBLE);
            hero4_talent_imageview.setVisibility(View.INVISIBLE);
            hero5_OC_imageview.setVisibility(View.INVISIBLE);
            hero5_aghs_imageview.setVisibility(View.INVISIBLE);
            hero5_talent_imageview.setVisibility(View.INVISIBLE);
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
                "Octarine Core",
                "Talent 10%",
                "Talent 12%",
                "Talent 15%",
                "Talent 20%",
                "Aghanim scepter"
        };

        checkForPreviousModifiers(heronumber);

        // Convert the color array to list
        final List<String> modifiersList = Arrays.asList(determineApproriateList(heronumber));

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
        builder.setMultiChoiceItems(determineApproriateList(heronumber), checkedModifiers, new DialogInterface.OnMultiChoiceClickListener() {
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

                //all heroes can do this
                if (checkedModifiers[0]) {
                    cooldownModifier = (cooldownModifier * 0.75);
                }

                switch (listStyle) {
                    case 1:
                        if (checkedModifiers[1]) {
                            cooldownModifier = (cooldownModifier * 0.9);
                        }
                        break;

                    case 2:
                        if (checkedModifiers[1]) {
                            cooldownModifier = (cooldownModifier * 0.88);
                        }
                        break;

                    case 3:
                        if (checkedModifiers[1]) {
                            cooldownModifier = (cooldownModifier * 0.85);
                        }
                        break;

                    case 4:
                        if (checkedModifiers[1]) {
                            cooldownModifier = (cooldownModifier * 80);
                        }
                        break;

                    case 5:
                        if (checkedModifiers[1]) {
                            cooldownModifier = (cooldownModifier * 0.9);
                        }
                        break;

                    case 6:
                        if (checkedModifiers[1]) {
                            cooldownModifier = (cooldownModifier * 0.88);
                        }
                        break;

                    case 7:
                        if (checkedModifiers[1]) {
                            cooldownModifier = (cooldownModifier * 0.85);
                        }
                        break;

                    case 8:
                        if (checkedModifiers[1]) {
                            cooldownModifier = (cooldownModifier * 0.80);
                        }
                        break;

                    case 9:
                        //Aghs changes cooldown of these heroes
                        //Still need to add Faceless, Lion, Necro, Pugna, QOP, Rubick
                        String name;
                        name = getSelectedHeroName(heronumber);
                        if (checkedModifiers[1]) {
                            if (name.equals("Axe")) {
                                switch (heronumber) {
                                    case 1:
                                        ultTimes[0] = 6 * 1000;
                                        ultTimes[1] = 6 * 1000;
                                        ultTimes[2] = 6 * 1000;
                                        break;
                                    case 2:
                                        ultTimes[3] = 6 * 1000;
                                        ultTimes[4] = 6 * 1000;
                                        ultTimes[5] = 6 * 1000;
                                        break;
                                    case 3:
                                        ultTimes[6] = 6 * 1000;
                                        ultTimes[7] = 6 * 1000;
                                        ultTimes[8] = 6 * 1000;
                                        break;
                                    case 4:
                                        ultTimes[9] = 6 * 1000;
                                        ultTimes[10] = 6 * 1000;
                                        ultTimes[11] = 6 * 1000;
                                        break;
                                    case 5:
                                        ultTimes[12] = 6 * 1000;
                                        ultTimes[13] = 6 * 1000;
                                        ultTimes[14] = 6 * 1000;
                                        break;
                                }
                            } else if (name.equals("Clockwerk")) {
                                switch (heronumber) {
                                    case 1:
                                        ultTimes[0] = 12 * 1000;
                                        ultTimes[1] = 12 * 1000;
                                        ultTimes[2] = 12 * 1000;
                                        break;
                                    case 2:
                                        ultTimes[3] = 12 * 1000;
                                        ultTimes[4] = 12 * 1000;
                                        ultTimes[5] = 12 * 1000;
                                        break;
                                    case 3:
                                        ultTimes[6] = 12 * 1000;
                                        ultTimes[7] = 12 * 1000;
                                        ultTimes[8] = 12 * 1000;
                                        break;
                                    case 4:
                                        ultTimes[9] = 12 * 1000;
                                        ultTimes[10] = 12 * 1000;
                                        ultTimes[11] = 12 * 1000;
                                        break;
                                    case 5:
                                        ultTimes[12] = 12 * 1000;
                                        ultTimes[13] = 12 * 1000;
                                        ultTimes[14] = 12 * 1000;
                                        break;
                                }
                            }
                        } else {
                            if (name.equals("Axe")) {
                                switch (heronumber) {
                                    case 1:
                                        ultTimes[0] = 75 * 1000;
                                        ultTimes[1] = 65 * 1000;
                                        ultTimes[2] = 55 * 1000;
                                        break;
                                    case 2:
                                        ultTimes[3] = 75 * 1000;
                                        ultTimes[4] = 65 * 1000;
                                        ultTimes[5] = 55 * 1000;
                                        break;
                                    case 3:
                                        ultTimes[6] = 75 * 1000;
                                        ultTimes[7] = 65 * 1000;
                                        ultTimes[8] = 55 * 1000;
                                        break;
                                    case 4:
                                        ultTimes[9] = 75 * 1000;
                                        ultTimes[10] = 65 * 1000;
                                        ultTimes[11] = 55 * 1000;
                                        break;
                                    case 5:
                                        ultTimes[12] = 75 * 1000;
                                        ultTimes[13] = 65 * 1000;
                                        ultTimes[14] = 5 * 1000;
                                        break;
                                }
                            } else if (name.equals("Clockwerk")) {
                                switch (heronumber) {
                                    case 1:
                                        ultTimes[0] = 70 * 1000;
                                        ultTimes[1] = 55 * 1000;
                                        ultTimes[2] = 40 * 1000;
                                        break;
                                    case 2:
                                        ultTimes[3] = 70 * 1000;
                                        ultTimes[4] = 55 * 1000;
                                        ultTimes[5] = 1402 * 1000;
                                        break;
                                    case 3:
                                        ultTimes[6] = 70 * 1000;
                                        ultTimes[7] = 55 * 1000;
                                        ultTimes[8] = 40 * 1000;
                                        break;
                                    case 4:
                                        ultTimes[9] = 70 * 1000;
                                        ultTimes[10] = 55 * 1000;
                                        ultTimes[11] = 40 * 1000;
                                        break;
                                    case 5:
                                        ultTimes[12] = 70 * 1000;
                                        ultTimes[13] = 55 * 1000;
                                        ultTimes[14] = 40 * 1000;
                                        break;
                                }
                            }
                        }
                        break;

                    case 10:
                        break;

                    case 11:
                        if (checkedModifiers[1]) {
                            cooldownModifier = (cooldownModifier * 0.75);
                        }
                        break;
                    default:
                        break;
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
                displayCooldownIcons(checkedModifiers, heronumber);
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

    public void displayCooldownIcons(boolean[] modifier, int heroNumber) {
        if (modifier[0]) {
            switch (heroNumber) {
                case 1:
                    hero1_OC_imageview.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    hero2_OC_imageview.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    hero3_OC_imageview.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    hero4_OC_imageview.setVisibility(View.VISIBLE);
                    break;
                case 5:
                    hero5_OC_imageview.setVisibility(View.VISIBLE);
                    break;
            }
        } else {
            switch (heroNumber) {
                case 1:
                    hero1_OC_imageview.setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    hero2_OC_imageview.setVisibility(View.INVISIBLE);
                    break;
                case 3:
                    hero3_OC_imageview.setVisibility(View.INVISIBLE);
                    break;
                case 4:
                    hero4_OC_imageview.setVisibility(View.INVISIBLE);
                    break;
                case 5:
                    hero5_OC_imageview.setVisibility(View.INVISIBLE);
                    break;
            }
        }
        if (listStyle != 9 && listStyle != 10) {
            if (modifier[1]) {
                switch (heroNumber) {
                    case 1:
                        hero1_talent_imageview.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        hero2_talent_imageview.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        hero3_talent_imageview.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        hero4_talent_imageview.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        hero5_talent_imageview.setVisibility(View.VISIBLE);
                        break;
                }
            } else {
                switch (heroNumber) {
                    case 1:
                        hero1_talent_imageview.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        hero2_talent_imageview.setVisibility(View.INVISIBLE);
                        break;
                    case 3:
                        hero3_talent_imageview.setVisibility(View.INVISIBLE);
                        break;
                    case 4:
                        hero4_talent_imageview.setVisibility(View.INVISIBLE);
                        break;
                    case 5:
                        hero5_talent_imageview.setVisibility(View.INVISIBLE);
                        break;
                }
            }

            if (listStyle == 1 || listStyle == 2 || listStyle == 3 || listStyle == 4) {
                if (modifier[2]) {
                    switch (heroNumber) {
                        case 1:
                            hero1_aghs_imageview.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            hero2_aghs_imageview.setVisibility(View.VISIBLE);
                            break;
                        case 3:
                            hero3_aghs_imageview.setVisibility(View.VISIBLE);
                            break;
                        case 4:
                            hero4_aghs_imageview.setVisibility(View.VISIBLE);
                            break;
                        case 5:
                            hero5_aghs_imageview.setVisibility(View.VISIBLE);
                            break;
                    }
                } else {
                    switch (heroNumber) {
                        case 1:
                            hero1_aghs_imageview.setVisibility(View.INVISIBLE);
                            break;
                        case 2:
                            hero2_aghs_imageview.setVisibility(View.INVISIBLE);
                            break;
                        case 3:
                            hero3_aghs_imageview.setVisibility(View.INVISIBLE);
                            break;
                        case 4:
                            hero4_aghs_imageview.setVisibility(View.INVISIBLE);
                            break;
                        case 5:
                            hero5_aghs_imageview.setVisibility(View.INVISIBLE);
                            break;
                    }
                }
            }

        } else if (listStyle == 9) {
            if (modifier[1]) {
                switch (heroNumber) {
                    case 1:
                        hero1_aghs_imageview.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        hero2_aghs_imageview.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        hero3_aghs_imageview.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        hero4_aghs_imageview.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        hero5_aghs_imageview.setVisibility(View.VISIBLE);
                        break;
                }
            } else {
                switch (heroNumber) {
                    case 1:
                        hero1_aghs_imageview.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        hero2_aghs_imageview.setVisibility(View.INVISIBLE);
                        break;
                    case 3:
                        hero3_aghs_imageview.setVisibility(View.INVISIBLE);
                        break;
                    case 4:
                        hero4_aghs_imageview.setVisibility(View.INVISIBLE);
                        break;
                    case 5:
                        hero5_aghs_imageview.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        }
    }

    public String[] determineApproriateList(int selectedHeroNumber) {

        listStyle = styleFromName(selectedHeroes[selectedHeroNumber - 1]);
        String[] list = {"test"};

        switch (listStyle) {
            case 1:
                String[] list1 = new String[3];
                list1[0] = "Octarine Core";
                list1[1] = "10% Reduction Talent";
                list1[2] = "Aghanims Scepter";
                return list1;

            case 2:
                String[] list2 = new String[3];
                list2[0] = "Octarine Core";
                list2[1] = "12% Reduction Talent";
                list2[2] = "Aghanims Scepter";
                return list2;

            case 3:
                String[] list3 = new String[3];
                list3[0] = "Octarine Core";
                list3[1] = "15% Reduction Talent";
                list3[2] = "Aghanims Scepter";
                return list3;

            case 4:
                String[] list4 = new String[3];
                list4[0] = "Octarine Core";
                list4[1] = "20% Reduction Talent";
                list4[2] = "Aghanims Scepter";
                return list4;

            case 5:
                String[] list5 = new String[2];
                list5[0] = "Octarine Core";
                list5[1] = "10% Reduction Talent";
                return list5;

            case 6:
                String[] list6 = new String[2];
                list6[0] = "Octarine Core";
                list6[1] = "12% Reduction Talent";
                return list6;

            case 7:
                String[] list7 = new String[2];
                list7[0] = "Octarine Core";
                list7[1] = "15% Reduction Talent";
                return list7;

            case 8:
                String[] list8 = new String[2];
                list8[0] = "Octarine Core";
                list8[1] = "20% Reduction Talent";
                return list8;

            case 9:
                String[] list9 = new String[2];
                list9[0] = "Octarine Core";
                list9[1] = "Aghanims Scepter";
                return list9;

            case 10:
                String[] list10 = new String[1];
                list10[0] = "Octarine Core";
                return list10;

            case 11:
                String[] list11 = new String[2];
                list11[0] = "Octarine Core";
                list11[1] = "25% Reduction Talent";
                return list11;

            default:
                return list;
        }
    }

    public int styleFromName(String name) {
        int style = 0;

        //10% Talent
        //aghs
        if (name.equals("1")) {
            style = 1;

            //12% Talent
            //aghs
        } else if (name.equals("Queen of Pain")) {
            style = 2;

            //15% Talent
            //aghs
        } else if (name.equals("3")) {
            style = 3;

            //20% talent
            //aghs
        } else if (name.equals("Rubick")) {
            style = 4;

            //10% talent
        } else if (name.equals("Arc Warden") ||
                name.equals("Death Prophet")) {
            style = 5;

            //12% Talent
        } else if (name.equals("Beastmaster") ||
                name.equals("Dark Seer") ||
                name.equals("Morphling")) {
            style = 6;

            //15% Talent
        } else if (name.equals("Abaddon") ||
                name.equals("Batrider") ||
                name.equals("Disruptor") ||
                name.equals("Enigma") ||
                name.equals("Lycan") ||
                name.equals("Phantom Lancer") ||
                name.equals("Treant Protector"))
        {
            style = 7;

            //20% Talent
        } else if (name.equals("Broodmother") ||
                name.equals("Chaos Knight") ||
                name.equals("Gyrocopter") ||
                name.equals("Tidehunter")) {
            style = 8;

            //Aghs
        } else if (name.equals("Axe") ||
                name.equals("Clockwerk") ||
                name.equals("Faceless Void") ||
                name.equals("Lion") ||
                name.equals("Necrophos") ||
                name.equals("Pugna") ||
                name.equals("Shadow Demon") ||
                name.equals("Slark") ||
                name.equals("Spirit Breaker") ||
                name.equals("Ursa") ||
                name.equals("Vengeful Spirit") ||
                name.equals("Venomancer") ||
                name.equals("Viper") ||
                name.equals("Weaver") ||
                name.equals("Windranger")) {
            style = 9;

            //just OC
        } else if (name.equals("Alchemist") ||
                name.equals("Ancient Apparition") ||
                name.equals("Anti-Mage") ||
                name.equals("Bane") ||
                name.equals("Bloodseeker") ||
                name.equals("Bounty Hunter") ||
                name.equals("Brewmaster") ||
                name.equals("Bristleback") ||
                name.equals("Centaur Warrunner") ||
                name.equals("Chen") ||
                name.equals("Clinkz") ||
                name.equals("Crystal Maiden") ||
                name.equals("Dazzle") ||
                name.equals("Doom") ||
                name.equals("Dragon Knight") ||
                name.equals("Drow Ranger") ||
                name.equals("Earth Spirit") ||
                name.equals("Earthshaker") ||
                name.equals("Elder Titan") ||
                name.equals("Ember Spirit") ||
                name.equals("Enchantress") ||
                name.equals("Huskar") ||
                name.equals("Invoker") ||
                name.equals("Io") ||
                name.equals("Jakiro") ||
                name.equals("Juggernaut") ||
                name.equals("Keeper of the Light") ||
                name.equals("Kunkka") ||
                name.equals("Leshrac") ||
                name.equals("Lich") ||
                name.equals("Lifestealer") ||
                name.equals("Lina") ||
                name.equals("Lone Druid") ||
                name.equals("Luna") ||
                name.equals("Magnus") ||
                name.equals("Medusa") ||
                name.equals("Meepo") ||
                name.equals("Mirana") ||
                name.equals("Monkey King") ||
                name.equals("Naga Siren") ||
                name.equals("Nature's Prophet") ||
                name.equals("Night Stalker") ||
                name.equals("Nyx") ||
                name.equals("Ogre Magi") ||
                name.equals("Omni Knight") ||
                name.equals("Oracle") ||
                name.equals("Outworld Devourer") ||
                name.equals("Phantom Assassin") ||
                name.equals("Phoenix") ||
                name.equals("Puck") ||
                name.equals("Pudge") ||
                name.equals("Razor") ||
                name.equals("Riki") ||
                name.equals("Sand King") ||
                name.equals("Shadow Fiend") ||
                name.equals("Shadow Shaman") ||
                name.equals("Silencer")  ||
                name.equals("Skywrath Mahe") ||
                name.equals("Slardar") ||
                name.equals("Spectre") ||
                name.equals("Storm Spirit") ||
                name.equals("Sven") ||
                name.equals("Templar Assassin") ||
                name.equals("Terrorblade") ||
                name.equals("Timbersaw") ||
                name.equals("Tinker") ||
                name.equals("Tiny") ||
                name.equals("Troll Warlord") ||
                name.equals("Tusk") ||
                name.equals("Underlord") ||
                name.equals("Undying") ||
                name.equals("Visage") ||
                name.equals("Warlock") ||
                name.equals("Winter Wyvern") ||
                name.equals("Witch Doctor") ||
                name.equals("Wraith King") ||
                name.equals("Zues"))
               {
            style = 10;
        } else if (name.equals("Legion Commander") ||
                name.equals("Sniper") ||
                name.equals("Techies")) {
            style = 11;
        }
        return style;
    }

    public void checkForPreviousModifiers(int heronumber) {
        switch (heronumber) {
            case 1:
                if (hero1_OC_imageview.getVisibility() == View.VISIBLE) {
                    checkedModifiers[0] = true;
                } else {
                    checkedModifiers[0] = false;
                }
                if (hero1_talent_imageview.getVisibility() == View.VISIBLE) {
                    checkedModifiers[1] = true;
                } else {
                    checkedModifiers[1] = false;
                }
                if (hero1_aghs_imageview.getVisibility() == View.VISIBLE) {
                    if (listStyle == 9) {
                        checkedModifiers[1] = true;
                    } else {
                        checkedModifiers[2] = true;
                    }
                } else {
                    if (listStyle == 9) {
                        checkedModifiers[1] = false;
                    } else {
                        checkedModifiers[2] = false;
                    }
                }
                break;
            case 2:
                if (hero2_OC_imageview.getVisibility() == View.VISIBLE) {
                    checkedModifiers[0] = true;
                } else {
                    checkedModifiers[0] = false;
                }
                if (hero2_talent_imageview.getVisibility() == View.VISIBLE) {
                    checkedModifiers[1] = true;
                } else {
                    checkedModifiers[1] = false;
                }
                if (hero2_aghs_imageview.getVisibility() == View.VISIBLE) {
                    if (listStyle == 9) {
                        checkedModifiers[1] = true;
                    } else {
                        checkedModifiers[2] = true;
                    }
                } else {
                    if (listStyle == 9) {
                        checkedModifiers[1] = false;
                    } else {
                        checkedModifiers[2] = false;
                    }
                }
                break;

            case 3:
                if (hero3_OC_imageview.getVisibility() == View.VISIBLE) {
                    checkedModifiers[0] = true;
                } else {
                    checkedModifiers[0] = false;
                }
                if (hero3_talent_imageview.getVisibility() == View.VISIBLE) {
                    checkedModifiers[1] = true;
                } else {
                    checkedModifiers[1] = false;
                }
                if (hero3_aghs_imageview.getVisibility() == View.VISIBLE) {
                    if (listStyle == 9) {
                        checkedModifiers[1] = true;
                    } else {
                        checkedModifiers[2] = true;
                    }
                } else {
                    if (listStyle == 9) {
                        checkedModifiers[1] = false;
                    } else {
                        checkedModifiers[2] = false;
                    }
                }
                break;
            case 4:
                if (hero4_OC_imageview.getVisibility() == View.VISIBLE) {
                    checkedModifiers[0] = true;
                } else {
                    checkedModifiers[0] = false;
                }
                if (hero4_talent_imageview.getVisibility() == View.VISIBLE) {
                    checkedModifiers[1] = true;
                } else {
                    checkedModifiers[1] = false;
                }
                if (hero4_aghs_imageview.getVisibility() == View.VISIBLE) {
                    if (listStyle == 9) {
                        checkedModifiers[1] = true;
                    } else {
                        checkedModifiers[2] = true;
                    }
                } else {
                    if (listStyle == 9) {
                        checkedModifiers[1] = false;
                    } else {
                        checkedModifiers[2] = false;
                    }
                }
                break;
            case 5:
                if (hero5_OC_imageview.getVisibility() == View.VISIBLE) {
                    checkedModifiers[0] = true;
                } else {
                    checkedModifiers[0] = false;
                }
                if (hero5_talent_imageview.getVisibility() == View.VISIBLE) {
                    checkedModifiers[1] = true;
                } else {
                    checkedModifiers[1] = false;
                }
                if (hero5_aghs_imageview.getVisibility() == View.VISIBLE) {
                    if (listStyle == 9) {
                        checkedModifiers[1] = true;
                    } else {
                        checkedModifiers[2] = true;
                    }
                } else {
                    if (listStyle == 9) {
                        checkedModifiers[1] = false;
                    } else {
                        checkedModifiers[2] = false;
                    }
                }
                break;
        }
    }

    public String getSelectedHeroName(int heronumber) {
        String name;

        switch (heronumber) {
            case 1:
                name = (String) hero1_textview.getText();
                break;
            case 2:
                name = (String) hero2_textview.getText();
                break;
            case 3:
                name = (String) hero3_textview.getText();
                break;
            case 4:
                name = (String) hero4_textview.getText();
                break;
            case 5:
                name = (String) hero5_textview.getText();
                break;
            default:
                name = null;
                break;
        }
        return selectedHeroes[heronumber];
    }
}