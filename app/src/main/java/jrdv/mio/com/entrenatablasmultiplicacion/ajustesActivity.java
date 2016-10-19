package jrdv.mio.com.entrenatablasmultiplicacion;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.builder.AnimateGifMode;

import static android.content.ContentValues.TAG;
import static jrdv.mio.com.entrenatablasmultiplicacion.LoginPadActivity.PREFS_NAME;

public class ajustesActivity extends Activity {

    private ImageView GifVew;

    public static int TablaMaximaelegida;//la hago public static para poder accerdr a ellas desde otras class
    public static final String PREF_TablaMAximaElegida="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////mio///////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //definios quien es Gifview

        GifVew =(ImageView)findViewById(R.id.picachu_gif);


        ///animamos el pokemon!!!!


        Ion.with(GifVew)
                .error(R.drawable.rosa_1)
                .animateGif(AnimateGifMode.NO_ANIMATE)
                // .load("android.resource://[packagename]" + R.drawable.optinscreen_map)
                .load("android.resource://jrdv.mio.com.entrenatablasmultiplicacion/" + R.drawable.pokemo_hablando);

        //recuperamos el valor deTablaMax si habia de antes y si no le damos 1

        SharedPreferences pref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        int tablaMaxEnPref=pref.getInt(PREF_TablaMAximaElegida,0);//por defecto vale 0

        Log.d(TAG, "PREF_TablaMAximaElegida:" +  tablaMaxEnPref);

    }


    //deshabilitar boton back 1 vez
    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////PULSADO UN BOTON MIC/////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void MicPulsado(View view) {


        Log.d(TAG, "pulsado MIC"  );
    }



    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////PULSADO UN BOTON DONE/////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void DonePulsado(View view) {

        Log.d(TAG, "pulsado DONE"  );
    }



    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////PULSADO UN POKEMON/////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void PulsadoPokemon(View view) {

        Log.d(TAG, "pulsado POKEMON"  );

        ///animamos el pokemon!!!!


        Ion.with(GifVew)
                .error(R.drawable.rosa_1)
                .animateGif(AnimateGifMode.ANIMATE)
                // .load("android.resource://[packagename]" + R.drawable.optinscreen_map)
                .load("android.resource://jrdv.mio.com.entrenatablasmultiplicacion/" + R.drawable.pokemo_hablando);

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


        ///animamos el pokemon!!!!


        Ion.with(GifVew)
                .error(R.drawable.rosa_1)
                .animateGif(AnimateGifMode.ANIMATE_ONCE)
                // .load("android.resource://[packagename]" + R.drawable.optinscreen_map)
                .load("android.resource://jrdv.mio.com.entrenatablasmultiplicacion/" + R.drawable.pokemo_hablando);



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

                break;
        }




                       //guardamos la Tabla elegida
                       SharedPreferences pref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
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
