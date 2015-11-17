package br.edu.ifspsaocarlos.sdm.projetogestordejogos.activity;

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
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import br.edu.ifspsaocarlos.sdm.projetogestordejogos.R;
import util.Util;

public class ChronometerActivity extends AppCompatActivity {
    private Chronometer chronometer;
    private FloatingActionButton fab;
    private Drawable ic_play, ic_pause;
    private int chonometerStatus = 0;

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
        setContentView(R.layout.activity_chronometer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        chronometer = (Chronometer) findViewById(R.id.chronometer);
        ic_play = getResources().getDrawable(android.R.drawable.ic_media_play);
        ic_pause = getResources().getDrawable(android.R.drawable.ic_media_pause);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(Util.DEGUB_NAME, "chronometer statuts: " + chonometerStatus);

                //Lógica do status do FAB
                switch (chonometerStatus) {
                    case STOPED:
                        chronometerControl(START);
                        break;

                    case STARTED:
                        chronometerControl(STOP);
                        break;

                    case PAUSED:
                        chronometerControl(RESTART);
                        break;
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chronometer_activity_bar, menu);

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

            case R.id.action_setting:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_reset:
                confirmReset();
                break;

            default:
                break;
        }

        return true;
    }

    public void confirmReset(){
        if(chonometerStatus == STARTED)
            chronometerControl(STOP);

        new MaterialDialog.Builder(this)
                .title(R.string.util_advise_title)
                .theme(Theme.DARK)
                .content(R.string.util_confirm_reset_chronometer)
                .positiveText(R.string.ok)
                .negativeText(R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        chronometerControl(RESET);
                    }
                })
                .show();
    }

    //Controlador do cronometro
    private void chronometerControl(int status){
        Log.d(Util.DEGUB_NAME, "System: " + SystemClock.elapsedRealtime() + " Chronometer base: " + chronometer.getBase());

        switch (status){
            case START:
                //Inicia o cronometro com a base de tempo sendo a mesma do sistema
                chronometer.setBase(SystemClock.elapsedRealtime());

                chronometer.start();
                fab.setImageDrawable(ic_pause);

                chonometerStatus = STARTED;

                Log.d(Util.DEGUB_NAME, "Start");
                break;

            case STOP:
                //Para o cronometro
                chronometer.stop();
                fab.setImageDrawable(ic_play);

                chonometerStatus = PAUSED;

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
                fab.setImageDrawable(ic_pause);

                chonometerStatus = STARTED;

                Log.d(Util.DEGUB_NAME, "Restart");
                break;

            case RESET:
                //Reseta o cronometro
                chronometer.stop();
                chronometer.setBase(SystemClock.elapsedRealtime());
                fab.setImageDrawable(ic_play);

                chonometerStatus = STOPED;

                Log.d(Util.DEGUB_NAME, "Reset");
                break;
        }
    }
}
