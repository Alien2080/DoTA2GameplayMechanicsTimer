package com.android_app.alien.dota2gameplaymechanicstimer;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import static android.R.attr.button;
import static com.android_app.alien.dota2gameplaymechanicstimer.R.id.aegis_reclaim_button;

public class HeroPickerActivity extends AppCompatActivity {

    static int MAX_SELECTED_HEROES = 5;

    //cooldown times of heroes in milliseconds
    static int ABBADON_ULT_CD_L1 = (60 * 1000);
    static int ABBADON_ULT_CD_L2 = (50 * 1000);
    static int ABBADON_ULT_CD_L3 = (40 * 1000);
    static int ALCHEMIST_ULT_CD_L1 = (45 * 1000);
    static int ALCHEMIST_ULT_CD_L2 = ALCHEMIST_ULT_CD_L1;
    static int ALCHEMIST_ULT_CD_L3 = ALCHEMIST_ULT_CD_L1;
    static int ANCIENT_APPARITION_ULT_CD_L1 = (40 * 1000);
    static int ANCIENT_APPARITION_ULT_CD_L2 = ANCIENT_APPARITION_ULT_CD_L1;
    static int ANCIENT_APPARITION_ULT_CD_L3 = ANCIENT_APPARITION_ULT_CD_L1;
    static int ANTI_MAGE_ULT_CD_L1 = (70 * 1000);
    static int ANTI_MAGE_ULT_CD_L2 = (70 * 1000);
    static int ANTI_MAGE_ULT_CD_L3 = (70 * 1000);
    static int ARC_WARDEN_ULT_CD_L1 = (60 * 1000);
    static int ARC_WARDEN_ULT_CD_L2 = (50 * 1000);
    static int ARC_WARDEN_ULT_CD_L3 = (40 * 1000);
    static int AXE_ULT_CD_L1 = (75 * 1000);
    static int AXE_ULT_CD_L2 = (65 * 1000);
    static int AXE_ULT_CD_L3 = (55 * 1000);
    static int BANE_ULT_CD_L1 = (100 * 1000);
    static int BANE_ULT_CD_L2 = BANE_ULT_CD_L1;
    static int BANE_ULT_CD_L3 = BANE_ULT_CD_L1;
    static int BAT_RIDER_ULT_CD_L1 = (90 * 1000);
    static int BAT_RIDER_ULT_CD_L2 = (75 * 1000);
    static int BAT_RIDER_ULT_CD_L3 = (60 * 1000);
    static int BEASTMASTER_ULT_CD_L1 = (80 * 1000);
    static int BEASTMASTER_ULT_CD_L2 = (75 * 1000);
    static int BEASTMASTER_ULT_CD_L3 = (70 * 1000);
    static int BLOODSEEKER_ULT_CD_L1 = (60 * 1000);
    static int BLOODSEEKER_ULT_CD_L2 = BLOODSEEKER_ULT_CD_L1;
    static int BLOODSEEKER_ULT_CD_L3 = BLOODSEEKER_ULT_CD_L1;
    static int BOUNTY_HUNTER_ULT_CD_L1 = (4 * 1000);
    static int BOUNTY_HUNTER_ULT_CD_L2 = BOUNTY_HUNTER_ULT_CD_L1;
    static int BOUNTY_HUNTER_ULT_CD_L3 = BOUNTY_HUNTER_ULT_CD_L1;
    static int BREWMASTER_ULT_CD_L1 = (140 * 1000);
    static int BREWMASTER_ULT_CD_L2 = (120 * 1000);
    static int BREWMASTER_ULT_CD_L3 = (100 * 1000);
    static int BRISTLEBACK_ULT_CD_L1 = (0 * 1000);
    static int BRISTLEBACK_ULT_CD_L2 = (0 * 1000);
    static int BRISTLEBACK_ULT_CD_L3 = (0 * 1000);
    static int BROODMOTHER_ULT_CD_L1 = (45 * 1000);
    static int BROODMOTHER_ULT_CD_L2 = BROODMOTHER_ULT_CD_L1;
    static int BROODMOTHER_ULT_CD_L3 = BROODMOTHER_ULT_CD_L1;
    static int CENTAUR_ULT_CD_L1 = (90 * 1000);
    static int CENTAUR_ULT_CD_L2 = (75 * 1000);
    static int CENTAUR_ULT_CD_L3 = (60 * 1000);
    static int CHAOS_ULT_CD_L1 = (130 * 1000);
    static int CHAOS_ULT_CD_L2 = CHAOS_ULT_CD_L1;
    static int CHAOS_ULT_CD_L3 = CHAOS_ULT_CD_L1;
    static int CHEN_ULT_CD_L1 = (160 * 1000);
    static int CHEN_ULT_CD_L2 = (140 * 1000);
    static int CHEN_ULT_CD_L3 = (120 * 1000);
    static int CLINKZ_ULT_CD_L1 = (45 * 1000);
    static int CLINKZ_ULT_CD_L2 = (30 * 1000);
    static int CLINKZ_ULT_CD_L3 = (15 * 1000);
    static int CLOCKWERK_ULT_CD_L1 = (70 * 1000);
    static int CLOCKWERK_ULT_CD_L2 = (55 * 1000);
    static int CLOCKWERK_ULT_CD_L3 = (40 * 1000);
    static int CM_ULT_CD_L1 = (90 * 1000);
    static int CM_ULT_CD_L2 = CM_ULT_CD_L1;
    static int CM_ULT_CD_L3 = CM_ULT_CD_L1;

    //default cooldown time of each heroes ultimate ability, the position in array relates to the heros name in heroNames array x3
    int[] ultTimes = new int[]{
            ABBADON_ULT_CD_L1,
            ABBADON_ULT_CD_L2,
            ABBADON_ULT_CD_L3,
            ALCHEMIST_ULT_CD_L1,
            ALCHEMIST_ULT_CD_L2 = ALCHEMIST_ULT_CD_L1,
            ALCHEMIST_ULT_CD_L3 = ALCHEMIST_ULT_CD_L1,
            ANCIENT_APPARITION_ULT_CD_L1,
            ANCIENT_APPARITION_ULT_CD_L2,
            ANCIENT_APPARITION_ULT_CD_L3,
            ANTI_MAGE_ULT_CD_L1,
            ANTI_MAGE_ULT_CD_L2,
            ANTI_MAGE_ULT_CD_L3,
            ARC_WARDEN_ULT_CD_L1,
            ARC_WARDEN_ULT_CD_L2,
            ARC_WARDEN_ULT_CD_L3,
            AXE_ULT_CD_L1,
            AXE_ULT_CD_L2,
            AXE_ULT_CD_L3,
            BANE_ULT_CD_L1,
            BANE_ULT_CD_L2,
            BANE_ULT_CD_L3,
            BAT_RIDER_ULT_CD_L1,
            BAT_RIDER_ULT_CD_L2,
            BAT_RIDER_ULT_CD_L3,
            BEASTMASTER_ULT_CD_L1,
            BEASTMASTER_ULT_CD_L2,
            BEASTMASTER_ULT_CD_L3,
            BLOODSEEKER_ULT_CD_L1,
            BLOODSEEKER_ULT_CD_L2,
            BLOODSEEKER_ULT_CD_L3,
            BOUNTY_HUNTER_ULT_CD_L1,
            BOUNTY_HUNTER_ULT_CD_L2,
            BOUNTY_HUNTER_ULT_CD_L3,
            BREWMASTER_ULT_CD_L1,
            BREWMASTER_ULT_CD_L2,
            BREWMASTER_ULT_CD_L3,
            BRISTLEBACK_ULT_CD_L1,
            BRISTLEBACK_ULT_CD_L2,
            BRISTLEBACK_ULT_CD_L3,
            BROODMOTHER_ULT_CD_L1,
            BROODMOTHER_ULT_CD_L2,
            BROODMOTHER_ULT_CD_L3,
            CENTAUR_ULT_CD_L1,
            CENTAUR_ULT_CD_L2,
            CENTAUR_ULT_CD_L3,
            CHAOS_ULT_CD_L1,
            CHAOS_ULT_CD_L2,
            CHAOS_ULT_CD_L3,
            CHEN_ULT_CD_L1,
            CHEN_ULT_CD_L2,
            CHEN_ULT_CD_L3,
            CLINKZ_ULT_CD_L1,
            CLINKZ_ULT_CD_L2,
            CLINKZ_ULT_CD_L3,
            CLOCKWERK_ULT_CD_L1,
            CLOCKWERK_ULT_CD_L2,
            CLOCKWERK_ULT_CD_L3,
            CM_ULT_CD_L1,
            CM_ULT_CD_L2,
            CM_ULT_CD_L3
    };

    ListView hero_list_view;
    String[] heroSelected = new String[MAX_SELECTED_HEROES];
    //each hero has 3 levels of ultimate and thus 3 ultimate cooldown times
    int[] heroSelectedUltTimes = new int[MAX_SELECTED_HEROES * 3];
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_picker);

        hero_list_view = (ListView) findViewById(R.id.hero_list_view);

        //position in array links the hero to their ultimate cooldown time by having the time in the same position as the hero name x3
        String[] heroNames = new String[]{
                "Abaddon",
                "Alchemist",
                "Ancient Apparition",
                "Anti-Mage",
                "Arc Warden",
                "Axe",
                "Bane",
                "Batrider",
                "Beastmaster",
                "Bloodseeker",
                "Bounty Hunter",
                "Brewmaster",
                "Bristleback",
                "Broodmother",
                "Centaur Warrunner",
                "Chaos Knight",
                "Chen",
                "Clinkz",
                "Clockwerk",
                "Crystal Maiden",
                "Dark Seer",
                "Dazzle",
                "Death Prophet",
                "Disruptor",
                "Doom",
                "Dragon Knight",
                "Drow Ranger",
                "Earth Spirit",
                "Earthshaker",
                "Elder Titan",
                "Ember Spirit",
                "Enchantress",
                "Enigma",
                "Faceless Void",
                "Gyrocopter",
                "Huskar",
                "Invoker",
                "Io",
                "Jakiro",
                "Juggernaut",
                "Keeper of the Light",
                "Kunkka",
                "Legion Commander",
                "Leshrac",
                "Lich",
                "Lifestealer",
                "Lina",
                "Lion",
                "Lone Druid",
                "Luna",
                "Lycan",
                "Magnus",
                "Medusa",
                "Meepo",
                "Mirana",
                "Monkey King",
                "Morphling",
                "Naga Siren",
                "Nature's Prophet",
                "Necrophos",
                "Night Stalker",
                "Nyx Assassin",
                "Ogre Magi",
                "Omniknight",
                "Oracle",
                "Outworld Devourer",
                "Phantom Assassin",
                "Phantom Lancer",
                "Phoenix",
                "Puck",
                "Pudge",
                "Pugna",
                "Queen of Pain",
                "Razor",
                "Riki",
                "Rubick",
                "Sand King",
                "Shadow Demon",
                "Shadow Fiend",
                "Shadow Shaman",
                "Silencer",
                "Skywrath Mage",
                "Slardar",
                "Slark",
                "Sniper",
                "Spectre",
                "Spirit Breaker",
                "Storm Spirit",
                "Sven",
                "Techies",
                "Templar Assassin",
                "Terrorblade",
                "Tidehunter",
                "Timbersaw",
                "Tinker",
                "Tiny",
                "Treant Protector",
                "Troll Warlord",
                "Tusk",
                "Underlord",
                "Undying",
                "Ursa",
                "Vengeful Spirit",
                "Venomancer",
                "Viper",
                "Visage",
                "Warlock",
                "Weaver",
                "Windranger",
                "Winter Wyvern",
                "Witch Doctor",
                "Wraith King",
                "Zeus"
        };


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, heroNames);

        // Assign adapter to ListView
        hero_list_view.setAdapter(adapter);
        // ListView Item Click Listener
        hero_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String itemValue = (String) hero_list_view.getItemAtPosition(position);

                // Add hero name to selectedHero array and ult time to heroSelectedUltTimes array to be passed with activity switch
                heroSelected[i] = itemValue;
                heroSelectedUltTimes[i * 3] = ultTimes[position * 3];
                heroSelectedUltTimes[(i * 3) + 1] = ultTimes[(position * 3) + 1];
                heroSelectedUltTimes[(i * 3) + 2] = ultTimes[(position * 3) + 2];
                i++;
                if (i >= MAX_SELECTED_HEROES) {
                    i = 0;
                }

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();
            }

        });
    }

    //called when backToMain button is clicked
    public void returnToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("selectedHeroes", heroSelected);
            intent.putExtra("selectedHeroesUltTimes", heroSelectedUltTimes);

        startActivity(intent);
    }
}