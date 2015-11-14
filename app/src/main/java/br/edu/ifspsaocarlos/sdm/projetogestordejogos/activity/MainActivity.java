package br.edu.ifspsaocarlos.sdm.projetogestordejogos.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

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
    }

    private View.OnClickListener btDiceOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this, "Dice", Toast.LENGTH_LONG).show();
        }
    };

    private View.OnClickListener btRouletteOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this, "Roll", Toast.LENGTH_LONG).show();
        }
    };

    private View.OnClickListener btChessOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this, "Chess", Toast.LENGTH_LONG).show();
        }
    };

    private View.OnClickListener btChronometerOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this, "Chronometer", Toast.LENGTH_LONG).show();
        }
    };
}
