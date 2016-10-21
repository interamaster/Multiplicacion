package jrdv.mio.com.entrenatablasmultiplicacion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cardinalsolutions.android.arch.autowire.AndroidLayout;
import com.cardinalsolutions.android.arch.autowire.AndroidView;

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


    //variables multiplicacion

    private int tablaMaxEnPref;
    private int primerdigito;
    private int segundodigito;
    private int numeroAciertos=0;

    //textview multitplicacvion

    private TextView MultiplicacionTextview ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //recupermos los valores del SharedPRefs sis e guardaron tras el signup activity

        SharedPreferences pref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);


        boolean alreadyloggedinbefore =  pref.getBoolean(PREF_BOOL_NINOYAOK, false);//falso si no existe
        tablaMaxEnPref=pref.getInt(ajustesActivity.PREF_TablaMAximaElegida,0);//por defecto vale 0



        Log.d(TAG, "niño ya eligio tabla y nombre: "+String.valueOf(alreadyloggedinbefore));

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






               generaMultiplicacion();


        }
        else {



            configureViews();
            configureAnimations();
            setEditTextListener();


            //ve  a la pantalla de meter nombre y tabla maxima!!

            Intent intent = new Intent(this, jrdv.mio.com.entrenatablasmultiplicacion.ajustesActivity.class);
            startActivity(intent);
        }

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

    private void attemptLogin(String userPinIn) {
        /*
         Details left out for brevity and the numerous was one can attempt a login, but...
         attempt your login here and grab the response...

	    If your login is a success, pass the user pin to save to shared prefs and close your
	    activity

	    saveUserPinToSharedPrefs("success", userPinIn);
	    closeActivity();

	    else run animation and reset the text field...

	    Wrapping the failed response in a handler to pause execution so we can demo the animation.
	    You do not need the handler in real world use!!
	    */

	    Handler handler = new Handler();
	    handler.postDelayed(new Runnable() {
		    @Override
		    public void run() {
			    mFailedLogin = true;
			    animateLoginButtonInOut(false);
			    mUserAccessCode.setText("");
		    }
	    }, 3000);

    }

	private void generaMultiplicacion(){




        Random r = new Random();
        primerdigito = r.nextInt(10 - 1) + 1;//de 1 a 10
        segundodigito =  r.nextInt(tablaMaxEnPref) + 1;// de 1 al avalor maximo de tabla

        MultiplicacionTextview.setText(String.valueOf(primerdigito) +"X"+ String.valueOf(segundodigito));

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
                    //TODO ver si es correcto o no y hacer animacion o borrar:

                    String numerometido=LoginPadActivity.this.mUserAccessCode.getText().toString();

                    int numeroenINT=Integer.parseInt(numerometido);

                    if (numeroenINT==(primerdigito*segundodigito)) {

                        //hemos acertado siguiente:
                        animateLoginButtonInOut(false);
                    }

                    else{

                        //hemos fallado
                        animateLoginButtonInOut(true);


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

    private void animateLoginButtonInOut(boolean animateIn) {

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

        if (animateIn) {
            //si hemos fallado:
          //  this.mUserAccessCode.startAnimation(this.mAnimSlideIn);

            //hago el shake
            this.mUserAccessCode.startAnimation(this.mShakeAnimation);

            //reseteo el numeor de aciertos

            numeroAciertos=0;


        } else {
            ////si hemos acertado:

            //this.mLoginProgress.setVisibility(View.GONE);
           // this.mUserAccessCode.startAnimation(this.mAnimSlideOut);
            Log.e("INFO","acertaste!!!!!");

            //borramos numero

            LoginPadActivity.this.mUserAccessCode.setText("");

            //aumentamos el num de qaciertos

            numeroAciertos++;
            Log.e("INFO","numerode aciertos: "+ numeroAciertos);


            if (numeroAciertos==30){

                //TODO video de premio 1

            }

            if (numeroAciertos==50){

                //TODO video de premio 2

            }

            if (numeroAciertos==70){

                //TODO video de premio 3
            }


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

          /*
          TODO boton de login o acierto/error
          case jrdv.mio.com.entrenatablasmultiplicacion.R.id.activity_login_access_code_login:
                crossFade(
                        getResources().getInteger(
                                android.R.integer.config_longAnimTime),
                        this.mLoginButton,
                        getResources().getString(jrdv.mio.com.entrenatablasmultiplicacion.R.string.activity_login));
                this.mLoginButton.setText("");
                this.mLoginProgress.setVisibility(View.VISIBLE);
                attemptLogin(this.mUserAccessCode.getText().toString());
                break;

                */
            case jrdv.mio.com.entrenatablasmultiplicacion.R.id.activity_login_access_code_delete:

               // if (this.mLoginButton.getVisibility() == View.VISIBLE) {
               //     animateLoginButtonInOut(false);
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
}
