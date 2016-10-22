package jrdv.mio.com.entrenatablasmultiplicacion;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cardinalsolutions.android.arch.autowire.AndroidLayout;
import com.cardinalsolutions.android.arch.autowire.AndroidView;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.builder.AnimateGifMode;

import java.util.Locale;
import java.util.Random;

import static jrdv.mio.com.entrenatablasmultiplicacion.ajustesActivity.PREF_TablaMAximaElegida;


@AndroidLayout(jrdv.mio.com.entrenatablasmultiplicacion.R.layout.activity_login_pad)

public class LoginPadActivity extends BaseActivity implements View.OnClickListener,
        View.OnTouchListener {

	private static final String TAG = "LoginPadActivity";

	@AndroidView(jrdv.mio.com.entrenatablasmultiplicacion.R.id.activity_login_access_code_value)
    private EditText mUserAccessCode;

   // @AndroidView(jrdv.mio.com.entrenatablasmultiplicacion.R.id.activity_login_access_code_login)
    private TextView mLoginButton;

  //  @AndroidView(jrdv.mio.com.entrenatablasmultiplicacion.R.id.login_button_progress)
    private ProgressBar mLoginProgress;

    @AndroidView(jrdv.mio.com.entrenatablasmultiplicacion.R.id.one_button)
    private TextView mOneButton;

    @AndroidView(jrdv.mio.com.entrenatablasmultiplicacion.R.id.two_button)
    private TextView mTwoButton;

    @AndroidView(jrdv.mio.com.entrenatablasmultiplicacion.R.id.three_button)
    private TextView mThreeButton;

    @AndroidView(jrdv.mio.com.entrenatablasmultiplicacion.R.id.four_button)
    private TextView mFourButton;

    @AndroidView(jrdv.mio.com.entrenatablasmultiplicacion.R.id.five_button)
    private TextView mFiveButton;

    @AndroidView(jrdv.mio.com.entrenatablasmultiplicacion.R.id.six_button)
    private TextView mSixButton;

    @AndroidView(jrdv.mio.com.entrenatablasmultiplicacion.R.id.seven_button)
    private TextView mSevenButton;

    @AndroidView(jrdv.mio.com.entrenatablasmultiplicacion.R.id.eight_button)
    private TextView mEightButton;

    @AndroidView(jrdv.mio.com.entrenatablasmultiplicacion.R.id.nine_button)
    private TextView mNineButton;

    @AndroidView(jrdv.mio.com.entrenatablasmultiplicacion.R.id.zero_button)
    private TextView mZeroButton;

    @AndroidView(jrdv.mio.com.entrenatablasmultiplicacion.R.id.activity_login_access_code_delete)
    private TextView mDeleteButton;

    // Animations
    private OvershootInterpolator mAnimationSlideInterpolator = new OvershootInterpolator(1.0f);
    private Animation mAnimSlideIn;
    private Animation mAnimSlideOut;

    //new shake:

    private  Animation mShakeAnimation;

    private boolean mDeleteIsShowing = false;
    private boolean mFailedLogin = false;


	public static   int USER_PIN_MAX_CHAR = 2;


    //preferences

    public static final String PREFS_NAME = "MyPrefsFile";
    public static  final  String PREF_BOOL_NINOYAOK="niñook" ;
    public static final String PREF_PUNTOS_PARA_VER_POKEMONS="puntospokemos";//max 151



    //variables multiplicacion

    private int tablaMaxEnPref;
    private int primerdigito;
    private int segundodigito;
    private int numeroAciertos=0;

    //textview multitplicacvion

    private TextView MultiplicacionTextview ;

    //para hablar

    private TextToSpeech textToSpeech;

    //PARA LOS PUNTOS

    private int puntosActuales;
    private String NombreNinoElegido;

    //para el pokjemon oculto

    private ImageView PokemonOcultoAnimadoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //definios quien es Gifview


        PokemonOcultoAnimadoView=(ImageView)findViewById(R.id.pokemonocultoview);

        //recupermos los valores del SharedPRefs sis e guardaron tras el signup activity

        SharedPreferences pref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);


        boolean alreadyloggedinbefore =  pref.getBoolean(PREF_BOOL_NINOYAOK, false);//falso si no existe
        tablaMaxEnPref=pref.getInt(ajustesActivity.PREF_TablaMAximaElegida,0);//por defecto vale 0
        puntosActuales=pref.getInt(PREF_PUNTOS_PARA_VER_POKEMONS,0);//por defecto vale 0

        //recupoeramos el nombre delk niño si tenia si no cambia el texto de wellcome


        NombreNinoElegido= pref.getString(ajustesActivity.PREF_NOMBRE_NINO,"NONAME");



        Log.d(TAG, "niño ya eligio tabla y nombre: "+String.valueOf(alreadyloggedinbefore));
        Log.d(TAG, "niño tiene : "+puntosActuales + "PUNTOS");
        //textview multitplicacvion
        MultiplicacionTextview =(TextView)findViewById(R.id.preguntaMultiplicacion);




         //tablaMaxEnPref=pref.getInt(ajustesActivity.PREF_TablaMAximaElegida,0);//por defecto vale 0






	    // only show the login pad if the  user hasn't logged in
	  /*  if (didLogIn()) {
		    closeActivity();
	    }
    */

        if (alreadyloggedinbefore)
        //si ya habiamos metido datos del niño y tablas maximas:
            {


                Log.d(TAG, "niño ya eligio tablaMax "+tablaMaxEnPref);

            configureViews();
            configureAnimations();
            setEditTextListener();

                iniciaVoces();






               generaMultiplicacion();


        }
        else {



            configureViews();
            configureAnimations();
            setEditTextListener();
            iniciaVoces();


            //ve  a la pantalla de meter nombre y tabla maxima!!

            Intent intent = new Intent(this, jrdv.mio.com.entrenatablasmultiplicacion.ajustesActivity.class);
            startActivity(intent);
        }






}


    private void iniciaVoces(){


        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    Locale locale = new Locale("es", "ES");

                    int result = textToSpeech.setLanguage(locale);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    }

                    // speak(saludoInicial);//aqui on habla de tiron!!!

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




    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "VUELTO A MAIN DESDE AJSUTES!!! "  );


        //recupermos los valores del MAXTABLE ACTUALIZADOS

        SharedPreferences pref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);


        tablaMaxEnPref=pref.getInt(PREF_TablaMAximaElegida,0);//por defecto vale 0

        generaMultiplicacion();
    }



	private void generaMultiplicacion(){




        Random r = new Random();
        primerdigito = r.nextInt(10 - 1) + 1;//de 1 a 10
        segundodigito =  r.nextInt(tablaMaxEnPref) + 1;// de 1 al avalor maximo de tabla

        MultiplicacionTextview.setText(String.valueOf(primerdigito) +"X"+ String.valueOf(segundodigito));


        speak("Y cuanto es , "+primerdigito +"por"+segundodigito+"?");

        if ((primerdigito*segundodigito)<10) {
            USER_PIN_MAX_CHAR=1;

        }
        else if ((primerdigito*segundodigito)==100){
            USER_PIN_MAX_CHAR=3;

        }
        else {
            USER_PIN_MAX_CHAR=2;
        }



    }

    private void configureViews() {
      //  this.mLoginProgress.setVisibility(View.GONE);
       // this.mLoginButton.setOnClickListener(this);
      //  this.mLoginButton.setVisibility(View.GONE);
        this.mOneButton.setOnClickListener(this);
        this.mOneButton.setOnTouchListener(this);
        this.mTwoButton.setOnClickListener(this);
        this.mTwoButton.setOnTouchListener(this);
        this.mThreeButton.setOnClickListener(this);
        this.mThreeButton.setOnTouchListener(this);
        this.mFourButton.setOnClickListener(this);
        this.mFourButton.setOnTouchListener(this);
        this.mFiveButton.setOnClickListener(this);
        this.mFiveButton.setOnTouchListener(this);
        this.mSixButton.setOnClickListener(this);
        this.mSixButton.setOnTouchListener(this);
        this.mSevenButton.setOnClickListener(this);
        this.mSevenButton.setOnTouchListener(this);
        this.mEightButton.setOnClickListener(this);
        this.mEightButton.setOnTouchListener(this);
        this.mNineButton.setOnClickListener(this);
        this.mNineButton.setOnTouchListener(this);
        this.mZeroButton.setOnClickListener(this);
        this.mZeroButton.setOnTouchListener(this);
        this.mDeleteButton.setVisibility(View.INVISIBLE);
        this.mDeleteButton.setOnClickListener(this);
    }

    private void setEditTextListener() {
        this.mUserAccessCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if (LoginPadActivity.this.mUserAccessCode.getText().length() == USER_PIN_MAX_CHAR) {

                    Log.e("INFO","hemos llegado al nuemro maximo");


                    String numerometido=LoginPadActivity.this.mUserAccessCode.getText().toString();

                    int numeroenINT=Integer.parseInt(numerometido);

                    if (numeroenINT==(primerdigito*segundodigito)) {

                        //hemos acertado siguiente:
                        ChequeaResultado(false);
                    }

                    else{

                        //hemos fallado
                        ChequeaResultado(true);


                    }

                }
            }
        });
    }

    private void configureAnimations() {
        this.mAnimSlideIn = AnimationUtils
                .loadAnimation(getApplicationContext(),
                        jrdv.mio.com.entrenatablasmultiplicacion.R.anim.slide_in_from_left_small_bounce);
         this.mAnimSlideIn.setInterpolator(this.mAnimationSlideInterpolator);



        this.mAnimSlideOut = AnimationUtils.loadAnimation(
                getApplicationContext(), jrdv.mio.com.entrenatablasmultiplicacion.R.anim.slide_out_right);
        this.mAnimSlideOut.setInterpolator(this.mAnimationSlideInterpolator);


        //new shake si falla:

        this.mShakeAnimation = AnimationUtils.loadAnimation(
                getApplicationContext(), R.anim.shake);
        this.mShakeAnimation.setInterpolator(this.mAnimationSlideInterpolator);


        this.mAnimSlideOut.setAnimationListener(new Animation.AnimationListener() {

	        @Override
	        public void onAnimationStart(Animation animationIn) {
	        }

	        @Override
	        public void onAnimationRepeat(Animation animationIn) {
	        }

	        @Override
	        public void onAnimationEnd(Animation animationIn) {
                //nunca se ejecuta
		        LoginPadActivity.this.mLoginButton.setVisibility(View.GONE);
		        if (LoginPadActivity.this.mFailedLogin) {
			        crossFade(getResources().getInteger(android.R.integer.config_mediumAnimTime),
			                  LoginPadActivity.this.mDeleteButton, null);
			        LoginPadActivity.this.mDeleteIsShowing = false;
			        LoginPadActivity.this.mFailedLogin = false;
		        }
	        }
        });
    }

    private void ChequeaResultado(boolean fallo) {

        /*
        TODO animar el borrado del numero metido
        if (animateIn) {
            this.mLoginButton.setVisibility(View.VISIBLE);
            this.mLoginButton.startAnimation(this.mAnimSlideIn);
        } else {
            this.mLoginProgress.setVisibility(View.GONE);
            this.mLoginButton.startAnimation(this.mAnimSlideOut);
            this.mLoginButton.setVisibility(View.GONE);
            this.mLoginButton.setText(getResources().getString(
                    jrdv.mio.com.entrenatablasmultiplicacion.R.string.activity_login));
        }*/


        //lo hago con el numero metido

        if (fallo) {
            //si hemos fallado:
          //  this.mUserAccessCode.startAnimation(this.mAnimSlideIn);

            //hago el shake
            this.mUserAccessCode.startAnimation(this.mShakeAnimation);

            //reseteo el numeor de aciertos

            numeroAciertos=0;


            //que vibre al fallar

            // vibration for 800 milliseconds
            ((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(800);

            speak("no");


        } else {
            ////si hemos acertado:

            //this.mLoginProgress.setVisibility(View.GONE);
            // this.mUserAccessCode.startAnimation(this.mAnimSlideOut);
            Log.e("INFO", "acertaste!!!!!");

            //borramos numero

            LoginPadActivity.this.mUserAccessCode.setText("");

            //aumentamos el num de qaciertos

            numeroAciertos++;
            Log.e("INFO", "numerode aciertos: " + numeroAciertos);

            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //////////////////////////////////////////////////////////LOGICA DE LOS PUNTOS/////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


            //TODO recupermos los puntos(aunque se podria quitar ya se recuperanen oncreate!!)
            SharedPreferences pref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = pref.edit();

            puntosActuales = pref.getInt(PREF_PUNTOS_PARA_VER_POKEMONS, 0);//por defecto vale 0



            switch (numeroAciertos) {

                case 1://1 punto mas




                    //aqui tenemos 3 variables a controlar:
                    //1º)la tabalamaxima elegida:tablaMaxEnPref
                    //2º)el numero de aciertos:numeroAciertos
                    //3º)lo puntos que ya tenia:puntosActuales


                    //si ya tenemos los 151 ptos maximos no se suman mas!!

                    if (puntosActuales >= 151) {
                        break;
                    }


                    //ahora dependiendo del nivel qu este puede conseguir un numero maximo
                    //tabla 1:max 10 ptos
                    //tabla 2:max 20 ptos
                    //tabla 3:max 30 ptos
                    // tabla 4:max 40 ptos
                    // tabla 5:max 50 ptos
                    //tabla 6:max 60 ptos
                    //tabla 7:max 70 ptos
                    //tabla 8:max 80 ptos
                    //tabla 9:max 90 ptos
                    //tabla 10:max 151 ptos

                    if (tablaMaxEnPref!=10 && ((tablaMaxEnPref*10)<puntosActuales) ){

                        //salimos ya tenemos los maximos tambien salvo en 10 que pueden ser 150
                        break;
                    }


                    puntosActuales++;

                    edit.putInt(PREF_PUNTOS_PARA_VER_POKEMONS, puntosActuales);


                    // Commit the changes
                    edit.commit();

                    Log.d(TAG, "niño ya AHORA TIENE  "+puntosActuales + " PUNTOS");


                    //TODO esto no suena porque suena la siguinete multiploicacion
                   // speak("acabas de ganar 1 punto para tu pokedex");

                    //avisamos del punto conseguido!!!

                    final Dialog dialog = new Dialog(this);
                    dialog.setContentView(R.layout.dialogalertlayout);
                    dialog.setTitle(NombreNinoElegido);

                    TextView textView = (TextView) dialog.findViewById(R.id.dialogtext);
                    textView.setText("HAS GANADO 1 PUNTO!!!");

                    ImageButton btnExit = (ImageButton) dialog.findViewById(R.id.btnExit);
                    btnExit.setOnClickListener(new View.OnClickListener() {
                        @Override public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    // show dialog on screen
                    dialog.show();


                    break;

            case 2://2 puntos mas




                //aqui tenemos 3 variables a controlar:
                //1º)la tabalamaxima elegida:tablaMaxEnPref
                //2º)el numero de aciertos:numeroAciertos
                //3º)lo puntos que ya tenia:puntosActuales


                //si ya tenemos los 151 ptos maximos no se suman mas!!

                if (puntosActuales >= 151) {
                    break;
                }


                //ahora dependiendo del nivel qu este puede conseguir un numero maximo
                //tabla 1:max 10 ptos
                //tabla 2:max 20 ptos
                //tabla 3:max 30 ptos
                // tabla 4:max 40 ptos
                // tabla 5:max 50 ptos
                //tabla 6:max 60 ptos
                //tabla 7:max 70 ptos
                //tabla 8:max 80 ptos
                //tabla 9:max 90 ptos
                //tabla 10:max 151 ptos

                if (tablaMaxEnPref!=10 && ((tablaMaxEnPref*10)<puntosActuales) ){

                    //salimos ya tenemos los maximos tambien salvo en 10 que pueden ser 150
                    break;
                }


                puntosActuales++;
                puntosActuales++;

                edit.putInt(PREF_PUNTOS_PARA_VER_POKEMONS, puntosActuales);


                // Commit the changes
                edit.commit();

                Log.d(TAG, "niño ya AHORA TIENE  "+puntosActuales + " PUNTOS");

                //avisamos del punto conseguido!!!

                final Dialog dialog2 = new Dialog(this);
                dialog2.setContentView(R.layout.dialogalertlayout);
                dialog2.setTitle(NombreNinoElegido);

                TextView textView2 = (TextView) dialog2.findViewById(R.id.dialogtext);
                textView2.setText("HAS GANADO 2 PUNTOS!!");

                ImageButton btnExit2 = (ImageButton) dialog2.findViewById(R.id.btnExit);
                btnExit2.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        dialog2.dismiss();
                    }
                });
                // show dialog on screen
                dialog2.show();

                    break;

                case 3://2 puntos mas


                    //aqui tenemos 3 variables a controlar:
                    //1º)la tabalamaxima elegida:tablaMaxEnPref
                    //2º)el numero de aciertos:numeroAciertos
                    //3º)lo puntos que ya tenia:puntosActuales


                    //si ya tenemos los 151 ptos maximos no se suman mas!!

                    if (puntosActuales >= 151) {
                        break;
                    }


                    //ahora dependiendo del nivel qu este puede conseguir un numero maximo
                    //tabla 1:max 10 ptos
                    //tabla 2:max 20 ptos
                    //tabla 3:max 30 ptos
                    // tabla 4:max 40 ptos
                    // tabla 5:max 50 ptos
                    //tabla 6:max 60 ptos
                    //tabla 7:max 70 ptos
                    //tabla 8:max 80 ptos
                    //tabla 9:max 90 ptos
                    //tabla 10:max 151 ptos

                    if (tablaMaxEnPref!=10 && ((tablaMaxEnPref*10)<puntosActuales) ){

                        //salimos ya tenemos los maximos tambien salvo en 10 que pueden ser 150
                        break;
                    }


                    puntosActuales++;
                    puntosActuales++;

                    edit.putInt(PREF_PUNTOS_PARA_VER_POKEMONS, puntosActuales);


                    // Commit the changes
                    edit.commit();


                    //avisamos del punto conseguido!!!

                    final Dialog dialog3 = new Dialog(this);
                    dialog3.setContentView(R.layout.dialogalertlayout);
                    dialog3.setTitle(NombreNinoElegido);

                    TextView textView3 = (TextView) dialog3.findViewById(R.id.dialogtext);
                    textView3.setText("HAS GANADO 3 PUNTOS!!");

                    ImageButton btnExit3 = (ImageButton) dialog3.findViewById(R.id.btnExit);
                    btnExit3.setOnClickListener(new View.OnClickListener() {
                        @Override public void onClick(View v) {
                            dialog3.dismiss();
                        }
                    });
                    // show dialog on screen
                    dialog3.show();


                    Log.d(TAG, "niño ya AHORA TIENE  "+puntosActuales + " PUNTOS");

                    break;

                case 5://5 puntos mas


                    //aqui tenemos 3 variables a controlar:
                    //1º)la tabalamaxima elegida:tablaMaxEnPref
                    //2º)el numero de aciertos:numeroAciertos
                    //3º)lo puntos que ya tenia:puntosActuales


                    //si ya tenemos los 151 ptos maximos no se suman mas!!

                    if (puntosActuales >= 151) {
                        break;
                    }


                    //ahora dependiendo del nivel qu este puede conseguir un numero maximo
                    //tabla 1:max 10 ptos
                    //tabla 2:max 20 ptos
                    //tabla 3:max 30 ptos
                    // tabla 4:max 40 ptos
                    // tabla 5:max 50 ptos
                    //tabla 6:max 60 ptos
                    //tabla 7:max 70 ptos
                    //tabla 8:max 80 ptos
                    //tabla 9:max 90 ptos
                    //tabla 10:max 151 ptos

                    if (tablaMaxEnPref!=10 && ((tablaMaxEnPref*10)<puntosActuales) ){

                        //salimos ya tenemos los maximos tambien salvo en 10 que pueden ser 150
                        break;
                    }


                    puntosActuales++;
                    puntosActuales++;
                    puntosActuales++;
                    puntosActuales++;
                    puntosActuales++;




                    edit.putInt(PREF_PUNTOS_PARA_VER_POKEMONS, puntosActuales);


                    // Commit the changes
                    edit.commit();


                    //avisamos del punto conseguido!!!

                    final Dialog dialog4 = new Dialog(this);
                    dialog4.setContentView(R.layout.dialogalertlayout);
                    dialog4.setTitle(NombreNinoElegido);

                    TextView textView4 = (TextView) dialog4.findViewById(R.id.dialogtext);
                    textView4.setText("HAS GANADO 5 PUNTOS!!");

                    ImageButton btnExit4 = (ImageButton) dialog4.findViewById(R.id.btnExit);
                    btnExit4.setOnClickListener(new View.OnClickListener() {
                        @Override public void onClick(View v) {
                            dialog4.dismiss();

                            // TODO video de premio 1

                            //creamsos la animacion
                            final Animation myAnim = AnimationUtils.loadAnimation(LoginPadActivity.this, R.anim.bounce2);

                            // Use bounce interpolator with amplitude 0.2 and frequency 20
                            BounceInterpolator interpolator = new BounceInterpolator(0.2, 20);
                            myAnim.setInterpolator(interpolator);


                            //elegimos uno al azar

                            Random r = new Random();
                            int i1 = r.nextInt(3 - 1) + 1;
                            final String str = "pokemon_animado" + String.valueOf(i1);
                            PokemonOcultoAnimadoView.setImageDrawable
                                    (
                                            getResources().getDrawable(getResourceID(str, "drawable",
                                                    getApplicationContext()))
                                    );

                            //hacemos visible la PokemoAniamdoiView
                            PokemonOcultoAnimadoView.setVisibility(View.VISIBLE);


                            //lo hacemo el gif animado:

                                /*
                            //ASI DA ERROR DE GIF RESOURCE Y PONE EL DE ERROR...NPI
                            String finalResourceparaIon="R.drawable."+str;


                            Ion.with(PokemonOcultoAnimadoView)
                                    .error(R.drawable.rosa_1)
                                    .animateGif(AnimateGifMode.ANIMATE)
                                    // .load("android.resource://[packagename]" + R.drawable.optinscreen_map)
                                     .load("android.resource://jrdv.mio.com.entrenatablasmultiplicacion/" + R.drawable.pokemon_animado1);
                                    //.load("android.resource://jrdv.mio.com.entrenatablasmultiplicacion/" + finalResourceparaIon);


                            */

                            //ponemos un sonido

                            MediaPlayer mp = MediaPlayer.create(LoginPadActivity.this, R.raw.pikachu5);
                            mp.start();



                            switch (i1){

                                case 1:

                                    Ion.with(PokemonOcultoAnimadoView)
                                            .error(R.drawable.rosa_1)
                                            .animateGif(AnimateGifMode.ANIMATE)
                                            // .load("android.resource://[packagename]" + R.drawable.optinscreen_map)
                                            .load("android.resource://jrdv.mio.com.entrenatablasmultiplicacion/" + R.drawable.pokemon_animado1);

                                    //animamos
                                    PokemonOcultoAnimadoView.startAnimation(myAnim);


                                    break;

                                case 2:

                                    Ion.with(PokemonOcultoAnimadoView)
                                            .error(R.drawable.rosa_1)
                                            .animateGif(AnimateGifMode.ANIMATE)
                                            // .load("android.resource://[packagename]" + R.drawable.optinscreen_map)
                                            .load("android.resource://jrdv.mio.com.entrenatablasmultiplicacion/" + R.drawable.pokemon_animado2);

                                    //animamos
                                    PokemonOcultoAnimadoView.startAnimation(myAnim);

                                    break;

                                case 3:

                                    Ion.with(PokemonOcultoAnimadoView)
                                            .error(R.drawable.rosa_1)
                                            .animateGif(AnimateGifMode.ANIMATE)
                                            // .load("android.resource://[packagename]" + R.drawable.optinscreen_map)
                                            .load("android.resource://jrdv.mio.com.entrenatablasmultiplicacion/" + R.drawable.pokemon_animado3);

                                    //animamos
                                    PokemonOcultoAnimadoView.startAnimation(myAnim);


                                    break;



                            }




                            //ponemos un listener para que lo haga invisble cuando acabe!!


                            myAnim.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {

                                    PokemonOcultoAnimadoView.setVisibility(View.INVISIBLE);

                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });


                        }





                    });
                    // show dialog on screen
                    dialog4.show();

                    Log.d(TAG, "niño ya AHORA TIENE  "+puntosActuales + " PUNTOS");





                    break;

                case 70://video

                //TODO video de premio 2
                    break;

        }//fin del switch
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //generamos nueva multiplicacion


            generaMultiplicacion();
        }
    }

    @Override
    public void onClick(View vIn) {
        if (!this.mDeleteIsShowing) {
            crossFade(
                    getResources().getInteger(
                            android.R.integer.config_mediumAnimTime),
                    this.mDeleteButton,
                    getResources().getString(R.string.activity_login_delete));
            this.mDeleteIsShowing = true;
        }
        switch (vIn.getId()) {
            case  jrdv.mio.com.entrenatablasmultiplicacion.R.id.one_button:
                if (this.mUserAccessCode.getText().length() < USER_PIN_MAX_CHAR) {
                    this.mUserAccessCode.append(this.mOneButton.getText());
                }
                break;
            case jrdv.mio.com.entrenatablasmultiplicacion.R.id.two_button:
                if (this.mUserAccessCode.getText().length() < USER_PIN_MAX_CHAR) {
                    this.mUserAccessCode.append(this.mTwoButton.getText());
                }
                break;
            case jrdv.mio.com.entrenatablasmultiplicacion.R.id.three_button:
                if (this.mUserAccessCode.getText().length() < USER_PIN_MAX_CHAR) {
                    this.mUserAccessCode.append(this.mThreeButton.getText());
                }
                break;
            case jrdv.mio.com.entrenatablasmultiplicacion.R.id.four_button:
                if (this.mUserAccessCode.getText().length() < USER_PIN_MAX_CHAR) {
                    this.mUserAccessCode.append(this.mFourButton.getText());
                }
                break;
            case jrdv.mio.com.entrenatablasmultiplicacion.R.id.five_button:
                if (this.mUserAccessCode.getText().length() < USER_PIN_MAX_CHAR) {
                    this.mUserAccessCode.append(this.mFiveButton.getText());
                }
                break;
            case jrdv.mio.com.entrenatablasmultiplicacion.R.id.six_button:
                if (this.mUserAccessCode.getText().length() < USER_PIN_MAX_CHAR) {
                    this.mUserAccessCode.append(this.mSixButton.getText());
                }
                break;
            case jrdv.mio.com.entrenatablasmultiplicacion.R.id.seven_button:
                if (this.mUserAccessCode.getText().length() < USER_PIN_MAX_CHAR) {
                    this.mUserAccessCode.append(this.mSevenButton.getText());
                }
                break;
            case jrdv.mio.com.entrenatablasmultiplicacion.R.id.eight_button:
                if (this.mUserAccessCode.getText().length() < USER_PIN_MAX_CHAR) {
                    this.mUserAccessCode.append(this.mEightButton.getText());
                }
                break;
            case jrdv.mio.com.entrenatablasmultiplicacion.R.id.nine_button:
                if (this.mUserAccessCode.getText().length() < USER_PIN_MAX_CHAR) {
                    this.mUserAccessCode.append(this.mNineButton.getText());
                }
                break;
            case jrdv.mio.com.entrenatablasmultiplicacion.R.id.zero_button:
                if (this.mUserAccessCode.getText().length() < USER_PIN_MAX_CHAR) {
                    this.mUserAccessCode.append(this.mZeroButton.getText());
                }
                break;

        
            case jrdv.mio.com.entrenatablasmultiplicacion.R.id.activity_login_access_code_delete:

               // if (this.mLoginButton.getVisibility() == View.VISIBLE) {
               //     ChequeaResultado(false);
               // }
                this.mUserAccessCode.dispatchKeyEvent(new KeyEvent(
                        KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                if (isEditTextEmpty(this.mUserAccessCode)) {
                    crossFade(
                            getResources().getInteger(
                                    android.R.integer.config_mediumAnimTime),
                            this.mDeleteButton, null);
                    this.mDeleteIsShowing = false;
                }
                break;
        }
    }

    private void crossFade(int animTimeIn, TextView textViewIn,
                          String valueStringIn) {

        textViewIn.setText(valueStringIn);
        textViewIn.setAlpha(0f);
        textViewIn.setVisibility(View.VISIBLE);

        textViewIn.animate().alpha(1f).setDuration(animTimeIn)
                .setListener(null);
    }

    private static boolean isEditTextEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    @Override
    public boolean onTouch(View vIn, MotionEvent eventIn) {
        switch (vIn.getId()) {
            case jrdv.mio.com.entrenatablasmultiplicacion.R.id.one_button:
                toggleNumberColor(vIn, eventIn);
                break;
            case jrdv.mio.com.entrenatablasmultiplicacion.R.id.two_button:
                toggleNumberColor(vIn, eventIn);
                break;
            case jrdv.mio.com.entrenatablasmultiplicacion.R.id.three_button:
                toggleNumberColor(vIn, eventIn);
                break;
            case jrdv.mio.com.entrenatablasmultiplicacion.R.id.four_button:
                toggleNumberColor(vIn, eventIn);
                break;
            case jrdv.mio.com.entrenatablasmultiplicacion.R.id.five_button:
                toggleNumberColor(vIn, eventIn);
                break;
            case jrdv.mio.com.entrenatablasmultiplicacion.R.id.six_button:
                toggleNumberColor(vIn, eventIn);
                break;
            case jrdv.mio.com.entrenatablasmultiplicacion.R.id.seven_button:
                toggleNumberColor(vIn, eventIn);
                break;
            case jrdv.mio.com.entrenatablasmultiplicacion.R.id.eight_button:
                toggleNumberColor(vIn, eventIn);
                break;
            case jrdv.mio.com.entrenatablasmultiplicacion.R.id.nine_button:
                toggleNumberColor(vIn, eventIn);
                break;
            case jrdv.mio.com.entrenatablasmultiplicacion.R.id.zero_button:
                toggleNumberColor(vIn, eventIn);
                break;
        }
        return false;
    }

    private void toggleNumberColor(View viewIn, MotionEvent eventIn) {
        if (eventIn.getAction() == MotionEvent.ACTION_DOWN) {
            ((TextView) viewIn).setTextColor(getResources().getColor(
                    R.color.blue));
            /*
            switch (viewIn.getId()) {
                case jrdv.mio.com.entrenatablasmultiplicacion.R.id.one_button:
                    mOneButton.setTextColor(getResources().getColor(R.color.blue));
                    mOneButton.setPressed(true);
                    break;
                case jrdv.mio.com.entrenatablasmultiplicacion.R.id.two_button:
                    mOneButton.setTextColor(getResources().getColor(R.color.blue));
                    mTwoButton.setTextColor(getResources().getColor(R.color.blue));
                    mOneButton.setPressed(true);
                    mTwoButton.setPressed(true);
                    break;
                case jrdv.mio.com.entrenatablasmultiplicacion.R.id.three_button:
                    mOneButton.setTextColor(getResources().getColor(R.color.blue));
                    mTwoButton.setTextColor(getResources().getColor(R.color.blue));
                    mThreeButton.setTextColor(getResources().getColor(R.color.blue));
                    mOneButton.setPressed(true);
                    mTwoButton.setPressed(true);
                    mThreeButton.setPressed(true);


                    break;
                case jrdv.mio.com.entrenatablasmultiplicacion.R.id.four_button:
                    mOneButton.setTextColor(getResources().getColor(R.color.blue));
                    mTwoButton.setTextColor(getResources().getColor(R.color.blue));
                    mThreeButton.setTextColor(getResources().getColor(R.color.blue));
                    mFourButton.setTextColor(getResources().getColor(R.color.blue));
                    mOneButton.setPressed(true);
                    mTwoButton.setPressed(true);
                    mThreeButton.setPressed(true);
                    mFourButton.setPressed(true);

                    break;
                case jrdv.mio.com.entrenatablasmultiplicacion.R.id.five_button:

                    break;
                case jrdv.mio.com.entrenatablasmultiplicacion.R.id.six_button:

                    break;
                case jrdv.mio.com.entrenatablasmultiplicacion.R.id.seven_button:

                    break;
                case jrdv.mio.com.entrenatablasmultiplicacion.R.id.eight_button:

                    break;
                case jrdv.mio.com.entrenatablasmultiplicacion.R.id.nine_button:

                    break;
                case jrdv.mio.com.entrenatablasmultiplicacion.R.id.zero_button:

                    break;
            }
                */

        } else if (eventIn.getAction() == MotionEvent.ACTION_UP) {
                 ((TextView) viewIn).setTextColor(getResources().getColor(jrdv.mio.com.entrenatablasmultiplicacion.R.color.white));
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////PULSADO UN BOTON POKEBALL/////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void PulsadoPokeballVolverAjustes(View view) {


        //ve  a la pantalla de meter nombre y tabla maxima!!

        Intent intent = new Intent(this, jrdv.mio.com.entrenatablasmultiplicacion.ajustesActivity.class);
        startActivity(intent);

    }

    public void PulsadoPokeballVerListaPokemon(View view) {

        //ve  a la pantalla los poekmons!!"!


        Intent intent2 = new Intent(this, jrdv.mio.com.entrenatablasmultiplicacion.PokedexActivity.class);
        startActivity(intent2);


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
}
