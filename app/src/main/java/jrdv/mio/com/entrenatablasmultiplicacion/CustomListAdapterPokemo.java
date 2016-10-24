package jrdv.mio.com.entrenatablasmultiplicacion;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by joseramondelgado on 21/10/16.
 */

public class CustomListAdapterPokemo extends ArrayAdapter<String> {
    //para los puntos

    private int puntospokemo;


    //This method shares the context of the MainActivity with the interface to use this.

    //Now make an interface in ListAdapter, this interface works like a listener for the button.

    customButtonListener customListner;

    public interface customButtonListener {
        public void onButtonClickListner(int position);
        public void onImageClickListner(int position);
        public void onHangoutClickListner(int position);
    }

    //And make a method to set the instance of the activity that has a ListView.
    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }




    private final Activity context;
    private final String[] itemname;
   // private final Integer[] imgid;

    //mio para pokemonsimagenes
    private final ArrayList<Integer> imagenPokemons;

    //para animacion
    private int lastPosition = -1;


    public CustomListAdapterPokemo(Activity context, String[] itemname, ArrayList<Integer> imagenPokemons, int  puntospokemopasados) {
        super(context, R.layout.new_pokemonlist, itemname);


        this.context=context;
        this.itemname=itemname;
        this.imagenPokemons = imagenPokemons;
        this.puntospokemo=puntospokemopasados;
    }







    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.new_pokemonlist, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);


       // TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(itemname[position]);
        //imageView.setImageResource(imgid[position]);
        imageView.setImageResource(imagenPokemons.get(position));

       // extratxt.setText("Description "+itemname[position]);

        //ahora el boton de share:

        ImageButton butonshare=(ImageButton) rowView.findViewById(R.id.sharepokemon) ;
        //idem para el hangout

        ImageButton hangoutshare = (ImageButton) rowView.findViewById(R.id.hangoutsharepokemon);

        //de moento son invisibles

        butonshare.setVisibility(View.INVISIBLE);
        hangoutshare.setVisibility(View.INVISIBLE);

        /*

        //asi funciona pero no pasa el click ala celda delc listview(y no suena el pokemon)
        butonshare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //...
                Log.e("INFO", "pulsado share en pokemon: " +  position);
            }
        });
        */

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////solo poenmos los botones de share si aplica////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        if (position<puntospokemo) {

            //dlos hacemos visibles

            butonshare.setVisibility(View.VISIBLE);
            hangoutshare.setVisibility(View.VISIBLE);


            //asi creasmo un ainterface y a cada objeto(button e imagen le asignamos un click distinto!:
            //1º)el boton disparara el metodo onButtonClickListner en el PokedexActivity
            butonshare.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (customListner != null) {
                        customListner.onButtonClickListner(position);
                    }

                }
            });


            //2º)la imagen  disparara el metodo onImageClickListner en el PokedexActivity
            imageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (customListner != null) {
                        customListner.onImageClickListner(position);
                    }

                }
            });

            //3º)el boton disparara el metodo onButtonClickListner en el PokedexActivity
            hangoutshare.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (customListner != null) {
                        customListner.onHangoutClickListner(position);
                    }

                }
            });

        }

        //aqui la animacion:http://yasirameen.com/2015/09/card-style-listview-with-google-like-animation/

        Animation animation = AnimationUtils.loadAnimation(getContext(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        rowView.startAnimation(animation);
        lastPosition = position;

        return rowView;



        };





}