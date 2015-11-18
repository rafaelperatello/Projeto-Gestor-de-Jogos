package br.edu.ifspsaocarlos.sdm.projetogestordejogos.activity;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.util.Random;
import br.edu.ifspsaocarlos.sdm.projetogestordejogos.R;
import br.edu.ifspsaocarlos.sdm.projetogestordejogos.util.Util;

public class DiceActivity extends AppCompatActivity {
    private Drawable face_1, face_2, face_3, face_4, face_5, face_6;
    private ImageView dice_1, dice_2, dice_3, dice_4;
    private LinearLayout table;
    AnimationDrawable animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        table = (LinearLayout) findViewById(R.id.linearLayoutTable);
        dice_1 = (ImageView) findViewById(R.id.imageViewDice1);
        dice_2 = (ImageView) findViewById(R.id.imageViewDice2);
        dice_3 = (ImageView) findViewById(R.id.imageViewDice3);
        dice_4 = (ImageView) findViewById(R.id.imageViewDice4);

        loadFaces();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortDice();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dice_activity_bar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;

            default:
                break;
        }

        return true;
    }

    //Sorteia os dados
    private void sortDice() {
        setAnimationOnDice(dice_1);
        setAnimationOnDice(dice_2);
        setAnimationOnDice(dice_3);
        setAnimationOnDice(dice_4);
    }

    //Gera uma animação randomica para os dados
    private void setAnimationOnDice(ImageView dice) {
        animation = new AnimationDrawable();
        animation.addFrame(getFace(sortNumber()), Util.ANIMDURATION);
        animation.addFrame(getFace(sortNumber()), Util.ANIMDURATION);
        animation.addFrame(getFace(sortNumber()), Util.ANIMDURATION);
        animation.setOneShot(true);

        dice.setImageDrawable(animation);
        animation.start();
    }

    //Retorna a face de acordo com o numero sorteado
    private Drawable getFace(int face) {
        Drawable faceDrawable = face_1;

        switch (face) {
            case 1:
                faceDrawable = face_1;
                break;

            case 2:
                faceDrawable = face_2;
                break;

            case 3:
                faceDrawable = face_3;
                break;

            case 4:
                faceDrawable = face_4;
                break;

            case 5:
                faceDrawable = face_5;
                break;

            case 6:
                faceDrawable = face_6;
                break;
        }

        return faceDrawable;
    }

    //Gerador de número randomico
    private int sortNumber() {
        Random gerador = new Random();
        int number = gerador.nextInt(6) + 1;

        Log.d(Util.DEGUB_NAME, "sorted number: " + number);

        return number;
    }

    //Carrega os drawables do dado
    private void loadFaces() {
        face_1 = getResources().getDrawable(R.drawable.dice1);
        face_2 = getResources().getDrawable(R.drawable.dice2);
        face_3 = getResources().getDrawable(R.drawable.dice3);
        face_4 = getResources().getDrawable(R.drawable.dice4);
        face_5 = getResources().getDrawable(R.drawable.dice5);
        face_6 = getResources().getDrawable(R.drawable.dice6);
    }

}
