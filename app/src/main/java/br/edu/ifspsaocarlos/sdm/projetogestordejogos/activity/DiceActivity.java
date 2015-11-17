package br.edu.ifspsaocarlos.sdm.projetogestordejogos.activity;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

import br.edu.ifspsaocarlos.sdm.projetogestordejogos.R;
import util.Util;

public class DiceActivity extends AppCompatActivity {
    private Drawable face_1, face_2, face_3, face_4, face_5, face_6;
    private ImageView imageView;
    AnimationDrawable animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = (ImageView)findViewById(R.id.imageViewDice);

        setFaces();

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

        setMenuColor(menu.findItem(R.id.action_add).getIcon());
        setMenuColor(menu.findItem(R.id.action_remove).getIcon());

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

            case R.id.action_add:
                Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_remove:
                Toast.makeText(this, "Remove", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }

        return true;
    }

    private void sortDice(){
        animation = new AnimationDrawable();
        animation.addFrame(getFace(sortNumber()), Util.ANIMDURATION);
        animation.addFrame(getFace(sortNumber()), Util.ANIMDURATION);
        animation.addFrame(getFace(sortNumber()), Util.ANIMDURATION);

        imageView.setImageDrawable(animation);

        try{
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    animation.start();
                }
            }, 5000);
        } catch (Exception e) {
            Log.d(Util.DEGUB_NAME, "error in animation: " + e);
        }
    }

    private Drawable getFace(int face){
        Drawable faceDrawable = face_1;

        switch (face) {
            case 1:
                faceDrawable = face_1;
                break;

            case 2:
                faceDrawable =  face_2;
            break;

            case 3:
                faceDrawable =  face_3;
            break;

            case 4:
                faceDrawable =  face_4;
            break;

            case 5:
                faceDrawable =  face_5;
            break;

            case 6:
                faceDrawable =  face_6;
            break;
        }

        return faceDrawable;
    }

    private int sortNumber(){
        Random gerador = new Random();
        int number = gerador.nextInt(6) +1;

        Log.d(Util.DEGUB_NAME, "sorted number: " + number);

        return number;
    }

    private void setFaces(){
        face_1 = getResources().getDrawable(R.drawable.dice1);
        face_2 = getResources().getDrawable(R.drawable.dice2);
        face_3 = getResources().getDrawable(R.drawable.dice3);
        face_4 = getResources().getDrawable(R.drawable.dice4);
        face_5 = getResources().getDrawable(R.drawable.dice5);
        face_6 = getResources().getDrawable(R.drawable.dice6);
    }

}
