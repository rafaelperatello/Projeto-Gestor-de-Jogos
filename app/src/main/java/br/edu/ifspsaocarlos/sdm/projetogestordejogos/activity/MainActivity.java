package br.edu.ifspsaocarlos.sdm.projetogestordejogos.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import br.edu.ifspsaocarlos.sdm.projetogestordejogos.ChronometerActivity;
import br.edu.ifspsaocarlos.sdm.projetogestordejogos.R;

public class MainActivity extends AppCompatActivity {
    private ImageButton bt_dice;
    private ImageButton bt_roulette;
    private ImageButton bt_chess;
    private ImageButton bt_chronometer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_dice = (ImageButton) findViewById(R.id.buttondice);
        bt_roulette = (ImageButton) findViewById(R.id.buttonroulette);
        bt_chess = (ImageButton) findViewById(R.id.buttonchess);
        bt_chronometer = (ImageButton) findViewById(R.id.buttonchronometer);

        bt_dice.setOnClickListener(btDiceOnClickListener);
        bt_roulette.setOnClickListener(btRouletteOnClickListener);
        bt_chess.setOnClickListener(btChessOnClickListener);
        bt_chronometer.setOnClickListener(btChronometerOnClickListener);

//        Drawable ic_dice = getResources().getDrawable( R.drawable.dice );
//        Drawable ic_roulette = getResources().getDrawable( R.drawable.roulette );
//        Drawable ic_chess = getResources().getDrawable( R.drawable.chess );
//        Drawable ic_chronometer = getResources().getDrawable( R.drawable.chronometer);
//
//        ColorFilter filter = new LightingColorFilter( Color.WHITE, Color.WHITE);
//        ic_dice.setColorFilter(filter);
//        ic_roulette.setColorFilter(filter);
//        ic_chess.setColorFilter(filter);
//        ic_chronometer.setColorFilter(filter);

        setupWindowAnimations();
    }

    private void setupWindowAnimations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide();
            slide.setDuration(1000);
            getWindow().setExitTransition(slide);
        }
    }

    private View.OnClickListener btDiceOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intentDice = new Intent(MainActivity.this, DiceActivity.class);
            startActivity(intentDice);
        }
    };

    private View.OnClickListener btRouletteOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intentRoulette = new Intent(MainActivity.this, RouletteActivity.class);
            startActivity(intentRoulette);
        }
    };

    private View.OnClickListener btChessOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intentChess = new Intent(MainActivity.this, ChessActivity.class);
            startActivity(intentChess);
        }
    };

    private View.OnClickListener btChronometerOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intentChronometer = new Intent(MainActivity.this, ChronometerActivity.class);
            startActivity(intentChronometer);
        }
    };
}
