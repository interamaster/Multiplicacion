package jrdv.mio.com.entrenatablasmultiplicacion;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by joseramondelgado on 21/10/16.
 */

public class CustomListAdapterPokemo extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
   // private final Integer[] imgid;

    //mio para pokemonsimagenes
    private final ArrayList<Integer> imagenPokemons;

    //para animacion
    private int lastPosition = -1;


    public CustomListAdapterPokemo(Activity context, String[] itemname, ArrayList<Integer> imagenPokemons) {
        super(context, R.layout.new_pokemonlist, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.imagenPokemons = imagenPokemons;
        //this.imgid=imgid;
    }







    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.new_pokemonlist, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
       // TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(itemname[position]);
        //imageView.setImageResource(imgid[position]);
        imageView.setImageResource(imagenPokemons.get(position));

       // extratxt.setText("Description "+itemname[position]);


        //aqui la animacion:http://yasirameen.com/2015/09/card-style-listview-with-google-like-animation/

        Animation animation = AnimationUtils.loadAnimation(getContext(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        rowView.startAnimation(animation);
        lastPosition = position;

        return rowView;

    };
}