package br.edu.ifspsaocarlos.sdm.projetogestordejogos.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import java.util.ArrayList;
import java.util.Random;

import br.edu.ifspsaocarlos.sdm.projetogestordejogos.R;
import br.edu.ifspsaocarlos.sdm.projetogestordejogos.adapter.RouletteAdapter;
import br.edu.ifspsaocarlos.sdm.projetogestordejogos.model.Roulette;
import br.edu.ifspsaocarlos.sdm.projetogestordejogos.util.Util;

public class RouletteActivity extends AppCompatActivity {
    private ArrayList<Roulette> roulette;
    private ArrayList<Integer> valuesBuffer;
    private RouletteAdapter rouletteAdapter;
    private ListView list;
    private boolean showColorInCard = false;
    private int numbersQuantity = 100;
    private int lastSorted = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roulette);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        list = (ListView) findViewById(R.id.listViewRoullete);
        list.setDivider(null);
        list.setDividerHeight(0);
        list.setPadding(0, 0, 0, 0);

        loadTable();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.roulette_activity_bar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;

            case R.id.action_setting:
                configSettings();
                break;
            default:
                break;
        }

        return true;
    }

    private void configSettings(){
        MaterialDialog mdialog = new MaterialDialog.Builder(this)
                .title(R.string.user_config_activity_name)
                .theme(Theme.DARK)
                .customView(R.layout.activity_settings_config, true)
                .positiveText(R.string.ok)
                .negativeText(R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Log.d(Util.DEGUB_NAME, "Botão Confirmar");
                        RelativeLayout text = (RelativeLayout) dialog.getCustomView();

                        EditText editQuantity = (EditText) text.getChildAt(0);
                        CheckBox checkCard = (CheckBox) text.getChildAt(1);

                        int valor = 0;
                        String valorToPase = editQuantity.getText().toString();

                        try {
                            valor = Integer.parseInt(valorToPase);
                        } catch (Exception e) {

                        }

                        saveSettings(valor, checkCard.isChecked());
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

        EditText editQuantity = (EditText) text.getChildAt(0);
        CheckBox checkCard = (CheckBox) text.findViewById(R.id.checkBoxShowCard);

        editQuantity.setText("" + numbersQuantity);
        checkCard.setChecked(showColorInCard);

    mdialog.show();
    }

    private void saveSettings(int value, boolean card){
        Log.d(Util.DEGUB_NAME, "valor: " + value + " showCard: " + card);

        SharedPreferences sharedpreferences = getSharedPreferences(Util.APP_SHARED_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putInt(Util.ROULETTE_QUANTITY, value);
        editor.putBoolean(Util.ROULETTE_SHOW_CARD, card);
        editor.commit();

        loadTable();
    }

    private void loadTable(){
        SharedPreferences sharedpreferences = getSharedPreferences(Util.APP_SHARED_FILE, Context.MODE_PRIVATE);

        numbersQuantity = sharedpreferences.getInt(Util.ROULETTE_QUANTITY, -1);
        if (numbersQuantity == -1) {
            numbersQuantity = 100;
        }
        if (numbersQuantity == 0) {
            numbersQuantity = 1;
        }

        showColorInCard = sharedpreferences.getBoolean(Util.ROULETTE_SHOW_CARD, false);

        valuesBuffer = fillValuesBuffer(numbersQuantity);
        roulette = fillRoulette(numbersQuantity);

        rouletteAdapter = new RouletteAdapter(this, roulette, showColorInCard);

        list.setAdapter(rouletteAdapter);
    }



    private void sort(){
        int sorted = sortNumber();

        if(lastSorted != -1){
            roulette.get(lastSorted).setImageSelected(0);
        }
        lastSorted = sorted;

        roulette.get(sorted).setImageSelected(R.drawable.ic_label);
        rouletteAdapter.notifyDataSetChanged();

        list.smoothScrollToPositionFromTop(sorted, 0, Util.ANIMDURATION);
    }

    private ArrayList<Roulette> fillRoulette(int size){
        ArrayList<Roulette> numbers = new ArrayList<>();

        boolean toggleBackgroung = false;

        for (int i = 0; i < size; i++){
            int color;
            int value = valuesBuffer.get(i);

            if (value != 0){
                if (toggleBackgroung){
                    color = ContextCompat.getColor(this, R.color.roulette_red);
                    toggleBackgroung = false;
                }
                else {
                    color = ContextCompat.getColor(this, R.color.roulette_black);
                    toggleBackgroung = true;
                }
            }
            else{
                color = ContextCompat.getColor(this, R.color.roulette_green);
            }

            numbers.add(new Roulette(value, color, R.drawable.roulette));
        }
        return numbers;
    }


    private ArrayList<Integer> fillValuesBuffer(int size){
        ArrayList<Integer> numbers = new ArrayList<>();

        while (numbers.size() < size) {
            int random = sortNumber();
            if (!numbers.contains(random))
                numbers.add(random);
        }

        return numbers;
    }

    private int sortNumber(){
        Random gerador = new Random();
        int number = gerador.nextInt(numbersQuantity);

        Log.d(Util.DEGUB_NAME, "sorted number: " + number);

        return number;
    }
}
