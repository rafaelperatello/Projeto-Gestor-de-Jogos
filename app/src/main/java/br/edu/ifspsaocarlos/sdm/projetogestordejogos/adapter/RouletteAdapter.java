package br.edu.ifspsaocarlos.sdm.projetogestordejogos.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import br.edu.ifspsaocarlos.sdm.projetogestordejogos.model.Roulette;

public class RouletteAdapter extends BaseAdapter implements View.OnClickListener {
    Activity activity;
    ArrayList<Roulette> values;
    Resources res;
    private static LayoutInflater inflater = null;

    public RouletteAdapter(Activity activity, ArrayList<Roulette> values, Resources res){
        this.activity = activity;
        this.values = values;
        this.res = res;

        inflater = ( LayoutInflater )this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(data.size()<=0)
            return 1;
        return data.size()
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public void onClick(View v) {

    }
}
