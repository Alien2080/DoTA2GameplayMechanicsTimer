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
import android.widget.TextView;
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

    static int DARK_SEER_ULT_CD_L1 = (100 * 1000);
    static int DARK_SEER_ULT_CD_L2 = (100 * 1000);
    static int DARK_SEER_ULT_CD_L3 = (100 * 1000);

    static int DAZZLE_ULT_CD_L1 = (40 * 1000);
    static int DAZZLE_ULT_CD_L2 = (40 * 1000);
    static int DAZZLE_ULT_CD_L3 = (40 * 1000);

    static int DEATH_PROPHET_CD_L1 = (145 * 1000);
    static int DEATH_PROPHET_CD_L2 = (145 * 1000);
    static int DEATH_PROPHET_CD_L3 = (145 * 1000);

    static int DISRUPTOR_CD_L1 = (90 * 1000);
    static int DISRUPTOR_CD_L2 = (80 * 1000);
    static int DISRUPTOR_CD_L3 = (70 * 1000);

    static int DOOM_CD_L1 = (145 * 1000);
    static int DOOM_CD_L2 = (145 * 1000);
    static int DOOM_CD_L3 = (145 * 1000);

    static int DRAGON_KNIGHT_CD_L1 = (115 * 1000);
    static int DRAGON_KNIGHT_CD_L2 = (115 * 1000);
    static int DRAGON_KNIGHT_CD_L3 = (115 * 1000);

    static int DROW_PRESICION_AURA_L1 = (100 * 1000);
    static int DROW_PRESICION_AURA_L2 = (100 * 1000);
    static int DROW_PRESICION_AURA_L3 = (100 * 1000);

    static int EARTH_SPIRIT_CD_L1 = (100 * 1000);
    static int EARTH_SPIRIT_CD_L2 = (90 * 1000);
    static int EARTH_SPIRIT_CD_L3 = (80 * 1000);

    static int EARTHSHAKER_CD_L1 = (150 * 1000);
    static int EARTHSHAKER_CD_L2 = (130 * 1000);
    static int EARTHSHAKER_CD_L3 = (110 * 1000);

    static int ELDER_TITAN_CD_L1 = (100 * 1000);
    static int ELDER_TITAN_CD_L2 = (100 * 1000);
    static int ELDER_TITAN_CD_L3 = (100 * 1000);

    static int EMBER_SPIRIT_FLAME_GUARD_CD_L1 = (30 * 1000);
    static int EMBER_SPIRIT_FLAME_GUARD_CD_L2 = (30 * 1000);
    static int EMBER_SPIRIT_FLAME_GUARD_CD_L3 = (30 * 1000);

    static int ENCHANTRESS_CD_L1 = (0 * 1000);
    static int ENCHANTRESS_CD_L2 = (0 * 1000);
    static int ENCHANTRESS_CD_L3 = (0 * 1000);

    static int ENIGMA_CD_L1 = (200 * 1000);
    static int ENIGMA_CD_L2 = (180 * 1000);
    static int ENIGMA_CD_L3 = (160 * 1000);

    static int FACELESS_CD_L1 = (140 * 1000);
    static int FACELESS_CD_L2 = (125 * 1000);
    static int FACELESS_CD_L3 = (110 * 1000);

    static int GYROCOPTER_CD_L1 = (55 * 1000);
    static int GYROCOPTER_CD_L2 = (50 * 1000);
    static int GYROCOPTER_CD_L3 = (45 * 1000);

    static int HUSKAR_CD_L1 = (12 * 1000);
    static int HUSKAR_CD_L2 = (12 * 1000);
    static int HUSKAR_CD_L3 = (12 * 1000);

    static int INVOKER_DEAFENING_BLAST_CD_L1 = (6 * 1000);
    static int INVOKER_DEAFENING_BLAST_CD_L2 = (6 * 1000);
    static int INVOKER_DEAFENING_BLAST_CD_L3 = (6 * 1000);

    static int IO_CD_L1 = (90 * 1000);
    static int IO_CD_L2 = (75 * 1000);
    static int IO_CD_L3 = (60 * 1000);

    static int JAKIRO_CD_L1 = (60 * 1000);
    static int JAKIRO_CD_L2 = (60 * 1000);
    static int JAKIRO_CD_L3 = (60 * 1000);

    static int JUGGERNAUT_CD_L1 = (130 * 1000);
    static int JUGGERNAUT_CD_L2 = (120 * 1000);
    static int JUGGERNAUT_CD_L3 = (110 * 1000);

    static int KOTL_CD_L1 = (80 * 1000);
    static int KOTL_CD_L2 = (70 * 1000);
    static int KOTL_CD_L3 = (60 * 1000);

    static int KUNKKA_CD_L1 = (60 * 1000);
    static int KUNKKA_CD_L2 = (50 * 1000);
    static int KUNKKA_CD_L3 = (40 * 1000);

    static int LC_CD_L1 = (50 * 1000);
    static int LC_CD_L2 = (50 * 1000);
    static int LC_CD_L3 = (50 * 1000);

    static int LESHRAC_DIABOLIC_EDICT_CD_L1 = (22 * 1000);
    static int LESHRAC_DIABOLIC_EDICT_CD_L2 = (22 * 1000);
    static int LESHRAC_DIABOLIC_EDICT_CD_L3 = (22 * 1000);

    static int LICH_CD_L1 = (120 * 1000);
    static int LICH_CD_L2 = (90 * 1000);
    static int LICH_CD_L3 = (60 * 1000);

    static int LIFESTEALER_CD_L1 = (100 * 1000);
    static int LIFESTEALER_CD_L2 = (75 * 1000);
    static int LIFESTEALER_CD_L3 = (50 * 1000);

    static int LINA_CD_L1 = (70 * 1000);
    static int LINA_CD_L2 = (60 * 1000);
    static int LINA_CD_L3 = (50 * 1000);

    static int LION_CD_L1 = (160 * 1000);
    static int LION_CD_L2 = (100 * 1000);
    static int LION_CD_L3 = (40 * 1000);

    static int LONE_DRUID_SPIRIT_BEAR_CD_L1 = (120 * 1000);
    static int LONE_DRUID_SPIRIT_BEAR_CD_L2 = (120 * 1000);
    static int LONE_DRUID_SPIRIT_BEAR_CD_L3 = (120 * 1000);

    static int LUNA_CD_L1 = (140 * 1000);
    static int LUNA_CD_L2 = (140 * 1000);
    static int LUNA_CD_L3 = (140 * 1000);

    static int LYCAN_CD_L1 = (120 * 1000);
    static int LYCAN_CD_L2 = (90 * 1000);
    static int LYCAN_CD_L3 = (60 * 1000);

    static int MAGNUS_CD_L1 = (120 * 1000);
    static int MAGNUS_CD_L2 = (110 * 1000);
    static int MAGNUS_CD_L3 = (100 * 1000);

    static int MEDUSA_CD_L1 = (90 * 1000);
    static int MEDUSA_CD_L2 = (90 * 1000);
    static int MEDUSA_CD_L3 = (90 * 1000);

    static int MEEPO_CD_L1 = (0 * 1000);
    static int MEEPO_CD_L2 = (0 * 1000);
    static int MEEPO_CD_L3 = (0 * 1000);

    static int MIRANA_CD_L1 = (140 * 1000);
    static int MIRANA_CD_L2 = (120 * 1000);
    static int MIRANA_CD_L3 = (100 * 1000);

    static int MONKEY_KING_CD_L1 = (100 * 1000);
    static int MONKEY_KING_CD_L2 = (85 * 1000);
    static int MONKEY_KING_CD_L3 = (70 * 1000);

    static int MORPH_CD_L1 = (80 * 1000);
    static int MORPH_CD_L2 = (80 * 1000);
    static int MORPH_CD_L3 = (80 * 1000);

    static int NAGA_ULT_CD_L1 = (180 * 1000);
    static int NAGA_ULT_CD_L2 = (120 * 1000);
    static int NAGA_ULT_CD_L3 = (60 * 1000);

    static int NP_ULT_CD_L1 = (90 * 1000);
    static int NP_ULT_CD_L2 = (75 * 1000);
    static int NP_ULT_CD_L3 = (60 * 1000);

    static int NECRO_ULT_CD_L1 = (100 * 1000);
    static int NECRO_ULT_CD_L2 = (85 * 1000);
    static int NECRO_ULT_CD_L3 = (70 * 1000);

    static int NIGHT_STALKER_ULT_CD_L1 = (160 * 1000);
    static int NIGHT_STALKER_ULT_CD_L2 = (120 * 1000);
    static int NIGHT_STALKER_ULT_CD_L3 = (80 * 1000);

    static int NYX_ULT_CD_L1 = (70 * 1000);
    static int NYX_ULT_CD_L2 = (60 * 1000);
    static int NYX_ULT_CD_L3 = (50 * 1000);

    static int ORGE_MAGI_BLOODLUST_CD_L1 = (20 * 1000);
    static int ORGE_MAGI_BLOODLUST_CD_L2 = (20 * 1000);
    static int ORGE_MAGI_BLOODLUST_CD_L3 = (20 * 1000);

    static int OMNI_ULT_CD_L1 = (160 * 1000);
    static int OMNI_ULT_CD_L2 = (160 * 1000);
    static int OMNI_ULT_CD_L3 = (160 * 1000);

    static int ORACLE_ULT_CD_L1 = (100 * 1000);
    static int ORACLE_ULT_CD_L2 = (65 * 1000);
    static int ORACLE_ULT_CD_L3 = (30 * 1000);

    static int OD_ULT_CD_L1 = (160 * 1000);
    static int OD_ULT_CD_L2 = (160 * 1000);
    static int OD_ULT_CD_L3 = (160 * 1000);

    static int PA_DAGGER_CD_L1 = (160 * 1000);
    static int PA_DAGGER_CD_L2 = (160 * 1000);
    static int PA_DAGGER_CD_L3 = (160 * 1000);

    static int PHANTOM_LANCER_CD_L1 = (0 * 1000);
    static int PHANTOM_LANCER_CD_L2 = (0 * 1000);
    static int PHANTOM_LANCER_CD_L3 = (0 * 1000);

    static int PHOENIX_ULT_CD_L1 = (110 * 1000);
    static int PHOENIX_ULT_CD_L2 = (110 * 1000);
    static int PHOENIX_ULT_CD_L3 = (110 * 1000);

    static int PUCK_ULT_CD_L1 = (70 * 1000);
    static int PUCK_ULT_CD_L2 = (65 * 1000);
    static int PUCK_ULT_CD_L3 = (60 * 1000);

    static int PUDGE_ULT_CD_L1 = (30 * 1000);
    static int PUDGE_ULT_CD_L2 = (25 * 1000);
    static int PUDGE_ULT_CD_L3 = (20 * 1000);

    static int PUGNA_ULT_CD_L1 = (22 * 1000);
    static int PUGNA_ULT_CD_L2 = (22 * 1000);
    static int PUGNA_ULT_CD_L3 = (22 * 1000);

    static int QOP_ULT_CD_L1 = (135 * 1000);
    static int QOP_ULT_CD_L2 = (135 * 1000);
    static int QOP_ULT_CD_L3 = (135 * 1000);

    static int RAZOR_ULT_CD_L1 = (80 * 1000);
    static int RAZOR_ULT_CD_L2 = (70 * 1000);
    static int RAZOR_ULT_CD_L3 = (60 * 1000);

    static int RIKI_ULT_CD_L1 = (40 * 1000);
    static int RIKI_ULT_CD_L2 = (35 * 1000);
    static int RIKI_ULT_CD_L3 = (30 * 1000);

    static int RUBICK_ULT_CD_L1 = (20 * 1000);
    static int RUBICK_ULT_CD_L2 = (18 * 1000);
    static int RUBICK_ULT_CD_L3 = (16 * 1000);

    static int SAND_KING_ULT_CD_L1 = (120 * 1000);
    static int SAND_KING_ULT_CD_L2 = (110 * 1000);
    static int SAND_KING_ULT_CD_L3 = (100 * 1000);

    static int SHADOW_DEMON_ULT_CD_L1 = (50 * 1000);
    static int SHADOW_DEMON_ULT_CD_L2 = (50 * 1000);
    static int SHADOW_DEMON_ULT_CD_L3 = (50 * 1000);

    static int SHADOW_FIEND_ULT_CD_L1 = (120 * 1000);
    static int SHADOW_FIEND_ULT_CD_L2 = (110 * 1000);
    static int SHADOW_FIEND_ULT_CD_L3 = (100 * 1000);

    static int SHADOW_SHAMAN_ULT_CD_L1 = (120 * 1000);
    static int SHADOW_SHAMAN_ULT_CD_L2 = (120 * 1000);
    static int SHADOW_SHAMAN_ULT_CD_L3 = (120 * 1000);

    static int SILENCER_ULT_CD_L1 = (130 * 1000);
    static int SILENCER_ULT_CD_L2 = (130 * 1000);
    static int SILENCER_ULT_CD_L3 = (130 * 1000);

    static int SKYWRATH_ULT_CD_L1 = (60 * 1000);
    static int SKYWRATH_ULT_CD_L2 = (40 * 1000);
    static int SKYWRATH_ULT_CD_L3 = (20 * 1000);

    static int SLARDAR_ULT_CD_L1 = (5 * 1000);
    static int SLARDAR_ULT_CD_L2 = (5 * 1000);
    static int SLARDAR_ULT_CD_L3 = (5 * 1000);

    static int SLARK_ULT_CD_L1 = (60 * 1000);
    static int SLARK_ULT_CD_L2 = (60 * 1000);
    static int SLARK_ULT_CD_L3 = (60 * 1000);

    static int SNIPER_ULT_CD_L1 = (20 * 1000);
    static int SNIPER_ULT_CD_L2 = (15 * 1000);
    static int SNIPER_ULT_CD_L3 = (10 * 1000);

    static int SPECTRE_ULT_CD_L1 = (140 * 1000);
    static int SPECTRE_ULT_CD_L2 = (130 * 1000);
    static int SPECTRE_ULT_CD_L3 = (120 * 1000);

    static int SPIRIT_BREAKER_ULT_CD_L1 = (80 * 1000);
    static int SPIRIT_BREAKER_ULT_CD_L2 = (70 * 1000);
    static int SPIRIT_BREAKER_ULT_CD_L3 = (60 * 1000);

    static int STORM_SPIRIT_ULT_CD_L1 = (0 * 1000);
    static int STORM_SPIRIT_ULT_CD_L2 = (0 * 1000);
    static int STORM_SPIRIT_ULT_CD_L3 = (0 * 1000);

    static int SVEN_ULT_CD_L1 = (80 * 1000);
    static int SVEN_ULT_CD_L2 = (80 * 1000);
    static int SVEN_ULT_CD_L3 = (80 * 1000);

    static int TECHIES_BLAST_OFF_CD_L1 = (35 * 1000);
    static int TECHIES_BLAST_OFF_CD_L2 = (35 * 1000);
    static int TECHIES_BLAST_OFF_CD_L3 = (35 * 1000);

    static int PA_REFRACTION_CD_L1 = (17 * 1000);
    static int PA_REFRACTION_CD_L2 = (17 * 1000);
    static int PA_REFRACTION_CD_L3 = (17 * 1000);

    static int TERRORBLADE_METAMORPHOSIS_CD_L1 = (140 * 1000);
    static int TERRORBLADE_METAMORPHOSIS_CD_L2 = (140 * 1000);
    static int TERRORBLADE_METAMORPHOSIS_CD_L3 = (140 * 1000);

    static int TIDEHUNTER_ULT_CD_L1 = (150 * 1000);
    static int TIDEHUNTER_ULT_CD_L2 = (150 * 1000);
    static int TIDEHUNTER_ULT_CD_L3 = (150 * 1000);

    static int TIMBERSAW_ULT_CD_L1 = (8 * 1000);
    static int TIMBERSAW_ULT_CD_L2 = (8 * 1000);
    static int TIMBERSAW_ULT_CD_L3 = (8 * 1000);

    static int TINKER_CD_L1 = (0 * 1000);
    static int TINKER_CD_L2 = (0 * 1000);
    static int TINKER_CD_L3 = (0 * 1000);

    static int TINY_AVALANCHE_CD_L1 = (17 * 1000);
    static int TINY_AVALANCHE_CD_L2 = (17 * 1000);
    static int TINY_AVALANCHE_CD_L3 = (17 * 1000);

    static int TREANT_PROTECTOR_ULT_CD_L1 = (70 * 1000);
    static int TREANT_PROTECTOR_ULT_CD_L2 = (70 * 1000);
    static int TREANT_PROTECTOR_ULT_CD_L3 = (70 * 1000);

    static int TROLL_WARLORD_ULT_CD_L1 = (30 * 1000);
    static int TROLL_WARLORD_ULT_CD_L2 = (30 * 1000);
    static int TROLL_WARLORD_ULT_CD_L3 = (30 * 1000);

    static int TUSK_ULT_CD_L1 = (36 * 1000);
    static int TUSK_ULT_CD_L2 = (24 * 1000);
    static int TUSK_ULT_CD_L3 = (12 * 1000);

    static int UNDERLORD_ULT_CD_L1 = (130 * 1000);
    static int UNDERLORD_ULT_CD_L2 = (120 * 1000);
    static int UNDERLORD_ULT_CD_L3 = (110 * 1000);

    static int UNDYING_ULT_CD_L1 = (75 * 1000);
    static int UNDYING_ULT_CD_L2 = (75 * 1000);
    static int UNDYING_ULT_CD_L3 = (75 * 1000);

    static int URSA_ULT_CD_L1 = (50 * 1000);
    static int URSA_ULT_CD_L2 = (40 * 1000);
    static int URSA_ULT_CD_L3 = (30 * 1000);

    static int VENGEFUL_SPIRIT_ULT_CD_L1 = (45 * 1000);
    static int VENGEFUL_SPIRIT_ULT_CD_L2 = (45 * 1000);
    static int VENGEFUL_SPIRIT_ULT_CD_L3 = (45 * 1000);

    static int VENOMANCER_ULT_CD_L1 = (140 * 1000);
    static int VENOMANCER_ULT_CD_L2 = (120 * 1000);
    static int VENOMANCER_ULT_CD_L3 = (100 * 1000);

    static int VIPER_ULT_CD_L1 = (70 * 1000);
    static int VIPER_ULT_CD_L2 = (50 * 1000);
    static int VIPER_ULT_CD_L3 = (30 * 1000);

    static int VISAGE_ULT_CD_L1 = (160 * 1000);
    static int VISAGE_ULT_CD_L2 = (145 * 1000);
    static int VISAGE_ULT_CD_L3 = (130 * 1000);

    static int WARLOCK_ULT_CD_L1 = (165 * 1000);
    static int WARLOCK_ULT_CD_L2 = (165 * 1000);
    static int WARLOCK_ULT_CD_L3 = (165 * 1000);

    static int WEAVER_ULT_CD_L1 = (60 * 1000);
    static int WEAVER_ULT_CD_L2 = (50 * 1000);
    static int WEAVER_ULT_CD_L3 = (40 * 1000);

    static int WINDRANGER_ULT_CD_L1 = (60 * 1000);
    static int WINDRANGER_ULT_CD_L2 = (60 * 1000);
    static int WINDRANGER_ULT_CD_L3 = (60 * 1000);

    static int WINTER_WYVERN_ULT_CD_L1 = (80 * 1000);
    static int WINTER_WYVERN_ULT_CD_L2 = (80 * 1000);
    static int WINTER_WYVERN_ULT_CD_L3 = (80 * 1000);

    static int WITCH_DOCTOR_ULT_CD_L1 = (80 * 1000);
    static int WITCH_DOCTOR_ULT_CD_L2 = (80 * 1000);
    static int WITCH_DOCTOR_ULT_CD_L3 = (80 * 1000);

    static int WRAITH_KING_ULT_CD_L1 = (240 * 1000);
    static int WRAITH_KING_ULT_CD_L2 = (140 * 1000);
    static int WRAITH_KING_ULT_CD_L3 = (40 * 1000);

    static int ZUES_ULT_CD_L1 = (90 * 1000);
    static int ZUES_ULT_CD_L2 = (90 * 1000);
    static int ZUES_ULT_CD_L3 = (90 * 1000);

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
            CM_ULT_CD_L3,
            DARK_SEER_ULT_CD_L1,
            DARK_SEER_ULT_CD_L2,
            DARK_SEER_ULT_CD_L3,
            DAZZLE_ULT_CD_L1,
            DAZZLE_ULT_CD_L2,
            DAZZLE_ULT_CD_L3,
            DEATH_PROPHET_CD_L1,
            DEATH_PROPHET_CD_L2,
            DEATH_PROPHET_CD_L3,
            DISRUPTOR_CD_L1,
            DISRUPTOR_CD_L2,
            DISRUPTOR_CD_L3,
            DOOM_CD_L1,
            DOOM_CD_L2,
            DOOM_CD_L3,
            DRAGON_KNIGHT_CD_L1,
            DRAGON_KNIGHT_CD_L2,
            DRAGON_KNIGHT_CD_L3,
            DROW_PRESICION_AURA_L1 ,
            DROW_PRESICION_AURA_L2 ,
            DROW_PRESICION_AURA_L3,
            EARTH_SPIRIT_CD_L1,
            EARTH_SPIRIT_CD_L2,
            EARTH_SPIRIT_CD_L3,
            EARTHSHAKER_CD_L1 ,
            EARTHSHAKER_CD_L2,
            EARTHSHAKER_CD_L3,
            ELDER_TITAN_CD_L1,
            ELDER_TITAN_CD_L2,
            ELDER_TITAN_CD_L3,
            EMBER_SPIRIT_FLAME_GUARD_CD_L1,
            EMBER_SPIRIT_FLAME_GUARD_CD_L2,
            EMBER_SPIRIT_FLAME_GUARD_CD_L3,
            ENCHANTRESS_CD_L1,
            ENCHANTRESS_CD_L2,
            ENCHANTRESS_CD_L3,
            ENIGMA_CD_L1,
            ENIGMA_CD_L2,
            ENIGMA_CD_L3,
            FACELESS_CD_L1,
            FACELESS_CD_L2,
            FACELESS_CD_L3,
            GYROCOPTER_CD_L1,
            GYROCOPTER_CD_L2 ,
            GYROCOPTER_CD_L3,
            HUSKAR_CD_L1,
            HUSKAR_CD_L2 ,
            HUSKAR_CD_L3,
            INVOKER_DEAFENING_BLAST_CD_L1,
            INVOKER_DEAFENING_BLAST_CD_L2,
            INVOKER_DEAFENING_BLAST_CD_L3,
            IO_CD_L1,
            IO_CD_L2,
            IO_CD_L3,
            JAKIRO_CD_L1 ,
            JAKIRO_CD_L2,
            JAKIRO_CD_L3,
            JUGGERNAUT_CD_L1,
            JUGGERNAUT_CD_L2,
            JUGGERNAUT_CD_L3,
            KOTL_CD_L1 ,
            KOTL_CD_L2,
            KOTL_CD_L3,
            KUNKKA_CD_L1,
            KUNKKA_CD_L2,
            KUNKKA_CD_L3,
            LC_CD_L1 ,
            LC_CD_L2,
            LC_CD_L3,
            LESHRAC_DIABOLIC_EDICT_CD_L1 ,
            LESHRAC_DIABOLIC_EDICT_CD_L2,
            LESHRAC_DIABOLIC_EDICT_CD_L3,
            LICH_CD_L1 ,
            LICH_CD_L2,
            LICH_CD_L3,
            LIFESTEALER_CD_L1,
            LIFESTEALER_CD_L2,
            LIFESTEALER_CD_L3,
            LINA_CD_L1 ,
            LINA_CD_L2,
            LINA_CD_L3,
            LION_CD_L1,
            LION_CD_L2 ,
            LION_CD_L3,
            LONE_DRUID_SPIRIT_BEAR_CD_L1,
            LONE_DRUID_SPIRIT_BEAR_CD_L2,
            LONE_DRUID_SPIRIT_BEAR_CD_L3,
            LUNA_CD_L1,
            LUNA_CD_L2,
            LUNA_CD_L3,
            LYCAN_CD_L1 ,
            LYCAN_CD_L2 ,
            LYCAN_CD_L3,
            MAGNUS_CD_L1 ,
            MAGNUS_CD_L2 ,
            MAGNUS_CD_L3,
            MEDUSA_CD_L1,
            MEDUSA_CD_L2,
            MEDUSA_CD_L3,
            MEEPO_CD_L1,
            MEEPO_CD_L2,
            MEEPO_CD_L3,
            MIRANA_CD_L1,
            MIRANA_CD_L2,
            MIRANA_CD_L3,
            MONKEY_KING_CD_L1,
            MONKEY_KING_CD_L2,
            MONKEY_KING_CD_L3,
            MORPH_CD_L1,
            MORPH_CD_L2,
            MORPH_CD_L3,
            NAGA_ULT_CD_L1,
            NAGA_ULT_CD_L2,
            NAGA_ULT_CD_L3,
            NP_ULT_CD_L1,
            NP_ULT_CD_L2,
            NP_ULT_CD_L3,
            NECRO_ULT_CD_L1,
            NECRO_ULT_CD_L2,
            NECRO_ULT_CD_L3,
            NIGHT_STALKER_ULT_CD_L1,
            NIGHT_STALKER_ULT_CD_L2,
            NIGHT_STALKER_ULT_CD_L3,
            NYX_ULT_CD_L1,
            NYX_ULT_CD_L2,
            NYX_ULT_CD_L3,
            ORGE_MAGI_BLOODLUST_CD_L1,
            ORGE_MAGI_BLOODLUST_CD_L2,
            ORGE_MAGI_BLOODLUST_CD_L3,
            OMNI_ULT_CD_L1,
            OMNI_ULT_CD_L2,
            OMNI_ULT_CD_L3,
            ORACLE_ULT_CD_L1,
            ORACLE_ULT_CD_L2,
            ORACLE_ULT_CD_L3,
            OD_ULT_CD_L1,
            OD_ULT_CD_L2,
            OD_ULT_CD_L3,
            PA_DAGGER_CD_L1,
            PA_DAGGER_CD_L2,
            PA_DAGGER_CD_L3,
            PHANTOM_LANCER_CD_L1,
            PHANTOM_LANCER_CD_L2,
            PHANTOM_LANCER_CD_L3,
            PHOENIX_ULT_CD_L1,
            PHOENIX_ULT_CD_L2,
            PHOENIX_ULT_CD_L3,
            PUCK_ULT_CD_L1,
            PUCK_ULT_CD_L2,
            PUCK_ULT_CD_L3,
            PUDGE_ULT_CD_L1,
            PUDGE_ULT_CD_L2,
            PUDGE_ULT_CD_L3,
            PUGNA_ULT_CD_L1,
            PUGNA_ULT_CD_L2,
            PUGNA_ULT_CD_L3,
            QOP_ULT_CD_L1,
            QOP_ULT_CD_L2,
            QOP_ULT_CD_L3,
            RAZOR_ULT_CD_L1,
            RAZOR_ULT_CD_L2,
            RAZOR_ULT_CD_L3,
            RIKI_ULT_CD_L1,
            RIKI_ULT_CD_L2,
            RIKI_ULT_CD_L3,
            RUBICK_ULT_CD_L1,
            RUBICK_ULT_CD_L2,
            RUBICK_ULT_CD_L3,
            SAND_KING_ULT_CD_L1,
            SAND_KING_ULT_CD_L2,
            SAND_KING_ULT_CD_L3,
            SHADOW_DEMON_ULT_CD_L1,
            SHADOW_DEMON_ULT_CD_L2,
            SHADOW_DEMON_ULT_CD_L3,
            SHADOW_FIEND_ULT_CD_L1,
            SHADOW_FIEND_ULT_CD_L2,
            SHADOW_FIEND_ULT_CD_L3,
            SHADOW_SHAMAN_ULT_CD_L1,
            SHADOW_SHAMAN_ULT_CD_L2,
            SHADOW_SHAMAN_ULT_CD_L3,
            SILENCER_ULT_CD_L1,
            SILENCER_ULT_CD_L2,
            SILENCER_ULT_CD_L3,
            SKYWRATH_ULT_CD_L1,
            SKYWRATH_ULT_CD_L2,
            SKYWRATH_ULT_CD_L3,
            SLARDAR_ULT_CD_L1,
            SLARDAR_ULT_CD_L2,
            SLARDAR_ULT_CD_L3,
            SLARK_ULT_CD_L1,
            SLARK_ULT_CD_L2,
            SLARK_ULT_CD_L3,
            SNIPER_ULT_CD_L1,
            SNIPER_ULT_CD_L2,
            SNIPER_ULT_CD_L3,
            SPECTRE_ULT_CD_L1,
            SPECTRE_ULT_CD_L2,
            SPECTRE_ULT_CD_L3,
            SPIRIT_BREAKER_ULT_CD_L1,
            SPIRIT_BREAKER_ULT_CD_L2,
            SPIRIT_BREAKER_ULT_CD_L3,
            STORM_SPIRIT_ULT_CD_L1,
            STORM_SPIRIT_ULT_CD_L2,
            STORM_SPIRIT_ULT_CD_L3,
            SVEN_ULT_CD_L1,
            SVEN_ULT_CD_L2,
            SVEN_ULT_CD_L3,
            TECHIES_BLAST_OFF_CD_L1,
            TECHIES_BLAST_OFF_CD_L2,
            TECHIES_BLAST_OFF_CD_L3,
            PA_REFRACTION_CD_L1,
            PA_REFRACTION_CD_L2,
            PA_REFRACTION_CD_L3,
            TERRORBLADE_METAMORPHOSIS_CD_L1,
            TERRORBLADE_METAMORPHOSIS_CD_L2,
            TERRORBLADE_METAMORPHOSIS_CD_L3,
            TIDEHUNTER_ULT_CD_L1,
            TIDEHUNTER_ULT_CD_L2,
            TIDEHUNTER_ULT_CD_L3,
            TIMBERSAW_ULT_CD_L1,
            TIMBERSAW_ULT_CD_L2,
            TIMBERSAW_ULT_CD_L3,
            TINKER_CD_L1,
            TINKER_CD_L2,
            TINKER_CD_L3,
            TINY_AVALANCHE_CD_L1,
            TINY_AVALANCHE_CD_L2,
            TINY_AVALANCHE_CD_L3,
            TREANT_PROTECTOR_ULT_CD_L1,
            TREANT_PROTECTOR_ULT_CD_L2,
            TREANT_PROTECTOR_ULT_CD_L3,
            TROLL_WARLORD_ULT_CD_L1,
            TROLL_WARLORD_ULT_CD_L2,
            TROLL_WARLORD_ULT_CD_L3,
            TUSK_ULT_CD_L1,
            TUSK_ULT_CD_L2,
            TUSK_ULT_CD_L3,
            UNDERLORD_ULT_CD_L1,
            UNDERLORD_ULT_CD_L2,
            UNDERLORD_ULT_CD_L3,
            UNDYING_ULT_CD_L1,
            UNDYING_ULT_CD_L2,
            UNDYING_ULT_CD_L3,
            URSA_ULT_CD_L1,
            URSA_ULT_CD_L2,
            URSA_ULT_CD_L3,
            VENGEFUL_SPIRIT_ULT_CD_L1,
            VENGEFUL_SPIRIT_ULT_CD_L2,
            VENGEFUL_SPIRIT_ULT_CD_L3,
            VENOMANCER_ULT_CD_L1,
            VENOMANCER_ULT_CD_L2,
            VENOMANCER_ULT_CD_L3,
            VIPER_ULT_CD_L1,
            VIPER_ULT_CD_L2,
            VIPER_ULT_CD_L3,
            VISAGE_ULT_CD_L1,
            VISAGE_ULT_CD_L2,
            VISAGE_ULT_CD_L3,
            WARLOCK_ULT_CD_L1,
            WARLOCK_ULT_CD_L2,
            WARLOCK_ULT_CD_L3,
            WEAVER_ULT_CD_L1,
            WEAVER_ULT_CD_L2,
            WEAVER_ULT_CD_L3,
            WINDRANGER_ULT_CD_L1,
            WINDRANGER_ULT_CD_L2,
            WINDRANGER_ULT_CD_L3,
            WINTER_WYVERN_ULT_CD_L1,
            WINTER_WYVERN_ULT_CD_L2,
            WINTER_WYVERN_ULT_CD_L3,
            WITCH_DOCTOR_ULT_CD_L1,
            WITCH_DOCTOR_ULT_CD_L2,
            WITCH_DOCTOR_ULT_CD_L3,
            WRAITH_KING_ULT_CD_L1,
            WRAITH_KING_ULT_CD_L2,
            WRAITH_KING_ULT_CD_L3,
            ZUES_ULT_CD_L1,
            ZUES_ULT_CD_L2,
            ZUES_ULT_CD_L3
};

    ListView hero_list_view;
    TextView hero1_textview;
    TextView hero2_textview;
    TextView hero3_textview;
    TextView hero4_textview;
    TextView hero5_textview;
    String[] heroSelected = new String[MAX_SELECTED_HEROES];
    String[] abilitySelected = new String [MAX_SELECTED_HEROES];
    //each hero has 3 levels of ultimate and thus 3 ultimate cooldown times
    int[] heroSelectedUltTimes = new int[MAX_SELECTED_HEROES * 3];
    int i = 0;

    String [] abilityName = {
            "Borrowed Time",
            "Chemical Rage",
            "Ice Blast",
            "Mana Void",
            "Tempest Double",
            "Culling Blade",
            "Fiend's Grip",
            "Flaming Lasso",
            "Primal Roar",
            "Rupture",
            "Track",
            "Primal Split",
            "Really?",
            "Insatiable Hunger",
            "Stampede",
            "Phantasm",
            "Hand of God",
            "Death Pact",
            "Hookshot",
            "Freezing Field",
            "Wall of Replica",
            "Weave",
            "Exorcism",
            "Static Storm",
            "DOOM",
            "Elder Dragon Form",
            "Presicion Aura",
            "Magnetize",
            "Echo Slam",
            "Earth Splitter",
            "Flame Guard",
            "Nature's Attendants",
            "Black Hole",
            "Chronosphere",
            "Call Down",
            "Life Break",
            "Deafening Blast",
            "Relocate",
            "Macropyre",
            "Omnislash",
            "Illuminate",
            "Ghostship",
            "Duel",
            "Diabolic Edict",
            "Chain Frost",
            "Infest",
            "Laguna Blade",
            "Finger of Death",
            "Summon Spirit Bear",
            "Eclipse",
            "Shapeshift",
            "Reverse Polarity",
            "Stone Gaze",
            "Meepo",
            "Moonlight Shadow",
            "Wukong's Command",
            "Replicate",
            "Song of the Siren",
            "Wrath of Nature",
            "Reaper's Scyth",
            "Darkness",
            "Vendetta",
            "Bloodlust",
            "Guardian Angel",
            "False Promise",
            "Sanity's Eclipse",
            "Stifling Dagger",
            "PL never sleeps",
            "Supernova",
            "Dream Coil",
            "Dismember",
            "Life Drain",
            "Sonic Wave",
            "Eye of the Storm",
            "Tricks of the Trade",
            "Spell Steal",
            "Epicenter",
            "Demonic Purge",
            "Requiem of Souls",
            "Mass Serpent Ward",
            "Global Silence",
            "Mystic Flare",
            "Corrosive Haze",
            "Shadow Dance",
            "Assassinate",
            "Haunt",
            "Nether Strike",
            "Ball Lightning",
            "God's Strength",
            "Blast Off!",
            "Refraction",
            "Metamorphosis",
            "Chakram",
            "Do you even DOTA?",
            "Overgrowth",
            "Battle Trance",
            "Walrus PUNCH!",
            "Dark Rift",
            "Flesh Golem",
            "Enrage",
            "Nether Swap",
            "Poison Nova",
            "Viper Strike",
            "Summon Familiars",
            "Chaotic Offering",
            "Time Lapse",
            "Focus Fire",
            "Winter's Curse",
            "Death Ward",
            "Reincarnation",
            "Thundergod's Wrath"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_picker);

        hero_list_view = (ListView) findViewById(R.id.hero_list_view);
        hero1_textview = (TextView)  findViewById(R.id.selected_hero1_hero_select_textview);
        hero2_textview = (TextView)  findViewById(R.id.selected_hero2_hero_select_textview);
        hero3_textview = (TextView)  findViewById(R.id.selected_hero3_hero_select_textview);
        hero4_textview = (TextView)  findViewById(R.id.selected_hero4_hero_select_textview);
        hero5_textview = (TextView)  findViewById(R.id.selected_hero5_hero_select_textview);

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
                abilitySelected[i] = abilityName[position];
                heroSelectedUltTimes[i * 3] = ultTimes[position * 3];
                heroSelectedUltTimes[(i * 3) + 1] = ultTimes[(position * 3) + 1];
                heroSelectedUltTimes[(i * 3) + 2] = ultTimes[(position * 3) + 2];

                switch (i + 1){
                    case 1:
                        hero1_textview.setText("1: " + heroSelected[i] + "\n" + abilitySelected[i]);
                        break;
                    case 2:
                        hero2_textview.setText("2: " + heroSelected[i] + "\n" + abilitySelected[i]);
                        break;
                    case 3:
                        hero3_textview.setText("3: " + heroSelected[i] + "\n" + abilitySelected[i]);
                        break;
                    case 4:
                        hero4_textview.setText("4: " + heroSelected[i] + "\n" + abilitySelected[i]);
                        break;
                    case 5:
                        hero5_textview.setText("5: " + heroSelected[i] + "\n" + abilitySelected[i]);
                        break;
                }



                i++;
                if (i >= MAX_SELECTED_HEROES) {
                    i = 0;
                }

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        itemValue, Toast.LENGTH_LONG)
                        .show();
            }

        });
    }

    //called when backToMain button is clicked
    public void returnToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("selectedHeroes", heroSelected);
        intent.putExtra("selectedHeroesUltTimes", heroSelectedUltTimes);
        intent.putExtra("abilityNames", abilitySelected);
        startActivity(intent);
    }
}