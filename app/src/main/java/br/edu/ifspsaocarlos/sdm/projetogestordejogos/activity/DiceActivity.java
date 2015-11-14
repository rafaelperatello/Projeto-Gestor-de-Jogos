package br.edu.ifspsaocarlos.sdm.projetogestordejogos.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import br.edu.ifspsaocarlos.sdm.projetogestordejogos.R;

public class DiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);
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

            case R.id.action_play:
                Toast.makeText(this, "Start", Toast.LENGTH_SHORT).show();
                break;

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


}
