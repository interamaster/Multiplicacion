package jrdv.mio.com.entrenatablasmultiplicacion;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.builder.AnimateGifMode;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LoginActivity extends Activity {

    private ImageView GifVew;






    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);



        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////mio///////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //definios quien es Gifview

        GifVew =(ImageView)findViewById(R.id.picachu_gif);


        ///animamos el pokemon!!!!


        Ion.with(GifVew)
                .error(R.drawable.rosa_1)
                .animateGif(AnimateGifMode.ANIMATE)
                // .load("android.resource://[packagename]" + R.drawable.optinscreen_map)
                .load("android.resource://jrdv.mio.com.entrenatablasmultiplicacion/" + R.drawable.pokemo_hablando);
    }


    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
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

                //TODO guardar ivar con valor TABLA MAXIMA a PREGUNTAR!!



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
                //TODO guardar ivar con valor TABLA MAXIMA a PREGUNTAR!!

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
                //TODO guardar ivar con valor TABLA MAXIMA a PREGUNTAR!!
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
                //TODO guardar ivar con valor TABLA MAXIMA a PREGUNTAR!!
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
                //TODO guardar ivar con valor TABLA MAXIMA a PREGUNTAR!!


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
                //TODO guardar ivar con valor TABLA MAXIMA a PREGUNTAR!!

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
                //TODO guardar ivar con valor TABLA MAXIMA a PREGUNTAR!!

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
                //TODO guardar ivar con valor TABLA MAXIMA a PREGUNTAR!!

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
                //TODO guardar ivar con valor TABLA MAXIMA a PREGUNTAR!!

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
                //TODO guardar ivar con valor TABLA MAXIMA a PREGUNTAR!!

                break;
        }

    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    }
