package jrdv.mio.com.entrenatablasmultiplicacion;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class PokedexActivity extends Activity {


    ListView list;
    String[] itemname = {
            "Safari",
            "Camera",
            "Global",
            "FireFox",
            "UC Browser",
            "Android Folder",
            "VLC Player",
            "Cold War",
            "Safari",
            "Camera",
            "Global",
            "FireFox",
            "UC Browser",
            "Android Folder",
            "VLC Player",
            "Cold War",
            "Safari",
            "Camera",
            "Global",
            "FireFox",
            "UC Browser",
            "Android Folder",
            "VLC Player",

    };
    /*

    //LOS REELENO EN TIMEPO REAL DESDE DRAWABLES
    Integer[] imgid = {
            R.drawable.p101,
            R.drawable.p102,
            R.drawable.p103,
            R.drawable.p104,
            R.drawable.p105,
            R.drawable.p106,
            R.drawable.p107,
            R.drawable.p108,
    };
*/

    //para el sonido del pokemon
private MediaPlayer mp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex);


        //rellenamos el array copn los pokemos png

        String nombreimagenpokemon;

        final ArrayList<Integer> imagenPokemons = new ArrayList<>();

        for (int n = 1; n <= 23; n++) {


            nombreimagenpokemon = "p" + String.valueOf(n);

            Log.d(TAG, "pokemon a poner " + nombreimagenpokemon);


            imagenPokemons.add(getResourceID(nombreimagenpokemon, "drawable",
                    getApplicationContext()));

        }



        //CustomListAdapterPokemo adapter = new CustomListAdapterPokemo(this, itemname, imgid);
        //new con pokemos
        CustomListAdapterPokemo adapter = new CustomListAdapterPokemo(this, itemname, imagenPokemons);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                //String Slecteditem = itemname[+];
                //Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
                ///en vez de un toast va a hacer su sonido que se llama igual que el nombre de la imagen:
                int posicionmas1=++position;

                String nombreimagen = "p"+posicionmas1;
                Toast.makeText(getApplicationContext(), nombreimagen, Toast.LENGTH_SHORT).show();


                //MediaPlayer mp = MediaPlayer.create(PokedexActivity.this, R.raw.pikachu5);

                //sacamos el int del resource del mp3

                int sonidomp3=getResourceID(nombreimagen, "raw",  getApplicationContext());
                  mp =MediaPlayer.create(PokedexActivity.this, sonidomp3);
                    mp.start();

            }
        });

    }

    public void SalirPokedex(View view) {

        finish();
    }


    protected final static int getResourceID
            (final String resName, final String resType, final Context ctx) {
        final int ResourceID =
                ctx.getResources().getIdentifier(resName, resType,
                        ctx.getApplicationInfo().packageName);
        if (ResourceID == 0) {
            throw new IllegalArgumentException
                    (
                            "No resource string found with name " + resName
                    );
        } else {
            return ResourceID;
        }


    }

}