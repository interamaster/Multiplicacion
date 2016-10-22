package jrdv.mio.com.entrenatablasmultiplicacion;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static jrdv.mio.com.entrenatablasmultiplicacion.LoginPadActivity.PREFS_NAME;

public class PokedexActivity extends Activity {


    ListView list;
    String[] itemname = {
            "Bulbasaur",
            "Ivysaur",
            "Venusaur",
            "Charmander",
            "Charmeleon",
            "Charizard",
            "Squirtle",
            "Wartortle",
            "Blastoise",
            "Caterpie",
            "Metapod",
            "Butterfree",
            "Weedle",
            "Kakuna",
            "Beedrill",
            "Pidgey",
            "Pidgeotto",
            "Pidgeot",
            "Rattata",
            "Raticate",
            "Spearow",
            "Fearow",
            "Ekans",

            "Arbok",
            "Pikachu",
            "Raichu",
            "Sandshrew",
            "Sandslash",
            "Nidoran?",
            "Nidorina",
            "Nidoqueen",
            "Nidoran?",
            "Nidorino",
            "Nidoking",
            "Clefairy",
            "Clefable",
            "Vulpix",
            "Ninetales",
            "Jigglypuff",
            "Wigglytuff",
            "Zubat",
            "Golbat",
            "Oddish",
            "Gloom",
            "Vileplume",
            "Paras",

            "Parasect",
            "Venonat",
            "Venomoth",
            "Diglett",
            "Dugtrio",
            "Meowth",
            "Persian",
            "Psyduck",
            "Golduck",
            "Mankey",
            "Primeape",
            "Growlithe",
            "Arcanine",
            "Poliwag",
            "Poliwhirl",
            "Poliwrath",
            "Abra",
            "Kadabra",
            "Alakazam",
            "Machop",
            "Machoke",
            "Machamp",
            "Bellsprout",

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

    //para los puntos

    private int puntospokemo ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex);


        //rellenamos el array copn los pokemos png segun los puntos:

        //TODO recupermos los puntos(aunque se podria quitar ya se recuperanen oncreate!!)
        SharedPreferences pref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();

          puntospokemo = pref.getInt(LoginPadActivity.PREF_PUNTOS_PARA_VER_POKEMONS, 0);//por defecto vale 0

        Log.e("INFO", "puntos par al PokeDexActivity: " + puntospokemo);




        String nombreimagenpokemon;

        final ArrayList<Integer> imagenPokemons = new ArrayList<>();

        for (int n = 1; n <= puntospokemo; n++) {


            nombreimagenpokemon = "p" + String.valueOf(n);

            Log.d(TAG, "pokemon a poner " + nombreimagenpokemon);


            imagenPokemons.add(getResourceID(nombreimagenpokemon, "drawable",
                    getApplicationContext()));

        }

        //el resto de pokemon sin puntos los pone deotra manera:


        for (int n = puntospokemo; n <= 151; n++) {

            //TODO poner icono
            imagenPokemons.add(R.drawable.done_icon);

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
                int posicionmas1 = ++position;

                String nombreimagen = "p" + posicionmas1;
                //Toast.makeText(getApplicationContext(), nombreimagen, Toast.LENGTH_SHORT).show();


                //MediaPlayer mp = MediaPlayer.create(PokedexActivity.this, R.raw.pikachu5);

                //sacamos el int del resource del mp3 solo si esta en los puntos

                if (position <= puntospokemo){

                    int sonidomp3 = getResourceID(nombreimagen, "raw", getApplicationContext());
                    mp = MediaPlayer.create(PokedexActivity.this, sonidomp3);
                    mp.start();

                }
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