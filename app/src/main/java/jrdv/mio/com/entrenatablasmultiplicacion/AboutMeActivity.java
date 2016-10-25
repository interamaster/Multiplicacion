package jrdv.mio.com.entrenatablasmultiplicacion;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutMeActivity extends AppCompatActivity   {

    //para el sonido del pokemon
    private MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
//        getSupportActionBar().hide();


        CardView cardView = (CardView) findViewById(R.id.card_view);
        ImageView movie_cover = (ImageView) findViewById(R.id.cover_details);
        TextView movie = (TextView) findViewById(R.id.txt_movie_details);
        //ImageView movie_bg = (ImageView) findViewById(R.id.cover_bg_details);
        TextView textosobremi = (TextView) findViewById(R.id.texto_aboutme);


        //These are lines helping Details_Card To Animate
        //===============================================
        AnimatorSet animationSet = new AnimatorSet();

        //Translating Details_Card in Y Scale
        ObjectAnimator card_y = ObjectAnimator.ofFloat(cardView, View.TRANSLATION_Y, 70);
        card_y.setDuration(2500);
        card_y.setRepeatMode(ValueAnimator.REVERSE);
        card_y.setRepeatCount(ValueAnimator.INFINITE);
        card_y.setInterpolator(new LinearInterpolator());

        //Translating Movie_Cover in Y Scale
        ObjectAnimator cover_y = ObjectAnimator.ofFloat(movie_cover, View.TRANSLATION_Y, 30);
        cover_y.setDuration(3000);
        cover_y.setRepeatMode(ValueAnimator.REVERSE);
        cover_y.setRepeatCount(ValueAnimator.INFINITE);
        cover_y.setInterpolator(new LinearInterpolator());

        animationSet.playTogether(card_y,cover_y);
        animationSet.start();



        textosobremi.setText("Octubre de 2016  para  mi hija Inmaculada!! \n" +
                "Para que este año aprenda las tablas jugando a coleccionar POKEMONS!!!\n"+
                "Consigue POKEMONS  en tu POKEDEX que puedes compartir " +
                "cada vez que vayas acertando multiplicaciones, si te equivocas empieza la serie de aciertos de nuevo \n" +
                 "ES MUY DIFICIL SI LO CONSIGUES ALGUIEN TE DARA UN SUPER PREMIO!!! \n \n" +
                "Gracias a los 2 por existir y la mami por aguantarme jajaja \n \nESTE JUEGO NO ESTARA NUNCA EN GOOGLE PLAY" +
                " PERO SI TE GUSTA PUEDES  COMPARTIRLO CON TUS AMIGOS!!!\n" +
                "SUERTE Y A VER SI ALGUIEN ES CAPAZ DE CONSEGUIR LOS 151 POKEMONS");

        //musica de fondo

            mp = MediaPlayer.create(AboutMeActivity.this, R.raw.pokemon_rap);
            mp.start();


    }

    @Override
    public void onDestroy() {
        if(mp != null) mp.release();
        super.onDestroy();
    }
}

