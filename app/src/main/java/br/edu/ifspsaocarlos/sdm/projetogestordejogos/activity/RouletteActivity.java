package br.edu.ifspsaocarlos.sdm.projetogestordejogos.activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.edu.ifspsaocarlos.sdm.projetogestordejogos.R;
import br.edu.ifspsaocarlos.sdm.projetogestordejogos.adapter.RouletteAdapter;
import br.edu.ifspsaocarlos.sdm.projetogestordejogos.model.Roulette;

public class RouletteActivity extends AppCompatActivity {
    private ArrayList<Roulette> values;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roulette);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        list = (ListView) findViewById(R.id.listViewRoullete);

        values = new ArrayList<>();
        setValues();
        list.setAdapter(new RouletteAdapter(this, values));

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
        inflater.inflate(R.menu.roulette_activity_bar, menu);

        setMenuColor(menu.findItem(R.id.action_setting).getIcon());

        return super.onCreateOptionsMenu(menu);
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
            default:
                break;
        }

        return true;
    }

    private void setMenuColor(Drawable d){
        if (d != null) {
            d.mutate();
            d.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }
    }

    private void setValues(){
        for (int i = 0; i < 100; i++){
            values.add(new Roulette(i, ContextCompat.getColor(this, R.color.roulette_red), R.drawable.roulette));
        }
    }
}
