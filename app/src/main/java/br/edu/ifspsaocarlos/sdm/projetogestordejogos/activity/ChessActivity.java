package br.edu.ifspsaocarlos.sdm.projetogestordejogos.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import br.edu.ifspsaocarlos.sdm.projetogestordejogos.R;
import util.Util;

public class ChessActivity extends AppCompatActivity {
    private Chronometer chronometerPlayer1;
    private Chronometer chronometerPlayer2;
    private FloatingActionButton fab;
    private LinearLayout layoutPlayer1, layoutPlayer2;
    private TextView textMovesPlayer1, textMovesPlayer2;
    private TextView textNamePlayer1, textNamePlayer2;
    private Drawable ic_play, ic_pause;

    private String nameJogador1, nameJogador2;

    //Controlador do jogo
    private int gameStatus = 0;
    private int movePlayer1, movePlayer2;
    private int playerMoving;

    //Controlador de jogador
    private final int PLAYER1 = 1;
    private final int PLAYER2 = 2;

    //Controlador de ação do FAB
    private final int STOPED = 0;
    private final int STARTED = 1;
    private final int PAUSED = 2;

    //Controlador de ação do cronometro
    private final int START = 0;
    private final int STOP = 1;
    private final int RESTART = 2;
    private final int RESET = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setResult(Util.CHESS_RESULT_NO_NEW_NAME);

        Intent intent = getIntent();
        //Nome do jogador 1
        if (intent.hasExtra(Util.PLAYER1_NAME)){
            nameJogador1 = intent.getStringExtra(Util.PLAYER1_NAME);
        }
        else{
            nameJogador1 = "Jogador 1";
        }

        //Nome do jogador 2
        if (intent.hasExtra(Util.PLAYER2_NAME)){
            nameJogador2 = intent.getStringExtra(Util.PLAYER2_NAME);
        }
        else{
            nameJogador2 = "Jogador 2";
        }


        textNamePlayer1 = (TextView)findViewById(R.id.textViewPlayer1);
        textNamePlayer2 = (TextView)findViewById(R.id.textViewPlayer2);

        updatePlayersNames();

        textMovesPlayer1 = (TextView)findViewById(R.id.textViewMovesPlayer1);
        textMovesPlayer2 = (TextView)findViewById(R.id.textViewMovesPlayer2);

        chronometerPlayer1 = (Chronometer) findViewById(R.id.chronometerPlayer1);
        chronometerPlayer2 = (Chronometer) findViewById(R.id.chronometerPlayer2);

        layoutPlayer1 = (LinearLayout )findViewById(R.id.layoutPlayer1);
        layoutPlayer2 = (LinearLayout )findViewById(R.id.layoutPlayer2);
        layoutPlayer1.setOnClickListener(layoutPlayer1ClickListener);
        layoutPlayer2.setOnClickListener(layoutPlayer2ClickListener);

        ic_play = getResources().getDrawable(android.R.drawable.ic_media_play);
        ic_pause = getResources().getDrawable(android.R.drawable.ic_media_pause);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(Util.DEGUB_NAME, "game statuts: " + gameStatus);

                //Lógica do status do FAB
                switch (gameStatus) {
                    case STOPED:
                        startGame();
                        break;

                    case STARTED:
                        pauseGame();
                        break;

                    case PAUSED:
                        restartGame();
                        break;
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chess_activity_bar, menu);

        setMenuColor(menu.findItem(R.id.action_reset).getIcon());
        setMenuColor(menu.findItem(R.id.action_setting).getIcon());

        return super.onCreateOptionsMenu(menu);
    }

    private void setMenuColor(Drawable d){
        if (d != null) {
            d.mutate();
            d.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;

            case R.id.action_reset:
                confirmReset();
                break;

            case R.id.action_setting:
                configUser();
                break;

            default:
                break;
        }

        return true;
    }

    public void confirmReset(){
        if(gameStatus == STARTED)
            pauseGame();

        new MaterialDialog.Builder(this)
                .title(R.string.chess_activity_confirm_reset_title)
                .content(R.string.chess_activity_confirm_reset)
                .positiveText(R.string.ok)
                .negativeText(R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        showScore();
                    }
                })
                .show();
    }

    public void showScore(){
        new MaterialDialog.Builder(this)
                .title(R.string.chess_activity_result_title)
                .content(nameJogador1
                        + "\nJogadas: "
                        + movePlayer1
                        + "\nTempo: "
                        + chronometerPlayer1.getText()
                        + "\n\n"
                        +nameJogador2
                        + "\nJogadas: "
                        + movePlayer2
                        + "\nTempo: "
                        + chronometerPlayer2.getText())
                .positiveText(R.string.ok)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        resetGame();
                    }
                })
                .show();
    }


    private void configUser(){
        if(gameStatus == STARTED)
            pauseGame();

        MaterialDialog mdialog = new MaterialDialog.Builder(this)
                .title(R.string.user_config_activity_name)
                .customView(R.layout.activity_user_config, true)
                .positiveText(R.string.ok)
                .negativeText(R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Log.d(Util.DEGUB_NAME, "Botão Confirmar");
                        RelativeLayout text = (RelativeLayout) dialog.getCustomView();

                        EditText editPlayer1 = (EditText) text.getChildAt(0);
                        EditText editPlayer2 = (EditText) text.getChildAt(1);

                        saveNames(editPlayer1.getText().toString(), editPlayer2.getText().toString());

                        Log.d(Util.DEGUB_NAME, "Jogador 1: " + nameJogador1);
                        Log.d(Util.DEGUB_NAME, "Jogador 2: " + nameJogador2);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Log.d(Util.DEGUB_NAME, "Botão Cancelar");
                    }
                })
                .build();


        RelativeLayout text = (RelativeLayout) mdialog.getCustomView();

        EditText editPlayer1 = (EditText) text.getChildAt(0);
        EditText editPlayer2 = (EditText) text.getChildAt(1);

        editPlayer1.setText(nameJogador1);
        editPlayer2.setText(nameJogador2);

        mdialog.show();
    }

    private void saveNames(String name1, String name2){
        nameJogador1 = name1;
        nameJogador2 = name2;

        SharedPreferences sharedpreferences = getSharedPreferences(Util.APP_SHARED_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(Util.PLAYER1_NAME, nameJogador1);
        editor.putString(Util.PLAYER2_NAME, nameJogador2);
        editor.commit();

        setResult(Util.CHESS_RESULT_NEW_NAME);

        updatePlayersNames();
    }


    private void startGame(){
        movePlayer1 = 0;
        movePlayer2 = 0;
        playerMoving = PLAYER1;

        layoutPlayer1.setEnabled(true);
        layoutPlayer2.setEnabled(false);

        fab.setImageDrawable(ic_pause);

        chronometerControl(START, chronometerPlayer1);
        chronometerControl(START, chronometerPlayer2);
        chronometerControl(STOP, chronometerPlayer2);

        gameStatus = STARTED;
    }

    private void pauseGame(){
        fab.setImageDrawable(ic_play);

        layoutPlayer1.setEnabled(false);
        layoutPlayer2.setEnabled(false);

        chronometerControl(STOP, chronometerPlayer1);
        chronometerControl(STOP, chronometerPlayer2);

        gameStatus = PAUSED;
    }

    private void restartGame(){
        fab.setImageDrawable(ic_pause);

        if(playerMoving == PLAYER1){
            chronometerControl(RESTART, chronometerPlayer1);
            layoutPlayer1.setEnabled(true);
        }
        else if (playerMoving == PLAYER2){
            chronometerControl(RESTART, chronometerPlayer2);
            layoutPlayer2.setEnabled(true);

        }

        gameStatus = STARTED;
    }

    private void resetGame(){
        fab.setImageDrawable(ic_play);

        chronometerControl(RESET, chronometerPlayer1);
        chronometerControl(RESET, chronometerPlayer2);

        layoutPlayer1.setEnabled(false);
        layoutPlayer2.setEnabled(false);


        movePlayer1 = 0;
        movePlayer2 = 0;

        updateMoves();
        gameStatus = STOPED;
    }

    private void switchPlayer(){
        if(playerMoving == PLAYER1){
            playerMoving = PLAYER2;

            chronometerControl(STOP, chronometerPlayer1);
            chronometerControl(RESTART, chronometerPlayer2);

            movePlayer1++;

            layoutPlayer1.setEnabled(false);
            layoutPlayer2.setEnabled(true);

            updateMoves();
        }
        else if (playerMoving == PLAYER2){
            playerMoving = PLAYER1;

            chronometerControl(STOP, chronometerPlayer2);
            chronometerControl(RESTART, chronometerPlayer1);

            movePlayer2++;

            layoutPlayer1.setEnabled(true);
            layoutPlayer2.setEnabled(false);

            updateMoves();
        }
        else{
            Log.d(Util.DEGUB_NAME, "Erro ao processar jogador atual");
        }

    }

    private void updateMoves(){
        textMovesPlayer1.setText("" + movePlayer1);
        textMovesPlayer2.setText("" + movePlayer2);
    }

    private void updatePlayersNames(){
        textNamePlayer1.setText(nameJogador1);
        textNamePlayer2.setText(nameJogador2);
    }

    private void chronometerControl(int status, Chronometer chronometer){
        Log.d(Util.DEGUB_NAME, "System: " + SystemClock.elapsedRealtime() + " Chronometer base: " + chronometer.getBase());

        switch (status){
            case START:
                //Inicia o cronometro com a base de tempo sendo a mesma do sistema
                chronometer.setBase(SystemClock.elapsedRealtime());

                chronometer.start();

                Log.d(Util.DEGUB_NAME, "Start");
                break;

            case STOP:
                //Para o cronometro
                chronometer.stop();

                Log.d(Util.DEGUB_NAME, "Stop");
                break;

            case RESTART:
                //Reinicia o cronometro com a base do sistema menos o tempo ja decorrido
                int stoppedMilliseconds = 0;

                String chronoText = chronometer.getText().toString();

                String array[] = chronoText.split(":");
                if (array.length == 2) {
                    stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 1000 + Integer.parseInt(array[1]) * 1000;
                }
                else if (array.length == 3) {
                    stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 60 * 1000 + Integer.parseInt(array[1]) * 60 * 1000 + Integer.parseInt(array[2]) * 1000;
                }

                chronometer.setBase(SystemClock.elapsedRealtime() - stoppedMilliseconds);

                chronometer.start();

                Log.d(Util.DEGUB_NAME, "Restart");
                break;

            case RESET:
                //Reseta o cronometro

                chronometer.stop();
                chronometer.setBase(SystemClock.elapsedRealtime());

                Log.d(Util.DEGUB_NAME, "Reset");
                break;
        }
    }

    private View.OnClickListener layoutPlayer1ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switchPlayer();
        }
    };

    private View.OnClickListener layoutPlayer2ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switchPlayer();
        }
    };
}
