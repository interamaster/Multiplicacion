package jrdv.mio.com.entrenatablasmultiplicacion;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Random;

import static android.content.ContentValues.TAG;
import static jrdv.mio.com.entrenatablasmultiplicacion.LoginPadActivity.PREFS_NAME;

public class PokedexActivity extends Activity  implements CustomListAdapterPokemo.customButtonListener {


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

            "Weepinbell",
            "Victreebel",
            "Tentacool",
            "Tentacruel",
            "Geodude",
            "Graveler",
            "Golem",
            "Ponyta",
            "Rapidash",
            "Slowpoke",
            "Slowbro",
            "Magnemite",
            "Magneton",
            "Farfetchd",
            "Doduo",
            "Dodrio",
            "Seel",
            "Dewgong",
            "Grimer",
            "Muk",
            "Shellder",
            "Cloyster",
            "Gastly"
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
    private MediaPlayer mp;

    //para los puntos

    private int puntospokemo;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    //para la barrade pokemos

    private IconRoundCornerProgressBar progress2;

    //PARA NOMBRE NINO A COMPARTIR


    private String NombreNinoElegido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex);



        //rellenamos el array copn los pokemos png segun los puntos:




        SharedPreferences pref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();

        NombreNinoElegido= pref.getString(ajustesActivity.PREF_NOMBRE_NINO,"NONAME");

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
        //para el custom clcik del boton:
        adapter.setCustomButtonListner(PokedexActivity.this);
        list = (ListView) findViewById(R.id.list);



        list.setAdapter(adapter);


        /*
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////ESTE LSITENER NO FUNCIONA SI HAY UN BOTON EN LA CELDA/////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////SE HACE IMPLEMENTADO  implements CustomListAdapterPokemo.customButtonListener / ////////////////////////////////////////////////////////
        ///////////////////////////////////////Y SUS 2 FUNCIONES UNA PARA DETECTAR EL CLICK EN IMAGEN Y OTRO EN BOTON SHARE///////////////////////
        ///////////////////////////////////////////onImageClickListner Y onButtonClickListner//////////////////////////////////////
         /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                //String Slecteditem = itemname[+];
                //Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
                ///en vez de un toast va a hacer su sonido que se llama igual que el nombre de la imagen:
                int posicionmas1 = ++position;

                String nombreimagen = "p" + posicionmas1;
                //Toast.makeText(getApplicationContext(), nombreimagen, Toast.LENGTH_SHORT).show();


                //MediaPlayer mp = MediaPlayer.create(PokedexActivity.this, R.raw.pikachu5);

                //sacamos el int del resource del mp3 solo si esta en los puntos

                if (position <= puntospokemo) {

                    int sonidomp3 = getResourceID(nombreimagen, "raw", getApplicationContext());

                    if (sonidomp3!=0) {

                        //solo sonido si tiene
                        mp = MediaPlayer.create(PokedexActivity.this, sonidomp3);
                        mp.start();
                    }

                    }
                }
            }

            );
*/



        //rellenamos la progresbar

        progress2 = (IconRoundCornerProgressBar) findViewById(R.id.progress_bar);
        // progress2.setProgressColor(Color.parseColor("#56d2c2"));
        // progress2.setProgressBackgroundColor(Color.parseColor("#757575"));
        progress2.setIconBackgroundColor(Color.parseColor("#38c0ae"));

        progress2.setMax(151);
        progress2.setProgress(puntospokemo);

        //TODO lo hacermo animado:
        /*
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 100, 0);
        animation.setDuration(3500); // 3.5 second
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();

        ObjectAnimator animation = ObjectAnimator.ofInt(progress2, "setProgress", 0, puntospokemo);
        animation.setDuration(1000); // 1 second
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
        */


        //cada vez el icono seraun pokemon al azar
        //progress2.setIconImageResource(R.drawable.p21);
        Random r = new Random();
        int i1 = r.nextInt(151 - 1) + 1;

        String nombreimagenpokemonazar = "p" + i1;

        progress2.setIconImageResource(getResourceID(nombreimagenpokemonazar, "drawable",  getApplicationContext()));

        //actualziamos colores de la barra

        updateProgressTwoBarColor();


        //con esto fuerzo a que re-dibuje y asi actualizae la progressbar!!!

        progress2.invalidate();

        //avisamos del punto conseguido!!!

        if(puntospokemo!=151) {

            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialogalertlayout);
            dialog.setTitle(NombreNinoElegido);

            TextView textView = (TextView) dialog.findViewById(R.id.dialogtext);
            textView.setText("YA SOLO TE QUEDAN " + (151 - puntospokemo) + "!!!");

            ImageButton btnExit = (ImageButton) dialog.findViewById(R.id.btnExit);
            btnExit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            // show dialog on screen
            dialog.show();

        }

        else{
            //TODO si ya tienes los 151 eres un maquina!!!!!

        }


        }

    public void SalirPokedex(View view) {

        finish();
    }


    protected final static int getResourceID (final String resName, final String resType, final Context ctx) {
        final int ResourceID =  ctx.getResources().getIdentifier(resName, resType, ctx.getApplicationInfo().packageName);
        if (ResourceID == 0) {



            //en vez de una excepcion que lo ponga en el log solo

            Log.e("INFO", "ojo no existe el resource: " + resName);
            return 0;


        } else {
            return ResourceID;
        }


    }



    @Override
    protected void onRestart() {
        super.onRestart();

        /*
        //rellenamos la progresbar

        progress2 = (IconRoundCornerProgressBar) findViewById(R.id.progress_bar);
        progress2.setProgressColor(Color.parseColor("#56d2c2"));
        progress2.setProgressBackgroundColor(Color.parseColor("#757575"));
        progress2.setIconBackgroundColor(Color.parseColor("#38c0ae"));

        progress2.setMax(151);
        progress2.setProgress(puntospokemo);
        //progress2.setIconImageResource(R.drawable.p21);
        Random r = new Random();
        int i1 = r.nextInt(151 - 1) + 1;

        String nombreimagenpokemonazar = "p" + i1;

        progress2.setIconImageResource(getResourceID(nombreimagenpokemonazar, "drawable",  getApplicationContext()));

        //actualziamos colores de la barra

        updateProgressTwoBarColor();


        //avisamos del punto que te quedan!!!

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogalertlayout);
        dialog.setTitle(NombreNinoElegido);

        TextView textView = (TextView) dialog.findViewById(R.id.dialogtext);
        textView.setText("YA SOLO TE QUEDAN "+(151-puntospokemo)+"!!!");

        ImageButton btnExit = (ImageButton) dialog.findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // show dialog on screen
        dialog.show();

*/
    }


    private void updateProgressTwoBarColor() {
        float progress = progress2.getProgress();
        if(progress <= 30) {
            progress2.setProgressColor(getResources().getColor(R.color.custom_progress_red_progress));
            progress2.setIconBackgroundColor(getResources().getColor(R.color.custom_progress_red_progress));
        } else if(progress > 30 && progress <= 100) {
            progress2.setProgressColor(getResources().getColor(R.color.custom_progress_orange_progress));
            progress2.setIconBackgroundColor(getResources().getColor(R.color.custom_progress_orange_progress));
        } else if(progress > 100) {
            progress2.setProgressColor(getResources().getColor(R.color.custom_progress_green_progress));
            progress2.setIconBackgroundColor(getResources().getColor(R.color.custom_progress_green_progress));
        }


    }

    @Override
    public void onButtonClickListner(int position) {
        Log.e("INFO", "pulsado share en pokemon: " +  position);

        //aqui el pokemo share


        String nombreimagenpokemon = "p" + position;




        //progress2.setIconImageResource(getResourceID(nombreimagenpokemonazar, "drawable",  getApplicationContext()));

        Uri imageUri = Uri.parse("android.resource://" + getPackageName()+ "/drawable/" + nombreimagenpokemon);

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "HOLA "+NombreNinoElegido +" Lleva conseguidos :" +puntospokemo +" POKEMONS EN POKEMULTIPLICACION!!");
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/html");
        //shareIntent.setType("text/html");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "send"));
    }

    @Override
    public void onImageClickListner(int position) {

        Log.e("INFO", "pulsado IMAGEN en pokemon: " +  position);

        //aqui el sonido


        int posicionmas1 = ++position;

        String nombreimagen = "p" + posicionmas1;
        //Toast.makeText(getApplicationContext(), nombreimagen, Toast.LENGTH_SHORT).show();


        //MediaPlayer mp = MediaPlayer.create(PokedexActivity.this, R.raw.pikachu5);

        //sacamos el int del resource del mp3 solo si esta en los puntos

        if (position <= puntospokemo) {

            int sonidomp3 = getResourceID(nombreimagen, "raw", getApplicationContext());

            if (sonidomp3!=0) {

                //solo sonido si tiene
                mp = MediaPlayer.create(PokedexActivity.this, sonidomp3);
                mp.start();
            }

        }

    }
}