package jrdv.mio.com.entrenatablasmultiplicacion;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.builder.AnimateGifMode;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import static android.content.ContentValues.TAG;
import static jrdv.mio.com.entrenatablasmultiplicacion.LoginPadActivity.PREFS_NAME;
import static jrdv.mio.com.entrenatablasmultiplicacion.LoginPadActivity.PREF_BOOL_NINOYAOK;

public class ajustesActivity extends Activity {

    private ImageView GifVew;
    private ImageView PokemonAnimadoView;

    public static int TablaMaximaelegida;//la hago public static para poder accerdr a ellas desde otras class
    public static  final String PREF_TablaMAximaElegida="PrefTablaMax";
    public static  final String PREF_NOMBRE_NINO="nombrenino";
    private String NombreNinoElegido;
    private boolean pulsadoPokemon=false;
    private int vecespulsadoPokemon=0;

    //para hablar

    private TextToSpeech textToSpeech;
    private String saludoInicial;

    //para desahbilitAR EL BACKBUTTON

    private  boolean doubleBackToExitPressedOnce = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////mio///////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //definios quien es Gifview

        GifVew =(ImageView)findViewById(R.id.picachu_gif);
        PokemonAnimadoView=(ImageView)findViewById(R.id.pikachusipulsasView);

        ///animamos el pokemon!!!!


        Ion.with(GifVew)
                .error(R.drawable.rosa_1)
                .animateGif(AnimateGifMode.ANIMATE)
                // .load("android.resource://[packagename]" + R.drawable.optinscreen_map)
                .load("android.resource://jrdv.mio.com.entrenatablasmultiplicacion/" + R.drawable.pokeball_animada4);



        //recuperamos el valor deTablaMax si habia de antes y si no le damos 1

        SharedPreferences pref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        int tablaMaxEnPref=pref.getInt(PREF_TablaMAximaElegida,0);//por defecto vale 0

        Log.d(TAG, "PREF_TablaMAximaElegida:" +  tablaMaxEnPref);


        //recupoeramos el nombre delk niño si tenia si no cambia el texto de wellcome


        NombreNinoElegido= pref.getString(PREF_NOMBRE_NINO,"NONAME");

        Log.d(TAG, "PREF_NOMBRE_NINO:" +  NombreNinoElegido);

          saludoInicial="Hola , "+NombreNinoElegido +" Quieres aumentar la dificultad? , asi me gusta elije una nueva tabla , o si quieres cambiar tu nombre , pulsa de nuevo " +
                  "en microfono pero perderas todos tus pokemons";

        if (NombreNinoElegido.equals("NONAME")){
            //si no tiene nombre aun del niño:

            saludoInicial="Hola ,  aun  no se quien eres , pulsa en microfono y dime tu nombre";
        }
        //empieza a hablar



        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    Locale locale = new Locale("es", "ES");

                    int result = textToSpeech.setLanguage(locale);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    }

                    speak(saludoInicial);

                } else {
                    Log.e("TTS", "Initilization Failed!");
                }
            }
        });

    }

//The speak() method takes a String parameter, which is the text you want Android to speak.
    private void speak(String text){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }else{
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

//After speak() create another method to stop the TextToSpeech service when a user closes the app:

    @Override
    public void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }


    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Pulsa icono verde para empezar o de nuevo atras para salir", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }



    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////PULSADO UN BOTON MIC/////////////////////////////////////////////////////////
    //////////////////////////////////////https://www.sitepoint.com/using-android-text-to-speech-to-create-a-smart-assistant/////// ////////////////

    public void MicPulsado(View view) {


        Log.d(TAG, "pulsado MIC"  );

        textToSpeech.stop();


        listen();

    }

    /*
    This method starts the listening activity which displays as a dialog with a text prompt.
    The language of the speech is taken from the device, via the Locale.getDefault() method.

    The startActivityForResult(i, 100) method waits for the current activity to return a result.
    100 is a random code attached to the started activity, and can be any number that suits your use case.
     When a result returns from the started activity, it contains this code and uses it to differentiate multiple results from each other.
     */
    private void listen(){
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Di tu nombre!!!");

        try {
            startActivityForResult(i, 100);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(ajustesActivity.this, "Your device doesn't support Speech Recognition", Toast.LENGTH_SHORT).show();
            //si no es compatible pasamos del nombre y le ponemo ARTISTA

            NombreNinoElegido="Artista";

            //y lo guardamos

            //guardamos el nombre elegido
            SharedPreferences pref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            // We need an editor object to make changes
            SharedPreferences.Editor edit = pref.edit();
            edit.putString(PREF_NOMBRE_NINO, NombreNinoElegido);

            //y empezamos de 0 con los puntos!!!
            edit.putInt(LoginPadActivity.PREF_PUNTOS_PARA_VER_POKEMONS, 0);

            // Commit the changes
            edit.commit();

            Log.d(TAG, "niño ya tiene nombre "+NombreNinoElegido);
        }
    }

/*
This method catches every result coming from an activity and uses the requestCode for the speech recognizer result.
If requestCode is equal to 100,resultCode equals OK and the data from this result is not null.
You get the result string from res.get(0)

 */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> res = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String inSpeech = res.get(0);
                recognition(inSpeech);
            }
        }
    }


    private void recognition(String text){
        Log.e("ha dicho",""+text);

        //Toast.makeText(ajustesActivity.this, "has dicho: "+text, Toast.LENGTH_SHORT).show();

        //le ponemos ese nombre:

        NombreNinoElegido=text;


        //guardamos el nombre elegido
        SharedPreferences pref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        // We need an editor object to make changes
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(PREF_NOMBRE_NINO, NombreNinoElegido);

        //y empezamos de 0 con los puntos!!!
        edit.putInt(LoginPadActivity.PREF_PUNTOS_PARA_VER_POKEMONS, 0);


        // Commit the changes
        edit.commit();

        Log.d(TAG, "niño ya tiene nombre "+NombreNinoElegido);

        speak("De acuerdo " + NombreNinoElegido +" ya sé tu nombre");





    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////PULSADO UN BOTON DONE/////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void DonePulsado(View view) {

        Log.d(TAG, "pulsado DONE"  );




        if (TablaMaximaelegida>=1 && !NombreNinoElegido.equals("NONAME")){


            speak("Okei"+ NombreNinoElegido);




            //guardamos  el PREF_BOOL_NINOYAOK a true!!!
            SharedPreferences pref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            // We need an editor object to make changes
            SharedPreferences.Editor edit = pref.edit();
            edit.putBoolean(PREF_BOOL_NINOYAOK, true);


            // Commit the changes
            edit.commit();

           // Log.d(TAG, "niño ya ok:"+PREF_BOOL_NINOYAOK);


            finish();
        }

        else
                {
                        if (NombreNinoElegido.equals("NONAME"))
                            speak("Lo siento , pero no me dijiste tu nombre , pulsa en microfono y dimelo");


                        if (TablaMaximaelegida==0)
                            speak("Lo siento , te falta elegir el nivel maximo de la tablas que te sabes");


                 }


    }



    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////PULSADO UN POKEMON/////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void PulsadoPokemon(View view) {

        Log.d(TAG, "pulsado POKEMON"  );

        //que vibre al pulsar


        view. performHapticFeedback(1,2);

        ///cambiamos de pokemons!!!!

        vecespulsadoPokemon++;

        if(!pulsadoPokemon ) {

            speak("oye , si me sigues pulsando al final te voy a tener que enseñar unos cuantos pokemons");
            pulsadoPokemon=true;

        }

         if(vecespulsadoPokemon>5 && vecespulsadoPokemon<7) {

            // speak("oye , para que voy a explotar");
             //ponemos un sonido

             MediaPlayer mp = MediaPlayer.create(ajustesActivity.this, R.raw.pikachu5);
             mp.start();



         }


        if (vecespulsadoPokemon>=7 && vecespulsadoPokemon<30){



            //creamsos la animacion
            final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

            // Use bounce interpolator with amplitude 0.2 and frequency 20
            BounceInterpolator interpolator = new BounceInterpolator(0.2, 20);
            myAnim.setInterpolator(interpolator);

            //elegimos uno al azar

            Random r = new Random();
            int i1 = r.nextInt(151 - 101) + 101;
            final String str = "p" + String.valueOf(i1);
            PokemonAnimadoView.setImageDrawable
                    (
                            getResources().getDrawable(getResourceID(str, "drawable",
                                    getApplicationContext()))
                    );

            //hacemos visible la PokemoAniamdoiView
            PokemonAnimadoView.setVisibility(View.VISIBLE);


            //ponemos un sonido

            MediaPlayer mp = MediaPlayer.create(ajustesActivity.this, R.raw.pikachu5);
            mp.start();

            PokemonAnimadoView.startAnimation(myAnim);

            //ponemos un listener para que lo haga invisble cuando acabe!!


            myAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {

                    PokemonAnimadoView.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });


        }

    }


    protected final static int getResourceID
            (final String resName, final String resType, final Context ctx)
    {
        final int ResourceID =
                ctx.getResources().getIdentifier(resName, resType,
                        ctx.getApplicationInfo().packageName);
        if (ResourceID == 0)
        {
            throw new IllegalArgumentException
                    (
                            "No resource string found with name " + resName
                    );
        }
        else
        {
            return ResourceID;
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////PULSADO UN BOTON DE TABLA/////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void pulsadoNum(View v) {

        //REFERECCIA  ALOS BOTONES:
        ImageButton button1 = (ImageButton) findViewById(R.id.tabla1);
        ImageButton button2 = (ImageButton) findViewById(R.id.tabla2);
        ImageButton button3 = (ImageButton) findViewById(R.id.tabla3);
        ImageButton button4 = (ImageButton) findViewById(R.id.tabla4);
        ImageButton button5 = (ImageButton) findViewById(R.id.tabla5);
        ImageButton button6 = (ImageButton) findViewById(R.id.tabla6);
        ImageButton button7 = (ImageButton) findViewById(R.id.tabla7);
        ImageButton button8 = (ImageButton) findViewById(R.id.tabla8);
        ImageButton button9 = (ImageButton) findViewById(R.id.tabla9);
        ImageButton button10 = (ImageButton) findViewById(R.id.tabla10);


        //creamsos la animacion
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        BounceInterpolator interpolator = new BounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);




        switch (v.getId()) {
            case R.id.tabla1:
                // do work for this Button


                //cambiamos el src al color verde!!! esta tabla se hara
                button1.setImageResource(R.drawable.verde_1);
                button2.setImageResource(R.drawable.rosa_2);
                button3.setImageResource(R.drawable.rosa_3);
                button4.setImageResource(R.drawable.rosa_4);
                button5.setImageResource(R.drawable.rosa_5);
                button6.setImageResource(R.drawable.rosa_6);
                button7.setImageResource(R.drawable.rosa_7);
                button8.setImageResource(R.drawable.rosa_8);
                button9.setImageResource(R.drawable.rosa_9);
                button10.setImageResource(R.drawable.rosa_10);

                //animate!!!
                button1.startAnimation(myAnim);

                //guardamos el valor elegido

                TablaMaximaelegida=1;
                speak("Has elegido la tabla del 1 ,  eso es muy facil , no?");

                break;
            case R.id.tabla2:
                // do work for this Button


                //cambiamos el src al color verde!!! esta tabla se hara
                button1.setImageResource(R.drawable.verde_1);
                button2.setImageResource(R.drawable.verde_2);
                button3.setImageResource(R.drawable.rosa_3);
                button4.setImageResource(R.drawable.rosa_4);
                button5.setImageResource(R.drawable.rosa_5);
                button6.setImageResource(R.drawable.rosa_6);
                button7.setImageResource(R.drawable.rosa_7);
                button8.setImageResource(R.drawable.rosa_8);
                button9.setImageResource(R.drawable.rosa_9);
                button10.setImageResource(R.drawable.rosa_10);
                //animate!!!
                button1.startAnimation(myAnim);
                button2.startAnimation(myAnim);

                //guardamos el valor elegido

                TablaMaximaelegida=2;

                speak("Has elegido hasta la tabla del 2 ");
                break;
            case R.id.tabla3:
                // do work for this Button

                //cambiamos el src al color verde!!! esta tabla se hara
                button1.setImageResource(R.drawable.verde_1);
                button2.setImageResource(R.drawable.verde_2);
                button3.setImageResource(R.drawable.verde_3);
                button4.setImageResource(R.drawable.rosa_4);
                button5.setImageResource(R.drawable.rosa_5);
                button6.setImageResource(R.drawable.rosa_6);
                button7.setImageResource(R.drawable.rosa_7);
                button8.setImageResource(R.drawable.rosa_8);
                button9.setImageResource(R.drawable.rosa_9);
                button10.setImageResource(R.drawable.rosa_10);
                //animate!!!
                button1.startAnimation(myAnim);
                button2.startAnimation(myAnim);
                button3.startAnimation(myAnim);

                //guardamos el valor elegido

                TablaMaximaelegida=3;
                speak("Has elegido hasta la tabla del 3 ");

                break;
            case R.id.tabla4:
                // do work for this Button

                //cambiamos el src al color verde!!! esta tabla se hara
                button1.setImageResource(R.drawable.verde_1);
                button2.setImageResource(R.drawable.verde_2);
                button3.setImageResource(R.drawable.verde_3);
                button4.setImageResource(R.drawable.verde_4);
                button5.setImageResource(R.drawable.rosa_5);
                button6.setImageResource(R.drawable.rosa_6);
                button7.setImageResource(R.drawable.rosa_7);
                button8.setImageResource(R.drawable.rosa_8);
                button9.setImageResource(R.drawable.rosa_9);
                button10.setImageResource(R.drawable.rosa_10);
                //animate!!!
                button1.startAnimation(myAnim);
                button2.startAnimation(myAnim);
                button3.startAnimation(myAnim);
                button4.startAnimation(myAnim);

                //guardamos el valor elegido

                TablaMaximaelegida=4;
                speak("Has elegido hasta la tabla del 4 ");

                break;
            case R.id.tabla5:
                // do work for this Button

                //cambiamos el src al color verde!!! esta tabla se hara
                button1.setImageResource(R.drawable.verde_1);
                button2.setImageResource(R.drawable.verde_2);
                button3.setImageResource(R.drawable.verde_3);
                button4.setImageResource(R.drawable.verde_4);
                button5.setImageResource(R.drawable.verde_5);
                button6.setImageResource(R.drawable.rosa_6);
                button7.setImageResource(R.drawable.rosa_7);
                button8.setImageResource(R.drawable.rosa_8);
                button9.setImageResource(R.drawable.rosa_9);
                button10.setImageResource(R.drawable.rosa_10);
                //animate!!!
                button1.startAnimation(myAnim);
                button2.startAnimation(myAnim);
                button3.startAnimation(myAnim);
                button4.startAnimation(myAnim);
                button5.startAnimation(myAnim);

                //guardamos el valor elegido

                TablaMaximaelegida=5;

                speak("Has elegido hasta la tabla del 5 ");
                break;
            case R.id.tabla6:
                // do work for this Button

                //cambiamos el src al color verde!!! esta tabla se hara
                button1.setImageResource(R.drawable.verde_1);
                button2.setImageResource(R.drawable.verde_2);
                button3.setImageResource(R.drawable.verde_3);
                button4.setImageResource(R.drawable.verde_4);
                button5.setImageResource(R.drawable.verde_5);
                button6.setImageResource(R.drawable.verde_6);
                button7.setImageResource(R.drawable.rosa_7);
                button8.setImageResource(R.drawable.rosa_8);
                button9.setImageResource(R.drawable.rosa_9);
                button10.setImageResource(R.drawable.rosa_10);
                //animate!!!
                button1.startAnimation(myAnim);
                button2.startAnimation(myAnim);
                button3.startAnimation(myAnim);
                button4.startAnimation(myAnim);
                button5.startAnimation(myAnim);
                button6.startAnimation(myAnim);

                //guardamos el valor elegido

                TablaMaximaelegida=6;
                speak("Has elegido hasta la tabla del 6  , ey , esto ya es mas dificil ");

                break;
            case R.id.tabla7:
                // do work for this Button

                //cambiamos el src al color verde!!! esta tabla se hara
                button1.setImageResource(R.drawable.verde_1);
                button2.setImageResource(R.drawable.verde_2);
                button3.setImageResource(R.drawable.verde_3);
                button4.setImageResource(R.drawable.verde_4);
                button5.setImageResource(R.drawable.verde_5);
                button6.setImageResource(R.drawable.verde_6);
                button7.setImageResource(R.drawable.verde_7);
                button8.setImageResource(R.drawable.rosa_8);
                button9.setImageResource(R.drawable.rosa_9);
                button10.setImageResource(R.drawable.rosa_10);
                //animate!!!
                button1.startAnimation(myAnim);
                button2.startAnimation(myAnim);
                button3.startAnimation(myAnim);
                button4.startAnimation(myAnim);
                button5.startAnimation(myAnim);
                button6.startAnimation(myAnim);
                button7.startAnimation(myAnim);

                //guardamos el valor elegido

                TablaMaximaelegida=7;
                speak("Has elegido hasta la tabla del 7 ");

                break;
            case R.id.tabla8:
                // do work for this Button

                //cambiamos el src al color verde!!! esta tabla se hara
                button1.setImageResource(R.drawable.verde_1);
                button2.setImageResource(R.drawable.verde_2);
                button3.setImageResource(R.drawable.verde_3);
                button4.setImageResource(R.drawable.verde_4);
                button5.setImageResource(R.drawable.verde_5);
                button6.setImageResource(R.drawable.verde_6);
                button7.setImageResource(R.drawable.verde_7);
                button8.setImageResource(R.drawable.verde_8);
                button9.setImageResource(R.drawable.rosa_9);
                button10.setImageResource(R.drawable.rosa_10);
                //animate!!!
                button1.startAnimation(myAnim);
                button2.startAnimation(myAnim);
                button3.startAnimation(myAnim);
                button4.startAnimation(myAnim);
                button5.startAnimation(myAnim);
                button6.startAnimation(myAnim);
                button7.startAnimation(myAnim);
                button8.startAnimation(myAnim);

                //guardamos el valor elegido

                TablaMaximaelegida=8;

                speak("Has elegido hasta la tabla del 8 , guau  , ya mismo te las sabes todas ,  demuestralo ");

                break;
            case R.id.tabla9:
                // do work for this Button

                //cambiamos el src al color verde!!! esta tabla se hara
                button1.setImageResource(R.drawable.verde_1);
                button2.setImageResource(R.drawable.verde_2);
                button3.setImageResource(R.drawable.verde_3);
                button4.setImageResource(R.drawable.verde_4);
                button5.setImageResource(R.drawable.verde_5);
                button6.setImageResource(R.drawable.verde_6);
                button7.setImageResource(R.drawable.verde_7);
                button8.setImageResource(R.drawable.verde_8);
                button9.setImageResource(R.drawable.verde_9);
                button10.setImageResource(R.drawable.rosa_10);
                //animate!!!
                button1.startAnimation(myAnim);
                button2.startAnimation(myAnim);
                button3.startAnimation(myAnim);
                button4.startAnimation(myAnim);
                button5.startAnimation(myAnim);
                button6.startAnimation(myAnim);
                button7.startAnimation(myAnim);
                button8.startAnimation(myAnim);
                button9.startAnimation(myAnim);

                //guardamos el valor elegido

                TablaMaximaelegida=9;

                speak("Has elegido hasta la tabla del 9 , estupendo  ");
                break;
            case R.id.tabla10:
                // do work for this Button

                //cambiamos el src al color verde!!! esta tabla se hara
                button1.setImageResource(R.drawable.verde_1);
                button2.setImageResource(R.drawable.verde_2);
                button3.setImageResource(R.drawable.verde_3);
                button4.setImageResource(R.drawable.verde_4);
                button5.setImageResource(R.drawable.verde_5);
                button6.setImageResource(R.drawable.verde_6);
                button7.setImageResource(R.drawable.verde_7);
                button8.setImageResource(R.drawable.verde_8);
                button9.setImageResource(R.drawable.verde_9);
                button10.setImageResource(R.drawable.verde_10);
                //animate!!!
                button1.startAnimation(myAnim);
                button2.startAnimation(myAnim);
                button3.startAnimation(myAnim);
                button4.startAnimation(myAnim);
                button5.startAnimation(myAnim);
                button6.startAnimation(myAnim);
                button7.startAnimation(myAnim);
                button8.startAnimation(myAnim);
                button9.startAnimation(myAnim);
                button10.startAnimation(myAnim);

                //guardamos el valor elegido

                TablaMaximaelegida=10;

                speak("bueno, bueno , bueno , asi que te la sabes todas no? vamos a ver si es verdad ");

                break;
        }




                       //guardamos la Tabla elegida
                       SharedPreferences pref = getSharedPreferences(LoginPadActivity.PREFS_NAME, Context.MODE_PRIVATE);
                       // We need an editor object to make changes
                      SharedPreferences.Editor edit = pref.edit();
                        edit.putInt(PREF_TablaMAximaElegida, TablaMaximaelegida);


                        // Commit the changes
                       edit.commit();

                      Log.d(TAG, "niño ya eligio tablaMax "+TablaMaximaelegida);



    }



    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
