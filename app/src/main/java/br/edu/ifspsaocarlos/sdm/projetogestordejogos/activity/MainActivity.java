package br.edu.ifspsaocarlos.sdm.projetogestordejogos.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Slide;
import android.util.Log;
import android.view.View;

import com.balysv.materialripple.MaterialRippleLayout;

import br.edu.ifspsaocarlos.sdm.projetogestordejogos.R;
import br.edu.ifspsaocarlos.sdm.projetogestordejogos.util.Util;

public class MainActivity extends AppCompatActivity {
    private CardView bt_dice;
    private CardView bt_roulette;
    private CardView bt_chess;
    private CardView bt_chronometer;

    private String nameJogador1, nameJogador2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setNames();

        bt_dice = (CardView) findViewById(R.id.card_view_dice);
        bt_roulette = (CardView) findViewById(R.id.card_view_roulette);
        bt_chess = (CardView) findViewById(R.id.card_view_chess);
        bt_chronometer = (CardView) findViewById(R.id.card_view_chronometer);

        setRipple(bt_dice);
        setRipple(bt_roulette);
        setRipple(bt_chess);
        setRipple(bt_chronometer);

        bt_dice.setOnClickListener(btDiceOnClickListener);
        bt_roulette.setOnClickListener(btRouletteOnClickListener);
        bt_chess.setOnClickListener(btChessOnClickListener);
        bt_chronometer.setOnClickListener(btChronometerOnClickListener);

        setupWindowAnimations();
    }

    private void setRipple(View v){
        MaterialRippleLayout.on(v)
                .rippleOverlay(true)
                .rippleColor(Color.WHITE)
                .rippleAlpha(0.1f)
                .rippleRoundedCorners(4)
                .rippleHover(true)
                .rippleDelayClick(true)
                .rippleDuration(350)
                .rippleFadeDuration(75)
                .create();
    }

    private void setNames(){
        SharedPreferences sharedpreferences = getSharedPreferences(Util.APP_SHARED_FILE, Context.MODE_PRIVATE);
        nameJogador1 = sharedpreferences.getString(Util.PLAYER1_NAME, null);
        if(nameJogador1 == null){
            nameJogador1 = "Jogador 1";
        }

        nameJogador2 = sharedpreferences.getString(Util.PLAYER2_NAME, null);
        if(nameJogador2 == null){
            nameJogador2 = "Jogador 2";
        }
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

            intentChess.putExtra(Util.PLAYER1_NAME, nameJogador1);
            intentChess.putExtra(Util.PLAYER2_NAME, nameJogador2);

            startActivityForResult(intentChess, Util.CHESS);
        }
    };

    private View.OnClickListener btChronometerOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intentChronometer = new Intent(MainActivity.this, ChronometerActivity.class);
            startActivity(intentChronometer);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Util.CHESS) {
            if (resultCode == Util.CHESS_RESULT_NEW_NAME) {
                Log.d(Util.DEGUB_NAME, "novo nome setado");

                setNames();
            }
            else if (resultCode == Util.CHESS_RESULT_NO_NEW_NAME) {
                Log.d(Util.DEGUB_NAME, "nome é o mesmo");
            }
        }
    }
}
