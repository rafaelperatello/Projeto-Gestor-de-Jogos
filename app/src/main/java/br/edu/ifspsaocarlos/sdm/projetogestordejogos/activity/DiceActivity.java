package br.edu.ifspsaocarlos.sdm.projetogestordejogos.activity;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import br.edu.ifspsaocarlos.sdm.projetogestordejogos.R;

public class DiceActivity extends AppCompatActivity {
    private Drawable face_1, face_2, face_3, face_4, face_5, face_6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setFaces();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
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

    private void setFaces(){
        face_1 = getResources().getDrawable(R.drawable.dice1);
        face_2 = getResources().getDrawable(R.drawable.dice2);
        face_3 = getResources().getDrawable(R.drawable.dice3);
        face_4 = getResources().getDrawable(R.drawable.dice4);
        face_5 = getResources().getDrawable(R.drawable.dice5);
        face_6 = getResources().getDrawable(R.drawable.dice6);
    }

}
