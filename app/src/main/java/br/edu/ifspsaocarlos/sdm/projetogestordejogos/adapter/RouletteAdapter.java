package br.edu.ifspsaocarlos.sdm.projetogestordejogos.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import br.edu.ifspsaocarlos.sdm.projetogestordejogos.R;
import br.edu.ifspsaocarlos.sdm.projetogestordejogos.model.Roulette;

public class RouletteAdapter extends BaseAdapter implements View.OnClickListener {
    private Activity activity;
    private ArrayList<Roulette> values;
    private static LayoutInflater inflater = null;

    public RouletteAdapter(Activity activity, ArrayList<Roulette> values){
        this.activity = activity;
        this.values = values;

        inflater = ( LayoutInflater )this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(values.size()<=0)
            return 1;
        else
            return values.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();

        View row = inflater.inflate(R.layout.roulette_row, null);
        holder.textValue =(TextView)row.findViewById(R.id.textViewRowValue);
        holder.textImage =(ImageView)row.findViewById(R.id.ImageViewRow);

        Roulette valor = values.get(position);

        holder.textValue.setText("" + valor.getNumber());
        holder.textImage.setImageResource(valor.getImage());

        return row;
    }

    @Override
    public void onClick(View v) {

    }

    public class Holder
    {
        ImageView textImage;
        TextView textValue;
    }
}
